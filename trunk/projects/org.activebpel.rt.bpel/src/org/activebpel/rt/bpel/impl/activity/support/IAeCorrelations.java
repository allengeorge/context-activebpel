//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/support/IAeCorrelations.java,v 1.4 2006/10/26 14:01:3
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
package org.activebpel.rt.bpel.impl.activity.support;

import java.util.Iterator;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.message.AeMessagePartsMap;
import org.activebpel.rt.message.IAeMessageData;

/**
 * Interface that encapsulates the initiation or validation of an activity's
 * correlations from a message.
 */
public interface IAeCorrelations
{
   /**
    * Initiates or validates the correlation sets with the message data
    * @param aData the data for the message
    * @param aMessagePartsMap the definition of the message parts
    * @throws AeBusinessProcessException
    */
   public void initiateOrValidate(IAeMessageData aData, AeMessagePartsMap aMessagePartsMap)
         throws AeBusinessProcessException;
   
   /**
    * Getter for the correlation defs
    */
   public Iterator getCorrelationDefs();
}
