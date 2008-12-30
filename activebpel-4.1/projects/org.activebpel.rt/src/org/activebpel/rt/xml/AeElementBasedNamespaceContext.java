// $Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/xml/AeElementBasedNamespaceContext.java,v 1.2 2007/03/14 14:25:3
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
package org.activebpel.rt.xml;

import java.util.HashSet;
import java.util.Set;

import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.w3c.dom.Element;

/**
 * A mutable namespace context implementation that uses a DOM Element to lookup and 
 * create namespace mappings.
 */
public class AeElementBasedNamespaceContext implements IAeMutableNamespaceContext
{
   /** The DOM Element. */
   private Element mElement;

   /**
    * Constructor.
    * 
    * @param aElement
    */
   public AeElementBasedNamespaceContext(Element aElement)
   {
      setElement(aElement);
   }

   /**
    * @return Returns the element.
    */
   protected Element getElement()
   {
      return mElement;
   }

   /**
    * @param aElement The element to set.
    */
   protected void setElement(Element aElement)
   {
      mElement = aElement;
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
      return AeXmlUtil.getOrCreatePrefix(getElement(), aNamespace, aPreferredPrefix, false, aAllowDefaultNamespace);
   }

   /**
    * @see org.activebpel.rt.xml.IAeNamespaceContext#resolvePrefixToNamespace(java.lang.String)
    */
   public String resolvePrefixToNamespace(String aPrefix)
   {
      return AeXmlUtil.getNamespaceForPrefix(getElement(), aPrefix);
   }

   /**
    * @see org.activebpel.rt.xml.IAeNamespaceContext#resolveNamespaceToPrefixes(java.lang.String)
    */
   public Set resolveNamespaceToPrefixes(String aNamespace)
   {
      // TODO (EPW) improve this implementation to actually return all of the prefixes.
      Set rval = new HashSet();
      String prefix = AeXmlUtil.getPrefixForNamespace(getElement(), aNamespace);
      if (AeUtil.notNullOrEmpty(prefix))
      {
         rval.add(prefix);
      }
      return rval;
   }
}
