//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.xmldb/src/org/activebpel/rt/bpel/server/engine/storage/xmldb/process/IAeProcessElements.java,v 1.1 2007/08/17 00:40:5
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
 * Constants that define the Process element names.
 */
public interface IAeProcessElements
{
   /* AeProcess doc type elements. */
   public static final String PROCESS_ID        = "ProcessID"; //$NON-NLS-1$
   public static final String PROCESS_NAME      = "ProcessName"; //$NON-NLS-1$
   public static final String PROCESS_STATE     = "ProcessState"; //$NON-NLS-1$
   public static final String PROCESS_STATE_REASON = "ProcessStateReason"; //$NON-NLS-1$
   public static final String START_DATE        = "StartDate"; //$NON-NLS-1$
   public static final String END_DATE          = "EndDate"; //$NON-NLS-1$
   public static final String PLAN_ID           = "PlanID"; //$NON-NLS-1$
   public static final String PROCESS_DOCUMENT  = "ProcessDocument"; //$NON-NLS-1$
   public static final String PENDING_INVOKES_COUNT = "PendingInvokesCount"; //$NON-NLS-1$
   public static final String MODIFIED_DATE     = "ModifiedDate"; //$NON-NLS-1$
   public static final String MODIFIED_COUNT    = "ModifiedCount"; //$NON-NLS-1$

   /* AeReceivedItem doc type elements. */
   public static final String LOCATION_PATH_ID    = "LocationPathID"; //$NON-NLS-1$
   public static final String MESSAGE_DOCUMENT    = "MessageDocument"; //$NON-NLS-1$

   /* AeVariable doc type elements. */
   public static final String VERSION_NUMBER    = "VersionNumber"; //$NON-NLS-1$
   public static final String VARIABLE_DOCUMENT = "VariableDocument"; //$NON-NLS-1$

   /* AeProcessLog doc type elements. */
   public static final String LOG_ID            = "LogID"; //$NON-NLS-1$
   public static final String LINE_COUNT        = "LineCount"; //$NON-NLS-1$
   public static final String PROCESS_LOG       = "ProcessLog"; //$NON-NLS-1$
}
