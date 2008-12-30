-- Change coordination indexes to avoid deadlocks and improve performance
DROP INDEX AeCoordByCoordinationId;
DROP INDEX AeCoordByState;
CREATE INDEX AeCoordByCoordId ON AeCoordination(CoordinationId, ProcessId);
CREATE INDEX AeCoordByProcessId ON AeCoordination(ProcessId);

UPDATE AeMetaInfo SET PropertyValue = '2.1' WHERE PropertyName = 'Version'; 

COMMIT;