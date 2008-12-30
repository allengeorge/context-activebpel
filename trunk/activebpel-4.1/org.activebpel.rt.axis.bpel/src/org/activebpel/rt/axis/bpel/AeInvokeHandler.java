// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis.bpel/src/org/activebpel/rt/axis/bpel/AeInvokeHandler.java,v 1.80 2007/07/27 18:22:4
/*
 * Copyright (c) 2004-2006 Active Endpoints, Inc.
 *
 * This program is licensed under the terms of the GNU General Public License
 * Version 2 (the "License") as published by the Free Software Foundation, and 
 * the ActiveBPEL Licensing Policies (the "Policies").  A copy of the License 
 * and the Policies were distributed with this program.  
 *
 * The License is available at:
 * http: *www.gnu.org/copyleft/gpl.html
 *
 * The Policies are available at:
 * http: *www.activebpel.org/licensing/index.html
 *
 * Unless required by applicable law or agreed to in writing, this program is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied.  See the License and the Policies
 * for specific language governing the use of this program.
 */
package org.activebpel.rt.axis.bpel;

import org.activebpel.rt.AeException;
import org.activebpel.rt.IAeConstants;
import org.activebpel.rt.IAePolicyConstants;
import org.activebpel.rt.axis.bpel.invokers.AeInvokeContext;
import org.activebpel.rt.axis.bpel.invokers.AeInvokerFactory;
import org.activebpel.rt.axis.bpel.invokers.IAeInvoker;
import org.activebpel.rt.bpel.*;
import org.activebpel.rt.bpel.def.IAeBPELConstants;
import org.activebpel.rt.bpel.impl.AeEndpointReference;
import org.activebpel.rt.bpel.impl.AeFaultFactory;
import org.activebpel.rt.bpel.impl.addressing.AeAddressingHeaders;
import org.activebpel.rt.bpel.impl.addressing.IAeAddressingHeaders;
import org.activebpel.rt.bpel.impl.queue.AeInvoke;
import org.activebpel.rt.bpel.server.AeCryptoUtil;
import org.activebpel.rt.bpel.server.addressing.IAePartnerAddressing;
import org.activebpel.rt.bpel.server.deploy.IAePolicyMapper;
import org.activebpel.rt.bpel.server.engine.AeEngineFactory;
import org.activebpel.rt.bpel.server.engine.invoke.AeAddressHandlingType;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.activebpel.rt.wsdl.def.AeBPELExtendedWSDLDef;
import org.activebpel.rt.wsdl.def.AeBindingUtils;
import org.activebpel.rt.wsdl.def.AeFaultMatcher;
import org.activebpel.rt.wsdl.def.policy.IAePolicy;
import org.activebpel.wsio.AeWebServiceMessageData;
import org.activebpel.wsio.AeWsAddressingException;
import org.activebpel.wsio.IAeWebServiceEndpointReference;
import org.activebpel.wsio.IAeWebServiceResponse;
import org.activebpel.wsio.invoke.AeInvokeResponse;
import org.activebpel.wsio.invoke.IAeInvoke;
import org.activebpel.wsio.invoke.IAeInvokeHandler;
import org.apache.axis.AxisFault;
import org.apache.axis.Constants;
import org.apache.axis.MessageContext;
import org.apache.axis.client.Call;
import org.apache.axis.client.Transport;
import org.apache.axis.constants.Style;
import org.apache.axis.handlers.soap.SOAPService;
import org.apache.axis.message.SOAPHeaderElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import javax.wsdl.*;
import javax.wsdl.extensions.UnknownExtensibilityElement;
import javax.xml.namespace.QName;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.*;

/**
 * Handles invoking of endpoint references on behalf of the business process engine.
 */
public class AeInvokeHandler implements IAeInvokeHandler, IAePolicyConstants {

   private static final String MUST_UNDERSTAND_ATTRIBUTE = "mustUnderstand"; //$NON-NLS-1$
   private static final String ACTOR_ATTRIBUTE = "actor"; //$NON-NLS-1$
   /**
    * namespace we're using for identifying the credentials embedded in the endpoint properties
    */
   private static final String CREDENTIALS_NAMESPACE = "http://active-endpoints/endpoint-credentials"; //$NON-NLS-1$

