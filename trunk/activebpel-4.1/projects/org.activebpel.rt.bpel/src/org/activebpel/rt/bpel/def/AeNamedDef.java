// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/AeNamedDef.java,v 1.7 2006/06/26 16:50:2
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
package org.activebpel.rt.bpel.def;

/**
 * Definition base for all named definition elements
 */
public abstract class AeNamedDef extends AeBaseDef
{
   // persistent attributes of the definition
   private String mName = ""; //$NON-NLS-1$

   /**
    * Default constructor
    */
   public AeNamedDef()
   {
      super();
   }

   /**
    * Accessor method to obtain name of this object.
    * 
    * @return name of object
    */
   public String getName()
   {
      return mName;
   }

   /**
    * Mutator method to set name of this object.
    * 
    * @param aName of object, ignored if null, use empty string to clear
    */
   public void setName(String aName)
   {
      if(aName != null)
        mName = aName;
   }
}
