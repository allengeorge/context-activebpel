//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis.bpel/src/org/activebpel/rt/axis/bpel/receivers/AeAxisReceiveHandlerFactory.java,v 1.1 2007/01/26 23:02:0
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
package org.activebpel.rt.axis.bpel.receivers; 

import org.activebpel.rt.axis.bpel.AeMessages;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.impl.IAeReceiveHandler;
import org.activebpel.rt.bpel.server.deploy.AeDeployConstants;
import org.activebpel.rt.bpel.server.engine.AeInvokeHandlerUri;
import org.activebpel.rt.bpel.server.engine.IAeReceiveHandlerFactory;

/**
 * A receive handler factory for Axis extensions to the AeSOAPReceiveHandler. 
 */
public class AeAxisReceiveHandlerFactory implements IAeReceiveHandlerFactory
{
   private static IAeReceiveHandler mRpcHandler = new AeRPCReceiveHandler();
   private static IAeReceiveHandler mMSGHandler = new AeMSGReceiveHandler();
   
   /**
    * Returns the appropriate receive handler for binding type. 
    * 
    * @see org.activebpel.rt.bpel.server.engine.IAeReceiveHandlerFactory#createReceiveHandler(org.activebpel.wsio.receive.IAeMessageContext)
    */
   public IAeReceiveHandler createReceiveHandler(String aProtocol) throws AeBusinessProcessException
   {
      String binding = AeInvokeHandlerUri.getInvokerString(aProtocol);
      if (AeDeployConstants.BIND_MSG.equals(binding))
      {
         return mMSGHandler;
      }
      else if (AeDeployConstants.BIND_RPC.equals(binding))
      {
         return mRpcHandler;
      }
      else if (AeDeployConstants.BIND_RPC_LIT.equals(binding))
      {
         return mRpcHandler;
      }
      else
      {
         throw new AeBusinessProcessException(AeMessages.format("AeAxisReceiveHandlerFactory.0", binding)); //$NON-NLS-1$
      }
   }   

}
 
