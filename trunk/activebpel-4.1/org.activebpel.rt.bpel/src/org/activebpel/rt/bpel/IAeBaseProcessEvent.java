//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/IAeBaseProcessEvent.java,v 1.2 2006/10/20 14:41:2
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
package org.activebpel.rt.bpel; 

/**
 * A base interface for process events. 
 */
public interface IAeBaseProcessEvent extends IAeEvent
{
   /**
    * Returns the node path related to this event.
    */
   public String getNodePath();
   
   /**
    * Returns the ID of this event.
    */
   public int getEventID();
   
   /**
    * Returns the name of the Fault associated with this event, or empty string.
    */
   public String getFaultName();
   
   /**
    * Returns ancilliary information which may have been specified for the process event.
    */
   public String getAncillaryInfo();
   
   /**
    * Returns the process ID of the engine instance that broadcast this event.
    */
   public long getPID();
}
 
