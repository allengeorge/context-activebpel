// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/AeActivityPickImpl.java,v 1.14 2006/07/14 15:46:5
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

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.bpel.def.activity.AeActivityPickDef;
import org.activebpel.rt.bpel.impl.AeBpelState;
import org.activebpel.rt.bpel.impl.IAeActivityParent;
import org.activebpel.rt.bpel.impl.IAeBpelObject;
import org.activebpel.rt.bpel.impl.activity.support.AeBaseEvent;
import org.activebpel.rt.bpel.impl.activity.support.AeEventHandlers;
import org.activebpel.rt.bpel.impl.activity.support.AeOnAlarm;
import org.activebpel.rt.bpel.impl.activity.support.AeOnMessage;
import org.activebpel.rt.bpel.impl.visitors.IAeImplVisitor;
import org.activebpel.rt.util.AeUtil;

import java.util.Iterator;

/**
 * Implementation of the bpel pick activity.
 */
public class AeActivityPickImpl extends AeActivityImpl implements IAeEventParent
{
   /**
    * Container for the messages and alarms.
    */
   private AeEventHandlers mEvents = new AeEventHandlers();
   
   /** default constructor for activity */
   public AeActivityPickImpl(AeActivityPickDef aActivityDef, IAeActivityParent aParent)
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
   
   /**
    * @see org.activebpel.rt.bpel.impl.activity.IAeEventParent#addAlarm(org.activebpel.rt.bpel.impl.activity.support.AeOnAlarm)
    */
   public void addAlarm(AeOnAlarm aAlarm)
   {
      mEvents.addAlarm(aAlarm);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.IAeEventParent#addMessage(org.activebpel.rt.bpel.impl.activity.support.AeOnMessage)
    */
   public void addMessage(AeOnMessage aMessage)
   {
      mEvents.addMessage(aMessage);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeBpelObject#getChildrenForStateChange()
    */
   public Iterator getChildrenForStateChange()
   {
      return AeUtil.join(mEvents.getAlarms(), mEvents.getMessages());
   }

   /**
    * Pick execution queues all child event objects to execute.
    * @see org.activebpel.rt.bpel.impl.IAeExecutableBpelObject#execute()
    */
   public void execute() throws AeBusinessProcessException
   {
      for(Iterator iter=getChildrenForStateChange(); iter.hasNext(); )
      {
         IAeBpelObject bpelObject = (IAeBpelObject)iter.next();
         if (!bpelObject.getState().isFinal())
            getProcess().queueObjectToExecute(bpelObject); 
      }
   }

   /**
    * If not child dead path callback then handle by completing ourselves.
    * @see org.activebpel.rt.bpel.impl.IAeExecutableBpelObject#childComplete(org.activebpel.rt.bpel.impl.IAeBpelObject)
    */
   public void childComplete(IAeBpelObject aChild) throws AeBusinessProcessException
   {
      // if this is not a dead path call back then set our execution as complete
      if( ! AeBpelState.DEAD_PATH.equals(aChild.getState()) )
         objectCompleted();
   }

   /**
    * Handles a child becoming active by setting the non-active children to dead paths.
    * @see org.activebpel.rt.bpel.impl.activity.IAeEventParent#childActive(org.activebpel.rt.bpel.impl.IAeBpelObject)
    */
   public void childActive(IAeBpelObject aChild) throws AeBusinessProcessException
   {
      // just in case throw an exception here
      //
      if(aChild.getState().isFinal())
         throw new AeBusinessProcessException(AeMessages.getString("AeActivityPickImpl.ERROR_0")); //$NON-NLS-1$
         
      // loop through children for non-active children and set to dead path
      for(Iterator iter=getChildrenForStateChange(); iter.hasNext(); )
      {
         IAeBpelObject bpelObject = (IAeBpelObject)iter.next();
         if(bpelObject != aChild)
         {
	        if(bpelObject.getState().isFinal() || ((AeBaseEvent)bpelObject).isActive())
    	        throw new AeBusinessProcessException(AeMessages.getString("AeActivityPickImpl.ERROR_1")); //$NON-NLS-1$
            bpelObject.setState(AeBpelState.DEAD_PATH);
         }
      }
   }
}
