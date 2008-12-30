// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/AeActivitySequenceImpl.java,v 1.14 2005/12/06 22:27:0
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
package org.activebpel.rt.bpel.impl.activity;

import java.util.ArrayList;
import java.util.Iterator;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeActivity;
import org.activebpel.rt.bpel.def.activity.AeActivitySequenceDef;
import org.activebpel.rt.bpel.impl.IAeActivityParent;
import org.activebpel.rt.bpel.impl.IAeBpelObject;
import org.activebpel.rt.bpel.impl.visitors.IAeImplVisitor;

/**
 * Implementation of the bpel sequence activity.
 */
public class AeActivitySequenceImpl extends AeActivityImpl implements IAeActivityParent
{
   /** activities to execute in sequence */
   private ArrayList mActivities = new ArrayList();
   
   /** constructor for sequence activity */
   public AeActivitySequenceImpl(AeActivitySequenceDef aActivityDef, IAeActivityParent aParent)
   {
      super(aActivityDef, aParent);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.visitors.IAeVisitable#accept(org.activebpel.rt.bpel.impl.visitors.IAeImplVisitor)
    */
   public void accept( IAeImplVisitor aVisitor ) throws AeBusinessProcessException
   {
      aVisitor.visit(this);
   }
   
   /** Adds an activity definition to the list of activities to execute */
   public void addActivity(IAeActivity aActivity)
   {
      mActivities.add(aActivity);
   }

   /** returns an iterator of activity objects to be executed in sequence */
   public Iterator getChildrenForStateChange()
   {
      return mActivities.iterator();
   }
   
   /**
    * Kicks off the sequence running, we'll queue the first eligible child to
    * execute
    * @see org.activebpel.rt.bpel.impl.IAeExecutableBpelObject#execute()
    */
   public void execute() throws AeBusinessProcessException
   {
      queueNextChild();
   }

   /**
    * Queues the next child to execute or completes if all of the child objects
    * have either executed or reached dead path
    */
   private void queueNextChild() throws AeBusinessProcessException
   {
      // Queues the first activity to execute
      IAeBpelObject child = getNextObject();
      if (child != null)
      {
         getProcess().queueObjectToExecute(child);
      }
      else
      {
         objectCompleted();
      }
   }

   /**
    * The completed child is either FINISHED or DEAD_PATH. We don't care which 
    * case it is, as long as the child complete is the one that we queued to execute.
    * @see org.activebpel.rt.bpel.impl.IAeExecutableBpelObject#childComplete(org.activebpel.rt.bpel.impl.IAeBpelObject)
    */
   public void childComplete(IAeBpelObject aChild) throws AeBusinessProcessException
   {
      queueNextChild();
   }
   
   /**
    * Walks the child array looking for the first non-final object to execute
    */
   protected IAeBpelObject getNextObject()
   {
      for (int i=0; i<mActivities.size(); i++)
      {
         IAeBpelObject nextObject = (IAeBpelObject)mActivities.get(i);
         if ( ! nextObject.getState().isFinal())
         {
            return nextObject;
         }
      }
      return null;
   }
}
