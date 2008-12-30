//$Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/wsdl/def/IAeBPELWSDLExtensionIOFactory.java,v 1.1 2007/08/13 17:46:3
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

import javax.wsdl.extensions.ExtensionDeserializer;
import javax.wsdl.extensions.ExtensionSerializer;
import javax.xml.namespace.QName;

/**
 * Interface for factory that provides the wsdl4j impls for reading/writing bpel extensions to wsdl   
 */
public interface IAeBPELWSDLExtensionIOFactory
{
   /**
    * Getter for the qname used for the partnerLinkType element
    */
   public QName getPartnerLinkTypeQName();
   
   /**
    * Getter for the qname used for the property element
    */
   public QName getPropertyQName();
   
   /**
    * Getter for the qname used for the propertyAlias element
    */
   public QName getPropertyAliasQName();
   
   /**
    * Getter for the partnerLinkType serializer
    */
   public ExtensionSerializer getPartnerLinkTypeSerializer();

   /**
    * Getter for the partnerLinkType deserializer
    */
   public ExtensionDeserializer getPartnerLinkTypeDeserializer();
   
   /**
    * Getter for the property serializer
    */
   public ExtensionSerializer getPropertySerializer();
   
   /**
    * Getter for the property deserializer
    */
   public ExtensionDeserializer getPropertyDeserializer();

   /**
    * Getter for the propertyAlias serializer
    */
   public ExtensionSerializer getPropertyAliasSerializer();
   
   /**
    * Getter for the propertyAlias deserializer
    */
   public ExtensionDeserializer getPropertyAliasDeserializer();
}
 
