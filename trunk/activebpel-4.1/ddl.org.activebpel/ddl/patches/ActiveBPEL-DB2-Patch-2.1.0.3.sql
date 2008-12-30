CONNECT TO ABPEL USER bpeluser;
-- The preceding line must be at the top of the file to work.

-- -----------------------------------------------------------------------------
-- SQL Patch script to upgrade the DB from version 2.1.0.2 to version 2.1.0.3
-- -----------------------------------------------------------------------------


-- ------------------------------------
-- Add AeMetaInfo PK
-- ------------------------------------
ALTER TABLE AeMetaInfo ADD PRIMARY KEY(PropertyName);


-- ----------------------------------------------------
-- Remove AeCoordination.EngineId
-- ----------------------------------------------------
CREATE TABLE AeCoordination2 (
   CoordinationPk DECIMAL(19, 0) NOT NULL,
   CoordinationType VARGRAPHIC(255) NOT NULL,
   CoordinationRole SMALLINT NOT NULL,
   CoordinationId VARGRAPHIC(255) NOT NULL,
   State VARGRAPHIC(255) NOT NULL,
   ProcessId DECIMAL(19, 0) NOT NULL,
   LocationPath DBCLOB(512M) NOT NULL,
   CoordinationDocument DBCLOB(512M),
   StartDate TIMESTAMP,
   ModifiedDate TIMESTAMP,
   PRIMARY KEY (CoordinationPk)
);

INSERT INTO AeCoordination2
   SELECT CoordinationPk, CoordinationType, CoordinationRole, CoordinationId, State, ProcessId, LocationPath, CoordinationDocument, StartDate, ModifiedDate FROM AeCoordination;

DROP TABLE AeCoordination;
RENAME TABLE AeCoordination2 TO AeCoordination;

ALTER TABLE AeCoordination ADD FOREIGN KEY (ProcessId) REFERENCES AeProcess(ProcessId) ON DELETE CASCADE;

-- ---------------------------------------------------
-- Remove unused AeQueuedReceiveByMatchHash index to
-- match an optimization we did to Oracle earlier.
-- ---------------------------------------------------

DROP INDEX AeQMatchHash;

-- --------------------------------------------------------
-- Add QueuedReceivedId to AeQueuedReceiveByCorrelateHash
-- index in some databases to optimize execution plan.
-- --------------------------------------------------------

DROP INDEX AeQCorrelateHash;
CREATE INDEX AeQCorrelateHash ON AeQueuedReceive(MatchHash, CorrelateHash, QueuedReceiveId);

-- --------------------------------------
-- Update the DB version number.
-- --------------------------------------
UPDATE AeMetaInfo SET PropertyValue = '2.1.0.3 ActiveBPEL Enterprise' WHERE PropertyName = 'Version';
