// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/IAeInvokeActivity.java,v 1.1 2007/01/25 21:34:2
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
package org.activebpel.rt.bpel;


/**
 * Describes an invoke object that reports whether it's a one way operation or not.
 */
public interface IAeInvokeActivity
{

   /**
    * Returns the unique location path within the process for this receiver.
    */
   public String getLocationPath();

   /**
    * Returns the unique location id within the process for this receiver.
    */
   public int getLocationId();

   /**
    * Returns true if this invoker is targeting a one-way operation
    */
   public boolean isOneWay();

   /**
    * Returns the transmission id if available otherwise returns <code>0</code>.
    * @return tx id.
    */
   public long getTransmissionId();

   /**
    * Sets the transmission id. Normally, a transmission id is assigned by an durable invoke
    * handler if the <code>IAeInvokeActivity</code> did not already have a
    * transmission id.
    *
    * @param aTransmissionId transmission id used in durable invokes.
    */
   public void setTransmissionId(long aTransmissionId);

   /**
    * @return Returns true if the response receiver is currently in an executing state.
    */
   public boolean isExecuting();

}
