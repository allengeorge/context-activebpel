//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/expr/functions/AeAbstractFunctionValidatorFactory.java,v 1.2 2007/08/16 14:36:0
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
package org.activebpel.rt.bpel.def.validation.expr.functions; 

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.def.IAeBPELConstants;
import org.activebpel.rt.bpel.def.expr.AeExpressionLanguageUtil;
import org.activebpel.rt.bpel.def.expr.AeScriptFuncDef;

/**
 * Provides lookup of validator given a function qname.
 */
public abstract class AeAbstractFunctionValidatorFactory implements IAeFunctionValidatorFactory
{
   /** singleton instance for BPWS */
   private static final IAeFunctionValidatorFactory sBPWSInstance = new AeBPWSFunctionValidatorFactory();

   /** singleton instance for WSBPEL */
   private static final IAeFunctionValidatorFactory sWSBPELInstance = new AeWSBPELFunctionValidatorFactory();
   
   /** map of qname to function validator */
   private Map mValidators = new HashMap();
   
   /**
    * private ctor for singleton
    */
   private AeAbstractFunctionValidatorFactory()
   {
      populateMap();
   }
   
   /**
    * Getter for the factory
    */
   public static IAeFunctionValidatorFactory getInstance(String aBPELNamespace)
   {
      if (IAeBPELConstants.BPWS_NAMESPACE_URI.equals(aBPELNamespace))
      {
         return sBPWSInstance;
      }
      else
      {
         return sWSBPELInstance;
      }
   }
   
   /**
    * Getter for the validator given the qname
    * @param aQName
    */
   public IAeFunctionValidator getValidator(AeScriptFuncDef aDef)
   {
      return (IAeFunctionValidator) getValidators().get(aDef.getQName());
   }

   /**
    * populates the map with validators
    */
   protected void populateMap()
   {
      Map map = getValidators();
      map.put(new QName(IAeBPELConstants.ABX_FUNCTIONS_NAMESPACE_URI, "getProcessId"), new AeGetProcessIdFunctionValidator()); //$NON-NLS-1$
      map.put(new QName(IAeBPELConstants.ABX_FUNCTIONS_NAMESPACE_URI, "getProcessName"), new AeGetProcessNameFunctionValidator()); //$NON-NLS-1$
      map.put(new QName(IAeBPELConstants.ABX_FUNCTIONS_NAMESPACE_URI, "resolveURN"), new AeResolveURNFunctionValidator()); //$NON-NLS-1$
      map.put(new QName(IAeBPELConstants.ABX_FUNCTIONS_NAMESPACE_URI, "getMyRoleProperty"), new AeGetMyRolePropertyFunctionValidator()); //$NON-NLS-1$
      map.put(new QName(IAeBPELConstants.ABX_FUNCTIONS_NAMESPACE_URI, "getAttachmentCount"), new AeGetAttachmentCountFunctionValidator()); //$NON-NLS-1$
      map.put(new QName(IAeBPELConstants.ABX_FUNCTIONS_NAMESPACE_URI, "copyAttachment"), new AeCopyAttachmentFunctionValidator()); //$NON-NLS-1$
      map.put(new QName(IAeBPELConstants.ABX_FUNCTIONS_NAMESPACE_URI, "replaceAttachment"), new AeReplaceAttachmentFunctionValidator()); //$NON-NLS-1$
      map.put(new QName(IAeBPELConstants.ABX_FUNCTIONS_NAMESPACE_URI, "removeAttachment"), new AeRemoveAttachmentFunctionValidator()); //$NON-NLS-1$
      map.put(new QName(IAeBPELConstants.ABX_FUNCTIONS_NAMESPACE_URI, "copyAllAttachments"), new AeCopyAllAttachmentsFunctionValidator()); //$NON-NLS-1$
      map.put(new QName(IAeBPELConstants.ABX_FUNCTIONS_NAMESPACE_URI, "removeAllAttachments"), new AeRemoveAllAttachmentsFunctionValidator()); //$NON-NLS-1$
      map.put(new QName(IAeBPELConstants.ABX_FUNCTIONS_NAMESPACE_URI, "getAttachmentType"), new AeGetAttachmentTypeFunctionValidator()); //$NON-NLS-1$
      map.put(new QName(IAeBPELConstants.ABX_FUNCTIONS_NAMESPACE_URI, "getAttachmentProperty"), new AeGetAttachmentPropertyFunctionValidator()); //$NON-NLS-1$
      map.put(new QName(IAeBPELConstants.ABX_FUNCTIONS_NAMESPACE_URI, "getAttachmentSize"), new AeGetAttachmentSizeFunctionValidator()); //$NON-NLS-1$
      map.put(new QName(IAeBPELConstants.ABX_FUNCTIONS_NAMESPACE_URI, "createAttachment"), new AeCreateAttachmentFunctionValidator()); //$NON-NLS-1$
      map.put(new QName(IAeBPELConstants.ABX_FUNCTIONS_NAMESPACE_URI, "base64Encode"), new AeBase64EncodeFunctionValidator()); //$NON-NLS-1$
   }
   
