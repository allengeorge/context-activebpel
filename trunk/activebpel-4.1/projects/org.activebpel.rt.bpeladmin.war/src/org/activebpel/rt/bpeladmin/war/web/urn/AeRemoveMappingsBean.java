//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/web/urn/AeRemoveMappingsBean.java,v 1.1 2005/06/22 17:17:3
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

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import org.activebpel.rt.bpeladmin.war.AeMessages;
import org.activebpel.rt.bpeladmin.war.web.AeAbstractAdminBean;

/**
 * Removes all of the checked mappings from the resolver. 
 */
public class AeRemoveMappingsBean extends AeAbstractAdminBean
{
   /** key to look for in request param mappings */
   private String mDeleteKey;
   
   /**
    * Setter for the delete key
    * @param aKey
    */
   public void setDeleteKey(String aKey)
   {
      mDeleteKey = aKey;
   }
   
   /**
    * Getter for the delete key
    */
   public String getDeleteKey()
   {
      return mDeleteKey;
   }
   
   /**
    * Sets the form post data on the bean and triggers the removal of the urn mappings.
    * 
    * @param aFormData
    */
   public void setFormData(Map aFormData)
   {
      String[] values = (String[]) aFormData.get(getDeleteKey());
      if (values != null && values.length > 0)
      {
         // run URLDecoding on the URN values
         try
         {
            for (int i = 0; i < values.length; i++)
            {
               values[i] = URLDecoder.decode(values[i], "UTF8"); //$NON-NLS-1$
            }
         }
         catch(UnsupportedEncodingException ex)
         {
            // ignore, we should have UTF8 or there are bigger problems.
         }
         
         
         getAdmin().getURNAddressResolver().removeMappings(values);
         setStatusDetail(AeMessages.getString("AeRemoveMappingsBean.MAPPING_REMOVED")); //$NON-NLS-1$
      }
      else
      {
         setStatusDetail(AeMessages.getString("AeRemoveMappingsBean.NO_MAPPINGS_REMOVED")); //$NON-NLS-1$
      }
   }
}
 
