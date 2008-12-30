//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/support/AeWSBPELCorrelationSet.java,v 1.2 2006/10/05 21:15:3
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

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.def.AeCorrelationSetDef;
import org.activebpel.rt.bpel.def.activity.support.AeCorrelationDef;
import org.activebpel.rt.bpel.impl.AeCorrelationViolationException;
import org.activebpel.rt.bpel.impl.activity.AeActivityScopeImpl;
import org.activebpel.rt.message.AeMessagePartsMap;
import org.activebpel.rt.message.IAeMessageData;
import org.activebpel.rt.util.AeUtil;

/**
 * Extension of the correlation set impl object that implments the rules for initiating/validating
 * a correlation set for WS-BPEL 2.0 
 */
public class AeWSBPELCorrelationSet extends AeCorrelationSet
{

   /**
    * Ctor
    * @param aDef
    * @param aParent
    */
   public AeWSBPELCorrelationSet(AeCorrelationSetDef aDef, AeActivityScopeImpl aParent)
   {
      super(aDef, aParent);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.support.AeCorrelationSet#initiateOrValidate(org.activebpel.rt.message.IAeMessageData, org.activebpel.rt.message.AeMessagePartsMap, java.lang.String)
    */
   public void initiateOrValidate(IAeMessageData aMessageData, AeMessagePartsMap aMessagePartsMap, String aInitiateValue) throws AeBusinessProcessException
   {
      if (AeCorrelationDef.INITIATE_YES.equals(aInitiateValue))
      {
         if (isInitialized())
         {
            throw new AeCorrelationViolationException(getBPELNamespace(), AeCorrelationViolationException.ALREADY_INITIALIZED );
         }
         else
         {
            initiate(aMessageData, aMessagePartsMap);
         }
      }
      else if (AeCorrelationDef.INITIATE_NO.equals(aInitiateValue) || AeUtil.isNullOrEmpty(aInitiateValue))
      {
         // Note: the validate method will throw if the correlationSet is uninitialized
         validate(aMessageData, aMessagePartsMap);
      }
      else if (AeCorrelationDef.INITIATE_JOIN.equals(aInitiateValue))
      {
         if (isInitialized())
         {
            validate(aMessageData, aMessagePartsMap);
         }
         else
         {
            initiate(aMessageData, aMessagePartsMap);
         }
      }
   }
}
 
