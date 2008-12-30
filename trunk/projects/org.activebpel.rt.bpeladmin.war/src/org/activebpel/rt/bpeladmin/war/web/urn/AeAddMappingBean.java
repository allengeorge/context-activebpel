//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/web/urn/AeAddMappingBean.java,v 1.1 2005/06/22 17:17:3
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
package org.activebpel.rt.bpeladmin.war.web.urn; 

import org.activebpel.rt.bpeladmin.war.AeMessages;
import org.activebpel.rt.bpeladmin.war.web.AeAbstractAdminBean;
import org.activebpel.rt.util.AeUtil;

/**
 * Adds a URN to URL mapping 
 */
public class AeAddMappingBean extends AeAbstractAdminBean
{
   /** urn used as the key */
   private String mURN;
   /** url that it maps to */
   private String mURL;
   
   /**
    * Trigger for the bean to add the mapping info to the resolver
    * @param aBool
    */
   public void setFinished(boolean aBool)
   {
      if (aBool)
      {
         if (isValid())
         {
            getAdmin().getURNAddressResolver().addMapping(getURN(), getURL());
            setStatusDetail(AeMessages.getString("AeAddMappingBean.MAPPING_ADDED")); //$NON-NLS-1$
         }
      }
   }
   
   /**
    * Mapping is valid if both values are non null.
    */
   protected boolean isValid()
   {
      boolean valid = AeUtil.notNullOrEmpty(mURN) && AeUtil.notNullOrEmpty(mURL);
      if (!valid)
      {
         setStatusDetail(AeMessages.getString("AeAddMappingBean.MAPPING_INVALID")); //$NON-NLS-1$
         setErrorDetail(true);
      }
      return valid;
   }
   
   /**
    * @return Returns the uRL.
    */
   public String getURL()
   {
      return mURL;
   }
   
   /**
    * @param aUrl The uRL to set.
    */
   public void setURL(String aUrl)
   {
      mURL = aUrl;
   }
   
   /**
    * @return Returns the uRN.
    */
   public String getURN()
   {
      return mURN;
   }
   
   /**
    * @param aUrn The uRN to set.
    */
   public void setURN(String aUrn)
   {
      mURN = aUrn;
   }
}
 
