//$Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/schemas/AeStandardSchemas.java,v 1.3 2005/02/08 15:27:1
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
package org.activebpel.rt.schemas;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;

import org.activebpel.rt.AeException;
import org.activebpel.rt.AeMessages;
import org.activebpel.rt.xml.AeXMLParserBase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * This class provides access to a static list of "well known" schemas, accessed by the
 * targetNamespace of the schema.  Given a schema namespace, this class will give back an
 * InputStream to the content of the schema, or null if it is not a "well known" one.
 */
public class AeStandardSchemas
{
   /** The static map of "well known" schemas (namespace -> resource name). */
   static private HashMap sSchemaMap = new HashMap();

   /*
    * Static initializer for loading the contents of the above schema map.
    */
   static 
   {
      URL schemasURL = AeStandardSchemas.class.getResource("standardSchemas.xml"); //$NON-NLS-1$
      if (schemasURL != null)
      {
         try
         {
            AeXMLParserBase parser = new AeXMLParserBase();
            parser.setValidating(false);
            parser.setNamespaceAware(false);
            Document schemasDoc = parser.loadDocument(schemasURL.openStream(), null);
            NodeList nl = schemasDoc.getDocumentElement().getElementsByTagName("schemaRef"); //$NON-NLS-1$
            if (nl != null)
            {
               for (int i = 0; i < nl.getLength(); i++)
               {
                  Element schemaRef = (Element) nl.item(i);
                  String ns = schemaRef.getAttribute("namespace"); //$NON-NLS-1$
                  String loc = schemaRef.getAttribute("location"); //$NON-NLS-1$
                  sSchemaMap.put(ns, loc);
               }
            }
         }
         catch (Throwable t)
         {
            AeException.logError(t, AeMessages.getString("AeStandardSchemas.ERROR_0")); //$NON-NLS-1$
         }
      }
   }

   /**
    * This method returns the input stream for a standard schema given the schema namespace.
    * 
    * @param aNamespace
    */
   public static InputStream getStandardSchema(String aNamespace)
   {
      try
      {
         String location = (String) sSchemaMap.get(aNamespace);
         if (location != null)
         {
            return AeStandardSchemas.class.getResourceAsStream(location);
         }
      }
      catch (Exception e)
      {
         AeException.logError(e, AeMessages.getString("AeStandardSchemas.ERROR_1") + aNamespace); //$NON-NLS-1$
      }
      return null;
   }
}
