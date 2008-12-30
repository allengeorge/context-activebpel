-- -----------------------------------------------------------------------------
-- SQL Patch script to upgrade the DB from version 2.1.0.3 to version 2.1.0.4
-- -----------------------------------------------------------------------------
USE ActiveBPEL;

-- ------------------------------------------------------------------------
-- AeProcessAttachment - Attachments accociated to processes
-- ------------------------------------------------------------------------
CREATE TABLE AeProcessAttachment (
   AttachmentGroupId BIGINT UNSIGNED NOT NULL,
   ProcessId BIGINT UNSIGNED,
   PRIMARY KEY (AttachmentGroupId),
   CONSTRAINT aeprocess_attachments FOREIGN Key (ProcessId) REFERENCES AeProcess (ProcessId) ON DELETE CASCADE
) TYPE = INNODB;

-- ------------------------------------------------------------------------
-- AeAttachment - Attachment Item Entries (Headers and Content)
-- ------------------------------------------------------------------------
CREATE TABLE AeAttachment (
   AttachmentGroupId BIGINT UNSIGNED NOT NULL,
   AttachmentItemId BIGINT UNSIGNED NOT NULL,
   AttachmentHeader LONGTEXT,
   AttachmentContent LONGBLOB NOT NULL,
   PRIMARY KEY (AttachmentItemId),
   CONSTRAINT attachment_items FOREIGN Key (AttachmentGroupId) REFERENCES AeProcessAttachment (AttachmentGroupId) ON DELETE CASCADE
) TYPE = INNODB;

-- --------------------------------------
-- Update the DB version number.
-- --------------------------------------
UPDATE AeMetaInfo SET PropertyValue = '2.1.0.4 ActiveBPEL Enterprise' WHERE PropertyName = 'Version';
