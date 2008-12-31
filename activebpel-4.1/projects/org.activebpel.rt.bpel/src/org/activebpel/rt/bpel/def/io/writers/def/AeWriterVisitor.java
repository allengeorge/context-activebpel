// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/io/writers/def/AeWriterVisitor.java,v 1.38 2007/09/12 02:48:1
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
package org.activebpel.rt.bpel.def.io.writers.def;

import java.util.Iterator;

import javax.xml.namespace.QName;

import org.activebpel.rt.IAeConstants;
import org.activebpel.rt.bpel.def.AeActivityDef;
import org.activebpel.rt.bpel.def.AeActivityPartnerLinkBaseDef;
import org.activebpel.rt.bpel.def.AeBaseDef;
import org.activebpel.rt.bpel.def.AeBaseDefNamespaceContext;
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
import org.activebpel.rt.bpel.def.AeNamedDef;
import org.activebpel.rt.bpel.def.AePartnerDef;
import org.activebpel.rt.bpel.def.AePartnerLinkDef;
import org.activebpel.rt.bpel.def.AePartnerLinksDef;
import org.activebpel.rt.bpel.def.AePartnersDef;
import org.activebpel.rt.bpel.def.AeProcessDef;
import org.activebpel.rt.bpel.def.AeScopeDef;
import org.activebpel.rt.bpel.def.AeTerminationHandlerDef;
import org.activebpel.rt.bpel.def.AeVariableDef;
import org.activebpel.rt.bpel.def.AeVariablesDef;
import org.activebpel.rt.bpel.def.IAeBPELConstants;
import org.activebpel.rt.bpel.def.IAeExpressionDef;
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
import org.activebpel.rt.bpel.def.activity.support.AeExpressionBaseDef;
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
import org.activebpel.rt.bpel.def.activity.support.AeVarDef;
import org.activebpel.rt.bpel.def.io.AeCommentIO;
import org.activebpel.rt.bpel.def.io.AeCorrelationPatternIOFactory;
import org.activebpel.rt.bpel.def.io.IAeCorrelationPatternIO;
import org.activebpel.rt.bpel.def.io.ext.AeExtensionElementDef;
import org.activebpel.rt.bpel.def.io.writers.AeCorrelationSetUtil;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.activebpel.rt.xml.IAeMutableNamespaceContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

/**
 * Impl of the def visitor that serializes the def to a DOM.
 */
public abstract class AeWriterVisitor implements IAeWriterDefVisitor, IAeBPELConstants
{
   /** The current element. */
   private Element mElement;
   /** The def being written. */
   private AeBaseDef mDef;

   /**
    * Creates a new element under the passed parent and starts visiting for it.
    * @param aDef The def that is being serialized
    * @param aParentElement The parent element of the objects created.
    * @param aNamespace The namespace of the element we're creating
    * @param aTagName The tag of the new element to create.
    */
   public AeWriterVisitor( AeBaseDef aDef, Element aParentElement, String aNamespace, String aTagName )
   {
      setDef(aDef);
      mElement = createElement(aParentElement, aNamespace, aTagName);
      // Check for null - parent element will be null when visiting the process root.
      if (aParentElement != null)
         addChildToParent( aParentElement );
   }

   /**
    * Adds the current element to the passed parent.
    */
   protected void addChildToParent( Element aParentElement )
   {
      aParentElement.appendChild( getElement() );
   }

   /**
    * @return Element the current element being written to.
    */
   public Element getElement()
   {
      return mElement;
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityAssignDef)
    */
   public void visit(AeActivityAssignDef aDef)
   {
      writeAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityCompensateDef)
    */
   public void visit(AeActivityCompensateDef aDef)
   {
      writeAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityCompensateScopeDef)
    */
   public void visit(AeActivityCompensateScopeDef aDef)
   {
      throw new UnsupportedOperationException();
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityEmptyDef)
    */
   public void visit(AeActivityEmptyDef aDef)
   {
      writeAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityContinueDef)
    */
   public void visit(AeActivityContinueDef aDef)
   {
      writeAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityBreakDef)
    */
   public void visit(AeActivityBreakDef aDef)
   {
      writeAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityFlowDef)
    */
   public void visit(AeActivityFlowDef aDef)
   {
      writeAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityInvokeDef)
    */
   public void visit(AeActivityInvokeDef aDef)
   {
      writeAttributes(aDef);
      setAttribute(TAG_INPUT_VARIABLE, aDef.getInputVariable());
      setAttribute(TAG_OUTPUT_VARIABLE, aDef.getOutputVariable());
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityPickDef)
    */
   public void visit(AeActivityPickDef aDef)
   {
      writeAttributes(aDef);
      setAttribute(TAG_CREATE_INSTANCE,aDef.isCreateInstance(), false);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityReceiveDef)
    */
   public void visit(AeActivityReceiveDef aDef)
   {
      writeAttributes(aDef);
      setAttribute(TAG_VARIABLE, aDef.getVariable());
      setAttribute(TAG_CREATE_INSTANCE, aDef.isCreateInstance(), false);
      writeMessageExchange(aDef.getMessageExchange());
   }