   /**
    * Invokes the partner operation. This is done by utlizing the endpoint
    * reference info to create an axis service and the operation
    * information in the queue object to create the call.  The input message
    * is then moved to the call input and vica versa on output.
    *
    * @see org.activebpel.wsio.invoke.IAeInvokeHandler#handleInvoke(org.activebpel.wsio.invoke.IAeInvoke, java.lang.String)
    */
   public IAeWebServiceResponse handleInvoke(IAeInvoke aInvokeQueueObject, String aQueryData) {
      //doMuseInvoke();

      AeAddressHandlingType addressHandling = AeAddressHandlingType.getByName(aQueryData);

      Operation operation = null;
      AeInvokeResponse response = new AeInvokeResponse();
      try {
         IAeWebServiceEndpointReference endpointReference = getEndpointReference(aInvokeQueueObject);
         AeBPELExtendedWSDLDef wsdlDef = getWsdlDef(aInvokeQueueObject, endpointReference);
         Service wsdlService = getServiceObject(wsdlDef, endpointReference);

         // The service name can be null in some circumstances. Specifically, if the addressHandling type is
         // URN or URL, then we'll attempt to use the address found in the wsa:Address
         // as opposed to the soap:address in the service.
         if (wsdlService == null && addressHandling == AeAddressHandlingType.SERVICE)
            throw new AeBusinessProcessException(AeMessages.getString("AeInvokeHandler.ERROR_0")); //$NON-NLS-1$

         PortType portType = getPortType(wsdlService, endpointReference, aInvokeQueueObject);
         operation = portType.getOperation(aInvokeQueueObject.getOperation(), null, null);
         if (operation == null)
            throw new AeBusinessProcessException(AeMessages.format("AeInvokeHandler.ERROR_MISSING_OPERATION", aInvokeQueueObject.getOperation())); //$NON-NLS-1$

         // get the implicit replyTo 
         IAeWebServiceEndpointReference replyTo = updateReplyToEndpoint(aInvokeQueueObject);

         // Resolve endpoint and wsdl policies
         List wsdlPolicy = getWsdlPolicies(wsdlDef, aInvokeQueueObject, endpointReference);

         Call call = createCall(wsdlService, operation, endpointReference, addressHandling, replyTo, wsdlPolicy);

         // Set the transmission id property on call
         call.setProperty(RM_TRANS_ID, new Long(aInvokeQueueObject.getTransmissionId()));

         if (aInvokeQueueObject.isOneWay())
            call.getOperation().setMep(OperationType.ONE_WAY);
         AeInvokeContext invokeCtx = createInvokeContext(aInvokeQueueObject, operation, response, call);

         if (wsdlService != null) {
            // list of input parts to add as headers
            // TODO (MF) get list of faults to look for in response soap headers
            BindingOperation bop = getBindingOperation(wsdlService, operation.getName(), endpointReference);
            invokeCtx.setBindingOperation(bop);
            BindingInput input = bop.getBindingInput();
            Message inputMessage = operation.getInput().getMessage();

            Collection inputHeaderParts = AeBindingUtils.getPartsForHeader(input, inputMessage.getQName());
            invokeCtx.setInputHeaderParts(inputHeaderParts);

            if (operation.getOutput() != null) {
               Collection outputHeaderParts = AeBindingUtils.getPartsForHeader(bop.getBindingOutput(), operation.getOutput().getMessage().getQName());
               invokeCtx.setOutputHeaderParts(outputHeaderParts);
            }
         }

         IAeInvoker invoker = AeInvokerFactory.getInvoker(invokeCtx);
         invoker.invoke(invokeCtx);
         extractMappedProperties(aInvokeQueueObject, call.getMessageContext());
      }
      catch (AxisFault e) {
         setFaultOnResponse(aInvokeQueueObject.getPortType(), operation, e, response);
      }
      catch (Throwable t) {
         AeException.logError(t, t.getMessage());
         IAeFault fault = AeFaultFactory.getSystemErrorFault(t);
         response.setFaultData(fault.getFaultName(), null);
         response.setErrorString(t.getMessage());
         response.setErrorDetail(AeUtil.getStacktrace(t));
      }

      return response;
   }

