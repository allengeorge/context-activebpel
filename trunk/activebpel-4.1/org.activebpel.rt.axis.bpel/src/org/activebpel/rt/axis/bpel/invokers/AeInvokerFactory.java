//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis.bpel/src/org/activebpel/rt/axis/bpel/invokers/AeInvokerFactory.java,v 1.2 2005/10/17 20:02:4
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
package org.activebpel.rt.axis.bpel.invokers;

import org.activebpel.rt.axis.bpel.AeMessages;
import org.apache.axis.AxisFault;
import org.apache.axis.client.Call;
import org.apache.axis.constants.Style;

/**
 * Provides factorya access for RPC or Document style invokers.  Invoker instances
 * returned from this impl are thread safe.
 */
public class AeInvokerFactory
{
   private static final String INVALID_INVOKE_STYLE = "AeInvokerFactory.ERROR_1"; //$NON-NLS-1$
   
   /** RPC style invoker singleton instance. */
   private static final AeRpcStyleInvoker RPC_INVOKER = new AeRpcStyleInvoker();
   /** Document style invoker singleton instance. */
   private static final AeDocumentStyleInvoker DOC_INVOKER = new AeDocumentStyleInvoker();

   /**
    * Get the invoker.
    * @param aContext
    */
   public static IAeInvoker getInvoker( AeInvokeContext aContext ) throws AxisFault
   {
      IAeInvoker invoker = null;
      String style = (String)aContext.getCall().getProperty( Call.OPERATION_STYLE_PROPERTY );
      
      if( Style.RPC.getName().equals( style ) )
      {
         invoker = RPC_INVOKER;
      }
      else if( Style.DOCUMENT.getName().equals( style ) )
      {
         invoker = DOC_INVOKER;
      }
      
      if( invoker == null )
      {
         throw new AxisFault( AeMessages.getString(INVALID_INVOKE_STYLE) );
      }
      return invoker;
   }
   
}
