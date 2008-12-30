-- $Header: /Development/AEDevelopment/projects/ddl.org.activebpel/ddl/ActiveBPEL-Informix.sql,v 1.5 2007/05/24 21:14:46 KRoe Exp $
--
-- Creates tables in Informix for process and variable persistence.

-- NOTE: The assumption is that there is a clobspace named 'ae_clob_space'
--       Change the PUT statements if there are different clob spaces 
--
-- This script should be run to set up the database and tables prior to
-- running the Active BPEL engine for the first time with JDBC persistence
-- enabled.
--

-- Drop the database if it already exists.
CLOSE DATABASE;
DROP DATABASE ActiveBPEL;


-- Create the database with transaction support
CREATE DATABASE ActiveBPEL WITH LOG;

GRANT DBA TO 'bpeluser';
GRANT CONNECT TO public;

-- Create the Meta Information table
CREATE TABLE AeMetaInfo (
   PropertyName VARCHAR(255) NOT NULL,
   PropertyValue VARCHAR(255) NOT NULL,
   PRIMARY KEY (PropertyName)
) LOCK MODE ROW;

-- Version column must be kept in sync with the aeEngineConfig entry used to check the schema
INSERT INTO AeMetaInfo VALUES ('Version', '@DB_VERSION@');
INSERT INTO AeMetaInfo VALUES ('DatabaseType', 'informix');

-- Create the Counters table.
CREATE TABLE AeCounter (
   CounterName VARCHAR(255) NOT NULL,
   CounterValue INT8 NOT NULL,
   PRIMARY KEY (CounterName)
) LOCK MODE ROW;

-- Create the Process table
CREATE TABLE AeProcess
(
   ProcessId INT8 NOT NULL,
   PlanId INT NOT NULL,
   ProcessName VARCHAR(255) NOT NULL,
   ProcessNamespace VARCHAR(255) NOT NULL,
   ProcessDocument CLOB,
   ProcessState INT NOT NULL,
   ProcessStateReason INT,
   StartDate DATETIME YEAR TO SECOND,
   EndDate DATETIME YEAR TO SECOND,
   PendingInvokesCount INT,
   ModifiedDate DATETIME YEAR TO SECOND,
   ModifiedCount INT DEFAULT 0,
   PRIMARY KEY (ProcessId)
) PUT ProcessDocument IN (ae_clob_space) (LOG)
  LOCK MODE ROW;

-- Note on AeProcess.ProcessState
--   value must be one of the following from org.activebpel.rt.bpel.IAeBusinessProcess
--   PROCESS_LOADED     = 0;
--   PROCESS_RUNNING    = 1;
--   PROCESS_SUSPENDED  = 2;
--   PROCESS_COMPLETE   = 3;
--   PROCESS_FAULTED    = 4;

CREATE INDEX AeProcessByName on AeProcess(ProcessName);
CREATE INDEX AeProcessByState on AeProcess(ProcessState);
CREATE INDEX AeProcessByStartDate on AeProcess(StartDate);
CREATE INDEX AeProcessByEndDate on AeProcess(EndDate);
CREATE INDEX AeProcessByPendingInvokesCount on AeProcess(PendingInvokesCount);

-- Create the Process Log table
CREATE TABLE AeProcessLog
(
   ProcessId INT8 NOT NULL,
   ProcessLog CLOB,
   Counter SERIAL NOT NULL,
   LineCount INT NOT NULL,
   FOREIGN KEY (ProcessId) REFERENCES AeProcess(ProcessId) ON DELETE CASCADE,
   PRIMARY KEY (ProcessId, Counter)
) PUT ProcessLog IN (ae_clob_space) (LOG)
  LOCK MODE ROW;

-- Create the Variable table
CREATE TABLE AeVariable
(
   ProcessId INT8 NOT NULL,
   LocationId INT NOT NULL,
   VersionNumber INT NOT NULL,
   VariableDocument CLOB NOT NULL,
   FOREIGN KEY (ProcessId) REFERENCES AeProcess(ProcessId) ON DELETE CASCADE,
   PRIMARY KEY (ProcessId, LocationId, VersionNumber)
) PUT VariableDocument IN (ae_clob_space) (LOG)
  LOCK MODE ROW;

-- Create the Alarm table
CREATE TABLE AeAlarm (
   ProcessId INT8 NOT NULL,
   LocationPathId INT NOT NULL,
   Deadline DATETIME YEAR TO SECOND NOT NULL,
   DeadlineMillis INT8 NOT NULL,
   GroupId INT NOT NULL,
   AlarmId INT8 NOT NULL,
   FOREIGN KEY (ProcessId) REFERENCES AeProcess(ProcessId) ON DELETE CASCADE,
   PRIMARY KEY (ProcessId, LocationPathId, AlarmId)
) LOCK MODE ROW;

CREATE INDEX AeAlarmByGroup ON AeAlarm(ProcessId, GroupId);

