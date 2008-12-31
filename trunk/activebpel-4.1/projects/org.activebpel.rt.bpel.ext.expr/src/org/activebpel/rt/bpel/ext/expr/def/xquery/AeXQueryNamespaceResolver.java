// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.ext.expr/src/org/activebpel/rt/bpel/ext/expr/def/xquery/AeXQueryNamespaceResolver.java,v 1.2 2006/09/27 20:00:2
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

import java.util.Collections;
import java.util.Iterator;

import net.sf.saxon.om.NamespaceResolver;

import org.activebpel.rt.xml.IAeNamespaceContext;

/**
 * An implementation of a Saxon NamespaceResolver.
 */
public class AeXQueryNamespaceResolver implements NamespaceResolver
{
   /** The namespace context. */
   private IAeNamespaceContext mNamespaceContext;

   /**
    * Constructor given a NS context.
    * 
    * @param aNamespaceContext
    */
   public AeXQueryNamespaceResolver(IAeNamespaceContext aNamespaceContext)
   {
      setNamespaceContext(aNamespaceContext);
   }
   
   /**
    * @see net.sf.saxon.om.NamespaceResolver#getURIForPrefix(java.lang.String, boolean)
    */
   public String getURIForPrefix(String aPrefix, boolean aUseDefault)
   {
      return getNamespaceContext().resolvePrefixToNamespace(aPrefix);
   }

   /**
    * @see net.sf.saxon.om.NamespaceResolver#iteratePrefixes()
    */
   public Iterator iteratePrefixes()
   {
      // There is no equivalent of this functinality in IAeNamespaceContext, so just return an 
      // empty iterator.
      return Collections.EMPTY_LIST.iterator();
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
