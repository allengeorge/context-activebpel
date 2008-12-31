//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/expr/AeAbstractExpressionParseResult.java,v 1.10 2007/06/22 17:48:1
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
package org.activebpel.rt.bpel.def.expr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.def.util.AeVariableData;
import org.activebpel.rt.bpel.def.util.AeVariableProperty;
import org.activebpel.rt.util.AeUtil;

/**
 * An abstract parse result object that can be extended by language specific impls.
 *
 * Fundamentally, all of the implementations of methods in this base class will use the
 * <code>getFunctions</code> method call, which any class extending this base class must
 * implement.  The <code>getFunctions</code> method will return a list of functions that
 * the other methods in this implementation will interrogate in various ways.  For example,
 * the <code>getLinkStatusFunctionList</code> implementation will get the full list of
 * functions and filter out any that aren't "getLinkStatus".
 */
public abstract class AeAbstractExpressionParseResult implements IAeExpressionParseResult
{
   /** The expression. */
   private String mExpression;
   /** The parser context. */
   private IAeExpressionParserContext mParserContext;
   /** A list of errors found during parse. */
   private List mErrors;

   /**
    * Creates the parse result.
    *
    * @param aExpression
    * @param aParserContext
    */
   public AeAbstractExpressionParseResult(String aExpression, IAeExpressionParserContext aParserContext)
   {
      setExpression(aExpression);
      setParserContext(aParserContext);
      setErrors(new ArrayList());
   }

   /**
    * @see org.activebpel.rt.bpel.def.expr.IAeExpressionParseResult#getExpression()
    */
   public String getExpression()
   {
      return mExpression;
   }

   /**
    * @see org.activebpel.rt.bpel.def.expr.IAeExpressionParseResult#getParseErrors()
    */
   public List getParseErrors()
   {
      return getErrors();
   }

   /**
    * @see org.activebpel.rt.bpel.def.expr.IAeExpressionParseResult#hasErrors()
    */
   public boolean hasErrors()
   {
      return getParseErrors().size() > 0;
   }

   /**
    * @see org.activebpel.rt.bpel.def.expr.IAeExpressionParseResult#getLinkStatusFunctionList()
    */
   public List getLinkStatusFunctionList()
   {
      List list = new LinkedList();
      for (Iterator iter = getFunctions().iterator(); iter.hasNext();)
      {
         AeScriptFuncDef function = (AeScriptFuncDef) iter.next();
         if (isGetLinkStatusFunction(function))
         {
            list.add(function);
         }
      }
      return list;
   }

   /**
    * @see org.activebpel.rt.bpel.def.expr.IAeExpressionParseResult#getDoXslTransformFunctionList()
    */
   public List getDoXslTransformFunctionList()
   {
      List list = new LinkedList();
      for (Iterator iter = getFunctions().iterator(); iter.hasNext();)
      {
         AeScriptFuncDef function = (AeScriptFuncDef) iter.next();
         if (isDoXslTransformFunction(function))
         {
            list.add(function);
         }
      }
      return list;
   }

   /**
    * @see org.activebpel.rt.bpel.def.expr.IAeExpressionParseResult#getVarDataFunctionList()
    */
   public List getVarDataFunctionList()
   {
      List list = new LinkedList();
      for (Iterator iter = getFunctions().iterator(); iter.hasNext();)
      {
         AeScriptFuncDef function = (AeScriptFuncDef) iter.next();
         if (isGetVariableDataFunction(function))
         {
            list.add(function);
         }
      }
      return list;
   }

   /**
    * @see org.activebpel.rt.bpel.def.expr.IAeExpressionParseResult#getVarPropertyFunctionList()
    */
   public List getVarPropertyFunctionList()
   {
      List list = new LinkedList();
      for (Iterator iter = getFunctions().iterator(); iter.hasNext();)
      {
         AeScriptFuncDef function = (AeScriptFuncDef) iter.next();
         if (isGetVariablePropertyFunction(function))
         {
            list.add(function);
         }
      }
      return list;
   }

