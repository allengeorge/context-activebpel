// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/AePartnerLink.java,v 1.18 2006/10/26 13:49:0
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

import java.text.MessageFormat;

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.bpel.IAeEndpointReference;
import org.activebpel.rt.bpel.IAePartnerLink;
import org.activebpel.rt.bpel.def.AePartnerLinkDef;
import org.activebpel.rt.bpel.impl.activity.AeActivityScopeImpl;
import org.activebpel.rt.bpel.impl.activity.support.AeScopedObject;
import org.activebpel.rt.util.AeUtil;
import org.w3c.dom.Document;

/** The class implements partner interactions. */
public class AePartnerLink extends AeScopedObject implements IAePartnerLink, Cloneable
{
   /** The authenticated principal that used this partner link */
   private String mPrincipal;
   /** The Endpoint reference for "myRole" of the partner link */
   private IAeEndpointReference mMyReference;
   /** The Endpoint reference for "partnerRole" of the partner link */
   private IAeEndpointReference mPartnerReference;

   // Note on versionNumber: The versionNumber field is not really a version 
   // number per se but rather a unique id assigned to each version of a 
   // variable within a process. The process maintains an int to use and 
   // increment with each new variable created.

   /** The version number of the variable. Increments with each change */
   private int mVersionNumber;

   /**
    * Constructs a new business partner link from a definition object
    * @param aPartnerLinkDef definition for the partner link
    **/
   public AePartnerLink(AeActivityScopeImpl aScope, AePartnerLinkDef aPartnerLinkDef)
   {
      super(aScope, aPartnerLinkDef);

      // Create an endpoint reference for "myRole" if applicable
      if (aPartnerLinkDef.getMyRole() != null)
         mMyReference = new AeEndpointReference();

      // Create an endpoint reference folr "partenerRole" if applicable
      if (aPartnerLinkDef.getPartnerRole() != null)
         mPartnerReference = new AeEndpointReference();

      // Incrementing the version number here to avoid a problem introduced with
      // parallel forEach's. In that case, unassigned partner links will have 
      // the same locationId and versionNumber which will be a problem with the 
      // persistence layer
      incrementVersionNumber();
   }

   /**
    * Get the definition associated with this partner link.
    **/
   public AePartnerLinkDef getDefinition()
   {
      return (AePartnerLinkDef) getBaseDef();
   }

   /**
    * Returns the endpoint reference for "myRole" or null if not defined.
    */
   public IAeEndpointReference getMyReference()
   {
      return mMyReference;
   }

   /**
    * Returns the endpoint reference for "partnerRole" or null if not defined.
    */
   public IAeEndpointReference getPartnerReference()
   {
      return mPartnerReference;
   }

   /**
    * Equality is determined by comparing the name of the partnerlink as well as
    * the values for the endpoint references
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aObject)
   {
      if (aObject instanceof IAePartnerLink)
      {
         IAePartnerLink other = (IAePartnerLink) aObject;
         // compare the name and the endpoint references
         return AeUtil.compareObjects(other.getName(), getName()) &&
                AeUtil.compareObjects(other.getPartnerReference(), getPartnerReference()) &&
                AeUtil.compareObjects(other.getMyReference(), getMyReference());
      }
      return super.equals(aObject);
   }

   /**
    * @see java.lang.Object#hashCode()
    */
   public int hashCode()
   {
      return getName().hashCode();
   }

   /**
    * @see org.activebpel.rt.bpel.IAePartnerLink#getPartnerLinkType()
    */
   public QName getPartnerLinkType()
   {
      return getDefinition().getPartnerLinkTypeName();
   }

   /**
    * Setter for the principal
    * @param aPrincipal
    */
   public void setPrincipal(String aPrincipal) throws AeBusinessProcessException
   {
      if (!AeUtil.isNullOrEmpty(getPrincipal()))
      {
         // previous principal was set to something, it better be set to the 
         // same value as the param or we've been contacted by the wrong partner
         if (!getPrincipal().equals(aPrincipal))
         {
            String pattern = AeMessages.getString("AePartnerLink.0"); //$NON-NLS-1$
            Object[] args = {getName(), getPrincipal(), aPrincipal};

            throw new AeBusinessProcessException(MessageFormat.format(pattern, args));
         }
      }
      mPrincipal = aPrincipal;
   }

   /**
    * Getter for the principal
    */
   public String getPrincipal()
   {
      return mPrincipal;
   }

   /**
    * @see org.activebpel.rt.bpel.IAePartnerLink#getMyRole()
    */
   public String getMyRole()
   {
      return getDefinition().getMyRole();
   }

   /**
    * @see org.activebpel.rt.bpel.IAePartnerLink#getPartnerRole()
    */
   public String getPartnerRole()
   {
      return getDefinition().getPartnerRole();
   }

   /**
    * @see org.activebpel.rt.bpel.IAePartnerLink#getVersionNumber()
    */
   public int getVersionNumber()
   {
      return mVersionNumber;
   }

   /**
    * @see org.activebpel.rt.bpel.IAePartnerLink#incrementVersionNumber()
    */
   public void incrementVersionNumber()
   {
      // Note: not thread safe but this isn't an issue with the current impl 
      // since only one activity will be executing at a time.
      IAeBusinessProcessInternal process = getProcess();
      setVersionNumber(process.getNextVersionNumber());
      process.setNextVersionNumber(getVersionNumber() + 1);
   }

   /**
    * @see org.activebpel.rt.bpel.IAePartnerLink#setVersionNumber(int)
    */
   public void setVersionNumber(int aVersionNumber)
   {
      mVersionNumber = aVersionNumber;
   }

   /**
    * @see java.lang.Object#clone()
    */
   public Object clone()
   {
      try
      {
         AePartnerLink clone = (AePartnerLink) super.clone();
         if (mMyReference != null)
         {
            Document myRefDoc = mMyReference.toDocument();
            clone.mMyReference = new AeEndpointReference();
            clone.mMyReference.setReferenceData(myRefDoc.getDocumentElement());
         }
         if (mPartnerReference != null)
         {
            Document partnerRefDoc = mPartnerReference.toDocument();
            clone.mPartnerReference = new AeEndpointReference();
            clone.mPartnerReference.setReferenceData(partnerRefDoc.getDocumentElement());
         }

         return clone;
      }
      catch (Exception ex)
      {
         throw new InternalError("Unexpected error during clone: " + ex.getLocalizedMessage()); //$NON-NLS-1$
      }
   }

   /**
    * @see org.activebpel.rt.bpel.IAePartnerLink#clear()
    */
   public void clear()
   {
      // Create an empty endpoint reference for the "partenerRole" if applicable
      if (getDefinition().getPartnerRole() != null)
      {
         mPartnerReference = new AeEndpointReference();
         incrementVersionNumber();
      }
   }

   /**
    * @see org.activebpel.rt.bpel.IAePartnerLink#getConversationId()
    */
   public String getConversationId()
   {
      String id = String.valueOf(getProcess().getProcessId()); 
      id = id.concat(":").concat(getLocationPath()); //$NON-NLS-1$
      return id;
   }
}
