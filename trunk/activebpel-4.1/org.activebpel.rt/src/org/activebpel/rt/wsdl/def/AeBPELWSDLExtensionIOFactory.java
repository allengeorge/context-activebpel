//$Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/wsdl/def/AeBPELWSDLExtensionIOFactory.java,v 1.1 2007/08/13 17:46:3
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
 * Factory for creating reader/writer entries in the WSDL serializers 
 */
public class AeBPELWSDLExtensionIOFactory
{
   /** factory for BPEL4WS */
   private static IAeBPELWSDLExtensionIOFactory BPEL4WS = new AeBPEL4WSImpl();
   /** factory for WSBPEL */
   private static IAeBPELWSDLExtensionIOFactory WSBPEL = new AeWSBPELImpl();
   
   /**
    * private ctor to force factory usage 
    */
   private AeBPELWSDLExtensionIOFactory()
   {
   }
   
   /**
    * Getter for the factory by its namespace
    * @param aNamespace
    */
   public static IAeBPELWSDLExtensionIOFactory getFactory(String aNamespace)
   {
      return IAeBPELExtendedWSDLConst.WSBPEL_2_0_NAMESPACE_URI.equals(aNamespace)? WSBPEL : BPEL4WS;
   }
   
   /**
    * Base impl handles our classes and QNames
    */
   private static class AeBaseImpl implements IAeBPELWSDLExtensionIOFactory
   {
      private QName mPlinkTypeName;
      private QName mPropertyName;
      private QName mPropertyAliasName;
      private ExtensionSerializer mPLTSerializer;
      private ExtensionSerializer mPropertySerializer;
      private ExtensionSerializer mPropertyAliasSerializer;
      private ExtensionDeserializer mPLTDeserializer;
      private ExtensionDeserializer mPropertyDeserializer;
      private ExtensionDeserializer mPropertyAliasDeserializer;
      
      /**
       * @param aPartnerLinkTypeName
       * @param aPropertyName
       * @param aPropertyAliasName
       */
      protected AeBaseImpl(QName aPartnerLinkTypeName, QName aPropertyName, QName aPropertyAliasName)
      {
         mPlinkTypeName = aPartnerLinkTypeName;
         mPropertyName = aPropertyName;
         mPropertyAliasName = aPropertyAliasName;
      }

      /**
       * @see org.activebpel.rt.wsdl.def.IAeBPELWSDLExtensionIOFactory#getPartnerLinkTypeQName()
       */
      public QName getPartnerLinkTypeQName()
      {
         return mPlinkTypeName;
      }

      /**
       * @see org.activebpel.rt.wsdl.def.IAeBPELWSDLExtensionIOFactory#getPropertyAliasQName()
       */
      public QName getPropertyAliasQName()
      {
         return mPropertyAliasName;
      }

      /**
       * @see org.activebpel.rt.wsdl.def.IAeBPELWSDLExtensionIOFactory#getPropertyQName()
       */
      public QName getPropertyQName()
      {
         return mPropertyName;
      }

      /**
       * @see org.activebpel.rt.wsdl.def.IAeBPELWSDLExtensionIOFactory#getPartnerLinkTypeDeserializer()
       */
      public ExtensionDeserializer getPartnerLinkTypeDeserializer()
      {
         return mPLTDeserializer;
      }

      /**
       * @see org.activebpel.rt.wsdl.def.IAeBPELWSDLExtensionIOFactory#getPartnerLinkTypeSerializer()
       */
      public ExtensionSerializer getPartnerLinkTypeSerializer()
      {
         return mPLTSerializer;
      }

      /**
       * @see org.activebpel.rt.wsdl.def.IAeBPELWSDLExtensionIOFactory#getPropertyAliasDeserializer()
       */
      public ExtensionDeserializer getPropertyAliasDeserializer()
      {
         return mPropertyAliasDeserializer;
      }

      /**
       * @see org.activebpel.rt.wsdl.def.IAeBPELWSDLExtensionIOFactory#getPropertyAliasSerializer()
       */
      public ExtensionSerializer getPropertyAliasSerializer()
      {
         return mPropertyAliasSerializer;
      }

      /**
       * @see org.activebpel.rt.wsdl.def.IAeBPELWSDLExtensionIOFactory#getPropertyDeserializer()
       */
      public ExtensionDeserializer getPropertyDeserializer()
      {
         return mPropertyDeserializer;
      }

