// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/activity/IAeTimedDef.java,v 1.4 2006/07/20 20:45:0
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
package org.activebpel.rt.bpel.def.activity;

import org.activebpel.rt.bpel.def.activity.support.AeForDef;
import org.activebpel.rt.bpel.def.activity.support.AeRepeatEveryDef;
import org.activebpel.rt.bpel.def.activity.support.AeUntilDef;

/**
 * Interface contains the getter methods for getting the duration or deadline
 * for a timed event like a wait or an alarm.
 */
public interface IAeTimedDef
{
   /**
    * Returns the for def, will be null if the def uses the deadline model.
    */
   public AeForDef getForDef();

   /**
    * Returns the until def, will be null if the def uses the duration model.
    */
   public AeUntilDef getUntilDef();
   
   /**
    * Returns the optional repeatEvery def
    */
   public AeRepeatEveryDef getRepeatEveryDef();
}
