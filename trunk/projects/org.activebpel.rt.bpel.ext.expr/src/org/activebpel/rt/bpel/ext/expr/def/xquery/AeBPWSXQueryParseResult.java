// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.ext.expr/src/org/activebpel/rt/bpel/ext/expr/def/xquery/AeBPWSXQueryParseResult.java,v 1.1 2006/09/15 14:53:3
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
package org.activebpel.rt.bpel.ext.expr.def.xquery;

import net.sf.saxon.Configuration;
import net.sf.saxon.expr.Expression;

import org.activebpel.rt.bpel.def.expr.IAeExpressionParserContext;

/**
 * A concrete implementation of a xquery parse result for BPEL 1.1.
 */
public class AeBPWSXQueryParseResult extends AeAbstractXQueryParseResult
{
   /**
    * Constructs the BPWS xquery parse result.
    */
   public AeBPWSXQueryParseResult(String aExpression, Expression aXQueryExpression, Configuration aConfiguration,
         IAeExpressionParserContext aParserContext)
   {
      super(aExpression, aXQueryExpression, aConfiguration, aParserContext);
   }
}
