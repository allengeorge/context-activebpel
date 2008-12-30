// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/AeActivityThrowImpl.java,v 1.14 2006/09/22 19:52:3
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
import org.activebpel.rt.bpel.IAeVariable;
import org.activebpel.rt.bpel.def.activity.AeActivityThrowDef;
import org.activebpel.rt.bpel.impl.IAeActivityParent;
import org.activebpel.rt.bpel.impl.activity.support.AeFault;
import org.activebpel.rt.bpel.impl.visitors.IAeImplVisitor;
import org.activebpel.rt.util.AeUtil;
import org.w3c.dom.Element;

/**
 * Implementation of the bpel throw activity.
 */
public class AeActivityThrowImpl extends AeActivityImpl
{
   /** default constructor for activity */
   public AeActivityThrowImpl(AeActivityThrowDef aActivityDef, IAeActivityParent aParent)
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
    * @see org.activebpel.rt.bpel.impl.IAeExecutableBpelObject#execute()
    */
   public void execute() throws AeBusinessProcessException
   {
      AeActivityThrowDef def = (AeActivityThrowDef) getDefinition();
      AeFault fault = null;
      if(getFaultVariable() != null)
      {
         if (getFaultVariable().isMessageType())
         {
            fault = new AeFault(def.getFaultName(), getFaultVariable().getMessageData()); 
         }
         else
         {
            fault = new AeFault(def.getFaultName(), getFaultVariable().getElementData());
         }
      }
      else
      {
         fault = new AeFault(def.getFaultName(), (Element)null);
      }
      objectCompletedWithFault(fault);
   }
   
   /**
    * Returns the variable for use in the throw or null if one wasn't part of the
    * definition.
    */
   private IAeVariable getFaultVariable()
   {
      AeActivityThrowDef def = (AeActivityThrowDef) getDefinition();
      String variableName = def.getFaultVariable();
      if ( ! AeUtil.isNullOrEmpty(variableName))
      {
         return findVariable(variableName);
      }
      return null;
   }

}
