//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/assign/AeVirtualCopyOperation.java,v 1.4 2006/11/16 23:39:0
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
 * Impl of copy operation for <assign> activity used in virtual copy. 
 * 
 * This implementation pairs an impl of a <from> and an impl of a <to> 
 * along with a strategy to handle the copy.  
 */
public class AeVirtualCopyOperation extends AeCopyOperationBase
{
   /**
    * Factory method for creating a virtual copy operation for use in a variable
    * intializer. 
    */
   public static AeVirtualCopyOperation createVariableInitializer()
   {
      return new AeVirtualCopyOperation(false);
   }
   
   /**
    * Factory method for creating a virtual copy operation for use in a fromPart
    * or toPart.
    */
   public static AeVirtualCopyOperation createFromPartToPartOperation()
   {
      return new AeVirtualCopyOperation(true);
   }
   
   /**
    * Constructs copy operation with the given explicit value for the "keep
    * source element name" flag.
    *
    * @param aKeepSrcElementName
    */
   protected AeVirtualCopyOperation(boolean aKeepSrcElementName)
   {
      super(aKeepSrcElementName, false);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.assign.AeCopyOperationBase#isVirtual()
    */
   public final boolean isVirtual()
   {
      return true;
   }
} 
