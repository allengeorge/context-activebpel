//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/web/processview/AeProcessViewUtil.java,v 1.8 2007/03/06 15:48:5
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
package org.activebpel.rt.bpeladmin.war.web.processview;

import java.io.File;

import org.activebpel.rt.AeException;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.xml.AeXMLParserBase;
import org.w3c.dom.Document;

/**
 * Contains convienience methods to load a XML Document object.
 */
public class AeProcessViewUtil
{

   /**
    * Clones and returns the copy of the original Document,
    * @param aDocument original document.
    * @return cloned document.
    * @throws Exception
    */
   public static Document cloneDocument(Document aDocument) throws AeException
   {
      AeXMLParserBase parser = new AeXMLParserBase();
      parser.setNamespaceAware(false);
      parser.setValidating(false);
      Document doc = parser.createDocument();
      doc.appendChild( doc.importNode(aDocument.getDocumentElement(), true) );
      return doc;
   }

   /**
    * Creates and returns a Document given a handle to the xml file.
    * @param aXmlFile File to the xml document.
    * @return Document object.
    * @throws Exception
    */
   public static Document domFromFile(File aXmlFile)
      throws AeException
   {
      Document rDoc;
      AeXMLParserBase parser = new AeXMLParserBase();
      parser.setNamespaceAware(true);
      parser.setValidating(false);
      rDoc = parser.loadDocument(aXmlFile.getAbsolutePath(), null);
      return rDoc;
   }

   /**
    * Creates and returns a Document given the xml as a string.
    * @param aXmlSource valid xml document content.
    * @return Document object.
    * @throws Exception
    */
   public static Document domFromString(String aXmlSource) throws AeException
   {
      try
      {
         AeXMLParserBase parser = new AeXMLParserBase();
         parser.setNamespaceAware(true);
         parser.setValidating(false);
         Document dom = parser.loadDocumentFromString(aXmlSource, null);
         return dom;
      }
      catch(AeException e)
      {
         throw e;
      }
   }

   /**
    * Converts a Document object to a string.
    * @param aDocument BPEL dom
    * @return String version of the dom or null if unable to convert.
    */
   public static String stringFromDom(Document aDocument, boolean indent)
   {
      try
      {
         return AeXMLParserBase.documentToString(aDocument, indent);
      }
      catch(Throwable t)
      {
         // ignore
         return null;
      }
   }

   /**
    * Formats a string so that the first character is uppper case, followed by all lower
    * case characters.
    * @param aLabel
    * @return formated string
    */
   public static String formatLabel(String aLabel)
   {
      String rVal = AeUtil.getSafeString(aLabel);
      if (rVal.length() > 1)
      {
         rVal = rVal.substring(0,1).toUpperCase() + rVal.substring(1);
      }
      else if (rVal.length() == 1)
      {
         rVal = rVal.toUpperCase();
      }
      return rVal;
   }
}
