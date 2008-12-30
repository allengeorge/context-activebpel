//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/activity/scope/AeBPWSCatchValidator.java,v 1.1 2006/09/11 23:06:2
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
package org.activebpel.rt.bpel.def.validation.activity.scope; 

import java.util.HashSet;
import java.util.Set;

import org.activebpel.rt.bpel.def.AeCatchDef;

/**
 * model provides the validation for the catchDef 
 */
public class AeBPWSCatchValidator extends AeBaseCatchValidator
{
   /** valid catch def patterns for BPWS */
   private static final Set BPWS_PATTERNS = new HashSet();

   static 
   {
      // catch w/ name only
      AeCatchSpec spec = new AeCatchSpec();
      spec.setFaultName();
      BPWS_PATTERNS.add(spec);
      
      // catch w/ variable only 
      spec = new AeCatchSpec();
      spec.setFaultVariable();
      BPWS_PATTERNS.add(spec);
      
      // catch w/ name and variable
      spec = new AeCatchSpec();
      spec.setFaultName();
      spec.setFaultVariable();
      BPWS_PATTERNS.add(spec);
   };
   
   /**
    * ctor
    * @param aDef
    */
   public AeBPWSCatchValidator(AeCatchDef aDef)
   {
      super(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.validation.activity.scope.AeBaseCatchValidator#getPatterns()
    */
   protected Set getPatterns()
   {
      return BPWS_PATTERNS;
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.activity.scope.AeBaseCatchValidator#getPatternErrorMessage()
    */
   protected String getPatternErrorMessage()
   {
      return ERROR_BPWS_CATCH_PATTERN;
   }
}
 
