// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/wsio/produce/AeAbstractMessageDataProducer.java,v 1.1 2006/08/03 23:33:0
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

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.AeMessageDataFactory;
import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.bpel.IAeVariable;
import org.activebpel.rt.bpel.impl.AeAbstractBpelObject;
import org.activebpel.rt.bpel.impl.activity.assign.AeCopyOperationContext;
import org.activebpel.rt.bpel.impl.activity.assign.IAeCopyOperationContext;
import org.activebpel.rt.bpel.impl.activity.wsio.AeAnonymousMessageVariable;
import org.activebpel.rt.message.AeMessagePartsMap;
import org.activebpel.rt.message.IAeMessageData;

/**
 * The base class for message data producers that provides access to a delegate
 * {@link org.activebpel.rt.bpel.impl.activity.wsio.produce.IAeMessageDataProducerContext}.
 */
public abstract class AeAbstractMessageDataProducer implements IAeMessageDataProducer, IAeMessageDataProducerContext
{
   /** The delegate message data producer context. */
   private final IAeMessageDataProducerContext mContext;

   /** An anonymous variable for wrapping outgoing message data. */
   private IAeVariable mAnonymousVariable;

   /** The copy operation context that accesses variables in the BPEL object. */
   private IAeCopyOperationContext mCopyOperationContext;

   /**
    * Constructs a message data producer with access to the given delegate
    * message data producer context.
    *
    * @param aContext
    */
   protected AeAbstractMessageDataProducer(IAeMessageDataProducerContext aContext)
   {
      mContext = aContext;
   }

   /**
    * Returns a new instance of the outgoing message data.
    */
   protected IAeMessageData createMessageData() throws AeBusinessProcessException
   {
      // We won't know the outgoing message type if we are replying with a fault
      // that was not defined as part of the WSDL operation. 
      // TODO (MF) When static analysis is updated, then remove this check and throw.
      if (getMessagePartsMap() == null)
      {
         throw new AeBusinessProcessException(AeMessages.getString("AeAbstractMessageDataProducer.ERROR_MissingMessagePartsMap")); //$NON-NLS-1$
      }

      return AeMessageDataFactory.instance().createMessageData(getMessagePartsMap().getMessageType());
   }

   /**
    * Returns an anonymous variable for wrapping outgoing message data.
    */
   protected IAeVariable getAnonymousVariable() throws AeBusinessProcessException
   {
      if (mAnonymousVariable == null)
      {
         mAnonymousVariable = new AeAnonymousMessageVariable(getMessagePartsMap());
      }
      
      return mAnonymousVariable;
   }

   /**
    * Returns the delegate message data producer context.
    */
   protected IAeMessageDataProducerContext getContext()
   {
      return mContext;
   }

   /**
    * Returns the copy operation context that accesses variables in the BPEL
    * object.
    */
   protected IAeCopyOperationContext getCopyOperationContext() throws AeBusinessProcessException
   {
      if (mCopyOperationContext == null)
      {
         mCopyOperationContext = new AeCopyOperationContext(getBpelObject());
      }
   
      return mCopyOperationContext;
   }

   /**
    * Returns the message type to produce.
    */
   protected QName getMessageType()
   {
      return getMessagePartsMap().getMessageType();
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
      return getContext().getBpelObject();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.wsio.produce.IAeMessageDataProducerContext#getMessagePartsMap()
    */
   public AeMessagePartsMap getMessagePartsMap()
   {
      return getContext().getMessagePartsMap();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.wsio.produce.IAeMessageDataProducerContext#getToPartDefs()
    */
   public Iterator getToPartDefs()
   {
      return getContext().getToPartDefs();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.wsio.produce.IAeMessageDataProducerContext#getVariable()
    */
   public IAeVariable getVariable()
   {
      return getContext().getVariable();
   }
}
