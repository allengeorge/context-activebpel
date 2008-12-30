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

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.def.visitors.IAeDefVisitor;
import org.activebpel.rt.util.AeUtil;

/**
 * Definition for bpel fault handler element.
 */
public class AeCatchDef extends AeSingleActivityParentBaseDef implements IAeSingleActivityContainerDef, IAeVariableParentDef, IAeFCTHandlerDef
{
   /** The 'faultName' attribute. */
   private QName mFaultName;
   /** The 'faultVariable' attribute. */
   private String mFaultVariable;
   /** The 'faultMessageType' attribute. */
   private QName mFaultMessageType;
   /** The 'faultElement' attribute. */
   private QName mFaultElement;
   /** The variable def for the fault (only applies to BPEL 2.0) */
   private AeVariableDef mFaultVariableDef;
   
   /**
    * Default constructor
    */
   public AeCatchDef()
   {
      super();
   }

   /**
    * @see org.activebpel.rt.bpel.def.faults.IAeCatch#hasFaultVariable()
    */
   public boolean hasFaultVariable()
   {
      return AeUtil.notNullOrEmpty(getFaultVariable());
   }

   /**
    * Accessor method to obtain fault name of this object.
    * 
    * @return the fault name of the object
    */
   public QName getFaultName()
   {
      return mFaultName;
   }

   /**
    * Mutator method to set the fault name of this object.
    * 
    * @param aFaultName the fault name for the object
    */
   public void setFaultName(QName aFaultName)
   {
      mFaultName = aFaultName;
   }

   /**
    * Accessor method to obtain fault variable of this object.
    * 
    * @return the fault variable of the object
    */
   public String getFaultVariable()
   {
      return mFaultVariable;
   }

   /**
    * Mutator method to set the fault variable of this object.
    * 
    * @param aFaultVariable the fault variable for the object
    */
   public void setFaultVariable(String aFaultVariable)
   {
      mFaultVariable = aFaultVariable;
   }

   /**
    * @return Returns the faultElement.
    */
   public QName getFaultElementName()
   {
      return mFaultElement;
   }

   /**
    * @param aFaultElement The faultElement to set.
    */
   public void setFaultElementName(QName aFaultElement)
   {
      mFaultElement = aFaultElement;
   }

   /**
    * @return Returns the faultMessageType.
    */
   public QName getFaultMessageType()
   {
      return mFaultMessageType;
   }

   /**
    * @param aFaultMessageType The faultMessageType to set.
    */
   public void setFaultMessageType(QName aFaultMessageType)
   {
      mFaultMessageType = aFaultMessageType;
   }

   /**
    * @see org.activebpel.rt.bpel.def.AeBaseDef#accept(org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void accept(IAeDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }

   /**
    * Setter for the fault variable def
    * @param aVarDef
    */
   public void setFaultVariableDef(AeVariableDef aVarDef)
   {
      mFaultVariableDef = aVarDef;
      if (mFaultVariableDef != null)
      {
         mFaultVariableDef.setImplicit(true);
      }
   }
   
   /**
    * Getter for the fault variable def
    */
   public AeVariableDef getFaultVariableDef()
   {
      return mFaultVariableDef; 
   }

   /**
    * @see org.activebpel.rt.bpel.def.IAeVariableParentDef#getVariableDef(java.lang.String)
    */
   public AeVariableDef getVariableDef(String aVariableName)
   {
      if (getFaultVariableDef() != null && getFaultVariableDef().getName().equals(aVariableName))
      {
         return getFaultVariableDef();
      }
      return null;
   }
}
