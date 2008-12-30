//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/pdd/AePartnerLinkDescriptorFactory.java,v 1.9 2006/06/26 18:28:2
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
package org.activebpel.rt.bpel.server.deploy.pdd;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.def.AePartnerLinkDef;
import org.activebpel.rt.bpel.def.AeProcessDef;
import org.activebpel.rt.bpel.server.AeMessages;
import org.activebpel.rt.bpel.server.deploy.AeDeploymentException;
import org.activebpel.rt.bpel.server.deploy.IAePddXmlConstants;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.w3c.dom.Element;

/**
 * Factory class for creating <code>AePartnerLinkDescriptor</code> objects.
 */
public class AePartnerLinkDescriptorFactory implements IAePddXmlConstants
{
   /** Singleton instance. */
   private static final AePartnerLinkDescriptorFactory INSTANCE = new AePartnerLinkDescriptorFactory();
   
   /**
    * Accessor for factory.
    */
   public static AePartnerLinkDescriptorFactory getInstance()
   {
      return INSTANCE;
   }
   
   /**
    * Create the <code>AePartnerLinkDescriptor</code> object.
    * @param aPartnerLinkElement
    * @param aDef
    * @throws AeDeploymentException
    */
   public AePartnerLinkDescriptor createPartnerLinkDesc( Element aPartnerLinkElement, 
         AeProcessDef aDef ) throws AeDeploymentException
   {
      String partnerLinkName = aPartnerLinkElement.getAttribute(ATT_NAME);
      String partnerLinkLoc = aPartnerLinkElement.getAttribute(ATT_LOCATION);
      AePartnerLinkDef plDef = null;
      if (AeUtil.notNullOrEmpty(partnerLinkLoc))
         plDef = aDef.findPartnerLink( partnerLinkLoc );
      else
         plDef = aDef.findPartnerLink( partnerLinkName );

      if (plDef == null)
         throw new AeDeploymentException(AeMessages.getString("AePartnerLinkDescriptorFactory.PARTNER_LINK_NOT_FOUND_ERROR") + partnerLinkName); //$NON-NLS-1$

      AePartnerLinkDescriptor partnerLinkDesc = null;
      Element partnerRoleElement = AeXmlUtil.findSubElement( aPartnerLinkElement, TAG_PARTNER_ROLE );
      try
      {
         if( partnerRoleElement != null )
         {
            partnerLinkDesc = new AePartnerLinkDescriptor( partnerLinkName, plDef.getLocationId(), partnerRoleElement );
         }
         else
         {
            partnerLinkDesc = new AePartnerLinkDescriptor( partnerLinkName, plDef.getLocationId(), null );
         }
      }
      catch( AeBusinessProcessException abe )
      {
         // caused by some problem parsing the WSA endpoint ref if
         // the type is static
         throw new AeDeploymentException(abe.getLocalizedMessage(), abe);
      }
      return partnerLinkDesc;
   }
}
