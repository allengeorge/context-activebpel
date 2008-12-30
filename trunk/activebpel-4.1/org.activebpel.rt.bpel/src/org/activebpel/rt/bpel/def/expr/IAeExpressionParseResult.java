//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/expr/IAeExpressionParseResult.java,v 1.10 2007/06/29 22:24:1
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

import java.util.List;
import java.util.Set;

import org.activebpel.rt.bpel.def.util.AeVariableData;
import org.activebpel.rt.bpel.def.util.AeVariableProperty;

/**
 * This interface defines what an expression parse result is.  An expression parse result is the
 * result of parsing an expression (of course).
 */
public interface IAeExpressionParseResult
{
   /**
    * Gets the expression that was parsed to create this result.
    */
   public String getExpression();

   /**
    * Returns true if there were errors during parse.
    */
   public boolean hasErrors();

   /**
    * Gets a list of errors found during parsing.
    */
   public List getParseErrors();

   /**
    * Gets a list of all the function calls made in the expression.  The return value is a set of
    * AeScriptFuncDef objects.
    */
   public Set getFunctions();

   /**
    * Gets a list of all the expression variable (not necessarily BPEL variable) references.  Note that this
    * should be a set of 'external' variable references.  This means that references to variables declared in
    * the expression itself (xquery, javascript, etc) should not be returned.
    * @return Set of {@link AeScriptVarDef}
    */
   public Set getVariableReferences();

   /**
    * Gets a list of all the variables used in the expression.  This method returns a set of String
    * objects (the names of the variables).
    */
   public Set getVarNames();

   /**
    * Gets a list of all the bpws:getVariableData functions in the expression. 
    * @return List of {@link AeScriptFuncDef}
    */
   public List getVarDataFunctionList();

   /**
    * Gets a list of all the bpws:getVariableProperty functions in the expression. 
    * @return List of {@link AeScriptFuncDef}
    */
   public List getVarPropertyFunctionList();
   
   /**
    * Gets a list of all of the abx: attachment functions in the expression.
    * @return List of {@link AeScriptFuncDef}
    */
   public List getAttachmentFunctionList();

   /**
    * Gets a list of all the abx:getMyRoleProperty functions in the expression. 
    * @return List of {@link AeScriptFuncDef}
    */
   public List getMyRolePropertyFunctionList();

   /**
    * Gets a list of all the bpws:getLinkStatus functions in the expression.
    * @return List of {@link AeScriptFuncDef}
    */
   public List getLinkStatusFunctionList();

   /**
    * Gets a list of all the bpel:doXslTransform functions in the expression.  
    * @return List of {@link AeScriptFuncDef}
    */
   public List getDoXslTransformFunctionList();

   /**
    * Gets a list of all the variable data (AeVariableData) objects.
    * @return List of {@link AeVariableData}
    */
   public List getVarDataList();

   /**
    * Gets a list of all the variable property (AeVariableProperty) objects.
    * @return List of {@link AeVariableProperty}
    */
   public List getVarPropertyList();
   
   /**
    * Gets a list of all the variable data (AeVariableData) objects which 
    * reference attachments.
    * @return {@link AeVariableData}
    */
   public List getVarAttachmentList();
   
   /**
    * Gets the list of all stylesheet URIs found in the expression.  Stylesheet URIs are found
    * as the first param of the bpel:doXslTransform function.
    */
   public List getStylesheetURIList();
}
