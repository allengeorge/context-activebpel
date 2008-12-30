-- Upgrade script to take the DB from ActiveBPEL version 2.0.0.3 to 2.0.0.4


USE ActiveBPEL;
GO

INSERT INTO AeCounter VALUES ('CoordinationPk', 0);
INSERT INTO AeCounter VALUES ('CoordinationId', 0);

-- Create the Coordination table

CREATE TABLE AeCoordination
(
   CoordinationPk BIGINT NOT NULL,
   EngineId INT NOT NULL,
   CoordinationType NVARCHAR(255) NOT NULL,
   CoordinationRole TINYINT NOT NULL,
   CoordinationId NVARCHAR(255) NOT NULL,
   State NVARCHAR(255) NOT NULL,
   ProcessId BIGINT NOT NULL,
   LocationPath NTEXT NOT NULL,
   CoordinationDocument NTEXT,
   StartDate DATETIME,
   ModifiedDate DATETIME,
   PRIMARY KEY (CoordinationPk),
   FOREIGN KEY (ProcessId) REFERENCES AeProcess(ProcessId) ON DELETE CASCADE
)

CREATE INDEX AeCoordByCoordinationId on AeCoordination(CoordinationId);
CREATE INDEX AeCoordByState on AeCoordination(State);


-- Update the DB version number.
UPDATE AeMetaInfo SET PropertyValue = '2.0.0.4' WHERE PropertyName = 'Version';