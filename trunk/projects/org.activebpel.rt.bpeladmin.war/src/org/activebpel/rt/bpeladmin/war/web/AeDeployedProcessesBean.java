// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/web/AeDeployedProcessesBean.java,v 1.1 2004/08/19 16:19:2
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
package org.activebpel.rt.bpeladmin.war.web;

import org.activebpel.rt.bpel.server.admin.AeProcessDeploymentDetail;
import org.activebpel.rt.bpel.server.engine.AeEngineFactory;

/**
 * Wraps the AeProcessDeploymentDetail array for the 
 * deployed processes listing.
 */
public class AeDeployedProcessesBean
{
   /** Deployed process details. */   
   protected AeProcessDeploymentDetail[] mDetails;
   /** Pointer to current index. */
   protected int mCurrentIndex;
   
   /**
    * Constructor.  Initializes the
    * deployment details array.
    */
   public AeDeployedProcessesBean()
   {
      mDetails = AeEngineFactory.getEngineAdministration().getDeployedProcesses();      
   }
   
   /**
    * Size accessor.
    * @return The number of detail rows.
    */
   public int getDetailSize()
   {
      if( mDetails == null )
      {
         return 0;
      }
      return mDetails.length;
   }
   
   /**
    * Indexed accessor.
    * @param aIndex
    */
   public AeProcessDeploymentDetail getDetail( int aIndex )
   {
      setCurrentIndex( aIndex );
      return mDetails[aIndex];
   }
   
   /**
    * Setter for the current index.
    * @param aIndex
    */
   protected void setCurrentIndex( int aIndex )
   {
      mCurrentIndex = aIndex;
   }

   /**
    * Accessor for the current index.
    */
   public int getCurrentIndex()
   {
      return mCurrentIndex;
   }
}
