package ece.uwaterloo.ca.aag.server.engine;

import ece.uwaterloo.ca.aag.platform.abaxis.IAagABAxisConstants;
import ece.uwaterloo.ca.aag.server.response.AagConsumerFactoryResponse;
import org.activebpel.rt.IAeConstants;
import org.activebpel.rt.bpel.impl.IAagConsumerFactoryInvokeInternal;
import org.apache.axis.Message;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.handlers.soap.SOAPService;
import org.apache.axis.message.SOAPHeaderElement;
import org.apache.muse.util.xml.XmlUtils;
import org.apache.muse.ws.addressing.EndpointReference;
import org.apache.muse.ws.addressing.soap.SoapFault;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;
import javax.xml.soap.*;
import java.rmi.RemoteException;

/**
 * Created by Allen Ajit George
 * Date: Apr 28, 2008
 * Time: 3:22:09 PM
 */
public class AagConsumerFactoryInvokeHandler {
   public AagConsumerFactoryResponse handleCreateConsumer(IAagConsumerFactoryInvokeInternal consumerFactoryInvoke) {
      AagConsumerFactoryResponse factoryResponse = null;

      try {
         Service service = new Service();
         Call call = (Call) service.createCall();
         call.setSOAPService(new SOAPService());

         String factoryURL = consumerFactoryInvoke.getEngineURL() + "/" + IAagABAxisConstants.MUSE_SERVICE_PATH + "/BPELConsumerFactory";

         call.setTargetEndpointAddress(factoryURL);
         // This is actually the SOAP action, which Muse doesn't look at
         call.setOperationName(new QName(BPEL_CONSUMER_FACTORY_NS, "CreateBPELConsumer", "bpelfact"));

         // Add the WS-A headers
         // Format is NS, localpart, value
         SOAPHeaderElement header;

         // WS-A To         
         header = new SOAPHeaderElement(IAeConstants.WSA_NAMESPACE_URI_2005_08, "To", factoryURL);
         call.addHeader(header);

         // WS-A Action
         header = new SOAPHeaderElement(IAeConstants.WSA_NAMESPACE_URI_2005_08, "Action", BPEL_CONSUMER_FACTORY_ACTION);
         call.addHeader(header);

         MessageFactory factory = MessageFactory.newInstance();
         SOAPMessage message = factory.createMessage();
         SOAPBody body = message.getSOAPBody();
         // XXX For some reason I'm unable to add a namespace declaration to the soap body

         SOAPFactory sFactory = SOAPFactory.newInstance();
         Name requestName = sFactory.createName("CreateBPELConsumer", "bpelfact", BPEL_CONSUMER_FACTORY_NS);
         SOAPBodyElement wrapperElement = body.addBodyElement(requestName);
         // Add the namespace for the process
         wrapperElement.addNamespaceDeclaration(BPEL_PROCESS_PREFIX, consumerFactoryInvoke.getProcessQName().getNamespaceURI());
         SOAPElement pidElement = wrapperElement.addChildElement("ProcessID");
         pidElement.addTextNode(Long.toString(consumerFactoryInvoke.getProcessId()));
         SOAPElement pqnameElement = wrapperElement.addChildElement("ProcessQName");
         // I'm using a hardcoded process prefix
         pqnameElement.addTextNode(BPEL_PROCESS_PREFIX + ":" + consumerFactoryInvoke.getProcessQName().getLocalPart());

         Object reply = call.invoke((Message) message);
         // FIXME Do something to pretty print this
         System.out.println(reply);

         SOAPEnvelope responseEnvelope = (SOAPEnvelope) reply;
         SOAPBody responseBody = responseEnvelope.getBody();
         if (responseBody.getChildNodes().getLength() > 1) {
            System.err.println("unexpected response from BPELConsumerFactory: too many response elements in message");
            System.err.println("response content:");

            NodeList responseNodes = responseBody.getChildNodes();
            int numResponseNodes = responseNodes.getLength();
            for (int currResponseNode = 0; currResponseNode < numResponseNodes; currResponseNode++) {
               Node currNode = responseNodes.item(currResponseNode);
               System.err.println(XmlUtils.toString(currNode));
            }
         } else {
            // There should only be one child
            Node wrapperNode = responseBody.getFirstChild();

            if (wrapperNode.getLocalName().compareToIgnoreCase("CreateBPELConsumerResponse") == 0) {
               NodeList childNodes = wrapperNode.getChildNodes();
               int numNodes = childNodes.getLength();
               int currNum = 0;
               boolean foundEPR = false;
               Node currNode;
               EndpointReference consumerEPR;

               while (!foundEPR && currNum < numNodes) {
                  currNode = childNodes.item(currNum);

                  if (currNode.getNodeType() == Node.ELEMENT_NODE) {
                     Element currElement = (Element) currNode;
                     if (currElement.getLocalName().compareToIgnoreCase("BPELConsumerEPR") == 0) {
                        foundEPR = true;
                        consumerEPR = new EndpointReference(currElement);
                        factoryResponse = new AagConsumerFactoryResponse(consumerEPR);
                     }
                  }

                  currNum++;
               }

               if (!foundEPR) {
                  System.err.println("BPELConsumerFactory did not return an EPR!");
               }
            } else {
               System.err.println("unexpected response from BPELConsumerFactory: possible fault response");
               System.err.println("response content:");

               NodeList responseNodes = wrapperNode.getChildNodes();
               int numResponseNodes = responseNodes.getLength();
               for (int currResponseNode = 0; currResponseNode < numResponseNodes; currResponseNode++) {
                  Node currNode = responseNodes.item(currResponseNode);
                  System.err.println(XmlUtils.toString(currNode));
               }
               
               factoryResponse = new AagConsumerFactoryResponse(wrapperNode);
            }
         }
      } catch (ServiceException e) {
         System.err.println("problem creating and sending request to BPELConsumerFactory");
         e.printStackTrace();
      } catch (RemoteException e) {
         System.err.println("problem creating and sending request to BPELConsumerFactory");
         e.printStackTrace();
      } catch (SOAPException e) {
         System.err.println("problem creating and sending request to BPELConsumerFactory");
         e.printStackTrace();
      } catch (SoapFault soapFault) {
         System.err.println("problem converting returned EPR to Muse EPR object");
         soapFault.printStackTrace();
      }

      return factoryResponse;
   }

   private static final String BPEL_PROCESS_PREFIX = "bpelproc";
   private static final String BPEL_CONSUMER_FACTORY_NS = "http://ece.uwaterloo.ca/aag/statefulbpel/factory/BPELConsumerFactory";
   private static final String BPEL_CONSUMER_FACTORY_ACTION = "http://ece.uwaterloo.ca/aag/statefulbpel/factory/BPELConsumerFactory/CreateBPELConsumer";
}
