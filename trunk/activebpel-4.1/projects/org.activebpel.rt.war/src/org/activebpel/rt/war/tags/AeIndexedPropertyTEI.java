// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.war/src/org/activebpel/rt/war/tags/AeIndexedPropertyTEI.java,v 1.1 2007/04/24 17:23:1
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

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

/**
 * TagExtraInfo impl for setting indexed property values as
 * scripting objects on the JSP page. 
 */
public class AeIndexedPropertyTEI extends TagExtraInfo
{
   /**
    * @see javax.servlet.jsp.tagext.TagExtraInfo#getVariableInfo(javax.servlet.jsp.tagext.TagData)
    */
   public VariableInfo[] getVariableInfo( TagData aData )
   {
      String name = aData.getId();

      return new VariableInfo[] {
         newVariableInfo(name, aData.getAttributeString("indexedClassName")), //$NON-NLS-1$
         newVariableInfo(name + "Index", AeIndexedPropertyTag.class.getName()) //$NON-NLS-1$
      };
   }

   /**
    * Returns new <code>VariableInfo</code> instance for the given name and
    * class name.
    *
    * @param aName
    * @param aClassName
    */
   protected VariableInfo newVariableInfo(String aName, String aClassName)
   {
      return new VariableInfo(aName, aClassName, true, VariableInfo.NESTED);
   }

   /**
    * Always returns true.
    */
   public boolean isValid()
   {
      return true;
   }
}
