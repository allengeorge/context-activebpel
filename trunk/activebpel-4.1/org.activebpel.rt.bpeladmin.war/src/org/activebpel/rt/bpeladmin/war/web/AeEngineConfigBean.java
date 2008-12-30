// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/web/AeEngineConfigBean.java,v 1.21 2007/07/02 19:18:3
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.config.IAeEngineConfiguration;
import org.activebpel.rt.bpel.config.IAeUpdatableEngineConfig;
import org.activebpel.rt.bpel.server.admin.AeBuildInfo;
import org.activebpel.rt.bpeladmin.war.AeBuildNumber;

/**
 *  Bean for driving display of home page.
 */
public class AeEngineConfigBean extends AeAbstractAdminBean
{
   private static final String CONFIG_ALARM_MAX_WORK_COUNT_PATH = IAeEngineConfiguration.WORK_MANAGER_ENTRY
      + "/" + IAeEngineConfiguration.CHILD_WORK_MANAGERS_ENTRY //$NON-NLS-1$
      + "/" + IAeEngineConfiguration.ALARM_CHILD_WORK_MANAGER_ENTRY //$NON-NLS-1$
      + "/" + IAeEngineConfiguration.MAX_WORK_COUNT_ENTRY; //$NON-NLS-1$

   /** Build info information. */
   protected AeBuildInfo[] mBuildInfos;
   
   // internal state
   private String mLoggingFilter;
   private boolean mAllowEmptyQuery;
   private boolean mValidateMessages;
   private boolean mCreateXPath;
   private boolean mReplaceResources;

   /**
    * Default constructor.
    */
   public AeEngineConfigBean()
   {
      mBuildInfos = getBuildInfo();
   }

   /**
    * Gets the array of build info objects.
    */
   protected AeBuildInfo[] getBuildInfo()
   {
      ArrayList list = new ArrayList();
      AeBuildInfo[] infoArray = getAdmin().getBuildInfo();
      for (int i = 0; i < infoArray.length; i++)
      {
         list.add(infoArray[i]);
      }

      addAdminBuildInfo(list);

      return (AeBuildInfo[]) list.toArray(new AeBuildInfo[list.size()]);
   }
   
   /**
    * Creates an additional bild info object for the admin console.
    */
   protected void addAdminBuildInfo(List aList)
   {
      try
      {
         aList.add(AeBuildInfo.createBuildInfoFor(AeBuildNumber.class, "ActiveBPEL Admin (BpelAdmin)")); //$NON-NLS-1$
      }
      catch (AeException ae)
      {
      }
   }
   
   /**
    * Setter for the create XPath property.
    * @param aValue
    */
   public void setAllowCreateXPath( boolean aValue )
   {
      mCreateXPath = aValue;
   }
   
   /**
    * Getter for create XPath property.
    */
   public boolean isAllowCreateXPath()
   {
      return getUpdatableConfig().allowCreateXPath();
   }
   
   /**
    * Setter for allow empty query property.
    * @param aValue
    */
   public void setAllowEmptyQuery( boolean aValue )
   {
      mAllowEmptyQuery = aValue;
   }
   
   /**
    * Getter for allow empty query property.
    */
   public boolean isAllowEmptyQuery()
   {
      return getUpdatableConfig().allowEmptyQuerySelection();
   }
   
   /**
    * Setter for validate service messages property.
    * @param aValue
    */
   public void setValidateServiceMessages( boolean aValue )
   {
      mValidateMessages = aValue;
   }
   
   /**
    * Getter for validate services message property.
    */
   public boolean isValidateServiceMessages()
   {
      return getUpdatableConfig().validateServiceMessages();
   }
   
   /**
    * Setter for logging enabled property.
    * @param aValue
    */
   public void setLoggingFilter( String aFilterName )
   {
      mLoggingFilter = aFilterName;
   }
   
   /**
    * Getter for logging enabled property.
    */
   public String getLoggingFilter()
   {
      return getUpdatableConfig().getLoggingFilter();
   }
   
   /**
    * Setter for time out value.
    * @param aTimeout time in seconds
    */
   public void setUnmatchedCorrelatedReceiveTimeout( int aTimeout )
   {
      getUpdatableConfig().setUnmatchedCorrelatedReceiveTimeout( aTimeout );
   }
   
   /**
    * Setter for web service timeouts, applies to messages sent to the engine and invokes.
    * @param aTimeout
    */
   public void setWebServiceTimeout(int aTimeout)
   {
      getUpdatableConfig().setWebServiceTimeout(aTimeout);
   }
   
   /**
    * Getter for time out value.
    */
   public int getUnmatchedCorrelatedReceiveTimeout()
   {
      return getUpdatableConfig().getUnmatchedCorrelatedReceiveTimeout();
   }
   
   /**
    * Getter for the timeout value
    */
   public int getWebServiceTimeout()
   {
      return getUpdatableConfig().getWebServiceTimeout();
   }
   