   /**
    * Writes the message exchange value if not empty or null.
    * @param aMessageExchangeValue
    */
   protected abstract void writeMessageExchange(String aMessageExchangeValue);

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityReplyDef)
    */
   public void visit(AeActivityReplyDef aDef)
   {
      writeAttributes(aDef);
      setAttribute(TAG_VARIABLE, aDef.getVariable());
      setAttribute(TAG_FAULT_NAME, aDef.getFaultName());
      writeMessageExchange(aDef.getMessageExchange());
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivitySuspendDef)
    */
   public void visit(AeActivitySuspendDef aDef)
   {
      writeAttributes(aDef);
      setAttribute(TAG_VARIABLE, aDef.getVariable());
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityScopeDef)
    */
   public void visit(AeActivityScopeDef aDef)
   {
      writeAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivitySequenceDef)
    */
   public void visit(AeActivitySequenceDef aDef)
   {
      writeAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityExitDef)
    */
   public void visit(AeActivityExitDef aDef)
   {
      writeAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityThrowDef)
    */
   public void visit(AeActivityThrowDef aDef)
   {
      writeAttributes(aDef);
      setAttribute(TAG_FAULT_NAME, aDef.getFaultName());
      setAttribute(TAG_FAULT_VARIABLE, aDef.getFaultVariable());
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityWaitDef)
    */
   public void visit(AeActivityWaitDef aDef)
   {
      writeAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityWhileDef)
    */
   public void visit(AeActivityWhileDef aDef)
   {
      writeAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityRepeatUntilDef)
    */
   public void visit(AeActivityRepeatUntilDef aDef)
   {
      throw new UnsupportedOperationException();
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityForEachDef)
    */
   public void visit(AeActivityForEachDef aDef)
   {
      writeAttributes(aDef);
      setAttribute(TAG_FOREACH_COUNTERNAME, aDef.getCounterName());
      setAttribute(TAG_FOREACH_PARALLEL, aDef.isParallel(), true);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeForEachCompletionConditionDef)
    */
   public void visit(AeForEachCompletionConditionDef aDef)
   {
      writeStandardAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeForEachFinalDef)
    */
   public void visit(AeForEachFinalDef aDef)
   {
      writeExpressionDef(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeForEachStartDef)
    */
   public void visit(AeForEachStartDef aDef)
   {
      writeExpressionDef(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeForEachBranchesDef)
    */
   public void visit(AeForEachBranchesDef aDef)
   {
      writeExpressionDef(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeAssignCopyDef)
    */
   public void visit(AeAssignCopyDef aDef)
   {
      writeAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeFromDef)
    */
   public void visit(AeFromDef aDef)
   {
      writeAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeToDef)
    */
   public void visit(AeToDef aDef)
   {
      writeAttributes(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeQueryDef)
    */
   public void visit(AeQueryDef aDef)
   {
      throw new UnsupportedOperationException();
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AeCorrelationsDef)
    */
   public void visit(AeCorrelationsDef aDef)
   {
      writeAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeCorrelationDef)
    */
   public void visit(AeCorrelationDef aDef)
   {
      writeAttributes(aDef);
      setAttribute(TAG_SET, aDef.getCorrelationSetName());
      if (aDef.getPattern() != null)
      {
         AeCorrelationDef.AeCorrelationPatternType type = aDef.getPattern();
         IAeCorrelationPatternIO patternIO = AeCorrelationPatternIOFactory.getInstance(getElement().getNamespaceURI());
         setAttribute(TAG_PATTERN, patternIO.toString(type));
      }
      setAttribute(TAG_INITIATE, aDef.getInitiate());
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AeCorrelationSetsDef)
    */
   public void visit(AeCorrelationSetsDef aDef)
   {
      writeAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AeCorrelationSetDef)
    */
   public void visit(AeCorrelationSetDef aDef)
   {
      writeAttributes(aDef);
      setAttribute(TAG_PROPERTIES,
               AeCorrelationSetUtil.formatProperties(aDef,getElement()));
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AeCatchAllDef)
    */
   public void visit(AeCatchAllDef aDef)
   {
      writeAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AeEventHandlersDef)
    */
   public void visit(AeEventHandlersDef aDef)
   {
      writeAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AeCatchDef)
    */
   public void visit(AeCatchDef aDef)
   {
      writeAttributes(aDef);
      setAttribute(TAG_FAULT_NAME, aDef.getFaultName());
      setAttribute(TAG_FAULT_VARIABLE, aDef.getFaultVariable());
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AeFaultHandlersDef)
    */
   public void visit(AeFaultHandlersDef aDef)
   {
      writeAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeLinksDef)
    */
   public void visit(AeLinksDef aDef)
   {
      writeAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeLinkDef)
    */
   public void visit(AeLinkDef aDef)
   {
      writeAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AeMessageExchangesDef)
    */
   public void visit(AeMessageExchangesDef aDef)
   {
      writeAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AeMessageExchangeDef)
    */
   public void visit(AeMessageExchangeDef aDef)
   {
      writeAttributes(aDef);

      setAttribute(TAG_NAME, aDef.getName());
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeOnAlarmDef)
    */
   public void visit(AeOnAlarmDef aDef)
   {
      writeAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeOnMessageDef)
    */
   public void visit(AeOnMessageDef aDef)
   {
      writeAttributes(aDef);
      setAttribute(TAG_PARTNER_LINK,   aDef.getPartnerLink());
      setAttribute(TAG_PORT_TYPE, aDef.getPortType());
      setAttribute(TAG_OPERATION, aDef.getOperation());
      setAttribute(TAG_VARIABLE, aDef.getVariable());
      writeMessageExchange(aDef.getMessageExchange());
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeOnEventDef)
    */
   public void visit(AeOnEventDef aDef)
   {
      visit((AeOnMessageDef) aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AePartnerDef)
    */
   public void visit(AePartnerDef aDef)
   {
      writeAttributes(aDef);

      for(Iterator iter=aDef.getPartnerLinks(); iter.hasNext();)
      {
         String qualifier = AeUtil.isNullOrEmpty(getElement().getPrefix()) ? "" : getElement().getPrefix() + ":"; //$NON-NLS-1$ //$NON-NLS-2$
         Element element = getElement().getOwnerDocument().createElementNS(getElement().getNamespaceURI(), qualifier + TAG_PARTNER_LINK);
         // use setAttributeNS to ensure Attr impl is NS aware
         element.setAttributeNS(null, TAG_NAME, (String) iter.next());
         getElement().appendChild(element);
      }
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AePartnerLinkDef)
    */
   public void visit(AePartnerLinkDef aDef)
   {
      writeAttributes(aDef);
      setAttribute(TAG_PARTNER_LINK_TYPE, aDef.getPartnerLinkTypeName());
      setAttribute(TAG_MY_ROLE, aDef.getMyRole());
      setAttribute(TAG_PARTNER_ROLE, aDef.getPartnerRole());
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AePartnerLinksDef)
    */
   public void visit(AePartnerLinksDef aDef)
   {
      writeAttributes(aDef);
   }


   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AePartnersDef)
    */
   public void visit(AePartnersDef aDef)
   {
      writeAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AeProcessDef)
    */
   public void visit(AeProcessDef aDef)
   {
      // Add preamble comment if available (user comments written with attributes method)
      if(! AeUtil.isNullOrEmpty(aDef.getProcessPreambleComment()))
      {
         Document doc = getElement().getOwnerDocument();
         Node commentNode = doc.createComment(aDef.getProcessPreambleComment());
         getElement().getParentNode().insertBefore( commentNode, getElement() );
      }

      writeAttributes(aDef);
      
      // write the default namespace out if it's present.
      if (AeUtil.notNullOrEmpty(aDef.getDefaultNamespace()))
      {
         getElement().setAttributeNS(IAeConstants.W3C_XMLNS, "xmlns", aDef.getDefaultNamespace()); //$NON-NLS-1$
      }

      setAttribute(TAG_TARGET_NAMESPACE, aDef.getTargetNamespace());
      setAttribute(TAG_QUERY_LANGUAGE, aDef.getQueryLanguage());
      setAttribute(TAG_EXPRESSION_LANGUAGE, aDef.getExpressionLanguage());

      setAttribute(TAG_SUPPRESS_JOIN_FAILURE, aDef.getSuppressJoinFailure(), false);
      setAttribute(TAG_ENABLE_INSTANCE_COMPENSATION, aDef.getEnableInstanceCompensation(), false);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AeCompensationHandlerDef)
    */
   public void visit(AeCompensationHandlerDef aDef)
   {
      writeAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AeScopeDef)
    */
   public void visit(AeScopeDef aDef)
   {
      writeAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeSourcesDef)
    */
   public void visit(AeSourcesDef aDef)
   {
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeSourceDef)
    */
   public void visit(AeSourceDef aDef)
   {
      writeAttributes(aDef);
      setAttribute(TAG_LINK_NAME, aDef.getLinkName());
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeTargetsDef)
    */
   public void visit(AeTargetsDef aDef)
   {
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeTargetDef)
    */
   public void visit(AeTargetDef aDef)
   {
      writeAttributes(aDef);
      setAttribute(TAG_LINK_NAME,aDef.getLinkName());
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AeVariableDef)
    */
   public void visit(AeVariableDef aDef)
   {
      writeAttributes(aDef);
      setAttribute(TAG_MESSAGE_TYPE, aDef.getMessageType());
      setAttribute(TAG_TYPE, aDef.getType());
      setAttribute(TAG_ELEMENT, aDef.getElement());
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AeVariablesDef)
    */
   public void visit(AeVariablesDef aDef)
   {
      writeAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AeImportDef)
    */
   public void visit(AeImportDef aDef)
   {
      throw new UnsupportedOperationException();
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AeDocumentationDef)
    */
   public void visit(AeDocumentationDef aDef)
   {
      throw new UnsupportedOperationException();
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeExtensibleAssignDef)
    */
   public void visit(AeExtensibleAssignDef aDef)
   {
      throw new UnsupportedOperationException();
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityValidateDef)
    */
   public void visit(AeActivityValidateDef aDef)
   {
      throw new UnsupportedOperationException();
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AeExtensionDef)
    */
   public void visit(AeExtensionDef aDef)
   {
      throw new UnsupportedOperationException();
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AeExtensionsDef)
    */
   public void visit(AeExtensionsDef aDef)
   {
      throw new UnsupportedOperationException();
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeFromPartsDef)
    */
   public void visit(AeFromPartsDef aDef)
   {
      throw new UnsupportedOperationException();
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeToPartsDef)
    */
   public void visit(AeToPartsDef aDef)
   {
      throw new UnsupportedOperationException();
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeFromPartDef)
    */
   public void visit(AeFromPartDef aDef)
   {
      throw new UnsupportedOperationException();
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeToPartDef)
    */
   public void visit(AeToPartDef aDef)
   {
      throw new UnsupportedOperationException();
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeJoinConditionDef)
    */
   public void visit(AeJoinConditionDef aDef)
   {
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeTransitionConditionDef)
    */
   public void visit(AeTransitionConditionDef aDef)
   {
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeForDef)
    */
   public void visit(AeForDef aDef)
   {
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeUntilDef)
    */
   public void visit(AeUntilDef aDef)
   {
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(AeNotUnderstoodExtensionActivityDef)
    */
   public void visit(AeNotUnderstoodExtensionActivityDef aDef)
   {
      throw new UnsupportedOperationException();
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AeExtensionActivityDef)
    */
   public void visit(AeExtensionActivityDef aDef)
   {
      // The writer registry for bpel 1.1 will skip the extension activity wrapper
      throw new UnsupportedOperationException();
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityIfDef)
    */
   public void visit(AeActivityIfDef aDef)
   {
      throw new UnsupportedOperationException();
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeConditionDef)
    */
   public void visit(AeConditionDef aDef)
   {
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeElseDef)
    */
   public void visit(AeElseDef aDef)
   {
      throw new UnsupportedOperationException();
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeElseIfDef)
    */
   public void visit(AeElseIfDef aDef)
   {
      throw new UnsupportedOperationException();
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeIfDef)
    */
   public void visit(AeIfDef aDef)
   {
      throw new UnsupportedOperationException();
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityRethrowDef)
    */
   public void visit(AeActivityRethrowDef aDef)
   {
      throw new UnsupportedOperationException();
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeRepeatEveryDef)
    */
   public void visit(AeRepeatEveryDef aDef)
   {
      throw new UnsupportedOperationException();
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AeTerminationHandlerDef)
    */
   public void visit(AeTerminationHandlerDef aDef)
   {
      throw new UnsupportedOperationException();
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeLiteralDef)
    */
   public void visit(AeLiteralDef aDef)
   {
      writeAttributes(aDef);

      for (Iterator iter = aDef.getChildNodes().iterator(); iter.hasNext(); )
      {
         Node node = (Node) iter.next();
         Node importedNode = getElement().getOwnerDocument().importNode(node, true);
         getElement().appendChild(importedNode);
         if (importedNode.getNodeType() == Node.ELEMENT_NODE)
            AeXmlUtil.removeDuplicateNSDecls((Element) importedNode);
      }
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityOpaqueDef)
    */
   public void visit(AeActivityOpaqueDef aDef)
   {
      throw new UnsupportedOperationException();
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AeExtensionAttributeDef)
    */
   public void visit(AeExtensionAttributeDef aDef)
   {
      // Note: the extension attribute def is skipped in the registry
      throw new UnsupportedOperationException();
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.io.ext.AeExtensionElementDef)
    */
   public void visit(AeExtensionElementDef aDef)
   {
      // Note: a special writer is used to write out the extension element def.
      throw new UnsupportedOperationException();
   }
   
   /**
    * Write attributes to the Element.
    * @param aDef
    */
   protected void writeAttributes( AeActivityPartnerLinkBaseDef aDef )
   {
      writeStandardAttributes(aDef);
      writeNamedAttributes(aDef);
      writeActivityAttributes(aDef);
      writeActivityPartnerLinkBasedAttributes(aDef);
   }

   /**
    * Write attributes to the Element.
    * @param aDef
    */
   protected void writeAttributes( AeActivityDef aDef )
   {
      writeStandardAttributes(aDef);
      writeNamedAttributes(aDef);
      writeActivityAttributes(aDef);
   }

   /**
    * Write attributes to the Element.
    * @param aDef
    */
   protected void writeAttributes( AeNamedDef aDef )
   {
      writeStandardAttributes(aDef);
      writeNamedAttributes(aDef);
   }

   /**
    * Write attributes to the Element.
    * @param aDef
    */
   protected void writeAttributes( AeToDef aDef )
   {
      writeStandardAttributes(aDef);
      writeAssignToAttributes(aDef);
   }

   /**
    * Write attributes to the Element.
    * @param aDef
    */
   protected void writeAttributes( AeFromDef aDef )
   {
      writeStandardAttributes(aDef);
      writeAssignFromAttributes(aDef);
   }

   /**
    * Write attributes to the Element.
    * @param aDef
    */
   protected void writeAttributes(AeBaseDef aDef)
   {
      writeStandardAttributes(aDef);
   }

   /**
    * Write attributes to the Element.
    * @param aDef
    */
   protected void writeActivityPartnerLinkBasedAttributes(AeActivityPartnerLinkBaseDef aDef )
   {
      setAttribute(TAG_PARTNER_LINK, aDef.getPartnerLink());
      setAttribute(TAG_PORT_TYPE, aDef.getPortType());
      setAttribute(TAG_OPERATION, aDef.getOperation());
   }

   /**
    * Write attributes to the Element.
    * @param aDef
    */
   protected void writeActivityAttributes( AeActivityDef aDef )
   {
      // Suppress join failure is an optional flag, do not set if null
      if (aDef.getSuppressFailure() != null)
      {
         setAttribute(TAG_SUPPRESS_FAILURE,
                     aDef.getSuppressFailure().booleanValue(), true);
      }
   }

   /**
    * Write attributes to the Element.
    * @param aDef
    */
   protected void writeNamedAttributes(AeNamedDef aDef)
   {
      setAttribute(TAG_NAME, aDef.getName());
   }

   /**
    * Write attributes to the Element.
    * @param aDef
    */
   protected void writeAssignVarAttributes(AeVarDef aDef)
   {
      setAttribute(TAG_VARIABLE, aDef.getVariable());
      setAttribute(TAG_PROPERTY, aDef.getProperty());
      setAttribute(TAG_PART, aDef.getPart());
      setAttribute(TAG_PARTNER_LINK, aDef.getPartnerLink());
   }

   /**
    * Write attributes to the Element.
    * @param aDef
    */
   protected void writeAssignToAttributes(AeToDef aDef)
   {
      writeAssignVarAttributes(aDef);
   }

   /**
    * Write attributes to the Element.
    * @param aDef
    */
   protected void writeAssignFromAttributes(AeFromDef aDef)
   {
      writeAssignVarAttributes(aDef);
      setAttribute(TAG_ENDPOINT_REFERENCE, aDef.getEndpointReference());
   }

   /**
    * Writes stored comments and namespace decls to the element.
    *
    * @param aBaseDef
    */
   protected void writeStandardAttributes(AeBaseDef aBaseDef)
   {
      AeCommentIO.writeFormattedComments( getElement(), aBaseDef.getComment() );
      writeNamespaceAttributes( aBaseDef, getElement() );
      writeExtensionAttributes( aBaseDef, getElement() );
   }


   /**
    * Responsible for writing out namespace attributes for the created element.
    * @param aElement current element node in the tree.
    */
   protected void writeNamespaceAttributes(AeBaseDef aBaseDef, Element aElement)
   {
      for (Iterator iter = aBaseDef.getNamespacePrefixList(); iter.hasNext();)
      {
         String prefix   = (String)iter.next();
         aElement.setAttributeNS(IAeConstants.W3C_XMLNS, "xmlns:" + prefix, aBaseDef.findNamespace(prefix)); //$NON-NLS-1$
      }
   }
   
   /**
    * Writes out all extension attributes found on the def.
    * 
    * @param aBaseDef
    * @param aElement
    */
   protected void writeExtensionAttributes(AeBaseDef aBaseDef, Element aElement)
   {
      for (Iterator iter = aBaseDef.getExtensionAttributeDefs(); iter.hasNext(); )
      {
         AeExtensionAttributeDef extAttribute = (AeExtensionAttributeDef) iter.next();
         aElement.setAttributeNS(extAttribute.getNamespace(), extAttribute.getQualifiedName(), extAttribute.getValue());
      }
   }

   /**
    * Sets (writes) an attribute to the Element.
    *
    * @param aAttrName
    * @param aAttrValue
    */
   protected void setAttribute( String aAttrName, String aAttrValue )
   {
      if (aAttrValue != null && aAttrValue.length() > 0)
      {
         getElement().setAttributeNS(null, aAttrName, aAttrValue);
      }
   }

   /**
    * Convenience method for adding a qualified attribute (by name) to the current context
    * element.  A prefix is found or created for the attribute, and used as the qualified
    * name of it.
    * 
    * @param aMutableNSContext
    * @param aAttrNamespace
    * @param aAttrName
    * @param aAttrValue
    */
   protected void setAttributeNS(IAeMutableNamespaceContext aMutableNSContext, String aAttrNamespace,
         String aPreferredPrefix, String aAttrName, String aAttrValue)
   {
      if (AeUtil.notNullOrEmpty(aAttrValue))
      {
         if (AeUtil.isNullOrEmpty(aAttrNamespace))
         {
            getElement().setAttributeNS(null, aAttrName, aAttrValue);
         }
         else
         {
            String prefix = aMutableNSContext.getOrCreatePrefixForNamespace(aPreferredPrefix, aAttrNamespace);
            getElement().setAttributeNS(aAttrNamespace, prefix + ":" + aAttrName, aAttrValue); //$NON-NLS-1$
         }
      }
   }
   
   /**
    * Facilitates setting a boolean attribute within the current context Element.
    * The aRequired flag will suppress the attribute generation if set to FALSE and if
    * the aAttrValue is false as well.
    *
    * @param aAttrName the name of the attribute we are setting
    * @param aValue the value of the attribute we are setting
    * @param aRequired flag indicating if the attribute is required in the document
    */
   protected void setAttribute(String aAttrName, boolean aValue, boolean aRequired)
   {
      if (aRequired || (!aRequired && aValue))
      {
         setAttribute(aAttrName, (aValue ? ATTR_YES : ATTR_NO));
      }
   }

   /**
    * Facilitates setting a boolean attribute within the element node which was passed in.
    * The aRequired flag will suppress the attribute generation if set to FALSE and if
    * the aAttrValue is false as well.
    *
    * @param aMutableNSContext
    * @param aAttrNamespace the namespace of the attribute we are setting
    * @param aPreferredPrefix the preferred prefix to use when qualifying the attribute name
    * @param aAttrName the name of the attribute we are setting
    * @param aValue the value of the attribute we are setting
    * @param aRequired flag indicating if the attribute is required in the document
    */
   protected void setAttributeNS(IAeMutableNamespaceContext aMutableNSContext, String aAttrNamespace,
         String aPreferredPrefix, String aAttrName, boolean aValue, boolean aRequired)
   {
      // Set the attribute if it is required OR if it is optional and
      // value is TRUE.  Otherwise no need to set it.
      if (aRequired || (!aRequired && aValue))
      {
         setAttributeNS(aMutableNSContext, aAttrNamespace, aPreferredPrefix, aAttrName, (aValue ? ATTR_YES : ATTR_NO));
      }
   }

   /**
    * Sets (writes) a QName attribute to the Element.
    *
    * @param aAttributeName
    * @param aQName
    */
   protected void setAttribute(String aAttributeName, QName aQName)
   {
      AeXmlUtil.setAttributeQName(getElement(), aAttributeName, aQName);
   }

   /**
    * Utility method for creating a NS aware DOM element.
    * The element will be created with the default BPEL namespace.
    *
    * @param aParentElement used to get a reference to the owner doc
    * @param aNamespace the namespace of the element we're creating
    * @param aName the element tag name
    * @return the newly created element
    */
   protected Element createElement(Element aParentElement, String aNamespace, String aName)
   {
      Element element = null;
      if (aParentElement == null)
      {
         element = createProcessElement(aNamespace, aName);
      }
      else
      {
         String currentDefaultNS = AeXmlUtil.findDefaultNamespace(aParentElement);
         if (!AeUtil.compareObjects(currentDefaultNS, aNamespace))
         {
            // if the element we're writing is in a namespace other than the bpel
            // namespace then we want to write the element with a prefix

            // check to see if there is a prefix already available for this namespace
            String prefix = AeXmlUtil.getOrCreatePrefix(aParentElement, aNamespace);

            // if we have a non-null prefix then create the element with that prefix
            String elementName = AeUtil.notNullOrEmpty(prefix) ? prefix + ":" + aName : aName; //$NON-NLS-1$
            element = aParentElement.getOwnerDocument().createElementNS(aNamespace, elementName);
         }
         else
         {
            element = aParentElement.getOwnerDocument().createElementNS(aNamespace, aName);
         }
      }
      return element;
   }

   /**
    * Create the xml doc first, then the process element. Appends the process element to the doc and returns
    * the process element.
    *
    * @param aNamespace
    * @param aName
    * @return process element
    */
   protected Element createProcessElement(String aNamespace, String aName)
   {
      Document doc = AeXmlUtil.newDocument();
      String elementName = aName;
      String defaultNS = getDef().getDefaultNamespace();
      if (!AeUtil.compareObjects(defaultNS, aNamespace))
      {
         String prefix = new AeBaseDefNamespaceContext(getDef()).getOrCreatePrefixForNamespace(
               IAeBPELConstants.WSBPEL_2_0_PREFIX, aNamespace, true);
         elementName = prefix + ":" + aName; //$NON-NLS-1$
      }
      Element element = doc.createElementNS(aNamespace, elementName);
      doc.appendChild(element);
      return element;
   }

   /**
    * Visits an expression base def in order to write out the expressionLanguage attribute
    * and the value of the expression.
    *
    * @param aDef
    */
   protected void writeExpressionDef(AeExpressionBaseDef aDef)
   {
      writeExpressionLang(aDef);
      if (AeUtil.notNullOrEmpty(aDef.getExpression()))
      {
         Text textNode = getElement().getOwnerDocument().createTextNode(aDef.getExpression());
         getElement().appendChild(textNode);
      }
   }

   /**
    * Method used to write out expression language for expression conditions
    * @param aDef Expression Definition object
    */
   protected void writeExpressionLang(IAeExpressionDef aDef)
   {
      setAttribute(TAG_EXPRESSION_LANGUAGE, aDef.getExpressionLanguage());
   }

   /**
    * @return Returns the def.
    */
   protected AeBaseDef getDef()
   {
      return mDef;
   }

   /**
    * @param aDef the def to set
    */
   protected void setDef(AeBaseDef aDef)
   {
      mDef = aDef;
   }
}
