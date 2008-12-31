// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/io/writers/def/AeWSBPELWriterVisitor.java,v 1.18 2007/09/12 02:48:1
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

import org.activebpel.rt.bpel.AeExpressionLanguageFactory;
import org.activebpel.rt.bpel.def.*;
import org.activebpel.rt.bpel.def.activity.*;
import org.activebpel.rt.bpel.def.activity.support.*;
import org.activebpel.rt.bpel.def.util.AeDefUtil;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.activebpel.rt.xml.AeElementBasedNamespaceContext;
import org.activebpel.rt.xml.IAeMutableNamespaceContext;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.namespace.QName;
import java.util.Iterator;
import java.util.Map;

/**
 * A WS-BPEL 2.0 implementation of a writer visitor.
 */
public class AeWSBPELWriterVisitor extends AeWriterVisitor
{
   /**
    * Constructs a ws-bpel 2.0 writer visitor.
    * 
    * @param aDef
    * @param aParentElement
    * @param aNamespace
    * @param aTagName
    */
   public AeWSBPELWriterVisitor(AeBaseDef aDef, Element aParentElement, String aNamespace, String aTagName)
   {
      super(aDef, aParentElement, aNamespace, aTagName);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.io.writers.def.AeWriterVisitor#writeMessageExchange(java.lang.String)
    */
   protected void writeMessageExchange(String aMessageExchangeValue)
   {
      setAttribute(TAG_MESSAGE_EXCHANGE, aMessageExchangeValue);
   }

   /**
    * @see org.activebpel.rt.bpel.def.io.writers.def.AeWriterVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeForEachBranchesDef)
    */
   public void visit(AeForEachBranchesDef aDef)
   {
      super.visit(aDef);
      setAttribute(TAG_FOREACH_BRANCH_COUNTCOMPLETED, aDef.isCountCompletedBranchesOnly(), false);
   }

   /**
    * Write attributes to the Element.
    * @param aDef
    */
   protected void writeAssignToAttributes(AeToDef aDef)
   {
      super.writeAssignToAttributes(aDef);

      if (AeUtil.notNullOrEmpty( aDef.getExpression() ))
      {
         writeExpressionLang(aDef);
         
         Text textNode = getElement().getOwnerDocument().createTextNode(aDef.getExpression());
         getElement().appendChild(textNode);
      }
   }

   /**
    * @see org.activebpel.rt.bpel.def.io.writers.def.AeWriterVisitor#writeAssignFromAttributes(org.activebpel.rt.bpel.def.activity.support.AeFromDef)
    */
   protected void writeAssignFromAttributes(AeFromDef aDef)
   {
      super.writeAssignFromAttributes(aDef);

      if (AeUtil.notNullOrEmpty( aDef.getExpression() ))
      {
         writeExpressionLang(aDef);
         
         Text textNode = getElement().getOwnerDocument().createTextNode(aDef.getExpression());
         getElement().appendChild(textNode);
      }
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AeProcessDef)
    */
   public void visit(AeProcessDef aDef)
   {
      super.visit(aDef);

      if(aDef.getExitOnStandardFault() != null)
      {
         setAttribute(TAG_EXIT_ON_STANDARD_FAULT, aDef.getExitOnStandardFault().booleanValue(), false);
      }
      
      IAeMutableNamespaceContext nsContext = new AeElementBasedNamespaceContext( getElement() );
      setAttributeNS(nsContext, IAeBPELConstants.AE_EXTENSION_NAMESPACE_URI_QUERY_HANDLING,
            IAeBPELConstants.AE_EXTENSION_PREFIX, TAG_CREATE_TARGET_XPATH, aDef.isCreateTargetXPath(), false);
      setAttributeNS(nsContext, IAeBPELConstants.AE_EXTENSION_NAMESPACE_URI_QUERY_HANDLING,
            IAeBPELConstants.AE_EXTENSION_PREFIX, TAG_DISABLE_SELECTION_FAILURE, aDef.isDisableSelectionFailure(), false);

      if (AeUtil.notNullOrEmpty(aDef.getAbstractProcessProfile()))
      {
         writeAbstractProcessProfileAttribute(aDef, nsContext);
      }
   }
   
   /**
    * Writes out the abstract process profile attribute.
    * @param aDef
    * @param aNsContext
    */
   protected void writeAbstractProcessProfileAttribute(AeProcessDef aDef, IAeMutableNamespaceContext aNsContext)
   {
      // do not write process profile for executable ns. 
      //setAttributeNS(aNsContext, IAeBPELConstants.WSBPEL_2_0_ABSTRACT_NAMESPACE_URI, 
      //      IAeBPELConstants.ABSTRACT_PROC_PREFIX, IAeBPELConstants.TAG_ABSTRACT_PROCESS_PROFILE, aDef.getAbstractProcessProfile());
      
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.io.writers.def.AeWriterVisitor#visit(org.activebpel.rt.bpel.def.AeImportDef)
    */
   public void visit(AeImportDef aDef)
   {
      writeAttributes(aDef);
      
      setAttribute(TAG_NAMESPACE, aDef.getNamespace());
      setAttribute(TAG_LOCATION, aDef.getLocation());
      setAttribute(TAG_IMPORT_TYPE, aDef.getImportType());
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.io.writers.def.AeWriterVisitor#visit(org.activebpel.rt.bpel.def.AeDocumentationDef)
    */
   public void visit(AeDocumentationDef aDef)
   {
      writeAttributes(aDef);

      setAttribute(TAG_DOCUMENTATION_SOURCE, aDef.getSource());
      getElement().setAttributeNS(W3C_XML_NAMESPACE, "xml:" +  TAG_DOCUMENTATION_LANG, aDef.getLanguage()); //$NON-NLS-1$
      if (AeUtil.notNullOrEmpty( aDef.getValue() ))
      {
         Text textNode = getElement().getOwnerDocument().createTextNode(aDef.getValue());
         getElement().appendChild(textNode);
      }
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.io.writers.def.AeWriterVisitor#visit(org.activebpel.rt.bpel.def.AePartnerLinkDef)
    */
   public void visit(AePartnerLinkDef aDef)
   {
      super.visit(aDef);

      if (aDef.getInitializePartnerRole() != null)
         setAttribute(TAG_INITIALIZE_PARTNER_ROLE, aDef.getInitializePartnerRole().booleanValue(), true);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.io.writers.def.AeWriterVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeAssignCopyDef)
    */
   public void visit(AeAssignCopyDef aDef)
   {
      super.visit(aDef);
      
      setAttribute(TAG_KEEP_SRC_ELEMENT_NAME, aDef.isKeepSrcElementName(), false);
      setAttribute(TAG_IGNORE_MISSING_FROM_DATA, aDef.isIgnoreMissingFromData(), false);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.io.writers.def.AeWriterVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityAssignDef)
    */
   public void visit(AeActivityAssignDef aDef)
   {
      super.visit(aDef);
      
      setAttribute(TAG_VALIDATE, aDef.isValidate(), false);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityValidateDef)
    */
   public void visit(AeActivityValidateDef aDef)
   {
      writeAttributes(aDef);

      setAttribute(TAG_VARIABLES, aDef.getVariablesAsString());
   }

   /**
    * @see org.activebpel.rt.bpel.def.io.writers.def.AeWriterVisitor#visit(org.activebpel.rt.bpel.def.AeExtensionDef)
    */
   public void visit(AeExtensionDef aDef)
   {
      writeAttributes(aDef);

      setAttribute(TAG_MUST_UNDERSTAND, aDef.isMustUnderstand(), true);
      setAttribute(TAG_NAMESPACE, aDef.getNamespace());
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.io.writers.def.AeWriterVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeFromPartsDef)
    */
   public void visit(AeFromPartsDef aDef)
   {
      writeAttributes(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.io.writers.def.AeWriterVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeToPartsDef)
    */
   public void visit(AeToPartsDef aDef)
   {
      writeAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeFromPartDef)
    */
   public void visit(AeFromPartDef aDef)
   {
      writeAttributes(aDef);
      
      setAttribute(TAG_PART, aDef.getPart());
      setAttribute(TAG_TO_VARIABLE, aDef.getToVariable());
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeToPartDef)
    */
   public void visit(AeToPartDef aDef)
   {
      writeAttributes(aDef);

      setAttribute(TAG_PART, aDef.getPart());
      setAttribute(TAG_FROM_VARIABLE, aDef.getFromVariable());
   }

   /**
    * @see org.activebpel.rt.bpel.def.io.writers.def.AeWriterVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeSourcesDef)
    */
   public void visit(AeSourcesDef aDef)
   {
      writeAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.io.writers.def.AeWriterVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeTargetsDef)
    */
   public void visit(AeTargetsDef aDef)
   {
      writeAttributes(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.io.writers.def.AeWriterVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeTransitionConditionDef)
    */
   public void visit(AeTransitionConditionDef aDef)
   {
      writeAttributes(aDef);
      writeExpressionDef(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.io.writers.def.AeWriterVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeJoinConditionDef)
    */
   public void visit(AeJoinConditionDef aDef)
   {
      super.visit(aDef);
      
      writeAttributes(aDef);
      writeExpressionDef(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeForDef)
    */
   public void visit(AeForDef aDef)
   {
      super.visit(aDef);
      
      writeExpressionDef(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeUntilDef)
    */
   public void visit(AeUntilDef aDef)
   {
      super.visit(aDef);

      writeExpressionDef(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.io.writers.def.AeWriterVisitor#visit(AeNotUnderstoodExtensionActivityDef)
    */
   public void visit(AeNotUnderstoodExtensionActivityDef aDef)
   {
      Element elem = getElement();
      Map attributes = aDef.getAttributes();
      for (Iterator iter = attributes.keySet().iterator(); iter.hasNext(); )
      {
         QName attrName = (QName) iter.next();
         String attrValue = (String) attributes.get(attrName);
         String qualifier = ""; //$NON-NLS-1$
         if (AeUtil.notNullOrEmpty(attrName.getNamespaceURI()))
         {
            qualifier = AeXmlUtil.getOrCreatePrefix(elem, attrName.getNamespaceURI(), "ext", false, false) + ":"; //$NON-NLS-1$ //$NON-NLS-2$
         }
         elem.setAttributeNS(attrName.getNamespaceURI(), qualifier + attrName.getLocalPart(), attrValue);
      }

      writeAttributes(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeConditionDef)
    */
   public void visit(AeConditionDef aDef)
   {
      writeAttributes(aDef);
      
      writeExpressionDef(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeRepeatEveryDef)
    */
   public void visit(AeRepeatEveryDef aDef)
   {
      writeAttributes(aDef);
      
      writeExpressionDef(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityScopeDef)
    */
   public void visit(AeActivityScopeDef aDef)
   {
      super.visit(aDef);

      setAttribute(TAG_ISOLATED, aDef.isIsolated(), false);

      if (aDef.getExitOnStandardFault() != null)
      {
         setAttribute(TAG_EXIT_ON_STANDARD_FAULT, aDef.getExitOnStandardFault().booleanValue(), true);
      }
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.io.writers.def.AeWriterVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeOnEventDef)
    */
   public void visit(AeOnEventDef aDef)
   {
      super.visit(aDef);

      setAttribute(TAG_MESSAGE_TYPE, aDef.getMessageType());
      setAttribute(TAG_ELEMENT, aDef.getElement());
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.io.writers.def.AeWriterVisitor#visit(org.activebpel.rt.bpel.def.AeCatchDef)
    */
   public void visit(AeCatchDef aDef)
   {
      super.visit(aDef);

      setAttribute(TAG_FAULT_MESSAGE_TYPE, aDef.getFaultMessageType());
      setAttribute(TAG_FAULT_ELEMENT, aDef.getFaultElementName());
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.io.writers.def.AeWriterVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityRepeatUntilDef)
    */
   public void visit(AeActivityRepeatUntilDef aDef)
   {
      writeAttributes(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.io.writers.def.AeWriterVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeExtensibleAssignDef)
    */
   public void visit(AeExtensibleAssignDef aDef)
   {
      writeAttributes(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.io.writers.def.AeWriterVisitor#visit(org.activebpel.rt.bpel.def.AeExtensionsDef)
    */
   public void visit(AeExtensionsDef aDef)
   {
      writeAttributes(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.io.writers.def.AeWriterVisitor#visit(org.activebpel.rt.bpel.def.AeExtensionActivityDef)
    */
   public void visit(AeExtensionActivityDef aDef)
   {
      writeAttributes(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.io.writers.def.AeWriterVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityIfDef)
    */
   public void visit(AeActivityIfDef aDef)
   {
      writeAttributes(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.io.writers.def.AeWriterVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeElseDef)
    */
   public void visit(AeElseDef aDef)
   {
      writeAttributes(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.io.writers.def.AeWriterVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeElseIfDef)
    */
   public void visit(AeElseIfDef aDef)
   {
      writeAttributes(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.io.writers.def.AeWriterVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityRethrowDef)
    */
   public void visit(AeActivityRethrowDef aDef)
   {
      writeAttributes(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.io.writers.def.AeWriterVisitor#visit(org.activebpel.rt.bpel.def.AeTerminationHandlerDef)
    */
   public void visit(AeTerminationHandlerDef aDef)
   {
      writeAttributes(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityCompensateScopeDef)
    */
   public void visit(AeActivityCompensateScopeDef aDef)
   {
      writeAttributes(aDef);
      setAttribute(TAG_TARGET, aDef.getTarget());
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.io.writers.def.AeWriterVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeQueryDef)
    */
   public void visit(AeQueryDef aDef)
   {
      writeAttributes(aDef);
      writeQueryDef(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityOpaqueDef)
    */
   public void visit(AeActivityOpaqueDef aDef)
   {
      writeAttributes(aDef);
   }

   /**
    * Visits a query def in order to write out the queryLanguage attribute
    * and the value of the query.
    *
    * @param aDef
    */
   protected void writeQueryDef(AeQueryDef aDef)
   {
      setAttribute(TAG_QUERY_LANGUAGE, aDef.getQueryLanguage());
      Text textNode = getElement().getOwnerDocument().createTextNode(aDef.getQuery());
      getElement().appendChild(textNode);
   }

   /**
    * @see org.activebpel.rt.bpel.def.io.writers.def.AeWriterVisitor#writeExpressionLang(org.activebpel.rt.bpel.def.IAeExpressionDef)
    */
   protected void writeExpressionLang(IAeExpressionDef aDef)
   {
      // No need to do anything unless an expression language is specified
      String exprLang = aDef.getExpressionLanguage();
      if (AeUtil.notNullOrEmpty(exprLang))
      {
         // Get the default expression language for the process
         AeProcessDef proc = AeDefUtil.getProcessDef((AeBaseDef)aDef);
         String defaultLang = proc.getExpressionLanguage();
         if (AeUtil.isNullOrEmpty(defaultLang))
         {
            // No language specified at process level, so need to get BPEL default from expression factory
            AeExpressionLanguageFactory exprFactory = new AeExpressionLanguageFactory();
            defaultLang = exprFactory.getBpelDefaultLanguage(IAeBPELConstants.WSBPEL_2_0_NAMESPACE_URI);
         }
         
         // If expression lang is different than default, we will need to write it out
         if (! exprLang.equals(defaultLang))
            setAttribute(TAG_EXPRESSION_LANGUAGE, exprLang);
      }
   }
}
