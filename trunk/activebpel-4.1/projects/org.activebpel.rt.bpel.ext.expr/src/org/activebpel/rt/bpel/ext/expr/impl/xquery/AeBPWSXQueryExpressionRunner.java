//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.ext.expr/src/org/activebpel/rt/bpel/ext/expr/impl/xquery/AeBPWSXQueryExpressionRunner.java,v 1.1 2006/09/15 14:53:3
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

import net.sf.saxon.variables.VariableResolver;

import org.activebpel.rt.bpel.function.IAeFunctionExecutionContext;

/**
 * Implements an XQuery 1.0 expression runner. This implementation is capable of executing expression that
 * conform to the XQuery 1.0 specification for BPEL 1.1 processes.
 */
public class AeBPWSXQueryExpressionRunner extends AeAbstractXQueryExpressionRunner
{
   /**
    * Default constructor.
    */
   public AeBPWSXQueryExpressionRunner()
   {
   }

   /**
    * There is no $varName support in BPEL 1.1.
    * 
    * @see org.activebpel.rt.bpel.ext.expr.impl.xquery.AeAbstractXQueryExpressionRunner#createVariableResolver(org.activebpel.rt.bpel.function.IAeFunctionExecutionContext)
    */
   protected VariableResolver createVariableResolver(IAeFunctionExecutionContext aFunctionExecContext)
   {
      return null;
   }

   /**
    * There is no $linkName support in BPEL 1.1.
    * 
    * @see org.activebpel.rt.bpel.ext.expr.impl.xquery.AeAbstractXQueryExpressionRunner#createLinkVariableResolver(org.activebpel.rt.bpel.function.IAeFunctionExecutionContext)
    */
   protected VariableResolver createLinkVariableResolver(IAeFunctionExecutionContext aFunctionExecContext)
   {
      return null;
   }
}
