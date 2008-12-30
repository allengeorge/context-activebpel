//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/AeEngineAlert.java,v 1.3 2006/10/20 14:41:2
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

import java.util.Date;

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.IAeEngineAlert;
import org.w3c.dom.Document;

/**
 * Impl of the engine alert interface
 */
public class AeEngineAlert extends AeEngineEvent implements IAeEngineAlert
{
   /** details for the alert */
   private Document mDetails;
   /** location of the activity that generated the alert */
   private String mLocation;
   /** name of the fault that generated the alert */
   private QName mFaultName;

   /**
    * Ctor for the engine alert
    * @param aProcessID id of the process that generated the alert
    * @param aEventID type of alert
    * @param aProcessName qname of the process that generated the alert 
    * @param aLocation location of the activity within the process that generated the alert
    * @param aFaultName optional qname of the fault
    * @param aDetails option details related to the alert - possibly variable data
    */
   public AeEngineAlert(long aProcessID, int aEventID, QName aProcessName, String aLocation, QName aFaultName, Document aDetails)
   {
      super(aProcessID, aEventID, aProcessName);
      setFaultName(aFaultName);
      setDetails(aDetails);
      setLocation(aLocation);
   }

   /**
    * Ctor for the engine alert
    * @param aProcessID id of the process that generated the alert
    * @param aEventID type of alert
    * @param aProcessName qname of the process that generated the alert 
    * @param aLocation location of the activity within the process that generated the alert
    * @param aFaultName optional qname of the fault
    * @param aDetails option details related to the alert - possibly variable data
    * @param aTimestamp the timestamp of the event/alert
    */
   public AeEngineAlert(long aProcessID, int aEventID, QName aProcessName, String aLocation,
         QName aFaultName, Document aDetails, Date aTimestamp)
   {
      super(aProcessID, aEventID, aProcessName, aTimestamp);
      setFaultName(aFaultName);
      setDetails(aDetails);
      setLocation(aLocation);
   }
   
   /**
    * @return Returns the faultName.
    */
   public QName getFaultName()
   {
      return mFaultName;
   }

   /**
    * @param aFaultName The faultName to set.
    */
   public void setFaultName(QName aFaultName)
   {
      mFaultName = aFaultName;
   }



   /**
    * setter for the details
    * @param aDetails
    */
   protected void setDetails(Document aDetails)
   {
      mDetails = aDetails;
   }

   /**
    * @see org.activebpel.rt.bpel.IAeEngineAlert#getDetails()
    */
   public Document getDetails()
   {
      return mDetails;
   }
   
   /**
    * Setter for the location
    * @param aLocation
    */
   protected void setLocation(String aLocation)
   {
      mLocation = aLocation;
   }
   
   /**
    * @see org.activebpel.rt.bpel.IAeEngineAlert#getLocation()
    */
   public String getLocation()
   {
      return mLocation;
   }
}
 
