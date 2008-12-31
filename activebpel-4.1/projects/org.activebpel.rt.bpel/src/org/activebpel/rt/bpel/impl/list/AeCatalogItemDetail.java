//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/list/AeCatalogItemDetail.java,v 1.3 2006/09/07 15:06:2
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
 * Essentially a struct that wraps the detail information
 * for a single catalog resource deployment.  Information includes the
 * location hint, the target namespace, the actual resource (e.g. wsdl)
 * (as a string), and an array of <code>AeCatalogItemPlanReference</code>
 * objects that represent the process plans that utilize this resource.
 */
public class AeCatalogItemDetail extends AeCatalogItem
{
   /** The catalog item resource as a string. */
   private String mText;
   /** Array of plans that point to this resource. */
   private AeCatalogItemPlanReference[] mPlanReferences;

   /**
    * Constructor.
    *
    * @param aLocation
    * @param aTypeURI
    * @param aTargetNamespace
    * @param aText
    * @param aPlanListing
    */
   public AeCatalogItemDetail(String aLocation, String aTypeURI, String aTargetNamespace, String aText, AeCatalogItemPlanReference[] aPlanListing)
   {
      super(aLocation, aTargetNamespace, aTypeURI);
      mText = aText;
      mPlanReferences = aPlanListing;
   }

   /**
    * @return Returns the wsdl xml as a string.
    */
   public String getText()
   {
      return mText;
   }

   /**
    * @return Returns the array of <code>AeCatalogItemPlanReference</code> objects.
    */
   public AeCatalogItemPlanReference[] getPlanReferences()
   {
      return mPlanReferences;
   }
}
