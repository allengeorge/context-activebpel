//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/AeDefToValidationVisitor.java,v 1.10 2007/09/12 02:48:1
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
package org.activebpel.rt.bpel.def.validation; 

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.def.*;
import org.activebpel.rt.bpel.def.activity.*;
import org.activebpel.rt.bpel.def.activity.support.*;
import org.activebpel.rt.bpel.def.io.ext.AeExtensionElementDef;
import org.activebpel.rt.bpel.def.validation.activity.*;
import org.activebpel.rt.bpel.def.validation.activity.assign.*;
import org.activebpel.rt.bpel.def.validation.activity.decision.AeElseIfValidator;
import org.activebpel.rt.bpel.def.validation.activity.decision.AeIfValidator;
import org.activebpel.rt.bpel.def.validation.activity.links.AeLinkValidator;
import org.activebpel.rt.bpel.def.validation.activity.links.*;
import org.activebpel.rt.bpel.def.validation.activity.scope.*;
import org.activebpel.rt.bpel.def.validation.activity.wsio.*;
import org.activebpel.rt.bpel.def.validation.expressions.*;
import org.activebpel.rt.bpel.def.validation.extensions.AeExtensionActivityValidator;
import org.activebpel.rt.bpel.def.validation.extensions.AeExtensionValidator;
import org.activebpel.rt.bpel.def.validation.extensions.AeExtensionsValidator;
import org.activebpel.rt.bpel.def.validation.process.AeImportValidator;
import org.activebpel.rt.bpel.def.validation.process.AePartnerValidator;
import org.activebpel.rt.bpel.def.validation.process.AePartnersValidator;
import org.activebpel.rt.bpel.def.validation.process.AeProcessValidator;
import org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor;
import org.activebpel.rt.bpel.def.visitors.AeDefTraverser;
import org.activebpel.rt.bpel.def.visitors.AeTraversalVisitor;
import org.activebpel.rt.bpel.def.visitors.IAeDefTraverser;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Visits the def to create validators
 */
public abstract class AeDefToValidationVisitor extends AeAbstractDefVisitor
{
   protected static final Object NULL = new Object();
   /** root validator */
   private AeProcessValidator mProcessValidator;
   /** stack used for parenting */
   private Stack mStack = new Stack();
   /** context for the validation */
   private IAeValidationContext mContext;
   /** maps defs classes to their validators */
   private Map mDefToValidatorMap = new HashMap();
   
   /**
    * Ctor accepts the validation context
    * @param aContext
    */
   protected AeDefToValidationVisitor(IAeValidationContext aContext)
   {
      mContext = aContext;
      setTraversalVisitor(new AeTraversalVisitor(createTraverser(), this));
      initMap();
   }
   
   /**
    * Creates a traverser for the def
    */
   protected IAeDefTraverser createTraverser()
   {
      return new AeDefTraverser();
   }
   
