//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/AeServiceMap.java,v 1.4 2007/02/13 15:26:5
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
package org.activebpel.rt.bpel.server.deploy; 

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

/**
 * Maps service names to their deployment information.
 */
public class AeServiceMap
{
   /** singleton instance */
   private static final AeServiceMap INSTANCE = new AeServiceMap();
   
   /** maps the service name to the service data */
   private Map mMap = new HashMap();
   
   /**
    * singleton getter
    */
   protected static final AeServiceMap getInstance()
   {
      return INSTANCE;
   }
   
   /**
    * Returns a list of <code>AeServiceDeploymentInfo</code> entries currently deployed in the engine. 
    */
   public static List getServiceEntries()
   {
      return new LinkedList(getInstance().mMap.values());
   }
   
   /**
    * Adds the service data to our cache. This is called when a plan is deployed
    * by the deployment handler. The handler is synchronized already so we don't
    * need to worry about synchronizing here.
    * 
    * @param aServiceData
    */
   public void addServiceData(IAeServiceDeploymentInfo[] aServiceData)
   {
      // work off of a copy of the map so we don't have to sync the reads.
      Map copy = new HashMap(mMap);
      for (int i = 0; i < aServiceData.length; i++)
      {
         copy.put(aServiceData[i].getServiceName(), aServiceData[i]);
      }
      mMap = copy;
   }
   
   /**
    * Gets the service data mapped to the service name
    * 
    * @param aServiceName
    */
   public IAeServiceDeploymentInfo getServiceData(String aServiceName)
   {
      return (IAeServiceDeploymentInfo) mMap.get(aServiceName);
   }
   
   /**
    * Called by the deployment provider when a plan is undeployed. The plans get
    * undeployed through the deployment handler which is already synchronized.
    * 
    * @param aProcessQName
    */
   public void processUndeployed(QName aProcessQName)
   {
      // work off of a copy of the map so we don't have to sync the reads.
      Map copy = new HashMap(mMap);
      for (Iterator iter = copy.entrySet().iterator(); iter.hasNext();)
      {
         Map.Entry entry = (Map.Entry) iter.next();
         IAeServiceDeploymentInfo data = (IAeServiceDeploymentInfo) entry.getValue();
         if (data.getProcessQName().equals(aProcessQName))
         {
            iter.remove();
         }
      }
      mMap = copy;
   }
}
 
