//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/function/IAeFunctionFactory.java,v 1.1 2005/06/08 12:50:2
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
package org.activebpel.rt.bpel.function;

import java.util.List;

/**
 * This interface represents a factory that will create instances of IAeFunction from a
 * given namespace and function name.  Typically an implementation will contain multiple
 * function contexts, each function context mapped to one or more namespaces.  A call
 * to getFunction will then look up the correct function context based on the namespace 
 * and then ask that context for the function.  In addition, this interface can be used
 * to enumerate all of the namespaces for which it has function contexts.
 */
public interface IAeFunctionFactory
{
   /**
    * Looks up a function given the namespace and function name.  The impl should locate a function
    * context that is configured for the given namespace and then ask that context for a function of
    * the given name.  Throws an unresolvable exception only if the context is found but there is no
    * function in the context with the given name.  If no context is found, then null is returned.
    * 
    * @param aNamespace
    * @param aFunctionName
    * @throws AeUnresolvableException only if the context is found but there is no function in the context with the given name
    */
   public IAeFunction getFunction(String aNamespace, String aFunctionName) throws AeUnresolvableException;

   /**
    * Gets a list of all namespaces for which function contexts are configured.  A list of Strings is
    * returns (each namespace is a String in the List).
    */
   public List getFunctionContextNamespaceList();
   
}
