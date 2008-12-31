//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/expr/IAeExpressionRunnerContext.java,v 1.6 2006/09/27 19:58:4
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
package org.activebpel.rt.bpel.impl.expr;

import org.activebpel.rt.bpel.function.IAeFunctionFactory;
import org.activebpel.rt.bpel.impl.AeAbstractBpelObject;
import org.activebpel.rt.bpel.impl.IAeFaultFactory;
import org.activebpel.rt.xml.IAeNamespaceContext;

/**
 * This interface is used by expression runners during execution of expressions.  The
 * expression runner can interrogate the expression runner context for various 
 * pieces of information needed during execution of the expression.
 */
public interface IAeExpressionRunnerContext
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
   // TODO (MF) see where this is used and replace with interface
   public AeAbstractBpelObject getAbstractBpelObject();
   
   /**
    * Getter for the fault factory
    */
   public IAeFaultFactory getFaultFactory();
   
   /**
    * Gets the BPEL namespace of the process that this expression is contained within.
    */
   public String getBpelNamespace();
   
   /**
    * Gets the URI of the language being used to execute the expression.
    */
   public String getLanguageURI();
}
