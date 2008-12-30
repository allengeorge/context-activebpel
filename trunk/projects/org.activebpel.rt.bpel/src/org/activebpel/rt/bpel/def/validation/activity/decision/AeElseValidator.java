//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/activity/decision/AeElseValidator.java,v 1.1 2006/08/16 22:07:2
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
package org.activebpel.rt.bpel.def.validation.activity.decision; 

import org.activebpel.rt.bpel.def.activity.support.AeElseDef;
import org.activebpel.rt.bpel.def.validation.AeBaseValidator;

/**
 * model provides validation for the else condition of an if or switch activity
 */
public class AeElseValidator extends AeBaseValidator
{
   /** value for the tag name, used for error reporting only ("otherwise" or "else") */
   private String mTagName;
   
   /**
    * ctor
    * @param aDef
    */
   public AeElseValidator(AeElseDef aDef)
   {
      super(aDef);
   }
   
   /**
    * Getter for the def
    */
   protected AeElseDef getDef()
   {
      return (AeElseDef) getDefinition();
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.AeBaseValidator#validate()
    */
   public void validate()
   {
      super.validate();

      // No empty otherwises allowed.
      //
      if ( getDef().getActivityDef() == null )
      {
         getReporter().addError( ERROR_ACTIVITY_MISSING,
               new String[] { getTagName() },
               getDef() );
      }
   }
   
   /**
    * Getter for the tag name
    */
   protected String getTagName()
   {
      return mTagName;
   }
   
   /**
    * Setter for the tag name
    * @param aTagName
    */
   public void setTagName(String aTagName)
   {
      mTagName = aTagName;
   }
}
 
