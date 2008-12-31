// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/util/AeVariableData.java,v 1.2 2004/07/08 13:09:5
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

/**
 * Helper class to package data from the bpws:getVariableData function.
 */
public class AeVariableData
{
   /** The variable name parameter of the function call */
   private String mVarName;

   /** The part name parameter of the function call (optional) */
   private String mPartName;

   /** The query expression parameter of the function call (optional) */
   private String mQueryName;
   
   /**
    * Constructor for variableData element which takes the parameter values as
    * input. 
    * @param aVarName the variable name we are requesting data for
    * @param aPartName the part name we are referencing or null
    * @param aQueryName the query expression or null
    */
   public AeVariableData(String aVarName, String aPartName, String aQueryName)
   {
      mVarName   = aVarName;
      mPartName  = aPartName;
      mQueryName = aQueryName;
   }
   
   /**
    * Returns the variable name part of the bpws:getVariableData function.
    */
   public String getVarName()
   {
      return mVarName;
   }

   /**
    * Returns the part name part of the bpws:getVariableData function. Note that
    * this value is not required to be set and may be null.
    */
   public String getPart()
   {
      return mPartName;
   }

   /**
    * Returns the part name part of the bpws:getVariableData function. Note that
    * this value is not required to be set and may be null.
    */
   public String getQuery()
   {
      return mQueryName;
   }
}
