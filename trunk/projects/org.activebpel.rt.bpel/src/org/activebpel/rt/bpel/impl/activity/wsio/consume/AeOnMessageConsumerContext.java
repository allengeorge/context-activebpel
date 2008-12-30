// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/wsio/consume/AeOnMessageConsumerContext.java,v 1.3 2006/09/22 19:52:3
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
package org.activebpel.rt.bpel.impl.activity.wsio.consume;

import java.util.Iterator;

import org.activebpel.rt.bpel.IAeVariable;
import org.activebpel.rt.bpel.def.activity.support.AeOnMessageDef;
import org.activebpel.rt.bpel.impl.AeAbstractBpelObject;
import org.activebpel.rt.bpel.impl.activity.support.AeOnMessage;
import org.activebpel.rt.message.AeMessagePartsMap;

/**
 * Implements access to an <code>onMessage</code> implementation object for a
 * message data consumer.
 */
public class AeOnMessageConsumerContext implements IAeMessageDataConsumerContext
{
   /** The <code>onMessage</code> implementation object. */
   private final AeOnMessage mOnMessage;

   /**
    * Constructs the context for the given <code>onMessage</code> implementation
    * object.
    *
    * @param aOnMessage
    */
   public AeOnMessageConsumerContext(AeOnMessage aOnMessage)
   {
      mOnMessage = aOnMessage;
   }

   /**
    * Returns the <code>onMessage</code> definition object.
    */
   protected AeOnMessageDef getDef()
   {
      return (AeOnMessageDef) getOnMessage().getDefinition();
   }

   /**
    * Returns the <code>onMessage</code> implementation object.
    */
   protected AeOnMessage getOnMessage()
   {
      return mOnMessage;
   }

   /*===========================================================================
    * IAeMessageDataConsumerContext methods
    *===========================================================================
    */

   /**
    * @see org.activebpel.rt.bpel.impl.activity.wsio.consume.IAeMessageDataConsumerContext#getBpelObject()
    */
   public AeAbstractBpelObject getBpelObject()
   {
      return getOnMessage();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.wsio.consume.IAeMessageDataConsumerContext#getFromPartDefs()
    */
   public Iterator getFromPartDefs()
   {
      return getDef().getFromPartDefs();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.wsio.consume.IAeMessageDataConsumerContext#getMessagePartsMap()
    */
   public AeMessagePartsMap getMessagePartsMap()
   {
      return getDef().getConsumerMessagePartsMap();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.wsio.consume.IAeMessageDataConsumerContext#getVariable()
    */
   public IAeVariable getVariable()
   {
      return getOnMessage().findVariable(getDef().getVariable()); 
   }
}
