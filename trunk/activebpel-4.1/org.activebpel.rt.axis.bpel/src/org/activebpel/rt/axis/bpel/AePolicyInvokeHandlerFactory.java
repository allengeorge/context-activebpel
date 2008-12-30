//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis.bpel/src/org/activebpel/rt/axis/bpel/AePolicyInvokeHandlerFactory.java,v 1.1 2006/05/15 21:25:5
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
public class AePolicyInvokeHandlerFactory implements IAeInvokeHandlerFactory
{
   private static final String POLICY_ERROR_MSG = AeMessages.getString("AePolicyInvokeHandlerFactory.0"); //$NON-NLS-1$

   /** The default invoke handler */
   private static final IAeInvokeHandler DEFAULT_HANDLER = new AeInvokeHandler();
   
   /**
    * @see org.activebpel.rt.bpel.server.engine.IAeInvokeHandlerFactory#createInvokeHandler(org.activebpel.wsio.invoke.IAeInvoke)
    */
   public IAeInvokeHandler createInvokeHandler(IAeInvoke aInvoke)
         throws AeBusinessProcessException
   {
      try 
      {
         return DEFAULT_HANDLER;
      }
      catch (Exception ae)
      {
        throw new AeBusinessProcessException(POLICY_ERROR_MSG, ae);
      }
  }
   
   /**
    * @see org.activebpel.rt.bpel.server.engine.IAeInvokeHandlerFactory#getQueryData(org.activebpel.wsio.invoke.IAeInvoke)
    */
   public String getQueryData(IAeInvoke aInvoke)
   {
      return AeInvokeHandlerUri.getInvokerString(aInvoke.getInvokeHandler());
   }
    
}
 
