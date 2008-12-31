//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/receive/IAeExtendedMessageContext.java,v 1.2 2007/02/13 15:26:5
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

import java.util.Map;

import javax.security.auth.Subject;

import org.activebpel.rt.bpel.impl.reply.IAeDurableReplyInfo;
import org.activebpel.wsio.receive.IAeMessageContext;
import org.w3c.dom.Element;

/**
 * Extended Message Context interface supporting the additional information required 
 * by our pluggable receive handlers such as those for SOAP messages, including WSRM
 */
public interface IAeExtendedMessageContext extends IAeMessageContext
{
   /**
    * @return the url request came in on, as determined by the inbound transport handler
    */
   public String getTransportUrl();
   
   /**
    * @return the encoding style (Encoded or Literal)
    */
   public String getEncodingStyle();
   
   /**
    * @return Element containing selected header elements mapped from the inbound message
    */
   public Element getMappedHeaders();
   
   /**
    * @return Information required to support creation of a durable reply for this request
    */
   public IAeDurableReplyInfo getDurableReplyInfo();
   
   /**
    * @param aKey
    * @return property object associated with the key
    */
   public Object getProperty(Object aKey);
   
   /**
    * @return map of properties
    */
   public Map getProperties();   
   
   /**
    * Add a reference property to be serialied as a SOAPHeaderElement
    * @param aRefProp
    */
   public void addReferenceProperty(Element aRefProp);

   /**
    * @return the subject containing the principals used to authorize a request
    */
   public Subject getSubject();
}
