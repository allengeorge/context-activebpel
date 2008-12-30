//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/coord/AeProtocolState.java,v 1.1 2005/10/28 21:10:3
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
package org.activebpel.rt.bpel.server.coord;

import org.activebpel.rt.bpel.coord.IAeProtocolState;

/**
 * Implementation of the coordination protocol state.
 */
public class AeProtocolState implements IAeProtocolState
{
   /**
    * state.
    */
   private String mState = null;
   
   /**
    * Constructs the state given the state. 
    */
   public AeProtocolState(String aState)
   {
      mState = aState;
   }

   /**
    * Overrides method to 
    * @see org.activebpel.rt.bpel.coord.IAeProtocolState#getState()
    */
   public String getState()
   {
      return mState;
   }

   /**
    * Returns true of the state of the IAeProtocolState being compared to is the same
    * as the state of this instance.
    */
   public boolean equals(Object other)
   {
      if (other != null && other instanceof IAeProtocolState)
      {
         return getState().equalsIgnoreCase( (( IAeProtocolState)other).getState() );
      }
      else
      {
         return false;
      }
   }
   
   /** 
    * Overrides method to return the state. 
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return getState();
   }
}
