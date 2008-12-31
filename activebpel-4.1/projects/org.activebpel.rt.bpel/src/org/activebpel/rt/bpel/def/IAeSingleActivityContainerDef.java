// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/IAeSingleActivityContainerDef.java,v 1.3 2006/06/26 16:50:2
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
 * Interface for all containers of single activities.
 */
public interface IAeSingleActivityContainerDef extends IAeActivityContainerDef
{
   /**
    * Obtains the current activity associated with this activity container.
    * @return an activity associated with this object
    * @see AeActivityDef
    */
   public AeActivityDef getActivityDef();

   /**
    * Set the activity to execute in this activity container.
    * @param aActivity activity to set
    * @see AeActivityDef
    */
   public void setActivityDef(AeActivityDef aActivity);

}
