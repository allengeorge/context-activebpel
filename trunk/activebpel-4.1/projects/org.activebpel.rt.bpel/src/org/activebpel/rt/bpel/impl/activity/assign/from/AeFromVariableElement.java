//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/assign/from/AeFromVariableElement.java,v 1.6 2007/05/09 20:36:3
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

import org.activebpel.rt.attachment.IAeAttachmentContainer;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeVariable;
import org.activebpel.rt.bpel.def.activity.support.AeFromDef;

/**
 * Handles selecting data from an element variable 
 */
public class AeFromVariableElement extends AeFromBase
{
   /** variable */
   private IAeVariable mVariable;

   /**
    * Ctor takes the def
    * 
    * @param aDef
    */
   public AeFromVariableElement(AeFromDef aDef)
   {
      super(aDef);
   }
   
   /**
    * Ctor accepts variable name
    * @param aVariableName
    */
   public AeFromVariableElement(String aVariableName)
   {
      setVariableName(aVariableName);
   }
   
   /**
    * Ctor accepts variable
    * @param aVariable
    */
   public AeFromVariableElement(IAeVariable aVariable)
   {
      setVariable(aVariable);
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.activity.assign.IAeFrom#getFromData()
    */
   public Object getFromData() throws AeBusinessProcessException
   {
      return getVariable().getElementData();
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
   
   /**
    * @see org.activebpel.rt.bpel.impl.activity.assign.from.AeFromBase#getAttachmentsSource()
    */
   public IAeAttachmentContainer getAttachmentsSource()
   {
      return getVariable().getAttachmentData();
   }
}
 
