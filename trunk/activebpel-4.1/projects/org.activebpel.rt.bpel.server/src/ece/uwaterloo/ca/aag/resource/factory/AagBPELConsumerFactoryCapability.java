package ece.uwaterloo.ca.aag.resource.factory;

import ece.uwaterloo.ca.aag.bpel.wsn.consumer.AagWSBPELStateConsumerCapability;
import ece.uwaterloo.ca.aag.bpel.wsn.consumer.IAagConsumerCapabilityConstants;
import org.apache.muse.core.AbstractCapability;
import org.apache.muse.core.Resource;
import org.apache.muse.core.ResourceManager;
import org.apache.muse.ws.addressing.EndpointReference;
import org.apache.muse.ws.addressing.soap.SoapFault;

import javax.xml.namespace.QName;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Allen Ajit George
 * Date: Apr 9, 2008
 * Time: 3:18:24 PM
 */
public class AagBPELConsumerFactoryCapability extends AbstractCapability implements IAagBPELConsumerFactoryCapability {
   public EndpointReference createBPELConsumer(long ProcessID, QName ProcessQName) throws SoapFault {
      ResourceManager manager = getResource().getResourceManager();
      /*
       * NOTE: The Muse example states that one should use the interface instead of the class
       *
       * However, the important thing to note is that whatever you _do_ use also has to be defined under
       * resource-type/capability/java-capability-class in muse.xml for the ActiveBPEL webapp 
       */
      String contextPath = manager.getResourceContextPath(AagWSBPELStateConsumerCapability.class);

      Resource consumerResource = manager.createResource(contextPath);

      Map processParams = new HashMap();
      processParams.put(IAagConsumerCapabilityConstants.AAG_CONSUMER_CAPABILITY_PROCESS_ID_PARAM, ProcessID);
      processParams.put(IAagConsumerCapabilityConstants.AAG_CONSUMER_CAPABILITY_PROCESS_NAME_PARAM, ProcessQName);
      consumerResource.setInitializationParameters(processParams);
      consumerResource.initialize();

      EndpointReference defaultReference = consumerResource.getEndpointReference();

      // Add the processID as a reference parameter
      QName processIDQName = new QName(IAagConsumerCapabilityConstants.AAG_CONSUMER_CAPABILITY_NS,
            IAagConsumerCapabilityConstants.AAG_CONSUMER_CAPABILITY_PROCESS_ID_PARAM,
            IAagConsumerCapabilityConstants.AAG_CONSUMER_CAPABILITY_PREFIX);
      defaultReference.addParameter(processIDQName, ProcessID);

      // Add the process name as a reference parameter
      QName processNameQName = new QName(IAagConsumerCapabilityConstants.AAG_CONSUMER_CAPABILITY_NS,
            IAagConsumerCapabilityConstants.AAG_CONSUMER_CAPABILITY_PROCESS_NAME_PARAM,
            IAagConsumerCapabilityConstants.AAG_CONSUMER_CAPABILITY_PREFIX);
      defaultReference.addParameter(processNameQName, ProcessQName);

      manager.addResource(defaultReference, consumerResource);

      getLog().info("created WS-N consumer BPELConsumer for process with process ID " + ProcessID);

      return defaultReference;
   }
}
