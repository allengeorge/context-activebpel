//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/expr/xpath/AeBPWSXPathExpressionRunner.java,v 1.1 2006/09/15 14:49:5
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
package org.activebpel.rt.bpel.impl.expr.xpath;

import org.activebpel.rt.bpel.function.IAeFunctionExecutionContext;
import org.jaxen.VariableContext;


/**
 * Implements an XPath 1.0 expression runner. This implementation is capable of executing 
 * expression that conform to the XPath 1.0 specification.  This implementation is specific to
 * BPEL 1.1 (BPEL4WS).
 */
public class AeBPWSXPathExpressionRunner extends AeAbstractXPathExpressionRunner
{
   /**
    * Default constructor.
    */
   public AeBPWSXPathExpressionRunner()
   {
   }

   /**
    * BPEL 1.1 processes don't support $varName syntax.
    * 
    * @see org.activebpel.rt.bpel.impl.expr.xpath.AeAbstractXPathExpressionRunner#createVariableContext(org.activebpel.rt.bpel.function.IAeFunctionExecutionContext)
    */
   protected VariableContext createVariableContext(IAeFunctionExecutionContext aContext)
   {
      return null;
   }

   /**
    * BPEL 1.1 processes don't support $linkName syntax.
    * 
    * @see org.activebpel.rt.bpel.impl.expr.xpath.AeAbstractXPathExpressionRunner#createJoinConditionVariableContext(org.activebpel.rt.bpel.function.IAeFunctionExecutionContext)
    */
   protected VariableContext createJoinConditionVariableContext(IAeFunctionExecutionContext aContext)
   {
      return null;
   }
}
