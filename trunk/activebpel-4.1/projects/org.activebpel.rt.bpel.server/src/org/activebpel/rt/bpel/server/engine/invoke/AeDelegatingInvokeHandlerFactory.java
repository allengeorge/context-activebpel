//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/invoke/AeDelegatingInvokeHandlerFactory.java,v 1.3 2007/01/26 22:54:0
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
package org.activebpel.rt.bpel.server.engine.invoke; 

import java.util.Map;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.server.AeMessages;
import org.activebpel.rt.bpel.server.engine.AeDelegatingHandlerFactory;
import org.activebpel.rt.bpel.server.engine.IAeInvokeHandlerFactory;
import org.activebpel.wsio.invoke.IAeInvoke;
import org.activebpel.wsio.invoke.IAeInvokeHandler;

/**
 * An invoke handler factory that delegates to other factories based on the protocol in the URN. 
 */
public class AeDelegatingInvokeHandlerFactory extends AeDelegatingHandlerFactory implements IAeInvokeHandlerFactory
{
   
   /**
    * Base Class constructor loads the delegate factories from the protocol map in the config.
    * @param aConfig
    */
   public AeDelegatingInvokeHandlerFactory(Map aConfig) throws AeException
   {
      super(aConfig);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.IAeInvokeHandlerFactory#createInvokeHandler(org.activebpel.wsio.invoke.IAeInvoke)
    */
   public IAeInvokeHandler createInvokeHandler(IAeInvoke aInvoke)
         throws AeBusinessProcessException
   {
      IAeInvokeHandlerFactory delegate = getDelegate(aInvoke);
      if (delegate == null)
      {
         Object[] args = new Object[2];
         args[0] = getProtocol(aInvoke.getInvokeHandler());
         args[1] = aInvoke.getInvokeHandler();
         throw new AeBusinessProcessException(AeMessages.format("AeDelegatingHandlerFactory.MissingHandlerFactory", args)); //$NON-NLS-1$
      }
      return delegate.createInvokeHandler(aInvoke);
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.engine.IAeInvokeHandlerFactory#getQueryData(org.activebpel.wsio.invoke.IAeInvoke)
    */
   public String getQueryData(IAeInvoke aInvoke)
   {
      try
      {
         IAeInvokeHandlerFactory delegate = getDelegate(aInvoke);
         return delegate.getQueryData(aInvoke);
      }
      catch (AeBusinessProcessException e)
      {
         throw new IllegalStateException(e.getLocalizedMessage());
      }
   }

   /**
    * Gets the factory to delegate the requests to.
    * @param aInvoke
    */
   protected IAeInvokeHandlerFactory getDelegate(IAeInvoke aInvoke) throws AeBusinessProcessException 
   {
      return (IAeInvokeHandlerFactory) getDelegate(aInvoke.getInvokeHandler());
   }
}
 