   /**
    * @return the validators
    */
   protected Map getValidators()
   {
      return mValidators;
   }

   /**
    * @param aValidators the validators to set
    */
   protected void setValidators(Map aValidators)
   {
      mValidators = aValidators;
   }
   
   private static class AeBPWSFunctionValidatorFactory extends AeAbstractFunctionValidatorFactory
   {
      /**
       * @see org.activebpel.rt.bpel.def.validation.expr.functions.AeAbstractFunctionValidatorFactory#populateMap()
       */
      protected void populateMap()
      {
         super.populateMap();

         Map map = getValidators();

         // add all BPWS functions
         map.put(AeExpressionLanguageUtil.VAR_DATA_FUNC, new AeGetVariableDataFunctionValidator());
         map.put(AeExpressionLanguageUtil.VAR_PROPERTY_FUNC, new AeGetVariablePropertyFunctionValidator());
         map.put(AeExpressionLanguageUtil.LINK_STATUS_FUNC, new AeGetLinkStatusFunctionValidator());
         
         // add all WSBPEL functions, but report errors if they're found
         map.put(AeExpressionLanguageUtil.DO_XSL_TRANSFORM_FUNC_BPEL20, new AeWSBPELFunctionUsedInBPWSValidator(new AeDoXslTransformFunctionValidator()));
         map.put(AeExpressionLanguageUtil.VAR_PROPERTY_FUNC_BPEL20, new AeWSBPELFunctionUsedInBPWSValidator(new AeGetVariablePropertyFunctionValidator()));
      }
   }

   private static class AeWSBPELFunctionValidatorFactory extends AeAbstractFunctionValidatorFactory
   {
      /**
       * @see org.activebpel.rt.bpel.def.validation.expr.functions.AeAbstractFunctionValidatorFactory#populateMap()
       */
      protected void populateMap()
      {
         super.populateMap();

         Map map = getValidators();

         // add all WSBPEL functions
         map.put(AeExpressionLanguageUtil.VAR_PROPERTY_FUNC_BPEL20, new AeGetVariablePropertyFunctionValidator());
         map.put(AeExpressionLanguageUtil.DO_XSL_TRANSFORM_FUNC_BPEL20, new AeDoXslTransformFunctionValidator());
         
         // add all BPWS functions, but report warning if they're found
         map.put(AeExpressionLanguageUtil.VAR_DATA_FUNC, new AeBPWSFunctionUsedInWSBPELValidator( new AeGetVariableDataFunctionValidator()));
         map.put(AeExpressionLanguageUtil.VAR_PROPERTY_FUNC, new AeBPWSFunctionUsedInWSBPELValidator( new AeGetVariablePropertyFunctionValidator() ));
         map.put(AeExpressionLanguageUtil.LINK_STATUS_FUNC, new AeBPWSFunctionUsedInWSBPELValidator( new AeGetLinkStatusFunctionValidator()));      
      }
   }
}
 
