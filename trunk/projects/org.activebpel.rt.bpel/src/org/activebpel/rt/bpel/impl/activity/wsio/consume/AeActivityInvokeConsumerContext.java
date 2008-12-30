// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/wsio/consume/AeActivityInvokeConsumerContext.java,v 1.2 2006/09/11 23:06:2
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
import org.activebpel.rt.bpel.def.activity.AeActivityInvokeDef;
import org.activebpel.rt.bpel.impl.AeAbstractBpelObject;
import org.activebpel.rt.bpel.impl.activity.AeActivityInvokeImpl;
import org.activebpel.rt.message.AeMessagePartsMap;

/**
 * Implements access to an <code>invoke</code> activity for a message data
 * consumer.
 */
public class AeActivityInvokeConsumerContext implements IAeMessageDataConsumerContext
{
   /** The <code>invoke</code> activity implementation object. */
   private final AeActivityInvokeImpl mInvokeImpl;

   /**
    * Constructs the context for the given <code>invoke</code> activity
    * implementation object.
    *
    * @param aInvokeImpl
    */
   public AeActivityInvokeConsumerContext(AeActivityInvokeImpl aInvokeImpl)
   {
      mInvokeImpl = aInvokeImpl;
   }

   /**
    * Returns the <code>invoke</code> activity definition object.
    */
   protected AeActivityInvokeDef getDef()
   {
      return (AeActivityInvokeDef) getInvokeImpl().getDefinition();
   }

   /**
    * Returns the <code>invoke</code> activity implementation object.
    */
   protected AeActivityInvokeImpl getInvokeImpl()
   {
      return mInvokeImpl;
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
      return getInvokeImpl();
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
      return getInvokeImpl().getOutputVariable(); 
   }
}
