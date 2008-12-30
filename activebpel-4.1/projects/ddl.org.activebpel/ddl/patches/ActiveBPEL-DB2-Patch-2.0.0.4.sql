CONNECT TO ABPEL USER bpeluser;
-- The preceding line must be at the top of the file to work.

-- Upgrade script to take the DB from version 2.0.0.3 to 2.0.0.4

INSERT INTO AeCounter VALUES ('CoordinationPk', 0);
INSERT INTO AeCounter VALUES ('CoordinationId', 0);

CREATE TABLE AeCoordination
(
   CoordinationPk DECIMAL(19, 0) NOT NULL,
   EngineId INT NOT NULL,
   CoordinationType VARGRAPHIC(255) NOT NULL,
   CoordinationRole SMALLINT NOT NULL,
   CoordinationId  VARGRAPHIC(255) NOT NULL,
   State VARGRAPHIC(255) NOT NULL,
   ProcessId DECIMAL(19, 0) NOT NULL,
   LocationPath  DBCLOB(512M) NOT NULL,
   CoordinationDocument DBCLOB(512M),
   StartDate TIMESTAMP,
   ModifiedDate TIMESTAMP,
   FOREIGN KEY (ProcessId) REFERENCES AeProcess(ProcessId) ON DELETE CASCADE.   
   PRIMARY KEY (CoordinationPk)
) ;

CREATE INDEX AeCoordByCoordId ON AeCoordination(CoordinationId);
CREATE INDEX AeCoordByState ON AeCoordination(State);

-- Update the DB version number.
UPDATE AeMetaInfo SET PropertyValue = '2.0.0.4' WHERE PropertyName = 'Version';