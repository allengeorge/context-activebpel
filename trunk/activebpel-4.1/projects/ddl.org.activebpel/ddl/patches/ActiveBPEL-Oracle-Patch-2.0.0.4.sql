-- Upgrade script to take the DB from version 2.0.0.3 to 2.0.0.4

INSERT INTO AeCounter VALUES ('CoordinationPk', 0);
INSERT INTO AeCounter VALUES ('CoordinationId', 0);

-- Create the Coordination table
-- Note on AeCoordination.CoordinationRole column:
--   value must be one of the following:
--   SUBPROCESS_COORDINATOR  = 0;
--   SUBPROCESS_PARICIPANT   = 1;

CREATE TABLE AeCoordination
(
   CoordinationPk INT NOT NULL,
   EngineId INT NOT NULL,
   CoordinationType VARCHAR(255) NOT NULL,
   CoordinationRole INT NOT NULL,
   CoordinationId VARCHAR(255) NOT NULL,
   State VARCHAR(255) NOT NULL,
   ProcessId INT NOT NULL,
   LocationPath VARCHAR2(4000) NOT NULL,
   CoordinationDocument CLOB,
   StartDate TIMESTAMP,
   ModifiedDate TIMESTAMP,
   FOREIGN KEY (ProcessId) REFERENCES AeProcess(ProcessId) ON DELETE CASCADE,
   PRIMARY KEY (CoordinationPk)   
);

CREATE INDEX AeCoordByCoordinationId ON AeCoordination(CoordinationId);
CREATE INDEX AeCoordByState ON AeCoordination(State);

-- Update the DB version number.
UPDATE AeMetaInfo SET PropertyValue = '2.0.0.4' WHERE PropertyName = 'Version';

COMMIT;