//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/AeActivityCreateInstanceBaseDef.java,v 1.2 2006/06/26 16:50:2
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
 * Base definition class for activities which is the createInstance attribute.
 */
public abstract class AeActivityCreateInstanceBaseDef extends AeActivityPartnerLinkBaseDef implements IAeActivityCreateInstanceDef
{
   /**
    * createInstance attribute.
    */
   private boolean mCreateInstance;

   /**
    * Default c'tor.
    */
   public AeActivityCreateInstanceBaseDef()
   {
      super();
   }

   /**
    * Accessor method to obtain the create instance flag.
    */
   public final boolean isCreateInstance()
   {
      return mCreateInstance;
   }

   /**
    * Mutator method to set the create instance flag for the activity.
    * 
    * @param aCreateInstance boolean flag indicating if instance should be 
    *        created for activity
    */
   public final void setCreateInstance(boolean aCreateInstance)
   {
      mCreateInstance = aCreateInstance;
   }   
}
