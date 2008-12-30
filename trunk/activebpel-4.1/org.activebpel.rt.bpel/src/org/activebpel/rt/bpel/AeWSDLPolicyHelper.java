// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/AeWSDLPolicyHelper.java,v 1.1 2007/07/27 18:13:0
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
package org.activebpel.rt.bpel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.wsdl.Binding;
import javax.wsdl.BindingOperation;
import javax.wsdl.Operation;
import javax.wsdl.Port;
import javax.wsdl.PortType;
import javax.wsdl.Service;
import javax.wsdl.extensions.ElementExtensible;
import javax.wsdl.extensions.ExtensibilityElement;
import javax.xml.namespace.QName;

import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.wsdl.def.AeBPELExtendedWSDLDef;
import org.activebpel.rt.wsdl.def.policy.AePolicyImpl;
import org.activebpel.rt.wsdl.def.policy.IAePolicy;
import org.activebpel.rt.wsdl.def.policy.IAePolicyReference;
import org.w3c.dom.Element;

/**
 * Utility helper class to do various WSDL policy attachment lookups.
 */
public class AeWSDLPolicyHelper
{
   /**
    * Returns the list of effective <code>IAePolicy</code> by merging the 
    * policies for all WSDL subjects defined by WS-Policy Attachment
    * 
    * Service Subject = Service object
    * Endpoint Subject = Port and Binding objects
    * Operation Subject = Operation and BindingOperation objects
    * Message Subject = BindingInput and Input Message objects
    * 
    * @param aProvider 
    * @param aService
    */
   public static List getEffectivePolicy(AeBPELExtendedWSDLDef aDef, QName aServiceName, String aPortName, String aOperationName)
   {
      if (aDef == null)
         return Collections.EMPTY_LIST;
      
      List policies = new ArrayList();
      
      Service service = (Service) aDef.getServices().get(aServiceName);
      if (service == null)
      {
         return policies;
      }

      // Service subject policies
      policies.addAll(getPolicies(aDef, service));
      
      // Endpoint subject policies
      Port port = service.getPort(aPortName);
      if (port == null)
      {
         return policies;
      }
      policies.addAll(getEndpointSubjectPolicies(aDef, port));
      
      // Operation subject policies
      Binding binding = port.getBinding();
      if (binding == null)
      {
         return policies;
      }
      policies.addAll(getOperationSubjectPolicies(aDef, binding, aOperationName));
      
      // Message subject policies
      policies.addAll(getMessageSubjectPolicies(aDef, binding, aOperationName));
      
      return policies;
   }

   /**
    * Returns the list of effective <code>IAePolicy</code> by merging the 
    * policies for the abstract WSDL subjects
    * 
    * Operation Subject = Operation object
    * Message Subject = Input Message objects
    * 
    * @param aProvider 
    * @param aService
    */
   public static List getEffectivePolicy(AeBPELExtendedWSDLDef aDef, QName aPortType, String aOperationName)
   {
      if (aDef == null)
         return Collections.EMPTY_LIST;
      
      List policies = new ArrayList();
      
      PortType portType = aDef.getPortType(aPortType);
      Operation op = portType.getOperation(aOperationName, null, null);
      if (op != null)
      {
         // Operation policies
         policies.addAll(getPolicies(aDef, op));
         // Message policies
         policies.addAll(getPolicies(aDef, op.getInput().getMessage()));
      }
      
      return policies;
   }
   
   /**
    * Returns the list of <code>IAePolicy</code> for an Endpoint subject
    * 
    * An Endpoint subject includes the Port, Binding, and PortType
    * 
    * @param aProvider 
    * @param aService
    */
   public static List getEndpointSubjectPolicies(AeBPELExtendedWSDLDef aDef, Port aPort)
   {
      List policies = new ArrayList();
      
      if (aPort == null)
      {
         return policies;
      }
      
      // add all the port policy
      policies.addAll(getPolicies(aDef, aPort));
      
      // add all the binding policy
      Binding binding = aPort.getBinding();
      if (binding != null)
      {
         policies.addAll(getPolicies(aDef, binding));
      }
      
      return policies;
   }

