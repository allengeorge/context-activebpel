//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/function/attachment/AeCopyAllAttachmentsFunction.java,v 1.3 2007/09/04 15:51:3
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
 * copyAllAttachments(fromVarnames, toVarname) function call.
 * <p>
 * <em>Description:</em> Copies all attachments from the variables identified by the fromVarnames to the
 * variable identified by the toVarname.
 * </p>
 * <em>Parameters:</em>
 * <ul>
 * <li>fromVarnames string containing the list of names of the variables to copy attachments from</li>
 * <li>toVarname string containing the name of the variable to copy attachments to</li>
 * </ul>
 * <p>
 * <em>Return:</em> Integer, containing the number of attachments copied.
 * </p>
 */
public class AeCopyAllAttachmentsFunction extends AeAbstractAttachmentFunction
{
   /** The name of the function implemented */
   public static final String FUNCTION_NAME = "copyAllAttachments"; //$NON-NLS-1$

   /**
    * Constructor.
    */
   public AeCopyAllAttachmentsFunction()
   {
      super(FUNCTION_NAME);
   }

   /**
    * Execution of XPath function.
    * @see org.jaxen.Function#call(org.jaxen.Context, java.util.List)
    */
   public Object call(IAeFunctionExecutionContext aContext, List aArgs) throws AeFunctionCallException
   {
      // Validate that we have the proper number of arguments
      int numArgs = aArgs.size();
      if (numArgs != 2)
         throwFunctionException(INVALID_PARAMS, getFunctionName());

      // Get the from and to variable object from the first and second function argument
      String fromVariableNames = getStringArg(aArgs,0);
      String toVariableName = getStringArg(aArgs,1);

      // Resolve the toVariable first.
      IAeVariable toVariable = getVariable(aContext, toVariableName);

      // Now get the list of from variables
      Collection fromVariables = resolveVariables(aContext, fromVariableNames, toVariableName);

      int preCopyCount = toVariable.getAttachmentData().size();
      int count = 0;
      for (Iterator varIter = fromVariables.iterator(); varIter.hasNext(); )
      {
         IAeVariable fromVariable = (IAeVariable) varIter.next();
         toVariable.getAttachmentData().addAll(fromVariable.getAttachmentData());
         count += toVariable.getAttachmentData().size();
      }
      return new Integer(count - preCopyCount);
   }
}
