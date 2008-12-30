// $Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/IAePolicyConstants.java,v 1.7 2007/01/26 22:29:5
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
package org.activebpel.rt;

import javax.xml.namespace.QName;

/**
 * Standard constants used in Active Endpoints policies.  
 */
public interface IAePolicyConstants
{
   /**
    *  Generic Handler constants
    */
   public static final String TAG_VALUE_ATTR = "value"; //$NON-NLS-1$
   public static final String TAG_NAME_ATTR = "name"; //$NON-NLS-1$
   public static final String TAG_TYPE_ATTR = "type"; //$NON-NLS-1$
   
   /**
    *  Policy-Driven provider constants
    */
   public static final String PARAM_HANDLER_CLASS = "handlerClass"; //$NON-NLS-1$
   public static final String PARAM_DELEGATE_CLASS = "handlerDelegate"; //$NON-NLS-1$
   public static final String PARAM_TRANSPORT = "Transport"; //$NON-NLS-1$
   public static final String PARAM_RECEIVE_HANDLER = "receiveHandler"; //$NON-NLS-1$
   public static final String PARAM_STYLE = "Style"; //$NON-NLS-1$
   public static final String PARAM_USE = "Use"; //$NON-NLS-1$
   
   /**
    *  Engine managed correlation constants
    */
   public static final String TAG_ASSERT_MANAGED_CORRELATION = "engineManagedCorrelationPolicy"; //$NON-NLS-1$
   public static final QName CONVERSATION_ID_HEADER = new QName(IAeConstants.ABX_NAMESPACE_URI, "conversationId"); //$NON-NLS-1$
   
   /**
    *  XPath receiver constants
    */
   public static final String TAG_ASSERT_XPATH_RECEIVE = "ReceiverXPathMap"; //$NON-NLS-1$ 
   public static final String TAG_ASSERT_XPATH_SEND = "SenderXPathMap"; //$NON-NLS-1$
   public static final String XPATH_QUERY_SOURCE = "XPathQuerySource"; //$NON-NLS-1$
   public static final String XPATH_QUERY_SOURCE_CONTEXT = "MessageContext"; //$NON-NLS-1$
   public static final String XPATH_QUERY_SOURCE_OPTIONS = "Options"; //$NON-NLS-1$
   public static final String XPATH_QUERY_PARAMS = "XPathQueryParams"; //$NON-NLS-1$   
   public static final String XMLNS_PREFIX = "xmlns:"; //$NON-NLS-1$
   public static final String XPATH_PREFIX = "xpath:"; //$NON-NLS-1$
   public static final String XPATH_MAP = "XPATH_MAP"; //$NON-NLS-1$
   public static final String AE_CONTEXT_MAPPED_PROPERTIES = "AE_MAPPED_PROPERTIES"; //$NON-NLS-1$

   /**
    *  Reliable Messaging constants
    */
   public static final String TAG_ASSERT_RM = "RMAssertion"; //$NON-NLS-1$
   public static final String TAG_ASSERT_RM_TIMEOUT = "InactivityTimeout"; //$NON-NLS-1$
   public static final String TAG_ASSERT_RM_RETRY_INTERVAL = "BaseRetransmissionInterval"; //$NON-NLS-1$
   public static final String TAG_ASSERT_EXP_BACKOFF = "ExponentialBackoff"; //$NON-NLS-1$   
   public static final String TAG_ASSERT_ACK_INTERVAL = "AcknowledgementInterval"; //$NON-NLS-1$   
   public static final String TAG_ASSERT_MILLIS = "Milliseconds"; //$NON-NLS-1$   
   public static final String TAG_ASSERT_RM_BINDING = "binding"; //$NON-NLS-1$
   public static final String TAG_ASSERT_RM_ACKTO = "AcksTo"; //$NON-NLS-1$
   public static final String TAG_ASSERT_RM_PROXY = "ProxyUrl"; //$NON-NLS-1$
   public static final String RM_TRANSPORT = "RMTransport"; //$NON-NLS-1$
   public static final String RM_TRANS_ID = "AeTransmissionId"; //$NON-NLS-1$
   
