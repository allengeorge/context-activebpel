package org.activebpel.rt.bpel.impl;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAagSubscriptionInvokeActivity;
import org.activebpel.rt.bpel.IAagStatefulVariable;
import org.activebpel.rt.context.IAagIncomingContextData;
import org.apache.muse.ws.addressing.EndpointReference;

import java.util.List;

/**
 * Created by Allen Ajit George
 * Date: Feb 28, 2008
 * Time: 5:17:05 PM
 */
public interface IAagStatefulProcess {
   /**
    * Returns true if the process is configured to create target xpath.
    */
   public boolean isStatefulProcess();

   /**
    * Will queue a subscribe to take place.  A framework in the engine
    * will typically perform the actual subscribe and then call back
    * the subscribe response receiver.
    *
    * @param aSubsInvoke           The object to receive the callback when the subscribe response is ready.
    * @throws org.activebpel.rt.bpel.AeBusinessProcessException
    *          Thrown if error occurs setting the receiver.
    */
   public void queueSubscribe(IAagSubscriptionInvokeActivity aSubsInvoke, List<IAagIncomingContextData> aContextParameters) throws AeBusinessProcessException;

   public void addSubscription(EndpointReference subscriptionEPR, IAagStatefulVariable statefulOutputVariable);
}
