//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/expr/xpath/AeXPathNamespaceContext.java,v 1.4 2006/09/27 19:58:4
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

import org.activebpel.rt.xml.IAeNamespaceContext;
import org.jaxen.NamespaceContext;

/**
 * Implements a namespace context that can be used during XPath expression execution.  It uses
 * the generic AE namespace context to do the actual translation.
 */
public class AeXPathNamespaceContext implements NamespaceContext
{
   /** The generic namespace context to use. */
   private IAeNamespaceContext mNamespaceContext;

   /**
    * Constructs an xpath namespace context from the generic namespace context.
    * 
    * @param aNamespaceContext
    */
   public AeXPathNamespaceContext(IAeNamespaceContext aNamespaceContext)
   {
      setNamespaceContext(aNamespaceContext);
   }

   /**
    * @see org.jaxen.NamespaceContext#translateNamespacePrefixToUri(java.lang.String)
    */
   public String translateNamespacePrefixToUri(String aPrefix)
   {
      return getNamespaceContext().resolvePrefixToNamespace(aPrefix);
   }

   /**
    * Getter for the namespace context.
    */
   protected IAeNamespaceContext getNamespaceContext()
   {
      return mNamespaceContext;
   }

   /**
    * Setter for the namespace context.
    * 
    * @param aContext
    */
   protected void setNamespaceContext(IAeNamespaceContext aContext)
   {
      mNamespaceContext = aContext;
   }
}
