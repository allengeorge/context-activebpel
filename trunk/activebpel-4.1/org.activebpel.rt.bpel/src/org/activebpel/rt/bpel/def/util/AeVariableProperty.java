// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/util/AeVariableProperty.java,v 1.3 2006/06/26 16:50:4
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
package org.activebpel.rt.bpel.def.util;

import javax.xml.namespace.QName;

/**
 * Helper class to package data from the bpws:getVariableProperty function.
 */
public class AeVariableProperty
{
   /** The variable name parameter of the function call */
   private String mVarName;
   /** The variable property parameter of the function call */
   private QName mProperty;

   /**
    * Constructor for variableProperty element which takes the parameter values
    * as input.
    * @param aVarName the variable name we are requesting data for
    * @param aPropertyName the property name we wish to access
    */
   public AeVariableProperty(String aVarName, QName aPropertyName)
   {
      mVarName = aVarName;
      mProperty = aPropertyName;
   }

   /**
    * Returns the variable name part of the bpws:getVariableProperty function.
    */
   public String getVarName()
   {
      return mVarName;
   }

   /**
    * Returns the property name part of the bpws:getVariableProperty function.
    */
   public QName getProperty()
   {
      return mProperty;
   }
}

