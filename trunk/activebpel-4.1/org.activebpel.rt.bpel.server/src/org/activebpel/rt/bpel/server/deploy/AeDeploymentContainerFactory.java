// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/AeDeploymentContainerFactory.java,v 1.1 2005/06/17 21:51:1
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

import java.net.URL;
import java.net.URLClassLoader;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.server.deploy.bpr.AeBpr;
import org.activebpel.rt.bpel.server.deploy.bpr.AeUnpackedBprContext;
import org.activebpel.rt.bpel.server.deploy.bpr.IAeBpr;


/**
 * Create and configure the <code>IAeDeploymentContainers</code>needed for
 * bpr deploys and undeploys.
 */
public class AeDeploymentContainerFactory
{
   /**
    * Create and configure the <code>IAeDeploymentContainer</code>for
    * deployment.
    * @param aInfo
    * @throws AeException
    */
   public static IAeDeploymentContainer createDeploymentContainer( AeNewDeploymentInfo aInfo )
   throws AeException
   {
      ClassLoader current = Thread.currentThread().getContextClassLoader();
      ClassLoader bprResourceClassLoader = URLClassLoader.newInstance( new URL[]{ aInfo.getTempURL() }, current ); 

      String urlString = aInfo.getURL().toString();
      String shortName = urlString.substring( urlString.lastIndexOf('/')+1 );
      AeUnpackedBprContext context = new AeUnpackedBprContext(aInfo.getTempURL(), bprResourceClassLoader, shortName );
      IAeBpr file = AeBpr.createUnpackedBpr( context );
      return new AeDeploymentContainer( context, file, aInfo.getURL() );
   }
   
   /**
    * Create and configure the <code>IAeDeploymentContainer</code>for
    * undeployment.
    * @param aInfo
    * @throws AeException
    */
   public static IAeDeploymentContainer createUndeploymentContainer( 
      AeNewDeploymentInfo aInfo ) throws AeException
   {
      ClassLoader current = Thread.currentThread().getContextClassLoader();
      ClassLoader bprResourceClassLoader = URLClassLoader.newInstance( new URL[]{ aInfo.getTempURL() }, current ); 

      String urlString = aInfo.getURL().toString();
      String shortName = urlString.substring( urlString.lastIndexOf('/')+1 );
      URL idUrl = aInfo.getURL();
      aInfo.setURL( aInfo.getTempURL() );
      AeUnpackedBprContext context = new AeUnpackedBprContext(aInfo.getTempURL(), bprResourceClassLoader, shortName );
      IAeBpr file = AeBpr.createUnpackedBpr( context );
      return new AeDeploymentContainer( context, file, idUrl );
   }
}
