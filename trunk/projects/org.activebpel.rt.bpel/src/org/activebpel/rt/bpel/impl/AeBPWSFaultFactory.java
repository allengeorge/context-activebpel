//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/AeBPWSFaultFactory.java,v 1.3 2006/10/26 13:42:2
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

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.bpel.IAeFault;
import org.activebpel.rt.bpel.impl.activity.support.AeFault;

/**
 * Fault factory for 1.1 faults. 
 */
public class AeBPWSFaultFactory extends AeBaseFaultFactory implements IAeBPWSFaultFactory
{
   // Standard faults from BPEL4WS 1.1
   private static final String SELECTION_FAILURE = "selectionFailure"; //$NON-NLS-1$
   private static final String CONFLICTING_RECEIVE = "conflictingReceive"; //$NON-NLS-1$
   private static final String CONFLICTING_REQUEST = "conflictingRequest"; //$NON-NLS-1$
   private static final String MISMATCHED_ASSIGNMENT_FAILURE = "mismatchedAssignmentFailure"; //$NON-NLS-1$
   private static final String JOIN_FAILURE = "joinFailure"; //$NON-NLS-1$
   private static final String CORRELATION_VIOLATION = "correlationViolation"; //$NON-NLS-1$
   private static final String UNINITIALIZED_VARIABLE = "uninitializedVariable"; //$NON-NLS-1$
   private static final String INVALID_REPLY = "invalidReply"; //$NON-NLS-1$
   private static final String REPEATED_COMPENSATION = "repeatedCompensation"; //$NON-NLS-1$
   private static final String FORCED_TERMINATION = "forcedTermination"; //$NON-NLS-1$
   
   // WS-BPEL 2.0 faults we used in our 2.0 release
   private static final String COMPLETION_CONDITION_FAILURE = "completionConditionFailure"; //$NON-NLS-1$
   private static final String MISSING_REPLY = "missingReply"; //$NON-NLS-1$
   private static final String INVALID_BRANCH_CONDITION = "invalidBranchCondition"; //$NON-NLS-1$
   private static final String FOREACH_COUNTER_ERROR = "forEachCounterError"; //$NON-NLS-1$

   // AE faults for earlier releases, most of these have become standard faults in WS-BPEL
   
   /** Identifier for internal ae:invalidTransitionCondition: used to report a non boolean return from an xpath evaluation of a transition condition*/
   private static final String INVALID_TRANS_CONDITION = "invalidTransitionCondition"; //$NON-NLS-1$
   /** Identifier for internal ae:xpathDateParseError: used to report error in parsing an xsd:date or xsd:datetime */
   private static final String XPATH_DATE_PARSE_ERROR = "xpathDateParseError"; //$NON-NLS-1$
   /** Identifier for internal ae:xpathDurationFormatError: used to report error in parsing an xsd:duration */
   private static final String XPATH_DURATION_FORMAT_ERROR = "xpathDurationFormatError"; //$NON-NLS-1$
   
   /** 
    * Identifier for internal ae:validationError: used to report validation 
    * errors in variable data 
    */
   protected static final String VALIDATION_ERROR = "validationError"; //$NON-NLS-1$
   
   /** Error message for trying to get a fault from the factory that isn't supported */
   private static final String INVALID_FAULT = AeMessages.getString("AeBPEL4WSFaultFactory.InvalidFault"); //$NON-NLS-1$

   private static String[] STANDARD_FAULTS =
   {
      SELECTION_FAILURE,
      CONFLICTING_RECEIVE,
      CONFLICTING_REQUEST,
      MISMATCHED_ASSIGNMENT_FAILURE,
      JOIN_FAILURE,
      CORRELATION_VIOLATION,
      UNINITIALIZED_VARIABLE,
      MISSING_REPLY,
      INVALID_REPLY,
      REPEATED_COMPENSATION,
      FORCED_TERMINATION
   };
   