   /**
    *  WS-Security constants
    */
   public static final String ISSUER_SERIAL = "IssuerSerial"; //$NON-NLS-1$
   public static final String TAG_ALIAS_ATTR = "alias"; //$NON-NLS-1$
   public static final String TAG_DIRECTION_ATTR = "direction"; //$NON-NLS-1$
   public static final String DIRECTION_IN = "in"; //$NON-NLS-1$
   public static final String DIRECTION_OUT = "out"; //$NON-NLS-1$
   public static final String DIRECTION_BOTH = "both"; //$NON-NLS-1$   
   public static final String TAG_ASSERT_SECURITY_AUTH = "Authentication"; //$NON-NLS-1$
   public static final String TAG_ASSERT_AUTH_USERNAMETOKEN = "UsernameToken"; //$NON-NLS-1$
   public static final String TAG_ASSERT_AUTH_PWD_TEXT = "PasswordText"; //$NON-NLS-1$
   public static final String TAG_ASSERT_AUTH_PWD_DIGEST = "PasswordDigest"; //$NON-NLS-1$
   public static final String TAG_ASSERT_AUTH_NONCE = "Nonce"; //$NON-NLS-1$
   public static final String TAG_ASSERT_AUTH_USER = "User"; //$NON-NLS-1$
   public static final String TAG_ASSERT_AUTH_PWD_CLEARTEXT = "CleartextPassword"; //$NON-NLS-1$
   public static final String TAG_ASSERT_AUTH_PREEMPTIVE = "HTTPPreemptive"; //$NON-NLS-1$   
   public static final String TAG_ASSERT_AUTH_PASSWORD = "Password"; //$NON-NLS-1$
   public static final String TAG_ASSERT_TIMESTAMP = "Timestamp"; //$NON-NLS-1$   
   public static final String TAG_ASSERT_SECURITY_ENCRYPT = "EncryptionParts"; //$NON-NLS-1$
   public static final String TAG_ASSERT_SECURITY_SIGN = "SignatureParts"; //$NON-NLS-1$
   public static final String TAG_NAMESPACE_ATTR = "namespace"; //$NON-NLS-1$
   public static final String DIRECTREFERENCE = "DirectReference"; //$NON-NLS-1$
   public static final String CRYPTO_PROPERTIES = "crypto.properties"; //$NON-NLS-1$
   public static final String ADDUTELEMENT = "addUTElement"; //$NON-NLS-1$
   public static final String DEFAULT_USER = "anonymous"; //$NON-NLS-1$
   public static final String X509KEYIDENTIFIER = "X509KeyIdentifier"; //$NON-NLS-1$
   public static final String SECURITY_ACTION = "SECURITY_ACTION"; //$NON-NLS-1$
   public static final String ENCRYPTION_ACTION = "ENCRYPTION_ACTION"; //$NON-NLS-1$
   public static final String SIGNATURE_USER = "SIGNATURE_USER"; //$NON-NLS-1$
   public static final String SIGNATURE_ACTION = "SIGNATURE_ACTION"; //$NON-NLS-1$
   public static final String RECEIVER_ACTION = "RECEIVER_ACTION"; //$NON-NLS-1$
   
   // retry policies
   /** Retry policy tag name. */
   public static final String RETRY_POLICY_TAG = "retry"; //$NON-NLS-1$
   /** Retry attempts attr name. */
   public static final String RETRY_ATTEMPTS_ATTR = "attempts"; //$NON-NLS-1$
   /** Retry interval attr name. */
   public static final String RETRY_INTERVAL_ATTR = "interval"; //$NON-NLS-1$
   /** Retry service name attr name. */
   public static final String PROCESS_SERVICE_NAME_ATTR = "service"; //$NON-NLS-1$
   /** Retry yes or no element returned from service. */
   public static final String RETRY_TAG = "retry"; //$NON-NLS-1$
   /** Retry interval element returned from service. */
   public static final String INTERVAL_TAG = "interval"; //$NON-NLS-1$
   /** Retry fault list attr name. */
   public static final String FAULT_LIST_ATTR = "faultList"; //$NON-NLS-1$
   /** Retry fault exclusion list attr name. */
   public static final String FAULT_EXCLUSION_LIST_ATTR = "faultExclusionList"; //$NON-NLS-1$

   /** Wild card for qname tests. */
   public static final String QNAME_WILDCARD = "*"; //$NON-NLS-1$

   /** ActiveBPEL retry check document namespace. */
   public static final String ABPEL_RETRY_CHECK_NS = "http://www.activebpel.org/services/retry"; //$NON-NLS-1$
   /** Retry check input element tag name. */
   public static final String RETRY_CHECK_INPUT_TAG = "retryCheckInput"; //$NON-NLS-1$
   /** Retry check input processId element tag name. */
   public static final String RETRY_CHECK_PROCESS_ID_TAG = "retryCheckInput"; //$NON-NLS-1$
   /** Retry check output part name. */
   public static final String RETRY_CHECK_OUTPUT_PART = "output"; //$NON-NLS-1$
   
}
