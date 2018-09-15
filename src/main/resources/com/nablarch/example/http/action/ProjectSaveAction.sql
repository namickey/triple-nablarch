SELECT_MAX_PROJECT_ID=
SELECT
  MAX(PROJECT_ID) AS PROJECT_ID
FROM
  PROJECT


INSERT_PROJECT=
INSERT INTO
  PROJECT
VALUES(
  :projectId,
  :projectName,
  :projectType,
  :projectClass,
  :projectStartDate,
  :projectEndDate,
  :clientId,
  :projectManager,
  :projectLeader,
  :userId,
  :note,
  :sales,
  :costOfGoodsSold,
  :sga,
  :allocationOfCorpExpenses,
  :version
)

