//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/function/IAeFunctionExecutionContext.java,v 1.6 2006/09/27 19:58:4
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
package org.activebpel.rt.bpel.function;

import org.activebpel.rt.bpel.impl.AeAbstractBpelObject;
import org.activebpel.rt.bpel.impl.IAeFaultFactory;
import org.activebpel.rt.bpel.impl.expr.IAeExpressionTypeConverter;
import org.activebpel.rt.xml.IAeNamespaceContext;

/**
 * This context is passed to a function when it is executed.
 */
public interface IAeFunctionExecutionContext
{
   /**
    * Returns the namespace context to use when executing an expression.
    */
   public IAeNamespaceContext getNamespaceContext();
   
   /**
    * Returns the function context to use when executing an expression.
    */
   public IAeFunctionFactory getFunctionContext();

   /**
    * Returns the object to use as the evaluation context.
    */
   public Object getEvaluationContext();

   /**
    * Returns the abstract bpel object that will be used during expression execution.
    */
   // TODO (EPW) see where this is used and replace with an interface
   public AeAbstractBpelObject getAbstractBpelObject();
   
   /**
    * Provides getter for the fault factory
    */
   public IAeFaultFactory getFaultFactory();

   /**
    * Returns the namespace of the BPEL process that this expression is contained within.
    */
   public String getBpelNamespace();
   
   /**
    * Returns the expression language-specific type converter.
    */
   public IAeExpressionTypeConverter getTypeConverter();
}
