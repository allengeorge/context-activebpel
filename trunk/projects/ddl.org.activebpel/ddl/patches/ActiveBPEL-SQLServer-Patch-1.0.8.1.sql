USE ActiveBPEL;
GO

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
-- Rename Counter column.
EXEC sp_rename 'AeProcessLog.Counter', 'OldCounter', 'COLUMN'

-- Add new IDENTITY Counter column.
ALTER TABLE AeProcessLog ADD Counter INT IDENTITY

-- Drop current primary key (ProcessId, OldCounter).
BEGIN
   DECLARE @n NVARCHAR(255)
   SET @n = (SELECT CONSTRAINT_NAME FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS
					WHERE TABLE_NAME = 'AeProcessLog' AND CONSTRAINT_TYPE = 'PRIMARY KEY'   )
   PRINT 'Dropping primary key constraint ' + @n
   EXEC('ALTER TABLE AeProcessLog DROP CONSTRAINT ' + @n)
END

-- Create a new primary key on the new Counter column.
ALTER TABLE AeProcessLog ADD PRIMARY KEY (ProcessId, Counter)

-- Drop old Counter column
ALTER TABLE AeProcessLog DROP COLUMN OldCounter

-- --------------------------------------------------------------------------------------
-- 2. Delete counter entry for ProcessLog
-- --------------------------------------------------------------------------------------
DELETE FROM AeCounter WHERE CounterName = 'ProcessLog';

-- --------------------------------------------------------------------------------------
-- 3. Update the version property to 1.0.8.1
-- --------------------------------------------------------------------------------------
UPDATE AeMetaInfo SET PropertyValue = '1.0.8.1' WHERE PropertyName = 'Version';
