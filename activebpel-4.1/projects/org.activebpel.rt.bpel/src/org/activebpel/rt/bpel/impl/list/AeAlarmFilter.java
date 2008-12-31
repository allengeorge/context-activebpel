// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/list/AeAlarmFilter.java,v 1.3 2005/07/12 00:17:0
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

import javax.xml.namespace.QName;

/**
 * Wraps selection criteria for selecting message receivers
 * off of the queue.
 */
public class AeAlarmFilter extends AeListingFilter implements Serializable
{
   /** No selection criteria specified. */
   public static final AeAlarmFilter NULL_FILTER = new AeAlarmFilter();
   /** Default 'null' value for process id. */
   public static final long NULL_ID = -1;
   
   /** Process id. */
   private long mProcessId = NULL_ID;
   /** Specifies the process creation starting date range to be included in the results */
   private Date mAlarmFilterStart;
   /** Specifies the process creation ending date range to be included in the results */
   private Date mAlarmFilterEnd;
   /** The namespace qualified name of process we are looking for. */
   private QName mProcessName;
   
   /**
    * Constructor.
    */
   public AeAlarmFilter()
   {
      super();
   }
   
   /**
    * Accessor for process id.
    */
   public long getProcessId()
   {
      return mProcessId;
   }
   
   /**
    * Returns true if the process id criteria has not been set.
    */
   public boolean isNullProcessId()
   {
      return mProcessId == NULL_ID;
   }

   /**
    * Setter for the process id.
    * @param aId
    */
   public void setProcessId(long aId)
   {
      mProcessId = aId;
   }
   
   /**
    * Returns the alarm filter end date (or null if not set).
    */
   public Date getAlarmFilterEnd()
   {
      return mAlarmFilterEnd;
   }

   /**
    * Returns the alarm filter start date (or null if not set).
    */
   public Date getAlarmFilterStart()
   {
      return mAlarmFilterStart;
   }

   /**
    * Sets the alarm filter end date (or null if not set).
    */
   public void setAlarmFilterEnd(Date aDate)
   {
      mAlarmFilterEnd = aDate;
   }

   /**
    * Sets the alarm filter start date (or null if not set).
    */
   public void setAlarmFilterStart(Date aDate)
   {
      mAlarmFilterStart = aDate;
   }

   /**
    * Get the name of the process we are filtering on, null if none.
    */
   public QName getProcessName()
   {
      return mProcessName;
   }

   /**
    * Sets the name of the process we are filtering on, null if none.
    */
   public void setProcessName(QName aName)
   {
      mProcessName = aName;
   }

}
