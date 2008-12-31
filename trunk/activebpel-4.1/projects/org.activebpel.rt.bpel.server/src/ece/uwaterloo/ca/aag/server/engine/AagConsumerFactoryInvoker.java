package ece.uwaterloo.ca.aag.server.engine;

import ece.uwaterloo.ca.aag.server.response.AagConsumerFactoryResponse;
import ece.uwaterloo.ca.aag.server.response.AagCreateConsumerQueuer;
import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.impl.IAagConsumerFactoryInvokeInternal;
import org.w3c.dom.Node;

/**
 * Created by Allen Ajit George
 * Date: Apr 28, 2008
 * Time: 3:16:03 PM
 */
public class AagConsumerFactoryInvoker {

   public AagConsumerFactoryInvoker(IAagConsumerFactoryInvokeInternal aCreateConsumerQueueObject, AagConsumerFactoryInvokeHandler aConsumerFactoryInvokeHandler) {
      mConsumerFactoryInvokeQueueObject = aCreateConsumerQueueObject;
      mConsumerFactoryInvokeHandler = aConsumerFactoryInvokeHandler;
      createConsumerQueuer = new AagCreateConsumerQueuer(mConsumerFactoryInvokeQueueObject);
   }

   public void handleCreateConsumer() {
      IAagConsumerFactoryInvokeInternal consumerFactoryInvoke = getConsumerFactoryInvokeQueueObject();
      try {
         AagConsumerFactoryResponse response = getCreateConsumerHandler().handleCreateConsumer(consumerFactoryInvoke);

         if (response != null) {
            if (response.isFault()) {
               handleConsumerCreationFault(response.getFaultNode());
            } else {
               handleConsumerCreation(response);
            }
         }
      }
      catch (Exception e) {
         AeException.logError(e, e.getMessage());
      }
   }

   private IAagConsumerFactoryInvokeInternal getConsumerFactoryInvokeQueueObject() {
      return mConsumerFactoryInvokeQueueObject;
   }

   private AagConsumerFactoryInvokeHandler getCreateConsumerHandler() {
      return mConsumerFactoryInvokeHandler;
   }

   private void handleConsumerCreationFault(Node faultNode) {
      // TODO Implement handleConsumerCreationFault
   }

   private void handleConsumerCreation(AagConsumerFactoryResponse factoryResponse) {
      getCreateConsumerReceiver().onConsumerCreation(factoryResponse.getConsumerEPR());
   }

   private AagCreateConsumerQueuer getCreateConsumerReceiver() {
      return this.createConsumerQueuer;
   }

   private AagCreateConsumerQueuer createConsumerQueuer;
   private IAagConsumerFactoryInvokeInternal mConsumerFactoryInvokeQueueObject;
   private AagConsumerFactoryInvokeHandler mConsumerFactoryInvokeHandler;
}
