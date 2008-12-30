-- -----------------------------------------------------------------------------
-- SQL Patch script to upgrade the DB from version 2.1.0.2 to version 2.1.0.3
-- -----------------------------------------------------------------------------
USE ActiveBPEL;
GO


-- ------------------------------------
-- Add AeMetaInfo PK
-- ------------------------------------
ALTER TABLE AeMetaInfo ADD PRIMARY KEY(PropertyName);
GO


-- ----------------------------------------------------
-- Remove AeCoordination.EngineId
-- ----------------------------------------------------
ALTER TABLE AeCoordination DROP COLUMN EngineId;
GO


-- ---------------------------------------------------
-- Remove unused AeQueuedReceiveByMatchHash index to
-- match an optimization we did to Oracle earlier.
-- ---------------------------------------------------

DROP INDEX AeQueuedReceiveByMatchHash ON AeQueuedReceive;

-- --------------------------------------
-- Update the DB version number.
-- --------------------------------------
UPDATE AeMetaInfo SET PropertyValue = '2.1.0.3 ActiveBPEL Enterprise' WHERE PropertyName = 'Version';
GO
