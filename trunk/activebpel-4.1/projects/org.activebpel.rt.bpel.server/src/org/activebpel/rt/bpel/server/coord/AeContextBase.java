//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/coord/AeContextBase.java,v 1.1 2005/10/28 21:10:3
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
package org.activebpel.rt.bpel.server.coord;

import java.io.Serializable;
import java.util.Properties;

import org.activebpel.rt.util.AeUtil;

/**
 * Base class for simple property based context implementations.
 */
public class AeContextBase implements Serializable
{
   /**
    * Properties.
    */
   private Properties mProperties = null;
   
   /**
    * Default constructor.
    *
    */
   public AeContextBase()
   {      
   }

   /**
    * Returns the property value.
    * @param aName name of property.
    * @return property value or null if not found.
    */
   public String getProperty(String aName)
   {
      String rVal = null;
      if (AeUtil.notNullOrEmpty(aName))
      {
         rVal = getProperties().getProperty(aName);
      }
      return rVal;
   }
   
   /**
    * Sets the property.
    * @param aName name of property.
    * @param aValue value of property. This should not be null.
    */
   public void setProperty(String aName, String aValue)
   {
      if (AeUtil.notNullOrEmpty(aName) && aValue != null)
      {
         getProperties().setProperty(aName, aValue.trim());
      }
   }
   
   /**
    * @return Returns the properties.
    */
   public Properties getProperties()
   {
      if (mProperties == null)
      {
         mProperties = new Properties();
      }
      return mProperties;
   }
   
}
