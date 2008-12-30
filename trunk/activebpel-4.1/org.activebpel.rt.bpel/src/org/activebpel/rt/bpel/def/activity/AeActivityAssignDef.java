// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/activity/AeActivityAssignDef.java,v 1.5 2006/06/26 16:50:3
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
package org.activebpel.rt.bpel.def.activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.activebpel.rt.bpel.def.AeActivityDef;
import org.activebpel.rt.bpel.def.activity.support.AeAssignCopyDef;
import org.activebpel.rt.bpel.def.activity.support.AeExtensibleAssignDef;
import org.activebpel.rt.bpel.def.visitors.IAeDefVisitor;

/**
 * Definition for bpel assign activity.
 */
public class AeActivityAssignDef extends AeActivityDef
{
   /** The assign's copy def children. */
   private List mCopies = new ArrayList();
   /** The assign's extensibleAssign children. */
   private List mExtensibleAssigns = new ArrayList();
   /** The assign's 'validate' attribute. */
   private boolean mValidate;

   /**
    * Default constructor
    */
   public AeActivityAssignDef()
   {
      super();
   }

   /**
    * Provides the ability to add a copy element to the assign element.
    *
    * @param aCopy copy element to be added
    */
   public void addCopyDef(AeAssignCopyDef aCopy)
   {
      mCopies.add(aCopy);
   }

   /**
    * Provide a list of the Copy objects for the user to iterate .
    *
    * @return iterator of AeAssignCopyDef objects
    */
   public Iterator getCopyDefs()
   {
      return mCopies.iterator();
   }

   /**
    * @return Returns the validate.
    */
   public boolean isValidate()
   {
      return mValidate;
   }

   /**
    * @param aValidate The validate to set.
    */
   public void setValidate(boolean aValidate)
   {
      mValidate = aValidate;
   }

   /**
    * @return Returns the extensibleAssigns.
    */
   public Iterator getExtensibleAssignDefs()
   {
      return mExtensibleAssigns.iterator();
   }

   /**
    * @param aDef The extensibleAssigns to set.
    */
   public void addExtensibleAssignDef(AeExtensibleAssignDef aDef)
   {
      mExtensibleAssigns.add(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.AeActivityDef#accept(org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void accept(IAeDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }
}
