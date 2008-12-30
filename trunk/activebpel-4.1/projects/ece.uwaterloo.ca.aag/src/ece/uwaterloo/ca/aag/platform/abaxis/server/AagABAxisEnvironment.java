package ece.uwaterloo.ca.aag.platform.abaxis.server;

import org.apache.axis.AxisEngine;
import org.apache.axis.MessageContext;
import org.apache.axis.message.MessageElement;
import org.apache.axis.transport.http.HTTPConstants;
import org.apache.muse.core.AbstractEnvironment;
import org.apache.muse.util.xml.XmlUtils;
import org.apache.muse.ws.addressing.EndpointReference;
import org.apache.muse.ws.addressing.MessageHeaders;
import org.apache.muse.ws.addressing.soap.SoapFault;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.namespace.QName;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Created by Allen Ajit George
 * Date: Mar 5, 2008
 * Time: 4:08:59 PM
 */
public class AagABAxisEnvironment extends AbstractEnvironment {
   /**
    * This constructor determines the value of the "real directory" - where
    * the application is installed on the file system so that it can read
    * local files when it needs to.
    */
   public AagABAxisEnvironment(ClassLoader classLoader) {
      /*
       * XXX IMPORTANT:
       *
       * The Axis2 implementation of Muse (on which I based this) assumes that all the Axis2 libraries live under
       * WEB-INF/lib. This is not true in the way I've packaged the muse libraries in ActiveBPEL. In my packaging, the
       * libraries are under <tomcat_home>/shared/lib. This poses a problem. The no-arg constructor for AbstractEnvironment
       * calls a function initialize(ClassLoader, boolean) which uses the classloader responsible for the _MUSE CLASS_ i.e.
       * AbstractEnvironment as the classloader to use. This is, of course, the shared loader in Tomcat. Therefore when this
       * shared loader was being used, I couldn't find any resources under the WEB-INF directory
       */

      // I don't want to create a soap client I think...
      super(classLoader, false);
      MessageHeaders wsa = convertContext();
      addAddressingContext(wsa);

      MessageContext context = MessageContext.getCurrentContext();
      String address = (String) context.getProperty(MessageContext.TRANS_URL);
      setDefaultURI(getDeploymentURI(address));

      _realDirectory = createRealDirectory();
   }

   /**
    * Converts Axis2's OperationContext into the Muse addressing context,
    * MessageHeaders. Hopefully this becomes irrelevant through the
    * adoption of something like JSR-261 or some WS-A project in Apache Commons.
    */
   public MessageHeaders convertContext() {
      try {
         MessageContext context = MessageContext.getCurrentContext();
         SOAPHeader messageHeader = context.getMessage().getSOAPHeader();
         Element headerDom = convertToDOM(messageHeader);
         return new MessageHeaders(headerDom);
      } catch (SoapFault error) {
         throw new RuntimeException(error.getMessage(), error);
      } catch (SOAPException error) {
         throw new RuntimeException(error.getMessage(), error);
      }
   }

   /**
    * Converts Axis2's EPR type into our general one so that code isn't specific
    * to the Axis2 platform. Hopefully this becomes irrelevant through the
    * adoption of something like JSR-261 or some WS-A project in Apache Commons.
    */
   public EndpointReference convertEPR(SOAPHeader axisHeader) {
      /* URI address = URI.create(axisEPR.getAddress());
            EndpointReference epr = new EndpointReference(address);

            Map parameters = axisEPR.getAllReferenceParameters();
            Iterator i = parameters.keySet().iterator();

            while (i.hasNext()) {
               QName name = (QName) i.next();

               OMElement axiomValue = (OMElement) parameters.get(name);
               Element domValue = convertToDOM(axiomValue);

               epr.addParameter(domValue);
            }
      */
      return null;
   }

   /**
    * Convert DOM to Axiom. Muse uses the DOM API in the JDK, Axis2 uses
    * the Axiom API, which is similar but... different.
    */
   public SOAPElement convertToSOAPElement(Element xml) {
      String xmlString = XmlUtils.toString(xml, false);
      byte[] xmlBytes = xmlString.getBytes();

      SOAPElement returnElement;
      try {
         InputStream is = new ByteArrayInputStream(xmlBytes);
         Document doc = XmlUtils.createDocument(is);
         returnElement = new MessageElement(doc.getDocumentElement());
      } catch (IOException error) {
         throw new RuntimeException(error.getMessage(), error);
      } catch (SAXException error) {
         throw new RuntimeException(error.getMessage(), error);
      }

      return returnElement;
   }

