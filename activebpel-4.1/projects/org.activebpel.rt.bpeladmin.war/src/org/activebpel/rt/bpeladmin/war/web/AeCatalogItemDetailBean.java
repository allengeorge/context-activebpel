// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/web/AeCatalogItemDetailBean.java,v 1.3 2006/08/16 14:23:1
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

import org.activebpel.rt.bpel.impl.list.AeCatalogItemDetail;
import org.activebpel.rt.bpel.impl.list.AeCatalogItemPlanReference;
import org.activebpel.rt.bpeladmin.war.AeMessages;

/**
 * Bean for displaying the details of a wsdl deployment.
 */
public class AeCatalogItemDetailBean extends AeAbstractAdminBean
{
   /** Catalog item deployment details. */
   private AeCatalogItemDetail mDetail;
   
   /**
    * Default constructor.
    */
   public AeCatalogItemDetailBean()
   {
      
   }
   
   /**
    * Setter for the location.
    * @param aHint
    */
   public void setLocation( String aHint )
   {
      setDetail(getAdmin().getCatalogAdmin().getCatalogItemDetail( aHint ));
   }
   
   /**
    * Returns true if there is detail info to display.
    */
   public boolean isValidDetail()
   {
      return getDetail() != null;
   }
   
   /**
    * Getter for the type.
    */
   public String getTypeDisplay()
   {
       return AeMessages.format("AeCatalogItemDetail.TYPE_DISPLAY", new Object[] { getDetail().getTypeDisplay(), getDetail().getTypeURI()}); //$NON-NLS-1$
   }
   
   /**
    * Getter for the location.
    */
   public String getLocation()
   {
      return getDetail().getLocation();
   }
   
   /**
    * Getter for the namespace.
    */
   public String getNamespace()
   {
       return getDetail().getNamespace();
   }
   
   /**
    * Getter for the short name to display for the item.
    */
   public String getFormattedName()
   {
       return getDetail().getFormattedName();
   }
   
   /**
    * Getter for the catalog resources text.
    */
   public String getText()
   {
      return getDetail().getText();
   }
   
   /**
    * Return the <code>AeCatalogItemPlanReference</code> referenced
    * by the index.
    * @param aIndex
    */
   public AeCatalogItemPlanReference getPlanReferenceDetail( int aIndex )
   {
       return getDetail().getPlanReferences()[aIndex];
   }
   
   /**
    * Return the size of the <code>AeCatalogItemPlanReference</code>
    * array. 
    */
   public int getPlanReferenceDetailSize()
   {
       if( getDetail().getPlanReferences() != null )
       {
           return getDetail().getPlanReferences().length;
       }
       return 0;
   }

   /**
    * Returns true if there are no <code>AeCatalogItemPlanReference<code> objects
    * to display.
    */
   public boolean isDetailArrayEmpty()
   {
       return getPlanReferenceDetailSize() == 0;
   }

   /**
    * @param detail The detail to set.
    */
   protected void setDetail(AeCatalogItemDetail detail)
   {
      mDetail = detail;
   }

   /**
    * @return Returns the detail.
    */
   protected AeCatalogItemDetail getDetail()
   {
      return mDetail;
   }
}
