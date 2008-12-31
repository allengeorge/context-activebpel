//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.war/src/org/activebpel/rt/war/tags/AeIfPropertyNotMatchesTag.java,v 1.2 2007/04/27 21:53:5
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

import javax.servlet.jsp.JspException;

/**
 * A tag that includes its body content only if the 
 * given value property does not match the string value of the
 * specified property for a named bean.
 * NOTE: the return type of the bean property can be
 * any type, but the evaluation will be performed 
 * against its toString method.
 */
public class AeIfPropertyNotMatchesTag extends AeIfPropertyMatchesTag
{

   /**
    * Returns true of the string value of the actual argument does not match the tag value.
    * @param aActualValue property object to compared to
    * @return true if property does not matche the value.
    */
   protected boolean handleCompareValue(Object aActualValue) throws JspException
   {
      return !( super.handleCompareValue(aActualValue.toString()) ); 
   }   

}
