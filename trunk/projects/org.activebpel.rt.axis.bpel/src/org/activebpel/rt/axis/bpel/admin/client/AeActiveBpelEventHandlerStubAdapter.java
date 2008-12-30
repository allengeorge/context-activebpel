//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis.bpel/src/org/activebpel/rt/axis/bpel/admin/client/AeActiveBpelEventHandlerStubAdapter.java,v 1.2 2007/01/25 16:57:2
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
package org.activebpel.rt.axis.bpel.admin.client;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;

import javax.xml.namespace.QName;

import org.activebpel.rt.axis.bpel.eventhandler.IAeActiveBpelEventHandler;
import org.activebpel.rt.axis.bpel.eventhandler.types.AesBreakpointEventHandlerInput;
import org.activebpel.rt.axis.bpel.eventhandler.types.AesEngineAlertHandlerInput;
import org.activebpel.rt.axis.bpel.eventhandler.types.AesEngineEventHandlerInput;
import org.activebpel.rt.axis.bpel.eventhandler.types.AesEngineEventHandlerOutput;
import org.activebpel.rt.axis.bpel.eventhandler.types.AesProcessEventHandlerInput;
import org.activebpel.rt.axis.bpel.eventhandler.types.AesProcessEventHandlerOutput;
import org.activebpel.rt.axis.bpel.eventhandler.types.AesProcessInfoEventHandlerInput;
import org.activebpel.rt.bpel.server.admin.rdebug.client.IAeEventHandler;
import org.activebpel.rt.xml.AeXMLParserBase;
import org.w3c.dom.Document;

public class AeActiveBpelEventHandlerStubAdapter implements IAeEventHandler
{
   /** Event handler interface we are adapting to */
   protected IAeActiveBpelEventHandler mEventHandler;
   
   /**
    * Constructor
    * @param aEventHandler
    */
   public AeActiveBpelEventHandlerStubAdapter(IAeActiveBpelEventHandler aEventHandler)
   {
      mEventHandler = aEventHandler;
   }

   /**
    * Returns the event handleradmin interface which we will invoke engine admin methods through.
    */
   protected IAeActiveBpelEventHandler getHandler()
   {
      return mEventHandler;
   }

   /**
    * @see org.activebpel.rt.bpel.server.admin.rdebug.client.IAeEventHandler#engineEventHandler(long, long, int, javax.xml.namespace.QName, java.util.Date)
    */
   public boolean engineEventHandler(long aContextId, long aProcessId, int aEventType, QName aProcessName, Date aTimestamp) throws RemoteException
   {
      Calendar cal = Calendar.getInstance();
      cal.setTimeInMillis(aTimestamp.getTime());
      AesEngineEventHandlerOutput out = getHandler().engineEventHandler(new AesEngineEventHandlerInput(aContextId, aProcessId, aEventType, aProcessName, cal));
      
      return out.isResponse();
   }

   /**
    * @see org.activebpel.rt.bpel.server.admin.rdebug.client.IAeEventHandler#engineAlertHandler(long, long, int, javax.xml.namespace.QName, java.lang.String, javax.xml.namespace.QName, org.w3c.dom.Document, java.util.Date)
    */
   public void engineAlertHandler(long aContextId, long aProcessId, int aEventType, QName aProcessName,
         String aLocationPath, QName aFaultName, Document aDetails, Date aTimestamp) throws RemoteException
   {
      String serializedXmlDetails = ""; //$NON-NLS-1$
      if (aDetails != null)
         serializedXmlDetails = AeXMLParserBase.documentToString(aDetails);
      
      Calendar cal = Calendar.getInstance();
      cal.setTimeInMillis(aTimestamp.getTime());
      getHandler().engineAlertHandler(new AesEngineAlertHandlerInput(aContextId, aProcessId, aEventType, aProcessName, aLocationPath, aFaultName, serializedXmlDetails, cal));
   }

   /**
    * @see org.activebpel.rt.bpel.server.admin.rdebug.client.IAeEventHandler#processEventHandler(long, long, java.lang.String, int, java.lang.String, java.lang.String, javax.xml.namespace.QName, java.util.Date)
    */
   public boolean processEventHandler(long aContextId, long aProcessId, String aPath, int aEventType,
         String aFaultName, String aText, QName aName, Date aTimestamp) throws RemoteException
   {
      Calendar cal = Calendar.getInstance();
      cal.setTimeInMillis(aTimestamp.getTime());
      AesProcessEventHandlerOutput out = getHandler().processEventHandler(new AesProcessEventHandlerInput(aContextId, aProcessId, aPath, aEventType, aFaultName, aText, aName, cal));
 
      return out.isResponse();
   }

   /**
    * @see org.activebpel.rt.bpel.server.admin.rdebug.client.IAeEventHandler#processInfoEventHandler(long, long, java.lang.String, int, java.lang.String, java.lang.String, java.util.Date)
    */
   public void processInfoEventHandler(long aContextId, long aProcessId, String aPath, int aEventType, String aFaultName, String aText, Date aTimestamp) throws RemoteException
   {
      Calendar cal = Calendar.getInstance();
      cal.setTimeInMillis(aTimestamp.getTime());
      getHandler().processInfoEventHandler(new AesProcessInfoEventHandlerInput(aContextId, aProcessId, aPath, aEventType, aFaultName, aText, cal));
   }   

   /**
    * @see org.activebpel.rt.bpel.server.admin.rdebug.client.IAeEventHandler#breakpointEventHandler(long, long, java.lang.String, javax.xml.namespace.QName, java.util.Date)
    */
   public void breakpointEventHandler(long aContextId, long aProcessId, String aPath, QName aProcessName, Date aTimestamp) throws RemoteException
   {
      Calendar cal = Calendar.getInstance();
      cal.setTimeInMillis(aTimestamp.getTime());
      getHandler().breakpointEventHandler(new AesBreakpointEventHandlerInput(aContextId, aProcessId, aPath, aProcessName, cal));
   }
}
