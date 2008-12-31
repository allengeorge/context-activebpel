//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/IAeEnginePartnerLinkStrategy.java,v 1.3 2006/09/27 00:36:2
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
package org.activebpel.rt.bpel.impl;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.wsio.receive.IAeMessageContext;

/**
 * Defines an interface for how a business process engine should manage
 * partner links.
 */
public interface IAeEnginePartnerLinkStrategy
{
   /**
    * Initialize a single partner link.  This is called when a scope executes and needs to init its
    * local partner links.
    * 
    * @param aPartnerLink
    * @param aPlan
    * @throws AeBusinessProcessException
    */
   public void initPartnerLink(AePartnerLink aPartnerLink, IAeProcessPlan aPlan)  throws AeBusinessProcessException;

   /**
    * Updates the partner link object with the data from the inbound receive.
    *
    * @param aPartnerLink
    * @param aPlan
    * @param aMessageContext
    * @throws AeBusinessProcessException
    */
   public void updatePartnerLink(AePartnerLink aPartnerLink, IAeProcessPlan aPlan, IAeMessageContext aMessageContext) throws AeBusinessProcessException;
}
