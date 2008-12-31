// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/io/readers/def/AeWSBPELReaderVisitor.java,v 1.12 2007/09/12 02:48:1
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

import org.activebpel.rt.IAeConstants;
import org.activebpel.rt.bpel.def.*;
import org.activebpel.rt.bpel.def.activity.*;
import org.activebpel.rt.bpel.def.activity.support.*;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import javax.xml.namespace.QName;

/**
 * Implements a WS-BPEL 2.0 version of the def reader visitor.
 */
public class AeWSBPELReaderVisitor extends AeReaderVisitor
{
   /**
    * Constructor.
    */
   public AeWSBPELReaderVisitor(AeBaseDef aParentDef, Element aElement)
   {
      super(aParentDef, aElement);
   }

   /**
    * Reads the namespace qualified attribute for message exchange.
    */
   protected String getMessageExchangeValue()
   {
      return getAttribute(TAG_MESSAGE_EXCHANGE);
   }

   /**
    * @see org.activebpel.rt.bpel.def.io.readers.def.AeReaderVisitor#readAttributes(org.activebpel.rt.bpel.def.activity.support.AeFromDef)
    */
   protected void readAttributes(AeFromDef aFromDef)
   {
      super.readAttributes(aFromDef);

      aFromDef.setExpressionLanguage(getAttribute(TAG_EXPRESSION_LANGUAGE));
      aFromDef.setExpression(AeXmlUtil.getText(getCurrentElement()));
   }

   /**
    * @see org.activebpel.rt.bpel.def.io.readers.def.AeReaderVisitor#readAttributes(org.activebpel.rt.bpel.def.activity.support.AeToDef)
    */
   protected void readAttributes(AeToDef aToDef)
   {
      super.readAttributes(aToDef);
      
      aToDef.setExpressionLanguage(getAttribute(TAG_EXPRESSION_LANGUAGE));
      aToDef.setExpression(AeXmlUtil.getText(getCurrentElement()));
   }

   /**
    * Overides to use the name space to determine the abstract process.
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AeProcessDef)
    */
   public void visit(AeProcessDef aDef)
   {
      super.visit(aDef);
      
      aDef.setExitOnStandardFault(new Boolean(getAttributeBoolean(TAG_EXIT_ON_STANDARD_FAULT)));
      if (AeUtil.notNullOrEmpty(getAttributeNS(IAeBPELConstants.WSBPEL_2_0_ABSTRACT_NAMESPACE_URI, TAG_ABSTRACT_PROCESS_PROFILE)))
      {
         aDef.setAbstractProcessProfile(getAttributeNS(IAeBPELConstants.WSBPEL_2_0_ABSTRACT_NAMESPACE_URI, TAG_ABSTRACT_PROCESS_PROFILE));
      }
      aDef.setCreateTargetXPath(getAttributeBooleanNS(
            IAeBPELConstants.AE_EXTENSION_NAMESPACE_URI_QUERY_HANDLING, TAG_CREATE_TARGET_XPATH));
      aDef.setDisableSelectionFailure(getAttributeBooleanNS(
            IAeBPELConstants.AE_EXTENSION_NAMESPACE_URI_QUERY_HANDLING, TAG_DISABLE_SELECTION_FAILURE));
      aDef.setStatefulProcess(getAttributeBooleanNS(
            IAeBPELConstants.SBPEL_EXTENSION_NAMESPACE_URI_STATEFUL_PROCESS, TAG_STATEFUL));
   }

