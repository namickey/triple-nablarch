package com.nablarch.example.http.domain;

import com.nablarch.example.http.code.ProjectClass;
import com.nablarch.example.http.code.ProjectType;
import com.nablarch.example.http.validator.CodeValue;
import com.nablarch.example.http.validator.YYYYMMDD;
import nablarch.core.validation.ee.Digits;
import nablarch.core.validation.ee.Length;
import nablarch.core.validation.ee.NumberRange;
import nablarch.core.validation.ee.SystemChar;

/**
 * ドメインを定義したBeanクラス。
 *
 * @author Nabu Rakutaro
 */
@SuppressWarnings("all")
public class HttpMessagingExampleDomain {
    /** ID */
    @NumberRange(min = 0)
    @Digits(integer = 9)
    private String id;

    /** 顧客名 */
    @Length(max = 64)
    @SystemChar(charsetDef = "全角文字", allowLineSeparator = false)
    private String clientName;

    /** プロジェクト名 */
    @Length(max = 64)
    @SystemChar(charsetDef = "全角文字", allowLineSeparator = false)
    private String projectName;

    /** 新規開発PJ、または保守PJを表すコード値 */
    @CodeValue(enumClass = ProjectType.class)
    private String projectType;

    /** プロジェクトの規模を表すコード値 */
    @CodeValue(enumClass = ProjectClass.class)
    private String projectClass;

    /** 日付 */
    @YYYYMMDD(allowFormat = "yyyyMMdd")
    private String date;

    /** ユーザ氏名（漢字） */
    @Length(max = 64)
    @SystemChar(charsetDef = "全角文字", allowLineSeparator = false)
    private String userName;

    /** 備考 */
    @Length(max = 64)
    @SystemChar(charsetDef = "システム許容文字", allowLineSeparator = true)
    private String note;

    /** 金額 */
    @NumberRange(min = 0, max = 999999999, message = "{nablarch.core.validation.ee.NumberRange.money.range.message}")
    private String amountOfMoney;

}
