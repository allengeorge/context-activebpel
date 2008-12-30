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

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.def.AeBaseDef;
import org.activebpel.rt.bpel.def.AePartnerLinkDef;
import org.activebpel.rt.bpel.def.AeVariableDef;
import org.activebpel.rt.bpel.def.activity.AeActivityScopeDef;
import org.activebpel.rt.bpel.def.util.AeDefUtil;
import org.activebpel.rt.bpel.def.visitors.IAeDefVisitor;

/**
 * Models the 'onEvent' bpel construct introduced in WS-BPEL 2.0.
 */
public class AeOnEventDef extends AeOnMessageDef
{
   /** The 'messageType' attribute. */
   private QName mMessageType;
   /** The 'element' attribute. */
   private QName mElement;

   /**
    * Default c'tor.
    */
   public AeOnEventDef()
   {
      super();
   }

   /**
    * @return Returns the element.
    */
   public QName getElement()
   {
      return mElement;
   }

   /**
    * @param aElement The element to set.
    */
   public void setElement(QName aElement)
   {
      mElement = aElement;
   }

   /**
    * @return Returns the messageType.
    */
   public QName getMessageType()
   {
      return mMessageType;
   }

   /**
    * @param aMessageType The messageType to set.
    */
   public void setMessageType(QName aMessageType)
   {
      mMessageType = aMessageType;
   }

   /**
    * @see org.activebpel.rt.bpel.def.activity.support.AeOnMessageDef#accept(org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void accept(IAeDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }

   /**
    * Returns the <code>onEvent</code>'s scope definition. 
    */
   public AeActivityScopeDef getChildScope()
   {
      // fixme (MF) not safe for bpws 1.1 but only called by ws-bpel classes at this point
      if (getActivityDef() instanceof AeActivityScopeDef)
      {
         return (AeActivityScopeDef) getActivityDef(); 
      }
      return null;
   }

   /**
    * Overrides in order to resolve the variable to the associated scope as opposed
    * to the anscestor scopes.
    * @see org.activebpel.rt.bpel.def.activity.support.AeOnMessageDef#getMessageDataConsumerVariable()
    */
   public AeVariableDef getMessageDataConsumerVariable()
   {
      return AeDefUtil.getVariableByName(getVariable(), getContext());
   }

   /**
    * @see org.activebpel.rt.bpel.def.activity.support.AeOnMessageDef#getPartnerLinkDef()
    */
   public AePartnerLinkDef getPartnerLinkDef()
   {
      AePartnerLinkDef plinkDef = AeDefUtil.findPartnerLinkDef(getContext(), getPartnerLink());
      return plinkDef;
   }

   /**
    * @see org.activebpel.rt.bpel.def.activity.support.AeOnMessageDef#getContext()
    */
   public AeBaseDef getContext()
   {
   // fixme (MF) come back to this and create separate def class for ws-bpel onEvent
      if (getActivityDef() == null)
      {
         return this;
      }
      else
      {
         if (AeDefUtil.isBPWS(this))
         {
            return this;
         }
         else
         {
            return getActivityDef();
         }
      }
   }
}
