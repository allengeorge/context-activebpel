//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/support/AeWSBPELFaultHandler.java,v 1.10 2006/11/03 22:48:0
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

import java.util.Collections;
import java.util.Iterator;

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeFault;
import org.activebpel.rt.bpel.IAeVariable;
import org.activebpel.rt.bpel.def.AeCatchDef;
import org.activebpel.rt.bpel.impl.AeBpelException;
import org.activebpel.rt.bpel.impl.activity.AeActivityScopeImpl;
import org.activebpel.rt.bpel.impl.activity.IAeVariableContainer;
import org.activebpel.rt.bpel.impl.visitors.IAeImplVisitor;
import org.activebpel.rt.message.IAeMessageData;
import org.activebpel.rt.util.AeXmlUtil;
import org.w3c.dom.Document;

/**
 * Impl of fault handler for WS-BPEL 2.0
 */
 // TODO (MF) rename this and base class to AeCatchHandler
public class AeWSBPELFaultHandler extends AeFaultHandler implements IAeVariableContainer
{
   /** locally declared variable */
   private IAeVariable mFaultVariable;

   /**
    * Ctor accepts the def and parent scope
    * @param aDef
    * @param aParent
    */
   public AeWSBPELFaultHandler(AeCatchDef aDef, AeActivityScopeImpl aParent)
   {
      super(aDef, aParent);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.support.AeFaultHandler#accept(org.activebpel.rt.bpel.impl.visitors.IAeImplVisitor)
    */
   public void accept(IAeImplVisitor aVisitor ) throws AeBusinessProcessException
   {
      aVisitor.visit(this);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.support.AeFaultHandler#getFaultVariable()
    */
   public IAeVariable getFaultVariable()
   {
      return mFaultVariable;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.IAeVariableContainer#initialize()
    */
   public void initialize() throws AeBpelException
   {
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.IAeVariableContainer#iterator()
    */
   public Iterator iterator()
   {
      if (getFaultVariable() != null)
      {
         return Collections.singleton(getFaultVariable()).iterator();
      }
      return Collections.EMPTY_LIST.iterator();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.support.AeFaultHandler#findVariable(java.lang.String)
    */
   public IAeVariable findVariable(String aName)
   {
      if (getFaultVariable() != null && getFaultVariable().getDefinition().getName().equals(aName))
      {
         return getFaultVariable();
      }
      return super.findVariable(aName);
   }

   /**
    * Setter for the fault variable
    * @param aVariable
    */
   public void setFaultVariable(IAeVariable aVariable)
   {
      mFaultVariable = aVariable;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.IAeVariableContainer#addVariable(org.activebpel.rt.bpel.IAeVariable)
    */
   public void addVariable(IAeVariable aVariable)
   {
      setFaultVariable(aVariable);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.support.AeFaultHandler#getFaultElementName()
    */
   public QName getFaultElementName()
   {
      return getDef().getFaultElementName();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.support.AeFaultHandler#getFaultMessageType()
    */
   public QName getFaultMessageType()
   {
      return getDef().getFaultMessageType();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.support.AeFaultHandler#setHandledFault(org.activebpel.rt.bpel.IAeFault)
    */
   public void setHandledFault(IAeFault aFault)
   {
      super.setHandledFault(aFault);

      // fixme should clear the fault data when the scope reexecutes
      // TODO (EPW) need some way to not clone the data if we don't need to
      IAeVariable variable = getFaultVariable();
      if (variable != null)
      {
         IAeMessageData messageData = aFault.getMessageData();
         if (variable.getDefinition().isMessageType())
         {
            variable.setMessageData((IAeMessageData) messageData.clone());
         }
         else if (aFault.getElementData() != null)
         {
            variable.setElementData(AeXmlUtil.cloneElement(aFault.getElementData()));
         }
         else if (variable.getDefinition().isElement() && aFault.hasMessageData())
         {
            // special case of matching message fault to element
            String partName = (String) messageData.getPartNames().next();
            Document document = (Document) messageData.getData(partName);
            variable.setElementData(AeXmlUtil.cloneElement(document.getDocumentElement()));
         }
      }
   }
}
