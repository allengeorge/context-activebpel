// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/wsdl/AeCatalogResourceResolver.java,v 1.1 2006/08/04 17:57:5
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
package org.activebpel.rt.bpel.server.wsdl;

import java.io.IOException;

import org.activebpel.rt.bpel.server.catalog.AeCatalogMappings;
import org.activebpel.rt.bpel.server.catalog.IAeCatalogMapping;
import org.activebpel.rt.bpel.server.engine.AeEngineFactory;
import org.xml.sax.InputSource;

/**
 * Interacts with the catalog for mappings to resolve resource references.
 */
public class AeCatalogResourceResolver implements IAeResourceResolver
{
   /**
    * Constructor.
    */
   public AeCatalogResourceResolver()
   {
   }
   
   /**
    * Convenienve method for formatting keys.
    * @param aLocationHint
    */
   protected String formatKey( String aLocationHint )
   {
      return AeCatalogMappings.makeKey( aLocationHint );
   }
   
   /**
    * Implements method by calling catalog to resolve mapping. 
    * @see org.activebpel.rt.bpel.server.wsdl.IAeResourceResolver#getInputSource(java.lang.String)
    */
   public InputSource getInputSource( String aLocationHint ) throws IOException
   {
      IAeCatalogMapping mapping = AeEngineFactory.getCatalog().getMappingForLocation(aLocationHint); 
      if(mapping != null)
         return mapping.getInputSource();
      return null;
   }
   
   /**
    * Implements method by calling catalog to see if it has the mapping for this location.
    * @see org.activebpel.rt.bpel.server.wsdl.IAeResourceResolver#hasMapping(java.lang.String)
    */
   public boolean hasMapping( String aLocationHint )
   {
      IAeCatalogMapping mapping = AeEngineFactory.getCatalog().getMappingForLocation(aLocationHint); 
      return mapping != null;
   }
}