   /**
    * Extract the <code>IAeWebServiceEndpointReference</code> from the
    * <code>IAeInvoke</code> object.
    *
    * @param aInvoke
    */
   protected IAeWebServiceEndpointReference getEndpointReference(IAeInvoke aInvoke) {
      return ((AeInvoke) aInvoke).getPartnerReference();
   }

   /**
    * Extract the wsa:ReplyTo <code>IAeWebServiceEndpointReference</code> from the
    * <code>IAeInvoke</code> object and see if we need to include engine managed correlation headers.
    *
    * @param aInvoke
    */
   protected IAeWebServiceEndpointReference updateReplyToEndpoint(IAeInvoke aInvoke) throws AeBusinessProcessException {
      IAeEndpointReference myRef = ((AeInvoke) aInvoke).getMyReference();
      if (myRef == null) {
         return null;
      }
      IAeEndpointReference replyTo = new AeEndpointReference();
      replyTo.setReferenceData(myRef);

      // we don't want to transmit our policy info in the replyTo
      replyTo.getPolicies().clear();

      return replyTo;
   }

   /**
    * Extracts mapped header properties from Axis MessageContext and adds to partnerLink on
    * <code>IAeInvoke</code> object.
    *
    * @param aInvoke
    */
   protected void extractMappedProperties(IAeInvoke aInvoke, MessageContext aMsgContext) {
      IAeEndpointReference myRef = ((AeInvoke) aInvoke).getMyReference();
      // Get any mapped message headers attached by our handler
      Element headers = (Element) aMsgContext.getProperty(AE_CONTEXT_MAPPED_PROPERTIES);
      if (headers != null) {
         headers.setAttribute("operation", aInvoke.getOperation()); //$NON-NLS-1$
         myRef.addExtensibilityElement(headers);
      }
   }

   /**
    * Find the matching <code>javax.wsdl.Service</code> service object.
    *
    * @param aInvoke
    * @param aEndpointRef
    */
   protected Service getServiceObject(AeBPELExtendedWSDLDef aDef, IAeWebServiceEndpointReference aEndpointRef) throws AeBusinessProcessException {
      Service service = null;
      if (aEndpointRef.getServiceName() != null) {
         if (aDef != null) {
            service = (Service) aDef.getServices().get(aEndpointRef.getServiceName());
         }
         // TODO (MF) if we were given a service name but couldn't find it, we should generate a warning message.
         //       we may still be able to do the invoke but w/o the binding info we might be missing out
         //       on adding some required info like the soap action.
      }
      return service;
   }

