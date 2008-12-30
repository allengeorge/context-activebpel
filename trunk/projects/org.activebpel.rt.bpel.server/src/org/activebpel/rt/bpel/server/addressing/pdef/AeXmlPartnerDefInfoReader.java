// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/addressing/pdef/AeXmlPartnerDefInfoReader.java,v 1.4 2005/08/25 23:20:1
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

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeEndpointReference;
import org.activebpel.rt.bpel.server.addressing.IAePartnerAddressing;
import org.activebpel.rt.util.AeXmlUtil;

import javax.xml.namespace.QName;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Utility class for reading in pdef xml files.
 */
public class AeXmlPartnerDefInfoReader
{

   /**
    * Deserialize the pdef xml into the IAePartnerDefInfo.
    * @param aPdefXml
    * @param aAddressing
    * @throws AeBusinessProcessException
    */
   public static IAePartnerDefInfo read( Document aPdefXml, 
      IAePartnerAddressing aAddressing ) throws AeBusinessProcessException
                                             
   {
      String principal = aPdefXml.getDocumentElement().getAttribute("principal"); //$NON-NLS-1$
      AePartnerDefInfo info = new AePartnerDefInfo(principal);
      
      NodeList partnerLinkTypes = aPdefXml.getDocumentElement().getElementsByTagNameNS(aPdefXml.getDocumentElement().getNamespaceURI(), "partnerLinkType"); //$NON-NLS-1$
      int max = partnerLinkTypes.getLength();
      for( int i = 0; i < max; i++ )
      {
         Element pLinkElement = (Element)partnerLinkTypes.item(i);
         String pLinkName = pLinkElement.getAttribute("name"); //$NON-NLS-1$
         QName pLinkQName = AeXmlUtil.createQName( pLinkElement, pLinkName );
         
         Element roleElement = AeXmlUtil.getFirstSubElement(pLinkElement);
         String roleName = roleElement.getAttribute("name"); //$NON-NLS-1$
         IAeEndpointReference ref = aAddressing.readFromDeployment( roleElement );
   
         info.addInfo( pLinkQName, roleName, ref );
      }
      
      return info;
   }
}
