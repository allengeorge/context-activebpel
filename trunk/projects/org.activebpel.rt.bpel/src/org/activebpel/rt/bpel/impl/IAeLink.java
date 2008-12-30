// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/IAeLink.java,v 1.3 2004/07/08 13:09:5
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
package org.activebpel.rt.bpel.impl;

import org.activebpel.rt.bpel.IAeActivity;

/**
 * Interface for Bpel Link object implementations. 
 */
public interface IAeLink
{
   /**
    * Returns a bpel path unqiue within the process for event processing.
    */
   public String getLocationPath();
   
   /**
    * Returns true if the status of this link is known.
    */
   public boolean isStatusKnown();
   
   /**
    * Returns the status. If the status of this link is unknown then it'll 
    * generate an exception since the spec dictates that you don't evaluate join
    * conditions until the status of all inbound links is known.
    * @return true if transition condition met, false if not, exception if not known
    */
   public boolean getStatus();
   
   /**
    * Getter for the target activity.
    */
   public IAeActivity getTargetActivity();
   
   /**
    * Getter for the source activity.
    */
   public IAeActivity getSourceActivity();
}
