package ece.uwaterloo.ca.aag.server.response;

import org.apache.muse.ws.addressing.EndpointReference;
import org.apache.muse.ws.notification.remote.SubscriptionClient;
import org.w3c.dom.Node;
import ece.uwaterloo.ca.aag.subscribe.IAagSubscribeResponse;

/**
 * Created by Allen Ajit George
 * Date: May 1, 2008
 * Time: 2:00:39 PM
 */
public class AagSubscribeResponse implements IAagSubscribeResponse {
   public AagSubscribeResponse(Node faultNode) {
      this.isFault = true;
      this.faultNode = faultNode;
      this.statefulParameterName = null;
      this.subscriptionEPR = null;
      this.subscriptionClient = null;
   }

   public AagSubscribeResponse(String statefulParameterName, SubscriptionClient subsResource, EndpointReference subscriptionEPR) {
      this.isFault = false;
      this.faultNode = null;
      this.statefulParameterName = statefulParameterName;
      this.subscriptionEPR = subscriptionEPR;
      this.subscriptionClient = subsResource;
   }

   public boolean isFault() {
      return isFault;
   }

   public Node getFaultNode() {
      return faultNode;
   }

   public String getStatefulParameterName() {
      return statefulParameterName;
   }

   public EndpointReference getSubscriptionEPR() {
      return subscriptionEPR;
   }

   public SubscriptionClient getSubscriptionResource() {
      return subscriptionClient;  
   }

   private boolean isFault;
   private Node faultNode;
   private String statefulParameterName;
   private EndpointReference subscriptionEPR;
   private SubscriptionClient subscriptionClient;
}
