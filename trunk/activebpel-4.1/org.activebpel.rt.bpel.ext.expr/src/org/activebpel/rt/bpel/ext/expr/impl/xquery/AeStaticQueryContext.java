//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.ext.expr/src/org/activebpel/rt/bpel/ext/expr/impl/xquery/AeStaticQueryContext.java,v 1.3 2006/09/27 20:00:2
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

import net.sf.saxon.Configuration;
import net.sf.saxon.query.StaticQueryContext;

import org.activebpel.rt.xml.IAeNamespaceContext;

/**
 * This class extends the Saxon static query context in order to override certain
 * functionality, such as namespace/prefix lookups.
 */
public class AeStaticQueryContext extends StaticQueryContext
{
   /** The ActiveBPEL expression namespace context to use to resolve namespaces from prefixes. */
   private IAeNamespaceContext mNamespaceContext;

   /**
    * Constructs a static query context from the saxon configuration and the generic ActiveBPEL
    * namespace context.
    * 
    * @param aConfig
    * @param aNamespaceContext
    */
   public AeStaticQueryContext(Configuration aConfig, IAeNamespaceContext aNamespaceContext)
   {
      super(aConfig);
      setNamespaceContext(aNamespaceContext);
   }

   /**
    * Overrides method to lookup the namespace from within our own namespace context before
    * delegating to Saxon.
    * 
    * @see net.sf.saxon.query.StaticQueryContext#checkURIForPrefix(java.lang.String)
    */
   public String checkURIForPrefix(String aPrefix) 
   {
      // Check the query itself for the mapping first - only check our namespace context
      // if we can't find a mapping in the query.
      String rval = super.checkURIForPrefix(aPrefix);
      if (rval == null)
      {
         rval = getNamespaceContext().resolvePrefixToNamespace(aPrefix);
      }
      return rval;
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
