CONNECT TO ABPEL USER bpeluser;
-- The preceding line must be at the top of the file to work.

-- Upgrade script to take the DB from ActiveBPEL version 2.0.0.4 to 2.0.0.5

-- Alter the AeProcess table to add the ProcessStateReason column.
ALTER TABLE AeProcess ADD COLUMN ProcessStateReason INT;
UPDATE AeProcess SET ProcessStateReason = -1;

-- Update the DB version number.
UPDATE AeMetaInfo SET PropertyValue = '2.0.0.5' WHERE PropertyName = 'Version';