// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/catalog/AeCatalogBprMapping.java,v 1.1 2006/08/04 17:57:5
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

import java.io.IOException;

import org.activebpel.rt.bpel.server.deploy.bpr.IAeBpr;
import org.xml.sax.InputSource;

/**
 * Represents the mapping info for a catalog resource in a bpr file.
 */
public class AeCatalogBprMapping extends AeAbstractCatalogMapping
{
   /** Bpr containing the entry. */
   private IAeBpr mBpr;
   /** Classpath location from catalog deployment. */
   private String mClasspath;

   /**
    * Constructor.
    * @param aBpr
    * @param aLocationHint
    * @param aTypeURI
    * @param aClasspath
    */
   public AeCatalogBprMapping( IAeBpr aBpr, String aLocationHint, String aTypeURI, String aClasspath )
   {
      super( aLocationHint, aTypeURI );
      mClasspath = aClasspath;
      mBpr = aBpr;
   }

   /**
    * @return Returns the classpath.
    */
   public String getClasspath()
   {
      return mClasspath;
   }

   /**
    * Returns true if the entry exists. 
    */
   public boolean exists()
   {
      return mBpr.exists(getClasspath());
   }
   
   /**
    * Returns input source from the bpr file. 
    */
   public InputSource getInputSource() throws IOException
   {
      InputSource in = new InputSource(mBpr.getResourceAsStream(getClasspath()));
      in.setSystemId(getLocationHint());
      return in;
   }         
}
