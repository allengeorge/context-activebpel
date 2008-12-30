CONNECT TO ABPEL USER bpeluser;
-- The preceding line must be at the top of the file to work.
-- Upgrade script to take the DB from ActiveBPEL version 2.0.0.5 to 2.0.0.6 for 2.1 release

-- -----------------------------------------------------------------------------
-- TransmissionTracker table - stores transmission id and data needed for durable invokes and durable reply.
-- -----------------------------------------------------------------------------
CREATE TABLE AeTransmissionTracker
(
   TransmissionId DECIMAL(19, 0) NOT NULL,
   State INT NOT NULL,
   MessageId VARGRAPHIC(255),
   PRIMARY KEY (TransmissionId)
) ;

-- Update the DB version number.
UPDATE AeMetaInfo SET PropertyValue = '2.0.0.6' WHERE PropertyName = 'Version';