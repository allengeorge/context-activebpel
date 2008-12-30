-- Upgrade script to take the DB from version 1.0.8.3 to 2.0.0.1

USE ActiveBPEL;
GO

-- add the urn mapping table
CREATE TABLE AeURNValues (
   URN NVARCHAR(255) NOT NULL,
   URL NTEXT NOT NULL,
   PRIMARY KEY (URN)
);

-- Update the DB version number.
UPDATE AeMetaInfo SET PropertyValue = '2.0.0.1' WHERE PropertyName = 'Version';
