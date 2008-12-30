-- -----------------------------------------------------------------------------
-- SQL Patch script to upgrade the DB from version 2.1.0.2 to version 2.1.0.3
-- -----------------------------------------------------------------------------
USE ActiveBPEL;


-- ------------------------------------
-- Add AeMetaInfo PK
-- ------------------------------------
ALTER TABLE AeMetaInfo ADD PRIMARY KEY(PropertyName);


-- ----------------------------------------------------
-- Remove AeCoordination.EngineId
-- ----------------------------------------------------
ALTER TABLE AeCoordination DROP COLUMN EngineId;


-- ---------------------------------------------------
-- Remove unused AeQueuedReceiveByMatchHash index to
-- match an optimization we did to Oracle earlier.
-- ---------------------------------------------------

DROP INDEX AeQueuedReceiveByMatchHash ON AeQueuedReceive;

-- --------------------------------------------------------
-- Add QueuedReceivedId to AeQueuedReceiveByCorrelateHash
-- index in some databases to optimize execution plan.
-- --------------------------------------------------------

DROP INDEX AeQueuedReceiveByCorrelateHash on AeQueuedReceive;
CREATE INDEX AeQueuedReceiveByCorrelateHash ON AeQueuedReceive(MatchHash, CorrelateHash, QueuedReceiveId);

-- --------------------------------------
-- Update the DB version number.
-- --------------------------------------
UPDATE AeMetaInfo SET PropertyValue = '2.1.0.3 ActiveBPEL Enterprise' WHERE PropertyName = 'Version';
