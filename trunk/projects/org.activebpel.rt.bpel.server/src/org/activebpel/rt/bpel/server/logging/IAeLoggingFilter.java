//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/logging/IAeLoggingFilter.java,v 1.1 2007/02/16 14:05:2
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
package org.activebpel.rt.bpel.server.logging; 

import org.activebpel.rt.bpel.IAeProcessEvent;
import org.activebpel.rt.bpel.IAeProcessInfoEvent;

/**
 * Interface used to filter log events
 */
public interface IAeLoggingFilter
{
   /**
    * Returns true if the process event should be logged
    * @param aEvent
    */
   public boolean accept(IAeProcessEvent aEvent);
   
   /**
    * Returns true if the process info event should be logged
    * @param aInfoEvent
    */
   public boolean accept(IAeProcessInfoEvent aInfoEvent);
   
   /**
    * Returns true if the filter will accept one or more process info events. 
    * If false, then there's no reason to listen for process events.
    */
   public boolean isEnabled();
   
   /**
    * Returns the name of the filter
    */
   public String getName();
}
 
