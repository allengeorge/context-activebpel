//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/IAeWSIOActivity.java,v 1.2 2006/08/03 23:32:1
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

import org.activebpel.rt.bpel.impl.IAeBpelObject;
import org.activebpel.rt.bpel.impl.IAeMessageValidator;
import org.activebpel.rt.bpel.impl.activity.support.IAeCorrelations;
import org.activebpel.rt.bpel.impl.activity.wsio.consume.IAeMessageDataConsumer;
import org.activebpel.rt.bpel.impl.activity.wsio.produce.IAeMessageDataProducer;

/**
 * interface for the web service in/out activities: receive, reply, invoke, onMessage, onEvent
 */
public interface IAeWSIOActivity extends IAeBpelObject
{
   /**
    * Setter for the message validator
    * @param aValidator
    */
   public void setMessageValidator(IAeMessageValidator aValidator);
   
   /**
    * Setter for the request correlations
    * @param aCorrelations
    */
   public void setRequestCorrelations(IAeCorrelations aCorrelations);
   
   /**
    * Setter for the response correlations
    * @param aCorrelations
    */
   public void setResponseCorrelations(IAeCorrelations aCorrelations);

   /**
    * Setter for the message data consumer.
    * @param aMessageDataConsumer
    */
   public void setMessageDataConsumer(IAeMessageDataConsumer aMessageDataConsumer);
   
   /**
    * Setter for the message data producer.
    * @param aMessageDataProducer
    */
   public void setMessageDataProducer(IAeMessageDataProducer aMessageDataProducer);
}
 
