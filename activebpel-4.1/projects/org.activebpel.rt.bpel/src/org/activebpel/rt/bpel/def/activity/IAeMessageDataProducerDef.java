//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/activity/IAeMessageDataProducerDef.java,v 1.2 2006/09/11 23:06:2
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
package org.activebpel.rt.bpel.def.activity; 

import org.activebpel.rt.bpel.def.AeVariableDef;
import org.activebpel.rt.bpel.def.activity.support.AeToPartsDef;
import org.activebpel.rt.message.AeMessagePartsMap;

/**
 * interface for the wsio activity defs that produce message data 
 */
public interface IAeMessageDataProducerDef
{
   /**
    * Getter for the variable def if the activity uses a variable to produce message data
    */
   public AeVariableDef getMessageDataProducerVariable();

   /**
    * Getter for the to parts def if the variable is using the toParts construct
    */
   public AeToPartsDef getToPartsDef();

   /**
    * Setter for the strategy name
    * @param aStrategy
    */
   public void setMessageDataProducerStrategy(String aStrategy);
   
   /**
    * Getter for the strategy name
    */
   public String getMessageDataProducerStrategy();

   /**
    * Returns the message parts map for the response
    */
   public AeMessagePartsMap getProducerMessagePartsMap();
}
 
