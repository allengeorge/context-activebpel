package org.activebpel.rt.bpel;

import ece.uwaterloo.ca.aag.subscribe.IAagSubscribeResponse;

/**
 * Created by Allen Ajit George
 * Date: Feb 29, 2008
 * Time: 1:21:38 PM
 *
 */
public interface IAagSubscriptionReceiver {

   /**
    * Callback when the subscription for the output variable parameters has completed
    *
    * @param statefulParameterName
    * @throws AeBusinessProcessException Allows the receiver to throw an exception.
    */
   public void onSubscribeResponse(IAagSubscribeResponse subsResponse) throws AeBusinessProcessException;

   /**
    * Callback when a fault arrives instead of the expected subscription
    *
    * @param aFault The fault which has been received.
    * @throws AeBusinessProcessException Allows the receiver to throw an exception.
    */
   public void onSubscriptionFault() throws AeBusinessProcessException;

   // TODO Add a method for adding a subscription consumer
}
