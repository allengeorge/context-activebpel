//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/assign/from/AeFromVariableMessagePart.java,v 1.6 2007/06/09 01:06:3
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
package org.activebpel.rt.bpel.impl.activity.assign.from; 

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeVariable;
import org.activebpel.rt.bpel.def.activity.support.AeFromDef;
import org.w3c.dom.Document;

/**
 * Handles selecting data from a variable part. The data will either be a xsd simple type,
 * element, or complex type.
 */
public class AeFromVariableMessagePart extends AeFromBase
{
   /** message part name */
   private String mPart;

   /** variable */
   private IAeVariable mVariable;

   /**
    * Ctor accepts the def object
    * 
    * @param aFromDef
    */
   public AeFromVariableMessagePart(AeFromDef aFromDef)
   {
      super(aFromDef);
      setPart(aFromDef.getPart());
   }
   
   /**
    * Ctor accepts variable name and part name
    * @param aVariableName
    * @param aPartName
    */
   public AeFromVariableMessagePart(String aVariableName, String aPartName)
   {
      setVariableName(aVariableName);
      setPart(aPartName);
   }

   /**
    * Ctor accepts variable and part name
    * @param aVariable
    * @param aPartName
    */
   public AeFromVariableMessagePart(IAeVariable aVariable, String aPartName)
   {
      this((String) null, aPartName);
      setVariable(aVariable);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.assign.IAeFrom#getFromData()
    */
   public Object getFromData() throws AeBusinessProcessException
   {
      Object data = getVariable().getMessageData().getData(getPart());
      if (data instanceof Document)
      {
         return ((Document)data).getDocumentElement();
      }
      return data;
   }

   /**
    * @return Returns the part.
    */
   public String getPart()
   {
      return mPart;
   }

   /**
    * @param aPart The part to set.
    */
   public void setPart(String aPart)
   {
      mPart = aPart;
   }

   /**
    * Overrides method to return variable set by {@link #setVariable(IAeVariable)} if defined.
    * 
    * @see org.activebpel.rt.bpel.impl.activity.assign.from.AeFromBase#getVariable()
    */
   protected IAeVariable getVariable()
   {
      return (mVariable != null) ? mVariable : super.getVariable();
   }

   /**
    * @param aVariable The variable to set.
    */
   public void setVariable(IAeVariable aVariable)
   {
      mVariable = aVariable;
   }
}
