package org.activebpel.rt.bpel.impl.queue;

import org.activebpel.rt.bpel.impl.IAagConsumerFactoryInvokeInternal;
import org.activebpel.rt.bpel.impl.AeBusinessProcess;

import javax.xml.namespace.QName;

/**
 * Created by Allen Ajit George
 * Date: Apr 28, 2008
 * Time: 2:54:00 PM
 */
public class AagCreateConsumer extends AeAbstractQueuedObject implements IAagConsumerFactoryInvokeInternal {
   public AagCreateConsumer(long processID, QName processQName, String engineURL, AeBusinessProcess process) {
      this.processID = processID;
      this.processQName = processQName;
      this.engineURL = engineURL;
      this.process = process;
   }

   public long getProcessId() {
      return processID;
   }

   public QName getProcessQName() {
      return processQName;
   }

   public String getEngineURL() {
      return engineURL;
   }

   public AeBusinessProcess getAeBusinessProcess() {
      return process;
   }

   public void dereferenceProcess()
   {
      process = null;
   }

   private AeBusinessProcess process;
   private QName processQName;
   private long processID;
   private String engineURL;
}
