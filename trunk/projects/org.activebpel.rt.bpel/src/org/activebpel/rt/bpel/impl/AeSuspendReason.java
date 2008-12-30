//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/AeSuspendReason.java,v 1.2 2006/06/08 20:26:2
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

import org.activebpel.rt.bpel.IAeVariable;

/**
 * Class used to indicate reason for a process being suspended.
 */
public class AeSuspendReason
{
   /** Process was suspended via the administrative console */
   public static final int SUSPEND_CODE_MANUAL = 0;
   /** Process was suspended due to and uncaught fault */
   public static final int SUSPEND_CODE_AUTOMATIC = 1;
   /** Process was suspended due to the suspend activity */
   public static final int SUSPEND_CODE_LOGICAL = 2;   
   /** Process was suspended for internal process migration. */
   public static final int SUSPEND_CODE_MIGRATE = 3;   

   /** The reason the process was suspended */
   private int mReasonCode;
   /** Location path where the process was suspended */
   private String mLocationPath;
   /** Variable from suspend activity */
   private IAeVariable mVariable;
   
   /**
    * Default contructor, used for administrative suspension.
    */
   public AeSuspendReason()
   {
      mReasonCode = SUSPEND_CODE_MANUAL;
   }

   /**
    * Constructor which accepts detail information
    * @param aReasonCode the reason the process was suspened 
    * @param aLocationPath location path where process was suspended (may be null)
    * @param aVariable variable from suspend activity (may be null) 
    */
   public AeSuspendReason(int aReasonCode, String aLocationPath, IAeVariable aVariable)
   {
      mReasonCode = aReasonCode;
      mLocationPath = aLocationPath;
      mVariable = aVariable;
   }

   /**
    * Returns the locationPath, or null if none specified.
    */
   public String getLocationPath()
   {
      return mLocationPath;
   }

   /**
    * Returns the reasonCode for process suspension.
    */
   public int getReasonCode()
   {
      return mReasonCode;
   }

   /**
    * Returns the fault variable responsible for process being suspended.
    */
   public IAeVariable getVariable()
   {
      return mVariable;
   }
}
