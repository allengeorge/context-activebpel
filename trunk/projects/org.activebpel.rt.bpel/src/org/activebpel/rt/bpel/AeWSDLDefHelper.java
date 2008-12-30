// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/AeWSDLDefHelper.java,v 1.26 2007/06/19 14:06:1
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

import org.activebpel.rt.AeException;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.wsdl.def.AeBPELExtendedWSDLDef;
import org.activebpel.rt.wsdl.def.IAeProperty;
import org.activebpel.rt.wsdl.def.IAePropertyAlias;

import javax.wsdl.*;
import javax.xml.namespace.QName;
import java.util.Iterator;
import java.util.Map;

/**
 * Utility helper class to do various WSDL definition lookups.
 */
public class AeWSDLDefHelper
{
   /**
    * unreachable ctor 
    */
   private AeWSDLDefHelper()
   {
   }
   
   /**
    * Returns the WSDL definition for the given service or null if none
    * is found.
    * @param aProvider The IAeWSDLProvider instance to use for lookup.
    * @param aService The QName of the PLT to find.
    * @return AeBPELExtendedWSDLDef
    */
   public static AeBPELExtendedWSDLDef getWSDLDefinitionForService( IAeWSDLProvider aProvider, QName aService )
   {
      for ( Iterator iter = aProvider.getWSDLIterator( aService.getNamespaceURI() ) ; iter.hasNext() ; )
      {
         AeBPELExtendedWSDLDef def = aProvider.dereferenceIteration( iter.next());
         Map services = def.getServices();
         if( services.get(aService) != null )
         {
            return def;
         }
      }
      return null;
   }
   
   /**
    * Returns the WSDL definition containing the given partner link type or null 
    * if not found.
    * @param aProvider The IAeContextWSDLProvider instance to use for lookup.
    * @param aPartnerLinkType The QName of the PLT to find.
    * @return AeBPELExtendedWSDLDef
    */
   public static AeBPELExtendedWSDLDef getWSDLDefinitionForPLT(IAeContextWSDLProvider aProvider, QName aPartnerLinkType)
   {
      if (aProvider != null && aPartnerLinkType != null )
      {
         for ( Iterator iter = aProvider.getWSDLIterator( aPartnerLinkType.getNamespaceURI() ) ; iter.hasNext() ; )
         {
            AeBPELExtendedWSDLDef def = aProvider.dereferenceIteration( iter.next());
            // check that we have a def with the right namespace or we may end up getting the def from imports by accident
            if ( AeUtil.compareObjects(def.getTargetNamespace(), aPartnerLinkType.getNamespaceURI()) )
               if ( def.getPartnerLinkType( aPartnerLinkType.getLocalPart()) != null )
                  return def ;
         }
      }
      
      return null;
   }
   
   /**
    * Returns the WSDL definition containing the given portType or null if
    * not found.
    * @param aProvider The IAeContextWSDLProvider instance to use for lookup.
    * @param aPortType The QName of the portType to find.
    * @return AeBPELExtendedWSDLDef
    */
   public static AeBPELExtendedWSDLDef getWSDLDefinitionForPortType(IAeContextWSDLProvider aProvider, QName aPortType)
   {
      if (aPortType != null && aProvider != null )
      {
         for ( Iterator iter = aProvider.getWSDLIterator( aPortType.getNamespaceURI() ) ; iter.hasNext() ; )
         {
            AeBPELExtendedWSDLDef def = aProvider.dereferenceIteration( iter.next());
            if ( def.getPortType( aPortType ) != null )
               return def ;
         }
      }

      return null;
   }

   /**
    * Returns the WSDL definition containing the given message or null if not 
    * found.
    * @param aProvider The IAeContextWSDLProvider instance to use for lookup.
    * @param aMsgName the message we are interested in.
    */
   public static AeBPELExtendedWSDLDef getWSDLDefinitionForMsg(IAeContextWSDLProvider aProvider, QName aMsgName)
   {
      if(aMsgName == null)
         return null;
         
      for ( Iterator iter = aProvider.getWSDLIterator( aMsgName.getNamespaceURI() ) ; iter.hasNext() ; )
      {
         AeBPELExtendedWSDLDef def = aProvider.dereferenceIteration( iter.next());
         // check that we have a def with the right namespace or we may end up getting the def from imports by accident
         if ( AeUtil.compareObjects(def.getTargetNamespace(), aMsgName.getNamespaceURI()) )
            if( def.definesMessage( aMsgName ) )
               return def ;
      }

      return null;
   }

