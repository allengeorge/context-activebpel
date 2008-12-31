//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/support/AeCorrelationsPatternImpl.java,v 1.2 2006/09/22 19:52:3
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

import org.activebpel.rt.bpel.def.AeCorrelationsDef;
import org.activebpel.rt.bpel.def.activity.support.AeCorrelationDef;
import org.activebpel.rt.bpel.impl.IAeBpelObject;
import org.activebpel.rt.util.AeFilteredIterator;

/**
 * Uses the pattern attribute when initiating or validating correlations sets 
 */
public class AeCorrelationsPatternImpl extends AeCorrelationsImpl
{
   /** if true, uses the request pattern correlations, otherwise uses the response pattern correlations */
   private boolean mRequest;
   
   /**
    * Ctor accepts the def, parent, and flag
    * @param aDef
    * @param aParent
    */
   public AeCorrelationsPatternImpl(AeCorrelationsDef aDef, IAeBpelObject aParent, boolean aRequest)
   {
      super(aDef, aParent);
      setRequest(aRequest);
   }
   
   /**
    * returns true if its a response
    */
   protected boolean isResponse()
   {
      return !isRequest();
   }

   /**
    * @return Returns the request.
    */
   protected boolean isRequest()
   {
      return mRequest;
   }

   /**
    * @param aRequest The request to set.
    */
   protected void setRequest(boolean aRequest)
   {
      mRequest = aRequest;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.support.IAeCorrelations#getCorrelationDefs()
    */
   public Iterator getCorrelationDefs()
   {
      return new AePatternFilteredIterator(super.getCorrelationDefs());
   }
   
   /**
    * filters the corrDefs to only include those that match our pattern
    */
   class AePatternFilteredIterator extends AeFilteredIterator
   {
      public AePatternFilteredIterator(Iterator aIterator)
      {
         super(aIterator);
      }

      /**
       * @see org.activebpel.rt.util.AeFilteredIterator#accept(java.lang.Object)
       */
      protected boolean accept(Object aObject)
      {
         AeCorrelationDef corrDef = (AeCorrelationDef) aObject;
         return ((isRequest() && corrDef.isRequestDataUsedForCorrelation()) 
               || isResponse() && corrDef.isResponseDataUsedForCorrelation());
      }
   }
}
 
