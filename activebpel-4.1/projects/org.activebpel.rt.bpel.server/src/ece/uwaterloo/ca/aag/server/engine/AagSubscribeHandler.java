package ece.uwaterloo.ca.aag.server.engine;

import ece.uwaterloo.ca.aag.platform.abaxis.client.AagABAxisClient;
import ece.uwaterloo.ca.aag.server.response.AagSubscribeResponse;
import ece.uwaterloo.ca.aag.subscribe.IAagSubscribeResponse;
import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.IAeEndpointReference;
import org.activebpel.rt.bpel.impl.IAagSubscribeInvokeInternal;
import org.activebpel.rt.context.IAagIncomingContextData;
import org.apache.muse.ws.addressing.EndpointReference;
import org.apache.muse.ws.addressing.WsaConstants;
import org.apache.muse.ws.addressing.soap.SoapFault;
import org.apache.muse.ws.notification.impl.TopicFilter;
import org.apache.muse.ws.notification.remote.NotificationProducerClient;
import org.apache.muse.ws.notification.remote.SubscriptionClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;
import java.util.List;
import java.util.LinkedList;

/**
 * Created by Allen Ajit George
 * Date: Mar 3, 2008
 * Time: 3:38:27 PM
 */
public class AagSubscribeHandler {
   public List<IAagSubscribeResponse> handleSubscribe(IAagSubscribeInvokeInternal subsInvoke) {
      List<IAagSubscribeResponse> producerResponses = new LinkedList<IAagSubscribeResponse>();

      try {
         List<IAagIncomingContextData> contextParams = subsInvoke.getContextParameters();

         for (IAagIncomingContextData cData : contextParams) {
            EndpointReference consumerReference = subsInvoke.getConsumerEPR();
            EndpointReference publisherReference = convertAeEPRToMuseEPR(cData.getServiceEPR());

            // XXX Related to Muse-149. Use a 'dummy' prefix, otherwise you get the following error:
            /*
             * Mar 4, 2008 12:52:17 PM org.activebpel.rt.AeException logError
             *  SEVERE: com.ctc.wstx.exc.WstxUnexpectedCharException: Unexpected character '=' (code 61) (expected a name start character) at [row,col {unknown-source}]: [1,1824]
             *  org.apache.muse.ws.addressing.soap.SoapFault: com.ctc.wstx.exc.WstxUnexpectedCharException: Unexpected character '=' (code 61) (expected a name start character) at [row,col {unknown-source}]: [1,1824]
             *          at org.apache.muse.core.AbstractResourceClient.invoke(AbstractResourceClient.java:298)
             *          at org.apache.muse.core.AbstractResourceClient.invoke(AbstractResourceClient.java:254)
             *          at org.apache.muse.ws.notification.remote.NotificationProducerClient.subscribe(NotificationProducerClient.java:97)
             *          at ece.uwaterloo.ca.aag.server.engine.AagSubscribeHandler.handleSubscribe(AagSubscribeHandler.java:47)
             *          at ece.uwaterloo.ca.aag.server.engine.AagSubscriber.handleSubscribe(AagSubscriber.java:26)
             *          at org.activebpel.rt.bpel.server.engine.AeInMemoryQueueManager$2.run(AeInMemoryQueueManager.java:181)
             *          at org.activebpel.work.AeDelegatingWork.run(AeDelegatingWork.java:73)
             *          at org.activebpel.work.AeProcessWorkManager$AeProcessWork.run(AeProcessWorkManager.java:265)
             *          at org.activebpel.work.AeDelegatingWork.run(AeDelegatingWork.java:73)
             *          at org.activebpel.work.AeExceptionReportingWork.run(AeExceptionReportingWork.java:69)
             *          at org.activebpel.work.AeWorkerThread.run(AeWorkerThread.java:153)
             */
            QName topicQName = new QName(cData.getTopicNamespace(), cData.getTopicName(), "tnPfx");
            TopicFilter tFilter = new TopicFilter(topicQName);

            // We're going to claim that we're an anonymous subscriber
            NotificationProducerClient npc = new NotificationProducerClient(publisherReference, WsaConstants.ANONYMOUS_EPR, new AagABAxisClient());

            // Each subscription is represented by a WS-Resource that's at a specified endpoint with reference parameters
            SubscriptionClient subsResource = npc.subscribe(consumerReference, tFilter, null);
            // Getting the endpoint of the subscription WS-Resource
            EndpointReference subscriptionEPR = subsResource.getDestination();
            Element[] referenceArray = subscriptionEPR.getParameters();
            for (Element e : referenceArray) {
               if (e.getLocalName().compareToIgnoreCase("ResourceId") == 0) {
                  System.out.println("Value of subscription resource ID is: " + e.getTextContent());
               }
            }

            producerResponses.add(new AagSubscribeResponse(cData.getStatefulParameterName(), subsResource, subscriptionEPR));
         }
      } catch (Throwable t) {
         // FIXME Do something reasonable here
         AeException.logError(t, t.getMessage());
         // Supposedly this is a fault
         producerResponses.add(new AagSubscribeResponse(null));
      }

      return producerResponses;
   }

   EndpointReference convertAeEPRToMuseEPR(IAeEndpointReference aEPRToConvert) throws SoapFault {
      Document eprDoc = aEPRToConvert.toDocument();
      eprDoc.normalize();
      EndpointReference museReference = new EndpointReference(eprDoc.getDocumentElement());
      return museReference;
   }
}
