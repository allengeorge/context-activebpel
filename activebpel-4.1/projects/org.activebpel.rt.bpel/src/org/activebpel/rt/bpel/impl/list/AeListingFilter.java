//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/list/AeListingFilter.java,v 1.5 2006/01/31 20:14:1
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
import java.util.Date;

import org.activebpel.rt.util.AeDate;

/**
 * Base class for filtering operations.
 */
public class AeListingFilter implements IAeListingFilter, Serializable
{
   /** The maximum set of results to be returned by this call, 0 is unlimited */
   private int mMaxReturn;
   /** The row number to start fetching results if a max return size is specified. */
   private int mListStart;
   
   /**
    * Convenience method that returns true if the value passed in is within the
    * range of rows that we're looking.  
    * @param aRowCount
    */
   public boolean isWithinRange(int aRowCount)
   {
      if (aRowCount > getListStart())
      {
         // figure out how many rows have actually been returned
         // at this point
         int actualNumberReturned = aRowCount - getListStart();

         // we've skipped passed the number of rows we're supposed to
         // we'll accept this row if it's less than our max or if our
         // max is 0
         return getMaxReturn() == 0 || actualNumberReturned <= getMaxReturn();
      }
      return false;
   }
   
   /**
    * Returns the maximum number of results to be returned.
    */
   public int getMaxReturn()
   {
      return mMaxReturn;
   }

   /**
    * Sets the maximum number of results to be returned.
    * @param aMaxReturn maximum results to be returned.
    */
   public void setMaxReturn(int aMaxReturn)
   {
      mMaxReturn = aMaxReturn;
   }

   /**
    * Returns the starting position from which to return results.
    */
   public int getListStart()
   {
      return mListStart;
   }

   /**
    * Sets the starting position from which to return results.
    * @param aListStart starting position from which to return results
    */
   public void setListStart(int aListStart)
   {
      mListStart = aListStart;
   }

   /**
    * Returns the start of the day following the specified date.
    */
   protected Date getNextDay(Date aDate)
   {
      return AeDate.getStartOfNextDay(aDate);
   }
}
 
