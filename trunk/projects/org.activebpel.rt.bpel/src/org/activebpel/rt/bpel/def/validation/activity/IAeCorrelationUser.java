//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/activity/IAeCorrelationUser.java,v 1.3 2006/09/27 00:36:2
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
package org.activebpel.rt.bpel.def.validation.activity; 

import org.activebpel.rt.bpel.def.validation.IAeValidator;
import org.activebpel.rt.message.AeMessagePartsMap;

/**
 * interface for models that use correlation data
 */
public interface IAeCorrelationUser extends IAeValidator
{
   /**
    * Gets the message parts map for the message being consumed
    */
   public AeMessagePartsMap getConsumerMessagePartsMap();

   /**
    * Gets the message parts map for the message being produced
    */
   public AeMessagePartsMap getProducerMessagePartsMap();

   /**
    * Returns true if the pattern attribute is required to be used with this model (only supported for invokes)
    */
   public boolean isPatternRequired();
}
 
