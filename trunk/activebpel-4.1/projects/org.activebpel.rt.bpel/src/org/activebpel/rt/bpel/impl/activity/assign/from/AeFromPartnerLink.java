//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/assign/from/AeFromPartnerLink.java,v 1.5 2006/08/24 19:22:4
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
package org.activebpel.rt.bpel.impl.activity.assign.from; 

import org.activebpel.rt.bpel.IAeEndpointReference;
import org.activebpel.rt.bpel.IAePartnerLink;
import org.activebpel.rt.bpel.def.IAeBPELConstants;
import org.activebpel.rt.bpel.def.activity.support.AeFromDef;
import org.activebpel.rt.bpel.impl.AeBpelException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Handles reading a partner link from the myRole or partnerRole 
 */
public class AeFromPartnerLink extends AeFromBase
{
   /** name of the partner link */
   private String mPartnerLinkName;
   /** true if we're selecting the partner role */
   private boolean mPartnerRole;
   
   /**
    * Ctor accepts def and context
    * 
    * @param aFromDef
    */
   public AeFromPartnerLink(AeFromDef aFromDef)
   {
      this(aFromDef.getPartnerLink(), IAeBPELConstants.TAG_PARTNER_ROLE.equals(aFromDef.getEndpointReference()));
   }
   
   /**
    * Ctor accepts plink name and role flag
    * @param aPartnerLinkName
    * @param aPartnerRoeFlag
    */
   public AeFromPartnerLink(String aPartnerLinkName, boolean aPartnerRoeFlag)
   {
      setPartnerLinkName(aPartnerLinkName);
      setPartnerRole(aPartnerRoeFlag);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.assign.IAeFrom#getFromData()
    */
   public Object getFromData() throws AeBpelException
   {
      IAePartnerLink plink = getCopyOperation().getContext().getPartnerLink(getPartnerLinkName());
      IAeEndpointReference epr =  isPartnerRole() ? plink.getPartnerReference() : plink.getMyReference();
      boolean bpws11 = IAeBPELConstants.BPWS_NAMESPACE_URI.equals( getCopyOperation().getContext().getBPELNamespace() );
      // expect epr document to be a wsa:EndpointReference element.
      Element eprElement = epr.toDocument().getDocumentElement();
      if (bpws11)
      {
         // for BPEL 1.1, return wsa:EndpointReference element as is.
         return eprElement;
      }
      else
      {
         // For BPEL 2.x, return wsa:EndpointReference element in a bpel 2.0 service-ref element.
         Document dom = eprElement.getOwnerDocument();
         // create service-ref element
         Element serviceRefEle = dom.createElementNS(IAeBPELConstants.WS_BPEL_SERVICE_REF.getNamespaceURI(), IAeBPELConstants.WS_BPEL_SERVICE_REF.getLocalPart());
         // replace the root (epr) element with the service element
         dom.replaceChild(serviceRefEle, eprElement);
         // make the epr element a child of the service element.
         serviceRefEle.appendChild(eprElement);
         return serviceRefEle;
      }
   }

   /**
    * @return Returns the partnerLinkName.
    */
   public String getPartnerLinkName()
   {
      return mPartnerLinkName;
   }

   /**
    * @param aPartnerLinkName The partnerLinkName to set.
    */
   public void setPartnerLinkName(String aPartnerLinkName)
   {
      mPartnerLinkName = aPartnerLinkName;
   }

   /**
    * @return Returns the partnerRole.
    */
   public boolean isPartnerRole()
   {
      return mPartnerRole;
   }

   /**
    * @param aPartnerRole The partnerRole to set.
    */
   public void setPartnerRole(boolean aPartnerRole)
   {
      mPartnerRole = aPartnerRole;
   }
}
 
