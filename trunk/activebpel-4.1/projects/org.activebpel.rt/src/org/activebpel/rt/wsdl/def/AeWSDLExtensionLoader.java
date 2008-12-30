//$Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/wsdl/def/AeWSDLExtensionLoader.java,v 1.1 2007/08/13 17:47:3
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

import javax.wsdl.Binding;
import javax.wsdl.BindingInput;
import javax.wsdl.BindingOperation;
import javax.wsdl.Definition;
import javax.wsdl.Message;
import javax.wsdl.Operation;
import javax.wsdl.Port;
import javax.wsdl.Service;
import javax.wsdl.extensions.ExtensionDeserializer;
import javax.wsdl.extensions.ExtensionRegistry;
import javax.wsdl.extensions.ExtensionSerializer;
import javax.xml.namespace.QName;

import org.activebpel.rt.IAeConstants;
import org.activebpel.rt.wsdl.def.policy.AePolicyIO;
import org.activebpel.rt.wsdl.def.policy.AePolicyImpl;
import org.activebpel.rt.wsdl.def.policy.AePolicyRefIO;
import org.activebpel.rt.wsdl.def.policy.AePolicyRefImpl;
import org.activebpel.rt.wsdl.def.policy.IAePolicy;
import org.activebpel.rt.wsdl.def.policy.IAePolicyReference;

/**
 * Populates an extension registry with IO objects for reading and writing 
 * WSDL extensions to and from a WSDL document. 
 */
public class AeWSDLExtensionLoader
{
   /**
    * No reason to instantiate this class 
    */
   private AeWSDLExtensionLoader()
   {
   }
   
   /**
    * Loads the registry with readers/writers for BPWS, WS-BPEL, and WS-Policy
    * 
    * @param aRegistry
    */
   public static void loadRegistry(ExtensionRegistry aRegistry)
   {
      loadBPELExtensions(aRegistry, AeBPELWSDLExtensionIOFactory.getFactory(IAeBPELExtendedWSDLConst.BPWS_NAMESPACE_URI));
      loadBPELExtensions(aRegistry, AeBPELWSDLExtensionIOFactory.getFactory(IAeBPELExtendedWSDLConst.WSBPEL_2_0_NAMESPACE_URI));
      loadWSPolicyExtensions(aRegistry);
   }

   /**
    * Loads the BPEL extensions
    * @param aRegistry
    */
   private static void loadBPELExtensions(ExtensionRegistry aRegistry, IAeBPELWSDLExtensionIOFactory factory)
   {
      // partner link reading and writing
      loadExtension(aRegistry, 
            Definition.class, 
            factory.getPartnerLinkTypeQName(), 
            AePartnerLinkTypeImpl.class, 
            factory.getPartnerLinkTypeDeserializer(), 
            factory.getPartnerLinkTypeSerializer());

      // property reading and writing
      loadExtension(aRegistry, 
            Definition.class, 
            factory.getPropertyQName(), 
            AePropertyImpl.class, 
            factory.getPropertyDeserializer(), 
            factory.getPropertySerializer());

      // property alias reading and writing
      loadExtension(aRegistry, 
            Definition.class, 
            factory.getPropertyAliasQName(), 
            AePropertyAliasImpl.class, 
            factory.getPropertyAliasDeserializer(), 
            factory.getPropertyAliasSerializer());
   }

   /**
    * Loads the policy extensions
    * @param aRegistry
    */
   private static void loadWSPolicyExtensions(ExtensionRegistry aRegistry)
   {
      Class[] clazz = new Class[] {
            Definition.class,
            Service.class,
            Port.class,
            Binding.class,
            BindingOperation.class,
            BindingInput.class,
            Operation.class,
            Message.class
      };
      
      QName policyName = new QName(IAeConstants.WSP_NAMESPACE_URI, IAePolicy.POLICY_ELEMENT);               
      AePolicyIO policyIO = new AePolicyIO();
      
      QName policyRefName = new QName(IAeConstants.WSP_NAMESPACE_URI, IAePolicyReference.POLICY_REFERENCE_ELEMENT);
      AePolicyRefIO policyRefIO = new AePolicyRefIO();
      
      for (int i = 0; i < clazz.length; i++)
      {
         loadExtension(aRegistry, clazz[i], policyName, AePolicyImpl.class, policyIO, policyIO);
         loadExtension(aRegistry, clazz[i], policyRefName, AePolicyRefImpl.class, policyRefIO, policyRefIO);
      }
   }

   /**
    * Loads the extension into the registry
    * @param aRegistry
    * @param aSubject
    * @param policyName
    * @param clazz
    * @param aDeserializer
    * @param aSerializer
    */
   private static void loadExtension(ExtensionRegistry aRegistry, Class aSubject, QName policyName, Class clazz, ExtensionDeserializer aDeserializer, ExtensionSerializer aSerializer)
   {
      aRegistry.mapExtensionTypes( aSubject, policyName, clazz);
      aRegistry.registerDeserializer( aSubject, policyName, aDeserializer);
      aRegistry.registerSerializer( aSubject, policyName, aSerializer);
   }
}
 
