//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/assign/AeMismatchedAssignmentException.java,v 1.3 2006/09/20 22:12:0
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
package org.activebpel.rt.bpel.impl.activity.assign; 

import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.bpel.impl.AeBpelException;
import org.activebpel.rt.bpel.impl.AeFaultFactory;

/**
 * Exception for the standard BPEL fault bpel:mismatchedAssignmentFailure
 */
public class AeMismatchedAssignmentException extends AeBpelException
{
   /** Error message for the exception */
   private static final String ERROR_MESSAGE = AeMessages.getString("AeMismatchedAssignmentException.Message"); //$NON-NLS-1$

   /**
    * Creates the exception with a bpws:mismatchedAssignmentFailure fault. 
    */
   public AeMismatchedAssignmentException(String aNamespace)
   {
      this(aNamespace, null);
   }
   
   /**
    * Creates the exception with a bpws:mismatchedAssignmentFailure fault. 
    */
   public AeMismatchedAssignmentException(String aNamespace, Throwable aThrowable)
   {
      super(ERROR_MESSAGE, AeFaultFactory.getFactory(aNamespace).getMismatchedAssignmentFailure(), aThrowable);
   }
}
 
