//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/function/IAeFunctionContextLocator.java,v 1.1 2005/06/08 12:50:2
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

import org.activebpel.rt.bpel.impl.function.AeInvalidFunctionContextException;

/**
 * Interface for class responsible for locating, loading and instantiating
 * <code>IAeFunctionContext</code> impls.
 */
public interface IAeFunctionContextLocator
{
   /**
    * Find, load, instantiate and return the <code>IAeFunctionContext</code> impl.
    * 
    * @param aNamespace The namespace of the function context.
    * @param aLocation A location hint for locating the <code>IAeFunctionContext</code>class.  This may be null or empty.
    * @param aClassName The class name of the <code>IAeFunctionContext</code> impl.
    * @throws AeInvalidFunctionContextException
    */
   public IAeFunctionContext locate( String aNamespace, String aLocation, String aClassName ) throws AeInvalidFunctionContextException;
}
