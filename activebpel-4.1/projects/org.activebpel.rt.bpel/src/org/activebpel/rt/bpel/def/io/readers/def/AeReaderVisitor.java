// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/io/readers/def/AeReaderVisitor.java,v 1.39 2007/09/12 02:48:1
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
package org.activebpel.rt.bpel.def.io.readers.def;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.namespace.QName;

import org.activebpel.rt.IAeConstants;
import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.bpel.def.AeActivityDef;
import org.activebpel.rt.bpel.def.AeActivityPartnerLinkBaseDef;
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
import org.activebpel.rt.bpel.def.IAeCatchParentDef;
import org.activebpel.rt.bpel.def.IAeCompensationHandlerParentDef;
import org.activebpel.rt.bpel.def.IAeCorrelationSetsParentDef;
import org.activebpel.rt.bpel.def.IAeCorrelationsParentDef;
import org.activebpel.rt.bpel.def.IAeEventHandlersParentDef;
import org.activebpel.rt.bpel.def.IAeFaultHandlersParentDef;
import org.activebpel.rt.bpel.def.IAeFromParentDef;
import org.activebpel.rt.bpel.def.IAeMessageExchangesParentDef;
import org.activebpel.rt.bpel.def.IAeMultipleActivityContainerDef;
import org.activebpel.rt.bpel.def.IAePartnerLinkParentDef;
import org.activebpel.rt.bpel.def.IAePartnerLinksParentDef;
import org.activebpel.rt.bpel.def.IAeSingleActivityContainerDef;
import org.activebpel.rt.bpel.def.IAeVariablesParentDef;
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
import org.activebpel.rt.bpel.def.activity.IAeAlarmParentDef;
import org.activebpel.rt.bpel.def.activity.IAeEventContainerDef;
import org.activebpel.rt.bpel.def.activity.IAeMessageContainerDef;
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
import org.activebpel.rt.bpel.def.io.AeCorrelationPatternIOFactory;
import org.activebpel.rt.bpel.def.io.IAeCorrelationPatternIO;
import org.activebpel.rt.bpel.def.io.ext.AeExtensionElementDef;
import org.activebpel.rt.bpel.def.io.writers.AeCorrelationSetUtil;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.activebpel.rt.xml.AeXMLParserBase;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Responsible for setting all of the properties on the newly created AeDef objects 
 * from the current xml element and adding then def to their parent def.
 */
public abstract class AeReaderVisitor implements IAeReaderDefVisitor, IAeBPELConstants
{
   /** list of errors encountered during parse */
   private List mErrors;
   /** current element being read */
   private Element mCurrentElement;
   /** Parent def object */
   private AeBaseDef mParentDef;
   /** A set of consumed attributes. */
   private Set mConsumedAttributes = new HashSet();

