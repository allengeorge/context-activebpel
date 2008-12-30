//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis.bpel/src/org/activebpel/rt/axis/bpel/handlers/AeWsaHeaderHandler.java,v 1.3 2006/12/20 23:26:4
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
package org.activebpel.rt.axis.bpel.handlers;

import org.activebpel.rt.IAeConstants;
import org.activebpel.rt.axis.bpel.handlers.soap.AeAxisObjectProxyFactory;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.impl.addressing.AeAddressingHeaders;
import org.activebpel.rt.bpel.impl.addressing.AeWsAddressingFactory;
import org.activebpel.rt.bpel.impl.addressing.IAeAddressingDeserializer;
import org.activebpel.rt.bpel.impl.addressing.IAeAddressingHeaders;
import org.apache.axis.AxisFault;
import org.apache.axis.MessageContext;
import org.apache.axis.handlers.BasicHandler;
import org.apache.axis.message.SOAPHeaderElement;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Axis handler that flags all WS-Addressing headers as understood
 */
public class AeWsaHeaderHandler extends BasicHandler
{
   public static final String AE_WSA_HEADERS_PROPERTY = IAeAddressingHeaders.class.getName();
   private static Set mWSASoapHeaderElementNames = new HashSet();
   static
   {
     mWSASoapHeaderElementNames.add(IAeAddressingHeaders.WSA_TO);       
     mWSASoapHeaderElementNames.add(IAeAddressingHeaders.WSA_FROM); 
     mWSASoapHeaderElementNames.add(IAeAddressingHeaders.WSA_REPLY_TO);
     mWSASoapHeaderElementNames.add(IAeAddressingHeaders.WSA_FAULT_TO);
     mWSASoapHeaderElementNames.add(IAeAddressingHeaders.WSA_ACTION); 
     mWSASoapHeaderElementNames.add(IAeAddressingHeaders.WSA_MESSAGE_ID);
     mWSASoapHeaderElementNames.add(IAeAddressingHeaders.WSA_RELATES_TO);     
     mWSASoapHeaderElementNames.add(IAeAddressingHeaders.WSA_RECIPIENT);
   }
   
   /**
    * Overrides method to flag all wsa headers as understood 
    * @see org.apache.axis.Handler#invoke(org.apache.axis.MessageContext)
    */
   public void invoke(MessageContext aMsgContext) throws AxisFault
   {
      try
      {
         if (aMsgContext.getCurrentMessage() == null)
         {
            return;
         }
         
         /*
          * Note: we proxy the message context here in order to fix some problems with Axis DOM interface 
          * implementations.  Specifically, we need to convert Axis Elements to DOM Elements for
          * persistence.  The Axis Nodes don't import properly (the namespace decls aren't imported).
          * The org.activebpel.rt.axis.bpel.handlers.soap package contains some proxies around the
          * various Axis objects that help with this.
          */
         SOAPHeader axisHeader = aMsgContext.getCurrentMessage().getSOAPHeader();
         IAeAddressingHeaders wsa = new AeAddressingHeaders(IAeConstants.WSA_NAMESPACE_URI);
         IAeAddressingDeserializer des = AeWsAddressingFactory.getInstance().getDeserializer(IAeConstants.WSA_NAMESPACE_URI);         
         if (axisHeader.getChildNodes().getLength() > 0)
         {
            SOAPHeader hdr = AeAxisObjectProxyFactory.getSOAPHeaderProxy((org.apache.axis.message.SOAPHeader) axisHeader, SOAPHeader.class);
            try
            {
               wsa = des.deserializeHeaders(hdr, wsa);
            }
            catch (AeBusinessProcessException ex)
            {
               throw new AxisFault(ex.getLocalizedMessage(), ex);
            }
         }

         aMsgContext.setProperty(AE_WSA_HEADERS_PROPERTY, wsa);         
         
         for (Iterator it = axisHeader.getChildElements(); it.hasNext(); )
         {
            SOAPHeaderElement element = (SOAPHeaderElement) it.next();
            if (des.isEndpointHeader(element))
            {
               element.setProcessed(true);
            }
         }
      }
      catch (SOAPException ex)
      {
         throw new AxisFault(ex.getLocalizedMessage(), ex);
      }
   }

}
