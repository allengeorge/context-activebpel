//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis.bpel/src/org/activebpel/rt/axis/bpel/AeAxisServerFactory.java,v 1.2 2007/08/02 19:54:2
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
package org.activebpel.rt.axis.bpel;

import org.activebpel.rt.axis.AeAxisEngineConfiguration;
import org.activebpel.rt.axis.bpel.deploy.AeResourceProvider;
import org.activebpel.rt.util.AeUtil;
import org.apache.axis.AxisFault;
import org.apache.axis.server.AxisServer;

import java.net.URL;
import java.util.logging.Logger;
/**
 *   Factory class to return the static Axis server to either the AxisServlet (http)
 *   or JMS listener (or any other listener type for that matter) 
**/
public class AeAxisServerFactory
{

   /* the default axis server configuration file name */
   private static final String DEFAULT_AXIS_SERVER_CONFIG = "ae-server-config.wsdd"; //$NON-NLS-1$
 
   /* the default axis client configuration file name */
   private static final String DEFAULT_AXIS_CLIENT_CONFIG = "ae-client-config.wsdd"; //$NON-NLS-1$

   /* the axis server instance */
   private static AxisServer sAxisServer = null;
   
   /** for deployment logging purposes */
   private static final Logger log = Logger.getLogger("ActiveBPEL"); //$NON-NLS-1$
   
   /**
    *  Get AxisServer instance
    *  Initialize the Axis Server if necessary.
    */
   public static AxisServer getAxisServer() throws AxisFault 
   {
      if (sAxisServer == null)
      {
         initAxisServer();
      }
      return sAxisServer;
   }   
   
   /**
    * Initialize the Axis Server.  Short return if already initialized
    * @throws AxisFault
    */
   private static synchronized void initAxisServer() throws AxisFault
   {
      if (sAxisServer == null)
      {
         // find the global config file in classpath
         URL resource = AeUtil.findOnClasspath( DEFAULT_AXIS_SERVER_CONFIG, AeAxisServerFactory.class );
         
         // if no configuration for axis has been found then throw an exception
         if (resource == null)
         {
            throw new AxisFault(AeMessages.getString("AeAxisServerFactory.ERROR_14") +  DEFAULT_AXIS_SERVER_CONFIG); //$NON-NLS-1$
         }
   
         // construct a configuration using our own provider so it can create 
         // our deployment type which is aware of classloader issues
         AeResourceProvider serverConfiguration = new AeResourceProvider(resource);
         sAxisServer = new AxisServer(serverConfiguration);
         log.info(AeMessages.getString("AeAxisServerFactory.15")); //$NON-NLS-1$
         
         // Initialize the axis client server
         AeAxisEngineConfiguration.loadConfig(DEFAULT_AXIS_CLIENT_CONFIG);
         log.info(AeMessages.getString("AeAxisServerFactory.16")); //$NON-NLS-1$
      }
   }
}