   /**
    * Constructor
    * @param aParentDef child will be added to this
    * @param aElement current element to read from
    */
   public AeReaderVisitor( AeBaseDef aParentDef, Element aElement )
   {
      mParentDef = aParentDef;
      mCurrentElement = aElement;
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.io.readers.def.IAeErrorReportingDefVisitor#hasErrors()
    */
   public boolean hasErrors()
   {
      return mErrors != null;
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.io.readers.def.IAeErrorReportingDefVisitor#getErrors()
    */
   public List getErrors()
   {
      if (mErrors == null)
         mErrors = new ArrayList();
      return mErrors;
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityAssignDef)
    */
   public void visit(AeActivityAssignDef aDef)
   {
      readAttributes(aDef);
      addActivityToParent(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityCompensateDef)
    */
   public void visit(AeActivityCompensateDef aDef)
   {
      readAttributes(aDef);
      addActivityToParent(aDef);
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
      readAttributes(aDef);
      addActivityToParent(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityContinueDef)
    */
   public void visit(AeActivityContinueDef aDef)
   {
      readAttributes(aDef);
      addActivityToParent(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityBreakDef)
    */
   public void visit(AeActivityBreakDef aDef)
   {
      readAttributes(aDef);
      addActivityToParent(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityFlowDef)
    */
   public void visit(AeActivityFlowDef aDef)
   {
      readAttributes(aDef);
      addActivityToParent(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityInvokeDef)
    */
   public void visit(AeActivityInvokeDef aDef)
   {
      readAttributes(aDef);
      aDef.setInputVariable(getAttribute(TAG_INPUT_VARIABLE));
      aDef.setOutputVariable(getAttribute(TAG_OUTPUT_VARIABLE));

      addActivityToParent(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityPickDef)
    */
   public void visit(AeActivityPickDef aDef)
   {
      readAttributes(aDef);
      aDef.setCreateInstance(getAttributeBoolean(TAG_CREATE_INSTANCE));
      addActivityToParent(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityReceiveDef)
    */
   public void visit(AeActivityReceiveDef aDef)
   {
      readAttributes(aDef);
      aDef.setVariable(getAttribute(TAG_VARIABLE));
      aDef.setCreateInstance(getAttributeBoolean(TAG_CREATE_INSTANCE));
      aDef.setMessageExchange(getMessageExchangeValue());
      addActivityToParent(aDef);
   }

   /**
    * Reads the namespace qualified attribute for message exchange.
    */
   protected abstract String getMessageExchangeValue();

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityReplyDef)
    */
   public void visit(AeActivityReplyDef aDef)
   {
      readAttributes(aDef);
      aDef.setVariable(getAttribute(TAG_VARIABLE));
      aDef.setFaultName(getAttributeQName(TAG_FAULT_NAME));
      aDef.setMessageExchange(getMessageExchangeValue());
      addActivityToParent(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivitySuspendDef)
    */
   public void visit(AeActivitySuspendDef aDef)
   {
      readAttributes(aDef);
      aDef.setVariable(getAttribute(TAG_VARIABLE));
      addActivityToParent(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityScopeDef)
    */
   public void visit(AeActivityScopeDef aDef)
   {
      readAttributes(aDef);
      addActivityToParent(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivitySequenceDef)
    */
   public void visit(AeActivitySequenceDef aDef)
   {
      readAttributes(aDef);
      addActivityToParent(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityExitDef)
    */
   public void visit(AeActivityExitDef aDef)
   {
      readAttributes(aDef);
      addActivityToParent(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityThrowDef)
    */
   public void visit(AeActivityThrowDef aDef)
   {
      readAttributes(aDef);
      aDef.setFaultName(getAttributeQName(TAG_FAULT_NAME));
      aDef.setFaultVariable(getAttribute(TAG_FAULT_VARIABLE));
      addActivityToParent(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityWaitDef)
    */
   public void visit(AeActivityWaitDef aDef)
   {
      readAttributes(aDef);

      addActivityToParent(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityWhileDef)
    */
   public void visit(AeActivityWhileDef aDef)
   {
      readAttributes(aDef);
      addActivityToParent(aDef);
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
      readAttributes(aDef);

      aDef.setCounterName(getAttribute(TAG_FOREACH_COUNTERNAME));
      aDef.setParallel(getAttributeBoolean(TAG_FOREACH_PARALLEL));
      
      addActivityToParent(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeForEachCompletionConditionDef)
    */
   public void visit(AeForEachCompletionConditionDef aDef)
   {
      readAttributes(aDef);
      ((AeActivityForEachDef)getParentDef()).setCompletionCondition(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeForEachFinalDef)
    */
   public void visit(AeForEachFinalDef aDef)
   {
      readAttributes(aDef);
      readExpressionDef((AeExpressionBaseDef) aDef);
      ((AeActivityForEachDef)getParentDef()).setFinalDef(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeForEachStartDef)
    */
   public void visit(AeForEachStartDef aDef)
   {
      readAttributes(aDef);
      readExpressionDef((AeExpressionBaseDef) aDef);
      ((AeActivityForEachDef)getParentDef()).setStartDef(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeForEachBranchesDef)
    */
   public void visit(AeForEachBranchesDef aDef)
   {
      readAttributes(aDef);
      readExpressionDef((AeExpressionBaseDef) aDef);
      ((AeForEachCompletionConditionDef)getParentDef()).setBranches(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeAssignCopyDef)
    */
   public void visit(AeAssignCopyDef aDef)
   {
      readAttributes(aDef);            
      ((AeActivityAssignDef)getParentDef()).addCopyDef(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeFromDef)
    */
   public void visit(AeFromDef aDef)
   {
      readAttributes(aDef);
      ((IAeFromParentDef)getParentDef()).setFromDef(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeToDef)
    */
   public void visit(AeToDef aDef)
   {
      readAttributes(aDef);
      ((AeAssignCopyDef)getParentDef()).setToDef(aDef);
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
      readAttributes(aDef);
      ((IAeCorrelationsParentDef) getParentDef()).setCorrelationsDef(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeCorrelationDef)
    */
   public void visit(AeCorrelationDef aDef)
   {
      readAttributes(aDef);
      aDef.setCorrelationSetName(getAttribute(TAG_SET));
      aDef.setInitiate(getAttribute(TAG_INITIATE));
      String pattern = getAttribute(TAG_PATTERN);
      if (AeUtil.notNullOrEmpty(pattern))
      {
         IAeCorrelationPatternIO patternIO = AeCorrelationPatternIOFactory.getInstance(getCurrentElement().getNamespaceURI());
         aDef.setPattern(patternIO.fromString(pattern));
      }
      ((AeCorrelationsDef)getParentDef()).addCorrelationDef(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AeCorrelationSetsDef)
    */
   public void visit(AeCorrelationSetsDef aDef)
   {
      readAttributes(aDef);
      
      ((IAeCorrelationSetsParentDef) getParentDef()).setCorrelationSetsDef(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AeCorrelationSetDef)
    */
   public void visit(AeCorrelationSetDef aDef)
   {
      readAttributes(aDef);
      String rawProperties = getAttribute(TAG_PROPERTIES);
      AeCorrelationSetUtil.addProperties(aDef, rawProperties, getCurrentElement() );
      ((AeCorrelationSetsDef)getParentDef()).addCorrelationSetDef(aDef);
   }
   

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AeCatchAllDef)
    */
   public void visit(AeCatchAllDef aDef)
   {
      readAttributes(aDef);
      ((IAeCatchParentDef)getParentDef()).setCatchAllDef(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AeEventHandlersDef)
    */
   public void visit(AeEventHandlersDef aDef)
   {
      readAttributes(aDef);
      ((IAeEventHandlersParentDef) getParentDef()).setEventHandlers(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AeCatchDef)
    */
   public void visit(AeCatchDef aDef)
   {
      readAttributes(aDef);
      aDef.setFaultName(getAttributeQName(TAG_FAULT_NAME));
      aDef.setFaultVariable(getAttribute(TAG_FAULT_VARIABLE));

      // could be adding fault handler to an invoke activity or
      // directly to a fault handler container element
      ((IAeCatchParentDef)getParentDef()).addCatchDef(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AeFaultHandlersDef)
    */
   public void visit(AeFaultHandlersDef aDef)
   {
      readAttributes(aDef);
      
      ((IAeFaultHandlersParentDef) getParentDef()).setFaultHandlersDef(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeLinksDef)
    */
   public void visit(AeLinksDef aDef)
   {
      readAttributes(aDef);
      
      ((AeActivityFlowDef) getParentDef()).setLinksDef(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeLinkDef)
    */
   public void visit(AeLinkDef aDef)
   {
      readAttributes( aDef );
      ((AeLinksDef)getParentDef()).addLinkDef(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AeMessageExchangesDef)
    */
   public void visit(AeMessageExchangesDef aDef)
   {
      readAttributes( aDef );

      ((IAeMessageExchangesParentDef)getParentDef()).setMessageExchangesDef(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AeMessageExchangeDef)
    */
   public void visit(AeMessageExchangeDef aDef)
   {
      readAttributes( aDef );

      aDef.setName(getAttribute(TAG_NAME));
      ((AeMessageExchangesDef) getParentDef()).addMessageExchangeDef(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeOnAlarmDef)
    */
   public void visit(AeOnAlarmDef aDef)
   {
      readAttributes(aDef);

      // could be pick activity or event handler
      ((IAeAlarmParentDef)getParentDef()).addAlarmDef(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeOnMessageDef)
    */
   public void visit(AeOnMessageDef aDef)
   {
      readAttributes(aDef);
      readOnMessageOrOnEventAttributes(aDef);

      ((IAeMessageContainerDef) getParentDef()).addOnMessageDef(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeOnEventDef)
    */
   public void visit(AeOnEventDef aDef)
   {
      readAttributes(aDef);
      readOnMessageOrOnEventAttributes(aDef);
      
      aDef.setMessageType(getAttributeQName(TAG_MESSAGE_TYPE));
      aDef.setElement(getAttributeQName(TAG_ELEMENT));

      ((IAeEventContainerDef) getParentDef()).addOnEventDef(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AePartnerDef)
    */
   public void visit(AePartnerDef aDef)
   {
      readAttributes(aDef);

      ((AePartnersDef)getParentDef()).addPartnerDef(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AePartnerLinkDef)
    */
   public void visit(AePartnerLinkDef aDef)
   {
      readAttributes(aDef);
      aDef.setPartnerLinkTypeName(getAttributeQName(TAG_PARTNER_LINK_TYPE));
      aDef.setMyRole(getAttribute(TAG_MY_ROLE));
      aDef.setPartnerRole(getAttribute(TAG_PARTNER_ROLE));

      // parent could be partnerLinks container def or partner def
      ((IAePartnerLinkParentDef)getParentDef()).addPartnerLinkDef( aDef );
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AePartnerLinksDef)
    */
   public void visit(AePartnerLinksDef aDef)
   {
      readAttributes(aDef);
      
      ((IAePartnerLinksParentDef) getParentDef()).setPartnerLinksDef(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AePartnersDef)
    */
   public void visit(AePartnersDef aDef)
   {
      readAttributes(aDef);
      
      ((AeProcessDef) getParentDef()).setPartnersDef(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AeProcessDef)
    */
   public void visit(AeProcessDef aDef)
   {
      readAttributes(aDef);
      aDef.setNamespace(getCurrentElement().getNamespaceURI());
      aDef.setTargetNamespace(getAttribute(TAG_TARGET_NAMESPACE));
      aDef.setQueryLanguage(getAttribute(TAG_QUERY_LANGUAGE));
      aDef.setExpressionLanguage(getAttribute(TAG_EXPRESSION_LANGUAGE));
      aDef.setSuppressJoinFailure(getAttributeBoolean(TAG_SUPPRESS_JOIN_FAILURE));
      aDef.setEnableInstanceCompensation(getAttributeBoolean(TAG_ENABLE_INSTANCE_COMPENSATION));
      
      // process def doesn't need to be added to anyone
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AeCompensationHandlerDef)
    */
   public void visit(AeCompensationHandlerDef aDef)
   {
      readAttributes(aDef);
      
      ((IAeCompensationHandlerParentDef) getParentDef()).setCompensationHandlerDef(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AeScopeDef)
    */
   public void visit(AeScopeDef aDef)
   {
      throw new UnsupportedOperationException(AeMessages.getString("AeReaderVisitor.ERROR_0")); //$NON-NLS-1$
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeSourcesDef)
    */
   public void visit(AeSourcesDef aDef)
   {
      throw new UnsupportedOperationException();
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeSourceDef)
    */
   public void visit(AeSourceDef aDef)
   {
      readAttributes(aDef);
      aDef.setLinkName(getAttribute(TAG_LINK_NAME));
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeTargetsDef)
    */
   public void visit(AeTargetsDef aDef)
   {
      throw new UnsupportedOperationException();
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeTargetDef)
    */
   public void visit(AeTargetDef aDef)
   {
      readAttributes(aDef);
      aDef.setLinkName(getAttribute(TAG_LINK_NAME));
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AeVariableDef)
    */
   public void visit(AeVariableDef aDef)
   {
      readAttributes(aDef);
      aDef.setMessageType(getAttributeQName(TAG_MESSAGE_TYPE));
      aDef.setType(getAttributeQName(TAG_TYPE));
      aDef.setElement(getAttributeQName(TAG_ELEMENT));
      ((AeVariablesDef)getParentDef()).addVariableDef(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AeVariablesDef)
    */
   public void visit(AeVariablesDef aDef)
   {
      readAttributes(aDef);
      
      ((IAeVariablesParentDef) getParentDef()).setVariablesDef(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityValidateDef)
    */
   public void visit(AeActivityValidateDef aDef)
   {
      throw new UnsupportedOperationException();
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
      throw new UnsupportedOperationException();
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeTransitionConditionDef)
    */
   public void visit(AeTransitionConditionDef aDef)
   {
      throw new UnsupportedOperationException();
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeForDef)
    */
   public void visit(AeForDef aDef)
   {
      throw new UnsupportedOperationException();
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeUntilDef)
    */
   public void visit(AeUntilDef aDef)
   {
      throw new UnsupportedOperationException();
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
      throw new UnsupportedOperationException();
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
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeRepeatEveryDef)
    */
   public void visit(AeRepeatEveryDef aDef)
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
      throw new UnsupportedOperationException();
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
      throw new UnsupportedOperationException();
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.io.ext.AeExtensionElementDef)
    */
   public void visit(AeExtensionElementDef aDef)
   {
      throw new UnsupportedOperationException();
   }
   
   /**
    * Reads the common attributes (namespace decls, extension attributes).
    * 
    * @param aCurrentDef
    */
   protected void readCommonAttributes(AeBaseDef aCurrentDef)
   {
      readNamespaceDeclarations(aCurrentDef);
   }

   /**
    * Set properties on the def object according to its type.
    * @param aCurrentDef
    */
   protected void readAttributes(AeActivityPartnerLinkBaseDef aCurrentDef)
   {
      readCommonAttributes(aCurrentDef);
      readNameAttributes(aCurrentDef);
      readActivityAttributes(aCurrentDef);

      aCurrentDef.setPartnerLink(getAttribute(TAG_PARTNER_LINK));
      aCurrentDef.setPortType(getAttributeQName(TAG_PORT_TYPE));
      aCurrentDef.setOperation(getAttribute(TAG_OPERATION));
   }

   /**
    * Set properties on the def object according to its type.
    * @param aCurrentDef
    */
   protected void readAttributes( AeActivityDef aCurrentDef )
   {
      readCommonAttributes(aCurrentDef);
      readNameAttributes(aCurrentDef);
      readActivityAttributes(aCurrentDef);
   }

   /**
    * Set properties on the def object according to its type.
    * @param aCurrentDef
    */
   protected void readAttributes( AeNamedDef aCurrentDef )
   {
      readCommonAttributes(aCurrentDef);
      readNameAttributes(aCurrentDef);
   }

   /**
    * Sets properties on the assign var def.
    * 
    * @param aVarDef
    */
   protected void readAssignVarDefAttributes(AeVarDef aVarDef)
   {
      readCommonAttributes(aVarDef);
      aVarDef.setVariable(getAttribute(TAG_VARIABLE));
      aVarDef.setProperty(getAttributeQName(TAG_PROPERTY));
      aVarDef.setPart(getAttribute(TAG_PART));
      aVarDef.setPartnerLink(getAttribute(TAG_PARTNER_LINK));
   }

   /**
    * Set properties on the def object according to its type.
    * 
    * @param aToDef
    */
   protected void readAttributes( AeToDef aToDef )
   {
      readAssignVarDefAttributes(aToDef);
   }

   /**
    * Set properties on the def object according to its type.
    * 
    * @param aFromDef
    */
   protected void readAttributes( AeFromDef aFromDef )
   {
      readAssignVarDefAttributes(aFromDef);
      aFromDef.setEndpointReference(getAttribute(TAG_ENDPOINT_REFERENCE));
   }

   /**
    * Clones the element passed in. We don't want to use the standard <code>clone()</code>
    * method since the resulting document literal element would have the bpel namespace.
    * As such, this method needs to make an exact copy of the element but using the null
    * namespace.  In order to do this, we'll make a custom "deep" clone of the element by
    * walking all of its children and recreating them in a new Document.
    * 
    * @param aElem
    */
   protected Element cloneElement(Element aElem)
   {
      try
      {
         Document doc = AeXmlUtil.getDocumentBuilder(true, false).newDocument();
         Element rootElem = (Element) doc.importNode(aElem, false);
         String prefix = aElem.getPrefix();
         if (!AeUtil.isNullOrEmpty(prefix))
         {
            String ns = AeXmlUtil.getNamespaceForPrefix(aElem, prefix);
            rootElem.setAttributeNS(IAeConstants.W3C_XMLNS, "xmlns:" + prefix, ns); //$NON-NLS-1$
         }
         AeXmlUtil.copyNodeContents(aElem, rootElem);
         return rootElem;
      }
      catch (Exception e)
      {
         /*
          * There shouldn't be any exception thrown here since the element is obviously
          * already well formed (or it wouldn't be in a DOM) so there shouldn't be any
          * issue reading it back in from a string.
          */
         throw new InternalError(AeMessages.getString("AeReaderVisitor.ERROR_3")); //$NON-NLS-1$
      }
   }

   /**
    * Reads the standard attributes from the current element and sets them on
    * the given def object.
    * 
    * @param aCurrentDefObj
    */
   protected void readAttributes(AeBaseDef aCurrentDefObj)
   {
      readCommonAttributes(aCurrentDefObj);
   }

   /**
    * Reads the attributes of an onMessage or an onEvent.
    * 
    * @param aDef
    */
   protected void readOnMessageOrOnEventAttributes(AeOnMessageDef aDef)
   {
      aDef.setPartnerLink(getAttribute(TAG_PARTNER_LINK));
      aDef.setPortType(getAttributeQName(TAG_PORT_TYPE));
      aDef.setOperation(getAttribute(TAG_OPERATION));
      aDef.setVariable(getAttribute(TAG_VARIABLE));
      aDef.setMessageExchange(getMessageExchangeValue());
   }

   /**
    * Read in attributes for activity def.
    * @param aCurrentDef the activity def object
    */
   protected void readActivityAttributes(AeActivityDef aCurrentDef)
   {
      aCurrentDef.setSuppressFailure(getAttributeBoolOptional(TAG_SUPPRESS_FAILURE));
   }

   /**
    * Read in attributes for names def.
    * @param aCurrentDef the named def object
    */
   protected void readNameAttributes(AeNamedDef aCurrentDef)
   {
      aCurrentDef.setName( getAttribute(TAG_NAME) );
   }

   /**
    * Given the current node element in a DOM tree, this method will read the
    * attributes into memory. This base element is responsible for obtaining
    * a map of all namespaces referenced in the node element.
    * 
    * @param aCurrentDefObj the current node within the DOM tree
    */
   protected void readNamespaceDeclarations(AeBaseDef aCurrentDefObj)
   {
      if (getCurrentElement().hasAttributes())
      {
         // Loop through and add all attributes which are part of the xmlns to the map
         NamedNodeMap attrNodes = getCurrentElement().getAttributes();
         for (int i = 0, length = attrNodes.getLength(); i < length; i++)
         {
            Attr attr = (Attr) attrNodes.item(i);
            if (IAeConstants.W3C_XMLNS.equals(attr.getNamespaceURI()))
            {
               String prefix = attr.getLocalName();
               String namespaceURI = attr.getNodeValue();
               aCurrentDefObj.addNamespace(prefix, namespaceURI);
               getConsumedAttributes().add(attr);
            }
         }
      }
   }

   /**
    * Convenience method for accessing an attribute from
    * the current context element.
    * @param aAttributeName
    * @return the attribute value
    */
   protected String getAttribute( String aAttributeName )
   {
      return getAttributeNS(null, aAttributeName); 
   }

   /**
    * Convenience method for accessing a qualified attribute from
    * the current context element.
    * 
    * @param aAttributeNamespace
    * @param aAttributeName
    * @return the attribute value
    */
   protected String getAttributeNS( String aAttributeNamespace, String aAttributeName )
   {
      Attr attr = getCurrentElement().getAttributeNodeNS(aAttributeNamespace, aAttributeName);
      if (attr != null)
      {
         getConsumedAttributes().add(attr);
         return attr.getValue();
      }
      else
      {
         return ""; //$NON-NLS-1$
      }
   }

   /**
    * Convenience method for accessing an attribute QName from
    * the current context element.
    * @param aAttributeName
    * @return the QName
    */
   protected QName getAttributeQName( String aAttributeName )
   {
      if (getCurrentElement().hasAttribute(aAttributeName))
      {
         return AeXmlUtil.createQName(getCurrentElement(), getAttribute(aAttributeName));
      }
      else
         return null;
   }
   
   /**
    * Convenience method for accessing an attribute with a
    * boolean value from the current context element.
    * @param aAttributeName
    * @return boolean flag to indicate attribute state 
    */
   protected boolean getAttributeBoolean( String aAttributeName )
   {
      return ATTR_YES.equalsIgnoreCase(getAttribute(aAttributeName));
   }
   
   /**
    * Convenience method for accessing a qualified attribute with a boolean value from
    * the current context element.
    * 
    * @param aAttributeNamespace
    * @param aAttributeName
    */
   protected boolean getAttributeBooleanNS( String aAttributeNamespace, String aAttributeName )
   {
      return ATTR_YES.equalsIgnoreCase(getAttributeNS(aAttributeNamespace, aAttributeName));
   }

   /**
    * Facilitates the retrieval of a boolean attribute from the current element 
    * This method makes provisions for attributes which may be null, in which 
    * case a null object is returned.
    * 
    * @param aAttributeName the name of the attribute we are looking for
    * @return Boolean flag to indicate attribute state
    */
   protected Boolean getAttributeBoolOptional(String aAttributeName)
   {
      Boolean flag = null;
      String attr = getAttribute(aAttributeName);
      if (attr.length() > 0)
         flag = new Boolean(ATTR_YES.equalsIgnoreCase(attr));
      
      return flag;
   }

   /**
    * Adds the activity def to the context parent def object.
    * @param aChildDef
    */
   protected void addActivityToParent(AeActivityDef aChildDef)
   {
       if( getParentDef() instanceof IAeMultipleActivityContainerDef )
       {
          ((IAeMultipleActivityContainerDef)getParentDef()).addActivityDef(aChildDef);   
       }
       else if( getParentDef() instanceof IAeSingleActivityContainerDef )
       {
          IAeSingleActivityContainerDef parent = (IAeSingleActivityContainerDef)getParentDef();
          if (parent.getActivityDef() == null)
          {
             parent.setActivityDef(aChildDef);
          }
          else
          {
             getErrors().add(AeMessages.format("AeReaderVisitor.ErrorAddingChild", AeXMLParserBase.documentToString(getCurrentElement()))); //$NON-NLS-1$
          }
       }
       else
       {
          getErrors().add(AeMessages.format("AeReaderVisitor.ErrorInRegistry", AeXMLParserBase.documentToString(getCurrentElement()))); //$NON-NLS-1$
       }
    }

   /**
    * Convenience method for accessing the current element.
    * @return current element being read
    */
   protected Element getCurrentElement()
   {
      return mCurrentElement;
   }
   
   /**
    * Convenience method for accessing the parent def object.
    * @return parent def object
    */
   protected AeBaseDef getParentDef()
   {
      return mParentDef;
   }

   /**
    * Visits an expression base def in order to read the expression language and expression value.
    * 
    * @param aDef
    */
   protected void readExpressionDef(AeExpressionBaseDef aDef)
   {
      aDef.setExpressionLanguage(getAttribute(TAG_EXPRESSION_LANGUAGE));
      aDef.setExpression(AeXmlUtil.getText(getCurrentElement()));
   }

   /**
    * Adds the children of the given Element to the literal def.  Returns true if any children
    * were added to the literal def.
    * 
    * @param aElement
    * @param aLiteralDef
    */
   protected boolean addChildrenToLiteral(Element aElement, AeLiteralDef aLiteralDef)
   {
      int numChildren = 0;
      aElement.normalize();
      if (aElement.hasChildNodes())
      {
         NodeList children = aElement.getChildNodes();
         for (int i = 0; i < children.getLength(); i++)
         {
            Node child = children.item(i);
            short type = child.getNodeType();
            if (type == Node.TEXT_NODE)
            {
               if (AeUtil.notNullOrEmpty(child.getNodeValue()))
               {
                  aLiteralDef.addChildNode(child);
                  numChildren++;
               }
            }
            else if (type == Node.ELEMENT_NODE || type == Node.CDATA_SECTION_NODE)
            {
               aLiteralDef.addChildNode(child);
               numChildren++;
            }
         }
      }
      else
      {
         aLiteralDef.addChildNode(aElement.getOwnerDocument().createTextNode("")); //$NON-NLS-1$
      }
      return numChildren > 0;
   }

   /**
    * @return Returns the consumedAttributes.
    */
   public Set getConsumedAttributes()
   {
      return mConsumedAttributes;
   }

   /**
    * @param aConsumedAttributes The consumedAttributes to set.
    */
   protected void setConsumedAttributes(Set aConsumedAttributes)
   {
      mConsumedAttributes = aConsumedAttributes;
   }
}
