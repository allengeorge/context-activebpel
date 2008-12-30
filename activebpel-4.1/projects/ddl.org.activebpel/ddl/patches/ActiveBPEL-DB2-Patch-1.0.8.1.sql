CONNECT TO ABPEL USER bpeluser;
-- The preceding line must be at the top of the file to work.

-- --------------------------------------------------------------------------------------
-- This patch does the following
--
-- 1. Change AeProcessLog.Counter to be AUTO-INCREMENT
-- 2. Delete counter entry for ProcessLog
-- 3. Update the version property to 1.0.8.1
--
-- --------------------------------------------------------------------------------------

-- --------------------------------------------------------------------------------------
-- 1. Change AeProcessLog.Counter to be AUTO-INCREMENT
-- --------------------------------------------------------------------------------------
CREATE TABLE AeProcessLog2
(
   ProcessId INT NOT NULL,
   ProcessLog DBCLOB(512M),
   Counter INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
   LineCount INT NOT NULL,
   PRIMARY KEY (ProcessId, Counter)
);
INSERT INTO AeProcessLog2 (ProcessId, ProcessLog, LineCount) SELECT ProcessId, ProcessLog, LineCount FROM AeProcessLog ORDER BY ProcessId, Counter ASC;
DROP TABLE AeProcessLog;
RENAME TABLE AeProcessLog2 TO AeProcessLog;
ALTER TABLE AeProcessLog ADD FOREIGN KEY (ProcessId) REFERENCES AeProcess(ProcessId) ON DELETE CASCADE;

-- --------------------------------------------------------------------------------------
-- 2. Delete counter entry for ProcessLog
-- --------------------------------------------------------------------------------------
DELETE FROM AeCounter WHERE CounterName = 'ProcessLog';

-- --------------------------------------------------------------------------------------
-- 3. Update the version property to 1.0.8.1
-- --------------------------------------------------------------------------------------
UPDATE AeMetaInfo SET PropertyValue = '1.0.8.1' WHERE PropertyName = 'Version';

