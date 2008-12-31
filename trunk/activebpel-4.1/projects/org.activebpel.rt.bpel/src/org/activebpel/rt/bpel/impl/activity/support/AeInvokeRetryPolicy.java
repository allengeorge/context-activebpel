//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/support/AeInvokeRetryPolicy.java,v 1.6 2007/05/23 15:26:3
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
package org.activebpel.rt.bpel.impl.activity.support; 

import java.util.Date;
import java.util.Iterator;

import javax.xml.namespace.QName;

import org.activebpel.rt.AeException;
import org.activebpel.rt.IAeConstants;
import org.activebpel.rt.IAePolicyConstants;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.bpel.IAeEndpointReference;
import org.activebpel.rt.bpel.IAeFault;
import org.activebpel.rt.bpel.IAeProcessInfoEvent;
import org.activebpel.rt.bpel.def.activity.AeActivityInvokeDef;
import org.activebpel.rt.bpel.impl.AePartnerLink;
import org.activebpel.rt.bpel.impl.AeProcessInfoEvent;
import org.activebpel.rt.bpel.impl.activity.AeActivityInvokeImpl;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.activebpel.rt.xml.AeXMLParserBase;
import org.activebpel.wsio.IAeWebServiceResponse;
import org.activebpel.wsio.receive.AeMessageContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Encapsulates all of the retry code for the invoke activity
 */
public class AeInvokeRetryPolicy
{
   /** Count of times that alarms have fired to retry this activity. */
   private int mRetries;
   
   /** Ref to the invoke activity */
   private AeActivityInvokeImpl mInvoke;
   
   /**
    * Ctor accepts the invoke
    * @param aInvoke
    */
   public AeInvokeRetryPolicy(AeActivityInvokeImpl aInvoke)
   {
      mInvoke = aInvoke;
   }
   
   /**
    * Getter for the invoke
    */
   protected AeActivityInvokeImpl getInvoke()
   {
      return mInvoke;
   }
   
   /**
    * Getter for the def
    */
   protected AeActivityInvokeDef getDef()
   {
      return (AeActivityInvokeDef) getInvoke().getDefinition();
   }
   
   /**
    * Tests the policies associated with this endpoint to see if the invoke
    * should be rescheduled for a later attempt. Ideally this could be handled
    * by an invoke handler itself, but for internal policy handling it is
    * simpler to do here since all the neccesary info is at hand.
    * @param aFault The fault which occured diuring the invoke.
    * @return true if the invoke has been rescheduled and the fault should be ignored.
    */
   public boolean reschedule(IAeFault aFault) throws AeBusinessProcessException
   {
      AePartnerLink partnerLink = getInvoke().findPartnerLink(getDef().getPartnerLink());
      IAeEndpointReference epr = partnerLink.getPartnerReference();

      // test the endpoint reference downtime policies if any
      if(epr != null && epr.getPolicies() != null && epr.getPolicies().size() > 0)
      {
         for(Iterator iter=epr.getPolicies().iterator(); iter.hasNext(); )
         {
            Element policyEl = (Element)iter.next();

            // test retry assertions
            NodeList children = policyEl.getElementsByTagNameNS(IAeConstants.ABP_NAMESPACE_URI, IAePolicyConstants.RETRY_POLICY_TAG);
            // for backward compatability check either the old policy namespace
            if(children == null || children.getLength() <= 0)
               children = policyEl.getElementsByTagNameNS(IAeConstants.ABPEL_POLICY_NS, IAePolicyConstants.RETRY_POLICY_TAG);
            for(int i = 0; children != null && i < children.getLength(); ++i)
            {
               Element assertionEl = (Element)children.item(i);
               if(rescheduleFromRetryAssertion(aFault, assertionEl))
                  return true;
            }
         }
      }

      return false;
   }

