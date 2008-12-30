//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/transreceive/AeNoopTransmissionTracker.java,v 1.3 2006/07/25 17:37:4
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
package org.activebpel.rt.bpel.server.transreceive;

import java.util.Map;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.config.IAeEngineConfiguration;
import org.activebpel.rt.bpel.impl.reply.IAeDurableReplyFactory;
import org.activebpel.rt.bpel.impl.reply.IAeTransmissionTracker;
import org.activebpel.rt.bpel.server.engine.reply.AeDurableReplyFactory;
import org.activebpel.rt.util.AeLongSet;
import org.activebpel.wsio.invoke.IAeInvoke;

/**
 * Transmissiond Id tracker used in the in memory configuration.
 */
public class AeNoopTransmissionTracker implements IAeTransmissionTracker
{

   /**  Durable reply factory. */
   private IAeDurableReplyFactory mDurableReplyFactory;
   
   /**
    * Default ctor.
    * @param aConfig
    * @throws AeException
    */
   public AeNoopTransmissionTracker(Map aConfig) throws AeException
   {
      init(aConfig);
   }

   /**
    * Initialize the <code>IAeDurableReplyFactory</code> instance.
    * @param aConfig
    * @throws AeException
    */
   protected void init( Map aConfig ) throws AeException
   {
      Map factoryListConfig = (Map) aConfig.get( IAeEngineConfiguration.DURABLE_REPLY_FACTORIES );
      mDurableReplyFactory = new AeDurableReplyFactory(factoryListConfig);
   }  
   
   /**
    * @see org.activebpel.rt.bpel.impl.reply.IAeTransmissionTracker#getDurableReplyFactory()
    */
   public IAeDurableReplyFactory getDurableReplyFactory()
   {
      return mDurableReplyFactory;
   }

   /** 
    * Overrides method to return 1 
    * @see org.activebpel.rt.bpel.impl.reply.IAeTransmissionTracker#getNextId()
    */
   public long getNextId()
   {
      return 1;
   }

   /**
    *  
    * @see org.activebpel.rt.bpel.impl.reply.IAeTransmissionTracker#add(long, java.lang.String, int)
    */
   public void add(long aTransmissionId, String aMessageId, int aState) throws AeException
   {
   }

   /**
    * 
    * Overrides method to return false.
    * @see org.activebpel.rt.bpel.impl.reply.IAeTransmissionTracker#exists(long)
    */
   public boolean exists(long aTxId) throws AeException
   {
      return false;
   }

   /** 
    * Overrides method to return false. 
    * @see org.activebpel.rt.bpel.impl.reply.IAeTransmissionTracker#exists(long, int)
    */
   public boolean exists(long aTxId, int aState) throws AeException
   {
      return false;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.reply.IAeTransmissionTracker#update(long, int)
    */
   public void update(long aTxId, int aState) throws AeException
   {
   }

   /** 
    * Overrides method to return NULL_STATE. 
    * @see org.activebpel.rt.bpel.impl.reply.IAeTransmissionTracker#getState(long)
    */
   public int getState(long aTxId) throws AeException
   {
      return NULL_STATE;
   }

   /** 
    * Overrides method to return null. 
    * @see org.activebpel.rt.bpel.impl.reply.IAeTransmissionTracker#getMessageId(long)
    */
   public String getMessageId(long aTxId) throws AeException
   {
      return null;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.reply.IAeTransmissionTracker#remove(long)
    */
   public void remove(long aTxId) throws AeException
   {
   }

   /** 
    * @see org.activebpel.rt.bpel.impl.reply.IAeTransmissionTracker#remove(org.activebpel.rt.util.AeLongSet)
    */
   public void remove(AeLongSet aTransmissionIds) throws AeException
   {      
   }
   
   /**  
    * @see org.activebpel.rt.bpel.impl.reply.IAeTransmissionTracker#isTransmitted(org.activebpel.wsio.invoke.IAeInvoke)
    */
   public boolean isTransmitted(IAeInvoke aInvoke) throws AeException
   {
      return false;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.reply.IAeTransmissionTracker#assignTransmissionId(org.activebpel.wsio.invoke.IAeInvoke)
    */
   public void assignTransmissionId(IAeInvoke aInvoke) throws AeException
   {
   }
}
