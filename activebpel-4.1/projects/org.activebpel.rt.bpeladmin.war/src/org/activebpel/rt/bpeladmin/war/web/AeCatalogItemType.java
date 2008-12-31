//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/web/AeCatalogItemType.java,v 1.1 2006/08/16 14:23:1
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

import org.activebpel.rt.bpeladmin.war.AeMessages;

/**
 * Simple class for wrapping a type for display in catalog filter.
 */
public class AeCatalogItemType
{
   /** The type number. */
   private int mTypeNumber;

   /** True if this is the selected filter type. */
   private boolean mSelected;
   
   /**
    * Constructor.
    */
   public AeCatalogItemType(int aTypeNumber, boolean aSelected)
   {
      mTypeNumber = aTypeNumber;
      mSelected = aSelected;
   }

   /**
    * @return Returns the typeNumber.
    */
   public int getTypeNumber()
   {
      return mTypeNumber;
   }

   /**
    * @return Returns the true if selected type for filtered, false otherwise.
    */
   public boolean isSelected()
   {
      return mSelected;
   }
   
   /**
    * @return Returns the typeDisplay.
    */
   public String getTypeDisplay()
   {
      String prop = "AeCatalogItemType.CATALOG_FILTER_TYPE." + getTypeNumber(); //$NON-NLS-1$ 
      return AeMessages.getString(prop);
   }

   /**
    * @return Returns the typeDisplay.
    */
   public String getTypeURI()
   {
      String prop = "AeCatalogItemType.CATALOG_FILTER_TYPE_URI." + getTypeNumber(); //$NON-NLS-1$ 
      return AeMessages.getString(prop);
   }
}
