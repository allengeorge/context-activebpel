package ece.uwaterloo.ca.aag.platform.abaxis.client;

import org.activebpel.rt.IAeConstants;
import org.activebpel.rt.util.AeUtil;
import org.apache.axis.client.Call;
import org.apache.axis.constants.Style;
import org.apache.axis.message.SOAPBodyElement;
import org.apache.axis.message.SOAPHeaderElement;
import org.apache.muse.util.xml.XmlUtils;
import org.apache.muse.ws.addressing.EndpointReference;
import org.apache.muse.ws.addressing.MessageHeaders;
import org.apache.muse.ws.addressing.WsaConstants;
import org.apache.muse.ws.addressing.soap.SoapClient;
import org.apache.muse.ws.addressing.soap.SoapFault;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.PrintWriter;
import java.util.Vector;

/**
 * Based on, and has a good chunk of code from Apache Muse 2.2.0's SimpleSoapClient
 *
 * @see org.apache.muse.ws.addressing.soap.SimpleSoapClient
 */
public class AagABAxisClient implements SoapClient {
   private void addHeadersToCall(Call call, EndpointReference source, EndpointReference destination, String action, Element[] bodyElements, Element[] extraHeaders) {
      Document doc = XmlUtils.createDocument();

      // Add WS-Addressing headers
      MessageHeaders headers = new MessageHeaders(destination, action);

      // If there's a source EPR, we can provide a wsa:From
      if (source != null) {
         headers.setFromAddress(source);
      }

      // NOTE: The toXML() function adds a containing soap:Header element, so I've got to remove it
      Element museHeaderElement = headers.toXML();
      museHeaderElement.normalize();
      NodeList headerList = museHeaderElement.getChildNodes();

      int headerLength = headerList.getLength();
      SOAPHeaderElement tempElement;
      for (int he = 0; he < headerLength; he++) {
         tempElement = new SOAPHeaderElement((Element) headerList.item(he));
         call.addHeader(tempElement);
      }

      // Add all of the non-WS-A SOAP headers
      SOAPHeaderElement header;
      for (int n = 0; n < extraHeaders.length; ++n) {
         if (extraHeaders[n].getNamespaceURI().equals(WsaConstants.NAMESPACE_URI)) {
            throw new RuntimeException("Duplicate addressing header");
         }

         extraHeaders[n] = (Element) doc.importNode(extraHeaders[n], true);
         header = new SOAPHeaderElement(extraHeaders[n]);

         // Axis will add duplicate actor and mustUnderstand attributes that will
         // cause issues later if we do not remove them explicitly
         String actor = header.getAttributeNS(IAeConstants.SOAP_NAMESPACE_URI, ACTOR_ATTRIBUTE);
         if (actor != null) {
            header.removeAttribute(ACTOR_ATTRIBUTE);
            header.setActor(actor);
         }
         String mustUnderstand = header.getAttributeNS(IAeConstants.SOAP_NAMESPACE_URI, MUST_UNDERSTAND_ATTRIBUTE);
         if (mustUnderstand != null) {
            header.removeAttribute(MUST_UNDERSTAND_ATTRIBUTE);
            header.setMustUnderstand(AeUtil.toBoolean(mustUnderstand));
         }

         call.addHeader(header);
      }
   }

   public Element[] send(EndpointReference src, EndpointReference dest, String wsaAction, Element[] body) {
      return send(src, dest, wsaAction, _EMPTY_ARRAY);
   }

