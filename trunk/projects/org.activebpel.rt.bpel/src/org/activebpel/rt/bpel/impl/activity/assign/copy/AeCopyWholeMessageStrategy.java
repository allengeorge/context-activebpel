//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/assign/copy/AeCopyWholeMessageStrategy.java,v 1.2 2006/06/26 16:50:4
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
package org.activebpel.rt.bpel.impl.activity.assign.copy; 

import org.activebpel.rt.bpel.impl.AeBpelException;
import org.activebpel.rt.bpel.impl.activity.assign.AeMismatchedAssignmentException;
import org.activebpel.rt.bpel.impl.activity.assign.IAeCopyOperation;
import org.activebpel.rt.bpel.impl.activity.assign.IAeCopyStrategy;
import org.activebpel.rt.bpel.impl.activity.assign.IAeMessageVariableWrapper;
import org.activebpel.rt.message.IAeMessageData;

/**
 * Copies the whole message variable from one variable to another.
 */
public class AeCopyWholeMessageStrategy implements IAeCopyStrategy
{
   /**
    * @see org.activebpel.rt.bpel.impl.activity.assign.IAeCopyStrategy#copy(org.activebpel.rt.bpel.impl.activity.assign.IAeCopyOperation, java.lang.Object, java.lang.Object)
    */
   public void copy(IAeCopyOperation aCopyOperation, Object aFromData, Object aToData) throws AeBpelException
   {
      IAeMessageVariableWrapper fromMessage = (IAeMessageVariableWrapper) aFromData;
      IAeMessageVariableWrapper toMessage = (IAeMessageVariableWrapper) aToData;
      
      if (fromMessage.getVariable().getMessageType().equals(toMessage.getVariable().getMessageType()))
      {
         IAeMessageData msgDataCopy = (IAeMessageData) fromMessage.getVariable().getMessageData().clone();
         toMessage.getVariable().setMessageData(msgDataCopy);
      }
      else
      {
         throw new AeMismatchedAssignmentException(aCopyOperation.getContext().getBPELNamespace());
      }
   }
}
 
