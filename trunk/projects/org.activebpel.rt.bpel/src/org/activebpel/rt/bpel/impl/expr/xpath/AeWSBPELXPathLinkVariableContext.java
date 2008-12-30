// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/expr/xpath/AeWSBPELXPathLinkVariableContext.java,v 1.2 2006/09/07 15:06:2
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
package org.activebpel.rt.bpel.impl.expr.xpath;

import java.util.ArrayList;
import java.util.List;

import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.bpel.function.AeFunctionCallException;
import org.activebpel.rt.bpel.function.IAeFunction;
import org.activebpel.rt.bpel.function.IAeFunctionExecutionContext;
import org.activebpel.rt.bpel.impl.function.AeGetLinkStatusFunction;
import org.activebpel.rt.util.AeUtil;
import org.jaxen.UnresolvableException;
import org.jaxen.VariableContext;

/**
 * An implementation of a Jaxen variable context.  This class is used in the BPEL 2.0 version of 
 * the XPath expression runner in order to resolve variable references using the syntax outlined
 * in that version of BPEL.  This class differs from the AeWSBPELXPathVariableContext class in
 * that it resolves XPath variable references to link names rather than BPEL variables.
 */
public class AeWSBPELXPathLinkVariableContext implements VariableContext
{
   /** The function exec context. */
   private IAeFunctionExecutionContext mFunctionExecutionContext;

   /**
    * Constructor.
    * 
    * @param aContext
    */
   public AeWSBPELXPathLinkVariableContext(IAeFunctionExecutionContext aContext)
   {
      setFunctionExecutionContext(aContext);
   }
   
   /**
    * @see org.jaxen.VariableContext#getVariableValue(java.lang.String, java.lang.String, java.lang.String)
    */
   public Object getVariableValue(String aNamespaceURI, String aPrefix, String aLocalName) throws UnresolvableException
   {
      Object result = null;

      // The variable must be unqualified.
      if (AeUtil.isNullOrEmpty(aNamespaceURI))
      {
         String linkName = aLocalName;
         IAeFunction function = new AeGetLinkStatusFunction();
         List args = new ArrayList();
         args.add(linkName);
         try
         {
            result = function.call(getFunctionExecutionContext(), args);
         }
         catch (AeFunctionCallException ex)
         {
            throw new UnresolvableException(ex.getMessage());
         }
      }
      else
      {
         // This is the case of the qualified variable - which could not possibly be resolved.
         //
         // Note that this exception should never happen, since we would presumably have caught 
         // this during validation (qualified variable reference).
         throw new UnresolvableException(AeMessages.format("AeWSBPELXPathLinkVariableContext.UnresolvedLinkError", aLocalName)); //$NON-NLS-1$
      }

      // Convert the variable value to something that Jaxen can properly use.
      return getFunctionExecutionContext().getTypeConverter().convertToExpressionType(result);
   }

   /**
    * @return Returns the functionExecutionContext.
    */
   protected IAeFunctionExecutionContext getFunctionExecutionContext()
   {
      return mFunctionExecutionContext;
   }

   /**
    * @param mContext The functionExecutionContext to set.
    */
   protected void setFunctionExecutionContext(IAeFunctionExecutionContext mContext)
   {
      mFunctionExecutionContext = mContext;
   }
}
