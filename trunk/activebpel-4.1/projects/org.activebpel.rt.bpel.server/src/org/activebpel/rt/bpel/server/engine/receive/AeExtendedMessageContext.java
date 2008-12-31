//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/receive/AeExtendedMessageContext.java,v 1.2 2007/02/13 15:26:5
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
package org.activebpel.rt.bpel.server.engine.receive;

import java.util.HashMap;
import java.util.Map;

import javax.security.auth.Subject;

import org.activebpel.rt.bpel.impl.reply.IAeDurableReplyInfo;
import org.activebpel.wsio.receive.AeMessageContext;
import org.activebpel.wsio.receive.IAeMessageContext;
import org.w3c.dom.Element;

/**
 * Extended Message Context supporting the additional information required 
 * by our pluggable receive handlers such as those for SOAP messages, including WSRM
 */
public class AeExtendedMessageContext extends AeMessageContext implements IAeExtendedMessageContext
{
   private IAeDurableReplyInfo mDurableReplyInfo;
   private Element mMappedHeaders;
   private String mTransportUrl;
   private String mEncodingStyle;
   private HashMap mProperties;
   private Subject mSubject;

   /**
    * Default c-tor
    */
   public AeExtendedMessageContext()
   {
      super();
   }
   
   /**
    * Copy constructor from IAeMessageContext
    * @param aContext
    */
   public AeExtendedMessageContext(IAeMessageContext aContext)
   {
      super(aContext);
   }

   /**
    * Full Copy constructor
    * @param aContext
    */
   public AeExtendedMessageContext(IAeExtendedMessageContext aContext)
   {
      super(aContext);
      setDurableReplyInfo(aContext.getDurableReplyInfo());
      setTransportUrl(aContext.getTransportUrl());
      setMappedHeaders(aContext.getMappedHeaders());
      setEncodingStyle(aContext.getEncodingStyle());
      getProperties().putAll(aContext.getProperties());
      setSubject(aContext.getSubject());
   }
   
   /**
    * Converts to an instance of this class
    * @param aContext
    */
   public static AeExtendedMessageContext convertToExtended(IAeMessageContext aContext)
   {
      if (aContext instanceof AeExtendedMessageContext)
      {
         return (AeExtendedMessageContext) aContext;
      }
      else
      {
         return new AeExtendedMessageContext(aContext);
      }
   }
   
   /**
    * @return the encodingStyle
    */
   public String getEncodingStyle()
   {
      return mEncodingStyle;
   }

   /**
    * @param aEncodingStyle the encodingStyle to set
    */
   public void setEncodingStyle(String aEncodingStyle)
   {
      mEncodingStyle = aEncodingStyle;
   }
   
   /**
    * @param aDurableReplyInfo the durableReplyInfo to set
    */
   public void setDurableReplyInfo(IAeDurableReplyInfo aDurableReplyInfo)
   {
      mDurableReplyInfo = aDurableReplyInfo;
   }

   /**
    * @param aMappedHeaders the mappedHeaders to set
    */
   public void setMappedHeaders(Element aMappedHeaders)
   {
      mMappedHeaders = aMappedHeaders;
   }

   /**
    * @param aTransportUrl the transportUrl to set
    */
   public void setTransportUrl(String aTransportUrl)
   {
      mTransportUrl = aTransportUrl;
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.receive.IAeExtendedMessageContext#getDurableReplyInfo()
    */
   public IAeDurableReplyInfo getDurableReplyInfo()
   {
      return mDurableReplyInfo;
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.receive.IAeExtendedMessageContext#getMappedHeaders()
    */
   public Element getMappedHeaders()
   {
      return mMappedHeaders;
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.receive.IAeExtendedMessageContext#getTransportUrl()
    */
   public String getTransportUrl()
   {
      return mTransportUrl;
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.receive.IAeExtendedMessageContext#getProperty(java.lang.Object)
    */
   public Object getProperty(Object aKey)
   {
      if (mProperties == null)
      {
         return null;
      }
      
      return mProperties.get(aKey);
   }
   
   /**
    * sets a property
    * @param aKey
    * @param aValue
    */
   public void setProperty(Object aKey, Object aValue)
   {
      getProperties().put(aKey, aValue);
   }
   
   /**
    * @return Map of properties
    */
   public Map getProperties()
   {
      if (mProperties == null)
      {
         mProperties = new HashMap();
      }
      return mProperties;
   }

   /**
    * @return the subject
    */
   public Subject getSubject()
   {
      return mSubject;
   }

   /**
    * @param aSubject the subject to set
    */
   public void setSubject(Subject aSubject)
   {
      mSubject = aSubject;
   }

}
