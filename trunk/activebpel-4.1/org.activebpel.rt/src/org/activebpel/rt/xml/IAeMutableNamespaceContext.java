//$Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/xml/IAeMutableNamespaceContext.java,v 1.4 2007/03/14 14:25:3
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

/**
 * Provides a way of resolving a namespace to a prefix or creating a new ns mapping.
 */
public interface IAeMutableNamespaceContext extends IAeNamespaceContext
{
   /**
    * Gets the prefix mapped to the given namespace or creates a new one. If the preferred
    * prefix is supplied then this will serve as the new prefix mapping or the base of the 
    * mapping if already mapped to another namespace.
    * 
    * Note: By default, this method will return a prefix even if the namespace
    *       matches the default declared namespace. This is useful for encoding
    *       QNames as attribute or element values.
    * 
    * @param aPreferredPrefix - optional preferred prefix
    * @param aNamespace
    */
   public String getOrCreatePrefixForNamespace(String aPreferredPrefix, String aNamespace);

   /**
    * Gets the prefix mapped to the given namespace or creates a new one. If the preferred
    * prefix is supplied then this will serve as the new prefix mapping or the base of the 
    * mapping if already mapped to another namespace.
    * @param aPreferredPrefix - optional preferred prefix
    * @param aNamespace
    * @param aAllowDefaultNamespace - if true, then we'll return "" if the
    *        namespace matches the declared default namespace. If false, then
    *        a prefix will be created even if it matches the default namespace.
    *        This is useful for encoding QNames.
    */
   public String getOrCreatePrefixForNamespace(String aPreferredPrefix, String aNamespace, boolean aAllowDefaultNamespace);
}
 
