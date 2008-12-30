// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/activity/support/AeAssignCopyDef.java,v 1.11 2006/11/16 23:34:1
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
package org.activebpel.rt.bpel.def.activity.support;

import org.activebpel.rt.bpel.def.AeBaseDef;
import org.activebpel.rt.bpel.def.IAeFromParentDef;
import org.activebpel.rt.bpel.def.visitors.IAeDefVisitor;

/**
 * Holds individual copy operations of an assign activity
 */
public class AeAssignCopyDef extends AeBaseDef implements IAeFromParentDef
{
   /** The copy's 'from' construct. */
   private AeFromDef mFrom;
   /** The copy's 'to' construct. */
   private AeToDef mTo;
   /** The copy's 'keepSrcElementName' attribute. */
   private boolean mKeepSrcElementName;
   /** The copy's 'ignoreMissingFromData' attribute  */
   private boolean mIgnoreMissingFromData;
   
   /**
    * Default constructor
    */
   public AeAssignCopyDef()
   {
      super();
   }

   /**
    * @see org.activebpel.rt.bpel.def.AeBaseDef#accept(org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public final void accept(IAeDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }

   /**
    * Accessor method to obtain the From assignment part of the Copy activity.
    * 
    * @return the From assignment object
    */
   public final AeFromDef getFromDef()
   {
      return mFrom;
   }

   /**
    * Mutator method to set the From assignment part of the Copy activity.
    * 
    * @param aFrom the From part of the Copy activity
    */
   public final void setFromDef(AeFromDef aFrom)
   {
      mFrom = aFrom;
   }

   /**
    * Accessor method to obtain the To assignment part of the Copy activity.
    * 
    * @return the To assignment object
    */
   public final AeToDef getToDef()
   {
      return mTo;
   }

   /**
    * Mutator method to set the To assignment part of the Copy activity.
    * 
    * @param aTo the To part of the Copy activity
    */
   public final void setToDef(AeToDef aTo)
   {
      mTo = aTo;
   }

   /**
    * @return Returns the keepSrcElementName.
    */
   public final boolean isKeepSrcElementName()
   {
      return mKeepSrcElementName;
   }

   /**
    * @param aKeepSrcElementName The keepSrcElementName to set.
    */
   public final void setKeepSrcElementName(boolean aKeepSrcElementName)
   {
      mKeepSrcElementName = aKeepSrcElementName;
   }

   /**
    * @return the ignoreMissingFromData
    */
   public final boolean isIgnoreMissingFromData()
   {
      return mIgnoreMissingFromData;
   }

   /**
    * @param aIgnoreMissingFromData the ignoreMissingFromData to set
    */
   public void setIgnoreMissingFromData(boolean aIgnoreMissingFromData)
   {
      mIgnoreMissingFromData = aIgnoreMissingFromData;
   }
}