   /**
    * Returns true if based on the passed retry assertion and fault the current invoke
    * has rescheduled itself.
    * @param aFault the fault returned from invoke.
    * @param aRetryAssertionElem The assertion element to test
    * @return true if the invoke has been rescheduled.
    * @throws AeBusinessProcessException
    */
   protected boolean rescheduleFromRetryAssertion(IAeFault aFault, Element aRetryAssertionElem) throws AeBusinessProcessException
   {
      // check if this fault is eligible for a retry
      if(isFaultEligibleForRetry(aFault, aRetryAssertionElem))
      {
         boolean retry = false;
         int intervalValue = 0;
         // check if service name is present
         String service = aRetryAssertionElem.getAttribute(IAePolicyConstants.PROCESS_SERVICE_NAME_ATTR);
         if(AeUtil.notNullOrEmpty(service))
         {
            intervalValue = callRetryCheckService(service, aFault);
            if(intervalValue < 0)
               retry = false;
            else
               retry = true;
         }
         else
         {
            // no service so user must provide attempts and interval in policy

            // check if there are any attempts left
            String attempts = aRetryAssertionElem.getAttribute(IAePolicyConstants.RETRY_ATTEMPTS_ATTR);
            if(AeUtil.notNullOrEmpty(attempts) && (getRetries() + 1) < Integer.parseInt(attempts))
            {
               retry = true;
               String interval = aRetryAssertionElem.getAttribute(IAePolicyConstants.RETRY_INTERVAL_ATTR);
               if(AeUtil.notNullOrEmpty(interval))
                  intervalValue = Integer.parseInt(interval);
            }
         }

         // if we are retrying then schedule it
         if(retry)
         {
            Date deadline = new Date(System.currentTimeMillis() + (intervalValue * 1000));
            getInvoke().setQueued(true);

            // fire an info event so the process has a record of the retry
            AeProcessInfoEvent infoEvent = new AeProcessInfoEvent(
                  getInvoke().getProcess().getProcessId(),
                  getInvoke().getLocationPath(),
                  IAeProcessInfoEvent.GENERIC_INFO_EVENT,
                  null,
                  AeMessages.format("AeInvokeRetryPolicy.RetryingInvoke", deadline.toString())); //$NON-NLS-1$
            getInvoke().getProcess().getEngine().fireInfoEvent(infoEvent);
            
            getInvoke().getProcess().queueAlarm(getInvoke(), deadline);
            return true;
         }
      }
      return false;
   }

   /**
    * Checks the current fault against the list of faults for inclusion or exclusion
    * to determine if the current fault is elegible for retry.
    * @param aFault
    * @param aAssertionEl
    */
   protected boolean isFaultEligibleForRetry(IAeFault aFault, Element aAssertionEl)
   {
      // if fault list is given it must match something in the fault list
      String faultList = aAssertionEl.getAttribute(IAePolicyConstants.FAULT_LIST_ATTR);
      if(AeUtil.notNullOrEmpty(faultList))
      {
         // split list on whitespace and test the indvidual entries
         String[] qnameList = faultList.split("\\s"); //$NON-NLS-1$
         for(int i=0; i < qnameList.length; ++i)
         {
            if(isFaultNameMatch(aFault.getFaultName(), qnameList[i]))
               return true;
         }
         return false;
      }

      // if fault exlusion list is given if the passed construct matches then we will return false
      String faultExclusionList = aAssertionEl.getAttribute(IAePolicyConstants.FAULT_EXCLUSION_LIST_ATTR);
      if(AeUtil.notNullOrEmpty(faultExclusionList))
      {
         // split list on whitespace and test the indvidual entries
         String[] qnameList = faultExclusionList.split("\\s"); //$NON-NLS-1$
         for(int i=0; i < qnameList.length; ++i)
         {
            if(isFaultNameMatch(aFault.getFaultName(), qnameList[i]))
               return false;
         }
         return true;
      }
      else
      {
         // there is an automatic exclusion of portType namespace faults
         QName portType = getInvoke().findPartnerLink(getDef().getPartnerLink()).getDefinition().getPartnerRolePortType();
         StringBuffer buf = (new StringBuffer("{")).append(portType.getNamespaceURI()).append("}").append(IAePolicyConstants.QNAME_WILDCARD); //$NON-NLS-1$ //$NON-NLS-2$
         if(isFaultNameMatch(aFault.getFaultName(), buf.toString()))
            return false;
      }

      return true;
   }

   /**
    * Tests if the passed qname and string qname match.  The string qname can contain wildcards
    * in the form of an asterisk.  The string qname is in the format <CODE>{namespace}localpart</CODE>.
    * @param aFaultName the fault name to test.
    * @param aString qname string in form that can be passed to valurOf in QName. The format is <CODE>{namespace}localpart</CODE>.
    * @return boolean true if the fault names match.
    */
   private boolean isFaultNameMatch(QName aFaultName, String aString)
   {
      try
      {
         QName testName = QName.valueOf(aString);
         if(IAePolicyConstants.QNAME_WILDCARD.equals(testName.getNamespaceURI()) ||
            AeUtil.compareObjects(aFaultName.getNamespaceURI(), testName.getNamespaceURI()) )
         {
            if(IAePolicyConstants.QNAME_WILDCARD.equals(testName.getLocalPart()) ||
               AeUtil.compareObjects(aFaultName.getLocalPart(), testName.getLocalPart()) )
               return true;
         }
      }
      catch (Exception ex)
      {
         // ignore exception on parse or test of fault names
         AeException.logError(ex, ex.getLocalizedMessage());
      }
      return false;
   }

