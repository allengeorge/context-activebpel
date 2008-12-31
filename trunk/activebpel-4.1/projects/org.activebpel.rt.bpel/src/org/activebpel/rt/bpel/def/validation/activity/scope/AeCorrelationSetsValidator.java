//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/activity/scope/AeCorrelationSetsValidator.java,v 1.1 2006/08/16 22:07:2
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

import java.util.Iterator;
import java.util.List;

import org.activebpel.rt.bpel.def.AeCorrelationSetsDef;
import org.activebpel.rt.bpel.def.validation.AeBaseValidator;

/**
 * model provides validation for the correlationSets def
 */
public class AeCorrelationSetsValidator extends AeBaseValidator
{
   /**
    * ctor
    * @param aDef
    */
   public AeCorrelationSetsValidator(AeCorrelationSetsDef aDef)
   {
      super(aDef);
   }

   /**
    * Returns the correlationSet with the given name or null if not defined here
    * @param aName
    */
   public AeCorrelationSetValidator getCorrelationSetModel(String aName)
   {
      List correlationSets = getChildren(AeCorrelationSetValidator.class);
      for (Iterator iter = correlationSets.iterator(); iter.hasNext();)
      {
         AeCorrelationSetValidator corrSetModel = (AeCorrelationSetValidator) iter.next();
         if (corrSetModel.getName().equals(aName))
            return corrSetModel;
      }
      return null;
   }
}
 
