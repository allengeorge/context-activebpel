CONNECT TO ABPEL USER bpeluser;
-- The preceding line must be at the top of the file to work.

-- Upgrade script to take the DB from version 1.0.8.3 to 2.0.0.1

-- add the urn mapping table
CREATE TABLE AeURNValues (
   URN VARGRAPHIC(255) NOT NULL,
   URL DBCLOB(512M) NOT NULL,
   DeploymentGroup INT DEFAULT 0 NOT NULL,
   PRIMARY KEY (URN, DeploymentGroup)
);

-- Update the DB version number.
UPDATE AeMetaInfo SET PropertyValue = '2.0.0.1' WHERE PropertyName = 'Version';