      /**
       * @see org.activebpel.rt.wsdl.def.IAeBPELWSDLExtensionIOFactory#getPropertySerializer()
       */
      public ExtensionSerializer getPropertySerializer()
      {
         return mPropertySerializer;
      }

      /**
       * Setter for the prop serializer
       * @param aSerializer
       */
      protected void setPropertySerializer(ExtensionSerializer aSerializer)
      {
         mPropertySerializer = aSerializer;
      }

      /**
       * Setter for the prop deserializer
       * @param aSerializer
       */
      protected void setPropertyDeserializer(ExtensionDeserializer aDeserializer)
      {
         mPropertyDeserializer = aDeserializer;
      }

      /**
       * Setter for the plt deserializer
       * @param aDeserializer
       */
      protected void setPartnerLinkTypeDeserializer(ExtensionDeserializer aDeserializer)
      {
         mPLTDeserializer = aDeserializer;
      }

      /**
       * Setter for the plt serializer
       * @param aSerializer
       */
      protected void setPartnerLinkTypeSerializer(ExtensionSerializer aSerializer)
      {
         mPLTSerializer = aSerializer;
      }

      /**
       * Setter for the propalias serializer
       * @param aSerializer
       */
      protected void setPropertyAliasSerializer(ExtensionSerializer aSerializer)
      {
         mPropertyAliasSerializer = aSerializer;
      }

      /**
       * Setter for the propalias deserializer
       * @param aSerializer
       */
      protected void setPropertyAliasDeserializer(ExtensionDeserializer aDeserializer)
      {
         mPropertyAliasDeserializer = aDeserializer;
      }
   }
   
   /**
    * WSBPEL impl 
    */
   private static class AeWSBPELImpl extends AeBaseImpl
   {
      public AeWSBPELImpl()
      {
         super(new QName(IAeBPELExtendedWSDLConst.WSBPEL_PARTNER_LINK_NAMESPACE, IAeBPELExtendedWSDLConst.PARTNER_LINK_TYPE_TAG),
               new QName(IAeBPELExtendedWSDLConst.PROPERTY_2_0_NAMESPACE, IAeBPELExtendedWSDLConst.PROPERTY_TAG),
               new QName(IAeBPELExtendedWSDLConst.PROPERTY_2_0_NAMESPACE, IAeBPELExtendedWSDLConst.PROPERTY_ALIAS_TAG)
         );
         AeWSBPELPartnerLinkTypeIO pltIO = new AeWSBPELPartnerLinkTypeIO();
         AeWSBPELPropertyAliasIO paIO = new AeWSBPELPropertyAliasIO();
         AeWSBPELPropertyIO pIO = new AeWSBPELPropertyIO();
         
         setPartnerLinkTypeDeserializer(pltIO);
         setPartnerLinkTypeSerializer(pltIO);
         setPropertyAliasSerializer(paIO);
         setPropertyAliasDeserializer(paIO);
         setPropertyDeserializer(pIO);
         setPropertySerializer(pIO);
      }
   }
   
   /**
    * BPEL4WS impl 
    */
   private static class AeBPEL4WSImpl extends AeBaseImpl
   {
      private AeBPEL4WSImpl()
      {
         super(new QName(IAeBPELExtendedWSDLConst.PARTNER_LINK_NAMESPACE, IAeBPELExtendedWSDLConst.PARTNER_LINK_TYPE_TAG),
               new QName(IAeBPELExtendedWSDLConst.PROPERTY_1_1_NAMESPACE, IAeBPELExtendedWSDLConst.PROPERTY_TAG),
               new QName(IAeBPELExtendedWSDLConst.BPWS_NAMESPACE_URI, IAeBPELExtendedWSDLConst.PROPERTY_ALIAS_TAG)
         );

         AePartnerLinkTypeIO pltIO = new AePartnerLinkTypeIO();
         AePropertyAliasIO paIO = new AePropertyAliasIO();
         AePropertyIO pIO = new AePropertyIO();
         
         setPartnerLinkTypeDeserializer(pltIO);
         setPartnerLinkTypeSerializer(pltIO);
         setPropertyAliasSerializer(paIO);
         setPropertyAliasDeserializer(paIO);
         setPropertyDeserializer(pIO);
         setPropertySerializer(pIO);
      }
   }
}
