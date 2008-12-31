//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.war/src/org/activebpel/rt/war/tags/AeAbstractFormPropertyTag.java,v 1.1 2007/04/24 17:23:1
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

/**
 * Base class used to set form parameters into a bean
 */
public class AeAbstractFormPropertyTag extends AeAbstractBeanPropertyTag
{
   /** Name of the param to read from the request */
   private String mParam;
   
   /**
    * Gets the param value that's converted to a date.
    */
   protected String getParamValue()
   {
      return pageContext.getRequest().getParameter(getParam());
   }

   /**
    * @return Returns the param.
    */
   public String getParam()
   {
      return mParam;
   }
   /**
    * @param aParam The param to set.
    */
   public void setParam(String aParam)
   {
      mParam = aParam;
   }   

}
