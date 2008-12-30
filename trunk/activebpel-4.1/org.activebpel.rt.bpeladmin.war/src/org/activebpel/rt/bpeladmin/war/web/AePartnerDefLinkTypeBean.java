//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/web/AePartnerDefLinkTypeBean.java,v 1.3 2005/10/17 19:55:4
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
package org.activebpel.rt.bpeladmin.war.web;

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.server.addressing.pdef.IAePartnerDefInfo;
import org.activebpel.rt.xml.AeXMLParserBase;
import org.w3c.dom.Document;

/**
 * This bean is used by the admin console when the popup info window is shown
 * for a single partner def's partner link type.
 */
public class AePartnerDefLinkTypeBean extends AeAbstractAdminBean
{
   /** Principal. */
   private String mPrincipal;
   /** Partner link type. */
   private QName mPartnerLinkType;
   /** Role. */
   private String mRole;
   /** Endpoint reference. */
   private String mEndpointReference;

   /**
    * Default constructor.
    */
   public AePartnerDefLinkTypeBean()
   {
      mPartnerLinkType = new QName("", ""); //$NON-NLS-1$ //$NON-NLS-2$
   }

   /**
    * Sets the partner link's namespace.
    * 
    * @param aPartnerLinkLP
    */
   public void setPartnerLinkNS(String aPartnerLinkNS)
   {
      mPartnerLinkType = new QName(aPartnerLinkNS, mPartnerLinkType.getLocalPart());
   }

   /**
    * Sets the partner link's local part.
    * 
    * @param aPartnerLinkLP
    */
   public void setPartnerLinkLP(String aPartnerLinkLP)
   {
      mPartnerLinkType = new QName(mPartnerLinkType.getNamespaceURI(), aPartnerLinkLP);
   }
   
   /**
    * Method that is called when the JSP has set all the values on the bean.
    * 
    * @param aBool
    */
   public void setFinished(boolean aBool)
   {
      if (aBool)
      {
         IAePartnerDefInfo info = getAdmin().getPartnerAddressingAdmin().getPartnerInfo( getPrincipal() );
         // Set the role
         setRole(info.getRoleName(getPartnerLinkType()));
         // Set the endpoint reference
         Document doc = info.getEndpointReference(getPartnerLinkType()).toDocument();
         setEndpointReference(AeXMLParserBase.documentToString(doc, true));
      }
   }

   /**
    * Getter for the endpoint reference.
    */
   public String getEndpointReference()
   {
      return mEndpointReference;
   }
   
   /**
    * Setter for the endpoint reference.
    * 
    * @param aEndpointReference
    */
   public void setEndpointReference(String aEndpointReference)
   {
      mEndpointReference = aEndpointReference.trim();
   }
   
   /**
    * Getter for the partner link type.
    */
   public QName getPartnerLinkType()
   {
      return mPartnerLinkType;
   }
   
   /**
    * Setter for the partner link type.
    * 
    * @param aPartnerLinkType
    */
   public void setPartnerLinkType(QName aPartnerLinkType)
   {
      mPartnerLinkType = aPartnerLinkType;
   }
   
   /**
    * Getter for the role.
    */
   public String getRole()
   {
      return mRole;
   }
   
   /**
    * Setter for the role.
    * 
    * @param aRole
    */
   public void setRole(String aRole)
   {
      mRole = aRole;
   }
   
   /**
    * Getter for the principal.
    */
   public String getPrincipal()
   {
      return mPrincipal;
   }

   /**
    * Setter for the principal.
    * 
    * @param aPrincipal
    */
   public void setPrincipal(String aPrincipal)
   {
      mPrincipal = aPrincipal;
   }
}
