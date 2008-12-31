// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/IAePartnerLink.java,v 1.9 2006/10/26 14:01:3
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
package org.activebpel.rt.bpel;

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.def.AePartnerLinkDef;

/**
 * Describes the interface used for interacting with business process partner 
 * links.
 */
public interface IAePartnerLink extends IAeLocatableObject
{
   /**
    * Gets the name of the partner link
    */
   public String getName();

   /**
    * Gets the type of the partner link
    */
   public QName getPartnerLinkType();

   /**
    * Get the version number.
    */
   public int getVersionNumber();

   /**
    * Returns the endpoint reference for "myRole" or null if not defined.
    */
   public IAeEndpointReference getMyReference();

   /**
    * Returns the endpoint reference for "partnerRole" or null if not defined.
    */
   public IAeEndpointReference getPartnerReference();

   /**
    * Returns the name of the role that the process is playing.
    */
   public String getMyRole();

   /**
    * Returns the name of the role that the partner is playing.
    */
   public String getPartnerRole();

   /**
    * Getter for the principal
    */
   public String getPrincipal();

   /**
    * Setter for the principal
    * @param aPrincipal
    * @throws AeBusinessProcessException If the principal was already set to 
    *         some value other than null.
    */
   public void setPrincipal(String aPrincipal) throws AeBusinessProcessException;

   /**
    * Sets the version number.
    *
    * @param aVersionNumber
    */
   public void setVersionNumber(int aVersionNumber);
   
   /**
    * Increments the version number for the variable 
    */
   public void incrementVersionNumber();

   /**
    * Clears the partner link value. Called from the partner link's declaring
    * scope each time the scope is going to execute since the partner link's 
    * state is not preserved across invocations.
    */
   public void clear();
   
   /**
    * Gets the definition for the plink
    */
   public AePartnerLinkDef getDefinition();
   
   /**
    * Gets the conversation id for engine managed correlation 
    */
   public String getConversationId();
   
}
