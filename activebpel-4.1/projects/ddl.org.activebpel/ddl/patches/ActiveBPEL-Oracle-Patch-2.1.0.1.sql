DROP INDEX AeProcessByPlanId;
DROP INDEX AeProcessByName;
DROP INDEX AeProcessByState;
DROP INDEX AeProcessByStartDate;
DROP INDEX AeProcessByEndDate;

-- Add all columns for Active Processes query to StartDate index to optimize
-- Active Processes query.
CREATE INDEX AeProcessByStartDate on AeProcess(StartDate DESC, ProcessId DESC, ProcessName, ProcessNamespace, ProcessState, ProcessStateReason, EndDate);

UPDATE AeMetaInfo SET PropertyValue = '2.1.0.1' WHERE PropertyName = 'Version'; 

COMMIT;