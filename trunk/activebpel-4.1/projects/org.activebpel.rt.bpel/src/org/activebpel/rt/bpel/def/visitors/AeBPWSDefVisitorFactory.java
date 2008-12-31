//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/visitors/AeBPWSDefVisitorFactory.java,v 1.6 2006/09/22 19:52:3
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
package org.activebpel.rt.bpel.def.visitors; 

import org.activebpel.rt.bpel.IAeContextWSDLProvider;
import org.activebpel.rt.bpel.IAeExpressionLanguageFactory;
import org.activebpel.rt.bpel.def.io.readers.def.AeCommonSpecStrategyMatcher;
import org.activebpel.rt.bpel.def.io.readers.def.IAeCopyOperationStrategyMatcher;
import org.activebpel.rt.bpel.def.validation.AeBPWSDefToValidationVisitor;
import org.activebpel.rt.bpel.def.validation.IAeValidationContext;
import org.activebpel.rt.bpel.def.visitors.preprocess.strategies.wsio.AeBPWSMessageDataStrategyMatcher;
import org.activebpel.rt.bpel.def.visitors.preprocess.strategies.wsio.AeMessageDataStrategyVisitor;
import org.activebpel.rt.bpel.def.visitors.preprocess.strategies.wsio.IAeMessageDataStrategyMatcher;
import org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal;
import org.activebpel.rt.bpel.impl.IAeBusinessProcessInternal;
import org.activebpel.rt.bpel.impl.IAeDynamicScopeParent;
import org.activebpel.rt.bpel.impl.IAeProcessPlan;

/**
 * Factory for creating def visitors for BPEL4WS 1.1 defs  
 */
public class AeBPWSDefVisitorFactory extends AeDefVisitorFactory
{
   /** matcher for 1.1 from/to strategies */
   private static final IAeCopyOperationStrategyMatcher BPEL4WS_STRATEGY_MATCHER = new AeCommonSpecStrategyMatcher();
   
   /** matcher for 1.1 invoke/reply message data producer strategies */
   private static final IAeMessageDataStrategyMatcher BPEL4WS_MESSAGE_DATA_STRATEGY_MATCHER = new AeBPWSMessageDataStrategyMatcher();

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitorFactory#createDefPathSegmentVisitor()
    */
   public IAeDefPathSegmentVisitor createDefPathSegmentVisitor()
   {
      return new AeBPWSDefPathSegmentVisitor();
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitorFactory#createImplVisitor(org.activebpel.rt.bpel.impl.IAeBusinessProcessInternal, org.activebpel.rt.bpel.impl.IAeDynamicScopeParent)
    */
   public IAeDefToImplVisitor createImplVisitor(IAeBusinessProcessInternal aProcess, IAeDynamicScopeParent aParent)
   {
      return new AeDefToBPWSImplVisitor(aProcess, aParent);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitorFactory#createImplVisitor(long, org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal, org.activebpel.rt.bpel.impl.IAeProcessPlan)
    */
   public IAeDefToImplVisitor createImplVisitor(long aPid, IAeBusinessProcessEngineInternal aEngine, IAeProcessPlan aPlan)
   {
      return new AeDefToBPWSImplVisitor(aPid, aEngine, aPlan);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitorFactory#createImplicitVariableVisitor()
    */
   public IAeDefVisitor createImplicitVariableVisitor()
   {
      return new AeImplicitVariableVisitor();
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitorFactory#createPropertyAliasInlineVisitor(org.activebpel.rt.bpel.IAeContextWSDLProvider, org.activebpel.rt.bpel.IAeExpressionLanguageFactory)
    */
   public IAeDefVisitor createPropertyAliasInlineVisitor(IAeContextWSDLProvider aProvider, IAeExpressionLanguageFactory aExpressionLanguageFactory)
   {
      return new AeInlinePropertyAliasVisitor(aProvider, aExpressionLanguageFactory);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitorFactory#createResourceLockingVisitor(org.activebpel.rt.bpel.IAeExpressionLanguageFactory)
    */
   public IAeDefVisitor createResourceLockingVisitor(IAeExpressionLanguageFactory aExpression)
   {
      return new AeDefVariableUsageVisitor(aExpression);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitorFactory#createValidationVisitor(org.activebpel.rt.bpel.def.validation.IAeValidationContext)
    */
   public IAeDefVisitor createValidationVisitor(IAeValidationContext aContext)
   {
      return new AeBPWSDefToValidationVisitor(aContext);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitorFactory#createCopyOperationStrategyVisitor(org.activebpel.rt.bpel.IAeExpressionLanguageFactory)
    */
   public IAeDefVisitor createCopyOperationStrategyVisitor(IAeExpressionLanguageFactory aExpressionLanguageFactory)
   {
      return new AeCopyOperationStrategyVisitor(BPEL4WS_STRATEGY_MATCHER, aExpressionLanguageFactory);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitorFactory#createMessageDataStrategyVisitor()
    */
   public IAeDefVisitor createMessageDataStrategyVisitor()
   {
      return new AeMessageDataStrategyVisitor(BPEL4WS_MESSAGE_DATA_STRATEGY_MATCHER);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitorFactory#createMessagePartsMapVisitor(org.activebpel.rt.bpel.IAeContextWSDLProvider)
    */
   public IAeDefMessagePartsMapVisitor createMessagePartsMapVisitor(IAeContextWSDLProvider aWSDLProvider)
   {
      return new AeBPWSDefMessagePartsMapVisitor(aWSDLProvider);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitorFactory#createMessageExchangeVisitor()
    */
   public IAeDefVisitor createMessageExchangeVisitor()
   {
      // TODO (MF) rename this to a base class and create a BPWS version
      return new AeMessageExchangeVisitor();
   }
}
 
