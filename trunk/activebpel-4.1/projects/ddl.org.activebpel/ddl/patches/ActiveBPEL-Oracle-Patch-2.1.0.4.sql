-- -----------------------------------------------------------------------------
-- SQL Patch script to upgrade the DB from version 2.1.0.3 to version 2.1.0.4
-- -----------------------------------------------------------------------------

-- ------------------------------------------------------------------------
-- AeProcessAttachment - Attachments accociated to processes
-- ------------------------------------------------------------------------
CREATE TABLE AeProcessAttachment (
   AttachmentGroupId INT NOT NULL,
   ProcessId INT,
   PRIMARY KEY (AttachmentGroupId),
   FOREIGN Key (ProcessId) REFERENCES AeProcess (ProcessId) ON DELETE CASCADE
);

-- ------------------------------------------------------------------------
-- AeAttachment - Attachment Item Entries (Headers and Content)
-- ------------------------------------------------------------------------
CREATE TABLE AeAttachment (
   AttachmentGroupId INT NOT NULL,
   AttachmentItemId INT NOT NULL,
   AttachmentHeader CLOB,
   AttachmentContent BLOB,
   PRIMARY KEY (AttachmentItemId),
   FOREIGN Key (AttachmentGroupId) REFERENCES AeProcessAttachment (AttachmentGroupId) ON DELETE CASCADE
);

-- --------------------------------------
-- Update the DB version number.
-- --------------------------------------
UPDATE AeMetaInfo SET PropertyValue = '2.1.0.4 ActiveBPEL Enterprise' WHERE PropertyName = 'Version';
COMMIT;
