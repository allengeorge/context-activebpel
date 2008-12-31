//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/function/attachment/AeCreateAttachmentFunction.java,v 1.2 2007/09/04 15:51:3
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

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activebpel.rt.base64.Base64;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeVariable;
import org.activebpel.rt.bpel.function.AeFunctionCallException;
import org.activebpel.rt.bpel.function.IAeFunctionExecutionContext;
import org.activebpel.rt.bpel.impl.IAeBusinessProcessInternal;
import org.activebpel.rt.util.AeMimeUtil;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.wsio.AeWebServiceAttachment;

/**
 * Class representing the function used by expression evaluators to handle the BPEL -
 * createAttachment(varname, mimeType, encodedContent, [contentType]) function call.
 * <p>
 * <em>Description:</em> Creates a new attachment for the named variable.
 * </p>
 * <em>Parameters:</em>
 * <ul>
 * <li>varname string containing the name of the target variable</li>
 * <li>mimeType string value of the attachment content type eg. text/plain, image/jpeg ....</li>
 * <li>encodedContent string containing Base64 encoded content data of the attachment</li>
 * <li>Optional content id</li>
 * </ul>
 * <p>
 * <em>Return:</em> true if variable attachment was created successfully.
 * </p>
 * <p>
 * <em>Throws:</em> attachmentFault if there was a problem with passed attachment.
 * </p>
 */
public class AeCreateAttachmentFunction extends AeAbstractAttachmentFunction
{

   /** The name of the function implemented */
   public static final String FUNCTION_NAME = "createAttachment"; //$NON-NLS-1$

   /**
    * Constructor.
    */
   public AeCreateAttachmentFunction()
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
      if ( numArgs < 3 || numArgs > 4)
         throwFunctionException(INVALID_PARAMS, getFunctionName());

      // Get the variable with name from the first function argument
      IAeVariable variable = getVariable(aContext, getStringArg(aArgs,0));

      // Get the mime type from the second function argument
      String mimeType = getStringArg(aArgs,1);

      if ( AeUtil.isNullOrEmpty(mimeType) )
      {
         throwFunctionException(MISSING_ATTACHMENT_MIME, getFunctionName());
      }

      // Get the attachment content from the third function argument
      String encodedContent = getStringArg(aArgs,2);
      if ( AeUtil.isNullOrEmpty(encodedContent) )
      {
         throwFunctionException(MISSING_ATTACHMENT_CONTENT, getFunctionName());
      }
 
      IAeBusinessProcessInternal process = aContext.getAbstractBpelObject().getProcess();

      long pid = process.getProcessId();
      Map attributes = new HashMap();
      attributes.put(AeMimeUtil.CONTENT_TYPE_ATTRIBUTE, mimeType);
      attributes.put(AeMimeUtil.CONTENT_ID_ATTRIBUTE, numArgs == 4 ? getStringArg(aArgs,3) : AeMimeUtil.AE_DEFAULT_INLINE_CONTENT_ID + pid);

      String variablePath = variable.getLocationPath();

      try
      {
         process.getEngine().addVariableAttachment(pid, variablePath, new AeWebServiceAttachment(new ByteArrayInputStream(Base64.decode(encodedContent)), attributes));
      }
      catch (AeBusinessProcessException ex)
      {
         throwFunctionException(CREATE_ATTACHMENT_FAILED, getFunctionName());
      }
      result = Boolean.TRUE;
      return result;
   }
}
