// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/visitors/AeDefTraverser.java,v 1.22 2007/09/12 02:48:1
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
package org.activebpel.rt.bpel.def.visitors;

import java.util.Iterator;

import org.activebpel.rt.bpel.def.AeActivityDef;
import org.activebpel.rt.bpel.def.AeBaseDef;
import org.activebpel.rt.bpel.def.AeCatchAllDef;
import org.activebpel.rt.bpel.def.AeCatchDef;
import org.activebpel.rt.bpel.def.AeCompensationHandlerDef;
import org.activebpel.rt.bpel.def.AeCorrelationSetDef;
import org.activebpel.rt.bpel.def.AeCorrelationSetsDef;
import org.activebpel.rt.bpel.def.AeCorrelationsDef;
import org.activebpel.rt.bpel.def.AeDocumentationDef;
import org.activebpel.rt.bpel.def.AeEventHandlersDef;
import org.activebpel.rt.bpel.def.AeExtensionActivityDef;
import org.activebpel.rt.bpel.def.AeExtensionAttributeDef;
import org.activebpel.rt.bpel.def.AeExtensionDef;
import org.activebpel.rt.bpel.def.AeExtensionsDef;
import org.activebpel.rt.bpel.def.AeFaultHandlersDef;
import org.activebpel.rt.bpel.def.AeImportDef;
import org.activebpel.rt.bpel.def.AeMessageExchangeDef;
import org.activebpel.rt.bpel.def.AeMessageExchangesDef;
import org.activebpel.rt.bpel.def.AePartnerDef;
import org.activebpel.rt.bpel.def.AePartnerLinkDef;
import org.activebpel.rt.bpel.def.AePartnerLinksDef;
import org.activebpel.rt.bpel.def.AePartnersDef;
import org.activebpel.rt.bpel.def.AeProcessDef;
import org.activebpel.rt.bpel.def.AeScopeDef;
import org.activebpel.rt.bpel.def.AeTerminationHandlerDef;
import org.activebpel.rt.bpel.def.AeVariableDef;
import org.activebpel.rt.bpel.def.AeVariablesDef;
import org.activebpel.rt.bpel.def.IAeForUntilParentDef;
import org.activebpel.rt.bpel.def.activity.AeActivityAssignDef;
import org.activebpel.rt.bpel.def.activity.AeActivityBreakDef;
import org.activebpel.rt.bpel.def.activity.AeActivityCompensateDef;
import org.activebpel.rt.bpel.def.activity.AeActivityCompensateScopeDef;
import org.activebpel.rt.bpel.def.activity.AeActivityContinueDef;
import org.activebpel.rt.bpel.def.activity.AeActivityEmptyDef;
import org.activebpel.rt.bpel.def.activity.AeActivityExitDef;
import org.activebpel.rt.bpel.def.activity.AeActivityFlowDef;
import org.activebpel.rt.bpel.def.activity.AeActivityForEachDef;
import org.activebpel.rt.bpel.def.activity.AeActivityIfDef;
import org.activebpel.rt.bpel.def.activity.AeActivityInvokeDef;
import org.activebpel.rt.bpel.def.activity.AeActivityOpaqueDef;
import org.activebpel.rt.bpel.def.activity.AeActivityPickDef;
import org.activebpel.rt.bpel.def.activity.AeActivityReceiveDef;
import org.activebpel.rt.bpel.def.activity.AeActivityRepeatUntilDef;
import org.activebpel.rt.bpel.def.activity.AeActivityReplyDef;
import org.activebpel.rt.bpel.def.activity.AeActivityRethrowDef;
import org.activebpel.rt.bpel.def.activity.AeActivityScopeDef;
import org.activebpel.rt.bpel.def.activity.AeActivitySequenceDef;
import org.activebpel.rt.bpel.def.activity.AeActivitySuspendDef;
import org.activebpel.rt.bpel.def.activity.AeActivityThrowDef;
import org.activebpel.rt.bpel.def.activity.AeActivityValidateDef;
import org.activebpel.rt.bpel.def.activity.AeActivityWaitDef;
import org.activebpel.rt.bpel.def.activity.AeActivityWhileDef;
import org.activebpel.rt.bpel.def.activity.AeNotUnderstoodExtensionActivityDef;
import org.activebpel.rt.bpel.def.activity.support.AeAssignCopyDef;
import org.activebpel.rt.bpel.def.activity.support.AeConditionDef;
import org.activebpel.rt.bpel.def.activity.support.AeCorrelationDef;
import org.activebpel.rt.bpel.def.activity.support.AeElseDef;
import org.activebpel.rt.bpel.def.activity.support.AeElseIfDef;
import org.activebpel.rt.bpel.def.activity.support.AeExtensibleAssignDef;
import org.activebpel.rt.bpel.def.activity.support.AeForDef;
import org.activebpel.rt.bpel.def.activity.support.AeForEachBranchesDef;
import org.activebpel.rt.bpel.def.activity.support.AeForEachCompletionConditionDef;
import org.activebpel.rt.bpel.def.activity.support.AeForEachFinalDef;
import org.activebpel.rt.bpel.def.activity.support.AeForEachStartDef;
import org.activebpel.rt.bpel.def.activity.support.AeFromDef;
import org.activebpel.rt.bpel.def.activity.support.AeFromPartDef;
import org.activebpel.rt.bpel.def.activity.support.AeFromPartsDef;
import org.activebpel.rt.bpel.def.activity.support.AeIfDef;
import org.activebpel.rt.bpel.def.activity.support.AeJoinConditionDef;
import org.activebpel.rt.bpel.def.activity.support.AeLinkDef;
import org.activebpel.rt.bpel.def.activity.support.AeLinksDef;
import org.activebpel.rt.bpel.def.activity.support.AeLiteralDef;
import org.activebpel.rt.bpel.def.activity.support.AeOnAlarmDef;
import org.activebpel.rt.bpel.def.activity.support.AeOnEventDef;
import org.activebpel.rt.bpel.def.activity.support.AeOnMessageDef;
import org.activebpel.rt.bpel.def.activity.support.AeQueryDef;
import org.activebpel.rt.bpel.def.activity.support.AeRepeatEveryDef;
import org.activebpel.rt.bpel.def.activity.support.AeSourceDef;
import org.activebpel.rt.bpel.def.activity.support.AeSourcesDef;
import org.activebpel.rt.bpel.def.activity.support.AeTargetDef;
import org.activebpel.rt.bpel.def.activity.support.AeTargetsDef;
import org.activebpel.rt.bpel.def.activity.support.AeToDef;
import org.activebpel.rt.bpel.def.activity.support.AeToPartDef;
import org.activebpel.rt.bpel.def.activity.support.AeToPartsDef;
import org.activebpel.rt.bpel.def.activity.support.AeTransitionConditionDef;
import org.activebpel.rt.bpel.def.activity.support.AeUntilDef;
import org.activebpel.rt.bpel.def.io.ext.AeExtensionElementDef;

