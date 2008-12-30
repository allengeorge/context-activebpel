// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/assign/to/AeToQueryVariableContext.java,v 1.5 2007/08/29 15:45:1
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
package org.activebpel.rt.bpel.impl.activity.assign.to;

import org.activebpel.rt.bpel.IAeVariable;
import org.activebpel.rt.bpel.def.expr.xpath.AeXPathVariableReference;
import org.activebpel.rt.bpel.impl.AeBpelException;
import org.activebpel.rt.bpel.impl.activity.assign.IAeCopyOperationContext;
import org.activebpel.rt.bpel.impl.expr.AeExpressionException;
import org.activebpel.rt.bpel.impl.expr.xpath.AeXPathExpressionTypeConverter;
import org.activebpel.rt.bpel.xpath.AeXPathHelper;
import org.activebpel.rt.util.AeUtil;
import org.jaxen.UnresolvableException;
import org.jaxen.VariableContext;
import org.w3c.dom.Document;

/**
 * Implements a Jaxen variable context using the copy operation context for variable resolution.
 */
public class AeToQueryVariableContext implements VariableContext
{
   /** The copy operation context to use to resolve variables. */
   private IAeCopyOperationContext mCopyOperationContext;
   

   /**
    * Creates a variable context for the to-query to-spec impl.
    * 
    * @param aCopyOpContext
    */
   public AeToQueryVariableContext(IAeCopyOperationContext aCopyOpContext)
   {
      setCopyOperationContext(aCopyOpContext);
   }

   /**
    * @see org.jaxen.VariableContext#getVariableValue(java.lang.String, java.lang.String, java.lang.String)
    */
   public Object getVariableValue(String aNamespaceURI, String aPrefix, String aLocalName)
         throws UnresolvableException
   {
      Object result = null;

      try
      {
         // The variable must be unqualified.
         if (AeUtil.isNullOrEmpty(aNamespaceURI))
         {
            AeXPathVariableReference varRef = new AeXPathVariableReference(aLocalName);
            
            // Find the variable with the given name.
            String variableName = varRef.getVariableName();
            // FIXMEQ don't use this variable resolver in the from query
            IAeVariable variable = getCopyOperationContext().getVariableForUpdate(variableName, varRef.getPartName());

            // Is the variable a message variable?
            if (variable.isMessageType())
            {
               result = variable.getMessageData().getData(varRef.getPartName());
               // Note: always return an Element rather than a Document so that a relative path
               // can then be applied to it.
               if (result instanceof Document)
                  result = ((Document) result).getDocumentElement();
            }
            else if (variable.isType())
            {
               result = variable.getTypeData();
            }
            else if (variable.isElement())
            {
               result = variable.getElementData();
            }
         }
      }
      catch (AeBpelException ex)
      {
         throw new AeExpressionException(ex);
      }
      
      AeXPathExpressionTypeConverter converter = new AeXPathExpressionTypeConverter(AeXPathHelper.getInstance(getCopyOperationContext().getBPELNamespace()));
      return converter.convertToExpressionType(result);
   }

   /**
    * @return Returns the copyOperationContext.
    */
   public IAeCopyOperationContext getCopyOperationContext()
   {
      return mCopyOperationContext;
   }

   /**
    * @param aCopyOperationContext The copyOperationContext to set.
    */
   public void setCopyOperationContext(IAeCopyOperationContext aCopyOperationContext)
   {
      mCopyOperationContext = aCopyOperationContext;
   }
}
