USE ActiveBPEL;
GO
-- Change coordination indexes to avoid deadlocks and improve performance
DROP INDEX AeCoordByCoordinationId ON AeCoordination;
DROP INDEX AeCoordByState ON AeCoordination;
CREATE INDEX AeCoordByCoordId ON AeCoordination(CoordinationId, ProcessId);
CREATE INDEX AeCoordByProcessId ON AeCoordination(ProcessId);

UPDATE AeMetaInfo SET PropertyValue = '2.1' WHERE PropertyName = 'Version'; 
GO