   public Element[] send(EndpointReference src, EndpointReference dest, String wsaAction, Element[] body, Element[] extraHeaders) {
      // TODO Implement tracing
      Element[] responseElements = new Element[0];

      if (dest == null)
         throw new NullPointerException("No destination EPR given");

      if (wsaAction == null)
         throw new NullPointerException("Empty wsa:Action URI");

      if (body == null)
         body = _EMPTY_ARRAY;

      if (extraHeaders == null)
         extraHeaders = _EMPTY_ARRAY;

      try {
         // XXX Do I need this?
         AagABAxisService service = new AagABAxisService();
         Call call = (Call) service.createCall();

         // FIXME Hardcoded timeout
         call.setTimeout(INVOKE_TIMEOUT);
         call.setTargetEndpointAddress(dest.getAddress().toURL());

         call.setOperationStyle(Style.MESSAGE);
         addHeadersToCall(call, src, dest, wsaAction, body, extraHeaders);
         Vector elems;

         // Copy data into SOAP body
         SOAPBodyElement[] bodyElements = new SOAPBodyElement[body.length];
         for (int n = 0; n < body.length; ++n) {
            bodyElements[n] = new SOAPBodyElement(body[n]);
         }

         // For document style we receive a vector of body elements from return
         elems = (Vector) call.invoke(bodyElements);

         /*
          * Process the return elements
          * These are all the elements _under_ the soap:Body element:
          *
          * <soap:Body>
          *   <!-- All these elements -->
          *   <wsnt:Subscribe xmlns:wsnt="http://docs.oasis-open.org/wsn/b-2">
          *     <wsnt:ConsumerReference>
          *       <wsa:Address xmlns:wsa="http://www.w3.org/2005/08/addressing">http://192.168.1.105:9090/contextconsumer/services/BPELConsumer</wsa:Address>
          *     </wsnt:ConsumerReference>
          *     <wsnt:Filter>
          *       <wsnt:TopicExpression
          *         Dialect="http://docs.oasis-open.org/wsn/t-1/TopicExpression/Concrete" xmlns:ship="http://delivery.company/shipping/Shipment">ship:ShipmentCondition</wsnt:TopicExpression>
          *     </wsnt:Filter>
          *   </wsnt:Subscribe>
          *   <!-- Till here -->
          * </soap:Body>
          */
         int elemsSize = elems.size();
         responseElements = new Element[elemsSize];
         for (int i = 0; i < elemsSize; i++) {
            SOAPBodyElement elem = (SOAPBodyElement) elems.get(i);

            if (elem != null) {
               responseElements[i] = elem.getAsDOM();
            }
         }
      } catch (Throwable error) {
         SoapFault soapFault = new SoapFault(error.getMessage(), error);
         return new Element[]{soapFault.toXML()};
      }

      return responseElements;
   }

   public int getSoapMonitorPort() {
      return _monitorPort;
   }

   public boolean isUsingSoapMonitor() {
      return _monitorPort >= 0;
   }

   public void startSoapMonitor(int monitorPort) {
      throw new UnsupportedOperationException("SOAP Monitor not implemented for this SoapClient");
      /*if (monitorPort < 1) {
         throw new RuntimeException("Invalid port: " + monitorPort);
      }

      _monitorPort = monitorPort;*/
   }

   public void stopSoapMonitor() {
      _monitorPort = -1;
   }

   public PrintWriter getTraceWriter() {
      return _traceWriter;
   }

   public boolean isUsingTrace() {
      return _trace;
   }

   public void setTrace(boolean trace) {
      _trace = trace;
   }

   public void setTraceWriter(PrintWriter writer) {
      if (writer == null) {
         throw new NullPointerException("Null TraceWriter");
      }

      _traceWriter = writer;
   }

   /**
    * @param xml      An XML fragment that will be sent to the trace log.
    * @param incoming True if the message was part of an incoming SOAP message. This
    *                 merely provides some context in the trace log.
    */
   protected void trace(Element xml, boolean incoming) {
      PrintWriter writer = getTraceWriter();
      writer.write("[CLIENT TRACE] SOAP envelope contents (");
      writer.write(incoming ? "incoming" : "outgoing");
      writer.write("):\n\n");
      writer.write(XmlUtils.toString(xml, false));
      writer.write('\n');
      writer.flush();
   }

   /**
    * @param message The message to print to the trace log.
    */
   protected void trace(String message) {
      PrintWriter writer = getTraceWriter();
      writer.write("[CLIENT TRACE] ");
      writer.write(message);
      writer.write('\n');
      writer.flush();
   }

   // Set to 0...65535 if TCP/SOAP monitor is in use
   private int _monitorPort = -1;
   // Print all SOAP messages if true - default is false.
   private boolean _trace = false;
   // The stream used for tracing - the default is stdout
   private PrintWriter _traceWriter = new PrintWriter(System.out);

   private static final String MUST_UNDERSTAND_ATTRIBUTE = "mustUnderstand"; //$NON-NLS-1$
   private static final String ACTOR_ATTRIBUTE = "actor"; //$NON-NLS-1$
   private static final Element[] _EMPTY_ARRAY = new Element[0];
   private static final int INVOKE_TIMEOUT = 10000;
}
