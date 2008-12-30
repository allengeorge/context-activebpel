//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/AeProcessCoordinationStub.java,v 1.5 2006/06/08 19:30:5
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

import org.activebpel.rt.bpel.IAeFault;
import org.activebpel.rt.bpel.coord.AeCoordinationException;
import org.activebpel.rt.bpel.coord.IAeCoordinator;

/**
 *  Void implementation.
 */
public class AeProcessCoordinationStub implements IAeProcessCoordination
{
   /**
    *  Default ctor.
    */
   public AeProcessCoordinationStub()
   {
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessCoordination#registerCoordinationId(long, java.lang.String, java.lang.String)
    */
   public void registerCoordinationId(long aProcessId, String aLocationPath, String aCoordinationId)
         throws AeCoordinationException
   {
      // no-op
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessCoordination#deregisterCoordinationId(long, java.lang.String, java.lang.String)
    */
   public void deregisterCoordinationId(long aProcessId, String aLocationPath, String aCoordinationId)
         throws AeCoordinationException
   {
      // no-op
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessCoordination#activityFaulted(long, java.lang.String, java.lang.String, org.activebpel.rt.bpel.IAeFault)
    */
   public void activityFaulted(long aProcessId, String aLocationPath, String aCoordinationId, IAeFault aFault)
         throws AeCoordinationException
   {
      // no-op
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessCoordination#installCompensationHandler(long, java.lang.String, java.lang.String, org.activebpel.rt.bpel.coord.IAeCoordinator)
    */
   public void installCompensationHandler(long aProcessId, String aLocationPath, String aCoordinationId,
         IAeCoordinator aCoordinator) throws AeCoordinationException
   {
      // no-op
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessCoordination#compensationCompletedCallback(long, java.lang.String, java.lang.String)
    */
   public void compensationCompletedCallback(long aProcessId, String aLocationPath, String aCoordinationId) throws AeCoordinationException
   {
      // no-op
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessCoordination#compensationCompletedWithFaultCallback(long, java.lang.String, java.lang.String, org.activebpel.rt.bpel.IAeFault)
    */
   public void compensationCompletedWithFaultCallback(long aProcessId, String aLocationPath,
         String aCoordinationId, IAeFault aFault) throws AeCoordinationException
   {
      // no-op
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessCoordination#compensateSubProcess(long, java.lang.String, long)
    */
   public void compensateSubProcess(long aProcessId, String aCoordinationId, long aJournalId) throws AeCoordinationException
   {
      // no-op
   }

   /** 
    * Empty method. 
    * 
    * @see org.activebpel.rt.bpel.impl.IAeProcessCoordination#cancelSubProcessCompensation(long)
    */
   public void cancelSubProcessCompensation(long aProcessId) throws AeCoordinationException
   {
      // no-op
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessCoordination#cancelProcess(long)
    */
   public void cancelProcess(long aProcessId) throws AeCoordinationException
   {
      // no-op
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessCoordination#subprocessCoordinationEnded(long)
    */
   public void subprocessCoordinationEnded(long aProcessId) throws AeCoordinationException
   {
      // no-op
   }
}
