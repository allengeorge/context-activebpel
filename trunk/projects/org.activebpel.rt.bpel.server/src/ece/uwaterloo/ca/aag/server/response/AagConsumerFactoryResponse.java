package ece.uwaterloo.ca.aag.server.response;

import org.apache.muse.ws.addressing.EndpointReference;
import org.w3c.dom.Node;

/**
 * Created by Allen Ajit George
 * Date: May 1, 2008
 * Time: 10:24:29 AM
 */
public class AagConsumerFactoryResponse {
   public AagConsumerFactoryResponse(EndpointReference consumerReference) {
      this.isFault = false;
      this.faultNode = null;
      this.consumerReference = consumerReference;
   }

   public AagConsumerFactoryResponse(Node faultNode) {
      this.isFault = true;
      this.faultNode = faultNode;
      this.consumerReference = null;
   }

   public boolean isFault() {
      return isFault;
   }

   public Node getFaultNode () {
      return faultNode;
   }

   public EndpointReference getConsumerEPR() {
      return consumerReference;
   }

   private boolean isFault;
   private Node faultNode;
   private EndpointReference consumerReference;
}
