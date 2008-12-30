// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/addressing/pdef/IAePartnerAddressingProvider.java,v 1.3 2004/10/05 23:00:4
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
package org.activebpel.rt.bpel.server.addressing.pdef;

import org.activebpel.rt.bpel.server.deploy.IAeDeploymentId;

/**
 * Interface for accessing, modifying the partner address definition layer.
 */
public interface IAePartnerAddressingProvider extends IAePartnerAddressingAdmin
{
   /**
    * Merges the partner def information with any existing information.
    * @param aDeployment used to identify the deployment and maintain a reference count
    * @param aDeploymentLocation Url string for the deployment location.
    * @param aInfo The definition object.
    */
   public void addAddresses( IAeDeploymentId aDeployment, String aDeploymentLocation, IAePartnerDefInfo aInfo );
   
   /**
    * Accessor for the partner address book.
    * @param aPrincipal the key into the partner mappings
    * @return address book of all partner link types mapped to the principal or null if none is found
    */
   public IAePartnerAddressBook getAddressBook( String aPrincipal );
   
   /**
    * Attempt to remove any addresses mapped to the deployment context.
    * NOTE: these mappings will only be removed (at the partner link type level)
    * if the reference count is equal to zero
    * @param aDeploymentId
    */
   public void removeAddresses( IAeDeploymentId aDeploymentId );

}
