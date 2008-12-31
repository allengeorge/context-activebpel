// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/visitors/AeWSBPELDefMessagePartsMapVisitor.java,v 1.1 2006/09/11 23:06:2
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
package org.activebpel.rt.bpel.def.visitors;

import javax.wsdl.Message;
import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.AeWSDLDefHelper;
import org.activebpel.rt.bpel.IAeContextWSDLProvider;
import org.activebpel.rt.bpel.def.activity.AeActivityInvokeDef;
import org.activebpel.rt.message.AeMessagePartsMap;

/**
 * Assigns {@link org.activebpel.rt.message.AeMessagePartsMap} info to web
 * service activities.
 */
public class AeWSBPELDefMessagePartsMapVisitor extends AeAbstractDefMessagePartsMapVisitor
{
   /**
    * Constructs the visitor with the given WSDL provider.
    *
    * @param aWSDLProvider
    */
   public AeWSBPELDefMessagePartsMapVisitor(IAeContextWSDLProvider aWSDLProvider)
   {
      super(aWSDLProvider);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityInvokeDef)
    */
   public void visit(AeActivityInvokeDef aDef)
   {
      QName portType = getPartnerRolePortType(aDef);
      String operation = aDef.getOperation();
      String locationPath = aDef.getLocationPath();
   
      if (aDef.getProducerMessagePartsMap() == null)
      {
         AeMessagePartsMap inputMap = createInputMessagePartsMap(portType, operation, null, null, locationPath);
         aDef.setProducerMessagePartsMap(inputMap);
      }
   
      if (aDef.getConsumerMessagePartsMap() == null)
      {
         AeMessagePartsMap outputMap = createOutputMessagePartsMap(portType, operation, null, null, locationPath);
         aDef.setConsumerMessagePartsMap(outputMap);
      }
   
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractDefMessagePartsMapVisitor#createFaultMessagePartsMap(javax.xml.namespace.QName, java.lang.String, javax.xml.namespace.QName, java.lang.String)
    */
   protected AeMessagePartsMap createFaultMessagePartsMap(QName aPortType, String aOperation, QName aFaultName, String aLocationPath)
   {
      Message message = AeWSDLDefHelper.getFaultMessage(getWSDLProvider(), aPortType, aOperation, aFaultName);
      return (message == null) ? null : createMessagePartsMap(message, aLocationPath);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractDefMessagePartsMapVisitor#createInputMessagePartsMap(javax.xml.namespace.QName, java.lang.String, javax.xml.namespace.QName, javax.xml.namespace.QName, java.lang.String)
    */
   protected AeMessagePartsMap createInputMessagePartsMap(QName aPortType, String aOperation, QName aRequestMessageType, QName aResponseMessageType, String aLocationPath)
   {
      Message message = AeWSDLDefHelper.getInputMessage(getWSDLProvider(), aPortType, aOperation);
      return (message == null) ? null : createMessagePartsMap(message, aLocationPath);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractDefMessagePartsMapVisitor#createOutputMessagePartsMap(javax.xml.namespace.QName, java.lang.String, javax.xml.namespace.QName, javax.xml.namespace.QName, java.lang.String)
    */
   protected AeMessagePartsMap createOutputMessagePartsMap(QName aPortType, String aOperation, QName aRequestMessageType, QName aResponseMessageType, String aLocationPath)
   {
      Message message = AeWSDLDefHelper.getOutputMessage(getWSDLProvider(), aPortType, aOperation);
      return (message == null) ? null : createMessagePartsMap(message, aLocationPath);
   }
}
