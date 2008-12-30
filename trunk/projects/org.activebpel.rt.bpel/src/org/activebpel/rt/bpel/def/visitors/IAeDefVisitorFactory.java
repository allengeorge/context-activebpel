//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/visitors/IAeDefVisitorFactory.java,v 1.7 2006/09/22 19:52:3
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
import org.activebpel.rt.bpel.def.validation.IAeValidationContext;
import org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal;
import org.activebpel.rt.bpel.impl.IAeBusinessProcessInternal;
import org.activebpel.rt.bpel.impl.IAeDynamicScopeParent;
import org.activebpel.rt.bpel.impl.IAeProcessPlan;

/**
 * Factory for the def visitors that have different implementations depending 
 * on the version of BPEL we're dealing with
 */
public interface IAeDefVisitorFactory
{
   /**
    * Creates the def path visitor. 
    */
   public IAeDefPathVisitor createDefPathVisitor();
   
   /**
    * Creates a visitor to use for getting the value to use for a def's segment of the location path
    */
   public IAeDefPathSegmentVisitor createDefPathSegmentVisitor();
   
   /**
    * Creates a def to impl visitor for creating a new process
    * @param aPid
    * @param aEngine
    * @param aPlan
    */
   public IAeDefToImplVisitor createImplVisitor(long aPid, IAeBusinessProcessEngineInternal aEngine, IAeProcessPlan aPlan);
   
   /**
    * Creates a def to impl visitor for creating new impls for a running process. This is used for the dynamic impls created
    * for a parallel forEach, onEvent, and onAlarm in WS-BPEL 2.0
    * @param aProcess
    * @param aParent
    */
   public IAeDefToImplVisitor createImplVisitor(IAeBusinessProcessInternal aProcess, IAeDynamicScopeParent aParent);
   
   /**
    * Creates a visitor that handles the creation of def objects to model implicit variables created by some bpel objects.
    * One example is the serial and parallel forEach which manifest their counterName attribute as an unsignedInt variable
    * within their child scope. 
    */
   public IAeDefVisitor createImplicitVariableVisitor();
   
   /**
    * Creates a def visitor that searches for property aliases usages and caches the alias on the def for fast access at runtime. 
    * @param aProvider
    * @param aExpressionLanguageFactory
    */
   public IAeDefVisitor createPropertyAliasInlineVisitor(IAeContextWSDLProvider aProvider, IAeExpressionLanguageFactory aExpressionLanguageFactory);
   
   /**
    * Creates a def visitor that identifies all of the resources (variables/partnerLinks) that an activity needs to lock with an exclusive or shared
    * lock based on whether the activity is nested within an isolated scope and the type of operation performed by the activity. 
    * @param aExpression
    */
   public IAeDefVisitor createResourceLockingVisitor(IAeExpressionLanguageFactory aExpression);
   
   /**
    * Creates a def visitor that validates the def objects against the rules for the appropriate BPEL version.
    * @param aContext
    */
   public IAeDefVisitor createValidationVisitor(IAeValidationContext aContext);

   /**
    * Creates a def visitor that adds the strategy hint to the copy operation
    * defs for &lt;from&gt; and &lt;to&gt; assigns.
    * <p>
    * This visitor should run after any implicit variables visitor since it
    * depends on the implicit variables having been created in order to validate
    * the copy operations.
    * 
    * @param aExpressionLanguageFactory
    */
   public IAeDefVisitor createCopyOperationStrategyVisitor(IAeExpressionLanguageFactory aExpressionLanguageFactory);
   
   /**
    * Creates a def visitor that adds the strategy hint to the wsio activities
    * <p>
    * This visitor should run after any implicit variables visitor since it
    * depends on the implicit variables having been created in order to determine
    * the type of the variable (messageType or element), if used.
    */
   public IAeDefVisitor createMessageDataStrategyVisitor();
   
   /**
    * Creates a def visitor that inlines all of the message parts information for 
    * the wsio activities.
    * @param aWSDLProvider - used to lookup the WSDL messages
    */
   public IAeDefMessagePartsMapVisitor createMessagePartsMapVisitor(IAeContextWSDLProvider aWSDLProvider);
   
   /**
    * Creates a def visitor that records the message exchange value for all of the create instances plus 
    * ensures that each of the "root" scopes provide an implicit message exchange value
    */
   public IAeDefVisitor createMessageExchangeVisitor();
}
 
