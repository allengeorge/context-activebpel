// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.ext.expr/src/org/activebpel/rt/bpel/ext/expr/impl/xquery/AeWSBPELXQueryVariableResolver.java,v 1.2 2006/09/22 19:55:0
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
package org.activebpel.rt.bpel.ext.expr.impl.xquery;

import java.util.ArrayList;
import java.util.List;

import net.sf.saxon.expr.Expression;
import net.sf.saxon.expr.VariableDeclaration;
import net.sf.saxon.query.GlobalVariableDefinition;
import net.sf.saxon.value.SequenceType;
import net.sf.saxon.value.StringValue;
import net.sf.saxon.variables.VariableResolver;

import org.activebpel.rt.bpel.IAeVariable;
import org.activebpel.rt.bpel.def.expr.xpath.AeXPathVariableReference;
import org.activebpel.rt.bpel.function.IAeFunction;
import org.activebpel.rt.bpel.function.IAeFunctionExecutionContext;
import org.activebpel.rt.bpel.impl.function.AeGetVariableDataFunction;
import org.activebpel.rt.util.AeUtil;

/**
 * A WS-BPEL Xquery variable resolver.  This class resolves XQuery variables (of the form $bpelVarName)
 * to XQuery expressions that, when executed, return the value of the named BPEL variable.
 */
public class AeWSBPELXQueryVariableResolver implements VariableResolver
{
   /** The function exec context. */
   private IAeFunctionExecutionContext mFunctionExecutionContext;

   /**
    * Constructor.
    * 
    * @param aContext
    */
   public AeWSBPELXQueryVariableResolver(IAeFunctionExecutionContext aContext)
   {
      setFunctionExecutionContext(aContext);
   }

   /**
    * @see net.sf.saxon.variables.VariableResolver#hasVariable(int, java.lang.String, java.lang.String)
    */
   public boolean hasVariable(int aNameCode, String aUri, String aLocal)
   {
      if (AeUtil.notNullOrEmpty(aUri))
      {
         return false;
      }
      AeXPathVariableReference varRef = new AeXPathVariableReference(aLocal);
      String varName = varRef.getVariableName();
      IAeVariable variable = getFunctionExecutionContext().getAbstractBpelObject().findVariable(varName);
      return variable != null;
   }

   /**
    * @see net.sf.saxon.variables.VariableResolver#resolve(int, java.lang.String, java.lang.String)
    */
   public VariableDeclaration resolve(int aNameCode, String aUri, String aLocal)
   {
      // Note: don't need to pass the URI here, because we already know it is empty
      return createGlobalVariableDef(aNameCode, aLocal);
   }

   /**
    * Creates a global variable definition for the given namecode + local name.
    * 
    * @param aNameCode
    * @param aVariableName
    */
   private GlobalVariableDefinition createGlobalVariableDef(int aNameCode, String aVariableName)
   {
      GlobalVariableDefinition globalVarDef = new GlobalVariableDefinition();
      globalVarDef.setNameCode(aNameCode);
      globalVarDef.setIsParameter(true);
      globalVarDef.setVariableName(aVariableName);
      globalVarDef.setRequiredType(SequenceType.SINGLE_ITEM);

      Expression expression = createVariableExpression(aVariableName);
      globalVarDef.setValueExpression(expression);

      return globalVarDef;
   }

   /**
    * Create the expression that will be executed for the variable reference.
    * 
    * @param aVariableName
    */
   protected Expression createVariableExpression(String aVariableName)
   {
      IAeFunction function = new AeGetVariableDataFunction();
      AeXPathVariableReference varRef = new AeXPathVariableReference(aVariableName);
      String varName = varRef.getVariableName();
      String partName = varRef.getPartName();
      List args = new ArrayList();
      args.add(StringValue.makeStringValue(varName));
      if (AeUtil.notNullOrEmpty(partName))
      {
         args.add(StringValue.makeStringValue(partName));
      }
      AeXQueryFunction func = new AeXQueryFunction(function, getFunctionExecutionContext());
      func.setArguments((Expression[]) args.toArray(new Expression[args.size()]));
      return func;
   }
   
   /**
    * @return Returns the functionExecutionContext.
    */
   protected IAeFunctionExecutionContext getFunctionExecutionContext()
   {
      return mFunctionExecutionContext;
   }

   /**
    * @param aFunctionExecutionContext The functionExecutionContext to set.
    */
   protected void setFunctionExecutionContext(IAeFunctionExecutionContext aFunctionExecutionContext)
   {
      mFunctionExecutionContext = aFunctionExecutionContext;
   }
}
