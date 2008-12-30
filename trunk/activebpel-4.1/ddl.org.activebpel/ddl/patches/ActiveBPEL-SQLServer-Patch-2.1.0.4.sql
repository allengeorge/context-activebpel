-- -----------------------------------------------------------------------------
-- SQL Patch script to upgrade the DB from version 2.1.0.3 to version 2.1.0.4
-- -----------------------------------------------------------------------------
USE ActiveBPEL;
GO

-- ------------------------------------------------------------------------
-- AeProcessAttachment - Attachments accociated to processes
-- ------------------------------------------------------------------------
CREATE TABLE AeProcessAttachment (
   AttachmentGroupId BIGINT NOT NULL,
   ProcessId BIGINT,
   PRIMARY KEY (AttachmentGroupId),
   FOREIGN Key (ProcessId) REFERENCES AeProcess (ProcessId) ON DELETE CASCADE
);

-- ------------------------------------------------------------------------
-- AeAttachment - Attachment Item Entries (Headers and Content)
-- ------------------------------------------------------------------------
CREATE TABLE AeAttachment (
   AttachmentGroupId BIGINT NOT NULL,
   AttachmentItemId BIGINT NOT NULL,
   AttachmentHeader NTEXT,
   AttachmentContent IMAGE NOT NULL,
   PRIMARY KEY (AttachmentItemId),
   FOREIGN Key (AttachmentGroupId) REFERENCES AeProcessAttachment (AttachmentGroupId) ON DELETE CASCADE
);

-- --------------------------------------
-- Update the DB version number.
-- --------------------------------------
UPDATE AeMetaInfo SET PropertyValue = '2.1.0.4 ActiveBPEL Enterprise' WHERE PropertyName = 'Version';
GO
