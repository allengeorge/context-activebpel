package org.activebpel.rt.context;

import org.activebpel.rt.bpel.IAeEndpointReference;

/**
 * Created by Allen Ajit George
 * Date: Feb 29, 2008
 * Time: 4:22:12 PM
 */
public interface IAagIncomingContextData {
   public String getStatefulParameterName();

   public String getTopicNamespace();

   public String getTopicName();

   public IAeEndpointReference getServiceEPR();
}