   /**
    * Creates map of def class to validator class
    */
   protected void initMap()
   {
      Map map = getDefToValidatorMap();
      map.put(AeProcessDef.class,                   AeProcessValidator.class);
      map.put(AeActivityAssignDef.class,            AeActivityAssignValidator.class);
      map.put(AeActivityCompensateDef.class,        AeActivityCompensateValidator.class);
      map.put(AeActivityCompensateScopeDef.class,   AeActivityCompensateScopeValidator.class);
      map.put(AeActivityEmptyDef.class,             AeActivityEmptyValidator.class);
      map.put(AeActivityFlowDef.class,              AeActivityFlowValidator.class);
      map.put(AeActivityInvokeDef.class,            AeActivityInvokeValidator.class);
      map.put(AeActivityPickDef.class,              AeActivityPickValidator.class);
      map.put(AeActivityReceiveDef.class,           AeActivityReceiveValidator.class);
      map.put(AeActivityReplyDef.class,             AeActivityReplyValidator.class);
      map.put(AeActivitySuspendDef.class,           AeActivitySuspendValidator.class);
      map.put(AeActivityContinueDef.class,          AeActivityContinueValidator.class);
      map.put(AeActivityBreakDef.class,             AeActivityBreakValidator.class);
      map.put(AeCorrelationSetDef.class,            AeCorrelationSetValidator.class);
      map.put(AeCatchAllDef.class,                  AeCatchAllValidator.class);
      map.put(AeVariableDef.class,                  AeVariableValidator.class);
      map.put(AeVariablesDef.class,                 AeVariablesValidator.class);
      map.put(AeEventHandlersDef.class,             AeEventHandlersValidator.class);
      map.put(AeCompensationHandlerDef.class,       AeCompensationHandlerValidator.class);
      map.put(AeCorrelationSetsDef.class,           AeCorrelationSetsValidator.class);
      map.put(AeOnMessageDef.class,                 AeOnMessageValidator.class);
      map.put(AeOnEventDef.class,                   AeOnEventValidator.class);
      map.put(AeOnAlarmDef.class,                   AeOnAlarmValidator.class);
      map.put(AeActivitySequenceDef.class,          AeActivitySequenceValidator.class);
      map.put(AeActivityExitDef.class,              AeActivityExitValidator.class);
      map.put(AeActivityWaitDef.class,              AeActivityWaitValidator.class);
      map.put(AeActivityWhileDef.class,             AeActivityWhileValidator.class);
      map.put(AeActivityRepeatUntilDef.class,       AeActivityRepeatUntilValidator.class);
      map.put(AeActivityForEachDef.class,           AeActivityForEachValidator.class);
      map.put(AeForEachCompletionConditionDef.class, AeForEachCompletionConditionValidator.class);
      map.put(AeForEachStartDef.class,              AeForEachStartValidator.class);
      map.put(AeForEachFinalDef.class,              AeForEachFinalValidator.class);
      map.put(AeForEachBranchesDef.class,           AeForEachBranchesValidator.class);
      map.put(AePartnerDef.class,                   AePartnerValidator.class);
      map.put(AeMessageExchangesDef.class,          AeMessageExchangesValidator.class);
      map.put(AeMessageExchangeDef.class,           AeMessageExchangeValidator.class);
      map.put(AeAssignCopyDef.class,                AeAssignCopyValidator.class);
      map.put(AeCorrelationDef.class,               AeCorrelationValidator.class);
      map.put(AeLinkDef.class,                      AeLinkValidator.class);
      map.put(AeSourceDef.class,                    AeSourceValidator.class);
      map.put(AeTargetDef.class,                    AeTargetValidator.class);
      map.put(AePartnerLinksDef.class,              AePartnerLinksValidator.class);
      map.put(AePartnersDef.class,                  AePartnersValidator.class);
      map.put(AePartnerLinkDef.class,               AePartnerLinkValidator.class);
      map.put(AeLinksDef.class,                     AeLinksValidator.class);
      map.put(AeCorrelationsDef.class,              AeCorrelationsValidator.class);
      map.put(AeFromDef.class,                      AeFromValidator.class);
      map.put(AeToDef.class,                        AeToValidator.class);
      map.put(AeImportDef.class,                    AeImportValidator.class);
      map.put(AeDocumentationDef.class,             NULL);
      map.put(AeActivityValidateDef.class,          AeActivityValidateValidator.class);
      map.put(AeExtensibleAssignDef.class,          AeExtensibleAssignValidator.class);
      map.put(AeExtensionsDef.class,                AeExtensionsValidator.class);
      map.put(AeExtensionDef.class,                 AeExtensionValidator.class);
      map.put(AeFromPartsDef.class,                 AeFromPartsValidator.class);
      map.put(AeToPartsDef.class,                   AeToPartsValidator.class);
      map.put(AeFromPartDef.class,                  AeFromPartValidator.class);
      map.put(AeToPartDef.class,                    AeToPartValidator.class);
      map.put(AeSourcesDef.class,                   AeSourcesValidator.class);
      map.put(AeTargetsDef.class,                   AeTargetsValidator.class);
      map.put(AeTransitionConditionDef.class,       AeTransitionConditionValidator.class);
      map.put(AeJoinConditionDef.class,             AeJoinConditionValidator.class);
      map.put(AeForDef.class,                       AeForValidator.class);
      map.put(AeUntilDef.class,                     AeUntilValidator.class);
      map.put(AeExtensionActivityDef.class,         AeExtensionActivityValidator.class);
      map.put(AeNotUnderstoodExtensionActivityDef.class,         AeActivityExtensionValidator.class);
      map.put(AeIfDef.class,                        AeIfValidator.class);
      map.put(AeElseIfDef.class,                    AeElseIfValidator.class);
      map.put(AeConditionDef.class,                 AeConditionValidator.class);
      map.put(AeActivityRethrowDef.class,           AeActivityRethrowValidator.class);
      map.put(AeRepeatEveryDef.class,               AeRepeatEveryValidator.class);
      map.put(AeTerminationHandlerDef.class,        AeTerminationHandlerValidator.class);
      map.put(AeLiteralDef.class,                   AeLiteralValidator.class);
      map.put(AeScopeDef.class,                     NULL);
      map.put(AeQueryDef.class,                     NULL);
      map.put(AeActivityOpaqueDef.class,            AeActivityOpaqueValidator.class);
      map.put(AeExtensionAttributeDef.class,        NULL);
      map.put(AeExtensionElementDef.class,          NULL);
   }
   
