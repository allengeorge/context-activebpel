package ece.uwaterloo.ca.aag.platform.abaxis.server;

import org.apache.axis.MessageContext;
import org.apache.muse.core.Environment;
import org.apache.muse.core.platform.AbstractIsolationLayer;
import org.apache.muse.core.routing.ResourceRouter;
import org.apache.muse.util.LoggingUtils;
import org.apache.muse.ws.addressing.MessageHeaders;
import org.w3c.dom.Element;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.util.Iterator;
import java.util.logging.Level;

/**
 * Created by Allen Ajit George
 * Date: Mar 5, 2008
 * Time: 4:05:01 PM
 */
public class AagABAxisIsolationLayer extends AbstractIsolationLayer {
   protected Environment createEnvironment() {
      // XXX This should be the webapp class loader...
      ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();
      return new AagABAxisEnvironment(currentClassLoader);
   }

   // XXX Might have to get SOAPEnvelopes
   public final Element[] handleRequest(Element[] bodyElements) {
      //
      // is this the first time this is being called? initialize!
      //
      if (!hasBeenInitialized())
         initialize();

      Element[] soapResponse = null;

      //
      // for all requests, we need to save the addressing headers so that
      // we can access the SOAP envelope in handleRequest()
      //
      if (hasFailedToInitialize()) {
         Element failureCause = getCauseOfFailure().toXML();
         soapResponse = new Element[]{failureCause};
      } else {
         soapResponse = invoke(bodyElements);
      }

      //
      // be sure to handle empty SOAP body in the response
      //
      if (soapResponse == null) {
         return null;
      }

      return soapResponse;
   }

   public Element[] invoke(Element[] requestElements) {
      ResourceRouter router = getRouter();
      AagABAxisEnvironment env = (AagABAxisEnvironment) router.getEnvironment();

      //
      // log incoming SOAP envelope
      //
      // NOTE: This is kind of hack-ish, but we check to see if the
      //       current log level is 'FINE' before we try and log the
      //       message. We don't actually have to do this - the JDK
      //       logging API fine() will do this for us - but because
      //       we have to translate from Axiom to DOM once already,
      //       I don't want to do it twice unless the tracing is being
      //       used. If SOAP-level tracing is on, it's likely that this
      //       is not being used in a production system, so we can afford
      //       the performance hit of an extra conversion.
      //
      if (router.getLog().getLevel() == Level.FINE) {
         try {
            SOAPMessage soap = MessageContext.getCurrentContext().getMessage();
            SOAPBody soapBody = soap.getSOAPBody();
            Element soapAsDOM = env.convertToDOM(soapBody);
            LoggingUtils.logMessage(router.getLog(), soapAsDOM, true);
         } catch (SOAPException e) {
            LoggingUtils.logError(router.getLog(), e);
         }
      }

      MessageHeaders wsa = env.convertContext();
      env.addAddressingContext(wsa);

      Element soapBody = null;
      //
      // handle empty SOAP bodies
      //
      // FIXME This method of converting SOAP to DOM does not account for namespace declarations in the SOAP envelope or SOAP header
      if (requestElements != null) {
         try {
            SOAPBody axisBody = MessageContext.getCurrentContext().getMessage().getSOAPBody();

            Iterator elementIt = axisBody.getChildElements();
            // FIXME I Support only doc-lit wrapped for now
            while (elementIt.hasNext()) {
               soapBody = env.convertToDOM((SOAPElement) elementIt.next());
            }

         } catch (SOAPException error) {
            throw new RuntimeException(error.getMessage(), error);
         }
      }

      // XXX We are expecting the _contents_ of the soap body as DOM here...
      Element soapResponse = getRouter().invoke(soapBody);
      Element[] soapResponseAsArray = new Element[]{soapResponse};

      //
      // all done - don't forget to clean up the context or
      // we'll have a memory leak
      //
      env.removeAddressingContext();

      return soapResponseAsArray;
   }
}
