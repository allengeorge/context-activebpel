package org.activebpel.rt.context;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeEndpointReference;
import org.activebpel.rt.bpel.impl.endpoints.AeEndpointFactory;
import org.activebpel.rt.bpel.impl.endpoints.IAeEndpointDeserializer;
import org.activebpel.rt.IAeConstants;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Created by Allen Ajit George
 * Date: Feb 29, 2008
 * Time: 4:22:49 PM
 */
public class AagIncomingContextData implements IAagIncomingContextData {
   public AagIncomingContextData(Element contextElem) throws AeBusinessProcessException {
      NodeList statefulParameterNames = contextElem.getElementsByTagName("StatefulParameterName");
      NodeList topicNames = contextElem.getElementsByTagName("NotificationTopic");
      NodeList topicNamespaces = contextElem.getElementsByTagName("NotificationTopicNamespace");
      NodeList serviceEPRs = contextElem.getElementsByTagName("ServiceEPR");

      if (topicNames.getLength() != 1 || topicNamespaces.getLength() != 1 || serviceEPRs.getLength() != 1 || statefulParameterNames.getLength() != 1) {
         throw new AeBusinessProcessException("Bad Context element in response message");
      }

      mStatefulParameterName = statefulParameterNames.item(0).getTextContent(); 
      mTopicName = ((Element) topicNames.item(0)).getAttribute("name");
      mTopicNamespace = topicNamespaces.item(0).getTextContent();

      // FIXME Assuming a return namespace of WSA 2005/08 for WS-Addressing
      // XXX Unsure if this is a Muse bug or not, but I can't use this function because the containing element does not have localName EndpointReference
      /*if (!AeEndpointFactory.isEndpointReferenceElement((Element) serviceEPRs.item(0))) {
         throw new AeBusinessProcessException("Bad ServiceEPR element in Context element of response message");
      }*/

      /*
       * Relevant classes:
       *
       * AeEndpointFactory
       * AeEndpointReference
       */
      AeEndpointFactory eprFactory = new AeEndpointFactory();
      IAeEndpointDeserializer eprDeserializer = eprFactory.getDeserializer(IAeConstants.WSA_NAMESPACE_URI_2005_08);
      mServiceEPR = eprDeserializer.deserializeEndpoint((Element) serviceEPRs.item(0));
   }

   public String getTopicNamespace() {
      return mTopicNamespace;
   }

   public String getTopicName() {
      return mTopicName;
   }

   public IAeEndpointReference getServiceEPR() {
      return mServiceEPR;
   }

   public String getStatefulParameterName() {
      return mStatefulParameterName;
   }

   private String mStatefulParameterName;
   private String mTopicNamespace;
   private String mTopicName;
   private IAeEndpointReference mServiceEPR; 
}