   /**
    * Convert SOAPElement to DOM. Muse uses the DOM API in the JDK
    */
   public Element convertToDOM(SOAPElement axisSOAPElement) {
      axisSOAPElement.normalize();

      String elementNS = axisSOAPElement.getElementName().getURI();

      // It's entirely possible that an element won't have a prefix
      String elementNSPrefix = axisSOAPElement.getPrefix();
      if (elementNSPrefix == null) {
         elementNSPrefix = axisSOAPElement.lookupPrefix(elementNS);
      }

      // The element damn well should have a local part
      String elementLocalPart = axisSOAPElement.getLocalName();

      Element newAxisElement;
      if (elementNSPrefix != null && elementNS != null && elementLocalPart != null) {
         newAxisElement = XmlUtils.createElement(new QName(elementNS, elementLocalPart,elementNSPrefix));
      }  else if (elementNSPrefix == null && elementNS != null && elementLocalPart != null) {
         newAxisElement = XmlUtils.createElement(new QName(elementNS, elementLocalPart));
      } else if (elementNSPrefix == null && elementNS == null && elementLocalPart != null) {
         newAxisElement = XmlUtils.createElement(new QName(elementLocalPart));
      }  else {
         newAxisElement = XmlUtils.createElement(new QName(elementLocalPart));
      }

      String text = axisSOAPElement.getValue();

      if (text != null && text.length() > 0)
         XmlUtils.setElementText(newAxisElement, text);

      Iterator i = axisSOAPElement.getAllAttributes();
      while (i.hasNext()) {
         Name attrName = (Name) i.next();
         String value = axisSOAPElement.getAttributeValue(attrName);
         newAxisElement.setAttributeNS(attrName.getURI(), attrName.getLocalName(), value);
      }

      i = axisSOAPElement.getNamespacePrefixes();
      String newAxisElementURI = newAxisElement.getNamespaceURI();

      while (i.hasNext()) {
         String prefix = (String) i.next();
         String uri = axisSOAPElement.getNamespaceURI(prefix);

         //
         // Axiom considers the element's prefix/namespace to be a
         // 'declared namespace', but DOM/Xerces doesn't. so, we have
         // to make sure we don't add a duplicate prefix/namespace
         // declaration for the element's namespace
         //
         if (!uri.equals(newAxisElementURI))
            XmlUtils.setNamespaceAttribute(newAxisElement, prefix, uri);
      }

      i = axisSOAPElement.getChildElements();

      while (i.hasNext()) {
         // XXX Why are non-SOAPElements returned as elements?!
         Object currentElement = i.next();

         if (currentElement instanceof SOAPElement) {
            Element child = convertToDOM((SOAPElement) currentElement);
            // XXX Technically I should import first (to get a copy of this node for the current document, and then append)
            // But the reason I don't do it is because I know all the child nodes are created with EMPTY_DOC
            newAxisElement.appendChild(child);
         }
      }

      return newAxisElement;
   }

   protected File createRealDirectory() {
      /*Object realPath1 = AxisEngine.getCurrentMessageContext().getProperty(HTTPConstants.MC_HTTP_SERVLETPATHINFO);*/ // Returns the context path
      // This actually returns the system directory
      Object realPath2 = AxisEngine.getCurrentMessageContext().getProperty(HTTPConstants.MC_HTTP_SERVLETLOCATION);

      // I get a pointer to the WEB-INF dir, which is what I'm interested in
      String axisServicePath = (String) realPath2;
      File webInfDir = new File(axisServicePath);
      return new File(webInfDir, _CLASSES);
   }

   public EndpointReference getDeploymentEPR() {
      return getDefaultEPR();
   }

   public File getRealDirectory() {
      return _realDirectory;
   }

   private static final String _CLASSES = "classes";

   //
   // The service installation directory (NOT the WAR file directory). This
   // is found at $WAR_INSTALL_DIR/WEB-INF/classes
   //
   private File _realDirectory = null;
}
