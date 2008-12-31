//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/activity/AeActivityScopeValidator.java,v 1.6 2006/10/03 19:35:1
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
package org.activebpel.rt.bpel.def.validation.activity; 

import org.activebpel.rt.bpel.def.activity.AeActivityScopeDef;

/**
 * validator provides validation for the scope activity
 */
public abstract class AeActivityScopeValidator extends AeBaseScopeValidator
{
   /**
    * ctor
    * @param aDef
    */
   public AeActivityScopeValidator(AeActivityScopeDef aDef)
   {
      super(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.validation.activity.AeActivityValidator#validate()
    */
   public void validate()
   {
      super.validate();
      
      if (getDef().isIsolated())
      {
         validateIsolatedScope();
      }
   }

   /**
    * Executes the validation code for an isolated scope 
    */
   protected abstract void validateIsolatedScope();
   
   /**
    * Getter for the scope def
    */
   protected AeActivityScopeDef getDef()
   {
      return (AeActivityScopeDef) getDefinition();
   }
}
 
