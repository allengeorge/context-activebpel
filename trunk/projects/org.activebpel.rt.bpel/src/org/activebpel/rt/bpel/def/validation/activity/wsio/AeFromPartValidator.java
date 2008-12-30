//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/activity/wsio/AeFromPartValidator.java,v 1.3 2006/12/14 22:48:4
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
package org.activebpel.rt.bpel.def.validation.activity.wsio; 

import org.activebpel.rt.bpel.def.activity.IAeMessageDataConsumerDef;
import org.activebpel.rt.bpel.def.activity.support.AeFromPartDef;
import org.activebpel.rt.bpel.def.validation.AeBaseValidator;
import org.activebpel.rt.bpel.def.validation.AeVariableValidator;
import org.activebpel.rt.bpel.def.validation.activity.AeWSIOActivityValidator;
import org.activebpel.rt.message.AeMessagePartsMap;

/**
 * moddel provides validation for the fromPart def
 */
public class AeFromPartValidator extends AeBaseValidator
{
   /**
    * ctor
    * @param aDef
    */
   public AeFromPartValidator(AeFromPartDef aDef)
   {
      super(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.AeBaseValidator#validate()
    */
   public void validate()
   {
      // get a ref to the fromPart's enclosing message consumer def
      IAeMessageDataConsumerDef def = (IAeMessageDataConsumerDef) getDefinition().getParent().getParent();
      if (def.getConsumerMessagePartsMap() != null)
      {
         // Note: if the parts are null, then an error will be reported elsewhere
         AeMessagePartsMap map = def.getConsumerMessagePartsMap();
         String partName = getDef().getPart();
         validateMessagePart(map, partName);
      }
      
      // validate the toVariable attribute using the proper scoping rules
      AeWSIOActivityValidator wsioParent = (AeWSIOActivityValidator) getAnscestor(AeWSIOActivityValidator.class);
      if (wsioParent != null)
      {
         String toVariableName = getDef().getToVariable();
         AeVariableValidator variable = wsioParent.resolveFromPartVariable(toVariableName);
         if (variable == null)
         {
            getReporter().addError( ERROR_VAR_NOT_FOUND,
                  new String[] { toVariableName },
                  getDefinition());
         }
      }
      
      super.validate();
   }

   /**
    * Validates that the part exists on the message part map
    * @param aMap
    * @param aPartName
    */
   protected void validateMessagePart(AeMessagePartsMap aMap, String aPartName)
   {
      if (aMap.getPartInfo(aPartName) == null)
      {
         getReporter().addError(ERROR_VAR_PART_NOT_FOUND, new String[] {aPartName,
                                       getNSPrefix(aMap.getMessageType().getNamespaceURI()),
                                       aMap.getMessageType().getLocalPart()}, getDefinition());
      }
   }
   
   /**
    * Getter for the fromPart def
    */
   protected AeFromPartDef getDef()
   {
      return (AeFromPartDef) getDefinition();
   }
}
 
