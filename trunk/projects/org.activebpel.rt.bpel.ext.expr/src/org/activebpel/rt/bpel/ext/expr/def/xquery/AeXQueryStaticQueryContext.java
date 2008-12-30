//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.ext.expr/src/org/activebpel/rt/bpel/ext/expr/def/xquery/AeXQueryStaticQueryContext.java,v 1.2 2006/09/27 20:00:2
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
import net.sf.saxon.query.StaticQueryContext;

import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.xml.IAeNamespaceContext;

/**
 * A simple class used during static XQuery expression syntax validation. This class is necessary to resolve
 * unknown prefixes to their corresponding namespaces. For static syntax validation, it is not important what
 * the prefix maps to, as long as it maps to something. The resulting resolved URI will then be passed into
 * the function library to bind a prefixed function to a function impl. During static syntax validation, we
 * install a function library that always returns a no-op XQuery function. Therefore, the
 */
public class AeXQueryStaticQueryContext extends StaticQueryContext
{
   /** A namespace context to use for resolving namespaces. */
   private IAeNamespaceContext mNamespaceContext;

   /**
    * Simple constructor for the custom static query context.
    * @param aConfig
    */
   public AeXQueryStaticQueryContext(Configuration aConfig, IAeNamespaceContext aNamespaceContext)
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
      String rval = getNamespaceContext().resolvePrefixToNamespace(aPrefix);
      if (AeUtil.isNullOrEmpty(rval))
      {
         rval = super.checkURIForPrefix(aPrefix);
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

