ALTER TABLE PUBLIC.SYSTEM_ACCOUNT ADD CONSTRAINT UQ_SYSTEM_ACCOUNT_LOGIN_ID UNIQUE
(
  LOGIN_ID
);