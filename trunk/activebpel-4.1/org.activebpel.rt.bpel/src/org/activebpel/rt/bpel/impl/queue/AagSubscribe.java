package org.activebpel.rt.bpel.impl.queue;

import org.activebpel.rt.bpel.IAagSubscriptionInvokeActivity;
import org.activebpel.rt.bpel.impl.IAagSubscribeInvokeInternal;
import org.activebpel.rt.context.IAagIncomingContextData;
import org.apache.muse.ws.addressing.EndpointReference;

import javax.xml.namespace.QName;
import java.util.List;

/**
 * Created by Allen Ajit George
 * Date: Mar 3, 2008
 * Time: 1:08:47 PM
 */
public class AagSubscribe extends AeAbstractQueuedObject implements IAagSubscribeInvokeInternal {
   public AagSubscribe(long processId, QName name, IAagSubscriptionInvokeActivity aSubsInvoke, List<IAagIncomingContextData> contextParams, EndpointReference consumerEPR) {
      mProcessId = processId;
      mProcessName = name;
      mContextParams = contextParams;
      mConsumerEPR = consumerEPR;
      setSubscriptionInvokeActivity(aSubsInvoke);
      mLocationPath = aSubsInvoke.getLocationPath();
   }

   public long getProcessId() {
      return mProcessId;
   }

   public QName getProcessName() {
      return mProcessName;
   }

   public String getLocationPath() {
      return mLocationPath;
   }

   public IAagSubscriptionInvokeActivity getSubscriptionInvokeActivity()
   {
      return mSubsInvokeActivity;
   }

   public EndpointReference getConsumerEPR() {
      return mConsumerEPR;
   }

   public void setSubscriptionInvokeActivity(IAagSubscriptionInvokeActivity aSubsInvoke) {
      mSubsInvokeActivity = aSubsInvoke;
   }

   public List<IAagIncomingContextData> getContextParameters() {
      return mContextParams;
   }

   /**
    * Overrides method to set invoke activity to <code>null</code>.
    * @see org.activebpel.rt.bpel.impl.IAagSubscribeInvokeInternal#dereferenceInvokeActivity()
    */
   public void dereferenceInvokeActivity()
   {
      mSubsInvokeActivity = null;
   }

   /**
    * process name
    */
   private QName mProcessName;
   /**
    * Process id is needed to differentiate between queued objects
    */
   private long mProcessId;
   /**
    * Receives the response for the invoke
    */
   private IAagSubscriptionInvokeActivity mSubsInvokeActivity;
   /**
    * The context parameters we're going to use for the subscribe
    */
   private List<IAagIncomingContextData> mContextParams;
   /**
    * The EPR of the entity that'll be consuming notifications
    */
   private EndpointReference mConsumerEPR;

   /**
    * Location path of the invoke activity this subscribe originated from
    */
   private String mLocationPath;
}
