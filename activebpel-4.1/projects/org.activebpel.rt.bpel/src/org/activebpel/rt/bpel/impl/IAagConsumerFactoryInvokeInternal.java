package org.activebpel.rt.bpel.impl;

import javax.xml.namespace.QName;

/**
 * Created by Allen Ajit George
 * Date: Apr 28, 2008
 * Time: 2:51:42 PM
 */
public interface IAagConsumerFactoryInvokeInternal {
   /**
    * Accessor for the process id.
    */
   public long getProcessId();

   /**
    * Accessor for the process <code>QName</code>.
    */
   public QName getProcessQName();

   /**
    * Accessor for the BPELConsumerFactory URL
    */
   public String getEngineURL();

   /**
    * Returns the process to be called when the BPELConsumer has been created
    *
    * @return
    */
   public AeBusinessProcess getAeBusinessProcess();

   public void dereferenceProcess();
}
