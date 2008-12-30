// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/assign/from/AeFromBase.java,v 1.5 2007/05/24 00:50:3
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
package org.activebpel.rt.bpel.impl.activity.assign.from; 

import org.activebpel.rt.attachment.IAeAttachmentContainer;
import org.activebpel.rt.bpel.IAeVariable;
import org.activebpel.rt.bpel.def.activity.support.AeFromDef;
import org.activebpel.rt.bpel.impl.activity.assign.AeCopyOperationComponentBase;
import org.activebpel.rt.bpel.impl.activity.assign.IAeFrom;

/**
 * Base class for objects implementing the selection of data for a <from> construct 
 */
public abstract class AeFromBase extends AeCopyOperationComponentBase implements IAeFrom
{
   /**
    * Ctor for the base accepts def
    */
   public AeFromBase(AeFromDef aDef)
   {
      setVariableName(aDef.getVariable());
   }
   
   /**
    * No arg ctor 
    */
   protected AeFromBase()
   {
   }

   /**
    * Getter for the variable
    */
   protected IAeVariable getVariable()
   {
      return getCopyOperation().getContext().getVariable(getVariableName());
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.activity.assign.IAeFrom#getAttachmentsSource()
    */
   public IAeAttachmentContainer getAttachmentsSource()
   {
      // Default behaviour
      return null;
   }
}
 
