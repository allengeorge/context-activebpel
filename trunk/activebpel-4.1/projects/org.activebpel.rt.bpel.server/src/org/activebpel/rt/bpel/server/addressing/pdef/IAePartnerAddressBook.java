// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/addressing/pdef/IAePartnerAddressBook.java,v 1.2 2004/07/08 13:10:0
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

import org.activebpel.rt.bpel.IAeEndpointReference;

import javax.xml.namespace.QName;

/**
 * Wraps all of the parnter definitions for a given principal.
 * NOTE: this can be an aggregate of multiple deployments as the
 * principal serves as the primary key.
 */
public interface IAePartnerAddressBook
{

   /**
    * The accessor for the principal.
    */
   public String getPrincipal();
   
   /**
    * Returns the IAeEndpointReference mapped to the given partner link type and role.
    * @param aPartnerLinkType
    * @param aRole
    */
   public IAeEndpointReference getEndpointReference( QName aPartnerLinkType, String aRole );

}
