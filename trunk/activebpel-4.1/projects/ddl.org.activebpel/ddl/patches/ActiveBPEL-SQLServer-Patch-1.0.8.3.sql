-- --------------------------------------------------------------------------------------
-- SQL Patch script to upgrade the DB from version 1.0.8.2 to version 1.0.8.3.
-- --------------------------------------------------------------------------------------

USE ActiveBPEL;
GO

-- ------------------------------------------
-- Convert AeReceivedItem to AeProcessJournal
-- ------------------------------------------
CREATE TABLE AeProcessJournal (
   JournalId BIGINT NOT NULL,
   ProcessId BIGINT NOT NULL,
   Counter BIGINT IDENTITY NOT NULL,
   LocationId INT NOT NULL,
   EntryType TINYINT NOT NULL,
   EntryDocument NTEXT,
   FOREIGN KEY (ProcessId) REFERENCES AeProcess(ProcessId) ON DELETE CASCADE,
   PRIMARY KEY (JournalId)
);

INSERT INTO AeProcessJournal(JournalId, ProcessId, LocationId, EntryType, EntryDocument)
SELECT
   ReceivedItemId,
   ProcessId,
   LocationId,
   CASE WHEN MessageDocument IS NULL THEN 1 ELSE 2 END,
   MessageDocument
FROM
   AeReceivedItem;

DROP TABLE AeReceivedItem;
GO

-- --------------------------------
-- Add GroupId to the AeAlarm table
-- --------------------------------

-- Alter the alarm table to add the GroupId column.
ALTER TABLE AeAlarm ADD GroupId BIGINT;
GO

-- Now set all the group id values to -1
UPDATE AeAlarm SET GroupId = -1 WHERE GroupId IS NULL;

-- Now alter the group id column to be 'not null'
ALTER TABLE AeAlarm ALTER COLUMN GroupId BIGINT NOT NULL;
CREATE INDEX AeAlarmByGroup ON AeAlarm(ProcessId, GroupId);

-- ----------------------------------------
-- Add GroupId to the AeQueuedReceive table
-- ----------------------------------------

-- Alter the queued receive table to add the GroupId column.
ALTER TABLE AeQueuedReceive ADD GroupId BIGINT;
GO

-- Now set all the group id values to -1
UPDATE AeQueuedReceive SET GroupId = -1 WHERE GroupId IS NULL;

-- Now alter the group id column to be 'not null'
ALTER TABLE AeQueuedReceive ALTER COLUMN GroupId BIGINT NOT NULL;
CREATE INDEX AeQueuedReceiveByGroup ON AeQueuedReceive(ProcessId, GroupId);

-- ------------------------------------------------------------------------------
-- Add an upgrader to the meta info table that will be run to fix up hash values.
-- ------------------------------------------------------------------------------
INSERT INTO AeMetaInfo(PropertyName, PropertyValue) VALUES ('PATCH_1.0.8.3_QUEUE', 'org.activebpel.ddl.storage.sql.upgrade.AeSQLUpgrader1_0_8_3_QueuedReceiveTable');

-- Update the DB version number.
UPDATE AeMetaInfo SET PropertyValue = '1.0.8.3' WHERE PropertyName = 'Version';
