package ece.uwaterloo.ca.aag.resource.factory;

import org.apache.muse.ws.addressing.EndpointReference;
import org.apache.muse.ws.addressing.soap.SoapFault;

import javax.xml.namespace.QName;

/**
 * Created by Allen Ajit George
 * Date: Apr 28, 2008
 * Time: 2:26:12 PM
 */
public interface IAagBPELConsumerFactoryCapability {
  String PREFIX = "bfact";

   /**
    * URI of this capability
    */
  public String NAMESPACE_URI = "http://ece.uwaterloo.ca/aag/statefulbpel/factory/BPELConsumerFactory";

   /**
    *
    * @param ProcessID ActiveBPEL process ID of the BPEL process this WS-N consumer is associated with
    * @param ProcessQName QName of the BPEL process (must have NS and localPart at the very least)
    * @return EPR of the WS-N consumer; can be used as the consumer in WS-N subscribes
    * @throws Exception
    */
  public EndpointReference createBPELConsumer(long ProcessID, QName ProcessQName) throws SoapFault ;
}
