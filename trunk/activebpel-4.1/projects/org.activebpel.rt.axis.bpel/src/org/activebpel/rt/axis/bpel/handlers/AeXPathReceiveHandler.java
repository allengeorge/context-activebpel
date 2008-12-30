//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis.bpel/src/org/activebpel/rt/axis/bpel/handlers/AeXPathReceiveHandler.java,v 1.9 2006/09/15 21:25:0
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

import java.util.HashMap;
import java.util.Iterator;

import org.activebpel.rt.IAeConstants;
import org.activebpel.rt.axis.bpel.AeMessages;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.apache.axis.AxisFault;
import org.apache.axis.Message;
import org.apache.axis.MessageContext;
import org.apache.axis.message.SOAPEnvelope;
import org.jaxen.XPath;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Maps SOAP Headers into process variable properties
 */
public class AeXPathReceiveHandler extends AeXPathHandler
{
   /**
    * @see org.apache.axis.Handler#invoke(org.apache.axis.MessageContext)
    */
   public void invoke(MessageContext aMsgContext) throws AxisFault
   {
      try
      {
         // Get xpaths
         HashMap xpaths = getXpaths(aMsgContext);
         // Get the message
         Message msg = aMsgContext.getCurrentMessage();
         if (msg == null)
            return;
         if (AeUtil.isNullOrEmpty(msg.getSOAPPartAsString()))
            return;
         
         // get the SOAPEnvelope
         SOAPEnvelope reqEnv = msg.getSOAPEnvelope();
         Document domReq = reqEnv.getAsDocument();
         // Add selected nodes to operation element
         Document headerDoc = AeXmlUtil.newDocument();
         Element headers = headerDoc.createElementNS(IAeConstants.ABX_NAMESPACE_URI, "abx:Headers"); //$NON-NLS-1$
         headers.setAttributeNS(IAeConstants.W3C_XMLNS, "xmlns:abx", IAeConstants.ABX_NAMESPACE_URI ); //$NON-NLS-1$
         // get passthrough option
         headers.setAttribute("passthrough", (String) getOption("passthrough")); //$NON-NLS-1$ //$NON-NLS-2$

         for (Iterator it = xpaths.keySet().iterator(); it.hasNext();)
         {
            String key = (String) it.next();
            XPath xpath = (XPath) xpaths.get(key);
            Element selected = (Element) xpath.selectSingleNode(domReq);
            if (selected != null)
            {
               Element target = headerDoc.createElementNS(selected.getNamespaceURI(), selected.getNodeName());
               AeXmlUtil.copyNodeContents(selected, target);
               headers.appendChild(headers.getOwnerDocument().importNode(target, true));
            }
         }
         headerDoc.appendChild(headers);
         aMsgContext.setProperty(AE_CONTEXT_MAPPED_PROPERTIES, headers);
      }
      catch (Exception e)
      {
         throw new AxisFault(AeMessages.getString("AeXPathReceiveHandler.Error_0"), e); //$NON-NLS-1$
      }
   }
}