   /**
    * Returns the list of <code>IAePolicy</code> for an Operation subject
    * 
    * An Operation subject includes the BindingOperation, and PortType Operation
    * 
    * @param aProvider 
    * @param aService
    */
   public static List getOperationSubjectPolicies(AeBPELExtendedWSDLDef aDef, Binding aBinding, String aOperationName)
   {
      List policies = new ArrayList();
      
      if (aBinding == null)
      {
         return policies;
      }
            
      // add all the Binding Operation policy
      BindingOperation bop = aBinding.getBindingOperation(aOperationName, null, null);
      if (bop != null)
         policies.addAll(getPolicies(aDef, bop));

      // all the port type operation policy
      PortType portType = aBinding.getPortType();
      if (portType != null)
      {
         Operation op = portType.getOperation(aOperationName, null, null);
         if (op != null)
            policies.addAll(getPolicies(aDef, op));
      }
      
      return policies;
   }

   /**
    * Returns the list of <code>IAePolicy</code> for an Endpoint subject
    * 
    * A Message subject includes the BindingInput and Input Message
    * 
    * @param aProvider 
    * @param aService
    */
   public static List getMessageSubjectPolicies(AeBPELExtendedWSDLDef aDef, Binding aBinding, String aOperationName)
   {
      List policies = new ArrayList();
      
      if (aBinding == null)
      {
         return policies;
      }
            
      // add all the Binding Operation input policy
      BindingOperation bop = aBinding.getBindingOperation(aOperationName, null, null);
      if (bop != null)
         policies.addAll(getPolicies(aDef, bop.getBindingInput()));

      // all the message policy
      PortType portType = aBinding.getPortType();
      if (portType != null)
      {
         Operation op = portType.getOperation(aOperationName, null, null);
         if (op != null)
            policies.addAll(getPolicies(aDef, op.getInput().getMessage()));
      }
      
      return policies;
   }
   
   /**
    * Returns the list of <code>IAePolicy</code> elements associated with the 
    * given WSDL element (Service, PortType, Operation, Message,...)
    * 
    * A wsp:Policy element may be either a direct child, or resolved 
    * as a wsp:PolicyReference 
    * 
    * @param aProvider WSDL provider to resolve external references by namespace, if null no external searching is done
    * @param aDef WSDL provider to resolve local references, if null no local lookups are used
    * @param aExtElement element that may contain policy elements
    */
   public static List getPolicies(AeBPELExtendedWSDLDef aDef, ElementExtensible aExtElement)
   {
      if (aDef == null)
         return Collections.EMPTY_LIST;
      
      List elements = aExtElement.getExtensibilityElements();
      if (AeUtil.isNullOrEmpty(elements))
      {
         return Collections.EMPTY_LIST;
      }
      
      List policies = new ArrayList();
      for (Iterator it = elements.iterator(); it.hasNext();)
      {
         ExtensibilityElement ext = (ExtensibilityElement) it.next();
         IAePolicy policy = null;

         // see if we've got a wsp:Policy or wsp:PolicyReference
         if (ext instanceof IAePolicy)
         {
            policy = (IAePolicy) ext;
         }
         else if (ext instanceof IAePolicyReference)
         {
            IAePolicyReference ref = null;
            ref = (IAePolicyReference) ext;
            // See if we can resolve the reference using the given wsdl
            policy = getPolicy(aDef, ref);
         }
         
         // add the policy we found
         if (policy != null)
         {
            policies.add(policy);
         }
      }
      return policies;
   }

