//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis/src/org/activebpel/rt/axis/AeWsdlTrimmer.java,v 1.11 2007/03/20 20:58:4
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
package org.activebpel.rt.axis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.wsdl.Definition;
import javax.wsdl.Fault;
import javax.wsdl.Import;
import javax.wsdl.Message;
import javax.wsdl.Operation;
import javax.wsdl.PortType;
import javax.wsdl.Types;
import javax.wsdl.extensions.ExtensibilityElement;
import javax.wsdl.extensions.UnknownExtensibilityElement;
import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.server.engine.AeEngineFactory;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.wsdl.def.AeBPELExtendedWSDLDef;
import org.activebpel.rt.wsdl.def.castor.AeSchemaParserUtil;
import org.apache.axis.AxisFault;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Utility class that removes all WSDL elements which do not pertain to this service deployment.
 */
public class AeWsdlTrimmer
{
   /** Replace colon's in location url with this string.  Note not final since it can be changed via configuration. */
   private static String sCatalogColonReplacement = ":"; //$NON-NLS-1$
   
   // TODO (MF) should this be hard coded?
   private static final String ACTIVE_BPEL = "active-bpel"; //$NON-NLS-1$

   /**
    * Removes all elements which do not pertain to this service deployment.
    * This includes port types and messages which are not part of this deployment.
    * Also remove bindings and services which may have been specified, since we
    * should be providing this in the concrete class implementation. Finally remove
    * any BPEL specific WSDL declarations.
    *
    * @param aDef the WSDL definition
    * @param aPortType the Port Type
    * @param aAllowedMethods list of methods to keep in generated WSDL
    * @param aTransportUrl
    */
   public static void trimWSDLDefinition( AeBPELExtendedWSDLDef aDef, QName aPortType, List aAllowedMethods,
         String aTransportUrl) throws AxisFault
   {
      Definition def = aDef.getWSDLDef();
      PortType portType = findMatchingPortType( aPortType, def );
      removeAllPortsExcept( aPortType, def );


      // Remove any operations we are not exposing
      ArrayList keepMsgs  = new ArrayList();
      ArrayList removeOps = new ArrayList();
      for (Iterator opIter=portType.getOperations().iterator(); opIter.hasNext();)
      {
         Operation operation = (Operation)opIter.next();
         if (! aAllowedMethods.contains( operation.getName() ) )
         {
            removeOps.add(operation);
         }
         else
         {
            if (operation.getInput() != null)
               keepMsgs.add(operation.getInput().getMessage().getQName());

            if (operation.getOutput() != null)
               keepMsgs.add(operation.getOutput().getMessage().getQName());

            for (Iterator faultIter=operation.getFaults().values().iterator(); faultIter.hasNext();)
               keepMsgs.add(((Fault)faultIter.next()).getMessage().getQName());
         }
      }

      // Do the actual removal of operations after iteration has completed
      for (Iterator iter=removeOps.iterator(); iter.hasNext();)
         portType.getOperations().remove(iter.next());

      // Create list of messages we will be removing
      ArrayList removeMsgs = new ArrayList();
      for (Iterator iter=def.getMessages().values().iterator(); iter.hasNext();)
      {
         Message msg = (Message)iter.next();
         if (! keepMsgs.contains(msg.getQName()))
            removeMsgs.add(msg.getQName());
      }

      // Do the actual removal of messages after iteration has completed
      for (Iterator iter=removeMsgs.iterator(); iter.hasNext();)
         def.removeMessage((QName)iter.next());

      removeExtensibilityElements( def );

      // Modify import reference locations to refer to wsdl locator servlet
      fixupImportReferences( aTransportUrl, def, aDef.getLocationHint() );
   }

   /**
    * Find our matching port type based on the qname. Check the def and its imports.
    * @param aPortTypeQName
    * @param aDef
    */
   protected static PortType findMatchingPortType(QName aPortTypeQName, Definition aDef) throws AxisFault
   {
      PortType portType = null;

      for (Iterator iter=aDef.getPortTypes().keySet().iterator(); iter.hasNext();)
      {
         QName tmpPort = (QName)iter.next();
         if( aPortTypeQName.getLocalPart().equals( tmpPort.getLocalPart() ) )
            portType = (PortType)aDef.getPortTypes().get(tmpPort);
      }

      if (portType == null)
      {
         List importList = aDef.getImports(aPortTypeQName.getNamespaceURI());
         if (importList != null)
         {
            for (Iterator iter=importList.iterator(); iter.hasNext() && portType ==null;)
            {
               Import importDef = (Import)iter.next();
               portType = importDef.getDefinition().getPortType(aPortTypeQName);
            }
         }
      }

      // Make sure we found the port type we were looking for
      if (portType == null)
      {
         throw new AxisFault(AeMessages.getString("AeHandler.ERROR_5") + aPortTypeQName); //$NON-NLS-1$
      }

      return portType;
   }

   /**
    * Remove all other port types except the one we are using.
    * @param aPortType
    * @param aDef
    */
   protected static void removeAllPortsExcept( QName aPortType, Definition aDef )
   {
      List removes = new ArrayList();
      for (Iterator iter=aDef.getPortTypes().keySet().iterator(); iter.hasNext();)
      {
         QName tmpPort = (QName)iter.next();
         if( !aPortType.getLocalPart().equals( tmpPort.getLocalPart() ) )
         {
            removes.add( tmpPort );
         }
      }

      for( Iterator iter = removes.iterator(); iter.hasNext(); )
      {
         aDef.removePortType( (QName)iter.next() );
      }
   }

