package org.activebpel.rt.bpel.impl;

import org.activebpel.rt.bpel.IAagSubscriptionInvokeActivity;
import org.activebpel.rt.context.IAagIncomingContextData;
import org.apache.muse.ws.addressing.EndpointReference;

import javax.xml.namespace.QName;
import java.util.List;

/**
 * Created by Allen Ajit George
 * Date: Mar 3, 2008
 * Time: 1:13:46 PM
 */
public interface IAagSubscribeInvokeInternal {
   /**
    * Accessor for the process id.
    */
   public long getProcessId();

   /**
    * Accessor for the process <code>QName</code>.
    */
   public QName getProcessName();

   /**
    * Returns the unique location path within the process for this receiver.
    */
   public String getLocationPath();

   /**
    * Sets the subscription invoker activity
    *
    * @param aSubsInvoke
    */
   public void setSubscriptionInvokeActivity(IAagSubscriptionInvokeActivity aSubsInvoke);

   /**
    * Returns the activity to be called when the subscription completes
    *
    * @return
    */
   public IAagSubscriptionInvokeActivity getSubscriptionInvokeActivity();

   /**
    * Returns the EPR of the entity that receives notifications
    *
    * @return
    */
   public EndpointReference getConsumerEPR();

   /**
    * Returns the list of context parameters (topic, topic namespace, publisher EPR) required for subscriptions
    *
    * @return
    */
   public List<IAagIncomingContextData> getContextParameters();

   /**
    * Clean up code to dereference the underlying invoke activity.
    */
   void dereferenceInvokeActivity();
}
