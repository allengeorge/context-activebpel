// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/wsio/consume/AeAbstractMessageDataConsumer.java,v 1.1 2006/08/03 23:33:0
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

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeVariable;
import org.activebpel.rt.bpel.impl.AeAbstractBpelObject;
import org.activebpel.rt.bpel.impl.activity.assign.AeAtomicCopyOperationContext;
import org.activebpel.rt.bpel.impl.activity.wsio.AeAnonymousMessageVariable;
import org.activebpel.rt.message.AeMessagePartsMap;
import org.activebpel.rt.message.IAeMessageData;

/**
 * The base class for message data consumers that provides access to a delegate
 * {@link org.activebpel.rt.bpel.impl.activity.wsio.consume.IAeMessageDataConsumerContext}.
 */
public abstract class AeAbstractMessageDataConsumer implements IAeMessageDataConsumer, IAeMessageDataConsumerContext
{
   /** The delegate message data consumer context. */
   protected final IAeMessageDataConsumerContext mContext;

   /**
    * The copy operation context that accesses variables in the BPEL object and
    * that can rollback changes made made to those variables.
    */
   private AeAtomicCopyOperationContext mAtomicCopyOperationContext;

   /**
    * Constructs a message data consumer with access to the given delegate
    * message data consumer context.
    *
    * @param aContext
    */
   protected AeAbstractMessageDataConsumer(IAeMessageDataConsumerContext aContext)
   {
      mContext = aContext;
   }

   /**
    * Returns an anonymous variable that wraps the given message data.
    *
    * @param aMessageData
    */
   protected IAeVariable createAnonymousVariable(IAeMessageData aMessageData) throws AeBusinessProcessException
   {
      IAeVariable variable = new AeAnonymousMessageVariable(getMessagePartsMap());
      variable.setMessageData(aMessageData);
      
      return variable;
   }

   /**
    * Returns the copy operation context that accesses variables in the BPEL
    * object and that can rollback changes made to those variables.
    */
   protected AeAtomicCopyOperationContext getAtomicCopyOperationContext()
   {
      if (mAtomicCopyOperationContext == null)
      {
         mAtomicCopyOperationContext = new AeAtomicCopyOperationContext(getBpelObject());
      }
      
      return mAtomicCopyOperationContext;
   }

   /**
    * Returns the delegate message data consumer context.
    */
   protected IAeMessageDataConsumerContext getContext()
   {
      return mContext;
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
      return getContext().getBpelObject();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.wsio.consume.IAeMessageDataConsumerContext#getFromPartDefs()
    */
   public Iterator getFromPartDefs()
   {
      return getContext().getFromPartDefs();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.wsio.consume.IAeMessageDataConsumerContext#getMessagePartsMap()
    */
   public AeMessagePartsMap getMessagePartsMap()
   {
      return getContext().getMessagePartsMap();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.wsio.consume.IAeMessageDataConsumerContext#getVariable()
    */
   public IAeVariable getVariable()
   {
      return getContext().getVariable();
   }
}
