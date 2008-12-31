// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.xmldb/src/org/activebpel/rt/bpel/server/engine/storage/xmldb/process/IAeProcessConfigKeys.java,v 1.1 2007/08/17 00:40:5
/*
 * Copyright (c) 2004-2006 Active Endpoints, Inc.
 *
 * This program is licensed under the terms of the GNU General Public License
 * Version 2 (the "License") as published by the Free Software Foundation, and 
 * the ActiveBPEL Licensing Policies (the "Policies").  A copy of the License 
 * and the Policies were distributed with this program.  
 *
 * The License is available at:
 * http: *www.gnu.org/copyleft/gpl.html
 *
 * The Policies are available at:
 * http: *www.activebpel.org/licensing/index.html
 *
 * Unless required by applicable law or agreed to in writing, this program is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied.  See the License and the Policies
 * for specific language governing the use of this program.
 */
package org.activebpel.rt.bpel.server.engine.storage.xmldb.process;

/**
 * An interface that simply holds some static constants which are the names of keys in the XMLDB config
 * object.
 */
public interface IAeProcessConfigKeys
{
   /* Used in the process state storage. */
   public static final String INSERT_PROCESS               = "InsertProcess"; //$NON-NLS-1$
   public static final String GET_PROCESS_INSTANCE_DETAIL  = "GetProcessInstanceDetail"; //$NON-NLS-1$
   public static final String GET_PROCESS_NAME             = "GetProcessName"; //$NON-NLS-1$
   public static final String GET_RECOVERY_PROCESS_IDS     = "GetRecoveryProcessIds"; //$NON-NLS-1$
   public static final String GET_PROCESS_LIST             = "GetProcessList"; //$NON-NLS-1$
   public static final String GET_PROCESS_IDS              = "GetProcessIds"; //$NON-NLS-1$
   public static final String DELETE_PROCESS               = "DeleteProcess"; //$NON-NLS-1$
   public static final String DELETE_JOURNAL_ENTRIES       = "DeleteJournalEntries"; //$NON-NLS-1$
   public static final String GET_MAX_PROCESSID            = "GetMaxProcessID"; //$NON-NLS-1$
   public static final String GET_MIN_PROCESSID            = "GetMinProcessID"; //$NON-NLS-1$
   public static final String GET_JOURNAL_ENTRIES          = "GetJournalEntries"; //$NON-NLS-1$
   public static final String GET_JOURNAL_ENTRY            = "GetJournalEntry"; //$NON-NLS-1$
   public static final String GET_JOURNAL_ENTRIES_LOCATION_IDS = "GetJournalEntriesLocationIds"; //$NON-NLS-1$

   /* Used in the process state connection. */
   public static final String INSERT_PROCESS_LOG           = "InsertProcessLog"; //$NON-NLS-1$
   public static final String INSERT_VARIABLE              = "InsertVariable"; //$NON-NLS-1$
   public static final String GET_PROCESS_DOCUMENT         = "GetProcessDocument"; //$NON-NLS-1$
   public static final String GET_PROCESS_VARIABLES        = "GetProcessVariables"; //$NON-NLS-1$
   public static final String GET_VARIABLE_DOCUMENT        = "GetVariableDocument"; //$NON-NLS-1$
   public static final String DELETE_VARIABLE              = "DeleteVariable"; //$NON-NLS-1$
   public static final String UPDATE_PROCESS               = "UpdateProcess"; //$NON-NLS-1$
   public static final String UPDATE_PROCESS_ENDDATE       = "UpdateProcessEndDate"; //$NON-NLS-1$

   /* Used for accessing process log related statements. */
   public static final String GET_HEAD           = "GetLogHead"; //$NON-NLS-1$
   public static final String GET_TAIL           = "GetLogTail"; //$NON-NLS-1$
   public static final String GET_LOG_ENTRIES    = "GetLogEntries"; //$NON-NLS-1$
   public static final String GET_SMALL_LOG      = "GetSmallLog"; //$NON-NLS-1$
   public static final String GET_LOG_IDS        = "GetLogIDs"; //$NON-NLS-1$
   public static final String GET_LOG            = "GetLog"; //$NON-NLS-1$
}
