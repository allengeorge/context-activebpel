//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/IAeEngineAlert.java,v 1.2 2006/01/24 23:46:2
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

import org.w3c.dom.Document;


/**
 * Event for reporting alert events to engine listeners. These events will
 * include processes that are suspended due to the suspend activity and any 
 * uncaught faults in a process which would lead to its termination. 
 */
public interface IAeEngineAlert extends IAeEngineEvent
{
   /** Process suspended due to suspended activity. */
   public static final int PROCESS_ALERT_SUSPENDED = 1000 ;
   /** Process is faulting due to an uncaught fault. */
   public static final int PROCESS_ALERT_FAULTING = 1001 ;

   /**
    * Location of the activity generating the alert.
    */
   public String getLocation();
   
   /**
    * Alert events (like those from the suspend activity) may have some extra 
    * details in the form of an xml document.
    */
   public Document getDetails();
   
   /**
    * Gets the fault name.
    */
   public QName getFaultName();
}
 
