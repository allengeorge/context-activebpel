// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis.bpel/src/org/activebpel/rt/axis/bpel/handlers/soap/AeSOAPMessageWrapper.java,v 1.3 2007/04/23 21:33:5
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
package org.activebpel.rt.axis.bpel.handlers.soap;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import javax.xml.soap.AttachmentPart;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.apache.axis.Message;

/**
 * A wrapper around an Axis message.  This class exists in order to fix some problems with the
 * Axis implementation of the org.w3c.dom.Element interface.
 */
public class AeSOAPMessageWrapper extends SOAPMessage
{
   /** The axis message being wrapped. */
   private Message mMessage;

   /**
    * Constructs the soap message wrapper around the given Axis message.
    *
    * @param aMessage
    */
   public AeSOAPMessageWrapper(Message aMessage)
   {
      setMessage(aMessage);
   }

   /**
    * @see javax.xml.soap.SOAPMessage#addAttachmentPart(javax.xml.soap.AttachmentPart)
    */
   public void addAttachmentPart(AttachmentPart aAttachmentPart)
   {
      getMessage().addAttachmentPart(aAttachmentPart);
   }

   /**
    * @see javax.xml.soap.SOAPMessage#countAttachments()
    */
   public int countAttachments()
   {
      return getMessage().countAttachments();
   }

   /**
    * @see javax.xml.soap.SOAPMessage#createAttachmentPart()
    */
   public AttachmentPart createAttachmentPart()
   {
      return getMessage().createAttachmentPart();
   }

   
   
   /**
    * @see javax.xml.soap.SOAPMessage#getAttachments()
    */
   public Iterator getAttachments()
   {
      return getMessage().getAttachments();
   }

   /**
    * @see javax.xml.soap.SOAPMessage#getAttachments(javax.xml.soap.MimeHeaders)
    */
   public Iterator getAttachments(MimeHeaders aHeaders)
   {
      return getMessage().getAttachments(aHeaders);
   }

   /**
    * @see javax.xml.soap.SOAPMessage#getContentDescription()
    */
   public String getContentDescription()
   {
      return getMessage().getContentDescription();
   }

   /**
    * @see javax.xml.soap.SOAPMessage#getMimeHeaders()
    */
   public MimeHeaders getMimeHeaders()
   {
      return getMessage().getMimeHeaders();
   }

   /**
    * @see javax.xml.soap.SOAPMessage#getProperty(java.lang.String)
    */
   public Object getProperty(String aProperty) throws SOAPException
   {
      return getMessage().getProperty(aProperty);
   }

   /**
    * @see javax.xml.soap.SOAPMessage#getSOAPBody()
    */
   public SOAPBody getSOAPBody() throws SOAPException
   {
      return getMessage().getSOAPBody();
   }

   /**
    * @see javax.xml.soap.SOAPMessage#getSOAPHeader()
    */
   public SOAPHeader getSOAPHeader() throws SOAPException
   {
      return AeAxisObjectProxyFactory.getSOAPHeaderProxy((org.apache.axis.message.SOAPHeader) getMessage().getSOAPHeader());
   }

   /**
    * @see javax.xml.soap.SOAPMessage#getSOAPPart()
    */
   public SOAPPart getSOAPPart()
   {
      return getMessage().getSOAPPart();
   }

   /**
    * @see javax.xml.soap.SOAPMessage#removeAllAttachments()
    */
   public void removeAllAttachments()
   {
      getMessage().removeAllAttachments();
   }
   
   /**
    * @see javax.xml.soap.SOAPMessage#removeAttachments(MimeHeaders)
    */
   public void removeAttachments(MimeHeaders aHeaders)
   {
      // TODO: remove attachments by headers. do we need this?
   }

   /**
    * @see javax.xml.soap.SOAPMessage#saveChanges()
    */
   public void saveChanges() throws SOAPException
   {
      getMessage().saveChanges();
   }

   /**
    * @see javax.xml.soap.SOAPMessage#saveRequired()
    */
   public boolean saveRequired()
   {
      return getMessage().saveRequired();
   }

   /**
    * @see javax.xml.soap.SOAPMessage#setContentDescription(java.lang.String)
    */
   public void setContentDescription(String aDescription)
   {
      getMessage().setContentDescription(aDescription);
   }

   /**
    * @see javax.xml.soap.SOAPMessage#setProperty(java.lang.String, java.lang.Object)
    */
   public void setProperty(String aProperty, Object aValue) throws SOAPException
   {
      getMessage().setProperty(aProperty, aValue);
   }

   /**
    * @see javax.xml.soap.SOAPMessage#writeTo(java.io.OutputStream)
    */
   public void writeTo(OutputStream aStream) throws SOAPException, IOException
   {
      getMessage().writeTo(aStream);
   }

   /**
    * @return Returns the message.
    */
   protected Message getMessage()
   {
      return mMessage;
   }

   /**
    * @param aMessage The message to set.
    */
   protected void setMessage(Message aMessage)
   {
      mMessage = aMessage;
   }
}
