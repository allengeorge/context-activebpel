//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/AeWSBPELFaultFactory.java,v 1.10 2006/12/14 15:12:0
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
package org.activebpel.rt.bpel.impl; 

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.bpel.IAeFault;
import org.activebpel.rt.util.AeUtil;

/**
 * Impl of the fault factory interface for WS-BPEL 2.0
 */
public class AeWSBPELFaultFactory extends AeBaseFaultFactory implements IAeFaultFactory
{
   private static final String SELECTION_FAILURE = "selectionFailure"; //$NON-NLS-1$
   private static final String CONFLICTING_RECEIVE = "conflictingReceive"; //$NON-NLS-1$
   private static final String CONFLICTING_REQUEST = "conflictingRequest"; //$NON-NLS-1$
   private static final String MISMATCHED_ASSIGNMENT_FAILURE = "mismatchedAssignmentFailure"; //$NON-NLS-1$
   private static final String JOIN_FAILURE = "joinFailure"; //$NON-NLS-1$
   private static final String CORRELATION_VIOLATION = "correlationViolation"; //$NON-NLS-1$
   private static final String UNINITIALIZED_VARIABLE = "uninitializedVariable"; //$NON-NLS-1$
   private static final String MISSING_REPLY = "missingReply"; //$NON-NLS-1$
   private static final String MISSING_REQUEST = "missingRequest"; //$NON-NLS-1$
   private static final String SUB_LANGUAGE_EXECUTION_FAULT = "subLanguageExecutionFault"; //$NON-NLS-1$
   private static final String UNSUPPORTED_REFERENCE = "unsupportedReference"; //$NON-NLS-1$
   private static final String INVALID_VARIABLES = "invalidVariables"; //$NON-NLS-1$
   private static final String UNINITIALIZED_PARTNER_ROLE = "uninitializedPartnerRole"; //$NON-NLS-1$
   private static final String SCOPE_ININTIALIZATION_FAILURE = "scopeInitializationFailure"; //$NON-NLS-1$
   private static final String INVALID_BRANCH_CONDITION = "invalidBranchCondition"; //$NON-NLS-1$
   private static final String COMPLETION_CONDITION_FAILURE = "completionConditionFailure"; //$NON-NLS-1$
   private static final String XSLT_STYLESHEET_NOT_FOUND = "xsltStylesheetNotFound"; //$NON-NLS-1$
   private static final String XSLT_INVALID_SOURCE = "xsltInvalidSource"; //$NON-NLS-1$
   private static final String INVALID_EXPRESSION_VALUE = "invalidExpressionValue"; //$NON-NLS-1$
   private static final String AMBIGUOUS_RECEIVE = "ambiguousReceive"; //$NON-NLS-1$
   
   /** std faults for ws-bpel 2.0 */
   private static final String[] STANDARD_FAULTS =
   {
      SELECTION_FAILURE,
      CONFLICTING_RECEIVE,
      CONFLICTING_REQUEST,
      MISMATCHED_ASSIGNMENT_FAILURE,
      JOIN_FAILURE,
      CORRELATION_VIOLATION,
      UNINITIALIZED_VARIABLE,
      MISSING_REPLY,
      MISSING_REQUEST,
      SUB_LANGUAGE_EXECUTION_FAULT,
      UNSUPPORTED_REFERENCE,
      INVALID_VARIABLES,
      UNINITIALIZED_PARTNER_ROLE,
      SCOPE_ININTIALIZATION_FAILURE,
      INVALID_BRANCH_CONDITION,
      COMPLETION_CONDITION_FAILURE,
      XSLT_STYLESHEET_NOT_FOUND,
      XSLT_INVALID_SOURCE,
      INVALID_EXPRESSION_VALUE,
      AMBIGUOUS_RECEIVE
   };
   
   /** standard BPEL faults */
   private static Set STANDARD_FAULTS_SET = new HashSet(Arrays.asList(STANDARD_FAULTS));

