//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/IAeServiceDeploymentInfo.java,v 1.1 2007/02/13 15:26:5
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
import java.util.Set;

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.def.AePartnerLinkDefKey;
import org.w3c.dom.Element;

/**
 * Models service data for persistence.
 */
public interface IAeServiceDeploymentInfo
{

   /**
    * Accessor for service name.
    */
   public String getServiceName();

   /**
    * Accessor for the partner link name.
    */
   public String getPartnerLinkName();

   /**
    * Gets the partner link def key reference.
    */
   public AePartnerLinkDefKey getPartnerLinkDefKey();

   /**
    * Accessor for binding string.
    */
   public String getBinding();

   /**
    * Returns true if the binding is MSG.
    */
   public boolean isMessageService();

   /**
    * Returns true if the binding is RPC
    */
   public boolean isRPCEncoded();

   /**
    * Returns true if the binding is RPC literal
    */
   public boolean isRPCLiteral();

   /**
    * Returns true if the binding is EXTERNAL
    */
   public boolean isExternalService();

   /**
    * Returns true if the binding is policy driven
    */
   public boolean isPolicyService();

   /**
    * Add a role to the service data.
    */
   public void addRole(String aRole);

   /**
    * Accessor for comma delimited allowed roles.  May be null.
    */
   public String getAllowedRolesAsString();

   /**
    * Accessor for allowed roles as a set. May be empty.
    */
   public Set getAllowedRoles();

   /**
    * Accessor for process qname.
    */
   public QName getProcessQName();

   /**
    * Accessor for process name.
    */
   public String getProcessName();

   /**
    * Add a policy to the list of policies.
    */
   public void addPolicy(Element policy);

   /**
    * Accessor for list of policies.
    */
   public List getPolicies();

}
