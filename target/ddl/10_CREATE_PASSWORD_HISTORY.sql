CREATE TABLE PUBLIC.PASSWORD_HISTORY (
  PASSWORD_HISTORY_ID BIGINT NOT NULL,
  USER_ID INTEGER,
  USER_PASSWORD VARCHAR(44) NOT NULL,
  PRIMARY KEY (PASSWORD_HISTORY_ID)
);
COMMENT ON table PUBLIC.PASSWORD_HISTORY is 'パスワード変更履歴';
COMMENT ON column PUBLIC.PASSWORD_HISTORY.PASSWORD_HISTORY_ID is 'パスワード履歴';
COMMENT ON column PUBLIC.PASSWORD_HISTORY.USER_ID is 'ユーザID';
COMMENT ON column PUBLIC.PASSWORD_HISTORY.USER_PASSWORD is 'パスワード';