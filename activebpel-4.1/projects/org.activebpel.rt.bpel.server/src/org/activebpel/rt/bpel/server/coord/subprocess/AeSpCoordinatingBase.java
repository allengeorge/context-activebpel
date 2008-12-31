//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/coord/subprocess/AeSpCoordinatingBase.java,v 1.1 2005/10/28 21:10:3
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
package org.activebpel.rt.bpel.server.coord.subprocess;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.IAeFault;
import org.activebpel.rt.bpel.coord.IAeCoordinationContext;
import org.activebpel.rt.bpel.coord.IAeProtocolMessage;
import org.activebpel.rt.bpel.impl.IAeCoordinationManagerInternal;
import org.activebpel.rt.bpel.server.coord.AeCoordinatingBase;


/**
 * Base class for subprocess type coordinated activities.
 */
public abstract class AeSpCoordinatingBase extends AeCoordinatingBase
{
   /** process id of participant (or coordinator) where the messages should be sent to. */
   private long mDestinationProcessId = 0;

   /**
    * Default ctor.
    * @param aContext coordination context.
    * @param aCoordinationManager
    */
   public AeSpCoordinatingBase(IAeCoordinationContext aContext, IAeCoordinationManagerInternal aCoordinationManager)
   {
      super(aContext, aCoordinationManager);
   }

   /**
    * Convenience method to create a message given a message 'template'.
    * @param aMessage message template.
    * @return message with the target (destination) pid and location path.
    */
   protected IAeSpProtocolMessage createMessage(IAeProtocolMessage aMessage)
   {
      return createMessage(aMessage, null);
   }   
   
   /**
    * Convenience method to create a message given a message 'template'.
    * @param aMessage message template.
    * @param aFault fault.
    * @return message with the target (destination) pid and location path.
    */   
   protected IAeSpProtocolMessage createMessage(IAeProtocolMessage aMessage, IAeFault aFault)
   {
      IAeSpProtocolMessage msg = new AeSpProtocolMessage(aMessage.getSignal(), 
                  getCoordinationId(), aFault,  getMessageDestinationProcessId(), getMessageDestinationLocationPath());
      return msg;
   }

   /**
    * Returns the process id for which a message should be sent to.
    * This method returns the process id of the participant (subprocess).
    * @return process id of the targeted process.
    */
   protected  long getMessageDestinationProcessId()
   {
      if (mDestinationProcessId == 0)
      {
         try
         {
            mDestinationProcessId = 
               Long.parseLong(getCoordinationContext().getProperty(IAeSpCoordinating.PROTOCOL_DESTINATION_PROCESS_ID));
         }
         catch(Exception e)
         {
            // ignore
            AeException.logError(e,e.getMessage());
         }
      }
      return mDestinationProcessId;
      
   }

   /**
    * Returns the location path for which a message should be sent to. This method returns
    * the path for the participant (subprocess) -  "/process".
    * @return process id of the participant (sub) process.
    */   
   protected String getMessageDestinationLocationPath()
   {
      return getCoordinationContext().getProperty(IAeSpCoordinating.PROTOCOL_DESTINATION_LOCATION_PATH);
   }
   
}