   /**
    * @see org.activebpel.rt.bpel.def.expr.IAeExpressionParseResult#getAttachmentFunctionList()
    */
   public List getAttachmentFunctionList()
   {
      List list = new LinkedList();
      for (Iterator iter = getFunctions().iterator(); iter.hasNext();)
      {
         AeScriptFuncDef function = (AeScriptFuncDef) iter.next();
         if (isAttachmentFunction(function))
            list.add(function);
      }
      return list;
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.expr.IAeExpressionParseResult#getMyRolePropertyFunctionList()
    */
   public List getMyRolePropertyFunctionList()
   {
      List list = new LinkedList();
      for (Iterator iter = getFunctions().iterator(); iter.hasNext();)
      {
         AeScriptFuncDef function = (AeScriptFuncDef) iter.next();
         if (AeExpressionLanguageUtil.isMyRolePropertyFunction(function))
         {
            list.add(function);
         }
      }
      return list;
   }

   /**
    * @see org.activebpel.rt.bpel.def.expr.IAeExpressionParseResult#getVarDataList()
    */
   public List getVarDataList()
   {
      List list = new LinkedList();
      Collection functionList = getVarDataFunctionList();
      for (Iterator iter = functionList.iterator(); iter.hasNext();)
      {
         AeScriptFuncDef function = (AeScriptFuncDef) iter.next();

         String var = function.getStringArgument(0);
         if (AeUtil.notNullOrEmpty(var))
         {
            String part = function.getStringArgument(1);
            String query = function.getStringArgument(2);
            list.add(new AeVariableData(var, part, query));
         }
      }
      return list;
   }

   /**
    * @see org.activebpel.rt.bpel.def.expr.IAeExpressionParseResult#getVarPropertyList()
    */
   public List getVarPropertyList()
   {
      List list = new LinkedList();
      Collection functionList = getFunctions();
      for (Iterator iter = functionList.iterator(); iter.hasNext();)
      {
         AeScriptFuncDef function = (AeScriptFuncDef) iter.next();

         if (isGetVariablePropertyFunction(function))
         {
            String var = function.getStringArgument(0);
            if (AeUtil.notNullOrEmpty(var))
            {
               String propertyStr = function.getStringArgument(1);
               QName property = parseQName(propertyStr);
               list.add(new AeVariableProperty(var, property));
            }
         }
      }
      return list;
   }

   /**
    * @see org.activebpel.rt.bpel.def.expr.IAeExpressionParseResult#getVarAttachmentList()
    */
   public List getVarAttachmentList()
   {
      List list = new LinkedList();
      Collection functionList = getAttachmentFunctionList();
      for (Iterator iter = functionList.iterator(); iter.hasNext();)
      {
         AeScriptFuncDef function = (AeScriptFuncDef) iter.next();
         if (AeExpressionLanguageUtil.ATTACHMENT_COPY_ALL_FUNC.equals(function.getQName()) ||
               AeExpressionLanguageUtil.ATTACHMENT_REMOVE_ALL_FUNC.equals(function.getQName()))
         {
            // first param in copyAllAttachments is a list of var names or *
            String fromVar = function.getStringArgument(0);
            if (AeUtil.notNullOrEmpty(fromVar))
            {
               for (StringTokenizer tok = new StringTokenizer(fromVar); tok.hasMoreTokens();)
               {
                  String varName = tok.nextToken().trim();
                  if (!"*".equals(varName)) //$NON-NLS-1$
                  {
                     list.add(varName);
                  }
               }
            }

            String toVar = function.getStringArgument(1);
            if (AeUtil.notNullOrEmpty(toVar))
               list.add(toVar);
         }
         else if (AeExpressionLanguageUtil.ATTACHMENT_COPY_FUNC.equals(function.getQName()) ||
                  AeExpressionLanguageUtil.ATTACHMENT_REPLACE_FUNC.equals(function.getQName()))
         {
            // copy / replace attachment
            // param 0 = varname
            // param 2 = varname
            String varOne = function.getStringArgument(0);
            if (varOne != null)
               list.add(varOne);
            String varTwo = function.getStringArgument(2);
            if (varTwo != null)
               list.add(varTwo);
         }
         else 
         {
            String var = function.getStringArgument(0);
            if (AeUtil.notNullOrEmpty(var))
               list.add(var);
         }
      }
      return list;
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.expr.IAeExpressionParseResult#getVarNames()
    */
   public Set getVarNames()
   {
      Set rval = new LinkedHashSet();
      for (Iterator iter = getVarDataList().iterator(); iter.hasNext(); )
      {
         AeVariableData vd = (AeVariableData) iter.next();
         rval.add(vd.getVarName());
      }

      for (Iterator iter = getVarPropertyList().iterator(); iter.hasNext(); )
      {
         AeVariableProperty vp = (AeVariableProperty) iter.next();
         rval.add(vp.getVarName());
      }

      for (Iterator iter = getVarAttachmentList().iterator(); iter.hasNext(); )
         rval.add(iter.next());
      
      return rval;
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.expr.IAeExpressionParseResult#getStylesheetURIList()
    */
   public List getStylesheetURIList()
   {
      List list = new LinkedList();
      
      for (Iterator iter = getDoXslTransformFunctionList().iterator(); iter.hasNext(); )
      {
         AeScriptFuncDef function = (AeScriptFuncDef) iter.next();
         String uri = function.getStringArgument(0);
         if (uri != null)
         {
            list.add(uri);
         }
      }
      
      return list;
   }

   /**
    * Parses a qname formatted string into a QName object.
    *
    * @param aQNameString
    */
   protected QName parseQName(String aQNameString)
   {
      // Note: the qname string is unlikely to be null, but it's possible if the call to getVariableProperty()
      // is invalid by having only 1 param.  If it IS invalid for that reason, we handle validating that in
      // another part of the code.
      if (AeUtil.isNullOrEmpty(aQNameString))
      {
         return null;
      }

      String [] parts = aQNameString.split(":"); //$NON-NLS-1$
      if (parts == null || parts.length != 2)
      {
         return new QName(null, aQNameString);
      }
      String ns = getParserContext().getNamespaceContext().resolvePrefixToNamespace(parts[0]);
      return new QName(ns, parts[1]);
   }

   /**
    * Method that returns true if the given function is a "getLinkStatus" function.
    * 
    * @param aFunction
    */
   protected boolean isGetLinkStatusFunction(AeScriptFuncDef aFunction)
   {
      return AeExpressionLanguageUtil.isLinkStatusFunction(aFunction);
   }

   /**
    * Method that returns true if the given function is a "getVariableData" function.
    * 
    * @param aFunction
    */
   protected boolean isGetVariableDataFunction(AeScriptFuncDef aFunction)
   {
      return AeExpressionLanguageUtil.isVarDataFunction(aFunction);
   }

   /**
    * Method that returns true if the given function is an attachment function.
    * 
    * @param aFunction
    */
   protected boolean isAttachmentFunction(AeScriptFuncDef aFunction)
   {
      return AeExpressionLanguageUtil.isAttachmentFunction(aFunction);
   }
   
   /**
    * Method that returns true if the given function is a "doXslTransform" function.
    * 
    * @param aFunction
    */
   protected boolean isDoXslTransformFunction(AeScriptFuncDef aFunction)
   {
      return AeExpressionLanguageUtil.isDoXslTransformFunction(aFunction);
   }

   /**
    * Method that returns true if the given function is a "getVariableProperty" function.
    * 
    * @param aFunction
    */
   protected boolean isGetVariablePropertyFunction(AeScriptFuncDef aFunction)
   {
      return AeExpressionLanguageUtil.isVarPropertyFunction(aFunction);
   }

   /**
    * @return Returns the parserContext.
    */
   protected IAeExpressionParserContext getParserContext()
   {
      return mParserContext;
   }

   /**
    * @param aParserContext The parserContext to set.
    */
   protected void setParserContext(IAeExpressionParserContext aParserContext)
   {
      mParserContext = aParserContext;
   }

   /**
    * @param aExpression The expression to set.
    */
   protected void setExpression(String aExpression)
   {
      mExpression = aExpression;
   }

   /**
    * @return Returns the list of errors.
    */
   public List getErrors()
   {
      return mErrors;
   }

   /**
    * @param aErrors The errors to set.
    */
   protected void setErrors(List aErrors)
   {
      mErrors = aErrors;
   }
}
