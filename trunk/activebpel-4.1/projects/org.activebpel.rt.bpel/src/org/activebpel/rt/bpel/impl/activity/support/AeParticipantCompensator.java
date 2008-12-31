//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/support/AeParticipantCompensator.java,v 1.4 2006/06/26 16:50:3
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
package org.activebpel.rt.bpel.impl.activity.support;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeFault;
import org.activebpel.rt.bpel.coord.IAeCoordinationManager;
import org.activebpel.rt.bpel.coord.IAeParticipant;
import org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal;
  
/**
 * AeParticipantCompensator implements an 'implicit' compensate activity
 * for the process under coordination. 
 */
public class AeParticipantCompensator implements IAeCompensationCallback
{
   /**
    * Coordination id.
    */
   private String mCoordinationId;
   
   /** Location path of this compensation activity. **/
   private String mLocationPath;
   
   /** Business process engine */
   private IAeBusinessProcessEngineInternal mEngine;
   
   /**
    *  Default ctor.
    */
   public AeParticipantCompensator(String aCoordinationId, IAeBusinessProcessEngineInternal aEngine)
   {
      mCoordinationId = aCoordinationId;
      setEngine( aEngine );
   }
      
   /**
    * @return Returns the engine.
    */
   protected IAeBusinessProcessEngineInternal getEngine()
   {
      return mEngine;
   }
   /**
    * @param aEngine The engine to set.
    */
   protected void setEngine(IAeBusinessProcessEngineInternal aEngine)
   {
      mEngine = aEngine;
   }
   /**
    * Returns the coordination context id.
    */
   public String getCoordinationId()
   {
      return mCoordinationId;
   }

   /** 
    * Returns true if this is a coordinated comp info. This is normally used by
    * during saving and restoring state information.
    * @return true if this is a coordinated comp info.
    */
   public boolean isCoordinated(){ 
      return true;
   }   
       
   /**
    * @return Returns the participant.
    */
   protected IAeParticipant getParticipant() throws AeBusinessProcessException
   {
      // get the coordination manager.
      try
      {
         IAeCoordinationManager manager = getEngine().getCoordinationManager();
         IAeParticipant participant = manager.getParticipant(getCoordinationId());
         return participant;
      }
      catch(Throwable t)
      {
         throw new AeBusinessProcessException(t.getMessage(), t);
      }
   }
   
   /**
    * Called by the participant process's compensationHandler when its compensation is complete.
    * @param aCompHandler
    */
   public void compensationComplete(AeCompensationHandler aCompHandler) throws AeBusinessProcessException
   {
      // Any clean up do be done?
      // notify coordinator with COMPENSATED message.
      getParticipant().compensationComplete();
   }

   /**
    * Called by the participant process's compensationHandler when its compensation was interrupted by a fault.
    * @param aCompHandler
    * @param aFault
    */
   public void compensationCompleteWithFault(AeCompensationHandler aCompHandler, IAeFault aFault) throws AeBusinessProcessException
   {
      // Any clean up do be done?     
      // notify coordinator with FAULT_COMPENSATED message.
      getParticipant().compensationCompleteWithFault(aFault);      
   }
   
   /** 
    * @see org.activebpel.rt.bpel.impl.activity.support.IAeCompensationCallback#compensationTerminated(org.activebpel.rt.bpel.impl.activity.support.AeCompensationHandler)
    */
   public void compensationTerminated(AeCompensationHandler aCompHandler) throws AeBusinessProcessException
   {
      // subprocess's instance compHandler was terminated.
      compensationCompleteWithFault(aCompHandler, aCompHandler.getFaultFactory().getCoordinatedCompensationTerminated() );
      
   }
   /**
    * Returns the location path for the callback object. This will be used to
    * identify the callback object in the event that the process is asked to
    * persist itself.
    */
   public String getLocationPath()
   {
      if (mLocationPath == null)
      {
         mLocationPath = AeSupportActivityLocationPathSuffix.COORDINATION_COMPENSATE_ACTIVITY;
      }
      return mLocationPath;
   }

   /**
    * Sets the location path for the callback object. Normally set during deserialization.
    */
   public void setLocationPath(String aLocationPath)
   {
      mLocationPath = aLocationPath;
   }   
  
}

