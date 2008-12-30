//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/validate/AePddInfo.java,v 1.3 2006/02/24 16:37:3
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
package org.activebpel.rt.bpel.server.deploy.validate;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.namespace.QName;

import org.activebpel.rt.util.AeXmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Wraps the process deployment descriptor xml for validation.
 */
public class AePddInfo
{
   /** pdd name */
   protected String mName;
   /** pdd xml */
   protected Document mDoc;
   
   /**
    * Constructor.
    * @param aName
    * @param aDoc
    */
   public AePddInfo( String aName, Document aDoc )
   {
      mName = aName;
      mDoc = aDoc;
   }
   
   /**
    * Accessor for the pdd name.
    */
   public String getName()
   {
      return mName;
   }
   
   /**
    * Returns a collection of myRole partner link names defined in the pdd.
    * This collection will be empty if none are defined.
    */
   public Collection getMyRolePartnerLinkNames()
   {
      NodeList myRoles = getDoc().getElementsByTagNameNS( 
               getDoc().getDocumentElement().getNamespaceURI(), IAePredeploymentValidator.MYROLE_ELEMENT );
      return extractPartnerLinkNames( myRoles );
   }
   
   /**
    * Returns a collection of partnerRole partner link names defined in the pdd.
    * This collection will be empty if none are defined.
    */
   public Collection getPartnerRolePartnerLinkNames()
   {
      NodeList partnerRoles = getDoc().getElementsByTagNameNS( 
               getDoc().getDocumentElement().getNamespaceURI(), IAePredeploymentValidator.PARTNERROLE_ELEMENT );
      return extractPartnerLinkNames( partnerRoles );
   }
   
   /**
    * Utility method to extract partner link names from node list of either
    * myRole or partnerRole elements.  The partnerLink element will be a parent
    * node and the name will be the value of the name attribute.
    * @param aList
    */
   protected Collection extractPartnerLinkNames( NodeList aList )
   {
      Collection retVal = new ArrayList();
      if( aList != null )
      {
         int max = aList.getLength();
         for( int i = 0; i < max; i++ )
         {
            Element partnerLinkChild = (Element)aList.item(i);
            Element partnerLinkEl = (Element)partnerLinkChild.getParentNode();
            retVal.add( partnerLinkEl.getAttribute(IAePredeploymentValidator.NAME_ATTR) );
         }
      }
      return retVal;
   }
   
   /**
    * Extract the process qname from the pdd <code>Document</code>.
    */
   public QName getProcessQName()
   {
      String nameWithPrefix = getDoc().getDocumentElement().getAttribute( IAePredeploymentValidator.NAME_ATTR );
      String localPart = AeXmlUtil.extractLocalPart( nameWithPrefix );
      String prefix = AeXmlUtil.extractPrefix( nameWithPrefix );
      String namespace = AeXmlUtil.getNamespaceForPrefix( getDoc().getDocumentElement(), prefix );
      return new QName(namespace, localPart);
   }
   
   /**
    * Accessor for bpel location.
    */
   public String getBpelLocation()
   {
      return getDoc().getDocumentElement().getAttribute( IAePredeploymentValidator.LOCATION_ATTR );
   }
   
   /**
    * Accessor for pdd <code>Document</code>.
    */
   public Document getDoc()
   {
      return mDoc;
   }

}
