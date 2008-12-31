// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/AeCatalogDeployer.java,v 1.3 2006/08/16 14:20:5
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
package org.activebpel.rt.bpel.server.deploy;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.server.AeMessages;
import org.activebpel.rt.bpel.server.catalog.AeCatalogEvent;
import org.activebpel.rt.bpel.server.catalog.AeCatalogMappings;
import org.activebpel.rt.bpel.server.catalog.IAeCatalogListener;
import org.activebpel.rt.bpel.server.catalog.IAeCatalogMapping;
import org.activebpel.rt.bpel.server.engine.AeEngineFactory;
import org.activebpel.rt.bpel.server.logging.IAeDeploymentLogger;
import org.activebpel.rt.util.AeUtil;

/**
 * IAeCatalogDeployer impl. 
 */
public class AeCatalogDeployer implements IAeCatalogDeployer
{
   /**
    * @see org.activebpel.rt.bpel.server.deploy.IAeCatalogDeployer#deployToCatalog(org.activebpel.rt.bpel.server.deploy.IAeDeploymentContainer, org.activebpel.rt.bpel.server.logging.IAeDeploymentLogger)
    */
   public void deployToCatalog(IAeDeploymentContainer aContainer, IAeDeploymentLogger aLogger)
   throws AeException
   {
      AeCatalogDeploymentLogger logger = new AeCatalogDeploymentLogger(aLogger);
      AeEngineFactory.getCatalog().addCatalogListener(logger);
      try
      {
         AeCatalogMappings catalog = new AeCatalogMappings(aContainer);
         IAeCatalogMapping[] mappingEntries = createCatalogMappings( catalog, (IAeDeploymentContext)aContainer );
         AeEngineFactory.getCatalog().addCatalogEntries( aContainer.getDeploymentId(), mappingEntries, catalog.replaceExistingResource() );
      }
      finally
      {
         AeEngineFactory.getCatalog().removeCatalogListener(logger);
      }
   }

   /**
    * Create the <code>IAeCatalogMapping</code> impl. 
    * @param aCatalog
    * @param aContext
    */
   protected IAeCatalogMapping[] createCatalogMappings( AeCatalogMappings aCatalog, IAeDeploymentContext aContext )
   {
      return (IAeCatalogMapping[])aCatalog.getResources().values().toArray(new IAeCatalogMapping[aCatalog.getResources().values().size()]);
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.deploy.IAeCatalogDeployer#undeployFromCatalog(org.activebpel.rt.bpel.server.deploy.IAeDeploymentContainer)
    */
   public void undeployFromCatalog(IAeDeploymentContainer aContainer)
      throws AeException
   {
      AeEngineFactory.getCatalog().remove( aContainer.getDeploymentId() );
   }
}

/**
 * Listens for and logs catalog deploment entries.
 */
class AeCatalogDeploymentLogger implements IAeCatalogListener
{
   /** The deployment logger to log to. */
   private IAeDeploymentLogger mLogger;
   
   /** 
    * Constructor.
    * @param aLogger
    */
   AeCatalogDeploymentLogger(IAeDeploymentLogger aLogger)
   {
      mLogger = aLogger;
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.catalog.IAeCatalogListener#onDeployment(org.activebpel.rt.bpel.server.catalog.AeCatalogEvent)
    */
   public void onDeployment(AeCatalogEvent aEvent)
   {
      Object[] objs = { AeUtil.getShortNameForLocation(aEvent.getLocationHint()), aEvent.getLocationHint()};
      mLogger.addInfo(AeMessages.format("AeCatalogDeployer.ADDED_RESOURCE", objs)); //$NON-NLS-1$
   }

   /**
    * @see org.activebpel.rt.bpel.server.catalog.IAeCatalogListener#onDuplicateDeployment(org.activebpel.rt.bpel.server.catalog.AeCatalogEvent)
    */
   public void onDuplicateDeployment(AeCatalogEvent aEvent)
   {
      Object[] objs = { AeUtil.getShortNameForLocation(aEvent.getLocationHint()), aEvent.getLocationHint() };
      if(aEvent.isReplacement())
         mLogger.addInfo(AeMessages.format("AeCatalogDeployer.REPLACED_RESOURCE", objs)); //$NON-NLS-1$
      else
         mLogger.addInfo(AeMessages.format("AeCatalogDeployer.EXISTING_RESOURCE", objs)); //$NON-NLS-1$
   }

   /**
    * @see org.activebpel.rt.bpel.server.catalog.IAeCatalogListener#onRemoval(org.activebpel.rt.bpel.server.catalog.AeCatalogEvent)
    */
   public void onRemoval(AeCatalogEvent aEvent)
   {
      Object[] objs = { AeUtil.getShortNameForLocation(aEvent.getLocationHint()), aEvent.getLocationHint() };
      mLogger.addInfo(AeMessages.format("AeCatalogDeployer.REMOVED_RESOURCE", objs)); //$NON-NLS-1$
   }

   /**
    * @return Returns the logger.
    */
   protected IAeDeploymentLogger getLogger()
   {
      return mLogger;
   }
  
}
