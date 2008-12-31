// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.ext.expr/src/org/activebpel/rt/bpel/ext/expr/def/javascript/AeAbstractJavaScriptParseResult.java,v 1.3 2006/10/12 20:22:1
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

import java.util.LinkedHashSet;
import java.util.Set;

import org.activebpel.rt.bpel.def.expr.AeAbstractExpressionParseResult;
import org.activebpel.rt.bpel.def.expr.AeScriptFuncDef;
import org.activebpel.rt.bpel.def.expr.IAeExpressionParserContext;
import org.mozilla.javascript.Node;
import org.mozilla.javascript.ScriptOrFnNode;
import org.mozilla.javascript.Token;

/**
 * A base class for javascript parse results.
 */
public abstract class AeAbstractJavaScriptParseResult extends AeAbstractExpressionParseResult
{
   /** The root of the javascript parse tree. */
   private ScriptOrFnNode mRootNode;
   /** The cached list of functions. */
   private Set mFunctions;
   /** The cached list of variables. */
   private Set mVariables;

   /**
    * Constructor.
    * 
    * @param aExpression
    * @param aRootNode
    * @param aParserContext
    */
   public AeAbstractJavaScriptParseResult(String aExpression, ScriptOrFnNode aRootNode,
         IAeExpressionParserContext aParserContext)
   {
      super(aExpression, aParserContext);
      setRootNode(aRootNode);
   }

   /**
    * @see org.activebpel.rt.bpel.def.expr.IAeExpressionParseResult#getFunctions()
    */
   public Set getFunctions()
   {
      if (mFunctions == null)
      {
         mFunctions = extractFunctions(getRootNode(), null);
      }

      return mFunctions;
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.expr.IAeExpressionParseResult#getVariableReferences()
    */
   public Set getVariableReferences()
   {
      if (mVariables == null)
         mVariables = extractVariables(getRootNode());

      return mVariables;
   }

   
   /**
    * Extracts a function def object from the given JavaScript (Rhino) Node.  The Node is actually
    * a parse tree. This parse tree will be navigated and nodes of type CALL will be extracted into
    * AeScriptFuncDef objects.  A list of these objects will then be returned.
    * 
    * @param aNode
    * @param aParentFunc
    */
   protected Set extractFunctions(Node aNode, AeScriptFuncDef aParentFunc)
   {
      Set set = new LinkedHashSet();
      AeScriptFuncDef parentFunc = aParentFunc;

      // If the Node is a Function Call, extract it and add it to the list.
      if (aNode.getType() == Token.CALL)
      {
         AeScriptFuncDef funcDef = AeJavaScriptParseUtil.extractFunction(getParserContext().getNamespaceContext(), aNode);
         funcDef.setParent(parentFunc);
         AeJavaScriptParseUtil.extractArgsIntoFunction(aNode, funcDef);
         set.add(funcDef);
         parentFunc = funcDef;
      }

      // Now process all of the node's children.
      Node child = aNode.getFirstChild();
      while (child != null)
      {
         set.addAll(extractFunctions(child, parentFunc));
         child = child.getNext();
      }

      return set;
   }

   /**
    * Extracts a variable def object from the given JavaScript (Rhino) Node.  The Node is actually
    * a parse tree. This parse tree will be navigated and nodes of type VAR will be extracted into
    * AeScriptVarDef objects.  A set of these objects will then be returned.
    * 
    * @param aNode
    */
   protected Set extractVariables(Node aNode)
   {
      Set set = new LinkedHashSet();

      // Now process all of the node's children.
      Node child = aNode.getFirstChild();
      while (child != null)
      {
         set.addAll(extractVariables(child));
         child = child.getNext();
      }

      return set;
   }

   /**
    * @return Returns the rootNode.
    */
   public ScriptOrFnNode getRootNode()
   {
      return mRootNode;
   }

   /**
    * @param aRootNode The rootNode to set.
    */
   protected void setRootNode(ScriptOrFnNode aRootNode)
   {
      mRootNode = aRootNode;
   }
}
