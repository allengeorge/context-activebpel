//$Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/xml/IAeNamespaceContext.java,v 1.1 2006/09/27 19:54:3
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

import java.util.Set;

/**
 * This interface defines a standard expression namespace context.  This namespace context
 * is used by expression runners during expression execution.
 */
public interface IAeNamespaceContext
{
   /**
    * Resolves a namespace prefix into a namespace URI.  This method should return null if the namespace
    * could not be found.
    * 
    * @param aPrefix The prefix to resolve.
    */
   public String resolvePrefixToNamespace(String aPrefix);

   /**
    * Resolves a namespace to its list of prefixes (each namespace could be mapped to 0 or more prefix).
    * 
    * @param aNamespace
    */
   public Set resolveNamespaceToPrefixes(String aNamespace);

}
