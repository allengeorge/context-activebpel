//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/function/attachment/AeCopyAttachmentFunction.java,v 1.2 2007/09/04 15:51:3
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

import java.util.List;

import org.activebpel.rt.attachment.IAeAttachmentItem;
import org.activebpel.rt.bpel.IAeVariable;
import org.activebpel.rt.bpel.function.AeFunctionCallException;
import org.activebpel.rt.bpel.function.IAeFunctionExecutionContext;

/**
 * Class representing the function used by expression evaluators to handle the BPEL -
 * copyAttachment(fromVarname, fromItemNumber, toVarname) 
 * <ul>
 * <li>fromVarname string containing the name of the source variable.</li>
 * <li>fromItemNumber item number of the source attachment.</li>
 *  <li>toVarname string containing the name of the target variable.</li>
 * </ul>
 * <p>
 * <em>Throws:</em> attachmentFault if there was a problem with passed attachment.
 */
public class AeCopyAttachmentFunction extends AeAbstractAttachmentFunction
{

   /** The name of the function implemented */
   public static final String FUNCTION_NAME = "copyAttachment"; //$NON-NLS-1$

   /**
    * Constructor.
    */
   public AeCopyAttachmentFunction()
   {
      super(FUNCTION_NAME);
   }

   /**
    * Execution of XPath function.
    * @see org.jaxen.Function#call(org.jaxen.Context, java.util.List)
    */
   public Object call(IAeFunctionExecutionContext aContext, List aArgs) throws AeFunctionCallException
   {
      Object result = new Boolean(false);
      
      // Validate that we have the proper number of arguments
      int numArgs = aArgs.size();
      if (numArgs != 3)
         throwFunctionException(INVALID_PARAMS, getFunctionName());

      // Get the from variable name from the first function argument
      // Get the from item number from the second function argument
      // Get the to variable name from the third function argument
      IAeAttachmentItem fromItem = getAttachment(aContext, getStringArg(aArgs,0), getPositiveIntArg(aArgs,1));
      IAeVariable toVariable = getVariable(aContext, getStringArg(aArgs,2));
      result =  new Boolean(toVariable.getAttachmentData().add(fromItem));
      return result;
   }
}
