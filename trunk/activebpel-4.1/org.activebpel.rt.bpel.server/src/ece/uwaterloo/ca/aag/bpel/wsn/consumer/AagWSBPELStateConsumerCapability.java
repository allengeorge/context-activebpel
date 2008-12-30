package ece.uwaterloo.ca.aag.bpel.wsn.consumer;

import org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal;
import org.activebpel.rt.bpel.server.engine.AeEngineFactory;
import org.apache.muse.core.AbstractCapability;
import org.apache.muse.ws.addressing.EndpointReference;
import org.apache.muse.ws.addressing.soap.SoapFault;
import org.apache.muse.ws.notification.NotificationConsumer;
import org.apache.muse.ws.notification.NotificationMessage;
import org.apache.muse.ws.notification.NotificationMessageListener;
import org.apache.muse.ws.notification.WsnConstants;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.namespace.QName;
import java.util.Map;

/**
 * Created by Allen Ajit George
 * Date: Mar 6, 2008
 * Time: 2:34:49 PM
 */
public class AagWSBPELStateConsumerCapability extends AbstractCapability implements NotificationMessageListener {
   public void initializeCompleted() throws SoapFault {
      super.initializeCompleted();

      /*
       * XXX I don't know if this is a bug or not, but a resource's initialization parameters are different from a capabilities...
       *
       * Now that I think about it, it's probably not a bug, because it's very possible that a capability's initialization parameters
       * are different from a resource's
       *
       * Method I used in the past:
       * Map paramMap = getInitializationParameters();
       *
       * The above method uses the initialization parameter map _for the capability_
       *
       * I am not doing null pointer checks in the below code because if I don't have the process ID, nothing will work
       */

      Map paramMap = getResource().getInitializationParameters();

      Object mapValue;

      mapValue = paramMap.get(IAagConsumerCapabilityConstants.AAG_CONSUMER_CAPABILITY_PROCESS_ID_PARAM);
      this.processID = ((Long) mapValue).longValue();

      // I don't _really_ need process QName, so I won't die if it's unavailable
      mapValue = paramMap.get(IAagConsumerCapabilityConstants.AAG_CONSUMER_CAPABILITY_PROCESS_NAME_PARAM);
      if (mapValue != null) {
         this.processQName = (QName) mapValue;
      } else {
         getLog().warning("process QName is not set");
      }

      NotificationConsumer wsn = (NotificationConsumer) getResource().getCapability(WsnConstants.CONSUMER_URI);
      wsn.addMessageListener(this);
   }

   public boolean accepts(NotificationMessage message) {
      return true;
   }

   public void process(NotificationMessage message) throws SoapFault {
      // TODO Find a way to parse and print the message ID
      getLog().info("received notification message");
      IAeBusinessProcessEngineInternal processEngine = AeEngineFactory.getEngine();

      EndpointReference subscriptionReference = message.getSubscriptionReference();
      /*
       * getMessageContent gets children under <wsnt:Message>
       *
       * Sample XML:
       *  <wsnt:Message>
       *    <wsrf-rp:ResourcePropertyValueChangeNotification xmlns:wsrf-rp="http://docs.oasis-open.org/wsrf/rp-2">
       *      <wsrf-rp:OldValues>
       *        <ship:ShipmentCondition xmlns:ship="http://delivery.company/shipping/Shipment">Undelivered</ship:ShipmentCondition>
       *      </wsrf-rp:OldValues>
       *      <wsrf-rp:NewValues>
       *        <ship:ShipmentCondition xmlns:ship="http://delivery.company/shipping/Shipment">Erik</ship:ShipmentCondition>
       *      </wsrf-rp:NewValues>
       *    </wsrf-rp:ResourcePropertyValueChangeNotification>
       *  </wsnt:Message>
       *
       * The current design is inefficient: there is one subscription per topic. So, when incoming notifications are received,
       * there should only be a single child under <OldValues/> and a single child under <NewValues/>
       */
      Element propChange = message.getMessageContent(WSRF_RP_PROPERTY_CHANGE_QNAME);
      if (propChange != null) {
         NodeList newValuesNodeList = propChange.getElementsByTagNameNS(WSRF_RP_NS, WSRF_RP_NEWVALUES_TAG);
         if (newValuesNodeList.getLength() != 1) {
            getLog().warning("multiple <NewValues/> elements; using changed properties in first element only");
         }
         NodeList changedProperties = newValuesNodeList.item(0).getChildNodes();
         if (changedProperties.getLength() == 1) {
            /*
             * This is the actual element with the changed value. An example is:
             *
             * <ship:ShipmentCondition>Condition-300-2</ship:ShipmentCondition>
             *
             * changedProperties XML would look like:
             * <wsrf-rp:NewValues>
             *    <ship:ShipmentCondition>Condition-300-2</ship:ShipmentCondition>
             * </wsrf-rp:NewValues>
             */
            Element newValueElement = (Element) changedProperties.item(0);
            processEngine.updateStatefulVariable(processID, subscriptionReference, newValueElement);
         }  else {
            getLog().severe("multiple <NewValues/> elements for a single context variable subscription reference");
         }
      } else {
         getLog().warning("no <ResourcePropertyValueChangeNotification/> element in notification message");
      }
   }

   private long processID;
   private QName processQName;

   private static final QName WSRF_RP_PROPERTY_CHANGE_QNAME = new QName("http://docs.oasis-open.org/wsrf/rp-2", "ResourcePropertyValueChangeNotification");
   private static final String WSRF_RP_NS = "http://docs.oasis-open.org/wsrf/rp-2";
   private static final String WSRF_RP_NEWVALUES_TAG = "NewValues";
}
