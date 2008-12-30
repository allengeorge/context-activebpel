
USE ActiveBpel;

-- Alter the alarm table to add the DeadlineMillis column.
ALTER TABLE AeAlarm ADD COLUMN DeadlineMillis BIGINT;

-- Now set all the deadline millis values to 0
UPDATE AeAlarm SET DeadlineMillis = 0 WHERE DeadlineMillis IS NULL;

-- Now alter the millis column to be 'not null'
ALTER TABLE AeAlarm MODIFY COLUMN DeadlineMillis BIGINT NOT NULL;

-- Add an upgrader to the meta info table that will be run to migrate the deadline data.
INSERT INTO AeMetaInfo(PropertyName, PropertyValue) VALUES ('PATCH_1.0.8.2_ALARM', 'org.activebpel.ddl.storage.sql.upgrade.AeSQLUpgrader1_0_8_2_AlarmTable');

-- Update the DB version number.
UPDATE AeMetaInfo SET PropertyValue = '1.0.8.2' WHERE PropertyName = 'Version';
