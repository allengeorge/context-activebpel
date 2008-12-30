//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/web/urn/AeListMappingsBean.java,v 1.1 2005/06/22 17:17:3
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.activebpel.rt.bpeladmin.war.web.AeAbstractAdminBean;

/**
 * Gets the URN mappings from the resolver and makes them available for the JSP.
 */
public class AeListMappingsBean extends AeAbstractAdminBean
{
   /** List of mappings */
   private List mValues = null;
   
   /**
    * Gets the mapping by offset
    * 
    * @param aOffset
    */
   public AeURNMapping getURNMapping(int aOffset)
   {
      return (AeURNMapping) getValues().get(aOffset);
   }
   
   /**
    * Gets the number of mappings
    */
   public int getURNMappingSize()
   {
      return getValues().size();
   }
   
   /**
    * Getter for the values, loads the map if it hasn't been loaded yet.
    */
   protected List getValues()
   {
      if (mValues == null)
         mValues = loadValues();
      return mValues;
   }
   
   /**
    * Gets the mappings from the resolver. They will be sorted alphabetically by URN.
    */
   protected List loadValues()
   {
      Map map = getAdmin().getURNAddressResolver().getMappings();
      List list = new ArrayList();
      list.addAll(map.keySet());
      Collections.sort(list);
      
      List values = new ArrayList();
      int offset = 0;
      for (Iterator iter = list.iterator(); iter.hasNext(); offset++)
      {
         String urn = (String) iter.next();
         values.add(new AeURNMapping(urn, map.get(urn).toString()));
      }
      return values;
   }
   
   /**
    * Returns true if there are no mappings.
    */
   public boolean isEmpty()
   {
      return getURNMappingSize() == 0;
   }
}
 
