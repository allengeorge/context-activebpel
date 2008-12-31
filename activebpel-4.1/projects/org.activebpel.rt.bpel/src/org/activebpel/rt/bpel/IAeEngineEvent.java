// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/IAeEngineEvent.java,v 1.15 2006/10/20 14:41:2
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

import javax.xml.namespace.QName;

/**
 * Interface for engine events 
 */
public interface IAeEngineEvent extends IAeEvent
{
   /** Process was created. */
   public static final int PROCESS_CREATED = 0 ;
   /** Process has been terminated. */
   public static final int PROCESS_TERMINATED = 1 ;
   /** Process has been suspended. */
   public static final int PROCESS_SUSPENDED = 2 ;
   /** Process has been resumed. */
   public static final int PROCESS_RESUMED = 3 ;
   /** Process has started executing. */
   public static final int PROCESS_STARTED = 4 ;

   /**
    * Returns the event id for the engine event.
    */
   public int getEventID();
   
   /**
    * Returns the process id for the engine event.
    */
   public long getPID();
   
   /**
    * Returns the namespace qualified name of the process this event represents.
    */
   public QName getProcessName();
}
