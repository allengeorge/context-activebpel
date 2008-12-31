//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/activity/IAeMessageDataConsumerDef.java,v 1.2 2006/09/11 23:06:2
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
import org.activebpel.rt.bpel.def.activity.support.AeFromPartsDef;
import org.activebpel.rt.message.AeMessagePartsMap;

/**
 * interface for wsio activity defs that consume message data  
 */
public interface IAeMessageDataConsumerDef
{
   /**
    * Gets the variable def that is being received into or null if not present
    */
   public AeVariableDef getMessageDataConsumerVariable();

   /**
    * Gets the from parts def or null if not present
    */
   public AeFromPartsDef getFromPartsDef();

   /**
    * Setter for the strategy name
    * @param aStrategy
    */
   public void setMessageDataConsumerStrategy(String aStrategy);

   /**
    * Getter for the strategy name
    */
   public String getMessageDataConsumerStrategy();
   
   /**
    * Returns the message parts map for the request
    */
   public AeMessagePartsMap getConsumerMessagePartsMap();
}
 
