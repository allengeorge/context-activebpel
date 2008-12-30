//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis.bpel/src/org/activebpel/rt/axis/bpel/handlers/IAePolicyHandler.java,v 1.1 2006/05/15 21:25:5
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
package org.activebpel.rt.axis.bpel.handlers;

import org.activebpel.rt.AeException;
import org.activebpel.rt.IAePolicyConstants;
import org.apache.axis.EngineConfiguration;
import org.apache.axis.deployment.wsdd.WSDDService;

/**
 * Interface for Policy driven handlers acting as pivots.  
 */
public interface IAePolicyHandler extends IAePolicyConstants
{
   /**
    * Initializes pivot handler with service and engine configuration
    * @param aService  Service deployment
    * @param aConfig   Engine configuration
    * @throws AeException
    */
   public abstract void init(WSDDService aService, EngineConfiguration aConfig) throws AeException;
}
