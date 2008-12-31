//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/web/processview/IAeWebProcessStates.java,v 1.3 2006/02/14 22:44:2
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
package org.activebpel.rt.bpeladmin.war.web.processview;

/**
 * Interface which defines the process states used by the web visual model.
 */
public interface IAeWebProcessStates
{
   /** Current process state for Loaded */
   public static final String PROCESS_STATE_LOADED = "loaded";  //$NON-NLS-1$
   
   /** Current process state for Running */
   public static final String PROCESS_STATE_RUNNING = "running";  //$NON-NLS-1$
   
   /** Current process state for Suspended */
   public static final String PROCESS_STATE_SUSPENDED_ACTIVITY = "suspendedActivity";  //$NON-NLS-1$
   
   /** Current process state for Suspended */
   public static final String PROCESS_STATE_SUSPENDED_FAULTING = "suspendedFaulting";  //$NON-NLS-1$
   
   /** Current process state for Suspended */
   public static final String PROCESS_STATE_SUSPENDED_MANUAL = "suspendedManual";  //$NON-NLS-1$
   
   /** Current process state for Suspended */
   public static final String PROCESS_STATE_SUSPENDED = "suspended";  //$NON-NLS-1$
   
   /** Current process state for Completed */
   public static final String PROCESS_STATE_COMPLETED = "completed";  //$NON-NLS-1$
   
   /** Current process state for Compensatable */
   public static final String PROCESS_STATE_COMPENSATABLE = "compensatable"; //$NON-NLS-1$
   
   /** Current process state for Faulted */
   public static final String PROCESS_STATE_FAULTED = "faulted";  //$NON-NLS-1$
   
   /** Current process state for Unknown */
   public static final String PROCESS_STATE_UNKNOWN = "unknown";  //$NON-NLS-1$
   
}
