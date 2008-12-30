//$Header: /Development/AEDevelopment/projects/org.activebpel.wsio/src/org/activebpel/wsio/invoke/IAeTwoPhaseInvokeHandler.java,v 1.1 2006/05/25 00:06:5
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
package org.activebpel.wsio.invoke;

/**
 * <p>
 * Extends the invoke handler such that the invocation process
 * is comprised of two parts - prepare followed by handleInvoke.
 * </p>
 * The prepare method is first called by the engine, and if this
 * method returns true, then the handleInvoke method is called,
 * aynchronous to the current execution thread. 
 *
 */
public interface IAeTwoPhaseInvokeHandler extends IAeInvokeHandler
{
   /**
    * <p>
    * Lets the handler prepare for the invoke. This method is called prior
    * to the handleInvoke method. If this method returns true, then the handleInvoke
    * will be called. 
    * </p>
    * <p>
    * This method is called in the same execution thread as the invoke activity.
    * How ever, the handleInvoke may be called asynchronously.
    * </p> 
    * @param aInvoke web service invoke. 
    * @param aQueryData.
    * @return true if successful.
    */
   public boolean prepare(IAeInvoke aInvoke, String aQueryData ) throws AeInvokePrepareException;
}
