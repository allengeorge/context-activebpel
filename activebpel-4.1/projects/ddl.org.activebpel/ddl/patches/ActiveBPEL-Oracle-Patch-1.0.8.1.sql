-- --------------------------------------------------------------------------------------
-- This patch does the following
--
-- 1. Create sequence for AeProcessLog.Counter
-- 2. Delete counter entry for ProcessLog
-- 3. Update the version property to 1.0.8.1
--
-- --------------------------------------------------------------------------------------

-- --------------------------------------------------------------------------------------
-- 1. Create sequence for AeProcessLog.Counter
-- --------------------------------------------------------------------------------------
DECLARE
	seq INT;
BEGIN
	SELECT NVL2(MAX(COUNTER), max(COUNTER), 0)+1 INTO seq FROM AeProcessLog;
	EXECUTE IMMEDIATE 'CREATE SEQUENCE AeProcessLogSequence START WITH ' || seq;
END;
/

-- --------------------------------------------------------------------------------------
-- 2. Delete counter entry for ProcessLog
-- --------------------------------------------------------------------------------------
DELETE FROM AeCounter WHERE CounterName = 'ProcessLog';

-- --------------------------------------------------------------------------------------
-- 3. Update the version property to 1.0.8.1
-- --------------------------------------------------------------------------------------
UPDATE AeMetaInfo SET PropertyValue = '1.0.8.1' WHERE PropertyName = 'Version';

COMMIT;