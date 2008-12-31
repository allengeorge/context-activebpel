package org.activebpel.rt.bpel;

import ece.uwaterloo.ca.aag.subscribe.IAagSubscribeResponse;
import org.activebpel.rt.bpel.impl.AeUninitializedVariableException;
import org.apache.muse.ws.addressing.EndpointReference;
import org.w3c.dom.Element;

import java.util.List;

/**
 * Created by Allen Ajit George
 * Date: Feb 28, 2008
 * Time: 5:39:25 PM
 */
public interface IAagStatefulVariable {
   /**
    * Indicates whether the variable is stateful or not
    *
    * @return
    */
   public boolean isStatefulVariable();

   /**
    * Indicates whether <i>any</i> subscriptions are associated with this variable
    *
    * @return
    */
   public boolean isSubscribed();

   /**
    * Get the subscription references associated with this variable
    *
    * @return
    */
   public List<EndpointReference> getSubscriptions();

   /**
    * Links the subscription properties to the appropriate parts of the variable 
    */
   public void setSubscription(IAagSubscribeResponse subsResponse);

   /**
    * Updates the variable part associated with the subscriptionReference to the new value passed in
    */
   public void updateValue(EndpointReference subscriptionReference, Element newValueElement) throws AeUninitializedVariableException;
}
