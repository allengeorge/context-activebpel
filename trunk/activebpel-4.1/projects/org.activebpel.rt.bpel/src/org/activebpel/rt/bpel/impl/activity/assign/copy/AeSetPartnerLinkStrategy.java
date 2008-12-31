//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/assign/copy/AeSetPartnerLinkStrategy.java,v 1.4 2006/08/24 19:22:4
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
package org.activebpel.rt.bpel.impl.activity.assign.copy; 

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAePartnerLink;
import org.activebpel.rt.bpel.def.IAeBPELConstants;
import org.activebpel.rt.bpel.impl.AeBpelException;
import org.activebpel.rt.bpel.impl.activity.assign.AeMismatchedAssignmentException;
import org.activebpel.rt.bpel.impl.activity.assign.IAeCopyOperation;
import org.activebpel.rt.bpel.impl.activity.assign.IAeCopyStrategy;
import org.activebpel.rt.util.AeXmlUtil;
import org.w3c.dom.Element;

/**
 * Copies a service-ref element into a partnerLink's partnerRole  
 */
public class AeSetPartnerLinkStrategy implements IAeCopyStrategy
{
   /**
    * @see org.activebpel.rt.bpel.impl.activity.assign.IAeCopyStrategy#copy(org.activebpel.rt.bpel.impl.activity.assign.IAeCopyOperation, java.lang.Object, java.lang.Object)
    */
   public void copy(IAeCopyOperation aCopyOperation, Object aFromData, Object aToData) throws AeBpelException
   {
      Element src = (Element) aFromData;
      IAePartnerLink target = (IAePartnerLink) aToData;
      
      // NOTE: BPEL 2.x expects eprs to be wrapped in a sref:service-ref. i.e. aFromData must be a <sref:service-ref>.      
      // For now, we will not enforce this case.
      
      // if the wsa:EndpointReference is wrapped in a sref:service-ref, then extract
      // the epr element, assuming that the epr is the first child element inside the service-ref.
      QName srcQName = AeXmlUtil.getElementType(src);
      if (IAeBPELConstants.WS_BPEL_SERVICE_REF.equals(srcQName))
      {
         //unwrap  bpws 2.0 service-ref element.
         src = AeXmlUtil.getFirstSubElement(src);
      }
      try
      {
         target.getPartnerReference().setReferenceData(src);
      }
      catch (AeBusinessProcessException e)
      {
         throw new AeMismatchedAssignmentException(aCopyOperation.getContext().getBPELNamespace(),e);
      }
   }
}
 
