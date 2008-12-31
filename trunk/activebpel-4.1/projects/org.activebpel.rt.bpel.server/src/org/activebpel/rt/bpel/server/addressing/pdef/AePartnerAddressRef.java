// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/addressing/pdef/AePartnerAddressRef.java,v 1.4 2004/10/05 23:00:4
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

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.IAeEndpointReference;
import org.activebpel.rt.bpel.server.deploy.IAeDeploymentId;

/**
 * This class models the mapping of a parnterLinkType and role to endpoint reference.
 * In addition, it keeps track of the references to this mapping (as there may be
 * more than one deployment using the same partner address mappings).
 */
public class AePartnerAddressRef
{
   /** partner link type */
   protected QName mPartnerLinkType;
   /** role name */
   protected String mRoleName;
   /** end point reference */
   protected IAeEndpointReference mEndpointReference;
   /** all of the deployment contexts mapped to this partner address */
   protected Map mReferences;
   
   /**
    * Constructor.
    * @param aPlinkType
    * @param aRoleName
    */
   public AePartnerAddressRef( QName aPlinkType, String aRoleName )
   {
      mPartnerLinkType = aPlinkType;
      mRoleName = aRoleName;
      mReferences = new HashMap();
   }
   
   /**
    * Overwrite the existing endpoint reference with the given endpoint reference
    * and increment the reference count.
    * @param aRef
    * @param aDeploymentId
    * @param aLocation
    */
   public void updateEndpoint( IAeEndpointReference aRef, IAeDeploymentId aDeploymentId, String aLocation )
   {
      mEndpointReference = aRef;
      mReferences.put( aDeploymentId, aLocation );      
   }
   
   /**
    * Accessor for the partnerLinkType.
    */
   public QName getPartnerLinkType()
   {
      return mPartnerLinkType;
   }
   
   /**
    * Accessor for the role name.
    */
   public String getRoleName()
   {
      return mRoleName;
   }
   
   /**
    * Removes a (deployment) reference to this mapping.
    * @param aDeploymentId
    */
   public void remove( IAeDeploymentId aDeploymentId )
   {
      mReferences.remove(aDeploymentId);
   }
   
   /**
    * Will return true if there are no (deployment) references to this mapping.
    */
   public boolean isOkToRemove()
   {
      return mReferences.isEmpty();
   }
   
   /**
    * Accessor for a iterator over the unmodifiable set of deployment references.
    */
   public Iterator getReferences()
   {
      return Collections.unmodifiableCollection(mReferences.values()).iterator();
   }
   
   /**
    * Accessor for the endpoint reference.
    */
   public IAeEndpointReference getEndpointRef()
   {
      return mEndpointReference;
   }
}
