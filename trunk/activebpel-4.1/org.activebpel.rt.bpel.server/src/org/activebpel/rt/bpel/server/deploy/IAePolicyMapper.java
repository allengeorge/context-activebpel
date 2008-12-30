// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/IAePolicyMapper.java,v 1.3 2007/02/13 15:26:5
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

import java.util.List;
import java.util.Map;

import org.activebpel.rt.AeException;

/**
 * Handle the platform specific details of deploying policy for a web service.
 */
public interface IAePolicyMapper
{

   /**
    * Maps policy assertions to service parameter definitions.
    * @param aPolicyList A list of policies to be mapped to the service
    * @return List of parameter definitions
    * @throws AeException
    */
   public List getServiceParameters( List aPolicyList )
   throws AeException;
      
   /**
    * Maps sender side policy assertions to handler chain definitions.
    * @param aPolicyList A list of policies to be mapped to the service request flow
    * @return List of handler definitions
    * @throws AeException
    */
   public List getClientRequestHandlers( List aPolicyList )
   throws AeException;

   /**
    * Maps receiver side policy assertions to handler defintions.
    * @param aPolicyList A list of policies to be mapped to the service request flow
    * @return List of handler definitions
    * @throws AeException
    */
   public List getClientResponseHandlers( List aPolicyList )
   throws AeException;
   
   
   /**
    * Maps receiver side policy assertions to wsdd deployment document.
    * @param aPolicyList A list of policies to be mapped to the service request flow
    * @return List of handler definitions
    * @throws AeException
    */
   public List getServerRequestHandlers( List aPolicyList )
   throws AeException;

   /**
    * Maps sender side policy assertions to wsdd deployment document.
    * @param aPolicyList A list of policies to be mapped to the service response flow
    * @return List of handler definitions
    * @throws AeException
    */
   public List getServerResponseHandlers( List aPolicyList )
   throws AeException;
   
   /**
    * Maps client policy assertions to property name/value pairs.
    * @param aPolicyList A list of policies to be mapped
    * @return Map of property name/value pairs
    * @throws AeException
    */
   public Map getCallProperties( List aPolicyList )
   throws AeException;
   
   /**
    * Determines the appropriate deployment handler key from policy assertions.
    * If no specific deployment handler is determined from policies, implementations
    * should return null.
    * 
    * @param aPolicyList
    * @return deployment handler key
    * @throws AeException
    */
   public String getDeploymentHandler(List aPolicyList) throws AeException;
   
}
