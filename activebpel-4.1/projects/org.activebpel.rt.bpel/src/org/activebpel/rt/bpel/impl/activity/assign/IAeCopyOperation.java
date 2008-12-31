//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/assign/IAeCopyOperation.java,v 1.4 2006/11/16 23:38:2
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

/**
 * Interface for the standard copy operation implementation.
 */
public interface IAeCopyOperation extends IAeAssignOperation
{
   /**
    * Getter for the copy operation context
    */
   public IAeCopyOperationContext getContext();
   
   /**
    * Setter for the context
    * @param aContext
    */
   public void setContext(IAeCopyOperationContext aContext);
   
   /**
    * Getter for the keepSrcElementName flag
    */
   public boolean isKeepSrcElementName();
   
   /**
    * Returns true if the copy operation is a virtual copy operation. These
    * copy operations exist to implement variable initialization and fromParts
    * and toParts processing. The only difference between these and the ones
    * in <assign> activities are that they do not cause faults for declaring the
    * keepSrcElementName behavior when the copy operation isn't an element to 
    * element style copy.
    */
   public boolean isVirtual();
   
   /**
    * If true, then a <from> that results in zero nodes will cause the <copy>
    * to be a no-op. The <to> MUST NOT be executed.
    */
   public boolean isIgnoreMissingFromData();

}
 
