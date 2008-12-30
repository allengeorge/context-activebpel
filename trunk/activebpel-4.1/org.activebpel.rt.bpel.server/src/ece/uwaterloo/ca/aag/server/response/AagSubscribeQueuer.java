package ece.uwaterloo.ca.aag.server.response;

import ece.uwaterloo.ca.aag.subscribe.IAagSubscribeResponse;
import org.activebpel.rt.bpel.impl.IAagSubscribeInvokeInternal;
import org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal;
import org.activebpel.rt.bpel.server.engine.AeEngineFactory;

/**
 * Created by Allen Ajit George
 * Date: May 1, 2008
 * Time: 1:37:06 PM
 */
public class AagSubscribeQueuer {
   public AagSubscribeQueuer(IAagSubscribeInvokeInternal aSubsInvokeQueueObject) {
      this.subsInvokeQueueObject = aSubsInvokeQueueObject;
   }

   public void onSubscription(IAagSubscribeResponse subsResponse) {
    getEngine().queueSubscribeData(subsInvokeQueueObject.getProcessId(),
                                   subsInvokeQueueObject.getProcessName(),
                                   subsInvokeQueueObject.getLocationPath(),
                                   subsResponse);
   }

   private IAeBusinessProcessEngineInternal getEngine() {
      return AeEngineFactory.getEngine();
   }

   private IAagSubscribeInvokeInternal subsInvokeQueueObject;
}
