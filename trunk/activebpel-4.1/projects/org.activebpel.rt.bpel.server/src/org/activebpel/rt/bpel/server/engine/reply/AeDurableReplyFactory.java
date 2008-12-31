//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/reply/AeDurableReplyFactory.java,v 1.3 2007/01/25 21:38:1
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
package org.activebpel.rt.bpel.server.engine.reply;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.impl.reply.AeMissingReplyReceiver;
import org.activebpel.rt.bpel.impl.reply.IAeDurableReplyFactory;
import org.activebpel.rt.bpel.impl.reply.IAeDurableReplyInfo;
import org.activebpel.rt.bpel.impl.reply.IAeReplyReceiver;
import org.activebpel.rt.bpel.impl.reply.IAeReplyReceiverFactory;
import org.activebpel.rt.bpel.server.engine.AeEngineFactory;
import org.activebpel.rt.util.AeUtil;

/**
 * Default implementation of the durable reply factory.
 */
public class AeDurableReplyFactory implements IAeDurableReplyFactory
{   
   /**
    * Map containing <code>IAeReplyReceiver<code> factories, keyed by the factory prototype.
    */
   private Map replyFactoryMap;
   
   public AeDurableReplyFactory(Map aConfig) throws AeException
   {    
      replyFactoryMap = new HashMap();
      init(aConfig);
   }
   
   /**
    * Creates factory objects based on the configuration.
    * @param aConfig
    * @throws AeException
    */
   protected void init(Map aConfig) throws AeException
   {
      Iterator it = aConfig.keySet().iterator();
      while (it.hasNext() )
      {
         String protoType = (String) it.next(); 
         if (AeUtil.notNullOrEmpty(protoType))
         {
            Map factoryConfig = (Map) aConfig.get(protoType);
            IAeReplyReceiverFactory factory = (IAeReplyReceiverFactory) AeEngineFactory.createConfigSpecificClass(factoryConfig);
            getReplyFactoryMap().put( protoType.toLowerCase().trim(), factory) ;
         }
      }      
   }
     
   /**
    * @return Returns the replyFactoryMap.
    */
   protected Map getReplyFactoryMap()
   {
      return replyFactoryMap;
   }
   
   /**
    * Returns a <code>IAeReplyReceiverFactory</code> given the type.
    * @param type
    * @return
    */
   protected IAeReplyReceiverFactory getFactory(String aProtoType)
   {
      IAeReplyReceiverFactory rVal = null;
      if ( AeUtil.notNullOrEmpty( aProtoType ) )
      {
         rVal = (IAeReplyReceiverFactory) getReplyFactoryMap().get( aProtoType.toLowerCase().trim() );
      }
      return rVal;
   }

   /**  
    * Overrides method to method to create appropriate reply receiver.
    * Returns an AeMissingReplyReceiver if the reply type is not supported. 
    * @see org.activebpel.rt.bpel.impl.reply.IAeDurableReplyFactory#createReplyReceiver(long, java.lang.String, java.util.Map)
    */
   public IAeReplyReceiver createReplyReceiver(long aReplyId, IAeDurableReplyInfo aInfo)
         throws AeBusinessProcessException
   {      
      IAeReplyReceiverFactory factory = null;
      if (aInfo != null && AeUtil.notNullOrEmpty(aInfo.getType()) )
      {
         factory = getFactory( aInfo.getType() );
      }      
      if (factory != null)
      {
         try
         {
            return factory.createReplyReceiver(aInfo.getProperties());
         }
         catch(AeException e)
         {
            throw new AeBusinessProcessException(e.getLocalizedMessage(), e);
         }
      }
      else
      {
         return createMissingReplyReceiver(aReplyId);
      }
   }

   /** 
    * Overrides method to AeMissingReplyReceiverInstance.
    * @see org.activebpel.rt.bpel.impl.reply.IAeDurableReplyFactory#createMissingReplyReceiver(long)
    */
   public IAeReplyReceiver createMissingReplyReceiver(long aReplyId)
   {
      return new AeMissingReplyReceiver(aReplyId);
   }
}
