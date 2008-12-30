//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/function/attachment/AeGetAttachmentSizeFunction.java,v 1.3 2007/09/04 15:51:3
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
import org.activebpel.rt.bpel.function.AeFunctionCallException;
import org.activebpel.rt.bpel.function.IAeFunctionExecutionContext;
import org.activebpel.rt.util.AeMimeUtil;

/**
 * Class representing the function used by expression evaluators to handle the BPEL -
 * getAttachmentSize(varname,itemNumber) function call.
 * <p>
 * <em>Description:</em> : Returns the length of the passed attachment.
 * </p>
 * <em>Parameters:</em>
 * <ul>
 * <li>varname string containing the name of the variable</li>
 * <li>itemNumber Item number of attachment (starting at one).</li>
 * </ul>
 * <p>
 * <em>Return:</em> long which contains the length of the attachment.
 * </p>
 * <p>
 * <em>Throws:</em> attachmentFault if there was a problem with passed attachment.
 */
public class AeGetAttachmentSizeFunction extends AeAbstractAttachmentFunction
{
   /** The name of the function implemented */
   public static final String FUNCTION_NAME = "getAttachmentSize"; //$NON-NLS-1$

   /**
    * Constructor.
    */
   public AeGetAttachmentSizeFunction()
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
      if ( numArgs != 2 )
         throwFunctionException(INVALID_PARAMS, getFunctionName());
      
      // Get the variable name from the first function argument
      String variableName = getStringArg(aArgs,0);
      // Get the variable attachment item number from the second function argument
      IAeAttachmentItem item = getAttachment(aContext, variableName, getPositiveIntArg(aArgs,1));
      
      result = item.getHeader(AeMimeUtil.AE_SIZE_ATTRIBUTE);
      
      if ( result == null )
         throwFunctionException(NULL_RESULT_ERROR, getFunctionName());

      return result;
   }
}
