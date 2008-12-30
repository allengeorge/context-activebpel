// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/catalog/IAeCatalog.java,v 1.2 2006/08/04 17:57:5
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
package org.activebpel.rt.bpel.server.catalog;

import org.activebpel.rt.bpel.IAeWSDLProvider;
import org.activebpel.rt.bpel.server.catalog.resource.IAeResourceCache;
import org.activebpel.rt.bpel.server.deploy.IAeDeploymentId;

/**
 * Global Catalog interface.
 * 
 * All access to resources should be via this interface.
 */
public interface IAeCatalog extends IAeWSDLProvider
{
   /** constants for replace wsdl flag */
   public static final boolean KEEP_EXISTING_RESOURCE = false;
   
   /**
    * Adds an array of catalog mapping entries.
    * @param aDeploymentId the id for the deployment or null if none to be recorded
    * @param aMappings the mappings to add
    * @param aReplaceFlag If this flag is set to true any previous wsdl entries
    * mapped to the same location hint will be over written.
    */
   public void addCatalogEntries( IAeDeploymentId aDeploymentId, IAeCatalogMapping[] aMappings, boolean aReplaceFlag );
   
   /**
    * Get a mapping to our in-memory map of catalog items. or null if it doesn't exist
    * @param aLocation
    * @return IAeCatalogMapping if it exists, null if not
    */
   public IAeCatalogMapping getMappingForLocation(String aLocation);

   /**
    * Remove the deployment context.
    * @param aDeploymentId
    */
   public void remove( IAeDeploymentId aDeploymentId );
   
   /**
    * Accessor for the resource cache.
    */
   public IAeResourceCache getResourceCache();
   
   /**
    * Add the catalog listener.
    * @param aListener
    */
   public void addCatalogListener( IAeCatalogListener aListener );
   
   /**
    * Remove the catalog listner.
    * @param aListener
    */
   public void removeCatalogListener( IAeCatalogListener aListener );
   
   /**
    * clears the catalog 
    */
   public void clear();
}
