//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/AeAbstractDeploymentProvider.java,v 1.1 2007/09/02 17:17:1
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

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.server.IAeDeploymentProvider;

/**
 * Base class for deployment providers
 */
public abstract class AeAbstractDeploymentProvider implements IAeDeploymentProvider
{
   /**
    * @see org.activebpel.rt.bpel.server.IAeDeploymentProvider#findService(java.lang.String, javax.xml.namespace.QName, java.lang.String)
    */
   public AeRoutingInfo findService(String aService, QName aPortType, String aOperation) throws AeServiceNotFoundException, AeOperationNotImplementedException
   {
      AeRoutingInfo info = null;
      try
      {
         info = getRoutingInfoByServiceName(aService);
      }
      catch (AeBusinessProcessException e)
      {
         throw new AeServiceNotFoundException(aService, e);
      }

      if (!info.isImplemented(aPortType, aOperation))
      {
         throw new AeOperationNotImplementedException(aService, aPortType, aOperation);
      }
      return info;
   }

}
 
