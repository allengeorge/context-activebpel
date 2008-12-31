//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/IAeInvokeInternal.java,v 1.5 2007/01/25 21:34:2
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

import org.activebpel.wsio.invoke.IAeInvoke;

/**
 * Extension of the IAeInvoke.
 */
public interface IAeInvokeInternal extends IAeInvoke
{
   /**
    * Returns true if the underlying BPEL invoke activity is current. That is,
    * it has not moved onto a next iteration (for example in a While loop).
    * 
    * The backed invoke activity is considered current if its transmission id
    * (use as execution iteration reference id) has not changed.
    */
   public boolean isCurrent();

   /**
    * Sets the transmission-id during journal playback in recovery.
    * @param aTransmissionId
    */
   public void setRecoveredTransmissionId(long aTransmissionId);
   
   /**
    * Clean up code to dereference the underlying invoke activity.
    */
   public void dereferenceInvokeActivity();
}