   public void visit(AeVariableDef aDef) {
      super.visit(aDef);

      // This should automatically add the stateful attribute to the list of consumed attributes
      aDef.setIsStateful(getAttributeBooleanNS(
         IAeBPELConstants.SBPEL_EXTENSION_NAMESPACE_URI_STATEFUL_PROCESS, TAG_STATEFUL));
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AeImportDef)
    */
   public void visit(AeImportDef aDef)
   {
      readAttributes(aDef);

      aDef.setNamespace(getAttribute(TAG_NAMESPACE));
      aDef.setLocation(getAttribute(TAG_LOCATION));
      aDef.setImportType(getAttribute(TAG_IMPORT_TYPE));

      ((AeProcessDef) getParentDef()).addImportDef(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.io.readers.def.AeReaderVisitor#visit(org.activebpel.rt.bpel.def.AeDocumentationDef)
    */
   public void visit(AeDocumentationDef aDef)
   {
      readAttributes(aDef);

      aDef.setSource(getAttribute(TAG_DOCUMENTATION_SOURCE));
      aDef.setLanguage(getAttributeNS(IAeConstants.W3C_XML_NAMESPACE, TAG_DOCUMENTATION_LANG));

      aDef.setValue(AeXmlUtil.getText(getCurrentElement()));
      getParentDef().addDocumentationDef(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.io.readers.def.AeReaderVisitor#visit(org.activebpel.rt.bpel.def.AePartnerLinkDef)
    */
   public void visit(AePartnerLinkDef aDef)
   {
      super.visit(aDef);
      
      // will set the Boolean or null if not present in xml
      aDef.setInitializePartnerRole(getAttributeBoolOptional((TAG_INITIALIZE_PARTNER_ROLE)));
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.io.readers.def.AeReaderVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeAssignCopyDef)
    */
   public void visit(AeAssignCopyDef aDef)
   {
      super.visit(aDef);
      
      aDef.setKeepSrcElementName(getAttributeBoolean(TAG_KEEP_SRC_ELEMENT_NAME));
      aDef.setIgnoreMissingFromData(getAttributeBoolean(TAG_IGNORE_MISSING_FROM_DATA));
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.io.readers.def.AeReaderVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityAssignDef)
    */
   public void visit(AeActivityAssignDef aDef)
   {
      super.visit(aDef);
      
      aDef.setValidate(getAttributeBoolean(TAG_VALIDATE));
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.io.readers.def.AeReaderVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeExtensibleAssignDef)
    */
   public void visit(AeExtensibleAssignDef aDef)
   {
      readAttributes(aDef);
      
      // TODO (MF) if we don't understand the extensible assign then we should ignore it

      ((AeActivityAssignDef)getParentDef()).addExtensibleAssignDef(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.io.readers.def.AeReaderVisitor#visit(org.activebpel.rt.bpel.def.AeExtensionsDef)
    */
   public void visit(AeExtensionsDef aDef)
   {
      readAttributes(aDef);
      
      ((AeProcessDef) getParentDef()).setExtensionsDef(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.io.readers.def.AeReaderVisitor#visit(org.activebpel.rt.bpel.def.AeExtensionDef)
    */
   public void visit(AeExtensionDef aDef)
   {
      readAttributes(aDef);
      
      aDef.setMustUnderstand(getAttributeBoolean(TAG_MUST_UNDERSTAND));
      aDef.setNamespace(getAttribute(TAG_NAMESPACE));

      ((AeExtensionsDef) getParentDef()).addExtensionDef(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeFromPartsDef)
    */
   public void visit(AeFromPartsDef aDef)
   {
      readAttributes(aDef);

      ((IAeFromPartsParentDef) getParentDef()).setFromPartsDef(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeToPartsDef)
    */
   public void visit(AeToPartsDef aDef)
   {
      readAttributes(aDef);
      
      ((IAeToPartsParentDef) getParentDef()).setToPartsDef(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeFromPartDef)
    */
   public void visit(AeFromPartDef aDef)
   {
      readAttributes(aDef);

      aDef.setPart(getAttribute(TAG_PART));
      aDef.setToVariable(getAttribute(TAG_TO_VARIABLE));

      ((AeFromPartsDef) getParentDef()).addFromPartDef(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeToPartDef)
    */
   public void visit(AeToPartDef aDef)
   {
      readAttributes(aDef);

      aDef.setPart(getAttribute(TAG_PART));
      aDef.setFromVariable(getAttribute(TAG_FROM_VARIABLE));

      ((AeToPartsDef) getParentDef()).addToPartDef(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeSourcesDef)
    */
   public void visit(AeSourcesDef aDef)
   {
      readAttributes(aDef);

      ((AeActivityDef) getParentDef()).setSourcesDef(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeSourceDef)
    */
   public void visit(AeSourceDef aDef)
   {
      super.visit(aDef);
      
      ((AeSourcesDef) getParentDef()).addSourceDef(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeTargetsDef)
    */
   public void visit(AeTargetsDef aDef)
   {
      readAttributes(aDef);
      
      ((AeActivityDef) getParentDef()).setTargetsDef(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeTargetDef)
    */
   public void visit(AeTargetDef aDef)
   {
      super.visit(aDef);
      
      ((AeTargetsDef)getParentDef()).addTargetDef(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.io.readers.def.AeReaderVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeJoinConditionDef)
    */
   public void visit(AeJoinConditionDef aDef)
   {
      readAttributes(aDef);
      
      readExpressionDef((AeExpressionBaseDef) aDef);
      ((AeTargetsDef) getParentDef()).setJoinConditionDef(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.io.readers.def.AeReaderVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeTransitionConditionDef)
    */
   public void visit(AeTransitionConditionDef aDef)
   {
      readAttributes(aDef);

      readExpressionDef((AeExpressionBaseDef) aDef);
      ((AeSourceDef) getParentDef()).setTransitionConditionDef(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.io.readers.def.AeReaderVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeForDef)
    */
   public void visit(AeForDef aDef)
   {
      readAttributes(aDef);

      readExpressionDef((AeExpressionBaseDef) aDef);
      ((IAeForUntilParentDef) getParentDef()).setForDef(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.io.readers.def.AeReaderVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeUntilDef)
    */
   public void visit(AeUntilDef aDef)
   {
      readAttributes(aDef);

      readExpressionDef((AeExpressionBaseDef) aDef);
      ((IAeForUntilParentDef) getParentDef()).setUntilDef(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityIfDef)
    */
   public void visit(AeActivityIfDef aDef)
   {
      readAttributes(aDef);
      addActivityToParent(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.io.readers.def.AeReaderVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeConditionDef)
    */
   public void visit(AeConditionDef aDef)
   {
      readAttributes(aDef);

      readExpressionDef((AeExpressionBaseDef) aDef);
      ((IAeConditionParentDef) getParentDef()).setConditionDef(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeElseDef)
    */
   public void visit(AeElseDef aDef)
   {
      readAttributes(aDef);

      ((AeActivityIfDef) getParentDef()).setElseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeElseIfDef)
    */
   public void visit(AeElseIfDef aDef)
   {
      readAttributes(aDef);

      ((AeActivityIfDef) getParentDef()).addElseIfDef(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeRepeatEveryDef)
    */
   public void visit(AeRepeatEveryDef aDef)
   {
      readAttributes(aDef);
      readExpressionDef((AeExpressionBaseDef) aDef);
      ((AeOnAlarmDef) getParentDef()).setRepeatEveryDef(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityScopeDef)
    */
   public void visit(AeActivityScopeDef aDef)
   {
      super.visit(aDef);
      
      aDef.setIsolated(getAttributeBoolean(TAG_ISOLATED));
      if (AeUtil.notNullOrEmpty (getAttribute(TAG_EXIT_ON_STANDARD_FAULT)) )
      {
         aDef.setExitOnStandardFault(new Boolean(getAttributeBoolean(TAG_EXIT_ON_STANDARD_FAULT)));
      }
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.io.readers.def.AeReaderVisitor#visit(org.activebpel.rt.bpel.def.AeCatchDef)
    */
   public void visit(AeCatchDef aDef)
   {
      super.visit(aDef);
      
      aDef.setFaultMessageType(getAttributeQName(TAG_FAULT_MESSAGE_TYPE));
      aDef.setFaultElementName(getAttributeQName(TAG_FAULT_ELEMENT));

      if (AeUtil.notNullOrEmpty(aDef.getFaultVariable()))
      {
         AeVariableDef varDef = new AeVariableDef();
         varDef.setName(aDef.getFaultVariable());
         if (aDef.getFaultElementName() != null)
         {
            varDef.setElement(aDef.getFaultElementName());
         }
         else if (aDef.getFaultMessageType() != null)
         {
            varDef.setMessageType(aDef.getFaultMessageType());
         }
         aDef.setFaultVariableDef(varDef);
      }
   }

   /**
    * @see org.activebpel.rt.bpel.def.io.readers.def.AeReaderVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityRepeatUntilDef)
    */
   public void visit(AeActivityRepeatUntilDef aDef)
   {
      readAttributes(aDef);
      addActivityToParent(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.io.readers.def.AeReaderVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityValidateDef)
    */
   public void visit(AeActivityValidateDef aDef)
   {
      readAttributes(aDef);
      aDef.setVariables(getAttribute(TAG_VARIABLES));
      addActivityToParent(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.io.readers.def.AeReaderVisitor#visit(AeNotUnderstoodExtensionActivityDef)
    */
   public void visit(AeNotUnderstoodExtensionActivityDef aDef)
   {
      // fixme (MF) make the reading of activity extension def "sufficient" (bpel dom traverser needs to continue traversing)
      QName elemName = new QName(getCurrentElement().getNamespaceURI(), getCurrentElement().getLocalName());
      aDef.setElementName(elemName);

      readAttributes(aDef);
      addActivityToParent(aDef);
      
      // Now read any attributes that might be on the unknown extension activity.
      NamedNodeMap attributes = getCurrentElement().getAttributes();
      for (int i = 0; i < attributes.getLength(); i++)
      {
         Attr attribute = (Attr) attributes.item(i);
         // Skip xmlns: decls.
         if (!IAeConstants.W3C_XMLNS.equals(attribute.getNamespaceURI()))
         {
            QName attrName = new QName(attribute.getNamespaceURI(), attribute.getLocalName());
            aDef.addAttribute(attrName, attribute.getValue());
            getConsumedAttributes().add(attribute);
         }
      }
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.io.readers.def.AeReaderVisitor#visit(org.activebpel.rt.bpel.def.AeExtensionActivityDef)
    */
   public void visit(AeExtensionActivityDef aDef)
   {
      readAttributes(aDef);
      addActivityToParent(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.io.readers.def.AeReaderVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityRethrowDef)
    */
   public void visit(AeActivityRethrowDef aDef)
   {
      readAttributes(aDef);
      addActivityToParent(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.io.readers.def.AeReaderVisitor#visit(org.activebpel.rt.bpel.def.AeTerminationHandlerDef)
    */
   public void visit(AeTerminationHandlerDef aDef)
   {
      readAttributes(aDef);
      
      ((IAeTerminationHandlerParentDef) getParentDef()).setTerminationHandlerDef(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.io.readers.def.AeReaderVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeLiteralDef)
    */
   public void visit(AeLiteralDef aDef)
   {
      readAttributes(aDef);

      addChildrenToLiteral(getCurrentElement(), aDef);

      ((AeFromDef) getParentDef()).setLiteralDef(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.io.readers.def.AeReaderVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeForEachBranchesDef)
    */
   public void visit(AeForEachBranchesDef aDef)
   {
      super.visit(aDef);
      
      aDef.setCountCompletedBranchesOnly(getAttributeBoolean(IAeBPELConstants.TAG_FOREACH_BRANCH_COUNTCOMPLETED));
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityCompensateScopeDef)
    */
   public void visit(AeActivityCompensateScopeDef aDef)
   {
      readAttributes(aDef);
      aDef.setTarget(getAttribute(TAG_TARGET));
      addActivityToParent(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.io.readers.def.AeReaderVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeQueryDef)
    */
   public void visit(AeQueryDef aDef)
   {
      readAttributes(aDef);

      readQueryDef(aDef);
      ((IAeQueryParentDef) getParentDef()).setQueryDef(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityOpaqueDef)
    */
   public void visit(AeActivityOpaqueDef aDef)
   {
      readAttributes(aDef);
      addActivityToParent(aDef);
   }   

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeFromDef)
    */
   public void visit(AeFromDef aDef)
   {
      if (TAG_OPAQUE_FROM.equals( getCurrentElement().getLocalName()) )      
      {
         readCommonAttributes(aDef);
         aDef.setOpaque(true);
      }
      else 
      {
         readAttributes(aDef);
      }
      ((IAeFromParentDef)getParentDef()).setFromDef(aDef);
   }   
   
   /**
    * Visits a query def in order to Oread the query language and query value.
    * 
    * @param aDef
    */
   protected void readQueryDef(AeQueryDef aDef)
   {
      aDef.setQueryLanguage(getAttribute(TAG_QUERY_LANGUAGE));
      aDef.setQuery(AeXmlUtil.getText(getCurrentElement()));
   }
}
