//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/web/processview/AeProcessViewButtonStates.java,v 1.7 2006/05/24 23:34:5
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

import java.util.ArrayList;

import org.activebpel.rt.bpel.def.AeBaseDef;
import org.activebpel.rt.bpel.def.visitors.AeCreateInstanceVisitor;
import org.activebpel.rt.bpel.impl.AeBpelState;
import org.activebpel.rt.bpel.impl.list.AeProcessInstanceDetail;
import org.activebpel.rt.bpeladmin.war.web.AeProcessInstanceDetailWrapper;

/**
 * Utility class for determining the button states for the various control
 * buttons in the process viewer.
 */
public class AeProcessViewButtonStates
{
   // containers for valid states
   /** Set containing element names of Pick construct's children names (onMessage and onAlarm).*/ 
   private static final ArrayList EVENT_NAMES = new ArrayList();
   /** Set containing element names of Switch construct's children names (case and otherwise).*/
   private static final ArrayList SWITCH_CHILD_NAMES = new ArrayList();
   /** Valid states where an activity can be executed (stepped) via Process Exception Management UI. */
   private static final ArrayList EXECUTE_STATES = new ArrayList();
   /** Valid states where an activity can be completed via Process Exception Management UI. */
   private static final ArrayList COMPLETE_STATES = new ArrayList();

   static
   {
      EVENT_NAMES.add( "onMessage" ); //$NON-NLS-1$
      EVENT_NAMES.add( "onAlarm" ); //$NON-NLS-1$

      SWITCH_CHILD_NAMES.add( "case" ); //$NON-NLS-1$
      SWITCH_CHILD_NAMES.add( "otherwise" ); //$NON-NLS-1$
      
      EXECUTE_STATES.add( AeBpelState.READY_TO_EXECUTE.toString() );
            
      COMPLETE_STATES.add( AeBpelState.READY_TO_EXECUTE.toString() );
      COMPLETE_STATES.add( AeBpelState.EXECUTING.toString() );
      COMPLETE_STATES.add( AeBpelState.FAULTING.toString() );
   }
   
   //----------[ Terminate | Suspend | Resume Button States ]-------------------
   
   /**
    * Returns true if the process can be terminated.
    * @param aInstanceDetail process instance details.
    */
   public static boolean isTerminatable( AeProcessInstanceDetail aInstanceDetail )
   {
      boolean isTerminatable = false;
      if (aInstanceDetail != null)
      {
         isTerminatable = new AeProcessInstanceDetailWrapper(aInstanceDetail).isTerminatable();
      }
      return isTerminatable;
   }
   
   /**
    * Returns true if the process can be suspended.
    * @param aInstanceDetail process instance details.
    */
   public static boolean isSuspendable( AeProcessInstanceDetail aInstanceDetail )
   {
      boolean isSuspendable = false;
      if (aInstanceDetail != null)
      {
         isSuspendable = new AeProcessInstanceDetailWrapper(aInstanceDetail).isSuspendable();
      }
      return isSuspendable;
   }
   
   
   /**
    * Returns true if the process can be resumed.
    * @param aInstanceDetail process instance details.
    */
   public static boolean isResumable( AeProcessInstanceDetail aInstanceDetail )
   {
      boolean isResumable = false;
      if (aInstanceDetail != null)
      {
         isResumable = new AeProcessInstanceDetailWrapper(aInstanceDetail).isResumable();
      }
      return isResumable;
   }
   
   //----------[ Execute | Retry | Complete Button States ]---------------------
   
   /**
    * Return true if the <code>AeBpelObjectBase</code> object can be executed (resumed).
    * @param aInstanceDetail
    * @param aBpelObj
    */
   public static boolean isExecuteActivityEnabled( AeProcessInstanceDetail aInstanceDetail, AeBpelObjectBase aBpelObj )
   {
      boolean enabled = false;
      if( isResumable( aInstanceDetail ) && (isAnActivity( aBpelObj ) || isNestedEvent( aBpelObj ) || isCaseOrOtherwise( aBpelObj)) )
      {
         enabled = EXECUTE_STATES.contains( aBpelObj.getState() );
      }
      return enabled;
   }
   
   /**
    * Return true if the <code>AeBpelObjectBase</code> object can be retried.
    * @param aBpelObj
    */
   public static boolean isRetryActivityEnabled( AeProcessInstanceDetail aInstanceDetail, AeBpelObjectBase aBpelObj )
   {
      boolean enabled = false;      
      if( isResumable( aInstanceDetail ) && isAnActivity( aBpelObj ) && isEligibleForRetry( aBpelObj) )
      {
         // retry is enabled on any activity that is faulting, or any scope that is executing.
         enabled = AeBpelState.FAULTING.toString().equalsIgnoreCase(aBpelObj.getState())
               || (  isScope(aBpelObj) && 
                     AeBpelState.EXECUTING.toString().equalsIgnoreCase( aBpelObj.getState()) );
      }
      return enabled;
   }
   
   /**
    * Return true if this process view contains an activity bpel object with
    * a state of ready to execute, executing or faulting.
    */
   public static boolean isCompleteActivityEnabled( AeProcessInstanceDetail aInstanceDetail, AeBpelObjectBase aBpelObj )
   {
      boolean enabled = false;
      if( isResumable( aInstanceDetail ) && isAnActivity( aBpelObj ) )
      {
         enabled = COMPLETE_STATES.contains( aBpelObj.getState() );
      }
      return enabled;
   }
   
   //----------[ Utility Methods ]----------------------------------------------
   
   /**
    * Return true if the <code>AeBpelObjectBase</code>'s tagname is 
    * onMessage or onAlarm.
    * @param aBpelObj current bpel object.
    */
   protected static boolean isNestedEvent( AeBpelObjectBase aBpelObj )
   {
      return aBpelObj != null && EVENT_NAMES.contains( aBpelObj.getTagName() );
   }
   
   /**
    * Return true if the <code>AeBpelObjectBase</code>'s tagname is a Case or Otherwise.
    * @param aBpelObj current bpel object.
    */
   protected static boolean isCaseOrOtherwise( AeBpelObjectBase aBpelObj )
   {
      return aBpelObj != null && SWITCH_CHILD_NAMES.contains( aBpelObj.getTagName() );
   }   
    
   /**
    * Return true if the current bpel object is represents an activity.
    * @param aBpelObj
    */
   protected static boolean isAnActivity( AeBpelObjectBase aBpelObj )
   {
      return aBpelObj != null && aBpelObj instanceof AeBpelActivityObject;
   }
   
   /**
    * Returns true if the givn visual model represents a Scope.
    * @param aBpelObj
    * @return true if the visual model is a scope.
    */
   protected static boolean isScope( AeBpelObjectBase aBpelObj )
   {
      return aBpelObj != null && aBpelObj instanceof AeBpelScopeObject;
   }
   
   /**
    * Return true if the current bpel object is eligible for retry.
    * @param aBpelObj
    */
   protected static boolean isEligibleForRetry(AeBpelObjectBase aBpelObj )
   {
      if (aBpelObj != null)
      {
         AeBaseDef def = aBpelObj.getDef();
         AeCreateInstanceVisitor createInstanceVisitor = new AeCreateInstanceVisitor();
         def.accept(createInstanceVisitor);
         return !createInstanceVisitor.isCreateInstanceFound();
      }
      else
      {
         return false;
      }
   }   
}
