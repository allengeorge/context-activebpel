//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis.bpel/src/org/activebpel/rt/axis/bpel/AeAxisInvokeHandlerFactory.java,v 1.1 2005/06/22 17:10:1
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
package org.activebpel.rt.axis.bpel; 

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.server.engine.AeInvokeHandlerUri;
import org.activebpel.rt.bpel.server.engine.IAeInvokeHandlerFactory;
import org.activebpel.wsio.invoke.IAeInvoke;
import org.activebpel.wsio.invoke.IAeInvokeHandler;

/**
 * Factory for the default axis invoke handler.
 */
public class AeAxisInvokeHandlerFactory implements IAeInvokeHandlerFactory
{
   /** The invoke handler used to delegate our requests to */
   private static final IAeInvokeHandler HANDLER = new AeInvokeHandler();

   /**
    * @see org.activebpel.rt.bpel.server.engine.IAeInvokeHandlerFactory#createInvokeHandler(org.activebpel.wsio.invoke.IAeInvoke)
    */
   public IAeInvokeHandler createInvokeHandler(IAeInvoke aInvoke)
         throws AeBusinessProcessException
   {
      return HANDLER;
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.engine.IAeInvokeHandlerFactory#getQueryData(org.activebpel.wsio.invoke.IAeInvoke)
    */
   public String getQueryData(IAeInvoke aInvoke)
   {
      return AeInvokeHandlerUri.getInvokerString(aInvoke.getInvokeHandler());
   }
}
 
