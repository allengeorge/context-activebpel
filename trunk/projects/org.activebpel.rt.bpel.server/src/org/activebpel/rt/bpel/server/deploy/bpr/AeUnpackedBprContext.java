// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/bpr/AeUnpackedBprContext.java,v 1.1 2005/01/28 17:18:4
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

import java.net.URL;


/**
 * Allows short name to be specified in x-tor.
 */
public class AeUnpackedBprContext extends AeBprContext
{
   /** Short name. */
   private String mShortName;
   
   /**
    * Constructor.
    * @param aURL
    * @param aLoader
    * @param aName
    */
   public AeUnpackedBprContext( URL aURL, ClassLoader aLoader, String aName )
   {
      super( aURL, aURL, aLoader );
      mShortName = aName;
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.deploy.IAeDeploymentContext#getShortName()
    */
   public String getShortName()
   {
      return mShortName;
   }
}
