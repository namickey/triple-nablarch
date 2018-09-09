CREATE TABLE PUBLIC.PROJECT (
  PROJECT_ID SERIAL NOT NULL,
  PROJECT_NAME VARCHAR(128) NOT NULL,
  PROJECT_TYPE VARCHAR(128) NOT NULL,
  PROJECT_CLASS VARCHAR(128) NOT NULL,
  PROJECT_START_DATE DATE,
  PROJECT_END_DATE DATE,
  CLIENT_ID INTEGER,
  PROJECT_MANAGER VARCHAR(128),
  PROJECT_LEADER VARCHAR(128),
  USER_ID INTEGER,
  NOTE VARCHAR(512),
  SALES INTEGER,
  COST_OF_GOODS_SOLD INTEGER,
  SGA INTEGER,
  ALLOCATION_OF_CORP_EXPENSES INTEGER,
  VERSION BIGINT NOT NULL DEFAULT 1,
  PRIMARY KEY (PROJECT_ID)
);
COMMENT ON table PUBLIC.PROJECT is 'プロジェクト';
COMMENT ON column PUBLIC.PROJECT.PROJECT_ID is 'プロジェクトID';
COMMENT ON column PUBLIC.PROJECT.PROJECT_NAME is 'プロジェクト名';
COMMENT ON column PUBLIC.PROJECT.PROJECT_TYPE is 'プロジェクト種別';
COMMENT ON column PUBLIC.PROJECT.PROJECT_CLASS is 'プロジェクト分類';
COMMENT ON column PUBLIC.PROJECT.PROJECT_START_DATE is 'プロジェクト開始日付';
COMMENT ON column PUBLIC.PROJECT.PROJECT_END_DATE is 'プロジェクト終了日付';
COMMENT ON column PUBLIC.PROJECT.CLIENT_ID is '顧客ID';
COMMENT ON column PUBLIC.PROJECT.PROJECT_MANAGER is 'プロジェクトマネージャー';
COMMENT ON column PUBLIC.PROJECT.PROJECT_LEADER is 'プロジェクトリーダー';
COMMENT ON column PUBLIC.PROJECT.USER_ID is 'ユーザID';
COMMENT ON column PUBLIC.PROJECT.NOTE is '備考';
COMMENT ON column PUBLIC.PROJECT.SALES is '売上高';
COMMENT ON column PUBLIC.PROJECT.COST_OF_GOODS_SOLD is '売上原価';
COMMENT ON column PUBLIC.PROJECT.SGA is '販管費';
COMMENT ON column PUBLIC.PROJECT.ALLOCATION_OF_CORP_EXPENSES is '本社配賦';
COMMENT ON column PUBLIC.PROJECT.VERSION is 'バージョン番号';