   /**
    * Setter for work manager thread pool min.
    * @param aValue
    */
   public void setThreadPoolMin( int aValue )
   {
      // Only try to set this value if we are using internal WrokManager
      if (isInternalWorkManager())
         getUpdatableConfig().setWorkManagerThreadPoolMin( aValue );
   }
   
   /**
    * Getter for work manager thread pool min.
    */
   public int getThreadPoolMin()
   {
      return getUpdatableConfig().getWorkManagerThreadPoolMin();
   }

   /**
    * Setter for work manager thread pool max.
    * @param aValue
    */
   public void setThreadPoolMax( int aValue )
   {
      // Only try to set this value if we are using internal WrokManager
      if (isInternalWorkManager())
         getUpdatableConfig().setWorkManagerThreadPoolMax( aValue );
   }
   
   /**
    * Getter for work manager thread pool max.
    */
   public int getThreadPoolMax()
   {
      return getUpdatableConfig().getWorkManagerThreadPoolMax();
   }
   
   /**
    * Setter for process work count.
    * @param aValue
    */
   public void setProcessWorkCount( int aValue )
   {
      getUpdatableConfig().setProcessWorkCount( aValue );
   }
   
   /**
    * Getter for process work count.
    */
   public int getProcessWorkCount()
   {
      return getUpdatableConfig().getProcessWorkCount();
   }

   /**
    * Sets max work count for the Alarm child work manager.
    *
    * @param aValue
    */
   public void setAlarmMaxWorkCount(int aValue)
   {
      // Anything less than 0 is the same as 0.
      int maxWorkCount = (aValue < 0) ? 0 : aValue;

      getUpdatableConfig().addEntryByPath(CONFIG_ALARM_MAX_WORK_COUNT_PATH, String.valueOf(maxWorkCount));
   }

   /**
    * Returns current configuration max work count for the Alarm child work
    * manager.
    *
    * @return max work count for the Alarm child work manager
    */
   public int getAlarmMaxWorkCount()
   {
      int result; 

      try
      {
         String entry = (String) getUpdatableConfig().getEntryByPath(CONFIG_ALARM_MAX_WORK_COUNT_PATH);
         result = Integer.parseInt(entry);
      }
      catch (Exception e)
      {
         AeException.logError(e);

         result = IAeEngineConfiguration.DEFAULT_CHILD_MAX_WORK_COUNT;
      }

      return result;
   }

   /**
    * Indicates that all updates have taken place if the
    * the given value is set to true.
    * @param aValue Flag to signal ok to do engine config updates.
    */
   public void setFinished( boolean aValue )
   {
      if( aValue )
      {
         getUpdatableConfig().setAllowCreateXPath( mCreateXPath );
         getUpdatableConfig().setLoggingFilter( mLoggingFilter );
         getUpdatableConfig().setAllowEmptyQuerySelection( mAllowEmptyQuery );
         getUpdatableConfig().setValidateServiceMessages( mValidateMessages );
         getUpdatableConfig().setResourceReplaceEnabled( mReplaceResources );
         getUpdatableConfig().update();
      }
   }
   
   /**
    * Accessor for updatable engine settings. 
    */
   protected IAeUpdatableEngineConfig getUpdatableConfig()
   {
      return getAdmin().getEngineConfig().getUpdatableEngineConfig();
   }
   
   /**
    * Returns the number of deployed processes.
    */
   public int getDeployedProcessesSize()
   {
      if( getAdmin().getDeployedProcesses() == null )
      {
         return 0;
      }
      return getAdmin().getDeployedProcesses().length;
   }
   
   /**
    * Returns the engine start date (output will be formatted according to
    * the date pattern property or date.toString() if none is specified).
    */
   public Date getStartDate()
   {
      return getAdmin().getStartDate();
   }
   
   /**
    * Indexed accessor for build infos.
    * @param aIndex
    */
   public AeBuildInfo getBuildInfo( int aIndex )
   {
      return mBuildInfos[aIndex];      
   }
   
   /**
    * Returns the build info array size.
    */
   public int getBuildInfoSize()
   {
      if( mBuildInfos == null )
      {
         return 0;
      }
      return mBuildInfos.length;
   }
   
   /**
    * Setter for the resource cache max.
    * @param aMax
    */
   public void setResourceCacheMax( int aMax )
   {
      getUpdatableConfig().setResourceCacheMax( aMax );
   }
   
   /**
    * Getter for the resource cache max.
    */
   public int getResourceCacheMax()
   {
      return getUpdatableConfig().getResourceCacheMax();
   }
   
   /**
    * Return true if resource replace flag is set to true.
    */
   public boolean isResourceReplaceEnabled()
   {
      return getUpdatableConfig().isResourceReplaceEnabled();
   }
   
   /**
    * Setter for resource replacement flag.
    * @param aFlag
    */
   public void setResourceReplaceEnabled( boolean aFlag )
   {
      mReplaceResources = aFlag;
   }

   /**
    * Returns True if using internal WorkManager or False if using server implementation. 
    */
   public boolean isInternalWorkManager()
   {
      return getAdmin().isInternalWorkManager();
   }
}
