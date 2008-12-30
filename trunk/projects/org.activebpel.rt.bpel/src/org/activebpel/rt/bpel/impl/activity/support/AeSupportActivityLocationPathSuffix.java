//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/support/AeSupportActivityLocationPathSuffix.java,v 1.2 2006/06/26 16:50:3
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
package org.activebpel.rt.bpel.impl.activity.support;


/**
 * Lists the location paths suffixes used by the support activity.
 * For example, "_ImplicitCompensateActivity".
 */
public class AeSupportActivityLocationPathSuffix
{
   /** Location path name for a default/implicit compensate activity object. */
   public static final String  IMPLICIT_COMPENSATE_ACTIVITY       = "_ImplicitCompensateActivity"; //$NON-NLS-1$
   
   /** Location path name for a default/implicit compensation handler. */
   public static final String  IMPLICIT_COMPENSATION_HANDLER      = "_ImplicitCompensationHandler"; //$NON-NLS-1$
   
   /** Location path name for a default/implicit termination handler. */
   public static final String  IMPLICIT_TERMINATION_HANDLER      = "_ImplicitTerminationHandler"; //$NON-NLS-1$

   /** Location path name of a default/implicit fault handler. */
   public static final String  IMPLICIT_FAULT_HANDLER             = "_ImplicitFaultHandler"; //$NON-NLS-1$
   
   /** 
    * Location path name for the implicit compensate activity which is run at the end of fault/compensation handler
    * to compensate any remaining coordinated activities. 
    */
   public static final String  IMPLICIT_CC_COMPENSATE_ACTIVITY    = "_ImplicitCompensateActivityCc"; //$NON-NLS-1$
   
   /** Location path name for the coordination container support object in a scope. */
   public static final String  COORDINATION_CONTAINER             = "_CoordinationContainer" ; //$NON-NLS-1$
   
   /**
    * Location path name for the Coordinator's (main process) compensation handler (proxy).
    */
   public static final String  COORDINATION_COMPENSATION_HANDLER  = "_CoordinatedCompensationHandler" ; //$NON-NLS-1$
   
   /**
    * Location path name for the Participant's (subprocess) implicit compensate activity object. 
    */
   public static final String  COORDINATION_COMPENSATE_ACTIVITY   = "_CoordinationCompensationActivity"; //$NON-NLS-1$
         
}
