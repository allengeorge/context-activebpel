//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/catalog/report/AeInMemoryCatalogListing.java,v 1.3 2006/08/16 14:20:5
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.activebpel.rt.AeWSDLException;
import org.activebpel.rt.bpel.impl.list.AeCatalogItem;
import org.activebpel.rt.bpel.impl.list.AeCatalogListResult;
import org.activebpel.rt.bpel.impl.list.AeCatalogListingFilter;
import org.activebpel.rt.bpel.server.catalog.IAeCatalogMapping;
import org.activebpel.rt.util.AeUtil;

/**
 * Produce a <code>AeCatalogListResult</code> for the catalog display.  
 */
public class AeInMemoryCatalogListing
{
   /** Sort by wsdl namespace. */
   private static AeResourceSorter SORTER = new AeResourceSorter();

   /**
    * Create the <code>AeCatalogListResult</code>.
    * @param aFilter The filter (row start and num of rows) params.
    * @param aLocationHintsToMapping Used to for mapping list.
    * @param aCache
    * @throws AeWSDLException
    */
   public static AeCatalogListResult extractListing(AeCatalogListingFilter aFilter, Map aLocationHintsToMapping)
         throws AeWSDLException
   {
      // create the container for the listing details
      List results = new ArrayList();

      // walk the list of mapping in the catalog
      for( Iterator iter = aLocationHintsToMapping.values().iterator(); iter.hasNext(); )
      {
         IAeCatalogMapping mapping = (IAeCatalogMapping)iter.next();
         AeCatalogItem item = new AeCatalogItem(mapping.getLocationHint(), mapping.getTargetNamespace(), mapping.getTypeURI());
         if(isMatch(aFilter, item))
            results.add(item);
      }

      // sort the results by namespace
      sort(results);
      int totalResults = results.size();

      // prime the results
      results = results.subList(aFilter.getListStart(), totalResults);

      // only return the max rows allowed
      if( aFilter.getMaxReturn() > 0 && aFilter.getMaxReturn() < results.size() )
      {
         results = results.subList(0, aFilter.getMaxReturn());
      }

      // return the results
      return new AeCatalogListResult(totalResults, results, true);
   }
   
   /**
    * Check if the passed item is applicable for the passed filter.
    * @param aFilter
    * @param aItem
    * @return true if the mapping matches the filter criteria
    */
   protected static boolean isMatch(AeCatalogListingFilter aFilter, AeCatalogItem aItem)
   {
      boolean match = true;
      
      if(AeUtil.notNullOrEmpty(aFilter.getTypeURI()))
         if(! aFilter.getTypeURI().equals(aItem.getTypeURI()))
            match = false;
      
      if(match && AeUtil.notNullOrEmpty(aFilter.getResource()))
      {
         if(aFilter.getResource().indexOf('*') >= 0)
         {
            String resourcePattern = aFilter.getResource();
            resourcePattern = resourcePattern.replaceAll("\\.", "\\."); //$NON-NLS-1$ //$NON-NLS-2$
            resourcePattern = resourcePattern.replaceAll("\\*", ".*"); //$NON-NLS-1$ //$NON-NLS-2$
            match = Pattern.matches(resourcePattern, aItem.getFormattedName());
         }
         else if(! aFilter.getResource().equals(aItem.getFormattedName()))
         {
            match = false;
         }
      }
      
      if(match && AeUtil.notNullOrEmpty(aFilter.getNamespace()))
      {
         if(aFilter.getNamespace().indexOf('*') >= 0)
         {
            String namespacePattern = aFilter.getNamespace();
            namespacePattern = namespacePattern.replaceAll("\\.", "\\."); //$NON-NLS-1$ //$NON-NLS-2$
            namespacePattern = namespacePattern.replaceAll("\\*", ".*"); //$NON-NLS-1$ //$NON-NLS-2$
            match = Pattern.matches(namespacePattern, aItem.getNamespace());
         }
         else if(! aFilter.getNamespace().equals(aItem.getNamespace()))
         {
            match = false;
         }
      }
      return match;
   }

   /**
    * Sort the resources by namespace.
    * @param aListingDetails
    */
   protected static void sort(List aListingDetails)
   {
      Collections.sort(aListingDetails, SORTER);
   }

   /**
    * Default sort order is namespace.
    */
   protected static class AeResourceSorter implements Comparator
   {
      /*
       * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
       */
      public int compare(Object aO1, Object aO2)
      {
         return ((AeCatalogItem) aO1).getFormattedName().compareTo(((AeCatalogItem) aO2).getFormattedName());
      }
   }
}
