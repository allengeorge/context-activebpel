// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.ext.expr/src/org/activebpel/rt/bpel/ext/expr/def/javascript/AeJavaScriptParseUtil.java,v 1.2 2006/09/27 20:00:2
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
package org.activebpel.rt.bpel.ext.expr.def.javascript;

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.def.expr.AeScriptFuncDef;
import org.activebpel.rt.xml.IAeNamespaceContext;
import org.mozilla.javascript.Node;
import org.mozilla.javascript.Token;

/**
 * A helper class that contains some static methods for parsing JavaScript expressions.
 */
public class AeJavaScriptParseUtil
{
   /**
    * Extracts a single function def object from the given node.  The node is assumed to be of type
    * CALL.  The name of the function is extracted from the first child.  The arguments are not
    * extracted, that will be done later by the <code>extractArgsIntoFunction</code> method.
    * 
    * @param aNode
    */
   public static AeScriptFuncDef extractFunction(IAeNamespaceContext aNamespaceContext, Node aNode)
   {
      String prefix = null;
      String name = null;

      // The first child is always the "name" of the function. This will either be a simple NAME
      // or an arbitrarily nested set of GETPROP nodes.
      Node funcNameNode = aNode.getFirstChild();
      if (funcNameNode.getType() == Token.NAME)
      {
         name = funcNameNode.getString();
      }
      else if (funcNameNode.getType() == Token.GETPROP)
      {
         QName funcName = extractName(funcNameNode);
         prefix = funcName.getNamespaceURI();
         name = funcName.getLocalPart();
      }
      else
      {
         name = "UnknownFuncCall"; //$NON-NLS-1$
      }

      String ns = aNamespaceContext.resolvePrefixToNamespace(prefix);
      return new AeScriptFuncDef(ns, name);
   }

   /**
    * Extracts a function name given the GETPROP node.  This node will always have two children.  The
    * first will be either a NAME or it will be another GETPROP node.  For example, a JavaScript call
    * of the form <code>myVar.funcName()</code> (when parsed) will look like:<br>
    * <pre>
    * GETPROP
    *   |--> NAME('myVar')
    *   |--> STRING('funcName')
    * </pre>
    * <br>
    * However, a JavaScript call of the form <code>myVar.bean.obj.funcName()</code> will look like:<br>
    * <pre>
    * GETPROP
    *   |--> GETPROP
    *          |--> GETPROP
    *                 |--> NAME('myVar')
    *                 |--> STRING('bean')
    *          |--> STRING('obj')
    *   |--> STRING('funcName')
    * </pre>
    * @param aGetPropNode
    */
   public static QName extractName(Node aGetPropNode)
   {
      Node propNameNode = aGetPropNode.getFirstChild();
      Node propValNode = propNameNode.getNext();

      if (propNameNode.getType() == Token.NAME)
      {
         return new QName(propNameNode.getString(), propValNode.getString());
      }
      else if (propNameNode.getType() == Token.GETPROP)
      {
         QName qname = extractName(propNameNode);
         String propVal = propValNode.getString();
         return new QName(qname.getNamespaceURI(), qname.getLocalPart() + "." + propVal); //$NON-NLS-1$
      }
      else
      {
         return new QName(null, "UnknownFuncName"); //$NON-NLS-1$
      }
   }

   /**
    * Extracts the argument nodes from the given node and adds them as arguments to the function
    * def object.  The Node that is passed in will have at least one node (the name of the function
    * call).  Any addition children of the node will be arguments to the call.
    * 
    * @param aNode mozilla javascript node
    * @param aFuncDef
    */
   public static void extractArgsIntoFunction(Node aNode, AeScriptFuncDef aFuncDef)
   {
      Node child = aNode.getFirstChild();
      while ( (child = child.getNext()) != null )
      {
         switch (child.getType())
         {
            case Token.STRING:
            {
               aFuncDef.getArgs().add(child.getString());
               break;
            }
            case Token.NUMBER:
            {
               aFuncDef.getArgs().add(new Double(child.getDouble()));
               break;
            }
            default:
            {
               aFuncDef.getArgs().add(AeScriptFuncDef.__EXPRESSION__);
               break;
            }
         }
      }
   }
}
