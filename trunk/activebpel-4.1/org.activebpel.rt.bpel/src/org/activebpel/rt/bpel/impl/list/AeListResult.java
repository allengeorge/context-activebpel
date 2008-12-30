//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/list/AeListResult.java,v 1.4 2005/02/19 00:43:5
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
package org.activebpel.rt.bpel.impl.list;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Base class for list results.
 */
public class AeListResult implements Serializable
{
   /** The total number of rows which match the process list request. */
   protected int mTotalRowCount;

   /** <code>true</code> if and only if <code>mTotalRowCount</code> is the true total row count. */
   protected boolean mCompleteRowCount = true;

   /** Container for the results. */
   protected List mResults = new ArrayList();

   /**
    * Default constructor for WebLogic bean serializer.
    */
   public AeListResult()
   {
   }

   /**
    * Constructor.
    * 
    * @param aTotalRowCount
    * @param aResults
    * @param aCompleteRowCount
    */
   protected AeListResult(int aTotalRowCount, Collection aResults, boolean aCompleteRowCount)
   {
      setTotalRowCount(aTotalRowCount);
      getResultsInternal().addAll(aResults);
      setCompleteRowCount(aCompleteRowCount);
   }

   /**
    * Returns the total number of rows which match the filter set used to
    * obtain the results. May actually be larger than the detail list of processes.
    */
   public int getTotalRowCount()
   {
      return mTotalRowCount;
   }

   /**
    * Sets the total the total number of rows which match the filter.
    * May actually be larger than the detail list of processes.
    */
   public void setTotalRowCount(int aTotalRowCount)
   {
      mTotalRowCount = aTotalRowCount;
   }

   /**
    * Returns <code>true</code> if and only if <code>getTotalRowCount()</code> reports
    * the true total row count.
    */
   public boolean isCompleteRowCount()
   {
      return mCompleteRowCount;
   }

   /**
    * Sets flag indicating whether {@link #getTotalRowCount()}returns
    * the true total row count.
    */
   public void setCompleteRowCount(boolean aCompleteRowCount)
   {
      mCompleteRowCount = aCompleteRowCount;
   }

   /**
    * Return true if there are no results in the listing.
    */
   public boolean isEmpty()
   {
      return getResultsInternal().isEmpty();
   }

   /**
    * Accessor for results container.
    */
   protected List getResultsInternal()
   {
      return mResults;
   }
}
