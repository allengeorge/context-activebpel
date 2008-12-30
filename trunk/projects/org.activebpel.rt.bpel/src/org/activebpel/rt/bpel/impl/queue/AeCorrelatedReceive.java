// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/queue/AeCorrelatedReceive.java,v 1.7 2006/09/07 15:06:2
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
package org.activebpel.rt.bpel.impl.queue;

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.def.AePartnerLinkOpKey;

/**
 * Base class for correlated queue objects. No behavior here, just providing
 * the common fields and getters and setters.
 */
public class AeCorrelatedReceive extends AeAbstractQueuedObject
{
   /** Correlation data of message queue entry */
   protected Map mCorrelation;
   /** Identifies the process QName that this queued object belongs to */
   protected QName mProcessName;

   /**
    * Accepts the common data required for a correlated exchange.
    *
    * @param aPartnerLinkOpKey
    * @param aProcessQName
    * @param aCorrelation
    */
   public AeCorrelatedReceive(AePartnerLinkOpKey aPartnerLinkOpKey, QName aProcessQName, Map aCorrelation)
   {
      super(aPartnerLinkOpKey);
      setCorrelation(aCorrelation);
      setProcessName(aProcessQName);
   }

   /**
    * Setter for the process name
    *
    * @param aProcessName
    */
   public void setProcessName(QName aProcessName)
   {
      mProcessName = aProcessName;
   }

   /**
    * Getter for the process name
    */
   public QName getProcessName()
   {
      return mProcessName;
   }

   /**
    * @return Map The correlation map for this entry.
    */
   public Map getCorrelation()
   {
      if (mCorrelation == null)
         setCorrelation(new HashMap());
      return mCorrelation;
   }

   /**
    * Setter for the correlation map
    * @param aMap
    */
   public void setCorrelation(Map aMap)
   {
      mCorrelation = aMap;
   }

   /**
    * A queue object that is correlated has its correlation data participate in
    * the evaluation of equality. Queue objects that are not correlated will
    * only perform the comparison against the plink, port type, and operation.
    */
   public boolean isCorrelated()
   {
      return mCorrelation != null && !getCorrelation().isEmpty();
   }
}
