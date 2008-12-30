CONNECT TO ABPEL USER bpeluser;
-- The preceding line must be at the top of the file to work.

-- -----------------------------------------------------------------------------
-- SQL Patch script to upgrade the DB from version 2.1.0.3 to version 2.1.0.4
-- -----------------------------------------------------------------------------

-- ------------------------------------------------------------------------
-- AeProcessAttachment - Attachments accociated to processes
-- ------------------------------------------------------------------------
CREATE TABLE AeProcessAttachment (
   AttachmentGroupId DECIMAL(19, 0) NOT NULL,
   ProcessId DECIMAL(19, 0),
   PRIMARY KEY (AttachmentGroupId),
   FOREIGN Key (ProcessId) REFERENCES AeProcess (ProcessId) ON DELETE CASCADE
);

-- ------------------------------------------------------------------------
-- AeAttachment - Attachment Item Entries (Headers and Content)
-- ------------------------------------------------------------------------
CREATE TABLE AeAttachment (
   AttachmentGroupId DECIMAL(19, 0) NOT NULL,
   AttachmentItemId DECIMAL(19, 0) NOT NULL,
   AttachmentHeader DBCLOB(512M),
   AttachmentContent BLOB(1G) NOT NULL,
   PRIMARY KEY (AttachmentItemId),
   FOREIGN Key (AttachmentGroupId) REFERENCES AeProcessAttachment (AttachmentGroupId) ON DELETE CASCADE
);

-- --------------------------------------
-- Update the DB version number.
-- --------------------------------------
UPDATE AeMetaInfo SET PropertyValue = '2.1.0.4 ActiveBPEL Enterprise' WHERE PropertyName = 'Version';
