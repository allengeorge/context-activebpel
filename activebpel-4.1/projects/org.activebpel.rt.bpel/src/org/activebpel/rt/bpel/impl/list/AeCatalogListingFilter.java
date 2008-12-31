//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/list/AeCatalogListingFilter.java,v 1.2 2006/08/16 14:15:0
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
package org.activebpel.rt.bpel.impl.list;

/**
 * Wsdl catalog filter. Current impl only controls row offset and number of rows
 * displayed.
 */
public class AeCatalogListingFilter extends AeListingFilter
{
   /** The type uri to select which is null or empty if none selected. */
   private String mTypeURI;
   
   /** The resource name to filter on, may contain asterisk as wild card. */
   private String mResource;
   
   /** The namespace to filter on, may contain asterisk as wild card. */
   private String mNamespace;
   
   /**
    * Constructor.
    */
   public AeCatalogListingFilter()
   {
      super();
   }

   /**
    * @return Returns the typeURI, which is null or empty if none selected for fuiltering.
    */
   public String getTypeURI()
   {
      return mTypeURI;
   }

   /**
    * @param aTypeURI The typeURI to set.
    */
   public void setTypeURI(String aTypeURI)
   {
      mTypeURI = aTypeURI;
   }

   /**
    * @return Returns the resource.
    */
   public String getResource()
   {
      return mResource;
   }

   /**
    * @param aResource The resource to set.
    */
   public void setResource(String aResource)
   {
      mResource = aResource;
   }

   /**
    * @return Returns the namespace.
    */
   public String getNamespace()
   {
      return mNamespace;
   }

   /**
    * @param aNamespace The namespace to set.
    */
   public void setNamespace(String aNamespace)
   {
      mNamespace = aNamespace;
   }
}