   /**
    * Returns the list of <code>IAePolicy</code> elements associated with the 
    * given DOM element
    * 
    * A wsp:Policy element may be either a direct child, or resolved 
    * as a wsp:PolicyReference 
    * 
    * @param aProvider WSDL provider to resolve external references by namespace
    * @param aExtElement element that may contain policy references
    */
   public static List getPolicies(IAeContextWSDLProvider aProvider, ElementExtensible aExtElement)
   {
      List elements = aExtElement.getExtensibilityElements();
      if (AeUtil.isNullOrEmpty(elements))
      {
         return Collections.EMPTY_LIST;
      }
      
      List policies = new ArrayList();
      for (Iterator it = elements.iterator(); it.hasNext();)
      {
         ExtensibilityElement ext = (ExtensibilityElement) it.next();
         IAePolicy policy = null;

         // see if we've got a wsp:Policy or wsp:PolicyReference
         if (ext instanceof IAePolicy)
         {
            policy = (IAePolicy) ext;
         }
         else if (ext instanceof IAePolicyReference)
         {
            IAePolicyReference ref = null;
            ref = (IAePolicyReference) ext;
            // See if we can resolve the reference using the given wsdl
            policy = getPolicy(aProvider, ref);
         }
         
         // add the policy we found
         if (policy != null)
         {
            policies.add(policy);
         }
      }
      return policies;
   }
   
   /**
    * Resolves and returns the IAePolicy element at the location specified in the URI attribute 
    * of a wsp:PolicyReference
    * 
    * For example, the namespace URI of "http://www.fabrikam123.com/policies#RmPolicy" 
    * is "http://www.fabrikam123.com/policies" and the wsu:Id is "RmPolicy"
    * 
    * If there's a null or empty namespace on the URI we'll just do the lookup using the id 
    * 
    * @param aProvider WSDL provider
    * @param aPolicyRef Policy Reference
    */
   public static IAePolicy getPolicy(AeBPELExtendedWSDLDef aDef, IAePolicyReference aPolicyRef)
   {
      if (aDef == null)
      {
         return null;
      }

      IAePolicy policy = null;
      if (aPolicyRef.isLocalReference())
      {
         policy = aDef.getPolicy(aPolicyRef.getReferenceId());
      }
      else 
      {
         policy = aDef.getPolicy(aPolicyRef.getNamespaceURI(), aPolicyRef.getReferenceId());
      }
      
      return policy;
   }

   /**
    * Resolves and returns the IAePolicy element at the location specified in the URI attribute 
    * of a wsp:PolicyReference
    * 
    * For example, the namespace URI of "http://www.fabrikam123.com/policies#RmPolicy" 
    * is "http://www.fabrikam123.com/policies" and the wsu:Id is "RmPolicy"
    * 
    * If there's a null or empty namespace on the URI we'll just do the lookup using the id 
    * 
    * @param aProvider WSDL provider
    * @param aPolicyRef Policy Reference
    */
   public static IAePolicy getPolicy(IAeContextWSDLProvider aProvider, IAePolicyReference aPolicyRef)
   {
      if (aProvider == null)
      {
         return null;
      }

      if (aPolicyRef == null)
      {
         return null;
      }
    
      IAePolicy policy = null;
      for (Iterator it = aProvider.getWSDLIterator(aPolicyRef.getNamespaceURI()); it.hasNext();)
      {
         AeBPELExtendedWSDLDef def = (AeBPELExtendedWSDLDef) aProvider.dereferenceIteration(it.next());
         policy = def.getPolicy(aPolicyRef.getReferenceId());
      }
      
      return policy;
   }
   
   /**
    * EndpointReference Policy elements may contain references to external policies that need to
    * be resolved before mapping
    * 
    * Note that references are resolved one layer deep.  If the referenced policy
    * contains references to yet another policy, a custom mapper would be needed to
    * handle the resolution beyond the first reference. 
    * 
    * @param aPolicyList
    * @return list of resolved policy references as elements
    */
   public static List resolvePolicyReferences(IAeContextWSDLProvider aProvider, List aPolicyElementList)
   {
      List resolved = new ArrayList();
      if (AeUtil.isNullOrEmpty(aPolicyElementList))
      {
         return Collections.EMPTY_LIST;
      }
      
      for (Iterator it = aPolicyElementList.iterator(); it.hasNext();) 
      {
         IAePolicy policy = new AePolicyImpl((Element) it.next());
         List policies = getPolicies(aProvider, policy );
         if (!AeUtil.isNullOrEmpty(policies))
         {
            for (Iterator p = policies.iterator(); p.hasNext();)
            {
               resolved.add(((IAePolicy) p.next()).getPolicyElement());
            }
         }
         else
         {
            resolved.add(policy.getPolicyElement());
         }
      }
      return resolved;
   }
   
}
