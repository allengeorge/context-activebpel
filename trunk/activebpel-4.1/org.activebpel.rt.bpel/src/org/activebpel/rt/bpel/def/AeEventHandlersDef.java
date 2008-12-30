// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/AeEventHandlersDef.java,v 1.6 2007/03/03 02:45:3
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
package org.activebpel.rt.bpel.def;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.activebpel.rt.bpel.def.activity.IAeEventContainerDef;
import org.activebpel.rt.bpel.def.activity.support.AeOnAlarmDef;
import org.activebpel.rt.bpel.def.activity.support.AeOnEventDef;
import org.activebpel.rt.bpel.def.visitors.IAeDefVisitor;

/**
 * This is the container for event handlers for a scope.  Event handlers
 * include onMessage and onAlarm activity containers.
 */
public class AeEventHandlersDef extends AeBaseDef 
      implements IAeEventContainerDef, IAeUncrossableLinkBoundary
{
   /** The list of on message definitions, may be null */
   private List mOnEventList = new ArrayList();
   /** The list of on alaram definitions, may be null */
   private List mOnAlarmList = new ArrayList();

   /**
    * Default constructor
    */
   public AeEventHandlersDef()
   {
      super();
   }

   /**
    * @see org.activebpel.rt.bpel.def.activity.IAeEventContainerDef#addOnEventDef(org.activebpel.rt.bpel.def.activity.support.AeOnEventDef)
    */
   public void addOnEventDef(AeOnEventDef aEvent)
   {
      mOnEventList.add(aEvent);
   }

   /**
    * @see org.activebpel.rt.bpel.def.activity.IAeEventContainerDef#getOnEventDefs()
    */
   public Iterator getOnEventDefs()
   {
      return mOnEventList.iterator();
   }

   /**
    * @see org.activebpel.rt.bpel.def.activity.IAeAlarmParentDef#addAlarmDef(org.activebpel.rt.bpel.def.activity.support.AeOnAlarmDef)
    */
   public void addAlarmDef(AeOnAlarmDef aAlarm)
   {
      mOnAlarmList.add(aAlarm);
   }

   /**
    * @see org.activebpel.rt.bpel.def.activity.IAeAlarmParentDef#getAlarmDefs()
    */
   public Iterator getAlarmDefs()
   {
      return mOnAlarmList.iterator();
   }

   /**
    * Returns true if has any onAlarm or onEvent defined.
    */
   public boolean hasEventHandler()
   {
      return getOnEventDefs().hasNext() || getAlarmDefs().hasNext();
   }

   /**
    * @see org.activebpel.rt.bpel.def.AeBaseDef#accept(org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void accept(IAeDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }

   /**
    * @see org.activebpel.rt.bpel.def.IAeUncrossableLinkBoundary#canCrossInbound()
    */
   public boolean canCrossInbound()
   {
      return false;
   }

   /**
    * @see org.activebpel.rt.bpel.def.IAeUncrossableLinkBoundary#canCrossOutbound()
    */
   public boolean canCrossOutbound()
   {
      return false;
   }
}
