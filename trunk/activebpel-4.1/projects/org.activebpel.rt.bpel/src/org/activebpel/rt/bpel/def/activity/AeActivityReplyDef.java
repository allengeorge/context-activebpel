// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/activity/AeActivityReplyDef.java,v 1.9 2006/08/18 22:20:3
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

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.def.AeActivityPartnerLinkBaseDef;
import org.activebpel.rt.bpel.def.AeVariableDef;
import org.activebpel.rt.bpel.def.IAeToPartsParentDef;
import org.activebpel.rt.bpel.def.activity.support.AeToPartsDef;
import org.activebpel.rt.bpel.def.util.AeDefUtil;
import org.activebpel.rt.bpel.def.visitors.IAeDefVisitor;

/**
 * Definition for bpel reply activity.
 */
public class AeActivityReplyDef extends AeActivityPartnerLinkBaseDef implements IAeToPartsParentDef, IAeMessageDataProducerDef
{
   /** The variable attribute. */
   private String mVariable;
   /** The fault name attribute. */
   private QName mFaultName;
   /** The message exchange attribute. */
   private String mMessageExchange;
   /** The 'toParts' child def. */
   private AeToPartsDef mToPartsDef;
   /** The strategy hint for producing the message data for the reply */
   private String mMessageDataProducerStrategy;

   /**
    * Default constructor
    */
   public AeActivityReplyDef()
   {
      super();
   }

   /**
    * Accessor method to obtain the name of the variable associated with this activity.
    * 
    * @return name of the variable
    */
   public String getVariable()
   {
      return mVariable;
   }

   /**
    * Mutator method to set the name of the variable for this activity.
    * 
    * @param aVariable name of variable
    */
   public void setVariable(String aVariable)
   {
      mVariable = aVariable;
   }

   /**
    * Accessor method to obtain the name of the fault associated with this activity.
    * 
    * @return name of the fault
    */
   public QName getFaultName()
   {
      return mFaultName;
   }

   /**
    * Mutator method to set the name of the fault for this activity.
    * 
    * @param aFaultName name of fault
    */
   public void setFaultName(QName aFaultName)
   {
      mFaultName = aFaultName;
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
    * @see org.activebpel.rt.bpel.def.IAeFromPartsParentDef#getFromPartDefs()
    */
   public Iterator getToPartDefs()
   {
      if (getToPartsDef() == null)
         return Collections.EMPTY_LIST.iterator();
      else
         return getToPartsDef().getToPartDefs();
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.IAeToPartsParentDef#getToPartsDef()
    */
   public AeToPartsDef getToPartsDef()
   {
      return mToPartsDef;
   }

   /**
    * @see org.activebpel.rt.bpel.def.IAeToPartsParentDef#setToPartsDef(AeToPartsDef)
    */
   public void setToPartsDef(AeToPartsDef aDef)
   {
      mToPartsDef = aDef;
   }

   /**
    * @see org.activebpel.rt.bpel.def.activity.IAeMessageDataProducerDef#getMessageDataProducerVariable()
    */
   public AeVariableDef getMessageDataProducerVariable()
   {
      return AeDefUtil.getVariableByName(getVariable(), this);
   }

   /**
    * @see org.activebpel.rt.bpel.def.activity.IAeMessageDataProducerDef#getMessageDataProducerStrategy()
    */
   public String getMessageDataProducerStrategy()
   {
      return mMessageDataProducerStrategy;
   }

   /**
    * @param aMessageDataProducerStrategy The messageDataProducerStrategy to set.
    */
   public void setMessageDataProducerStrategy(String aMessageDataProducerStrategy)
   {
      mMessageDataProducerStrategy = aMessageDataProducerStrategy;
   }
}
