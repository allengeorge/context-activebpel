//$Header: /Development/AEDevelopment/projects/org.activebpel.wsio/src/org/activebpel/wsio/IAeWsAddressingHeaders.java,v 1.2 2006/09/26 15:03:0
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
package org.activebpel.wsio;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;


/**
 * Represents the values of a set of WS-Addressing header elements
 * from a SOAP Envelope
 */
public interface IAeWsAddressingHeaders extends Serializable
{
   /** Header element names */ 
   public static final String WSA_RELATES_TO = "RelatesTo"; //$NON-NLS-1$
   public static final String WSA_MESSAGE_ID = "MessageID"; //$NON-NLS-1$
   public static final String WSA_ACTION = "Action"; //$NON-NLS-1$
   public static final String WSA_FAULT_TO = "FaultTo"; //$NON-NLS-1$
   public static final String WSA_REPLY_TO = "ReplyTo"; //$NON-NLS-1$
   public static final String WSA_FROM = "From"; //$NON-NLS-1$
   public static final String WSA_TO = "To"; //$NON-NLS-1$
   public static final String WSA_RECIPIENT = "Recipient"; //$NON-NLS-1$   
   public static final String WSA_ADDRESS = "Address"; //$NON-NLS-1$
   public static final String ABX_CONVERSATION_ID = "conversationId"; //$NON-NLS-1$
   
   /**
    * @return Returns the messageId URI as a string.
    */
   public String getMessageId();

   /**
    * @param aMessageId The messageId URI string to set.
    */
   public void setMessageId(String aMessageId);

   /**
    * @return Returns the Action URI.
    */
   public String getAction();

   /**
    * @param aAction The Action URI to set.
    */
   public void setAction(String aAction);

   /**
    * @return Returns the FaultTo endpoint.
    */
   public IAeWebServiceEndpointReference getFaultTo();

   /**
    * @param aFaultTo The FaultTo endpoint to set.
    */
   public void setFaultTo(IAeWebServiceEndpointReference aFaultTo);

   /**
    * @return Returns the From endpoint.
    */
   public IAeWebServiceEndpointReference getFrom();

   /**
    * @param aFrom The From endpoint to set.
    */
   public void setFrom(IAeWebServiceEndpointReference aFrom);

   /**
    * @return Returns collection of message id's related to this message, 
    * indexed by the wsa RelationshipType QName.
    */
   public Map getRelatesTo();
   
   /**
    * @param aRelatesTo The collection of RelationshipType QName/MessageId pairs to set.
    */
   public void setRelatesTo(Map aRelatesTo);

   /**
    * Adds a MessageId to the RelatesTo collection, identified by 
    * the qualified name of the relationship type
    * @param aMessageId message id of related message 
    * @param aRelationship relationship type as defined by wsa
    */
   public void addRelatesTo(String aMessageId, QName aRelationship);

   /**
    * @return Returns the MessageId for a given RelationshipType QName.
    */
   public String getRelatesTo(QName aRelation);
      
   /**
    * @return Returns the ReplyTo endpoint.
    */
   public IAeWebServiceEndpointReference getReplyTo();

   /**
    * @param aReplyTo The ReplyTo endpoint to set.
    */
   public void setReplyTo(IAeWebServiceEndpointReference aReplyTo);

   /**
    * @return Returns address of the To destination.
    */
   public String getTo();

   /**
    * @param aTo Sets the value of the To address.
    */
   public void setTo(String aAddress);
   
   /**
    * @return Returns additional elements (non-WSA) to serialize as headers
    */
   public List getReferenceProperties();
   
   /**
    * @param aNamespace The WSA namespace to use.
    */
   public void setSourceNamespace(String aNamespace);
   
   /**
    * @returns the WSA namespace to use.
    */
   public String getSourceNamespace();
   
   /**
    * Gets the endpoint reference that was invoked
    */
   public IAeWebServiceEndpointReference getRecipient();

   /**
    * Sets the Recipient as an endpoint reference. 
    */
   public void setRecipient(IAeWebServiceEndpointReference aEndpoint);
   
   /**
    * Sets the value for the abx:conversationId
    * @param aId
    */
   public void setConversationId(String aId);
   
   /**
    * Gets the value of the abx:conversationId 
    */
   public String getConversationId();
}
