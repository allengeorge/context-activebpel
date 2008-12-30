//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/coord/AeProtocolMessage.java,v 1.1 2005/10/28 21:10:3
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

import org.activebpel.rt.bpel.IAeFault;
import org.activebpel.rt.bpel.coord.IAeProtocolMessage;

/**
 * Basic implementation of the coordination protocol message.
 */
public class AeProtocolMessage implements IAeProtocolMessage
{
   /**
    * The command or signal of this message.
    */
   private String mSignal = null;
   
   /**
    * Coordination id.
    */
   private String mCoordinationId = null;
   
   /**
    * Fault data.
    */
   private IAeFault mFault = null;
   
   
   /**
    * Constructs the object using protocol signal from the given message. 
    */
   public AeProtocolMessage(IAeProtocolMessage aMessage)
   {
      this(aMessage.getSignal(), null, null);
   }

   /**
    * Constructs the object given the protocol signal. 
    */
   public AeProtocolMessage(String aSignal)
   {
      this(aSignal, null, null);
   }

   /**
    * Constucts a message given signal, coordination id and fault.
    * @param aSignal
    * @param aCoordinationId
    * @param aFault
    */
   public AeProtocolMessage(String aSignal, String aCoordinationId, IAeFault aFault)
   {
      setSignal(aSignal);
      setCoordinationId(aCoordinationId);
      setFault(aFault);
   }
   
   /**
    * Overrides method to 
    * @see org.activebpel.rt.bpel.coord.IAeProtocolMessage#getSignal()
    */
   public String getSignal()
   {
      return mSignal;
   }
   
   /**
    * Sets the protocol signal.
    * @param aSignal
    */
   protected void setSignal(String aSignal)
   {
      mSignal = aSignal;
   }
   
   /** 
    * @return Returns the coordination id.
    */
   public String getCoordinationId()
   {
      return mCoordinationId;
   }
   
   /**
    * Sets the coordination id.
    * @param aId
    */
   protected void setCoordinationId(String aId)
   {
      mCoordinationId = aId;
   }
   
   /**
    * @return Returns the fault if any or null otherwise.
    */
   public IAeFault getFault()
   {
      return mFault;
   }
   
   /**
    * Sets the fault data.
    * @param aFault
    */
   protected void setFault(IAeFault aFault)
   {
      mFault = aFault;
   }
   
   /** 
    * Returns true if the signal of the IAeProtocolMessage being 
    * compared with equals this instance's signal.
    * 
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equalsSignal(IAeProtocolMessage aOther)
   {
      if (aOther != null)
      {
         return getSignal().equalsIgnoreCase(aOther.getSignal() );
      }
      else
      {
         return false;
      }
   }   

   /** 
    * Overrides method to the message signal. 
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return getSignal();
   }
}
