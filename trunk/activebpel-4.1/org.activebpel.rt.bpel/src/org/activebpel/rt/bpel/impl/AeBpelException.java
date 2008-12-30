// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/AeBpelException.java,v 1.7 2006/06/26 16:50:2
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

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeFault;
import org.activebpel.rt.util.AeUtil;

/**
 * An exception which is created as a result of a BPEL execution failure.
 */
public class AeBpelException extends AeBusinessProcessException
{
   /** The BPEL fault which was created */
   private IAeFault mFault;
   
   /**
    * Constructs a new BPEL exception with the given info and fault.
    * @param aInfo informational message  
    * @param aFault the BPEL fault responsible for this exception
    */
   public AeBpelException(String aInfo, IAeFault aFault)
   {
      super(aInfo);
      
      mFault = aFault;
      mFault.setInfo( aInfo );
   }
   
   /**
    * Constructs a new BPEL exception with the info, fault, and a throwable
    * who's stacktrace will be included in the fault.
    * 
    * @param aInfo
    * @param aFault
    * @param aThrowable
    */
   public AeBpelException(String aInfo, IAeFault aFault, Throwable aThrowable)
   {
      super(aInfo);
      mFault = aFault;
      mFault.setInfo(aInfo);
      if (aThrowable != null)
         mFault.setDetailedInfo(AeUtil.getStacktrace(aThrowable));
   }

   /**
    * Returns the BPEL fault which was the cause of this exception.
    */
   public IAeFault getFault()
   {
      return mFault;
   }
}