   /**
    * Create and configure the client call object.
    *
    * @param aWsdlService
    * @param aOperation
    * @param aEndpoint
    * @throws Exception
    */
   protected Call createCall(Service aWsdlService, Operation aOperation,
                             IAeWebServiceEndpointReference aEndpoint, AeAddressHandlingType aAddressHandlingType,
                             IAeWebServiceEndpointReference aDefaultReplyTo, List aWsdlPolicy)
         throws Exception {
      String url = getEndpointUrl(aWsdlService, aEndpoint, aAddressHandlingType);
      Style requestStyle = getRequestStyle(aWsdlService, aOperation.getName(), aEndpoint);
      String soapAction = getSoapAction(aWsdlService, aOperation.getName(), aEndpoint);

      // use AeService cached client
      /**
       * TODO: KP add a special .pdd flag to indicate that we need a whole new one-off client for particular cases
       **/
      AeService service = new AeService();
      Call call = (Call) service.createCall();

      // set an empty soap service so axis doesn't bother trying to find specific one for invoke, since there won't be one
      call.setSOAPService(new SOAPService());

      call.setTimeout(new Integer(AeEngineFactory.getEngineConfig().getWebServiceTimeout() * 1000));
      call.setTargetEndpointAddress(new URL(url));
      call.setSOAPActionURI(soapAction);
      call.setOperationName(aOperation.getName());
      call.setProperty(Call.OPERATION_STYLE_PROPERTY, requestStyle.getName());

      // for policy assertions associated with our endpoint & wsdl subjects
      if (!AeUtil.isNullOrEmpty(aWsdlPolicy)) {
         setupCallForPolicies(aWsdlPolicy, call);
      } else {
         setCredentialsOnCall(aEndpoint, call);
      }

      // Setup a holder for WS-Addressing info
      IAeWebServiceEndpointReference newEndpoint = aEndpoint;
      if (isWsaMandatory(aEndpoint, aDefaultReplyTo)) {
         // Update the endpoint with WS-Addressing info
         IAeAddressingHeaders wsaHeaders = new AeAddressingHeaders(aEndpoint.getSourceNamespace());
         wsaHeaders.setAction(soapAction);
         wsaHeaders.setTo(url);
         wsaHeaders.setReplyTo(aDefaultReplyTo);
         IAePartnerAddressing pa = AeEngineFactory.getPartnerAddressing();
         newEndpoint = pa.updateEndpointHeaders(wsaHeaders, aEndpoint);
      }

      // Add all wsa:ReferenceProperties to the call header, per WS-Addressing spec
      Iterator refProps = newEndpoint.getReferenceProperties().iterator();
      SOAPHeaderElement header = null;
      while (refProps.hasNext()) {
         header = new SOAPHeaderElement((Element) refProps.next());
         // special handling for credentials stored as reference properties
         // in the header
         if (header.getNamespaceURI().equals(CREDENTIALS_NAMESPACE))
            continue;

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

      return call;
   }

   /**
    * Mandatory WSA information should be added automatically if
    * 1. we have a ReplyTo address from the partner endpoint reference properties --OR--
    * 2. we have headers required for engine managed correlation in the default reply to ref props
    * TODO: Use the 2006 WSA SOAP Binding spec to decide this in the future
    */
   protected boolean isWsaMandatory(IAeWebServiceEndpointReference aPartnerEndpoint, IAeWebServiceEndpointReference aMyEndpoint) throws AeWsAddressingException {
      IAeAddressingHeaders wsaHeaders = new AeAddressingHeaders(aPartnerEndpoint.getSourceNamespace());
      wsaHeaders.setReferenceProperties(aPartnerEndpoint.getReferenceProperties());
      return (wsaHeaders.getReplyTo() != null || AeUtil.notNullOrEmpty(aMyEndpoint.getReferenceProperties()));
   }

   /**
    * Extract the endpoint url string from the service.
    *
    * @param aWsdlService
    * @param aEndpointRef
    * @param aAddressHandlingType
    * @throws AeBusinessProcessException
    */
   protected String getEndpointUrl(Service aWsdlService, IAeWebServiceEndpointReference aEndpointRef, AeAddressHandlingType aAddressHandlingType) throws AeBusinessProcessException {
      // get the port we are executin on the service and get the url
      String url = ""; //$NON-NLS-1$

      if (aAddressHandlingType == AeAddressHandlingType.SERVICE) {
         Port port = aWsdlService.getPort(aEndpointRef.getServicePort());
         if (port == null) {
            Object[] args = {aEndpointRef.getServiceName(), aEndpointRef.getServicePort()};
            throw new AeBusinessProcessException(AeMessages.format("AeInvokeHandler.MissingPort", args)); //$NON-NLS-1$
         }

         List extList = port.getExtensibilityElements();
         for (int i = 0; extList != null && i < extList.size(); i++) {
            Object obj = extList.get(i);

            if (obj instanceof UnknownExtensibilityElement) {
               UnknownExtensibilityElement sop = (UnknownExtensibilityElement) obj;
               if ("address".equals(sop.getElement().getLocalName())) //$NON-NLS-1$
               {
                  url = sop.getElement().getAttribute("location"); //$NON-NLS-1$
                  break;
               }
            }
         }
      } else if (aAddressHandlingType == AeAddressHandlingType.ADDRESS) {
         url = aEndpointRef.getAddress();
      }

      // in either case, send the url through the urn mapping facility to see if
      // it maps to another url.
      return AeEngineFactory.getURNResolver().getURL(url);
   }

   /**
    * Determine the call style (RPC or Document).
    *
    * @param aWsdlService
    * @param aOperationName
    * @param aEndpointRef
    * @throws AeBusinessProcessException
    */
   protected Style getRequestStyle(Service aWsdlService, String aOperationName, IAeWebServiceEndpointReference aEndpointRef) throws AeBusinessProcessException {
      if (aWsdlService == null) {
         return Style.DOCUMENT;
      }

      Style requestStyle = null;

      Port port = aWsdlService.getPort(aEndpointRef.getServicePort());
      Binding binding = port.getBinding();
      List extList = binding.getExtensibilityElements();

      for (int i = 0; extList != null && i < extList.size(); i++) {
         Object obj = extList.get(i);

         if (obj instanceof UnknownExtensibilityElement) {
            UnknownExtensibilityElement sop = (UnknownExtensibilityElement) obj;
            if ("binding".equals(sop.getElement().getLocalName())) //$NON-NLS-1$
            {
               String style = sop.getElement().getAttribute("style"); //$NON-NLS-1$
               if (Style.DOCUMENT.getName().equals(style)) {
                  requestStyle = Style.DOCUMENT;
               } else if (Style.RPC.getName().equals(style)) {
                  requestStyle = Style.RPC;
               }
               break;
            }
         }
      }

      // find out if document or rpc and get the soap action from binding/operation
      BindingOperation bop = getBindingOperation(aWsdlService, aOperationName, aEndpointRef);
      extList = bop.getExtensibilityElements();
      for (int i = 0; extList != null && i < extList.size(); i++) {
         Object obj = extList.get(i);

         if (obj instanceof UnknownExtensibilityElement) {
            UnknownExtensibilityElement sop = (UnknownExtensibilityElement) obj;
            if ("operation".equals(sop.getElement().getLocalName())) //$NON-NLS-1$
            {
               String style = sop.getElement().getAttribute("style"); //$NON-NLS-1$
               if (Style.DOCUMENT_STR.equals(style)) {
                  requestStyle = Style.DOCUMENT;
               } else if (Style.RPC_STR.equals(style)) {
                  requestStyle = Style.RPC;
               }
            }
         }
      }
      return requestStyle;
   }

   /**
    * Look for the value of a soapAction attribute.
    * If no action is explicitly defined in wsdl,
    * returns the implicit action based on the service namespace and operation name
    * since WS-Addressing mandates the use of a wsa:Action header
    *
    * @param aWsdlService
    * @param aOperationName
    * @param aEndpointRef
    * @throws AeBusinessProcessException
    */
   protected String getSoapAction(Service aWsdlService, String aOperationName, IAeWebServiceEndpointReference aEndpointRef) throws AeBusinessProcessException {
      String soapAction = null;
      if (aWsdlService != null) {
         // get the soap action from binding/operation
         BindingOperation bop = getBindingOperation(aWsdlService, aOperationName, aEndpointRef);
         List extList = bop.getExtensibilityElements();
         for (int i = 0; extList != null && i < extList.size(); i++) {
            Object obj = extList.get(i);

            if (obj instanceof UnknownExtensibilityElement) {
               UnknownExtensibilityElement sop = (UnknownExtensibilityElement) obj;
               if ("operation".equals(sop.getElement().getLocalName())) //$NON-NLS-1$
               {
                  soapAction = sop.getElement().getAttribute("soapAction"); //$NON-NLS-1$
                  break;
               }
            }
         }
      }
      return soapAction;
   }

   /**
    * Get the <code>BindingOperation</code> from the service object.
    *
    * @param aWsdlService
    * @param aOperationName
    * @param aEndpointRef
    * @throws AeBusinessProcessException
    */
   protected BindingOperation getBindingOperation(Service aWsdlService, String aOperationName, IAeWebServiceEndpointReference aEndpointRef) throws AeBusinessProcessException {
      Port port = aWsdlService.getPort(aEndpointRef.getServicePort());
      Binding binding = port.getBinding();
      BindingOperation operation;
      if (binding == null || (operation = binding.getBindingOperation(aOperationName, null, null)) == null) {
         Object[] args = {aEndpointRef.getServiceName(), aEndpointRef.getServicePort()};
         throw new AeBusinessProcessException(AeMessages.format("AeInvokeHandler.MissingBinding", args)); //$NON-NLS-1$
      }
      return operation;
   }

   /**
    * Get the <code>PortType</code> that we're invoking.
    *
    * @param aWsdlService
    * @param aEndpointRef
    * @param aInvoker
    * @throws AeBusinessProcessException
    */
   protected PortType getPortType(Service aWsdlService, IAeWebServiceEndpointReference aEndpointRef, IAeInvoke aInvoker) throws AeBusinessProcessException {
      PortType portType = null;
      if (aWsdlService != null) {
         // get the operation and build the body elements
         Port port = aWsdlService.getPort(aEndpointRef.getServicePort());
         if (port == null) {
            Object[] args = {aEndpointRef.getServiceName().getNamespaceURI(), aEndpointRef.getServiceName().getLocalPart(), aEndpointRef.getServicePort()};
            throw new AeBusinessProcessException(AeMessages.format("AeInvokeHandler.MissingPort", args)); //$NON-NLS-1$
         }
         if (port.getBinding() == null) {
            throw new AeBusinessProcessException(AeMessages.format("AeInvokeHandler.MissingBinding", aEndpointRef.getServicePort())); //$NON-NLS-1$
         }
         portType = port.getBinding().getPortType();
      } else {
         // TODO (RN) May want to revisit this to pass the IAeProcessDeployment instead of looking it up
         IAeContextWSDLProvider wsdlProvider = AeEngineFactory.getDeploymentProvider().findDeploymentPlan(aInvoker.getProcessId(), aInvoker.getProcessName());
         if (wsdlProvider != null) {
            AeBPELExtendedWSDLDef def = AeWSDLDefHelper.getWSDLDefinitionForPortType(wsdlProvider, aInvoker.getPortType());
            if (def != null)
               portType = def.getPortType(aInvoker.getPortType());
            else
               throw new AeBusinessProcessException(AeMessages.format("AeInvokeHandler.NoDefForPortType", aInvoker.getPortType())); //$NON-NLS-1$
         }
      }

      return portType;
   }

   /**
    * Create the invoke context.
    *
    * @param aInvoke
    * @param aOperation
    * @param aResponse
    * @param aCall
    */
   protected AeInvokeContext createInvokeContext(IAeInvoke aInvoke, Operation aOperation, AeInvokeResponse aResponse, Call aCall) {
      AeInvokeContext ctx = new AeInvokeContext();
      ctx.setInvoke(aInvoke);
      ctx.setOperation(aOperation);
      ctx.setResponse(aResponse);
      ctx.setCall(aCall);
      return ctx;
   }

   /**
    * Sets the fault data on the response.
    *
    * @param aPortTypeNamespace namespace of the port type we invoked
    * @param aOper              the operation we invoked
    * @param aAxisFault         fault generated from invoke
    * @param aResponse          response object we're populating
    */
   protected void setFaultOnResponse(QName aPortType, Operation aOper, AxisFault aAxisFault, AeInvokeResponse aResponse) {
      QName faultCode = aAxisFault.getFaultCode();
      Element[] details = aAxisFault.getFaultDetails();
      Element firstDetailElement = details != null && details.length > 0 ? details[0] : null;

      AeFaultMatcher faultMatcher = new AeFaultMatcher(aPortType, aOper, faultCode, firstDetailElement);
      Fault wsdlFault = faultMatcher.getWsdlFault();
      QName faultName = faultMatcher.getFaultName();
      if (faultName == null) {
         faultName = new QName(Constants.NS_URI_AXIS, Constants.FAULT_SERVER_GENERAL);
      }

      AeWebServiceMessageData data = null;
      if (wsdlFault != null) {
         // if we have a wsdl fault, then the faultName is the QName of the wsdl fault
         // and the data is extracted from the firstDetailElement
         data = extractMessageData(wsdlFault, firstDetailElement);
      }

      aResponse.setFaultData(faultName, data);
      aResponse.setErrorString(aAxisFault.getFaultString());

      // we weren't able to extract a WSDL fault, convert the error details
      // into a human readable string for debugging purposes.
      if (data == null) {
         aResponse.setErrorDetail(getErrorDetail(aAxisFault));
      }
   }

   /**
    * Converts the Element[] in Axis's faultDetail to a String for as the error
    * detail string in our response.
    *
    * @param aFault
    */
   protected String getErrorDetail(AxisFault aFault) {
      String errorDetail = null;
      Element[] details = aFault.getFaultDetails();
      if (details != null) {
         StringWriter sw = new StringWriter();
         PrintWriter pw = new PrintWriter(sw);

         for (int i = 0; i < details.length; i++) {
            // todo should we include the element names?
            pw.println(AeXmlUtil.getText(details[i]));
         }

         errorDetail = sw.toString();
      }
      return errorDetail;
   }

   /**
    * Extracts the message data from an AxisFault's fault details.
    *
    * @param aWsdlFault
    * @param aFirstDetailElement
    */
   protected AeWebServiceMessageData extractMessageData(Fault aWsdlFault, Element aFirstDetailElement) {
      AeWebServiceMessageData data = new AeWebServiceMessageData(aWsdlFault.getMessage().getQName());
      String partName = (String) aWsdlFault.getMessage().getParts().keySet().iterator().next();
      if (isSimpleType(aFirstDetailElement)) {
         data.setData(partName, AeXmlUtil.getText(aFirstDetailElement));
      } else {
         Document doc = AeXmlUtil.newDocument();
         Element details = (Element) doc.importNode(aFirstDetailElement, true);
         doc.appendChild(details);
         data.setData(partName, doc);
      }
      return data;
   }

   /**
    * Helper method that checks to see if the passed elements data is a simple type
    * or complex type.
    *
    * @param aElement The element to check the contents of.
    */
   protected boolean isSimpleType(Element aElement) {
      boolean simple = false;
      // TODO Simple check for now, a complex type will have attributes and/or child elements.
      if (AeUtil.isNullOrEmpty(aElement.getNamespaceURI()) && AeXmlUtil.getFirstSubElement(aElement) == null) {
         simple = true;
         if (aElement.hasAttributes()) {
            NamedNodeMap attrs = aElement.getAttributes();
            for (int i = 0; i < attrs.getLength(); ++i) {
               String nsURI = attrs.item(i).getNamespaceURI();
               if (!IAeBPELConstants.W3C_XMLNS.equals(nsURI) &&
                     !IAeBPELConstants.W3C_XML_SCHEMA_INSTANCE.equals(nsURI)) {
                  simple = false;
                  break;
               }
            }
         }
      }
      return simple;
   }

   /**
    * Extracts the credentials (if any) from the endpoint reference and sets them
    * on the call object.
    *
    * @param aEndpointReference
    * @param aCall
    */
   protected void setCredentialsOnCall(IAeWebServiceEndpointReference aEndpointReference,
                                       Call aCall) {
      String username = aEndpointReference.getUsername();
      if (!AeUtil.isNullOrEmpty(username)) {
         aCall.setUsername(username);
      }
      String password = aEndpointReference.getPassword();
      if (!AeUtil.isNullOrEmpty(password)) {
         aCall.setPassword(password);
      }
   }

   /**
    * Get policy driven call properties.
    *
    * @param aPolicyList
    */
   protected Map getPolicyDrivenProperties(List aPolicyList) throws AeBusinessProcessException {
      try {
         // Map policy assertions to call properties
         if (!AeUtil.isNullOrEmpty(aPolicyList)) {
            // get the main policy mapper
            IAePolicyMapper mapper = AeEngineFactory.getPolicyMapper();
            // get Client Request properties
            return mapper.getCallProperties(aPolicyList);
         } else {
            return Collections.EMPTY_MAP;
         }
      }
      catch (Throwable t) {
         throw new AeBusinessProcessException(AeMessages.getString("AeInvokeHandler.0"), t);  //$NON-NLS-1$
      }
   }

   /**
    * Call setup for policy assertions.
    *
    * @param aPolicyList
    * @param aCall
    */
   protected void setupCallForPolicies(List aPolicyList, Call aCall) throws AeException {
      try {
         // Map policy assertions to call properties
         if (!AeUtil.isNullOrEmpty(aPolicyList)) {
            Map props = getPolicyDrivenProperties(aPolicyList);
            for (Iterator it = props.keySet().iterator(); it.hasNext();) {
               String name = (String) it.next();
               if (name.equals(TAG_ASSERT_AUTH_USER))
                  aCall.setUsername((String) props.get(name));
               else if (name.equals(TAG_ASSERT_AUTH_PASSWORD)) {
                  String password = (String) props.get(name);
                  aCall.setPassword(AeCryptoUtil.decryptString(password));
               } else if (name.equals(PARAM_TRANSPORT)) {
                  Transport transport = (Transport) props.get(name);
                  transport.setUrl(aCall.getTargetEndpointAddress());
                  aCall.setTransport(transport);
               } else
                  aCall.setProperty(name, props.get(name));
            }
         }
      }
      catch (Throwable t) {
         throw new AeException(AeMessages.getString("AeInvokeHandler.0"), t);  //$NON-NLS-1$
      }
   }

   /**
    * Gets all of the policy attachments from the endpoint and wsdl definitions that may
    * be required for the invoke
    *
    * @param aInvoke
    * @param aService
    * @param aOperation
    * @throws AeBusinessProcessException
    */
   protected List getWsdlPolicies(AeBPELExtendedWSDLDef aDef, IAeInvoke aInvoke, IAeWebServiceEndpointReference aEndpoint) throws AeBusinessProcessException {
      // Resolve from the wsdl definition
      List policies = new ArrayList();
      if (!AeUtil.isNullOrEmpty(aEndpoint.getServiceName())) {
         policies.addAll(AeWSDLPolicyHelper.getEffectivePolicy(aDef, aEndpoint.getServiceName(), aEndpoint.getServicePort(), aInvoke.getOperation()));
      } else {
         policies.addAll(AeWSDLPolicyHelper.getEffectivePolicy(aDef, aInvoke.getPortType(), aInvoke.getOperation()));
      }

      // The policy mappers will expect an array of elements
      // TODO (KP) maybe treat all policy lists as IAePolicy, rather than converting back and forth
      List policyElements = new ArrayList();
      if (!AeUtil.isNullOrEmpty(policies)) {
         for (Iterator it = policies.iterator(); it.hasNext();) {
            IAePolicy policy = (IAePolicy) it.next();
            policyElements.add(policy.getPolicyElement());
         }
      }

      // resolve endpoint policies
      if (!AeUtil.isNullOrEmpty(aEndpoint.getPolicies())) {
         IAeContextWSDLProvider wsdlProvider = AeEngineFactory.getDeploymentProvider().findDeploymentPlan(aInvoke.getProcessId(), aInvoke.getProcessName());
         policyElements.addAll(AeWSDLPolicyHelper.resolvePolicyReferences(wsdlProvider, aEndpoint.getPolicies()));
      }

      return policyElements;
   }

   protected AeBPELExtendedWSDLDef getWsdlDef(IAeInvoke aInvoke, IAeWebServiceEndpointReference aEndpointRef) throws AeBusinessProcessException {
      AeBPELExtendedWSDLDef def = null;
      if (aEndpointRef.getServiceName() != null) {
         // they have a service name, see if we can find the wsdl for it
         def = AeWSDLDefHelper.getWSDLDefinitionForService(AeEngineFactory.getCatalog(), aEndpointRef.getServiceName());
         // if not global wsdl check the context wsdl for service
         if (def == null) {
            IAeContextWSDLProvider wsdlProvider = AeEngineFactory.getDeploymentProvider().findDeploymentPlan(aInvoke.getProcessId(), aInvoke.getProcessName());
            if (wsdlProvider != null)
               def = AeWSDLDefHelper.getWSDLDefinitionForService(wsdlProvider, aEndpointRef.getServiceName());
         }
      } else {
         IAeContextWSDLProvider wsdlProvider = AeEngineFactory.getDeploymentProvider().findDeploymentPlan(aInvoke.getProcessId(), aInvoke.getProcessName());
         if (wsdlProvider != null)
            def = AeWSDLDefHelper.getWSDLDefinitionForPortType(wsdlProvider, aInvoke.getPortType());
      }
      return def;
   }
}
