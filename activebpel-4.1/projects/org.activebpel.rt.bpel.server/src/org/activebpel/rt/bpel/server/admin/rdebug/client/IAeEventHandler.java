// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/admin/rdebug/client/IAeEventHandler.java,v 1.7 2007/01/25 16:55:1
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
package org.activebpel.rt.bpel.server.admin.rdebug.client;

import org.w3c.dom.Document;

import javax.xml.namespace.QName;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

/**
 * Interface which defines the operations exposed by the remote debugger.
 */
public interface IAeEventHandler extends Remote
{
   /**
    * Event handler callbacl for engine alerts such as process suspending or faulting
    * 
    * @param aContextId the context id used to locate the callback
    * @param aProcessId the id of the process affected
    * @param aEventType the event type
    * @param aProcessName the name of the process
    * @param aLocationPath location path of the activity causing the fault/suspension
    * @param aFaultName the name of the fault that caused the alert
    * @param aDetails optional details for the event
    * @param aTimestamp the event's timestamp
    * @throws RemoteException
    */
   public void engineAlertHandler(long aContextId, long aProcessId, int aEventType, QName aProcessName,
         String aLocationPath, QName aFaultName, Document aDetails, Date aTimestamp) throws RemoteException;

   /**
    * Event handler callback for engine events, such as process creation and termination.
    * 
    * @param aContextId the context id used to locate the callback
    * @param aProcessId the id of the process affected
    * @param aEventType the event type
    * @param aProcessName the name of the process
    * @param aTimestamp the event's timestamp
    * @return true return indicates to suspend the associated process.
    */
   public boolean engineEventHandler(long aContextId, long aProcessId, int aEventType, QName aProcessName,
         Date aTimestamp) throws RemoteException;

   /**
    * Event handler callback for process events, such as process state and info.
    * 
    * @param aContextId the context id used to locate the callback
    * @param aProcessId the id of the process affected
    * @param aPath path of node affected by the event
    * @param aEventType the event type
    * @param aFaultName an optional fault name if fault event
    * @param aText optional text
    * @param aName The process' QName.
    * @param aTimestamp the event's timestamp
    * @return True if the process needs to be suspended, False otherwise
    */
   public boolean processEventHandler(long aContextId, long aProcessId, String aPath, int aEventType,
         String aFaultName, String aText, QName aName, Date aTimestamp) throws RemoteException;

   /**
    * Event handler callback for process info events, such as process state and info.
    * 
    * @param aContextId the context id used to locate the callback
    * @param aProcessId the id of the process affected
    * @param aPath path of node affected by the event
    * @param aEventType the event type
    * @param aFaultName an optional fault name if fault event
    * @param aText optional text
    * @param aTimestamp the event's timestamp
    */
   public void processInfoEventHandler(long aContextId, long aProcessId, String aPath, int aEventType,
         String aFaultName, String aText, Date aTimestamp) throws RemoteException;

   /**
    * Event handler callback for breakpoint events.
    * 
    * @param aContextId the context id used to locate the callback
    * @param aProcessId the id of the process affected
    * @param aPath the node path where the breakpoint occurred.
    * @param aProcessName the name of the process
    * @param aTimestamp the event's timestamp
    */
   public void breakpointEventHandler(long aContextId, long aProcessId, String aPath, QName aProcessName,
         Date aTimestamp) throws RemoteException;
}
