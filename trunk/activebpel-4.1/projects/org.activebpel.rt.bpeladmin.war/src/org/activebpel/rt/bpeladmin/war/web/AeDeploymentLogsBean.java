// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/web/AeDeploymentLogsBean.java,v 1.2 2004/10/21 19:19:5
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

import org.activebpel.rt.bpel.server.engine.AeEngineFactory;

/**
 * Wraps the deployment logs listing and allows access to
 * a single log file via its name.
 */
public class AeDeploymentLogsBean
{
   /** Types wrapper around deployment log file names. */
   protected AeJavaTypesWrapper[] mLogFiles;
   /** A specific log file name. */
   protected String mLogFile;

   /**
    * Constructor.
    */
   public AeDeploymentLogsBean()
   {
      mLogFiles = AeJavaTypesWrapper.wrap(
         AeEngineFactory.getEngineAdministration().getDeploymentLogListing() );
         
      mLogFile = AeEngineFactory.getEngineAdministration().getDeploymentLog();         
   }
     
   /**
    * Returns the contents of the named log file as a string.
    */
   public String getLogFile()
   {
      return mLogFile;
   }
   
   /**
    * Accessor for the log file listing size.
    */
   public int getLogListingSize()
   {
      if( mLogFiles != null )
      {
         return mLogFiles.length;
      }
      else
      {
         return 0;
      }
   }
   
   /**
    * Indexed accessor for the log file listing.
    * @param aIndex
    */
   public AeJavaTypesWrapper getLogListing( int aIndex )
   {
      return mLogFiles[aIndex];
   }
}
