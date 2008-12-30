// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis.bpel.web/src/org/activebpel/rt/axis/bpel/web/deploy/AeAxisWebServicesDeployer.java,v 1.8 2007/02/13 15:49:3
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
package org.activebpel.rt.axis.bpel.web.deploy;

import java.util.Map;

import org.activebpel.rt.axis.bpel.deploy.AeAxisWebServicesDeployerBase;
import org.activebpel.rt.axis.bpel.web.AeProcessEngineServlet;
import org.activebpel.rt.bpel.server.deploy.IAeWebServicesDeployer;
import org.apache.axis.server.AxisServer;

/**
 * WebServicesDeployer impl that deploys web services to Axis running
 * under a servlet. 
 */
public class AeAxisWebServicesDeployer extends AeAxisWebServicesDeployerBase implements IAeWebServicesDeployer
{

   /**
    * Constructor.
    * @param aConfig
    */
   public AeAxisWebServicesDeployer()
   {
      super(null);
   }   
   
   /**
    * Constructor.
    * @param aConfig
    */
   public AeAxisWebServicesDeployer(Map aConfig)
   {
      super(aConfig);
   }   
   
   /**
    * Implements method by returning the axis server associated with the process engine servlet. 
    * @see org.activebpel.rt.axis.bpel.deploy.AeAxisWebServicesDeployerBase#getAxisServer()
    */
   protected AxisServer getAxisServer()
   {
      return AeProcessEngineServlet.getAxisServer();
   }
}
