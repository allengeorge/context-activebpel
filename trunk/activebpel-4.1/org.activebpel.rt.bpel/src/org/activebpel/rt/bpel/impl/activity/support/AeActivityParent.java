// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/support/AeActivityParent.java,v 1.6 2006/10/26 14:01:3
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
package org.activebpel.rt.bpel.impl.activity.support;

import org.activebpel.rt.bpel.IAeActivity;
import org.activebpel.rt.bpel.def.AeBaseDef;
import org.activebpel.rt.bpel.impl.AeAbstractBpelObject;
import org.activebpel.rt.bpel.impl.IAeActivityParent;
import org.activebpel.rt.bpel.impl.IAeBpelObject;

import java.util.Collections;
import java.util.Iterator;

/**
 * Base class for implementation objects that are parents for a single activity.
 */
abstract public class AeActivityParent
   extends AeAbstractBpelObject
   implements IAeActivityParent
{
   /** Child activity */
   protected IAeActivity mChild;

   /**
    * Takes the base def and parent object.
    * @param aDef
    * @param aParent
    */
   public AeActivityParent(AeBaseDef aDef, IAeBpelObject aParent)
   {
      super(aDef, aParent);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeActivityParent#addActivity(org.activebpel.rt.bpel.IAeActivity)
    */
   public void addActivity(IAeActivity aActivity)
   {
      setActivity(aActivity);
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.IAeBpelObject#getChildrenForStateChange()
    */
   public Iterator getChildrenForStateChange()
   {
      return Collections.singleton(getActivity()).iterator();
   }

   /**
    * Getter for the activity.
    */
   public IAeActivity getActivity()
   {
      return mChild;
   }

   /**
    * Setter for the activity
    * @param aActivity
    */
   protected void setActivity(IAeActivity aActivity)
   {
      mChild = aActivity;
   }
}
