// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/list/AeProcessListResult.java,v 1.6 2005/02/19 00:43:5
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
import java.util.Arrays;
import java.util.Collection;

/**
 * Wrapper for the result set returned from the engine administration function
 * of getProcessList(). The total rows reflect the number of rows which match the
 * filter criteria we passed in. This may actually be greater than the number of
 * rows set in the detail list, based upon input settings from the filter.
 */
public class AeProcessListResult extends AeListResult implements Serializable
{
   /** Empty details for a default constructor. */
   private static AeProcessInstanceDetail[] EMPTY_DETAILS = new AeProcessInstanceDetail[0];
   
   /**
    * WS Workaround, empty constructor to make bean deserializers happy.
    */
   public AeProcessListResult()
   {
      this(0, EMPTY_DETAILS);
   }
   
   /**
    * Constructor which creates the result set wrapper from a process list request.
    * @param aTotalRows total rows which matched our search request
    * @param aRowDetails array of process instance details from our search request.
    */
   public AeProcessListResult(int aTotalRows, AeProcessInstanceDetail[] aRowDetails)
   {
      this(aTotalRows, aRowDetails, true);
   }

   /**
    * Constructor which creates the result set wrapper from a process list request.
    * @param aTotalRows total rows which matched our search request
    * @param aRowDetails array of process instance details from our search request.
    * @param aCompleteRowCount <code>true</code> if and only if <code>aTotalRows</code> is the true total row count
    */
   public AeProcessListResult(int aTotalRows, AeProcessInstanceDetail[] aRowDetails, boolean aCompleteRowCount)
   {
      this( aTotalRows, Arrays.asList(aRowDetails), aCompleteRowCount );
   }

   /**
    * Constructor.
    *
    * @param aTotalRows total rows which matched our search request
    * @param aCollection A <code>Collection</code> of <code>AeProcessInstanceDetail</code> instances.
    */
   public AeProcessListResult(int aTotalRows, Collection aCollection)
   {
      this(aTotalRows, aCollection, true);
   }

   /**
    * Constructor.
    *
    * @param aTotalRows total rows which matched our search request
    * @param aCollection A <code>Collection</code> of <code>AeProcessInstanceDetail</code> instances.
    * @param aCompleteRowCount <code>true</code> if and only if <code>aTotalRows</code> is the true total row count
    */
   public AeProcessListResult(int aTotalRows, Collection aCollection, boolean aCompleteRowCount)
   {
      super(aTotalRows, aCollection, aCompleteRowCount);
   }

   /**
    * Returns the row details of the process list results.
    */
   public AeProcessInstanceDetail[] getRowDetails()
   {
      return (AeProcessInstanceDetail[]) getResultsInternal().toArray( new AeProcessInstanceDetail[getResultsInternal().size()] );
   }
   
   /**
    * WS Workaround, dummy entry for bean serializer.
    */
   public void setRowDetails(AeProcessInstanceDetail[] aDetails)
   {
      getResultsInternal().addAll(Arrays.asList(aDetails));
   }

   /**
    * WS Workaround, entry point for bean serializer.
    */
   public int getTotalRowCount()
   {
      // TODO (MF) remove these workaround methods and provide a custom helper class for serialization/deserialization
      return super.getTotalRowCount();
   }

   /**
    * WS Workaround, entry point for bean serializer.
    */
   public void setTotalRowCount(int aTotalRowCount)
   {
      super.setTotalRowCount(aTotalRowCount);
   }
   
   /**
    * WS Workaround, entry point for bean serializer.
    * @see org.activebpel.rt.bpel.impl.list.AeListResult#isCompleteRowCount()
    */
   public boolean isCompleteRowCount()
   {
      return super.isCompleteRowCount();
   }
   
   /**
    * WS Workaround, entry point for bean serializer.
    * @see org.activebpel.rt.bpel.impl.list.AeListResult#isEmpty()
    */
   public boolean isEmpty()
   {
      return super.isEmpty();
   }

   /**
    * WS Workaround, entry point for bean serializer.
    * @param aIgnored
    */
   public void setEmpty(boolean aIgnored)
   {
      // ignored
   }
   
   /**
    * WS Workaround, entry point for bean serializer.
    *
    * @see org.activebpel.rt.bpel.impl.list.AeListResult#setCompleteRowCount(boolean)
    */
   public void setCompleteRowCount(boolean aCompleteRowCount)
   {
      super.setCompleteRowCount(aCompleteRowCount);
   }
}
