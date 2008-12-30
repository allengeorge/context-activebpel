// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/wsio/produce/AeActivityReplyProducerContext.java,v 1.3 2006/09/22 19:52:3
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
package org.activebpel.rt.bpel.impl.activity.wsio.produce;

import java.util.Iterator;

import org.activebpel.rt.bpel.IAeVariable;
import org.activebpel.rt.bpel.def.activity.AeActivityReplyDef;
import org.activebpel.rt.bpel.impl.AeAbstractBpelObject;
import org.activebpel.rt.bpel.impl.activity.AeActivityReplyImpl;
import org.activebpel.rt.message.AeMessagePartsMap;

/**
 * Implements access to an <code>reply</code> activity for a message data
 * producer.
 */
public class AeActivityReplyProducerContext implements IAeMessageDataProducerContext
{
   /** The <code>reply</code> activity implementation object. */
   private final AeActivityReplyImpl mReplyImpl;

   /**
    * Constructs the context for the given <code>reply</code> activity
    * implementation object.
    *
    * @param aReplyImpl
    */
   public AeActivityReplyProducerContext(AeActivityReplyImpl aReplyImpl)
   {
      mReplyImpl = aReplyImpl;
   }

   /**
    * Returns the <code>reply</code> activity definition object.
    */
   protected AeActivityReplyDef getDef()
   {
      return (AeActivityReplyDef) getReplyImpl().getDefinition();
   }

   /**
    * Returns the <code>reply</code> activity implementation object.
    */
   protected AeActivityReplyImpl getReplyImpl()
   {
      return mReplyImpl;
   }

   /*===========================================================================
    * IAeMessageDataProducerContext methods
    *===========================================================================
    */

   /**
    * @see org.activebpel.rt.bpel.impl.activity.wsio.produce.IAeMessageDataProducerContext#getBpelObject()
    */
   public AeAbstractBpelObject getBpelObject()
   {
      return getReplyImpl();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.wsio.produce.IAeMessageDataProducerContext#getMessagePartsMap()
    */
   public AeMessagePartsMap getMessagePartsMap()
   {
      return getDef().getProducerMessagePartsMap();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.wsio.produce.IAeMessageDataProducerContext#getToPartDefs()
    */
   public Iterator getToPartDefs()
   {
      return getDef().getToPartDefs();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.wsio.produce.IAeMessageDataProducerContext#getVariable()
    */
   public IAeVariable getVariable()
   {
      return getReplyImpl().findVariable(getDef().getVariable());
   }
}
