//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/AeWSIOActivityImpl.java,v 1.2 2006/08/03 23:32:1
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
package org.activebpel.rt.bpel.impl.activity; 

import org.activebpel.rt.bpel.def.AeActivityDef;
import org.activebpel.rt.bpel.impl.IAeActivityParent;
import org.activebpel.rt.bpel.impl.IAeMessageValidator;
import org.activebpel.rt.bpel.impl.activity.support.IAeCorrelations;
import org.activebpel.rt.bpel.impl.activity.wsio.consume.IAeMessageDataConsumer;
import org.activebpel.rt.bpel.impl.activity.wsio.produce.IAeMessageDataProducer;

/**
 * Base class for some of the WSIO activities.
 */
public abstract class AeWSIOActivityImpl extends AeActivityImpl implements IAeWSIOActivity
{
   /** request correlations */
   protected IAeCorrelations mRequestCorrelations;
   /** response correlations */
   protected IAeCorrelations mResponseCorrelations;
   /** validates the variable */
   private IAeMessageValidator mMessageValidator;
   /** consumes incoming message data */
   private IAeMessageDataConsumer mMessageDataConsumer;
   /** produces outgoing message data */
   private IAeMessageDataProducer mMessageDataProducer;

   /**
    * Ctor
    * @param aActivityDef
    * @param aParent
    */
   public AeWSIOActivityImpl(AeActivityDef aActivityDef, IAeActivityParent aParent)
   {
      super(aActivityDef, aParent);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.IAeWSIOActivity#setMessageValidator(org.activebpel.rt.bpel.impl.IAeMessageValidator)
    */
   public void setMessageValidator(IAeMessageValidator aValidator)
   {
      mMessageValidator = aValidator;
   }

   /**
    * Getter for the message validator
    */
   protected IAeMessageValidator getMessageValidator()
   {
      return mMessageValidator;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.IAeWSIOActivity#setRequestCorrelations(org.activebpel.rt.bpel.impl.activity.support.IAeCorrelations)
    */
   public void setRequestCorrelations(IAeCorrelations aCorrelations)
   {
      mRequestCorrelations = aCorrelations;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.IAeWSIOActivity#setResponseCorrelations(org.activebpel.rt.bpel.impl.activity.support.IAeCorrelations)
    */
   public void setResponseCorrelations(IAeCorrelations aCorrelations)
   {
      mResponseCorrelations = aCorrelations;
   }
   
   /**
    * Getter for the request correlations
    */
   protected IAeCorrelations getRequestCorrelations()
   {
      return mRequestCorrelations;
   }
   
   /**
    * Getter for the response correlations
    */
   protected IAeCorrelations getResponseCorrelations()
   {
      return mResponseCorrelations;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.IAeWSIOActivity#setMessageDataConsumer(org.activebpel.rt.bpel.impl.activity.wsio.consume.IAeMessageDataConsumer)
    */
   public void setMessageDataConsumer(IAeMessageDataConsumer aMessageDataConsumer)
   {
      mMessageDataConsumer = aMessageDataConsumer;
   }

   /**
    * Getter for the message data consumer
    */
   protected IAeMessageDataConsumer getMessageDataConsumer()
   {
      return mMessageDataConsumer;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.IAeWSIOActivity#setMessageDataProducer(org.activebpel.rt.bpel.impl.activity.wsio.produce.IAeMessageDataProducer)
    */
   public void setMessageDataProducer(IAeMessageDataProducer aMessageDataProducer)
   {
      mMessageDataProducer = aMessageDataProducer;
   }

   /**
    * Getter for the message data producer
    */
   protected IAeMessageDataProducer getMessageDataProducer()
   {
      return mMessageDataProducer;
   }
}
 
