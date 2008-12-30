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
import java.util.Iterator;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.server.addressing.pdef.IAePartnerDefInfo;
import org.activebpel.rt.bpel.server.engine.AeEngineFactory;

// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/AePdefDeployer.java,v 1.2 2004/10/05 23:00:40 PCollins Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2004 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

/**
 * IAePdefDeployer impl.
 */
public class AePdefDeployer implements IAePdefDeployer
{
   /**
    * @see org.activebpel.rt.bpel.server.deploy.IAePdefDeployer#deployPdefs(org.activebpel.rt.bpel.server.deploy.IAeDeploymentContainer)
    */
   public void deployPdefs(IAeDeploymentContainer aContainer)
   throws AeException
   {
      // deploy partner pdefs                  
      for( Iterator iter = aContainer.getPdefResources().iterator(); iter.hasNext(); )
      {
         IAePartnerDefInfo info = aContainer.getPartnerDefInfo((String)iter.next());
         AeEngineFactory.getPartnerAddressProvider().addAddresses(
            aContainer.getDeploymentId(),
            aContainer.getDeploymentLocation().toExternalForm(),
            info);
      }
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.deploy.IAePdefDeployer#undeployPdefs(org.activebpel.rt.bpel.server.deploy.IAeDeploymentContainer)
    */
   public void undeployPdefs( IAeDeploymentContainer aContainer )
   {
      AeEngineFactory.getPartnerAddressProvider().removeAddresses( aContainer.getDeploymentId() );
   }

}
