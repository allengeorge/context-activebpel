// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.ext.expr/src/org/activebpel/rt/bpel/ext/expr/impl/xquery/AeWSBPELXQueryExpressionRunner.java,v 1.2 2006/09/15 14:53:3
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
 * A WS-BPEL implementation of an XQuery expression runner.
 */
public class AeWSBPELXQueryExpressionRunner extends AeAbstractXQueryExpressionRunner
{
   /**
    * Default c'tor.
    */
   public AeWSBPELXQueryExpressionRunner()
   {
      super();
   }
   
   /**
    * @see org.activebpel.rt.bpel.ext.expr.impl.xquery.AeBPWSXQueryExpressionRunner#createLinkVariableResolver(org.activebpel.rt.bpel.function.IAeFunctionExecutionContext)
    */
   protected VariableResolver createLinkVariableResolver(IAeFunctionExecutionContext aFunctionExecContext)
   {
      return new AeWSBPELXQueryLinkVariableResolver(aFunctionExecContext);
   }

   /**
    * @see org.activebpel.rt.bpel.ext.expr.impl.xquery.AeBPWSXQueryExpressionRunner#createVariableResolver(org.activebpel.rt.bpel.function.IAeFunctionExecutionContext)
    */
   protected VariableResolver createVariableResolver(IAeFunctionExecutionContext aFunctionExecContext)
   {
      return new AeWSBPELXQueryVariableResolver(aFunctionExecContext);
   }
}
