//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/validate/AeStaticEndpointReferenceValidator.java,v 1.5 2006/07/18 20:05:3
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

import java.util.Iterator;

import javax.wsdl.Service;

import org.activebpel.rt.bpel.AeWSDLDefHelper;
import org.activebpel.rt.bpel.IAeContextWSDLProvider;
import org.activebpel.rt.bpel.IAeEndpointReference;
import org.activebpel.rt.bpel.IAeWSDLProvider;
import org.activebpel.rt.bpel.def.AePartnerLinkDef;
import org.activebpel.rt.bpel.def.validation.IAeBaseErrorReporter;
import org.activebpel.rt.bpel.server.AeMessages;
import org.activebpel.rt.bpel.server.IAeProcessDeployment;
import org.activebpel.rt.bpel.server.deploy.IAeDeploymentSource;
import org.activebpel.rt.bpel.server.deploy.pdd.AePartnerLinkDescriptor;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.wsdl.def.AeBPELExtendedWSDLDef;

/**
 * Emit a warning for static endpoint references with service names if
 * we can't find a matching port in the catalog.
 */
public class AeStaticEndpointReferenceValidator
{
   // WARNING constants
   private static final String COULD_NOT_FIND_PORT = AeMessages.getString("AeStaticEndpointReferenceValidator.NO_PORT_FOUND"); //$NON-NLS-1$

   /**
    * Validates a partner reference against the WSDL.
    * 
    * @param aReporter
    * @param aPartnerReference
    * @param aWsdlProvider
    * @param aProcessName
    */
   private static void validatePartnerReference(IAeBaseErrorReporter aReporter,
         IAeEndpointReference aPartnerReference, IAeWSDLProvider aWsdlProvider, String aProcessName)
   {
      if (aPartnerReference != null && aPartnerReference.getServiceName() != null)
      {
         AeBPELExtendedWSDLDef wsdlDef = AeWSDLDefHelper.getWSDLDefinitionForService( aWsdlProvider, aPartnerReference.getServiceName() );
         if( wsdlDef != null )
         {
            Service wsdlService = (Service)wsdlDef.getServices().get( aPartnerReference.getServiceName() );
            
            if( wsdlService != null && wsdlService.getPort( aPartnerReference.getServicePort() ) == null )
            {
               String[] args = { aProcessName, aPartnerReference.getServicePort(), AeUtil.qNameToString(aPartnerReference.getServiceName()) };
               aReporter.addWarning( COULD_NOT_FIND_PORT, args, null );
            }
         }
      }
   }
   
   /**
    * Emit a warning for static endpoint references with service names if
    * we can't find a matching port in the catalog.
    * @param aReporter
    * @param aDeployment
    */
   public static void validate(IAeBaseErrorReporter aReporter, IAeProcessDeployment aDeployment)
   {
      for (Iterator it = aDeployment.getProcessDef().getAllPartnerLinkDefs(); it.hasNext(); )
      {
         AePartnerLinkDef plinkDef = (AePartnerLinkDef) it.next();

         IAeEndpointReference partnerRef = aDeployment.getPartnerEndpointRef(plinkDef.getLocationPath());
         validatePartnerReference(aReporter, partnerRef,  aDeployment, aDeployment.getProcessDef().getName());
      }
   }
   
   /**
    * Emit a warning for static endpoint references with service names if
    * we can't find a matching port in the catalog.
    * 
    * @param aReporter
    * @param aProvider
    * @param aSource
    */
   public static void validate(IAeBaseErrorReporter aReporter, IAeContextWSDLProvider aProvider,
         IAeDeploymentSource aSource)
   {
      for (Iterator iter = aSource.getPartnerLinkDescriptors().iterator(); iter.hasNext(); )
      {
         AePartnerLinkDescriptor desc = (AePartnerLinkDescriptor)iter.next();
         IAeEndpointReference partnerRef = desc.getPartnerEndpointReference();
         validatePartnerReference(aReporter, partnerRef,  aProvider, aSource.getProcessDef().getName());
      }
   }
}
