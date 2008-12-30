-- Upgrade script to take the DB from ActiveBPEL version 2.0.0.3 to 2.0.0.4

USE ActiveBpel;

INSERT INTO AeCounter VALUES ('CoordinationPk', 0);
INSERT INTO AeCounter VALUES ('CoordinationId', 0);

CREATE TABLE AeCoordination
(
   CoordinationPk BIGINT UNSIGNED NOT NULL,
   EngineId INT UNSIGNED NOT NULL,
   CoordinationType VARCHAR(255) NOT NULL,
   CoordinationRole TINYINT UNSIGNED NOT NULL,
   CoordinationId VARCHAR(255) NOT NULL,
   State VARCHAR(255) NOT NULL,
   ProcessId BIGINT UNSIGNED NOT NULL,
   LocationPath TEXT NOT NULL,
   CoordinationDocument LONGTEXT,
   StartDate DATETIME,
   ModifiedDate DATETIME,
   PRIMARY KEY (CoordinationPk),
   FOREIGN KEY (ProcessId) REFERENCES AeProcess(ProcessId) ON DELETE CASCADE
) TYPE = INNODB;

CREATE INDEX AeCoordByCoordinationId on AeCoordination(CoordinationId);
CREATE INDEX AeCoordByState on AeCoordination(State);

-- Update the DB version number.
UPDATE AeMetaInfo SET PropertyValue = '2.0.0.4' WHERE PropertyName = 'Version';
