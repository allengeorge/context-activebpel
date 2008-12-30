//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/AeBaseDefNamespaceContext.java,v 1.4 2007/03/14 14:29:2
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
package org.activebpel.rt.bpel.def;

import java.util.Iterator;
import java.util.Set;

import org.activebpel.rt.bpel.def.util.AeDefUtil;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.xml.IAeMutableNamespaceContext;

/**
 * A concrete implementation of a namespace context that uses a bpel def object as the namespace info source.
 */
public class AeBaseDefNamespaceContext implements IAeMutableNamespaceContext
{
   /** The base def. */
   private AeBaseDef mBaseDef;
   
   /**
    * Constructor.
    * 
    * @param aDef
    */
   public AeBaseDefNamespaceContext(AeBaseDef aDef)
   {
      setBaseDef(aDef);
   }

   /**
    * @see org.activebpel.rt.xml.IAeNamespaceContext#resolveNamespaceToPrefixes(java.lang.String)
    */
   public Set resolveNamespaceToPrefixes(String aNamespace)
   {
      return AeDefUtil.getPrefixesForNamespace(getBaseDef(), aNamespace);
   }
   
   /**
    * @see org.activebpel.rt.xml.IAeNamespaceContext#resolvePrefixToNamespace(java.lang.String)
    */
   public String resolvePrefixToNamespace(String aPrefix)
   {
      return AeDefUtil.translateNamespacePrefixToUri(getBaseDef(), aPrefix);
   }
   
   /**
    * @see org.activebpel.rt.xml.IAeMutableNamespaceContext#getOrCreatePrefixForNamespace(java.lang.String, java.lang.String)
    */
   public String getOrCreatePrefixForNamespace(String aPreferredPrefix, String aNamespace)
   {
      return getOrCreatePrefixForNamespace(aPreferredPrefix, aNamespace, false);
   }

   /**
    * @see org.activebpel.rt.xml.IAeMutableNamespaceContext#getOrCreatePrefixForNamespace(java.lang.String, java.lang.String, boolean)
    */
   public String getOrCreatePrefixForNamespace(String aPreferredPrefix, String aNamespace, boolean aAllowDefaultNamespace)
   {
      Set prefixes = AeDefUtil.getPrefixesForNamespace(getBaseDef(), aNamespace);
      
      if (!prefixes.isEmpty() && !aAllowDefaultNamespace)
         prefixes.remove(""); //$NON-NLS-1$
      
      // If no prefixes are found, then create a new one based on the preferred prefix.
      if (prefixes.isEmpty())
      {
         return createPrefixForNamespace(aPreferredPrefix, aNamespace);
      }
      else
      {
         String prefix = null;
         for (Iterator iter = prefixes.iterator(); iter.hasNext(); )
         {
            prefix = (String) iter.next();
            if (AeUtil.compareObjects(prefix, aPreferredPrefix))
               break;
         }
         return prefix;
      }
   }
   
   /**
    * Creates a new prefix->namespace mapping using the given preferred prefix and the given
    * namespace.
    * 
    * @param aPreferredPrefix
    * @param aNamespace
    */
   protected String createPrefixForNamespace(String aPreferredPrefix, String aNamespace)
   {
      final String preferredPrefix = AeUtil.isNullOrEmpty(aPreferredPrefix) ? "ns" : aPreferredPrefix ; //$NON-NLS-1$
      String prefix = preferredPrefix;

      String mappedNamespace = resolvePrefixToNamespace(prefix);
      if (!AeUtil.compareObjects(aNamespace, mappedNamespace))
      {
         // it's mapped to something else or isn't mapped at all
         // keep going until mappedNamespace is null
         int index = 0;
         while ( mappedNamespace != null )
         {
            prefix = preferredPrefix + String.valueOf( index++ );
            mappedNamespace = resolvePrefixToNamespace(prefix);
         }

         getBaseDef().addNamespace(prefix, aNamespace);
      }
      return prefix;
   }

   /**
    * @return Returns the baseDef.
    */
   protected AeBaseDef getBaseDef()
   {
      return mBaseDef;
   }

   /**
    * @param aBaseDef The baseDef to set.
    */
   protected void setBaseDef(AeBaseDef aBaseDef)
   {
      mBaseDef = aBaseDef;
   }
}
