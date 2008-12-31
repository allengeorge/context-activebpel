// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/sql/IAeProcessSQLKeys.java,v 1.10 2007/01/26 15:05:5
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
package org.activebpel.rt.bpel.server.engine.storage.sql;

/**
 * Constants for the Process storage SQL keys (keys into the {@link
 * org.activebpel.rt.bpel.server.engine.storage.sql.AeSQLConfig} object).
 */
public interface IAeProcessSQLKeys
{
   public static final String DELETE_JOURNAL_ENTRIES       = "DeleteJournalEntries"; //$NON-NLS-1$
   public static final String DELETE_JOURNAL_ENTRY         = "DeleteJournalEntry"; //$NON-NLS-1$
   public static final String DELETE_PROCESS               = "DeleteProcess"; //$NON-NLS-1$
   public static final String DELETE_PROCESS_LOG           = "DeleteProcessLog"; //$NON-NLS-1$
   public static final String DELETE_PROCESS_VARIABLES     = "DeleteProcessVariables"; //$NON-NLS-1$
   public static final String DELETE_PROCESSES             = "DeleteProcesses"; //$NON-NLS-1$
   public static final String DELETE_VARIABLE              = "DeleteVariable"; //$NON-NLS-1$
   public static final String GET_JOURNAL_ENTRIES          = "GetJournalEntries"; //$NON-NLS-1$
   public static final String GET_JOURNAL_ENTRIES_LOCATION_IDS = "GetJournalEntriesLocationIds"; //$NON-NLS-1$
   public static final String GET_JOURNAL_ENTRY            = "GetJournalEntry"; //$NON-NLS-1$
   public static final String GET_PROCESS_DOCUMENT         = "GetProcessDocument"; //$NON-NLS-1$
   public static final String GET_PROCESS_INSTANCE_DETAIL  = "GetProcessInstanceDetail"; //$NON-NLS-1$
   public static final String GET_PROCESS_NAME             = "GetProcessName"; //$NON-NLS-1$
   public static final String GET_PROCESS_LIST             = "GetProcessList"; //$NON-NLS-1$
   public static final String GET_PROCESS_IDS              = "GetProcessIds"; //$NON-NLS-1$
   public static final String GET_PROCESS_COUNT            = "GetProcessCount"; //$NON-NLS-1$
   public static final String GET_PROCESS_LIST_WHERE       = "GetProcessWhereClause";  //$NON-NLS-1$
   public static final String GET_PROCESS_VARIABLES        = "GetProcessVariables"; //$NON-NLS-1$
   public static final String GET_RECOVERY_PROCESS_IDS     = "GetRecoveryProcessIds"; //$NON-NLS-1$
   public static final String GET_VARIABLE_DOCUMENT        = "GetVariableDocument"; //$NON-NLS-1$
   public static final String INSERT_PROCESS               = "InsertProcess"; //$NON-NLS-1$
   public static final String INSERT_PROCESS_LOG           = "InsertProcessLog"; //$NON-NLS-1$
   public static final String INSERT_VARIABLE              = "InsertVariable"; //$NON-NLS-1$
   public static final String UPDATE_PROCESS               = "UpdateProcess"; //$NON-NLS-1$
}
