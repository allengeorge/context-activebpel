// $Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/util/AeXPathUtil.java,v 1.2 2007/08/17 19:58:5
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
package org.activebpel.rt.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.activebpel.rt.AeException;
import org.activebpel.rt.xml.schema.AeSchemaDateTime;
import org.activebpel.rt.xml.schema.AeSchemaTypeParseException;
import org.jaxen.JaxenException;
import org.jaxen.XPath;
import org.jaxen.dom.DOMXPath;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Convenience methods for using xpath.
 */
public class AeXPathUtil
{
   /**
    * Select nodes by XPath.
    *
    * @param aNode The node to select from.
    * @param aPath The XPath expression.
    * @return List The list of matching nodes.
    * @throws AeException
    */
   public static List selectNodes(Node aNode, String aPath) throws AeException
   {
      return selectNodes(aNode, aPath, null);
   }

   /**
    * Select nodes by XPath.
    * @param aNode The node to select from.
    * @param aPath The XPath expression.
    * @param aNsPrefixUriMap optional namespace prefix:uri map.
    * @return List The list of matching nodes.
    * @throws AeException
    */
   public static List selectNodes(Node aNode, String aPath, Map aNsPrefixUriMap) throws AeException
   {
      try
      {
         XPath xpath = new DOMXPath(aPath);
         AeXPathUtil.setNameSpacePrefixUris(xpath, aNsPrefixUriMap);
         return xpath.selectNodes(aNode);
      }
      catch (JaxenException e)
      {
         AeException aex = new AeException(e);
         throw aex;
      }
   }

   /**
    * Selects single node by XPath.
    *
    * @param aNode The node to select from.
    * @param aPath The XPath expression.
    * @return List The list of matching nodes.
    * @throws AeException
    */
   public static Node selectSingleNode(Node aNode, String aPath) throws AeException
   {
      return selectSingleNode(aNode, aPath, null);
   }

   /**
    * Selects single node by XPath.
    *
    * @param aNode The node to select from.
    * @param aPath The XPath expression.
    * @param aNsPrefixUriMap optional namespace prefix:uri map.
    * @return List The list of matching nodes.
    * @throws AeException
    */
   public static Node selectSingleNode(Node aNode, String aPath, Map aNsPrefixUriMap) throws AeException
   {
      return (Node) selectSingleObject(aNode, aPath, aNsPrefixUriMap);
   }

   /**
    * Selects single object by XPath.
    *
    * @param aNode The node to select from.
    * @param aPath The XPath expression.
    * @param aNsPrefixUriMap optional namespace prefix:uri map.
    * @return List The list of matching nodes.
    * @throws AeException
    */
   public static Object selectSingleObject(Node aNode, String aPath, Map aNsPrefixUriMap) throws AeException
   {
      try
      {
         XPath xpath = new DOMXPath(aPath);
         AeXPathUtil.setNameSpacePrefixUris(xpath, aNsPrefixUriMap);
         return xpath.selectSingleNode(aNode);
      }
      catch (JaxenException e)
      {
         AeException aex = new AeException(e);
         throw aex;
      }
   }

   /**
    * Selects an int value
    * @param aNode
    * @param aPath
    * @param aNsPrefixMap
    * @throws NumberFormatException if value isn't a valid int
    */
   public static int selectInt(Node aNode, String aPath, Map aNsPrefixMap) throws NumberFormatException
   {
      String value = selectText(aNode, aPath, aNsPrefixMap);
      return Integer.parseInt(value);
   }

   /**
    * Selects a long value
    * @param aNode
    * @param aPath
    * @param aNsPrefixMap
    * @throws NumberFormatException if value isn't a valid long
    */
   public static long selectLong(Node aNode, String aPath, Map aNsPrefixMap) throws NumberFormatException
   {
      String value = selectText(aNode, aPath, aNsPrefixMap);
      return Long.parseLong(value);
   }

   /**
    * Selects a boolean value.
    *
    * @param aNode
    * @param aPath
    * @param aNsPrefixMap
    * @return true if value found and Boolean.valueOf(value) is true, or false if null
    */
   public static boolean selectBoolean(Node aNode, String aPath, Map aNsPrefixMap)
   {
      String value = selectText(aNode, aPath, aNsPrefixMap);
      return Boolean.valueOf(value).booleanValue();
   }

   /**
    * Selects an encoded QName value.
    *
    * @param aNode
    * @param aPath
    * @param aNsPrefixMap
    * @throws AeException
    */
   public static QName selectQName(Node aNode, String aPath, Map aNsPrefixMap) throws AeException
   {
      Element elem = (Element) selectSingleNode(aNode, aPath, aNsPrefixMap);
      return AeXmlUtil.getQName(elem);
   }

   /**
    * Selects a long value
    * @param aNode
    * @param aPath
    * @param aNsPrefixMap
    * @throws AeSchemaTypeParseException if value isn't a valid xs:datetime
    * @return AeSchemaDateTime if value is found or null if none found
    */
   public static AeSchemaDateTime selectDateTime(Node aNode, String aPath, Map aNsPrefixMap) throws AeSchemaTypeParseException
   {
      String value = selectText(aNode, aPath, aNsPrefixMap);
      if (AeUtil.notNullOrEmpty(value))
      {
         return new AeSchemaDateTime(value);
      }
      else
      {
         return null;
      }
   }

   /**
    * Returns text of a node identified by the given xpath.
    * @param aNode The node to select from.
    * @param aPath The XPath expression.
    * @param aNsPrefixUriMap optional namespace prefix:uri map.
    * @return String value or null if not found.
    */
   public static String selectText(Node aNode, String aPath, Map aNsPrefixUriMap)
   {
      try
      {
         Element elem = (Element) selectSingleNode(aNode, aPath, aNsPrefixUriMap);
         if (elem != null)
         {
            return AeXmlUtil.getText(elem).trim();
         }
         else
         {
            return null;
         }
      }
      catch (Exception ex)
      {
         AeException.logError(ex);
         return null;
      }
   }

   /**
    * Utility method to add a set namespace prefix:uris.
    * @param aXpath
    * @param aNsPrefixUriMap optional namespace prefix:uri map.
    */
   public static void setNameSpacePrefixUris(XPath aXpath, Map aNsPrefixUriMap) throws JaxenException
   {
      if (aXpath != null && AeUtil.notNullOrEmpty(aNsPrefixUriMap))
      {
         for(Iterator it = aNsPrefixUriMap.keySet().iterator(); it.hasNext();)
         {
            String prefix = (String) it.next();
            String uri = (String) aNsPrefixUriMap.get(prefix);
            if (AeUtil.notNullOrEmpty(prefix) && AeUtil.notNullOrEmpty(uri))
            {
               aXpath.addNamespace(prefix, uri);
            }
         }
      }
   }

   /**
    * Returns true if the element identified by a xpath expression is null or if it has xsi:nil attribute value of "true".
    * @param aNode context root node
    * @param aXPath
    * @return if element is null or nil.
    */
   public static boolean isNil(Node aNode, String aXPath) throws AeException
   {
      return isNil(aNode, aXPath, null );
   }

   /**
    * Returns true if the element identified by a xpath expression is null or if it has xsi:nil attribute value of "true".
    * @param aNode context root node
    * @param aXPath
    * @param aNsPrefixUriMap optional namespace prefix:uri map.
    * @return if element is null or nil.
    */
   public static boolean isNil(Node aNode, String aXPath, Map aNsPrefixUriMap) throws AeException
   {
      return AeXmlUtil.isNil((Element) selectSingleNode(aNode, aXPath, aNsPrefixUriMap) );
   }
}