   /**
    * Remove all extensibility elements from the definition
    * @param aDefinition
    */
   protected static void removeExtensibilityElements( Definition aDefinition )
   {
      for (int i=aDefinition.getExtensibilityElements().size() - 1; i >= 0; --i)
      {
         aDefinition.getExtensibilityElements().remove(i);
      }
   }

   /**
    * Used to modify the import location references so that they may be resolved
    * by the catalog servlet.
    * @param aTransportUrl
    * @param aDef the WSDL definition we are processing
    * @param aParentLocation
    */
   public static void fixupImportReferences(String aTransportUrl, Definition aDef, String aParentLocation)
   {
      fixUpWsdlImportReferences( aTransportUrl, aDef, aParentLocation );
      fixUpSchemaImportReferences( aTransportUrl, aDef, aParentLocation );
   }

   /**
    * Fix up wsdl import references so that they may be resolved
    * by the catalog servlet.
    * @param aTransportUrl
    * @param aDef
    * @param aParentLocation
    */
   protected static void fixUpWsdlImportReferences( String aTransportUrl, Definition aDef, String aParentLocation )
   {
      AeImportUrl importUrl = new AeImportUrl( aTransportUrl, aParentLocation );
      for (Iterator iter=aDef.getImports().keySet().iterator(); iter.hasNext();)
      {
         List importList = aDef.getImports(iter.next().toString());
         for (Iterator impIter=importList.iterator(); impIter.hasNext();)
         {
            Import wsdlImport = (Import)impIter.next();
            String importLocation = wsdlImport.getLocationURI();
            wsdlImport.setLocationURI(importUrl.getImportUrl(importLocation));
         }
      }
   }

   /**
    * Fix up schema import references so that they may be resolved
    * by the catalog servlet.
    * @param aTransportUrl
    * @param aDef
    * @param aParentLocation
    */
   protected static void fixUpSchemaImportReferences( String aTransportUrl,
                                       Definition aDef, String aParentLocation )
   {
      AeImportUrl importUrl = new AeImportUrl( aTransportUrl, aParentLocation );

      Types schemaTypes = aDef.getTypes();
      if( schemaTypes != null && schemaTypes.getExtensibilityElements() != null )
      {
         for( Iterator iter = schemaTypes.getExtensibilityElements().iterator(); iter.hasNext(); )
         {
            ExtensibilityElement el = (ExtensibilityElement)iter.next();

            if( AeSchemaParserUtil.isSchemaQName( el.getElementType() ) )
            {
               Element rawSchemaElement = ((UnknownExtensibilityElement)el).getElement();
               NodeList imports = AeSchemaParserUtil.getSchemaImportNodeList(rawSchemaElement);

               for( int i = 0; i < imports.getLength(); i++ )
               {
                  Element impElement = ((Element)imports.item(i));
                  String location = impElement.getAttribute( AeSchemaParserUtil.SCHEMA_LOCATION );
                  if (AeUtil.notNullOrEmpty(location))
                     impElement.setAttribute(AeSchemaParserUtil.SCHEMA_LOCATION, importUrl.getImportUrl(location));
               }
            }
         }
      }
   }

   /**
    * @param aCatalogColonReplacement The CatalogColonReplacement to set.
    */
   public static void setCatalogColonReplacement(String aCatalogColonReplacement)
   {
      sCatalogColonReplacement = aCatalogColonReplacement;
   }

   /**
    * @return Returns the CatalogColonReplacement.
    */
   public static String getCatalogColonReplacement()
   {
      return sCatalogColonReplacement;
   }

   /**
    * Utility class for formatting the "fixed up" import urls for wsdl and schemas.
    */
   protected static class AeImportUrl
   {
      /** the import url template */
      private String mImportUrl;
      /** the parent location string */
      private String mParentLocation;

      /**
       * Constructor.
       * @param aTransportUrl
       * @param aParentLocation
       */
      public AeImportUrl( String aTransportUrl, String aParentLocation )
      {
         int index = aTransportUrl.indexOf(ACTIVE_BPEL);
         mImportUrl = aTransportUrl.substring(0, index+ACTIVE_BPEL.length()) + "/catalog/"; //$NON-NLS-1$
         mParentLocation = aParentLocation;
      }

      /**
       * Format the import URL for the import location.
       * @param aImportLocation
       */
      public String getImportUrl(String aImportLocation)
      {
         String loc = resolveImportLocation(aImportLocation);

         // If no engine available or no catalog entry return default import URL
         if (AeEngineFactory.getEngineAdministration() == null || AeEngineFactory.getEngineAdministration().getCatalogAdmin().getCatalogInputSource(loc) == null)
         {
            return loc;
         }
         else
         {
            return mImportUrl + loc.replaceAll(" ", "%20").replaceAll(":", getCatalogColonReplacement()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
         }
      }

      /**
       * Resolve the import location relative to the parent if necessary.
       * @param aImportLocation
       */
      protected String resolveImportLocation( String aImportLocation )
      {
         String importLocation = aImportLocation;
         if (!AeUtil.isNullOrEmpty(mParentLocation))
         {
            importLocation = AeUtil.resolveImport(mParentLocation, importLocation);
         }
         return importLocation;
      }
   }
}
