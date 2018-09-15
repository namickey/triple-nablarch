package com.nablarch.example.action;

import com.nablarch.example.form.ProjectForm;
import nablarch.common.dao.UniversalDao;
import nablarch.core.beans.BeanUtil;
import nablarch.core.dataformat.DataRecordFormatter;
import nablarch.core.dataformat.FormatterFactory;
import nablarch.core.db.statement.ParameterizedSqlPStatement;
import nablarch.core.db.statement.SqlPStatement;
import nablarch.core.db.statement.SqlResultSet;
import nablarch.core.db.statement.SqlRow;
import nablarch.core.message.ApplicationException;
import nablarch.core.util.FilePathSetting;
import nablarch.core.validation.ee.ValidatorUtil;
import nablarch.example.entity.Project;
import nablarch.fw.ExecutionContext;
import nablarch.fw.messaging.RequestMessage;
import nablarch.fw.messaging.ResponseMessage;
import nablarch.fw.messaging.action.MessagingAction;
import nablarch.fw.web.HttpResponse;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * JSON形式の電文でプロジェクト情報を受け付け、DBを更新する業務アクションクラス。
 *
 * @author Nabu Rakutaro
 */
public class ProjectSaveAction extends MessagingAction {

    /**
     * 電文を受信した際に実行される業務処理。
     * <p>
     * プロジェクト情報をバリデーションし、DBに登録する。
     * このメソッドは、一つのプロジェクトを登録するための処理である。
     * (汎用フォーマットによる形式チェックにより単独プロジェクトであることが保証される)
     * </p>
     * 登録が完了した場合は、レスポンスコードを記載した応答電文を設定する。
     * 例外が発生した場合は、{@link ProjectSaveAction#onError(Throwable, RequestMessage, ExecutionContext)}
     * にて応答電文を設定する。
     *
     * @param requestMessage   受信したメッセージ
     * @param executionContext 実行コンテキスト
     * @return 応答電文
     */
    @Override
    protected ResponseMessage onReceive(RequestMessage requestMessage, ExecutionContext executionContext) {

        // 入力値をフォームにバインドする
        ProjectForm form = BeanUtil.createAndCopy(ProjectForm.class, requestMessage.getParamMap());
        // バリデーションエラーがある場合は業務例外を送出
        ValidatorUtil.validate(form);




        //project_idの最大値を取得｡重複してしまう可能性があるので本来はシーケンスか採番テーブルを使うべき｡
        SqlPStatement statement = getSqlPStatement("SELECT_MAX_PROJECT_ID");
        SqlResultSet rs = statement.retrieve();
        int max = rs.get(0).getInteger("project_id");

        //↓参考
        System.out.println("size:" + rs.size());
        for (SqlRow row : rs){
            for(String key:row.keySet()){
                System.out.println("key:" + key);
            }
            System.out.println("max:" + row.getInteger("project_id"));
        }
        //↑参考


        //コピーして､プロジェクトEntityを生成する｡
        Project project = BeanUtil.createAndCopy(Project.class, form);


//        //insert
//        Project project = new Project();
        project.setProjectId(max + 1); //カウントアップ｡シーケンスを使う場合はカウントアップ不要｡
//        project.setProjectName("プロジェクト名");
//        project.setProjectType("development");
//        project.setProjectClass("a");
//        project.setProjectStartDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
//        project.setProjectEndDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
//        project.setClientId(1);
//        project.setProjectManager("佐藤");
//        project.setProjectLeader("鈴木");
//        project.setNote("備考８８８");
//        project.setSales(20000);
//        project.setCostOfGoodsSold(2000);
//        project.setSga(3000);
//        project.setAllocationOfCorpExpenses(4000);
        project.setVersion(0L); //楽観排他で使用するバージョン番号｡初期値ゼロ｡

        ParameterizedSqlPStatement pStatement = getParameterizedSqlStatement("INSERT_PROJECT", project);
        int i = pStatement.executeUpdateByObject(project);

        System.out.println("insert:" + i);




        // 応答電文のフォーマッタを作成する
        requestMessage.setFormatterOfReply(createFormatter());
        // 応答電文に記載するステータスコードを設定する
        Map<String, String> map = new HashMap<>();
        map.put("statusCode", String.valueOf(HttpResponse.Status.CREATED.getStatusCode()));
        // 応答データ返却
        return requestMessage.reply()
                .setStatusCodeHeader(String.valueOf(HttpResponse.Status.CREATED.getStatusCode()))
                .addRecord("data", map);
    }

    /**
     * 業務処理がエラー終了した場合に送信する応答電文の内容を設定する。
     * <p/>
     * 記載するステータスコードは以下。
     * <ul>
     *     <li>バリデーションエラーが発生した場合・・・400</li>
     *     <li>上記以外の例外が発生した場合・・・500</li>
     * </ul>
     *
     * @param e 発生した例外オブジェクト
     * @param requestMessage 要求電文オブジェクト
     * @param context 実行コンテキスト
     * @return 応答電文オブジェクト
     */
    @Override
    protected ResponseMessage onError(Throwable e,
                                      RequestMessage requestMessage,
                                      ExecutionContext context) {

        // 応答電文のフォーマッタを作成する
        requestMessage.setFormatterOfReply(createFormatter());

        // ステータスコードを特定する。
        String statusCode = String.valueOf(HttpResponse.Status.INTERNAL_SERVER_ERROR.getStatusCode());
        if (e instanceof ApplicationException) {
            statusCode = String.valueOf(HttpResponse.Status.BAD_REQUEST.getStatusCode());
        }

        Map<String, String> map = new HashMap<>();
        map.put("statusCode", statusCode);

        return requestMessage.reply()
                .setStatusCodeHeader(statusCode)
                .addRecord("data", map);
    }

    /**
     * 応答データのフォーマッタを取得する。
     *
     * @return レコードフォーマット
     */
    private static DataRecordFormatter createFormatter() {
        File file = FilePathSetting.getInstance().getFileIfExists("format", "ProjectSaveAction_REPLY");
        if (file == null) {
            throw new IllegalStateException("format file does not exist.");
        }
        return FormatterFactory.getInstance().createFormatter(file);
    }
}