   /**
    * Ctor accepts namespace
    * @param aNamespace
    */
   public AeBPWSFaultFactory(String aNamespace)
   {
      super(aNamespace);
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.AeWSBPELFaultFactory#getProcessCancelled()
    */
   public IAeFault getProcessCancelled()
   {
      return getForcedTermination();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeFaultFactory#getProcessTerminated()
    */
   public IAeFault getProcessTerminated() 
   {
      return getForcedTermination();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.AeWSBPELFaultFactory#getInvalidExpressionValue(java.lang.String)
    */
   public IAeFault getInvalidExpressionValue(String aType)
   {
      if (TYPE_BOOLEAN.equals(aType))
      {
         // seems strange but we were always throwing this error for boolean conversions, even though they might not 
         // have been transition conditions.
         return AeFaultFactory.makeAeNamespaceFault(INVALID_TRANS_CONDITION);
      }
      else if (TYPE_DEADLINE.equals(aType))
      {
         return AeFaultFactory.makeAeNamespaceFault(XPATH_DATE_PARSE_ERROR);
      }
      else if (TYPE_DURATION.equals(aType))
      {
         return AeFaultFactory.makeAeNamespaceFault(XPATH_DURATION_FORMAT_ERROR);
      }
      else
      {
         return AeFaultFactory.makeAeNamespaceFault(AeFaultFactory.SYSTEM_ERROR);
      }
   }

   /**
    * @see org.activebpel.rt.bpel.impl.AeWSBPELFaultFactory#getUnsupportedReference(javax.xml.namespace.QName)
    */
   public IAeFault getUnsupportedReference(QName aElementName)
   {
      // TODO (MF) use param in fault
      return AeFaultFactory.makeAeNamespaceFault(AeFaultFactory.SYSTEM_ERROR);
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.AeWSBPELFaultFactory#getUninitializedPartnerRole()
    */
   public IAeFault getUninitializedPartnerRole()
   {
      return AeFaultFactory.makeAeNamespaceFault(AeFaultFactory.SYSTEM_ERROR);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeFaultFactory#getSubLanguageExecutionFault(java.lang.String, java.lang.Throwable, java.lang.String)
    */
   public IAeFault getSubLanguageExecutionFault(String aLanguage, Throwable aThrowable, String aErrorMessage)
   {
      if (XPATH_FUNCTION_ERROR.equals(aLanguage))
      {
         return AeFaultFactory.makeAeNamespaceFault(XPATH_FUNCTION_ERROR, aThrowable, aErrorMessage);
      }
      else
      {
         return AeFaultFactory.makeAeNamespaceFault(AeFaultFactory.SYSTEM_ERROR, aThrowable, aErrorMessage);
      }
   }

   /**
    * @see org.activebpel.rt.bpel.impl.AeWSBPELFaultFactory#getInvalidVariables()
    */
   public IAeFault getInvalidVariables()
   {
      return AeFaultFactory.makeAeNamespaceFault(VALIDATION_ERROR);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.AeWSBPELFaultFactory#getScopeInitializationFailure()
    */
   public IAeFault getScopeInitializationFailure()
   {
      throw new UnsupportedOperationException(INVALID_FAULT);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.AeWSBPELFaultFactory#getXsltInvalidSource()
    */
   public IAeFault getXsltInvalidSource()
   {
      throw new UnsupportedOperationException(INVALID_FAULT);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.AeWSBPELFaultFactory#getXsltStyleSheetNotFound()
    */
   public IAeFault getXsltStyleSheetNotFound()
   {
      throw new UnsupportedOperationException(INVALID_FAULT);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.AeWSBPELFaultFactory#getMissingRequest()
    */
   public IAeFault getMissingRequest()
   {
      return makeBpelFault(INVALID_REPLY);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.AeWSBPELFaultFactory#getStandardFaults()
    */
   public String[] getStandardFaults()
   {
      return STANDARD_FAULTS;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeBPWSFaultFactory#getForcedTermination()
    */
   public IAeFault getForcedTermination()
   {
      IAeFault fault = makeBpelFault(FORCED_TERMINATION);
      ((AeFault)fault).setRethrowable(false);
      return fault;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeBPWSFaultFactory#getRepeatedCompensation()
    */
   public IAeFault getRepeatedCompensation()
   {
      return makeBpelFault(REPEATED_COMPENSATION);
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.IAeFaultFactory#isStandardFaultForExit(org.activebpel.rt.bpel.IAeFault)
    */
   public boolean isStandardFaultForExit(IAeFault aFault)
   {
      return false;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeFaultFactory#getAmbiguousReceive()
    */
   public IAeFault getAmbiguousReceive()
   {
      throw new UnsupportedOperationException(INVALID_FAULT);
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
    * @see org.activebpel.rt.bpel.impl.IAeFaultFactory#getInvalidBranchCondition()
    */
   public IAeFault getInvalidBranchCondition()
   {
      return makeBpelFault(INVALID_BRANCH_CONDITION);
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
    * @see org.activebpel.rt.bpel.impl.IAeFaultFactory#getSelectionFailure()
    */
   public IAeFault getSelectionFailure()
   {
      return makeBpelFault(SELECTION_FAILURE);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeFaultFactory#getUninitializedVariable()
    */
   public IAeFault getUninitializedVariable()
   {
      return makeBpelFault(UNINITIALIZED_VARIABLE);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeFaultFactory#getUnmatchedRequest()
    */
   public IAeFault getUnmatchedRequest()
   {
      return makeBpelFault(CORRELATION_VIOLATION);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeFaultFactory#getForEachCounterError()
    */
   public IAeFault getForEachCounterError()
   {
      return makeBpelFault(FOREACH_COUNTER_ERROR);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeFaultFactory#isAmbiguousReceiveFaultSupported()
    */
   public boolean isAmbiguousReceiveFaultSupported()
   {
      return false;
   }
}
 
