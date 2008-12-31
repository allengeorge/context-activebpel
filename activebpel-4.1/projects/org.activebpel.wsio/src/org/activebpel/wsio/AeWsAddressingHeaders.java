//$Header: /Development/AEDevelopment/projects/org.activebpel.wsio/src/org/activebpel/wsio/AeWsAddressingHeaders.java,v 1.3 2007/02/13 15:56:4
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

/**
 *  Holder for the values from a set of WS-Addressing Headers
 */
public class AeWsAddressingHeaders implements IAeWsAddressingHeaders
{
   /** Action URI */
   private String mAction;
   /** Message ID URI */
   private String mMessageId;
   /** Collection of RelationshipType QName/MessageId pairs */
   private HashMap mRelatesTo = new HashMap();
   /** From endpoint */
   private IAeWebServiceEndpointReference mFrom;
   /** ReplyTo endpoint */
   private IAeWebServiceEndpointReference mReplyTo;
   /** FaultTo endpoint */
   private IAeWebServiceEndpointReference mFaultTo;
   /** Endpoint containing To address URI */
   private IAeWebServiceEndpointReference mTo;
   /** List of additional header elements to serialize */
   private List mReferenceProperties;
   /** WSA Namespace URI */ 
   private String mNamespace;   
   /** WSA To URI */ 
   private String mToURI;   
   /** abx:conversationId */
   private String mConversationId;
   
   /**
    * Constructor 
    * @param aNamespace WS-Addressing namespace URI for this instance
    */
   public AeWsAddressingHeaders(String aNamespace)
   {
      setSourceNamespace(aNamespace);
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.endpoints.IAeWsAddressingHeaders#getMessageId()
    */
   public String getMessageId()
   {
      return mMessageId;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.endpoints.IAeWsAddressingHeaders#setMessageId(java.lang.String)
    */
   public void setMessageId(String aMessageId)
   {
      mMessageId = aMessageId;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.endpoints.IAeWsAddressingHeaders#getAction()
    */
   public String getAction()
   {
      return mAction;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.endpoints.IAeWsAddressingHeaders#setAction(java.lang.String)
    */
   public void setAction(String aAction)
   {
      mAction = aAction;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.endpoints.IAeWsAddressingHeaders#getFaultTo()
    */
   public IAeWebServiceEndpointReference getFaultTo()
   {
      return mFaultTo;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.endpoints.IAeWsAddressingHeaders#setFaultTo(org.activebpel.rt.bpel.IAeEndpointReference)
    */
   public void setFaultTo(IAeWebServiceEndpointReference aFaultTo)
   {
      mFaultTo = aFaultTo;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.endpoints.IAeWsAddressingHeaders#getFrom()
    */
   public IAeWebServiceEndpointReference getFrom()
   {
      return mFrom;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.endpoints.IAeWsAddressingHeaders#setFrom(org.activebpel.rt.bpel.IAeEndpointReference)
    */
   public void setFrom(IAeWebServiceEndpointReference aFrom)
   {
      mFrom = aFrom;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.endpoints.IAeWsAddressingHeaders#getRelatesTo()
    */
   public Map getRelatesTo()
   {
      return mRelatesTo;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.endpoints.IAeWsAddressingHeaders#getRelatesTo()
    */
   public String getRelatesTo(QName aRelation)
   {
      return (String) mRelatesTo.get(aRelation);
   }
   
   
   /**
    * @see org.activebpel.rt.bpel.impl.endpoints.IAeWsAddressingHeaders#setRelatesTo(java.lang.String)
    */
   public void setRelatesTo(Map aRelatesTo)
   {
      mRelatesTo = (HashMap) aRelatesTo;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.endpoints.IAeWsAddressingHeaders#addRelatesTo(java.lang.String, javax.xml.namespace.QName)
    */
   public void addRelatesTo(String aMessageId, QName aRelation)
   {
      getRelatesTo().put(aRelation, aMessageId);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.endpoints.IAeWsAddressingHeaders#getReplyTo()
    */
   public IAeWebServiceEndpointReference getReplyTo()
   {
      return mReplyTo;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.endpoints.IAeWsAddressingHeaders#setReplyTo(org.activebpel.rt.bpel.IAeEndpointReference)
    */
   public void setReplyTo(IAeWebServiceEndpointReference aReplyTo)
   {
      mReplyTo = aReplyTo;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.endpoints.IAeWsAddressingHeaders#getTo()
    */
   public String getTo()
   {
      return mToURI;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.endpoints.IAeWsAddressingHeaders#setTo(org.activebpel.rt.bpel.IAeEndpointReference)
    */
   public void setTo(String aTo)
   {
      mToURI = aTo;
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.endpoints.IAeWsAddressingHeaders#setSourceNamespace(java.lang.String)
    */
   public void setSourceNamespace(String aNamespace)
   {
      mNamespace = aNamespace;      
   }

   /**
    * @see org.activebpel.rt.bpel.impl.endpoints.IAeWsAddressingHeaders#getSourceNamespace()
    */
   public String getSourceNamespace()
   {
      return mNamespace;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.endpoints.IAeWsAddressingHeaders#getRecipient()
    */
   public IAeWebServiceEndpointReference getRecipient()
   {
      return mTo;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.endpoints.IAeWsAddressingHeaders#setRecipient(org.activebpel.wsio.IAeWebServiceEndpointReference)
    */
   public void setRecipient(IAeWebServiceEndpointReference aEndpoint)
   {
      mTo = aEndpoint;
   }
   
   
   /**
    * @see org.activebpel.rt.bpel.impl.endpoints.IAeWsAddressingHeaders#getReferenceProperties()
    */
   public List getReferenceProperties()
   {
      if (mReferenceProperties == null)
      {
         mReferenceProperties = new ArrayList();
      }
      return mReferenceProperties;
   }

   /**
    * @see org.activebpel.wsio.IAeWsAddressingHeaders#setConversationId(java.lang.String)
    */
   public void setConversationId(String aId)
   {
      mConversationId = aId;
   }

   /**
    * @see org.activebpel.wsio.IAeWsAddressingHeaders#getConversationId()
    */
   public String getConversationId()
   {
      return mConversationId;
   }

}
