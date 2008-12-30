package ece.uwaterloo.ca.aag.subscribe;

import org.w3c.dom.Node;
import org.apache.muse.ws.addressing.EndpointReference;
import org.apache.muse.ws.notification.remote.SubscriptionClient;

/**
 * Created by Allen Ajit George
 * Date: May 7, 2008
 * Time: 12:41:16 PM
 */
public interface IAagSubscribeResponse {
   boolean isFault();

   Node getFaultNode();

   String getStatefulParameterName();

   EndpointReference getSubscriptionEPR();

   SubscriptionClient getSubscriptionResource();
}
