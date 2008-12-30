//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/function/AeGetVariablePropertyFunction.java,v 1.10 2007/09/04 15:51:3
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
package org.activebpel.rt.bpel.impl.function;

import java.util.List;

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.bpel.IAeVariable;
import org.activebpel.rt.bpel.def.AeVariableDef;
import org.activebpel.rt.bpel.def.util.AeDefUtil;
import org.activebpel.rt.bpel.function.AeFunctionCallException;
import org.activebpel.rt.bpel.function.IAeFunctionExecutionContext;
import org.activebpel.rt.bpel.impl.AeNamespaceResolver;
import org.activebpel.rt.bpel.impl.AeSelectionFailureException;
import org.activebpel.rt.bpel.impl.expr.xpath.AeXPathFunctionContext;
import org.activebpel.rt.bpel.impl.expr.xpath.AeXPathNamespaceContext;
import org.activebpel.rt.bpel.xpath.AeXPathHelper;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.wsdl.def.IAePropertyAlias;
import org.w3c.dom.Node;

/**
 * Class representing the XPath function used by expression evaluator to handle 
 * the BPEL getVariableProperty function call.
 */
public class AeGetVariablePropertyFunction extends AeAbstractBpelFunction
{
   public static final String FUNCTION_NAME = "getVariableProperty"; //$NON-NLS-1$
   // error message constants
   private static final String INVALID_ARGS              = AeMessages.getString( "AeAbstractBpelObject.ERROR_33" ); //$NON-NLS-1$
   private static final String ERROR_WITH_PROPERTY_ALIAS = AeMessages.getString( "AeAbstractBpelObject.ERROR_34" ); //$NON-NLS-1$
   private static final String ERROR_MSG                 = AeMessages.getString( "AeAbstractBpelObject.ERROR_36" ); //$NON-NLS-1$
   
   /**
    * Constructor.
    */
   public AeGetVariablePropertyFunction()
   {
      super(FUNCTION_NAME);
   }

   /**
    * @see org.activebpel.rt.bpel.function.IAeFunction#call(org.activebpel.rt.bpel.function.IAeFunctionExecutionContext, java.util.List)
    */
   public Object call(IAeFunctionExecutionContext aContext, List aArgs) throws AeFunctionCallException
   {
      String bpelNamespace = aContext.getBpelNamespace();
      Object result = null;
      
      if (aArgs.size() != 2)
      {
         throw new AeFunctionCallException( INVALID_ARGS );
      }

      // Get variable from arg list and make sure we have data to process
      IAeVariable variable = aContext.getAbstractBpelObject().findVariable(getStringArg(aArgs,0));
      if (variable != null && variable.hasData())
      {
         // Get variable def so that we may get the msg type and property
         AeVariableDef def = variable.getDefinition();
         
         int propertyAliasType = -1;
         QName typeName = null;
         if (def.isMessageType())
         {
            propertyAliasType = IAePropertyAlias.MESSAGE_TYPE;
            typeName = def.getMessageType();
         }
         else if (def.isElement())
         {
            propertyAliasType = IAePropertyAlias.ELEMENT_TYPE;
            typeName = def.getElement();
         }
         else
         {
            propertyAliasType = IAePropertyAlias.TYPE;
            typeName = def.getType();
         }
         
         QName propName = AeDefUtil.parseQName(aContext.getAbstractBpelObject().getDefinition(), getStringArg(aArgs,1));
            
         // Get the property alias given the msg type and property
         try
         {
            IAePropertyAlias propAlias = getPropertyAlias(aContext.getAbstractBpelObject(), propertyAliasType, typeName, propName);
            // Get the data for the context
            result = getDataForQueryContext(variable, propAlias);
            if (result != null && ! AeUtil.isNullOrEmpty(propAlias.getQuery()))
            {
               if (result instanceof Node)
               {
                  // Execute selection query provided by alias to get desired node
                  // Note: The namespace context changes here to have the
                  //       property alias resolve any prefixes.
                  // TODO (EPW) can't assume XPath 1.0 for BPEL 2.0
                  result = AeXPathHelper.getInstance(bpelNamespace).executeXPathExpression(
                        propAlias.getQuery(), result,
                        new AeXPathFunctionContext(aContext), 
                        new AeXPathNamespaceContext(new AeNamespaceResolver(propAlias)) );
               }
            }
         }
         catch (AeBusinessProcessException e)
         {
            throw new AeFunctionCallException( ERROR_WITH_PROPERTY_ALIAS, e);
         }
      }
      
      // Make sure we have selected only one node, otherwise create the appropriate fault
      if (result instanceof List && ((List)result).size() != 1)
      {
         Exception e = new AeSelectionFailureException(bpelNamespace, ((List)result).size());            
         throw new AeFunctionCallException(ERROR_MSG, e);
      }         

      return result;
   }
   
   /**
    * Gets the data to use for the query context. In the case of message variables, this will
    * be the data from a part. In the case of elements or types, this will be the data from
    * the type or element from the variable.
    * @param aVariable
    * @param aPropAlias
    * @throws AeBusinessProcessException
    */
   protected Object getDataForQueryContext(IAeVariable aVariable, IAePropertyAlias aPropAlias) throws AeBusinessProcessException
   {
      AeVariableDef def = aVariable.getDefinition();
      if (def.isMessageType())
      {
         return aVariable.getMessageData().getData(aPropAlias.getPart());
      }
      else if (def.isElement())
      {
         return aVariable.getElementData();
      }
      else
      {
         return aVariable.getTypeData();
      }
   }
   
}
