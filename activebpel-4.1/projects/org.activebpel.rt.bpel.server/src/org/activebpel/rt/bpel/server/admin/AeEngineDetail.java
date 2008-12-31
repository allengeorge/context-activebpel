//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/admin/AeEngineDetail.java,v 1.3 2007/08/15 21:02:0
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
package org.activebpel.rt.bpel.server.admin;

import java.io.Serializable;
import java.util.Date;

/**
 * This class contains details about a currently running engine.
 */
public class AeEngineDetail implements Serializable
{
   /** The current engine state. */
   protected int mState;
   /** The current monitor status. */
   protected int mMonitorStatus;
   /** The error message if the state of the engine is "ERROR". */
   protected String mErrorMessage;
   /** The current engine's start time (null if stopped). */
   protected Date mStartTime;

   /**
    * Default constructor.
    */
   public AeEngineDetail()
   {
      mErrorMessage = ""; //$NON-NLS-1$
   }

   /**
    * Getter for the engine state property.
    */
   public int getState()
   {
      return mState;
   }
   
   /**
    * Setter for the engine state.
    * 
    * @param aEngineState The engine state.
    */
   public void setState(int aEngineState)
   {
      mState = aEngineState;
   }

   /**
    * Getter for the monitor status property.
    */
   public int getMonitorStatus()
   {
      return mMonitorStatus;
   }
   
   /**
    * Setter for the monitor status.
    * 
    * @param aMonitorStatus The monitor status
    */
   public void setMonitorStatus(int aMonitorStatus)
   {
      mMonitorStatus = aMonitorStatus;
   }

   /**
    * Getter for the engine start time property.
    */
   public Date getStartTime()
   {
      return mStartTime;
   }
   
   /**
    * Setter for the engine start time property.
    * 
    * @param aStartTime The engine start time.
    */
   public void setStartTime(Date aStartTime)
   {
      mStartTime = aStartTime;
   }

   /**
    * Getter for the engine error message property.
    */
   public String getErrorMessage()
   {
      return mErrorMessage;
   }

   /**
    * Setter for the engine error message property.
    */
   public void setErrorMessage(String aErrorMessage)
   {
      mErrorMessage = aErrorMessage;
   }
}