-- Create the Receive Queue table
CREATE TABLE AeQueuedReceive (
   QueuedReceiveId INT NOT NULL,
   ProcessId INT8 NOT NULL,
   LocationPathId INT NOT NULL,
   Operation VARCHAR(255) NOT NULL,
   PartnerLinkName VARCHAR(255) NOT NULL,
   PortTypeNamespace VARCHAR(255) NOT NULL,
   PortTypeLocalPart VARCHAR(255) NOT NULL,
   CorrelationProperties CLOB NOT NULL,
   MatchHash INT NOT NULL,
   CorrelateHash INT NOT NULL,
   GroupId INT NOT NULL,
   PartnerLinkId INT NOT NULL,
   AllowsConcurrency SMALLINT NOT NULL,
   FOREIGN KEY (ProcessId) REFERENCES AeProcess(ProcessId) ON DELETE CASCADE,
   PRIMARY KEY (QueuedReceiveId)
) PUT CorrelationProperties IN (ae_clob_space) (LOG)
  LOCK MODE ROW;

CREATE INDEX AeQueuedReceiveByLocation ON AeQueuedReceive(ProcessId, LocationPathId);
CREATE INDEX AeQueuedReceiveByGroup ON AeQueuedReceive(ProcessId, GroupId);
CREATE INDEX AeQueuedReceiveByMatchHash ON AeQueuedReceive(ProcessId, MatchHash);
CREATE INDEX AeQueuedReceiveByCorrelateHash ON AeQueuedReceive(MatchHash, CorrelateHash);

-- Create the Process Journal table
CREATE TABLE AeProcessJournal (
   JournalId INT8 NOT NULL,
   ProcessId INT8 NOT NULL,
   Counter SERIAL8 NOT NULL,
   LocationId INT NOT NULL,
   EntryType SMALLINT NOT NULL,
   EntryDocument CLOB,
   FOREIGN KEY (ProcessId) REFERENCES AeProcess(ProcessId) ON DELETE CASCADE,
   PRIMARY KEY (JournalId)
) PUT EntryDocument IN (ae_clob_space) (LOG)
  LOCK MODE ROW;

CREATE TABLE AeURNValues (
   URN VARCHAR(255) NOT NULL,
   URL CLOB NOT NULL,
   PRIMARY KEY (URN)
) PUT URL IN (ae_clob_space) (LOG)
  LOCK MODE ROW;

-- Create the Coordination table
-- Note on AeCoordination.CoordinationRole column:
--   value must be one of the following:
--   SUBPROCESS_COORDINATOR  = 0;
--   SUBPROCESS_PARICIPANT   = 1;

CREATE TABLE AeCoordination
(
   CoordinationPk INT NOT NULL,
   CoordinationType VARCHAR(255) NOT NULL,
   CoordinationRole INT NOT NULL,
   CoordinationId VARCHAR(255) NOT NULL,
   State VARCHAR(255) NOT NULL,
   ProcessId INT8 NOT NULL,
   LocationPath LVARCHAR(4000) NOT NULL,
   CoordinationDocument CLOB,
   StartDate DATETIME YEAR TO SECOND,
   ModifiedDate DATETIME YEAR TO SECOND,
   FOREIGN KEY (ProcessId) REFERENCES AeProcess(ProcessId) ON DELETE CASCADE,
   PRIMARY KEY (CoordinationPk)
) PUT CoordinationDocument IN (ae_clob_space) (LOG)
  LOCK MODE ROW;

CREATE INDEX AeCoordByCoordId ON AeCoordination(CoordinationId, ProcessId);

-- -----------------------------------------------------------------------------
-- TransmissionTracker table - stores transmission id and data needed for durable invokes and durable reply.
-- -----------------------------------------------------------------------------
CREATE TABLE AeTransmissionTracker
(
   TransmissionId INT8 NOT NULL,
   State INT NOT NULL,
   MessageId  VARCHAR(255),
   PRIMARY KEY (TransmissionId)
) LOCK MODE ROW;

-- ------------------------------------------------------------------------
-- AeProcessAttachment - Attachments accociated to processes
-- ------------------------------------------------------------------------
CREATE TABLE AeProcessAttachment (
   AttachmentGroupId INT8 NOT NULL,
   ProcessId INT8,
   PRIMARY KEY (AttachmentGroupId),
   FOREIGN Key (ProcessId) REFERENCES AeProcess (ProcessId) ON DELETE CASCADE
) LOCK MODE ROW;

-- ------------------------------------------------------------------------
-- AeAttachment - Attachment Item Entries (Headers and Content)
-- ------------------------------------------------------------------------
CREATE TABLE AeAttachment (
   AttachmentGroupId INT8 NOT NULL,
   AttachmentItemId INT8 NOT NULL,
   AttachmentHeader CLOB,
   AttachmentContent BLOB NOT NULL,
   PRIMARY KEY (AttachmentItemId),
   FOREIGN Key (AttachmentGroupId) REFERENCES AeProcessAttachment (AttachmentGroupId) ON DELETE CASCADE
) PUT AttachmentHeader IN (ae_clob_space), 
      AttachmentContent IN (ae_clob_space) (LOG)
  LOCK MODE ROW;