   /**
    * Getter for the process validator
    */
   protected AeProcessValidator getProcessValidator()
   {
      return mProcessValidator;
   }
   
   /**
    * Setter for the process validator
    * @param aValidator
    */
   protected void setProcessValidator(AeProcessValidator aValidator)
   {
      mProcessValidator = aValidator;
   }
   
   /**
    * Getter for the current parent 
    */
   protected AeBaseValidator getParent()
   {
      return (AeBaseValidator) mStack.peek();
   }
   
   /**
    * Getter for the def to validator map
    */
   protected Map getDefToValidatorMap()
   {
      return mDefToValidatorMap;
   }
   
   /**
    * Gets the validator class to create for the given def
    * @param aDef
    */
   protected Class getValidatorClass(AeBaseDef aDef)
   {
      Object clazz = getDefToValidatorMap().get(aDef.getClass());
      if (clazz == NULL)
      {
         // there is no validation validator for this def, we'll return null which skips it
         return null;
      }
      else if (clazz == null)
      {
         // we didn't have a mapping for this def, this is a configuation error
         AeException.logError(new Exception(), "Error in validation configuration"); //$NON-NLS-1$
      }
      return (Class) clazz;
   }
   
   /**
    * Creates the validator given the validator class and the arg for its ctor
    * @param aValidatorClass
    * @param aDef
    */
   protected AeBaseValidator createValidator(Class aValidatorClass, AeBaseDef aDef)
   {
      try
      {
         Constructor c = aValidatorClass.getConstructor(new Class[]{aDef.getClass()});
         return (AeBaseValidator) c.newInstance(new Object[]{aDef});
      }
      catch(Throwable t)
      {
         AeException.logError(t);
         return null;
      }
   }

   /**
    * Creates the process validator for the given process def.
    * 
    * @param aProcessDef
    */
   protected AeProcessValidator createProcessValidator(AeProcessDef aProcessDef)
   {
      try
      {
         Class validatorClass = getValidatorClass(aProcessDef);
         Class [] classes = new Class[] { IAeValidationContext.class, AeProcessDef.class };
         Object [] args = new Object[] { mContext, aProcessDef };
         Constructor c = validatorClass.getConstructor(classes);
         return (AeProcessValidator) c.newInstance(args);
      }
      catch (Exception e)
      {
         AeException.logError(e);
         return null;
      }
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor#traverse(org.activebpel.rt.bpel.def.AeBaseDef)
    */
   protected void traverse(AeBaseDef aDef)
   {
      Class validatorClass = getValidatorClass(aDef);
      if (validatorClass == null)
      {
         super.traverse(aDef);
      }
      else
      {
         AeBaseValidator validator = createValidator(validatorClass, aDef);
         traverse(aDef, validator);
      }
   }

   /**
    * Generic traverse method handles the push, traverse, and pop
    * @param aDef
    * @param aImpl
    */
   protected void traverse(AeBaseDef aDef, AeBaseValidator aImpl)
   {
      if (!mStack.isEmpty())
      {
         getParent().add(aImpl);
      }

      // So, if this is the process, then the top part doesn't get executed - just this one
      mStack.push(aImpl);
      aDef.accept(getTraversalVisitor());
      mStack.pop();
   }

   /**
    * Creates process validator and sets the root
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AeProcessDef)
    */
   public void visit(AeProcessDef aDef)
   {
      setProcessValidator(createProcessValidator(aDef));
      traverse(aDef, getProcessValidator());
      
      getProcessValidator().validate();
   }
}
 
