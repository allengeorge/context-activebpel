// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/AeStateChangeDetail.java,v 1.4 2004/10/16 16:47:2
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

/**
 * Provides additional information regarding the object's state change. This is
 * currently only used to convey the fault name during an object's entering into
 * the faulted state. In all other cases, we're simply using the NONE static field. 
 */
public class AeStateChangeDetail implements IAeStateChangeDetail
{
   /** constant used to report no additional information regarding the state change */
   public static final IAeStateChangeDetail NONE = new AeStateChangeDetail(null);
   
   /** The name of the fault */
   private String mFaultName;
   
   /** Additional info associated with the fault. */
   private String mAdditionalInfo;
   
   /**
    * Creates a detail object with just the fault name
    * @param aFaultName
    */
   public AeStateChangeDetail(String aFaultName)
   {
      mFaultName = aFaultName;
   }
   
   /**
    * Creates a detail object with the fault name and additional info
    * @param aFaultName
    * @param aInfo
    */
   public AeStateChangeDetail(String aFaultName, String aInfo)
   {
      mFaultName = aFaultName;
      mAdditionalInfo = aInfo ;
   }
   
   /**
    * Return additional information regarding a state change.
    * @see org.activebpel.rt.bpel.impl.IAeStateChangeDetail#getAdditionalInfo()
    */
   public String getAdditionalInfo()
   {
      return mAdditionalInfo;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeStateChangeDetail#getFaultName()
    */
   public String getFaultName()
   {
      return mFaultName;
   }
}
