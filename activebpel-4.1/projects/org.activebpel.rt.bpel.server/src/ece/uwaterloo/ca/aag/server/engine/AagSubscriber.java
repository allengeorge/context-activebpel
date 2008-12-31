package ece.uwaterloo.ca.aag.server.engine;

import ece.uwaterloo.ca.aag.server.response.AagSubscribeQueuer;
import ece.uwaterloo.ca.aag.subscribe.IAagSubscribeResponse;
import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.impl.IAagSubscribeInvokeInternal;

import java.util.List;

/**
 * Created by Allen Ajit George
 * Date: Mar 3, 2008
 * Time: 3:29:55 PM
 */
public class AagSubscriber {
   public AagSubscriber(IAagSubscribeInvokeInternal aSubsInvokeQueueObject, AagSubscribeHandler aSubscribeHandler) {
      mSubsInvokeQueueObject = aSubsInvokeQueueObject;
      mSubscribeHandler = aSubscribeHandler;
      subscribeQueuer = new AagSubscribeQueuer(aSubsInvokeQueueObject);
   }

   public void handleSubscribe() {
      IAagSubscribeInvokeInternal subsInvoke = getSusbsInvokeQueueObject();
      try
      {
         List<IAagSubscribeResponse> subscribeResponses = getSubscribeHandler().handleSubscribe(subsInvoke);

         if (subscribeResponses != null) {
            for (IAagSubscribeResponse response : subscribeResponses) {
               if (response.isFault()) {
                  handleSubscriptionFault();
               } else {
                  handleSubscription(response);
               }
            }
         }
      }
      catch (Exception e)
      {
         AeException.logError(e,e.getMessage());
      }
   }

   private IAagSubscribeInvokeInternal getSusbsInvokeQueueObject() {
      return mSubsInvokeQueueObject;
   }

   private AagSubscribeHandler getSubscribeHandler() {
      return mSubscribeHandler;
   }

   private void handleSubscriptionFault() {
      // TODO Implement handleSubscriptionFault
   }

   private void handleSubscription(IAagSubscribeResponse producerResponse) {
      // Would have to have an interface in the project containing AeBusinessProcess, and create a concrete object here
      getSubscribeQueuer().onSubscription(producerResponse);
   }

   private AagSubscribeQueuer getSubscribeQueuer() {
      return this.subscribeQueuer;
   }

   private AagSubscribeQueuer subscribeQueuer;
   private IAagSubscribeInvokeInternal mSubsInvokeQueueObject;
   private AagSubscribeHandler mSubscribeHandler;
}
