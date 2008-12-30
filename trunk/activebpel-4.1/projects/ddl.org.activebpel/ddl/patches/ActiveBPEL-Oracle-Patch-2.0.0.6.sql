-- Upgrade script to take the DB from ActiveBPEL version 2.0.0.5 to 2.0.0.6 (ver 2.1)

-- -----------------------------------------------------------------------------
-- TransmissionTracker table - stores transmission id and data needed for durable invokes and durable reply.
-- -----------------------------------------------------------------------------
CREATE TABLE AeTransmissionTracker
(
   TransmissionId INT NOT NULL,
   State INT NOT NULL,
   MessageId  VARCHAR(255),
   PRIMARY KEY (TransmissionId)
);

-- Update the DB version number.
UPDATE AeMetaInfo SET PropertyValue = '2.0.0.6' WHERE PropertyName = 'Version';

COMMIT;