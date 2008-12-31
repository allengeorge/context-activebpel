// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/admin/rdebug/server/AeBreakpointList.java,v 1.1 2004/12/02 00:01:4
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
package org.activebpel.rt.bpel.server.admin.rdebug.server;

import java.io.Serializable;


/**
 * Wrapper for the list of breakpoints sent by the designer during remote debugging.
 */
public class AeBreakpointList implements Serializable
{
   /** The total number of rows in the list of breakpoints. */
   private int mTotalRowCount;
   
   /** The detail rows for the list of breakpoints. */
   private AeBreakpointInstanceDetail[] mRowDetails;

   /**
    * No-arg constructor
    */
   public AeBreakpointList()
   {
   }

   /**
    * Returns the row details of the breakpoint definitions.
    */
   public AeBreakpointInstanceDetail[] getRowDetails()
   {
      return mRowDetails;
   }

   /**
    * Returns the total number of rows containing breakpoint definitions.
    */
   public int getTotalRowCount()
   {
      return mTotalRowCount;
   }
   
   /**
    * Sets the detail list in the breakpoint set.
    * @param aDetails breakpoint list details to be set
    */
   public void setRowDetails(AeBreakpointInstanceDetail[] aDetails)
   {
      mRowDetails = aDetails;
   }

   /**
    * Sets the total row count for the breakpoint list.
    * @param aTotalRows total rows to be set
    */
   public void setTotalRowCount(int aTotalRows)
   {
      mTotalRowCount = aTotalRows;
   }
}