   /**
    * Returns the WSDL definition containing the given element or null if not 
    * found.
    * @param aProvider The IAeContextWSDLProvider instance to use for lookup.
    * @param aElementName the element we are interested in.
    */
   public static AeBPELExtendedWSDLDef getWSDLDefinitionForElement(IAeContextWSDLProvider aProvider, QName aElementName)
   {
      if (aElementName != null)
      {
         for ( Iterator iter = aProvider.getWSDLIterator() ; iter.hasNext() ; )
         {
            AeBPELExtendedWSDLDef def = aProvider.dereferenceIteration( iter.next());
            if (def != null && def.findElement( aElementName ) != null )
               return def ;
         }
      }

      return null;
   }

   /**
    * Returns the WSDL definition containing the given Type or null if not 
    * found.
    * @param aProvider The IAeContextWSDLProvider instance to use for lookup.
    * @param aTypeName the Type we are interested in.
    */
   public static AeBPELExtendedWSDLDef getWSDLDefinitionForType(IAeContextWSDLProvider aProvider, QName aTypeName)
   {
      for ( Iterator iter = aProvider.getWSDLIterator() ; iter.hasNext() ; )
      {
         AeBPELExtendedWSDLDef def = aProvider.dereferenceIteration( iter.next());
         try
         {
            if (def != null && def.findType( aTypeName ) != null )
               return def ;
         }
         catch (AeException e)
         {
            // ignore error as if we couldn't find the Type
         }
      }

      return null;
   }

   /**
    * Returns the WSDL definition containing the given property or null if not 
    * found.
    * @param aProvider The IAeContextWSDLProvider instance to use for lookup.
    * @param aPropName the property we are interested in.
    */
   public static AeBPELExtendedWSDLDef getWSDLDefinitionForProp(IAeContextWSDLProvider aProvider, QName aPropName)
   {
      for ( Iterator iter = aProvider.getWSDLIterator( aPropName.getNamespaceURI() ) ; iter.hasNext() ; )
      {
         AeBPELExtendedWSDLDef def = aProvider.dereferenceIteration( iter.next());
         for ( Iterator iterProps = def.getPropExtElements().iterator() ; iterProps.hasNext() ; )
         {
            IAeProperty prop = (IAeProperty)iterProps.next(); 
            if ( prop.getQName().equals( aPropName ) )
               return def ;
         }
      }

      return null;
   }

   /**
    * Get the WSDL PropertyAlias object for a given WSDL Message type and 
    * Property name.
    *   
    * @param aProvider The IAeContextWSDLProvider instance to use for lookup.
    * @param aTypeName the QName of the underlying variable type - either a 
    *                  message, element, or complex type name.
    * @param aType indicates which type the above QName is for.
    * @param aPropName the name of the WSDL Property we are searching for
    * 
    * @return PropertyAlias object or null if not found.
    */
   public static IAePropertyAlias getPropertyAlias(IAeContextWSDLProvider aProvider,  
                                                     QName aTypeName, int aType, QName aPropName)
   {
      for ( Iterator iterW = aProvider.getWSDLIterator() ; iterW.hasNext() ; )
      {
         AeBPELExtendedWSDLDef def = aProvider.dereferenceIteration( iterW.next());
         for ( Iterator iter = def.getPropAliasExtElements().iterator() ; iter.hasNext() ; )
         {
            IAePropertyAlias alias = (IAePropertyAlias)iter.next();
            if ( alias.getType() == aType && aTypeName.equals( alias.getQName() ) && 
                 aPropName.equals( alias.getPropertyName()))
            {
               return alias ;
            }
         }
      }

      return null;
   }

   /**
    * Gets an <code>IAeProperty</code> given the property name and a WSDL 
    * provider.
    * 
    * @param aWSDLProvider
    * @param aPropertyName
    * @return The <code>IAeProperty</code> with the given name.
    */
   public static IAeProperty getProperty(IAeContextWSDLProvider aWSDLProvider, QName aPropertyName)
   {
      IAeProperty prop = null;
      AeBPELExtendedWSDLDef def = AeWSDLDefHelper.getWSDLDefinitionForProp(aWSDLProvider, aPropertyName);
      if (def != null)
      {
         for (Iterator iter = def.getPropExtElements().iterator(); iter.hasNext() && prop == null;)
         {
            IAeProperty property = (IAeProperty) iter.next();
            if (property.getQName().equals(aPropertyName))
            {
               prop = property;
            }
         }
      }

      return prop;
   }

