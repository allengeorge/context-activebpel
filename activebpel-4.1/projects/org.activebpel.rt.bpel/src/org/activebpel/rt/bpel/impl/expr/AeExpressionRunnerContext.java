//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/expr/AeExpressionRunnerContext.java,v 1.6 2006/09/27 19:58:4
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
 * This is a standard expression runner context. It will be passed to the expression runner when an expression
 * is going to be executed.
 */
public class AeExpressionRunnerContext implements IAeExpressionRunnerContext
{
   /** An abstract bpel object. */
   private AeAbstractBpelObject mAbstractBpelObject;
   /** The context to use when evaluating an expression. */
   private Object mEvaluationContext;
   /** A generic namespace context. */
   private IAeNamespaceContext mNamespaceContext;
   /** A generic function context. */
   private IAeFunctionFactory mFunctionContext;
   /** The expression language URI. */
   private String mLanguageURI;

   /**
    * Constructs an expression runner context.
    * 
    * @param aAbstractBpelObject
    * @param aEvaluationContext
    * @param aLanguageURI
    * @param aNamespaceContext
    * @param aFunctionContext
    */
   public AeExpressionRunnerContext(AeAbstractBpelObject aAbstractBpelObject, Object aEvaluationContext,
         String aLanguageURI, IAeNamespaceContext aNamespaceContext, IAeFunctionFactory aFunctionContext)
   {
      setAbstractBpelObject(aAbstractBpelObject);
      setEvaluationContext(aEvaluationContext);
      setLanguageURI(aLanguageURI);
      setNamespaceContext(aNamespaceContext);
      setFunctionContext(aFunctionContext);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.expr.IAeExpressionRunnerContext#getAbstractBpelObject()
    */
   public AeAbstractBpelObject getAbstractBpelObject()
   {
      return mAbstractBpelObject;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.expr.IAeExpressionRunnerContext#getNamespaceContext()
    */
   public IAeNamespaceContext getNamespaceContext()
   {
      return mNamespaceContext;
   }

   /**
    * Overrides method to 
    * @see org.activebpel.rt.bpel.impl.expr.IAeExpressionRunnerContext#getEvaluationContext()
    */
   public Object getEvaluationContext()
   {
      return mEvaluationContext;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.expr.IAeExpressionRunnerContext#getFunctionContext()
    */
   public IAeFunctionFactory getFunctionContext()
   {
      return mFunctionContext;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.expr.IAeExpressionRunnerContext#getFaultFactory()
    */
   public IAeFaultFactory getFaultFactory()
   {
      return getAbstractBpelObject().getFaultFactory();
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.expr.IAeExpressionRunnerContext#getBpelNamespace()
    */
   public String getBpelNamespace()
   {
      return getFaultFactory().getNamespace();
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.expr.IAeExpressionRunnerContext#getLanguageURI()
    */
   public String getLanguageURI()
   {
      return mLanguageURI;
   }

   /**
    * @param aAbstractBpelObject The abstractBpelObject to set.
    */
   protected void setAbstractBpelObject(AeAbstractBpelObject aAbstractBpelObject)
   {
      mAbstractBpelObject = aAbstractBpelObject;
   }

   /**
    * @param aFunctionContext The functionContext to set.
    */
   protected void setFunctionContext(IAeFunctionFactory aFunctionContext)
   {
      mFunctionContext = aFunctionContext;
   }

   /**
    * @param aNamespaceContext The namespaceContext to set.
    */
   protected void setNamespaceContext(IAeNamespaceContext aNamespaceContext)
   {
      mNamespaceContext = aNamespaceContext;
   }

   /**
    * @param aEvaluationContext The evaluationContext to set.
    */
   protected void setEvaluationContext(Object aEvaluationContext)
   {
      mEvaluationContext = aEvaluationContext;
   }

   /**
    * @param aLanguageURI The languageURI to set.
    */
   protected void setLanguageURI(String aLanguageURI)
   {
      mLanguageURI = aLanguageURI;
   }
}