/**
 * The external iterator for the IAeDefVisitor interface. Provides the logic for
 * traversing the BPEL definition objects.
 */
public class AeDefTraverser implements IAeDefTraverser
{
   /**
    * Default c'tor.
    */
   public AeDefTraverser()
   {
      super();
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.AeProcessDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeProcessDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      callAccept(aDef.getExtensionsDef(), aVisitor);
      callAccept(aDef.getImportDefs(), aVisitor);
      callAccept(aDef.getPartnerLinksDef(), aVisitor);
      callAccept(aDef.getPartnersDef(), aVisitor);
      callAccept(aDef.getMessageExchangesDef(), aVisitor);
      callAccept(aDef.getVariablesDef(), aVisitor);
      callAccept(aDef.getCorrelationSetsDef(), aVisitor);
      callAccept(aDef.getFaultHandlersDef(), aVisitor);
      callAccept(aDef.getCompensationHandlerDef(), aVisitor);
      callAccept(aDef.getEventHandlersDef(), aVisitor);
      callAccept(aDef.getTerminationHandlerDef(), aVisitor);
      callAccept(aDef.getActivityDef(), aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.AeActivityScopeDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeActivityScopeDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      traverseSourceAndTargetLinks(aDef, aVisitor);
      AeScopeDef scope = aDef.getScopeDef();
      scope.accept(aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * Walk each of the <code>AeCorrelationSetDef</code> and call accept.
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.AeCorrelationSetsDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeCorrelationSetsDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      // @todo these classes should impl common interface as per chris's
      //       original idea.
      callAccept(aDef.getValues(), aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * Walk each of the <code>AeOnMessageDef</code> and <code>AeOnAlarmDef</code>
    * and call accept.
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.AeEventHandlersDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeEventHandlersDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      callAccept(aDef.getOnEventDefs(), aVisitor);
      callAccept(aDef.getAlarmDefs(), aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * Walk all of the <code>AeFaultHandlerDef</code> as well as the
    * <code>AeDefaultFaultHandlerDef</code> and call accept
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.AeFaultHandlersDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeFaultHandlersDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      callAccept(aDef.getCatchDefs(), aVisitor);
      callAccept(aDef.getCatchAllDef(), aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * Calls accept on the single child of the compensation handler
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.AeCompensationHandlerDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeCompensationHandlerDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      callAccept(aDef.getActivityDef(), aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * Walk all of the <code>AeVariableDef</code> and call accept.
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.AeVariablesDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeVariablesDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      callAccept(aDef.getValues(), aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.AeCorrelationSetDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeCorrelationSetDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * Calls <code>accept</code> on the child activity for the fault
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.AeCatchDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeCatchDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      callAccept(aDef.getActivityDef(), aVisitor);
      // ws-bpel has a fault variable def
      callAccept(aDef.getFaultVariableDef(), aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.AeVariableDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeVariableDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      callAccept(aDef.getFromDef(), aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * Calls <code>accept</code> on the child activity for the alarm.
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.support.AeOnAlarmDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeOnAlarmDef aDef, IAeDefVisitor aVisitor)
   {
      // Note: Check for overrides if you're changing this code.
      traverseDocumentationDef(aDef, aVisitor);
      traverseForAndUntilDefs(aDef, aVisitor);
      callAccept(aDef.getRepeatEveryDef(), aVisitor);
      callAccept(aDef.getActivityDef(), aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * Calls <code>accept</code> on the correlations and the child activity
    * for the message.
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.support.AeOnMessageDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeOnMessageDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      callAccept(aDef.getCorrelationsDef(), aVisitor);
      callAccept(aDef.getFromPartsDef(), aVisitor);
      callAccept(aDef.getActivityDef(), aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.support.AeOnEventDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeOnEventDef aDef, IAeDefVisitor aVisitor)
   {
      traverse((AeOnMessageDef) aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * Calls <code>accept</code> on the <code>AeAssignCopyDef</code> children.
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.AeActivityAssignDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeActivityAssignDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      traverseSourceAndTargetLinks(aDef, aVisitor);
      callAccept(aDef.getCopyDefs(), aVisitor);
      callAccept(aDef.getExtensibleAssignDefs(), aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.AeActivityCompensateDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeActivityCompensateDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      traverseSourceAndTargetLinks(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.AeActivityCompensateScopeDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeActivityCompensateScopeDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      traverseSourceAndTargetLinks(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.AeActivityEmptyDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeActivityEmptyDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      traverseSourceAndTargetLinks(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.AeActivityOpaqueDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeActivityOpaqueDef  aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      traverseSourceAndTargetLinks(aDef, aVisitor);      
      traverseExtensionDefs(aDef, aVisitor);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.AeActivitySuspendDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeActivitySuspendDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      traverseSourceAndTargetLinks(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.AeActivityContinueDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeActivityContinueDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      traverseSourceAndTargetLinks(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.AeActivityBreakDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeActivityBreakDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      traverseSourceAndTargetLinks(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * Walk all of the <code>AeLinkDef</code> and <code>AeActivityDef</code>
    * and call <code>accept</code>
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.AeActivityFlowDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeActivityFlowDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      traverseSourceAndTargetLinks(aDef, aVisitor);
      callAccept(aDef.getLinksDef(), aVisitor);
      callAccept(aDef.getActivityDefs(), aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * Walks all of the <code>AeInvokeDef</code> and calls <code>accept</code>.
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.AeActivityInvokeDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeActivityInvokeDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      traverseSourceAndTargetLinks(aDef, aVisitor);
      callAccept(aDef.getCorrelationsDef(), aVisitor);
      if (aDef.getImplicitScopeDef() != null)
      {
         AeScopeDef implicitScopeDef = aDef.getImplicitScopeDef().getScopeDef();
         callAccept(implicitScopeDef.getCatchDefs(), aVisitor);
         callAccept(implicitScopeDef.getCatchAllDef(), aVisitor);
         callAccept(implicitScopeDef.getCompensationHandlerDef(), aVisitor);
      }
      callAccept(aDef.getToPartsDef(), aVisitor);
      callAccept(aDef.getFromPartsDef(), aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * Walk the <code>AeOnMessageDef</code> and <code>AeOnAlarmDef</code> and
    * call <code>accept</code>.
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.AeActivityPickDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeActivityPickDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      traverseSourceAndTargetLinks(aDef, aVisitor);
      callAccept(aDef.getOnMessageDefs(), aVisitor);
      callAccept(aDef.getAlarmDefs(), aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * Walks all of the <code>AeCorrelationDef</code> and calls 
    * <code>accept</code>.
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.AeActivityReceiveDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeActivityReceiveDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      traverseSourceAndTargetLinks(aDef, aVisitor);
      callAccept(aDef.getCorrelationsDef(), aVisitor);
      callAccept(aDef.getFromPartsDef(), aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * Walks all of the <code>AeCorrelationDef</code> and calls 
    * <code>accept</code>.
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.AeActivityReplyDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeActivityReplyDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      traverseSourceAndTargetLinks(aDef, aVisitor);
      callAccept(aDef.getCorrelationsDef(), aVisitor);
      callAccept(aDef.getToPartsDef(), aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * Walk all of the child activities and call <code>accept</code>.
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.AeActivitySequenceDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeActivitySequenceDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      traverseSourceAndTargetLinks(aDef, aVisitor);
      callAccept(aDef.getActivityDefs(), aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * Traverse terminate for inbound links
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.AeActivityExitDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeActivityExitDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      traverseSourceAndTargetLinks(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.AeActivityThrowDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeActivityThrowDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      traverseSourceAndTargetLinks(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.AeActivityWaitDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeActivityWaitDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      traverseSourceAndTargetLinks(aDef, aVisitor);
      traverseForAndUntilDefs(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * Call <code>accept</code> on the child activity.
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.AeActivityWhileDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeActivityWhileDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      traverseSourceAndTargetLinks(aDef, aVisitor);
      callAccept(aDef.getConditionDef(), aVisitor);
      callAccept(aDef.getActivityDef(), aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.AeActivityRepeatUntilDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeActivityRepeatUntilDef aDef, IAeDefVisitor aVisitor)
   {
      // different from while - traverse the children in a different order
      traverseDocumentationDef(aDef, aVisitor);
      traverseSourceAndTargetLinks(aDef, aVisitor);
      callAccept(aDef.getConditionDef(), aVisitor);
      callAccept(aDef.getActivityDef(), aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.AeActivityForEachDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeActivityForEachDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      traverseSourceAndTargetLinks(aDef, aVisitor);
      callAccept(aDef.getStartDef(), aVisitor);
      callAccept(aDef.getFinalDef(), aVisitor);
      callAccept(aDef.getCompletionCondition(), aVisitor);
      callAccept(aDef.getActivityDef(), aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.support.AeForEachFinalDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeForEachFinalDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.support.AeForEachStartDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeForEachStartDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.support.AeForEachBranchesDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeForEachBranchesDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.support.AeForEachCompletionConditionDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeForEachCompletionConditionDef aDef,
         IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      callAccept(aDef.getBranches(), aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * Call <code>accept</code> on the child activity.
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.AeCatchAllDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeCatchAllDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      callAccept(aDef.getActivityDef(), aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * Calls <code>accept</code> on the From and To
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.support.AeAssignCopyDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeAssignCopyDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      callAccept(aDef.getFromDef(), aVisitor);
      callAccept(aDef.getToDef(), aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.support.AeFromDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeFromDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);

      callAccept(aDef.getLiteralDef(), aVisitor);
      callAccept(aDef.getQueryDef(), aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.support.AeToDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeToDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      callAccept(aDef.getQueryDef(), aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.support.AeQueryDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeQueryDef aDef, IAeDefVisitor aVisitor)
   {
   }

   /**
    * Walks all of the <code>correlation</code> objects and calls accept.
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.AeCorrelationsDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeCorrelationsDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      callAccept(aDef.getValues(), aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.support.AeCorrelationDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeCorrelationDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.support.AeLinkDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeLinkDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.AePartnerDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AePartnerDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.AePartnerLinkDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AePartnerLinkDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.AeMessageExchangesDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeMessageExchangesDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);

      callAccept(aDef.getMessageExchangeDefs(), aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.AeMessageExchangeDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeMessageExchangeDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.support.AeExtensibleAssignDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeExtensibleAssignDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.AeExtensionDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeExtensionDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.AeExtensionsDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeExtensionsDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);

      callAccept(aDef.getExtensionDefs(), aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * Calls <code>accept</code> on the fault handler, compensation handler,
    * variable container, correlationset container, event handler, and the
    * child activity.
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.AeScopeDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeScopeDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      callAccept(aDef.getPartnerLinksDef(), aVisitor);
      callAccept(aDef.getMessageExchangesDef(), aVisitor);
      callAccept(aDef.getVariablesDef(), aVisitor);
      callAccept(aDef.getCorrelationSetsDef(), aVisitor);
      callAccept(aDef.getFaultHandlersDef(), aVisitor);
      callAccept(aDef.getCompensationHandlerDef(), aVisitor);
      callAccept(aDef.getEventHandlersDef(), aVisitor);
      callAccept(aDef.getTerminationHandlerDef(), aVisitor);
      callAccept(aDef.getActivityDef(), aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * Walk all of the child partnerLinks and call <code>accept</code>.
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.AePartnerLinksDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AePartnerLinksDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      callAccept(aDef.getValues(), aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * Walk all of the child partners and call <code>accept</code>.
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.AePartnersDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AePartnersDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      callAccept(aDef.getValues(), aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * Walk all of the child links and call <code>accept</code>.
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.support.AeLinksDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeLinksDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      callAccept(aDef.getLinkDefs(), aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.support.AeSourcesDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeSourcesDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      callAccept(aDef.getSourceDefs(), aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.support.AeSourceDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeSourceDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      callAccept(aDef.getTransitionConditionDef(), aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.support.AeTargetsDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeTargetsDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);

      callAccept(aDef.getJoinConditionDef(), aVisitor);
      callAccept(aDef.getTargetDefs(), aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.support.AeTargetDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeTargetDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.AeImportDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeImportDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.AeDocumentationDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeDocumentationDef aDef, IAeDefVisitor aVisitor)
   {
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.AeActivityValidateDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeActivityValidateDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      traverseSourceAndTargetLinks(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.support.AeFromPartsDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeFromPartsDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      
      callAccept(aDef.getFromPartDefs(), aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.support.AeToPartsDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeToPartsDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      
      callAccept(aDef.getToPartDefs(), aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.support.AeFromPartDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeFromPartDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.support.AeToPartDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeToPartDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.support.AeJoinConditionDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeJoinConditionDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.support.AeTransitionConditionDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeTransitionConditionDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.support.AeForDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeForDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.support.AeUntilDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeUntilDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(AeNotUnderstoodExtensionActivityDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeNotUnderstoodExtensionActivityDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      traverseSourceAndTargetLinks(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.AeExtensionActivityDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeExtensionActivityDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);

      callAccept(aDef.getActivityDef(), aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.AeActivityIfDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeActivityIfDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      traverseSourceAndTargetLinks(aDef, aVisitor);

      callAccept(aDef.getIfDef(), aVisitor);
      callAccept(aDef.getElseIfDefs(), aVisitor);
      callAccept(aDef.getElseDef(), aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.support.AeConditionDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeConditionDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.support.AeElseDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeElseDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);

      callAccept(aDef.getActivityDef(), aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.support.AeElseIfDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeElseIfDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);

      callAccept(aDef.getConditionDef(), aVisitor);
      callAccept(aDef.getActivityDef(), aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.support.AeIfDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeIfDef aDef, IAeDefVisitor aVisitor)
   {
      callAccept(aDef.getConditionDef(), aVisitor);
      callAccept(aDef.getActivityDef(), aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.AeActivityRethrowDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeActivityRethrowDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      traverseSourceAndTargetLinks(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.support.AeRepeatEveryDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeRepeatEveryDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.AeTerminationHandlerDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeTerminationHandlerDef aDef, IAeDefVisitor aVisitor)
   {
      traverseDocumentationDef(aDef, aVisitor);
      callAccept(aDef.getActivityDef(), aVisitor);
      traverseExtensionDefs(aDef, aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.activity.support.AeLiteralDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeLiteralDef aDef, IAeDefVisitor aVisitor)
   {
      // literals cannot have documentation or extensions
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.AeExtensionAttributeDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeExtensionAttributeDef aDef, IAeDefVisitor aVisitor)
   {
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefTraverser#traverse(org.activebpel.rt.bpel.def.io.ext.AeExtensionElementDef, org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void traverse(AeExtensionElementDef aDef, IAeDefVisitor aVisitor)
   {
   }

   /**
    * Traverse for and until constructs.
    *
    * @param aDef
    * @param aVisitor
    */
   protected void traverseForAndUntilDefs(IAeForUntilParentDef aDef, IAeDefVisitor aVisitor)
   {
      callAccept(aDef.getForDef(), aVisitor);
      callAccept(aDef.getUntilDef(), aVisitor);
   }

   /**
    * @param aDef
    * @param aVisitor
    */
   protected void traverseSourceAndTargetLinks(AeActivityDef aDef, IAeDefVisitor aVisitor)
   {
      callAccept(aDef.getTargetsDef(), aVisitor);
      callAccept(aDef.getSourcesDef(), aVisitor);
   }

   /**
    * Called to traverse any extension defs that may exist.
    * 
    * @param aDef
    * @param aVisitor
    */
   protected void traverseExtensionDefs(AeBaseDef aDef, IAeDefVisitor aVisitor)
   {
      callAccept(aDef.getExtensionElementDefs(), aVisitor);
      callAccept(aDef.getExtensionAttributeDefs(), aVisitor);
   }

   /**
    * Traverse the documentation node.
    *
    * @param aBaseDef
    * @param aVisitor
    */
   protected void traverseDocumentationDef(AeBaseDef aBaseDef, IAeDefVisitor aVisitor)
   {
      callAccept(aBaseDef.getDocumentationDefs(), aVisitor);
   }

   /**
    * Calls <code>accept</code> on each of the definition objects in the 
    * Iterator
    * @param aIterator
    * @param aVisitor
    */
   protected void callAccept(Iterator aIterator, IAeDefVisitor aVisitor)
   {
      while (aIterator.hasNext())
      {
         AeBaseDef def = (AeBaseDef) aIterator.next();
         callAccept(def, aVisitor);
      }
   }

   /**
    * Calls <code>accept</code> on the def.
    * @param aDef can be null.
    * @param aVisitor
    */
   protected void callAccept(AeBaseDef aDef, IAeDefVisitor aVisitor)
   {
      if (aDef != null)
      {
         aDef.accept(aVisitor);
      }
   }
}
