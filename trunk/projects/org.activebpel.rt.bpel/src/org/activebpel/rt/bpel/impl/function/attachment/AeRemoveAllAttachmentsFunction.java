//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/function/attachment/AeRemoveAllAttachmentsFunction.java,v 1.2 2007/06/15 14:17:3
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
package org.activebpel.rt.bpel.impl.function.attachment;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.activebpel.rt.bpel.IAeVariable;
import org.activebpel.rt.bpel.function.AeFunctionCallException;
import org.activebpel.rt.bpel.function.IAeFunctionExecutionContext;

/**
 * Class representing the function used by expression evaluators to handle the BPEL -
 * removeAllAttachments(varnames) function call.
 * <p>
 * <em>Description:</em> Deletes all attachment of the named variable.
 * </p>
 * <em>Parameters:</em>
 * <ul>
 * <li>varname string containing the name of the variables. Can use "*" for all variables in scope, space
 * separated variable names or a single variable name.</li>
 * </ul>
 * <p>
 * <em>Return:</em> Integer, containing the number of attachments deleted.
 * </p>
 */
public class AeRemoveAllAttachmentsFunction extends AeAbstractAttachmentFunction
{

   /** The name of the function implemented */
   public static final String FUNCTION_NAME = "removeAllAttachments"; //$NON-NLS-1$

   /**
    * Constructor.
    */
   public AeRemoveAllAttachmentsFunction()
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

      // Validate that we have the proper number of arguments
      int numArgs = aArgs.size();
      if ( numArgs != 1 )
         throwFunctionException(INVALID_PARAMS, getFunctionName());

      // Get the variable name from the first function argument
      String variableNames = aArgs.get(0).toString();

      // Now get the list of variables
      Collection variables = resolveVariables(aContext, variableNames);
      int count = 0;
      for (Iterator varIter = variables.iterator(); varIter.hasNext();)
      {
         IAeVariable variable = (IAeVariable)varIter.next();

         count += variable.getAttachmentData().size();
         variable.getAttachmentData().clear();
      }

      result = new Integer(count);

      return result;
   }
}
