-- --------------------------------------------------------------------------------------
-- SQL Patch script to upgrade the DB from version 1.0.8.2 to version 1.0.8.3.
-- --------------------------------------------------------------------------------------

-- ------------------------------------------
-- Convert AeReceivedItem to AeProcessJournal
-- ------------------------------------------
-- Create the Process Journal table
CREATE TABLE AeProcessJournal (
   JournalId INT NOT NULL,
   ProcessId INT NOT NULL,
   Counter INT NOT NULL,
   LocationId INT NOT NULL,
   EntryType NUMBER(1) NOT NULL,
   EntryDocument CLOB,
   FOREIGN KEY (ProcessId) REFERENCES AeProcess(ProcessId) ON DELETE CASCADE,
   PRIMARY KEY (JournalId)
);

CREATE SEQUENCE AeProcessJournalSequence;

INSERT INTO AeProcessJournal(JournalId, ProcessId, LocationId, EntryType, EntryDocument)
SELECT
   ReceivedItemId,
   ProcessId,
   AeProcessJournalSequence.nextval,
   LocationId,
   CASE WHEN MessageDocument IS NULL THEN 1 ELSE 2 END,
   MessageDocument
FROM
   AeReceivedItem;

DROP TABLE AeReceivedItem;

-- --------------------------------
-- Add GroupId to the AeAlarm table
-- --------------------------------

-- Alter the alarm table to add the GroupId column.
ALTER TABLE AeAlarm ADD GroupId INT;

-- Now set all the group id values to -1
UPDATE AeAlarm SET GroupId = -1 WHERE GroupId IS NULL;

-- Now alter the group id column to be 'not null'
ALTER TABLE AeAlarm MODIFY GroupId INT NOT NULL;
CREATE INDEX AeAlarmByGroup ON AeAlarm(ProcessId, GroupId);

-- ----------------------------------------
-- Add GroupId to the AeQueuedReceive table
-- ----------------------------------------

-- Alter the queued receive table to add the GroupId column.
ALTER TABLE AeQueuedReceive ADD GroupId INT;

-- Now set all the group id values to -1
UPDATE AeQueuedReceive SET GroupId = -1 WHERE GroupId IS NULL;

-- Now alter the group id column to be 'not null'
ALTER TABLE AeQueuedReceive MODIFY GroupId INT NOT NULL;
CREATE INDEX AeQueuedReceiveByGroup ON AeQueuedReceive(ProcessId, GroupId);

-- ------------------------------------------------------------------------------
-- Add an upgrader to the meta info table that will be run to fix up hash values.
-- ------------------------------------------------------------------------------
INSERT INTO AeMetaInfo(PropertyName, PropertyValue) VALUES ('PATCH_1.0.8.3_QUEUE', 'org.activebpel.ddl.storage.sql.upgrade.AeSQLUpgrader1_0_8_3_QueuedReceiveTable');


-- Update the DB version number.
UPDATE AeMetaInfo SET PropertyValue = '1.0.8.3' WHERE PropertyName = 'Version';

COMMIT;