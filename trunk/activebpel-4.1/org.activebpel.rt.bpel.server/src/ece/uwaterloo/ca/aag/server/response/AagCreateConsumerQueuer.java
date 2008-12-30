package ece.uwaterloo.ca.aag.server.response;

import org.activebpel.rt.bpel.impl.IAagConsumerFactoryInvokeInternal;
import org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal;
import org.activebpel.rt.bpel.server.engine.AeEngineFactory;
import org.apache.muse.ws.addressing.EndpointReference;

import javax.xml.namespace.QName;

/**
 * Created by Allen Ajit George
 * Date: May 1, 2008
 * Time: 1:36:51 PM
 */
public class AagCreateConsumerQueuer {
   public AagCreateConsumerQueuer(IAagConsumerFactoryInvokeInternal consumerFactoryInvokeQueueObject) {
      this.processID = consumerFactoryInvokeQueueObject.getProcessId();
      this.processQName = consumerFactoryInvokeQueueObject.getProcessQName();
   }

   public void onConsumerCreation(EndpointReference consumerEPR) {
      this.processConsumerEPR = consumerEPR;
      getEngine().queueCreateConsumerData(processID, processQName, processConsumerEPR);
   }

   private IAeBusinessProcessEngineInternal getEngine() {
      return AeEngineFactory.getEngine();
   }

   private long processID;
   private QName processQName;
   private EndpointReference processConsumerEPR;
}
