//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/receive/AeDelegatingReceiveHandlerFactory.java,v 1.1 2007/01/26 22:54:0
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
package org.activebpel.rt.bpel.server.engine.receive; 

import java.util.Map;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.impl.IAeReceiveHandler;
import org.activebpel.rt.bpel.server.engine.AeDelegatingHandlerFactory;
import org.activebpel.rt.bpel.server.engine.IAeReceiveHandlerFactory;
import org.activebpel.wsio.receive.IAeMessageContext;

/**
 * A receive handler factory that delegates to other factories based on the protocol in the URN. 
 */
public class AeDelegatingReceiveHandlerFactory extends AeDelegatingHandlerFactory implements IAeReceiveHandlerFactory
{
   /**
    * Base class Constructor loads the delegate factories from the protocol map in the config.
    * @param aConfig
    */
   public AeDelegatingReceiveHandlerFactory(Map aConfig) throws AeException
   {
      super(aConfig);
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.engine.IAeReceiveHandlerFactory#createReceiveHandler(org.activebpel.wsio.receive.IAeMessageContext)
    */
   public IAeReceiveHandler createReceiveHandler(String aProtocol) throws AeBusinessProcessException
   {
      IAeReceiveHandlerFactory delegate = (IAeReceiveHandlerFactory) getDelegate(aProtocol);
      return delegate.createReceiveHandler(aProtocol);
   }

   /**
    * Gets the factory to delegate the requests to.
    * @param aContext
    */
   protected IAeReceiveHandlerFactory getDelegate(IAeMessageContext aContext) throws AeBusinessProcessException 
   {
      return (IAeReceiveHandlerFactory) getDelegate(aContext.getReceiveHandler());
   }

}
 
