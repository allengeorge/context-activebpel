//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/function/AeExtensionFunctionContext.java,v 1.8 2007/08/16 14:37:3
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

import org.activebpel.rt.bpel.function.AeUnresolvableException;
import org.activebpel.rt.bpel.function.IAeFunction;
import org.activebpel.rt.bpel.impl.function.attachment.AeCopyAllAttachmentsFunction;
import org.activebpel.rt.bpel.impl.function.attachment.AeCopyAttachmentFunction;
import org.activebpel.rt.bpel.impl.function.attachment.AeCreateAttachmentFunction;
import org.activebpel.rt.bpel.impl.function.attachment.AeGetAttachmentCountFunction;
import org.activebpel.rt.bpel.impl.function.attachment.AeGetAttachmentSizeFunction;
import org.activebpel.rt.bpel.impl.function.attachment.AeGetAttachmentPropertyFunction;
import org.activebpel.rt.bpel.impl.function.attachment.AeGetAttachmentTypeFunction;
import org.activebpel.rt.bpel.impl.function.attachment.AeRemoveAllAttachmentsFunction;
import org.activebpel.rt.bpel.impl.function.attachment.AeRemoveAttachmentFunction;
import org.activebpel.rt.bpel.impl.function.attachment.AeReplaceAttachmentFunction;

/**
 * A <code>FunctionContext</code> implementation that handles returning BPEL extension functions. Currently
 * supported function are:
 * 
 * <pre>
 * getProcessId
 * </pre>
 */
public class AeExtensionFunctionContext extends AeAbstractFunctionContext
{
   // Constant for getProcessId bpel extension function. */
   private static final String PROCESS_ID = "getProcessId"; //$NON-NLS-1$

   // Constant for getProcessName bpel extension function. */
   private static final String PROCESS_NAME = "getProcessName"; //$NON-NLS-1$

   // Constant for getMyRoleProperty bpel function. */
   private static final String GET_MYROLE_PROPERTY = "getMyRoleProperty"; //$NON-NLS-1$

   /**
    * @see org.activebpel.rt.bpel.function.IAeFunctionContext#getFunction(java.lang.String) 
    * TODO: (JB) refractor into a function factory or chain of command pattern to return the objects
    */
   public IAeFunction getFunction(String aLocalName) throws AeUnresolvableException
   {
      if ( PROCESS_ID.equals(aLocalName) )
      {
         return new AeGetProcessIdFunction();
      }
      else if ( PROCESS_NAME.equals(aLocalName) )
      {
         return new AeGetProcessNameFunction();
      }
      else if ( GET_MYROLE_PROPERTY.equals(aLocalName) )
      {
         return new AeGetMyRolePropertyFunction();
      }
      else if ( AeGetAttachmentCountFunction.FUNCTION_NAME.equals(aLocalName) )
      {
         return new AeGetAttachmentCountFunction();
      }
      else if ( AeCopyAttachmentFunction.FUNCTION_NAME.equals(aLocalName) )
      {
         return new AeCopyAttachmentFunction();
      }
      else if ( AeCopyAllAttachmentsFunction.FUNCTION_NAME.equals(aLocalName) )
      {
         return new AeCopyAllAttachmentsFunction();
      }
      else if ( AeGetAttachmentTypeFunction.FUNCTION_NAME.equals(aLocalName) )
      {
         return new AeGetAttachmentTypeFunction();
      }
      else if ( AeGetAttachmentPropertyFunction.FUNCTION_NAME.equals(aLocalName) )
      {
         return new AeGetAttachmentPropertyFunction();
      }
      else if ( AeRemoveAllAttachmentsFunction.FUNCTION_NAME.equals(aLocalName) )
      {
         return new AeRemoveAllAttachmentsFunction();
      } 
      else if ( AeRemoveAttachmentFunction.FUNCTION_NAME.equals(aLocalName) )
      {
         return new AeRemoveAttachmentFunction();
      } 
      else if ( AeReplaceAttachmentFunction.FUNCTION_NAME.equals(aLocalName) )
      {
         return new AeReplaceAttachmentFunction();
      }
      else if ( AeGetAttachmentSizeFunction.FUNCTION_NAME.equals(aLocalName) )
      {
         return new AeGetAttachmentSizeFunction();
      }
      else if ( AeCreateAttachmentFunction.FUNCTION_NAME.equals(aLocalName) )
      {
         return new AeCreateAttachmentFunction();
      }
      else if ( AeResolveURNFunction.FUNCTION_NAME.equals(aLocalName) )
      {
         return new AeResolveURNFunction();
      }
      else if ( AeBase64EncodeFunction.FUNCTION_NAME.equals(aLocalName) )
      {
         return new AeBase64EncodeFunction();
      }
      
      else
      {
         throw new AeUnresolvableException(formatFunctionNotFoundErrorMsg(aLocalName));
      }
   }
}
