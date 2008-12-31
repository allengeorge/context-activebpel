//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.tamino/src/org/activebpel/rt/bpel/server/engine/storage/tamino/installer/AeTaminoSchema.java,v 1.3 2006/02/24 16:44:3
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
package org.activebpel.rt.bpel.server.engine.storage.tamino.installer;

import java.io.File;
import java.io.Reader;
import java.io.StringReader;

import org.activebpel.rt.xml.AeXMLParserBase;
import org.jaxen.JaxenException;
import org.jaxen.NamespaceContext;
import org.jaxen.dom.DOMXPath;
import org.w3c.dom.Element;

/**
 * A class that reads in a schema file and provides access to interesting information about it.
 */
public class AeTaminoSchema
{
   private static final String SCHEMA_XPATH = "xs:annotation/xs:appinfo/tsd:schemaInfo"; //$NON-NLS-1$
   private static final String COLLECTION_XPATH = "xs:annotation/xs:appinfo/tsd:schemaInfo/tsd:collection"; //$NON-NLS-1$

   /**
    * A namespace context to use for the above XPaths.
    */
   private static NamespaceContext sNamespaceContext = new NamespaceContext()
      {
         /**
          * @see org.jaxen.NamespaceContext#translateNamespacePrefixToUri(java.lang.String)
          */
         public String translateNamespacePrefixToUri(String aPrefix)
         {
            if ("tsd".equals(aPrefix)) //$NON-NLS-1$
            {
               return "http://namespaces.softwareag.com/tamino/TaminoSchemaDefinition"; //$NON-NLS-1$
            }
            else if ("xs".equals(aPrefix)) //$NON-NLS-1$
            {
               return "http://www.w3.org/2001/XMLSchema"; //$NON-NLS-1$
            }

            return null;
         }
      };

   /** The Schema file parsed into a DOM. */
   private Element mSchemaDom;

   /**
    * Constructs a tamino schema given the .tsd file.
    * 
    * @param aSchemaFile
    */
   public AeTaminoSchema(File aSchemaFile) throws Exception
   {
      setSchemaDom(readSchemaFile(aSchemaFile));
   }
   
   /**
    * Constructs a tamino schema given the schema Element/DOM.
    * 
    * @param aSchemaElem
    */
   public AeTaminoSchema(Element aSchemaElem)
   {
      setSchemaDom(aSchemaElem);
   }

   /**
    * Reads the schema file and parses it to determine the schema name and collection name.
    * 
    * @param aSchemaFile
    * @throws Exception
    */
   protected Element readSchemaFile(File aSchemaFile) throws Exception
   {
      AeXMLParserBase parser = new AeXMLParserBase();
      parser.setNamespaceAware(true);
      parser.setValidating(false);
      return parser.loadDocument(aSchemaFile.getAbsolutePath(), null).getDocumentElement();
   }

   /**
    * @return Returns the collectionName.
    */
   public String getCollectionName()
   {
      try
      {
         DOMXPath xpath = new DOMXPath(COLLECTION_XPATH);
         xpath.setNamespaceContext(sNamespaceContext);
         Element elem = (Element) xpath.selectSingleNode(getSchemaDom());
         return elem.getAttribute("name"); //$NON-NLS-1$
      }
      catch (JaxenException ex)
      {
         throw new RuntimeException(ex);
      }
   }

   /**
    * @param aCollectionName The collectionName to set.
    */
   public void setCollectionName(String aCollectionName)
   {
      try
      {
         DOMXPath xpath = new DOMXPath(COLLECTION_XPATH);
         xpath.setNamespaceContext(sNamespaceContext);
         Element elem = (Element) xpath.selectSingleNode(getSchemaDom());
         elem.setAttribute("name", aCollectionName); //$NON-NLS-1$
      }
      catch (JaxenException ex)
      {
         throw new RuntimeException(ex);
      }
   }

   /**
    * @return Returns the schemaName.
    */
   public String getSchemaName()
   {
      try
      {
         DOMXPath xpath = new DOMXPath(SCHEMA_XPATH);
         xpath.setNamespaceContext(sNamespaceContext);
         Element elem = (Element) xpath.selectSingleNode(getSchemaDom());
         return elem.getAttribute("name"); //$NON-NLS-1$
      }
      catch (JaxenException ex)
      {
         throw new RuntimeException(ex);
      }
   }

   /**
    * @param aSchemaName The schemaName to set.
    */
   public void setSchemaName(String aSchemaName)
   {
      try
      {
         DOMXPath xpath = new DOMXPath(SCHEMA_XPATH);
         xpath.setNamespaceContext(sNamespaceContext);
         Element elem = (Element) xpath.selectSingleNode(getSchemaDom());
         elem.setAttribute("name", aSchemaName); //$NON-NLS-1$
      }
      catch (JaxenException ex)
      {
         throw new RuntimeException(ex);
      }
   }

   /**
    * Gets a Reader over the serialized xml of the schema.
    */
   public Reader getReader()
   {
      return new StringReader(AeXMLParserBase.documentToString(getSchemaDom()));
   }

   /**
    * Gets the schema dom.
    */
   protected Element getSchemaDom()
   {
      return mSchemaDom;
   }

   /**
    * 
    * @param aSchemaDom
    */
   protected void setSchemaDom(Element aSchemaDom)
   {
      mSchemaDom = aSchemaDom;
   }
}
