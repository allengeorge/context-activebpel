//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/IAeInvokeHandlerFactory.java,v 1.2 2005/01/19 22:47:5
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
package org.activebpel.rt.bpel.server.engine;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.wsio.invoke.IAeInvoke;
import org.activebpel.wsio.invoke.IAeInvokeHandler;

/**
 *  Factory interface for the creation of <code>IAeInvokeHandler</code> impls.
 */
public interface IAeInvokeHandlerFactory
{
   /**
    * Create an new <code>IAeInvokeHandler</code> instance.
    * @param aInvoke
    * @throws AeBusinessProcessException
    */
   public IAeInvokeHandler createInvokeHandler( IAeInvoke aInvoke ) throws AeBusinessProcessException;
   
   /**
    * Return any custom query data that should be passed into the invoke 
    * handler.  This will be null if none was specified.
    * @param aInvoke
    */
   public String getQueryData( IAeInvoke aInvoke );
}