   /**
    * Returns the WSDL {@link Operation} for the given port type and operation
    * name.
    *
    * Note: This method does not support overloaded operations.
    * It will match on the first operation found with the given name.
    * 
    * @param aProvider
    * @param aPortType
    * @param aOperation
    */
   public static Operation getOperation(IAeContextWSDLProvider aProvider, QName aPortType, String aOperation)
   {
      Operation result = null;

      if ((aProvider != null) && (aPortType != null) && AeUtil.notNullOrEmpty(aOperation))
      {
         AeBPELExtendedWSDLDef wsdlDef = AeWSDLDefHelper.getWSDLDefinitionForPortType(aProvider, aPortType);
         if (wsdlDef != null)
         {
            PortType portType = wsdlDef.getPortType(aPortType);
            if (portType != null)
            {
               result = portType.getOperation(aOperation, null, null);
            }
         }
      }

      return result;
   }

   /**
    * Returns the WSDL {@link Message} for the given port type, operation name,
    * and fault.
    *
    * Note: This method does not support overloaded operations.
    * It will match on the first operation found with the given name.
    * 
    * @param aProvider
    * @param aPortType
    * @param aOperation
    * @param aFaultName
    */
   public static Message getFaultMessage(IAeContextWSDLProvider aProvider, QName aPortType, String aOperation, QName aFaultName)
   {
      Message result = null;

      Operation operation = getOperation(aProvider, aPortType, aOperation);
      if ((operation != null) && (aFaultName != null))
      {
         Fault fault = operation.getFault(aFaultName.getLocalPart());
         if (fault != null)
         {
            result = fault.getMessage();
         }
      }

      return result;
   }

   /**
    * Returns the input WSDL {@link Message} for the given port type and
    * operation name. 
    * 
    * Note: This method does not support overloaded operations.
    * It will match on the first operation found with the given name.
    *
    * @param aProvider
    * @param aPortType
    * @param aOperation
    */
   public static Message getInputMessage(IAeContextWSDLProvider aProvider, QName aPortType, String aOperation)
   {
      Message result = null;

      Operation operation = getOperation(aProvider, aPortType, aOperation);
      if (operation != null)
      {
         Input input = operation.getInput();
         if (input != null)
         {
            result = input.getMessage();
         }
      }

      return result;
   }

   /**
    * Returns the output WSDL {@link Message} for the given port type and
    * operation name.
    *
    * Note: This method does not support overloaded operations.
    * It will match on the first operation found with the given name.
    * 
    * @param aProvider
    * @param aPortType
    * @param aOperation
    */
   public static Message getOutputMessage(IAeContextWSDLProvider aProvider, QName aPortType, String aOperation)
   {
      Message result = null;

      Operation operation = getOperation(aProvider, aPortType, aOperation);
      if (operation != null)
      {
         Output output = operation.getOutput();
         if (output != null)
         {
            result = output.getMessage();
         }
      }

      return result;
   }
      
   /**
    * Gets the substitution group level for specified group head and group 
    * member. For the memeber element donesnt belong the gorup, level = -1, 
    * If two elements belong to a gorup but in the same level, level = 0, If the 
    * member element is in the head element group, level = n, where n = 1, 2, 
    * 3...
    * @param aProvider
    * @param aHeadElementName
    * @param aMemberElementName
    * @return substitution group level
    * @throws Exception
    */
   public static int getSubstitutionGroupLevel(IAeContextWSDLProvider aProvider, QName aHeadElementName, QName aMemberElementName) //throws AeException
   {
      if( aHeadElementName == null | aMemberElementName == null)
         return -1;
      
      AeBPELExtendedWSDLDef def = getWSDLDefinitionForElement(aProvider, aMemberElementName);
      if (def != null)
      {
         return def.getSubstitutionGroupLevel(aHeadElementName, aMemberElementName);
      }
      return -1;
   }   
}
