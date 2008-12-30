//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/bpr/IAeBprAccessor.java,v 1.2 2006/07/18 20:05:3
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
import org.w3c.dom.Document;

/**
 * Represents the strategy for accessing resources within a bpr deployment.
 */
public interface IAeBprAccessor
{
   /** wsdlCatalog xml file location - this file name has been deprecated and people should use catalog.xml instead */
   public static final String WSDL_CATALOG = "META-INF/wsdlCatalog.xml"; //$NON-NLS-1$
   /** Catalog xml file location - the catalog file has resource names and locations in the bpr file which should be added to the global catalog */
   public static final String CATALOG = "META-INF/catalog.xml"; //$NON-NLS-1$
   
   /**
    * Initialize the bpr strategy. 
    * @throws AeException
    */
   public void init() throws AeException;
   
   /**
    * Return true if the underlying deployment is a wsr deployment.
    */
   public boolean isWsddDeployment();
   
   /**
    * Return the name of the wsdd resource.
    */
   public String getWsddResource();
   
   /**
    * Return the collection of pdef resource names.
    */
   public Collection getPdefResources();
   
   /**
    * Return the collection of pdd resource names.
    */
   public Collection getPddResources();
   
   /**
    * Return the catalog xml file <code>Document</code>.
    * @throws AeException
    */
   public Document getCatalogDocument() throws AeException;
   
   /**
    * Return true if the bpr has access to this resource.
    * @param aResourceName
    */
   public boolean hasResource( String aResourceName );
   
   /**
    * Get the <code>InputStream</code> to the named resource.
    * @param aResourceName
    */
   public InputStream getResourceAsStream( String aResourceName );
   
   /**
    * Access the named resource as a <code>Document</code>.
    * @param aResourceName
    * @throws AeException
    */
   public Document getResourceAsDocument( String aResourceName ) throws AeException;
}
