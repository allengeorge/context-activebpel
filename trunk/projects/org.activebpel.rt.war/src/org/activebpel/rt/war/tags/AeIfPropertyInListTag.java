//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.war/src/org/activebpel/rt/war/tags/AeIfPropertyInListTag.java,v 1.1 2007/04/24 17:23:1
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
package org.activebpel.rt.war.tags;

import org.activebpel.rt.util.AeUtil;

/**
 * A tag that includes its body content only if the given property value is
 * in the given list of strings. The list of strings are separated by a comma.
 * This class does a case-insensitve test against the given list of values.
 * <br/>
 * The property value must be a string.
 */

public class AeIfPropertyInListTag extends AeIfPropertyMatchesTag
{

   /**
    * Returns true if the string representation of <code>aActualValue</code> is in a given list of
    * comma separated strings.
    * @param aActualValue property object to compared to
    * @return true if property is in the comma separated list of values.
    */
   protected boolean handleCompareValue(Object aActualValue)
   {
      boolean rVal = false;
      if (aActualValue != null) 
      {
         rVal = AeUtil.isStringInCsvList(aActualValue.toString(), getValue(), false);
      }
      return rVal;
   }
   
}
