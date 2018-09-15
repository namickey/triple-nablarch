package com.nablarch.example.http.action;

import com.nablarch.example.http.form.ProjectForm;
import nablarch.core.beans.BeanUtil;
import nablarch.core.dataformat.DataRecordFormatter;
import nablarch.core.dataformat.FormatterFactory;
import nablarch.core.db.statement.ParameterizedSqlPStatement;
import nablarch.core.db.statement.SqlResultSet;
import nablarch.core.db.statement.SqlRow;
import nablarch.core.util.FilePathSetting;
import nablarch.core.validation.ee.ValidatorUtil;
import nablarch.example.entity.Project;
import nablarch.fw.ExecutionContext;
import nablarch.fw.messaging.RequestMessage;
import nablarch.fw.messaging.ResponseMessage;
import nablarch.fw.messaging.action.MessagingAction;
import nablarch.fw.web.HttpResponse;

import javax.persistence.OptimisticLockException;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ProjectUpdateAction extends MessagingAction {

    @Override
    protected ResponseMessage onReceive(RequestMessage requestMessage, ExecutionContext executionContext) {

        ProjectForm form = BeanUtil.createAndCopy(ProjectForm.class, requestMessage.getParamMap());
        ValidatorUtil.validate(form);


        //SELECT FOR UPDATE
        ParameterizedSqlPStatement pStatement = getParameterizedSqlStatement("SELECT_FOR_UPDATE");
        Project project = new Project();
        project.setProjectId(4);
        SqlResultSet rs = pStatement.retrieve(project);

        for (SqlRow row : rs) {
            for (String key : row.keySet()) {
                System.out.println("key:" + key + ", value:" + row.get(key));
            }
        }

        long version = rs.get(0).getLong("VERSION");
        System.out.println("version:" + version);
        String projectName = rs.get(0).getString("PROJECT_NAME");
        System.out.println("projectName:" + projectName);



        //UPDATE_PROJECT_NAME
        pStatement = getParameterizedSqlStatement("UPDATE_PROJECT_NAME");
        project = new Project();
        project.setProjectId(4);
        project.setProjectName(projectName + version);
        project.setVersion(version);
        int i = pStatement.executeUpdateByObject(project);
        System.out.println("update count:" + i);




        requestMessage.setFormatterOfReply(createFormatter());
        // 応答電文に記載するステータスコードを設定する
        Map<String, String> map = new HashMap<>();
        map.put("statusCode", String.valueOf(HttpResponse.Status.CREATED.getStatusCode()));
        // 応答データ返却
        return requestMessage.reply()
                .setStatusCodeHeader(String.valueOf(HttpResponse.Status.CREATED.getStatusCode()))
                .addRecord("data", map);
    }

    private static DataRecordFormatter createFormatter() {
        File file = FilePathSetting.getInstance().getFileIfExists("format", "http/ProjectSaveAction_REPLY");
        if (file == null) {
            throw new IllegalStateException("format file does not exist.");
        }
        return FormatterFactory.getInstance().createFormatter(file);
    }
}
