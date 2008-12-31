//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/function/AeBase64EncodeFunction.java,v 1.2 2007/09/04 15:51:3
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

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.activebpel.rt.base64.Base64;
import org.activebpel.rt.bpel.function.AeFunctionCallException;
import org.activebpel.rt.bpel.function.IAeFunctionExecutionContext;

/**
 * Implements the abx:base64Encode(stringValue,[charSet]) custom function.
 * <p/>
 * <em>Parameters:</em>
 * <ul>
 * <li>stringValue string to be encoded</li>
 * <li>charSet optional named charset. When omitted, the default charset used is UTF-8</li>
 * </ul>
 */
public class AeBase64EncodeFunction extends AeAbstractBpelFunction
{
   /** Defult character set encoding */
   public final static  String DEFAULT_CHAR_SET = "UTF-8"; //$NON-NLS-1$
   
   /** The name of the function implemented */
   public static final String FUNCTION_NAME = "base64Encode"; //$NON-NLS-1$

   /**
    * Constructor
    */
   public AeBase64EncodeFunction()
   {
      super(FUNCTION_NAME);
   }
  
   /**
    * @see org.activebpel.rt.bpel.function.IAeFunction#call(org.activebpel.rt.bpel.function.IAeFunctionExecutionContext, java.util.List)
    */
   public Object call(IAeFunctionExecutionContext aContext, List aArgs) throws AeFunctionCallException
   {
      if(aArgs.size() < 1 || aArgs.size() > 2)
         throwFunctionException(INVALID_PARAMS, FUNCTION_NAME);
     
      try
      {
         return (String)Base64.encodeBytes(getStringArg(aArgs,0).getBytes(aArgs.size() == 2 ? getStringArg(aArgs,1) : DEFAULT_CHAR_SET));
      }
      catch (UnsupportedEncodingException ex)
      {
        throw new AeFunctionCallException(ex);
      }
   }
}
