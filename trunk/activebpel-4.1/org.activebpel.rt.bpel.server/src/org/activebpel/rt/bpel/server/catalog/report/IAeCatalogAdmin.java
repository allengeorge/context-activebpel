// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/catalog/report/IAeCatalogAdmin.java,v 1.3 2007/03/06 21:52:5
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
package org.activebpel.rt.bpel.server.catalog.report;

import org.activebpel.rt.bpel.impl.list.AeCatalogListingFilter;
import org.activebpel.rt.bpel.impl.list.AeCatalogItemDetail;
import org.activebpel.rt.bpel.impl.list.AeCatalogListResult;
import org.activebpel.rt.bpel.server.catalog.resource.IAeResourceStats;
import org.xml.sax.InputSource;

/**
 *  Admin interface into the catalog.
 */
public interface IAeCatalogAdmin
{
   /**
    * Return the <code>AeCatalogListResult</code> for the catalog
    * contents listing.
    * @param aFilter
    */
   public AeCatalogListResult getCatalogListing( AeCatalogListingFilter aFilter );
   
   /**
    * Return a <code>AeCatalogItemDetail</code> object for a single resource deployment.
    * @param aLocationHint
    * @return catalog item detail if available or <code>null</code> if not found.
    */
   public AeCatalogItemDetail getCatalogItemDetail( String aLocationHint );

   /**
    * Return a <code>InputSource</code> object for a catalog item.
    * @param aLocationHint
    */
   public InputSource getCatalogInputSource(String aLocationHint);
   
   /**
    * Accessor for the resource stats.
    */
   public IAeResourceStats getResourceStats();  
}
