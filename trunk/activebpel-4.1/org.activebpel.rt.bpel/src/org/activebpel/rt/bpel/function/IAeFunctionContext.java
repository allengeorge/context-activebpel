//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/function/IAeFunctionContext.java,v 1.1 2005/06/08 12:50:2
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


/**
 * Interface for <code>IAeFunctionContext</code> impls.
 */
public interface IAeFunctionContext
{
   /**
    * An implementation should return a <code>IAeFunction</code> implementation object
    * based on the name of the function requested.
    *
    * <p>
    * It must not use the prefix parameter to select an implementation,
    * because a prefix could be bound to any namespace; the prefix parameter
    * could be used in debugging output or other generated information.
    * The prefix may otherwise be completely ignored.
    * </p>
    * 
    * @param aFunctionName The name of the function
    * @return  a IAeFunction implementation object.
    * @throws AeUnresolvableException when the function cannot be found.
    */
   public IAeFunction getFunction(String aFunctionName) throws AeUnresolvableException;

}
