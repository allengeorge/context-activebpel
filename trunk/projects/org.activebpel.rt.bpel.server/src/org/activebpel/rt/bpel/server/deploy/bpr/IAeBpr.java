// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/bpr/IAeBpr.java,v 1.3 2006/08/04 17:57:5
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
package org.activebpel.rt.bpel.server.deploy.bpr;

import java.io.InputStream;
import java.util.Collection;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.server.addressing.pdef.IAePartnerDefInfo;
import org.activebpel.rt.bpel.server.deploy.IAeDeploymentContext;
import org.activebpel.rt.bpel.server.deploy.IAeDeploymentSource;
import org.w3c.dom.Document;

/**
 * Interface for wrapping the deployment of a BPR. 
 */
public interface IAeBpr
{
   /**
    * Returns the deployment context for this bpr.
    */
   public IAeDeploymentContext getDeploymentContext();
   
   /**
    * Return the short name of the bpr deployment.
    */
   public String getBprFileName();
   
   /**
    * Return true if this is a standard (non-BPEL) web services deployment.
    */
   public boolean isWsddDeployment();
   
   /**
    * Return a collection of names for the pdd resources or
    * an empty collection if none are found.  If the isBpelDeployment
    * method returns true, there should be at least one name in
    * this collection.
    */
   public Collection getPddResources();
   
   /**
    * Return the name of the wsdd resource (for straight Axis deployment) or
    * null if none is found.  The isBpelDeployment method should return false
    * if this method does not return null.
    */
   public String getWsddResource();

   /**
    * Return a deployment source for the given pdd resource name.
    * @param aPddName The name of the pdd resource.
    * @throws AeException
    */
   public IAeDeploymentSource getDeploymentSource(String aPddName) throws AeException;   

   /**
    * Returns a collection of partner definition resource names or an
    * empty collection if none are found.
    */
   public Collection getPdefResources();
   
   /**
    * Return the corresponding IAePartnerDefInfo object for the given
    * pdef resource name.
    * @param aPdefResource The partner definition resource name.
    * @throws AeException
    */
   public IAePartnerDefInfo getPartnerDefInfo( String aPdefResource ) throws AeException;
   
   /**
    * Return the catalog document for this BPR.
    * @throws AeException
    */
   public Document getCatalogDocument() throws AeException;
   
   /**
    * Returns true if the given resource exists within the BPR.
    * @param aResourceName A BPR resource.
    */
   public boolean exists( String aResourceName );
   
   /**
    * Return the named resource as a document object.
    * @param aResourceName
    * @throws AeException
    */
   public Document getResourceAsDocument( String aResourceName ) throws AeException;
   
   /**
    * Returns a names resource from the BPR
    * @param aResourceName
    */
   public InputStream getResourceAsStream(String aResourceName);
   
}
