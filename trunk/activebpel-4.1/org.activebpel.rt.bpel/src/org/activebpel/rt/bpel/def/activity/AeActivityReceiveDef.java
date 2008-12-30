// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/activity/AeActivityReceiveDef.java,v 1.14 2006/09/27 00:36:2
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

import java.util.Collections;
import java.util.Iterator;

import org.activebpel.rt.bpel.def.AeActivityCreateInstanceBaseDef;
import org.activebpel.rt.bpel.def.AeBaseDef;
import org.activebpel.rt.bpel.def.AeVariableDef;
import org.activebpel.rt.bpel.def.IAeFromPartsParentDef;
import org.activebpel.rt.bpel.def.activity.support.AeFromPartsDef;
import org.activebpel.rt.bpel.def.util.AeDefUtil;
import org.activebpel.rt.bpel.def.visitors.IAeDefVisitor;

/**
 * Definition for bpel receive activity.
 */
public class AeActivityReceiveDef extends AeActivityCreateInstanceBaseDef implements IAeReceiveActivityDef,
      IAeFromPartsParentDef, IAeMessageDataConsumerDef
{
   /** The variable. */
   private String mVariable;
   /**  indicates the one-way attribute flag. */
   private boolean mOneWay;
   /** used to match a receive to its reply */
   private String mMessageExchange;
   /** The 'fromParts' child def. */
   private AeFromPartsDef mFromPartsDef;
   /** name of the strategy used to consumer the message data */
   private String mMessageDataConsumerStrategy;

   /**
    * Default constructor
    */
   public AeActivityReceiveDef()
   {
      super();
   }

   /**
    * Accessor method to set the container for the receive element.
    * 
    * @return name of variable
    */
   public String getVariable()
   {
      return mVariable;
   }

   /**
    * Mutator method to set the variable for the receive element.
    * 
    * @param aVariable name of variable
    */
   public void setVariable(String aVariable)
   {
      mVariable = aVariable;
   }

   /**
    * Accessor method to obtain the oneWay flag.
    */
   public boolean isOneWay()
   {
      return mOneWay;
   }

   /**
    * Mutator method to set the one way flag for the activity.
    * 
    * @param aOneWay boolean flag indicating if the instance should be one way.
    */
   public void setOneWay(boolean aOneWay)
   {
      mOneWay = aOneWay;
   }

   /**
    * @see org.activebpel.rt.bpel.def.AeActivityDef#accept(org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void accept(IAeDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }
   
   /**
    * @return Returns the messageExchange.
    */
   public String getMessageExchange()
   {
      return mMessageExchange;
   }

   /**
    * @param aMessageExchange The messageExchange to set.
    */
   public void setMessageExchange(String aMessageExchange)
   {
      mMessageExchange = aMessageExchange;
   }

   /**
    * @see org.activebpel.rt.bpel.def.IAeFromPartsParentDef#setFromPartsDef(AeFromPartsDef)
    */
   public void setFromPartsDef(AeFromPartsDef aDef)
   {
      mFromPartsDef = aDef;
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.IAeFromPartsParentDef#getFromPartsDef()
    */
   public AeFromPartsDef getFromPartsDef()
   {
      return mFromPartsDef;
   }

   /**
    * @see org.activebpel.rt.bpel.def.IAeFromPartsParentDef#getFromPartDefs()
    */
   public Iterator getFromPartDefs()
   {
      if (getFromPartsDef() == null)
         return Collections.EMPTY_LIST.iterator();
      else
         return getFromPartsDef().getFromPartDefs();
   }

   /**
    * @see org.activebpel.rt.bpel.def.activity.IAeMessageDataConsumerDef#getMessageDataConsumerVariable()
    */
   public AeVariableDef getMessageDataConsumerVariable()
   {
      return AeDefUtil.getVariableByName(getVariable(), this);
   }

   /**
    * @see org.activebpel.rt.bpel.def.activity.IAeMessageDataConsumerDef#setMessageDataConsumerStrategy(java.lang.String)
    */
   public void setMessageDataConsumerStrategy(String aStrategy)
   {
      mMessageDataConsumerStrategy = aStrategy;
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.activity.IAeMessageDataConsumerDef#getMessageDataConsumerStrategy()
    */
   public String getMessageDataConsumerStrategy()
   {
      return mMessageDataConsumerStrategy;
   }

   /**
    * @see org.activebpel.rt.bpel.def.activity.IAeReceiveActivityDef#getContext()
    */
   public AeBaseDef getContext()
   {
      return this;
   }
}
