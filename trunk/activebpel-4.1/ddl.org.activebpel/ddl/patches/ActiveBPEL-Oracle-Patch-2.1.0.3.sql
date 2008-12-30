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
ALTER TABLE AeCoordination DROP COLUMN EngineId;


-- --------------------------------------
-- Update the DB version number.
-- --------------------------------------
UPDATE AeMetaInfo SET PropertyValue = '2.1.0.3 ActiveBPEL Enterprise' WHERE PropertyName = 'Version';
COMMIT;