   /**
    * Calls the passed service to decide whether and when the invoke should be retried.
    * @param aService the service to invoke.
    * @param aFault the fault which is being checked for retry.
    * @return int which will be the number of seconds before retrying, -1 means no retry,
    */
   protected int callRetryCheckService(String aService, IAeFault aFault)
   {
      int interval = -1;
      try
      {
         // build message context to call the process service and call it
         AeMessageContext context = new AeMessageContext();
         context.setServiceName(aService);
         Document[] array = new Document[1];
         array[0] = createRetryCheckInput(aFault);
         IAeWebServiceResponse resp = getInvoke().getProcess().getEngine().queueReceiveData(context, array);

         // if the service returned a good response then process it
         if(! resp.isFaultResponse())
         {
            Element elem = ((Document)resp.getMessageData().getMessageData().get(IAePolicyConstants.RETRY_CHECK_OUTPUT_PART)).getDocumentElement();
            String retry = AeXmlUtil.getText(AeXmlUtil.findSubElement(elem, IAePolicyConstants.RETRY_TAG));
            if("yes".equals(retry)) //$NON-NLS-1$
            {
               String intervalStr = AeXmlUtil.getText(AeXmlUtil.findSubElement(elem, IAePolicyConstants.INTERVAL_TAG));
               if(AeUtil.notNullOrEmpty(intervalStr))
                  interval = Integer.parseInt(intervalStr);
            }
         }
         else
         {
            AeException.logWarning(AeMessages.format("AeInvokeRetryPolicy.ERROR_RETRY_SERVICE_FAILURE", aService)); //$NON-NLS-1$
         }
      }
      catch (Exception e)
      {
         // should never happen since the alert invoke is a one-way
         AeException.logError(e, AeMessages.format("AeInvokeRetryPolicy.ERROR_RETRY_SERVICE_FAILURE", aService)); //$NON-NLS-1$
      }
      return interval;
   }

   /**
    * @return the document to be passed to the retry check service operation.
    */
   protected Document createRetryCheckInput(IAeFault aFault) throws AeException
   {
      AeXMLParserBase parser = new AeXMLParserBase();
      Document doc = parser.createDocument();
      Element root = doc.createElementNS(IAePolicyConstants.ABPEL_RETRY_CHECK_NS, IAePolicyConstants.RETRY_CHECK_INPUT_TAG);
      doc.appendChild(root);
      root.setAttributeNS(IAeConstants.W3C_XMLNS, "xmlns", IAePolicyConstants.ABPEL_RETRY_CHECK_NS); //$NON-NLS-1$
      AeXmlUtil.addElementNS(root, IAePolicyConstants.ABPEL_RETRY_CHECK_NS, "faultName", aFault.getFaultName().toString()); //$NON-NLS-1$
      AeXmlUtil.addElementNS(root, IAePolicyConstants.ABPEL_RETRY_CHECK_NS, "processId", "" + getInvoke().getProcess().getProcessId()); //$NON-NLS-1$ //$NON-NLS-2$
      AeXmlUtil.addElementNS(root, IAePolicyConstants.ABPEL_RETRY_CHECK_NS, "processName", getInvoke().getProcess().getName().toString()); //$NON-NLS-1$
      AeXmlUtil.addElementNS(root, IAePolicyConstants.ABPEL_RETRY_CHECK_NS, "invokePath", getInvoke().getLocationPath()); //$NON-NLS-1$
      AeXmlUtil.addElementNS(root, IAePolicyConstants.ABPEL_RETRY_CHECK_NS, "attempts", "" + (getRetries() + 1)); //$NON-NLS-1$ //$NON-NLS-2$
      AeXmlUtil.addElementNS(root, IAePolicyConstants.ABPEL_RETRY_CHECK_NS, "partnerLinkName", getDef().getPartnerLink()); //$NON-NLS-1$
      Element pl = AeXmlUtil.addElementNS(root, IAePolicyConstants.ABPEL_RETRY_CHECK_NS, "partnerLink", null); //$NON-NLS-1$
      Document epr = getInvoke().findPartnerLink(getDef().getPartnerLink()).getPartnerReference().toDocument();
      pl.appendChild(doc.importNode(epr.getDocumentElement(), true));
      return doc;
   }

   /**
    * @return Returns the retries.
    */
   public int getRetries()
   {
      return mRetries;
   }

   /**
    * @param aRetries The retries to set.
    */
   public void setRetries(int aRetries)
   {
      mRetries = aRetries;
   }
}
 