   /**
    * Ctor accepts namespace
    * 
    * @param aNamespace
    */
   protected AeWSBPELFaultFactory(String aNamespace)
   {
      super(aNamespace);
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.IAeFaultFactory#getUnmatchedRequest()
    */
   public IAeFault getUnmatchedRequest()
   {
      // fixme (MF-3.1) we should create our own fault for this
      return makeBpelFault(CORRELATION_VIOLATION);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeFaultFactory#isStandardFaultForExit(org.activebpel.rt.bpel.IAeFault)
    */
   public boolean isStandardFaultForExit(IAeFault aFault)
   {
      if ( getNamespace().equals(aFault.getFaultName().getNamespaceURI())
            && !aFault.getFaultName().getLocalPart().equalsIgnoreCase(JOIN_FAILURE) )
      {
         return STANDARD_FAULTS_SET.contains(aFault.getFaultName().getLocalPart());
      }
      return false;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeFaultFactory#getCompletionConditionFailure()
    */
   public IAeFault getCompletionConditionFailure()
   {
      return makeBpelFault(COMPLETION_CONDITION_FAILURE);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeFaultFactory#getConflictingReceive()
    */
   public IAeFault getConflictingReceive()
   {
      return makeBpelFault(CONFLICTING_RECEIVE);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeFaultFactory#getConflictingRequest()
    */
   public IAeFault getConflictingRequest()
   {
      return makeBpelFault(CONFLICTING_REQUEST);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeFaultFactory#getCorrelationViolation()
    */
   public IAeFault getCorrelationViolation()
   {
      return makeBpelFault(CORRELATION_VIOLATION);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeFaultFactory#getForEachCounterError()
    */
   public IAeFault getForEachCounterError()
   {
      return getInvalidExpressionValue(TYPE_UNSIGNEDINT);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeFaultFactory#getInvalidBranchCondition()
    */
   public IAeFault getInvalidBranchCondition()
   {
      return makeBpelFault(INVALID_BRANCH_CONDITION);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeFaultFactory#getInvalidExpressionValue(java.lang.String)
    */
   public IAeFault getInvalidExpressionValue(String aType)
   {
      IAeFault fault = makeBpelFault(INVALID_EXPRESSION_VALUE);
      fault.setInfo(AeMessages.format("AeWSBPELFaultFactory.InvalidExpressionValueError", aType)); //$NON-NLS-1$
      return fault;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeFaultFactory#getInvalidVariables()
    */
   public IAeFault getInvalidVariables()
   {
      return makeBpelFault(INVALID_VARIABLES);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeFaultFactory#getJoinFailure()
    */
   public IAeFault getJoinFailure()
   {
      return makeBpelFault(JOIN_FAILURE);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeFaultFactory#getMismatchedAssignmentFailure()
    */
   public IAeFault getMismatchedAssignmentFailure()
   {
      return makeBpelFault(MISMATCHED_ASSIGNMENT_FAILURE);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeFaultFactory#getMissingReply()
    */
   public IAeFault getMissingReply()
   {
      return makeBpelFault(MISSING_REPLY);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeFaultFactory#getMissingRequest()
    */
   public IAeFault getMissingRequest()
   {
      return makeBpelFault(MISSING_REQUEST);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeFaultFactory#getScopeInitializationFailure()
    */
   public IAeFault getScopeInitializationFailure()
   {
      return makeBpelFault(SCOPE_ININTIALIZATION_FAILURE);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeFaultFactory#getSelectionFailure()
    */
   public IAeFault getSelectionFailure()
   {
      return makeBpelFault(SELECTION_FAILURE);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeFaultFactory#getStandardFaults()
    */
   public String[] getStandardFaults()
   {
      return STANDARD_FAULTS;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeFaultFactory#getSubLanguageExecutionFault(java.lang.String, java.lang.Throwable, java.lang.String)
    */
   public IAeFault getSubLanguageExecutionFault(String aLanguage, Throwable aThrowable, String aErrorMessage)
   {
      String info = MessageFormat.format("[{0}] {1}", new Object[] { aLanguage, aErrorMessage }); //$NON-NLS-1$

      IAeFault fault = makeBpelFault(SUB_LANGUAGE_EXECUTION_FAULT);
      fault.setInfo(info);
      fault.setDetailedInfo(AeUtil.getStacktrace(aThrowable));
      return fault;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeFaultFactory#getUninitializedPartnerRole()
    */
   public IAeFault getUninitializedPartnerRole()
   {
      return makeBpelFault(UNINITIALIZED_PARTNER_ROLE);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeFaultFactory#getUninitializedVariable()
    */
   public IAeFault getUninitializedVariable()
   {
      return makeBpelFault(UNINITIALIZED_VARIABLE);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeFaultFactory#getUnsupportedReference(javax.xml.namespace.QName)
    */
   public IAeFault getUnsupportedReference(QName aElementName)
   {
      return makeBpelFault(UNSUPPORTED_REFERENCE);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeFaultFactory#getXsltInvalidSource()
    */
   public IAeFault getXsltInvalidSource()
   {
      return makeBpelFault(XSLT_INVALID_SOURCE);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeFaultFactory#getXsltStyleSheetNotFound()
    */
   public IAeFault getXsltStyleSheetNotFound()
   {
      return makeBpelFault(XSLT_STYLESHEET_NOT_FOUND);
   }
   
   /**
    * Repeated compensation is ignored for WS-BPEL 2.0
    * 
    * @see org.activebpel.rt.bpel.impl.IAeFaultFactory#getRepeatedCompensation()
    */
   public IAeFault getRepeatedCompensation()
   {
      return null;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeFaultFactory#getAmbiguousReceive()
    */
   public IAeFault getAmbiguousReceive()
   {
      return makeBpelFault(AMBIGUOUS_RECEIVE);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeFaultFactory#isAmbiguousReceiveFaultSupported()
    */
   public boolean isAmbiguousReceiveFaultSupported()
   {
      return true;
   }
} 
