//$Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/wsdl/def/AeWSBPELPartnerLinkTypeIO.java,v 1.2 2006/06/26 16:46:4
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
package org.activebpel.rt.wsdl.def; 

import javax.wsdl.Definition;
import javax.wsdl.WSDLException;

import org.activebpel.rt.util.AeXmlUtil;
import org.w3c.dom.Element;


/**
 * Reads and writes a partner link type def for WS BPEL 2.0
 */
public class AeWSBPELPartnerLinkTypeIO extends AePartnerLinkTypeIO implements IAeBPELExtendedWSDLConst
{
   /**
    * @see org.activebpel.rt.wsdl.def.AePartnerLinkTypeIO#appendRole(org.activebpel.rt.wsdl.def.AeRoleImpl, org.w3c.dom.Element, javax.wsdl.Definition)
    */
   protected Element appendRole(AeRoleImpl aRole, Element aPartnerLinkElement, Definition aDefinition)
   {
      //<plnk:partnerLinkType name="NCName">
      //   <plnk:role name="NCName" portType="QName"/>
      //   <plnk:role name="NCName" portType="QName"/>?
      // </plnk:partnerLinkType>
      
      Element roleElement = aPartnerLinkElement.getOwnerDocument().createElementNS(aPartnerLinkElement.getNamespaceURI(), ROLE_TAG);
      roleElement.setAttribute(NAME_ATTRIB, aRole.getName());
      roleElement.setPrefix(aPartnerLinkElement.getPrefix());

      String ptQName = aDefinition.getPrefix( aRole.getPortType().getQName().getNamespaceURI() ) 
                              + ":" + aRole.getPortType().getQName().getLocalPart(); //$NON-NLS-1$
      
      roleElement.setAttribute(PORT_TYPE_TAG, ptQName);
      
      return roleElement;
   }

   /**
    * @see org.activebpel.rt.wsdl.def.AePartnerLinkTypeIO#readRole(org.w3c.dom.Element, javax.wsdl.Definition)
    */
   protected IAeRole readRole(Element aRoleElement, Definition aDefinition) throws WSDLException
   {
      AeRoleImpl role = new AeRoleImpl(aRoleElement.getAttribute(NAME_ATTRIB));
      role.setPortType(new AePortTypeImpl(AeXmlUtil.createQName(aRoleElement, aRoleElement.getAttribute(PORT_TYPE_TAG))));
      return role;
   }
}
 
