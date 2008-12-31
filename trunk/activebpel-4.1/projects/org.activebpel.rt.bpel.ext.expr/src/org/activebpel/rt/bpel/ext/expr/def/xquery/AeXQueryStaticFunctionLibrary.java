//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.ext.expr/src/org/activebpel/rt/bpel/ext/expr/def/xquery/AeXQueryStaticFunctionLibrary.java,v 1.3 2006/09/27 20:00:2
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

import net.sf.saxon.expr.Expression;
import net.sf.saxon.functions.FunctionLibrary;
import net.sf.saxon.trans.XPathException;

import org.activebpel.rt.xml.IAeNamespaceContext;

/**
 * Implements a function library for use during XQuery syntax validation.  This function
 * library does not contain any real functions - it will return a simple no-op function
 * for all function lookups.
 */
public class AeXQueryStaticFunctionLibrary implements FunctionLibrary
{
   /** A namespace context. */
   private IAeNamespaceContext mNamespaceContext;

   /**
    * Constructor.
    * 
    * @param aNamespaceContext
    */
   public AeXQueryStaticFunctionLibrary(IAeNamespaceContext aNamespaceContext)
   {
      setNamespaceContext(aNamespaceContext);
   }

   /**
    * @see net.sf.saxon.functions.FunctionLibrary#isAvailable(int, java.lang.String, java.lang.String, int)
    */
   public boolean isAvailable(int aFingerprint, String aUri, String aLocal, int arity)
   {
      return true;
   }

   /**
    * @see net.sf.saxon.functions.FunctionLibrary#bind(int, java.lang.String, java.lang.String, net.sf.saxon.expr.Expression[])
    */
   public Expression bind(int aNameCode, String aUri, String aLocal, Expression[] aStaticArgs)
         throws XPathException
   {
      AeNoOpFunctionCall func = new AeNoOpFunctionCall(aUri, aLocal);
      func.setArguments(aStaticArgs);
      return func;
   }

   /**
    * @see net.sf.saxon.functions.FunctionLibrary#copy()
    */
   public FunctionLibrary copy()
   {
      return new AeXQueryStaticFunctionLibrary(getNamespaceContext());
   }

   /**
    * @return Returns the namespaceContext.
    */
   protected IAeNamespaceContext getNamespaceContext()
   {
      return mNamespaceContext;
   }

   /**
    * @param aNamespaceContext The namespaceContext to set.
    */
   protected void setNamespaceContext(IAeNamespaceContext aNamespaceContext)
   {
      mNamespaceContext = aNamespaceContext;
   }
}
