//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/activity/decision/AeIfValidator.java,v 1.2 2006/10/03 19:25:4
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

import org.activebpel.rt.bpel.def.activity.support.AeIfDef;

/**
 * model provides validation for the if def of the <if> activity 
 */
public class AeIfValidator extends AeElseIfValidator
{
   /**
    * Ctor accepts the def
    * @param aDef
    */
   public AeIfValidator(AeIfDef aDef)
   {
      super(aDef);
   }
   
   /**
    * Getter for the if def
    */
   public AeIfDef getIfDef()
   {
      return (AeIfDef) getDefinition();
   }
}
 
