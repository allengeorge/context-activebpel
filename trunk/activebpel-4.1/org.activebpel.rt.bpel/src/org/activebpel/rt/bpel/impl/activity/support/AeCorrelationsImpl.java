//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/support/AeCorrelationsImpl.java,v 1.4 2006/10/05 21:15:3
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
import org.activebpel.rt.bpel.def.AeCorrelationsDef;
import org.activebpel.rt.bpel.def.activity.support.AeCorrelationDef;
import org.activebpel.rt.bpel.impl.IAeBpelObject;
import org.activebpel.rt.message.AeMessagePartsMap;
import org.activebpel.rt.message.IAeMessageData;

/**
 * impl for the <correlations> used by a wsio activity
 */
public class AeCorrelationsImpl implements IAeCorrelations
{
   /** correlations def */
   private AeCorrelationsDef mDef;
   
   /** reference to our parent activity */
   private IAeBpelObject mParent;
   
   /**
    * Ctor takes the ref to the parent wsio activity
    * @param aParent
    */
   public AeCorrelationsImpl(AeCorrelationsDef aDef, IAeBpelObject aParent)
   {
      mParent = aParent;
      mDef = aDef;
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.activity.support.IAeCorrelations#initiateOrValidate(org.activebpel.rt.message.IAeMessageData, org.activebpel.rt.message.AeMessagePartsMap)
    */
   public void initiateOrValidate(IAeMessageData aData, AeMessagePartsMap aMessagePartsMap) throws AeBusinessProcessException
   {
      for(Iterator iter = getCorrelationDefs(); iter.hasNext(); )
      {
         AeCorrelationDef corrDef = (AeCorrelationDef)iter.next();
         String csName = corrDef.getCorrelationSetName();
         AeCorrelationSet corrSet = findCorrelationSet(csName);
         corrSet.initiateOrValidate(aData, aMessagePartsMap, corrDef.getInitiate());
      }
   }

   /**
    * Finds the correlation set through the parent
    * @param csName
    */
   protected AeCorrelationSet findCorrelationSet(String csName)
   {
      return getParent().findCorrelationSet(csName);
   }
   
   /**
    * Getter for the parent
    */
   protected IAeBpelObject getParent()
   {
      return mParent;
   }
   
   /**
    * Getter for the def
    */
   protected AeCorrelationsDef getDef()
   {
      return mDef;
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.activity.support.IAeCorrelations#getCorrelationDefs()
    */
   public Iterator getCorrelationDefs()
   {
      return getDef().getValues();
   }
}
