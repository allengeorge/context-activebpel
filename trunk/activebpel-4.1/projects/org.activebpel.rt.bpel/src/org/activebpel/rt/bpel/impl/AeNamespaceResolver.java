// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/AeNamespaceResolver.java,v 1.4 2006/09/27 19:58:4
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
package org.activebpel.rt.bpel.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.activebpel.rt.wsdl.def.IAePropertyAlias;
import org.activebpel.rt.xml.IAeNamespaceContext;


/**
 * Class used to resolve namespace prefixes for property aliases. 
 */
public class AeNamespaceResolver implements IAeNamespaceContext
{
   /** Property alias which contains the prefix to namespace mappings */
   IAePropertyAlias mPropAlias;

   /**
    * Constructs a namespace resolver for use with the given property alias
    * @param aPropAlias the alias to use in getting prefix mappings
    */
   public AeNamespaceResolver(IAePropertyAlias aPropAlias)
   {
      mPropAlias = aPropAlias;
   }
   
   /**
    * Returns the namespace associated with the prefix from the associated model.
    * 
    * @see org.activebpel.rt.xml.IAeNamespaceContext#resolvePrefixToNamespace(java.lang.String)
    */
   public String resolvePrefixToNamespace(String aPrefix)
   {
      return (String) mPropAlias.getNamespaces().get(aPrefix);
   }

   /**
    * @see org.activebpel.rt.xml.IAeNamespaceContext#resolveNamespaceToPrefixes(java.lang.String)
    */
   public Set resolveNamespaceToPrefixes(String aNamespace)
   {
      HashSet set = new HashSet();
      Map map = mPropAlias.getNamespaces();
      
      for (Iterator iter = map.entrySet().iterator(); iter.hasNext(); )
      {
         Map.Entry entry = (Map.Entry) iter.next();
         if (entry.getValue().equals(aNamespace))
         {
            set.add(entry.getKey());
         }
      }
      return set;
   }

}
