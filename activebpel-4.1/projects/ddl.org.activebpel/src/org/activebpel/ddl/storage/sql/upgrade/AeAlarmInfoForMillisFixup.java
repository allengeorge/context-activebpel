//$Header: /Development/AEDevelopment/projects/ddl.org.activebpel/src/org/activebpel/ddl/storage/sql/upgrade/AeAlarmInfoForMillisFixup.java,v 1.1 2005/03/17 21:44:2
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
package org.activebpel.ddl.storage.sql.upgrade;

import java.util.Date;

/**
 * Represents alarm information found in the database prior to populating the DeadlineMillis column. Used
 * during upgrade from version 1.0 to version 1.1 of ActiveBPEL.
 */
public class AeAlarmInfoForMillisFixup
{
   /** The alarm's process id. */
   private long mProcessId;
   /** The alarm's location path id. */
   private int mLocPathId;
   /** The alarm's deadline. */
   private Date mDeadline;

   /**
    * Simple constructor.
    * 
    * @param aProcessId
    * @param aLocPathId
    * @param aDeadline
    */
   public AeAlarmInfoForMillisFixup(long aProcessId, int aLocPathId, Date aDeadline)
   {
      setProcessId(aProcessId);
      setLocPathId(aLocPathId);
      setDeadline(aDeadline);
   }

   /**
    * Getter for the deadline.
    */
   public Date getDeadline()
   {
      return mDeadline;
   }

   /**
    * Setter for the deadline.
    * 
    * @param aDeadline
    */
   public void setDeadline(Date aDeadline)
   {
      mDeadline = aDeadline;
   }

   /**
    * Getter for the location path id.
    */
   public int getLocPathId()
   {
      return mLocPathId;
   }

   /**
    * Setter for the location path id.
    * 
    * @param aLocPathId
    */
   public void setLocPathId(int aLocPathId)
   {
      mLocPathId = aLocPathId;
   }

   /**
    * Getter for the process id.
    */
   public long getProcessId()
   {
      return mProcessId;
   }

   /**
    * Setter for the process id.
    * 
    * @param aProcessId
    */
   public void setProcessId(long aProcessId)
   {
      mProcessId = aProcessId;
   }
}
