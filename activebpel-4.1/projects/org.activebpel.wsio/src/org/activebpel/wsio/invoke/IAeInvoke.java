// $Header: /Development/AEDevelopment/projects/org.activebpel.wsio/src/org/activebpel/wsio/invoke/IAeInvoke.java,v 1.10 2007/05/01 17:01:0
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
package org.activebpel.wsio.invoke;

import java.io.Serializable;
import java.util.Map;

import javax.xml.namespace.QName;

import org.activebpel.wsio.IAeWebServiceMessageData;

/**
 * Wraps the invoke data.
 */
public interface IAeInvoke extends Serializable
{
   /**
    * Accessor for the process id.
    */
   public long getProcessId();

   /**
    * Accessor for the process <code>QName</code>.
    */
   public QName getProcessName();

   /**
    * Accessor for the partner link (may be either a simple PL name or full location).
    */
   public String getPartnerLink();

   /**
    * Accessor for the partner endpoint reference.
    */
   public String getPartnerEndpointReferenceString();

   /***
    * Accessor for my endpoint reference.
    */
   public String getMyEndpointReferenceString();

   /**
    * Accessor for the operation.
    */
   public String getOperation();

   /**
    * Accessor for the input message data.
    */
   public IAeWebServiceMessageData getInputMessageData();

   /**
    * Return true if this is a one way invoke.
    */
   public boolean isOneWay();

   /**
    * Accessor for the location path.
    */
   public String getLocationPath();

   /**
    * Accessor for the location id.
    * @return
    */
   public int getLocationId();
   
   /**
    * Return the custom invoker uri string or null if none was specified.
    * @deprecated use getInvokeHandler
    */
   public String getCustomInvokerUri();

   /**
    * Setter for the invoke handler
    *
    * @param aHandler
    */
   public void setInvokeHandler(String aHandler);

   /**
    * Returns the uri for the invoke handler or null if none defined
    */
   public String getInvokeHandler();

   /**
    * Gets the port type for the invoke.
    */
   public QName getPortType();

   /**
    * The <code>Map</code> of (string) name/value pairs that will be sent to the
    * business process instance when the invoke is executed.
    */
   public Map getBusinessProcessProperties();
   
   /** 
    * Returns the transmission id used in durable invokes. If id is not available,
    * this method returns 0.
    * @return transmission journal id if available.
    */
   public long getTransmissionId();
   
   /**
    * Called by durable invoke handlers when the invoke has been assigned a transmission id
    * and journaled. 
    * @param aTxJournalId unique identifier for invokes outbound message (transmission).
    */
   public void setTransmissionId(long aTransimissionId);
}
