-- Upgrade script to take the DB from ActiveBPEL version 2.0.0.4 to 2.0.0.5

USE ActiveBpel;

-- Alter the AeProcess table to add the ProcessStateReason column.
ALTER TABLE AeProcess ADD ProcessStateReason INT;
GO

UPDATE AeProcess SET ProcessStateReason = -1;

-- Update the DB version number.
UPDATE AeMetaInfo SET PropertyValue = '2.0.0.5' WHERE PropertyName = 'Version';