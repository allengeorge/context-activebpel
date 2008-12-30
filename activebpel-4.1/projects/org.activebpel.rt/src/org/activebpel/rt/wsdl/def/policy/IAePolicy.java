//$Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/wsdl/def/policy/IAePolicy.java,v 1.1 2007/07/27 18:08:5
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
package org.activebpel.rt.wsdl.def.policy;

import javax.wsdl.extensions.ElementExtensible;
import javax.wsdl.extensions.ExtensibilityElement;
import javax.xml.namespace.QName;

import org.activebpel.rt.IAeConstants;
import org.w3c.dom.Element;

/**
 * Interface for classes that represent a wsp:Policy extensibility element in 
 * a WSDL document
 */
public interface IAePolicy extends ExtensibilityElement, ElementExtensible 
{
   public static final String WSU_ID_ATTRIBUTE = "Id"; //$NON-NLS-1$
   public static final String POLICY_ELEMENT = "Policy"; //$NON-NLS-1$
   public static final QName POLICY_QNAME = new QName(IAeConstants.WSP_NAMESPACE_URI, POLICY_ELEMENT);
   
   /**
    * @return the wsu:Id relative to the base URI
    */
   public String getReferenceId();

   /**
    * @param aId the wsu:Id to set
    */
   public void setReferenceId(String aId);

   /**
    * @return the wsp:Policy element
    */
   public Element getPolicyElement();

   /**
    * @param aPolicy the policy element to set
    */
   public void setPolicyElement(Element aPolicy);
   
}
