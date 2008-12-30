//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/attachment/AePairDeserializer.java,v 1.1 2007/04/23 23:38:2
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
package org.activebpel.rt.bpel.server.engine.storage.attachment;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.AeMessages;
import org.jaxen.JaxenException;
import org.jaxen.XPath;
import org.jaxen.dom.DOMXPath;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Utility class to convert between a java.util.Map containing name/value pairs and an AeFastDocument
 * Restrictions on the map: key and value are assumed to be Strings
 */
public class AePairDeserializer
{

   public static Map deserialize(Document aDocument) throws AeBusinessProcessException
   {
      Map headers = new HashMap();
      
      Element root = aDocument.getDocumentElement();
      
      List pairElements = selectNodes(root, "./" + AePairSerializer.PAIR_ELEMENT); //$NON-NLS-1$
      
      for (Iterator pairIter = pairElements.iterator(); pairIter.hasNext();)
      {
         Element pairElement = (Element) pairIter.next();
         String name = pairElement.getAttribute(AePairSerializer.NAME_ATTRIBUTE);
         headers.put(name, pairElement.getNodeValue());
      }
      return headers;
   }

   /**
    * Selects nodes by XPath.
    *
    * @param aNode The node to select from.
    * @param aPath The XPath expression.
    * @return List The list of matching nodes.
    * @throws AeBusinessProcessException
    */
   protected static List selectNodes(Node aNode, String aPath) throws AeBusinessProcessException
   {
      try
      {
         XPath xpath = new DOMXPath(aPath);
         return xpath.selectNodes(aNode);
      }
      catch (JaxenException e)
      {
         throw new AeBusinessProcessException(AeMessages.getString("AePairDeserializer.ERROR_XPATH_QUERY") + aPath, e); //$NON-NLS-1$
      }
   }
}
