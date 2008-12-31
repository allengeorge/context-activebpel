//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/function/AeGetMyRolePropertyFunction.java,v 1.9 2007/09/04 15:51:3
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

import java.util.Iterator;
import java.util.List;

import org.activebpel.rt.IAeConstants;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.bpel.IAeEndpointReference;
import org.activebpel.rt.bpel.function.AeFunctionCallException;
import org.activebpel.rt.bpel.function.IAeFunction;
import org.activebpel.rt.bpel.function.IAeFunctionExecutionContext;
import org.activebpel.rt.bpel.impl.AePartnerLink;
import org.activebpel.rt.bpel.impl.AeSelectionFailureException;
import org.activebpel.rt.bpel.impl.expr.xpath.AeXPathFunctionContext;
import org.activebpel.rt.bpel.impl.expr.xpath.AeXPathNamespaceContext;
import org.activebpel.rt.bpel.xpath.AeXPathHelper;
import org.activebpel.rt.util.AeXmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


/**
 * Class representing the function used by expression evaluators to handle 
 * the BPEL getVariableData() function call.
 */
public class AeGetMyRolePropertyFunction extends AeAbstractBpelFunction implements IAeFunction
{   
   /** The name of the function implemented */
   public static final String FUNCTION_NAME = "getMyRoleProperty"; //$NON-NLS-1$
   
   // error message constants
   private static final String INVALID_ARGS            = AeMessages.getString( "AeAbstractBpelObject.ERROR_45" ); //$NON-NLS-1$
   private static final String INVALID_PARTNER_LINK    = AeMessages.getString( "AeAbstractBpelObject.ERROR_42" ); //$NON-NLS-1$ 
   private static final String INVALID_MYROLE          = AeMessages.getString( "AeAbstractBpelObject.ERROR_43" ); //$NON-NLS-1$ 
   private static final String ERROR_EVALUATING_QUERY  = AeMessages.getString( "AeAbstractBpelObject.ERROR_28" ); //$NON-NLS-1$
   private static final String GET_MYROLE_DATA_ERROR   = AeMessages.getString( "AeAbstractBpelObject.ERROR_44" ); //$NON-NLS-1$
   private static final String SELECTION_FAULT_ERROR   = AeMessages.getString( "AeAbstractBpelObject.ERROR_46" ); //$NON-NLS-1$
   private static final String TAG_HEADERS = "Headers"; //$NON-NLS-1$
   private static final String TAG_OPERATION = "operation"; //$NON-NLS-1$

   /**
    * Constructor.
    */
   public AeGetMyRolePropertyFunction()
   {
       super(FUNCTION_NAME);
   }
   
   /**
    * Execution of XPath function. 
    * @see org.jaxen.Function#call(org.jaxen.Context, java.util.List)
    */
   public Object call(IAeFunctionExecutionContext aContext, List aArgs) throws AeFunctionCallException
   {
      Object result = null;

      try
      {

         // Validate that we have the proper number of arguments
         int numArgs = aArgs.size();
         if ( numArgs < 2 || numArgs > 3 )
            throw new AeFunctionCallException(INVALID_ARGS);

         // Get the partner link
         AePartnerLink plink = aContext.getAbstractBpelObject().findPartnerLink(getStringArg(aArgs,0));
         if ( plink == null )
            // no such partner link
            throw new AeFunctionCallException(INVALID_PARTNER_LINK);

         // get myRole endpoint reference
         IAeEndpointReference myRef = plink.getMyReference();
         if ( myRef == null )
            // myRole endpoint not initialized
            throw new AeFunctionCallException(INVALID_MYROLE);

         // Get Headers for operation from Extensibility Elements
         for (Iterator it = myRef.getExtensibilityElements(); it.hasNext();)
         {
            Element elem = (Element)it.next();
            // Find Headers for operation
            if ( elem.getLocalName().equals(TAG_HEADERS) && elem.getNamespaceURI().equals(IAeConstants.ABX_NAMESPACE_URI) )
            {
               if ( elem.getAttribute(TAG_OPERATION).equals(getStringArg(aArgs,1)) )
               {
                  result = elem;
                  break;
               }
            }
         }

         // if the third argument is used it represents a query against the headers element
         if ( result != null && numArgs > 2 )
         {
            try
            {
               String xpathQuery = getStringArg(aArgs,2);
               AeXPathHelper xpathHelper = AeXPathHelper.getInstance(aContext.getBpelNamespace());
               Document headerDoc = AeXmlUtil.newDocument();
               headerDoc.appendChild(headerDoc.importNode((Node) result, true));
               Object xpathContext = headerDoc;
               AeXPathFunctionContext funcContext = new AeXPathFunctionContext(aContext);
               AeXPathNamespaceContext namespaceContext = new AeXPathNamespaceContext(
                     aContext.getAbstractBpelObject());
               
               // Execute the xpath query/expression to get the result.
               result = xpathHelper.executeXPathExpression(xpathQuery, xpathContext, funcContext,
                     namespaceContext);
            }
            catch (AeBusinessProcessException ex)
            {
               throw new AeFunctionCallException(ERROR_EVALUATING_QUERY, ex);
            }
         }
         // if selection failure is turned off then don't test for these errors
         if ( !isSelectionFailureDisabled(aContext.getAbstractBpelObject()) )
         {
            // Make sure data exists
            if ( result == null )
            {
               throw new AeFunctionCallException(GET_MYROLE_DATA_ERROR);
            }

            // If the result is a list it must return exactly one node
            if ( result instanceof List )
            {
               // Make sure we have selected only one node, otherwise create the appropriate fault
               List list = (List)result;
               if ( list.isEmpty() || list.size() > 1)
               {
                  Exception e = new AeSelectionFailureException(aContext.getBpelNamespace(), list.size());
                  throw new AeFunctionCallException(SELECTION_FAULT_ERROR, e);
               }
            }
         }
      }
      catch (AeFunctionCallException fce)
      {
         throw fce;
      }
      catch (Exception e)
      {
         throw new AeFunctionCallException(ERROR_EVALUATING_QUERY, e);
      }

      return result;
   }

}
