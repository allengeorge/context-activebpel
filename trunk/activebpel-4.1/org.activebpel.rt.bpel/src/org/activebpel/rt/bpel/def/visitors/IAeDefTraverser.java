// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/visitors/IAeDefTraverser.java,v 1.13 2007/09/12 02:48:1
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
 * Traversal interface for use in conjunction with BPEL definition object
 * visitation. Each traverse method below accepts a definition object and
 * a visitor object. The method's responsibility is to decide how to traverse the
 * given definition object so each of its child objects (if any) will get
 * visited. 
 */
public interface IAeDefTraverser
{
   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeProcessDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeActivityAssignDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeActivityCompensateDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeActivityCompensateScopeDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeActivityEmptyDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeActivityFlowDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeActivityInvokeDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeActivityPickDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeActivityReceiveDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeActivityReplyDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeActivityScopeDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeVariablesDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeVariableDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeCorrelationSetsDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeCorrelationSetDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeFaultHandlersDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeCatchDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeCompensationHandlerDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeEventHandlersDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeOnAlarmDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeOnMessageDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeOnEventDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeActivitySequenceDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeActivityExitDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeActivityThrowDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeActivityWaitDef aDef, IAeDefVisitor aVisitor);
   
   /**
    * Traverses the definition objects, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * 
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeActivityForEachDef aDef, IAeDefVisitor aVisitor);
   
   /**
    * Traverses the definition objects, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * 
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeForEachFinalDef aDef, IAeDefVisitor aVisitor);
   
   /**
    * Traverses the definition objects, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * 
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeForEachStartDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition objects, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * 
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeForEachBranchesDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition objects, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * 
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeForEachCompletionConditionDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeActivityWhileDef aDef, IAeDefVisitor aVisitor);
   
   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeActivityRepeatUntilDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeActivityContinueDef aDef, IAeDefVisitor aVisitor);
   
   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeActivityBreakDef aDef, IAeDefVisitor aVisitor);
   
   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeActivitySuspendDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeCatchAllDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeAssignCopyDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeCorrelationDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeLinkDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AePartnerDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AePartnerLinkDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeScopeDef aDef, IAeDefVisitor aVisitor);
   
   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeMessageExchangesDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeMessageExchangeDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeSourcesDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeSourceDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeTargetsDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeTargetDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AePartnerLinksDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AePartnersDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeLinksDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeCorrelationsDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeFromDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeToDef aDef, IAeDefVisitor aVisitor);
   
   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeQueryDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeImportDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeDocumentationDef aDef, IAeDefVisitor aVisitor);
   
   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeActivityValidateDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeExtensibleAssignDef aDef, IAeDefVisitor aVisitor);
   
   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeExtensionsDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeExtensionDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeFromPartsDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeToPartsDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeFromPartDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeToPartDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeTransitionConditionDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeJoinConditionDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeForDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeUntilDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeExtensionActivityDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeNotUnderstoodExtensionActivityDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeActivityIfDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeConditionDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeElseIfDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeIfDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeElseDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeActivityRethrowDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeRepeatEveryDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeTerminationHandlerDef aDef, IAeDefVisitor aVisitor);

   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeLiteralDef aDef, IAeDefVisitor aVisitor);
   
   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeActivityOpaqueDef  aDef, IAeDefVisitor aVisitor);   
   
   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeExtensionAttributeDef aDef, IAeDefVisitor aVisitor);   
   
   /**
    * Traverses the definition object, calling <code>accept</code> on each
    * of the object's child objects that can be visited.
    * @param aDef
    * @param aVisitor
    */
   public void traverse(AeExtensionElementDef aDef, IAeDefVisitor aVisitor);   
   
}
