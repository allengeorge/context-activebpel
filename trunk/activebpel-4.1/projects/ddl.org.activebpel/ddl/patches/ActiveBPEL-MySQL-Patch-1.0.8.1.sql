USE ActiveBpel;

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
ALTER TABLE AeProcessLog CHANGE COLUMN Counter Counter INT UNSIGNED NOT NULL AUTO_INCREMENT, ADD  KEY (Counter);

-- --------------------------------------------------------------------------------------
-- 2. Delete counter entry for ProcessLog
-- --------------------------------------------------------------------------------------
DELETE FROM AeCounter WHERE CounterName = 'ProcessLog';

-- --------------------------------------------------------------------------------------
-- 3. Update the version property to 1.0.8.1
-- --------------------------------------------------------------------------------------
UPDATE AeMetaInfo SET PropertyValue = '1.0.8.1' WHERE PropertyName = 'Version';

