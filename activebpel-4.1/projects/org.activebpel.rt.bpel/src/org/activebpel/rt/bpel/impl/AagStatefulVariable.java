package org.activebpel.rt.bpel.impl;

import ece.uwaterloo.ca.aag.subscribe.IAagSubscribeResponse;
import org.activebpel.rt.bpel.IAagStatefulVariable;
import org.activebpel.rt.bpel.def.AeVariableDef;
import org.activebpel.rt.bpel.impl.activity.IAeVariableContainer;
import org.activebpel.rt.message.IAeMessageData;
import org.apache.muse.ws.addressing.EndpointReference;
import org.apache.muse.ws.notification.remote.SubscriptionClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import java.util.*;

/**
 * Created by Allen Ajit George
 * Date: Feb 28, 2008
 * Time: 5:55:24 PM
 */
public class AagStatefulVariable extends AeVariable implements IAagStatefulVariable {
   public AagStatefulVariable(IAeVariableContainer variableContainer, AeVariableDef aVarDef) {
      super(variableContainer, aVarDef);
      // I know this is true because I was created.
      mIsStateful = true;
      mIsSubscribed = false;
   }

   public boolean isStatefulVariable() {
      return mIsStateful;
   }

   public boolean isSubscribed() {
      return mIsSubscribed;
   }

   public void setSubscription(IAagSubscribeResponse subsResponse) {
      if (subscriptionMap == null) {
         subscriptionMap = new HashMap<EndpointReference, AagSubscriptionInfo>();
      }

      AagSubscriptionInfo subsInfo = new AagSubscriptionInfo(subsResponse.getStatefulParameterName(), subsResponse.getSubscriptionResource());
      subscriptionMap.put(subsResponse.getSubscriptionEPR(), subsInfo);
      mIsSubscribed = true;
   }

   // FIXME Lock variable before playing with it...
   public void updateValue(EndpointReference subscriptionReference, Element newValue) throws AeUninitializedVariableException {
      System.out.println("Updating variable value");

      IAeMessageData currentMessageData = getMessageData();

      if (currentMessageData.getPartCount() != 1) {
         System.err.println("error: message has more than 1 part; only document-literal/wrapped supported");
      } else {
         Iterator it = currentMessageData.getPartNames();
         String dataPartName = (String) it.next();
         // Gah...should I just throw an NPE?
         Document currentDataDoc = (Document) currentMessageData.getData(dataPartName);
         currentDataDoc.normalize();
         Element docElement = currentDataDoc.getDocumentElement();

         AagSubscriptionInfo subsInfo = subscriptionMap.get(subscriptionReference);
         if (subsInfo != null) {
            NodeList contextVarNodes = docElement.getElementsByTagName(subsInfo.partName);

            for (int i = 0; i < contextVarNodes.getLength(); i++) {
               Node newValueNode = currentDataDoc.importNode(newValue, true);
               Node parentNode = contextVarNodes.item(i).getParentNode();
               parentNode.replaceChild(newValueNode, contextVarNodes.item(i));
            }
         } else {
            System.err.println("error: could not find subscription info for subscription reference: ");
            System.err.println(subscriptionReference);
         }
      }
   }

   public List<EndpointReference> getSubscriptions() {
      // Hmm...why don't I simply return an iterator?
      return new ArrayList<EndpointReference>(subscriptionMap.keySet());
   }

   private class AagSubscriptionInfo {
      public AagSubscriptionInfo(String partName, SubscriptionClient subscriptionClient) {
         this.partName = partName;
         this.subscriptionClient = subscriptionClient;
      }

      private String partName;
      private SubscriptionClient subscriptionClient;
   }

   private Map<EndpointReference, AagSubscriptionInfo> subscriptionMap;
   /**
    * Indicates whether this variable could be updated from an outside source
    */
   private boolean mIsStateful;
   private boolean mIsSubscribed;
}
