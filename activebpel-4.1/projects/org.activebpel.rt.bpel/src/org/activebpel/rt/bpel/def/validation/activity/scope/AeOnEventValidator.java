//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/activity/scope/AeOnEventValidator.java,v 1.7 2007/01/27 14:40:0
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
package org.activebpel.rt.bpel.def.validation.activity.scope; 

import org.activebpel.rt.bpel.def.activity.support.AeOnEventDef;
import org.activebpel.rt.bpel.def.validation.AeVariableValidator;
import org.activebpel.rt.bpel.def.validation.activity.AeActivityScopeValidator;
import org.activebpel.rt.bpel.def.validation.activity.AeOnMessageBaseValidator;
import org.activebpel.rt.util.AeUtil;

/**
 * model provides validation for the onEvent model
 */
public class AeOnEventValidator extends AeOnMessageBaseValidator
{
   /** flag to avoid reporting scope error multiple times */
   private boolean mScopeChecked;
   
   /**
    * ctor
    * @param aDef
    */
   public AeOnEventValidator(AeOnEventDef aDef)
   {
      super(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.activity.AeWSIOActivityValidator#validate()
    */
   public void validate()
   {
      super.validate();
      
      // checks for required scope
      getChildScope();
      
      // verify that the variable, if provided, is implicit
      AeVariableValidator model = getVariable();
      
      if (model != null && !model.getDef().isImplicit())
      {
         getReporter().addError( ERROR_IMPLICIT_VARIABLE_EXPLICITLY_DEFINED,
               new String[] { getDefinition().getLocationPath(), model.getDef().getName(), model.getDef().getLocationPath() },
               getDefinition() );
      }

      // Issue warning if there are no correlations for this event
      if ( !getDef().getCorrelationDefs().hasNext())
      {
         getReporter().addWarning( WARNING_NO_CORR_SET_NO_CREATE,
                                    new String[] { AeOnEventDef.TAG_ON_EVENT },
                                    getDef() );
      }
   }
   
   /**
    * Gets the def
    */
   protected AeOnEventDef getDef()
   {
      return (AeOnEventDef) getDefinition();
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.activity.AeOnMessageBaseValidator#resolveVariable()
    */
   protected AeVariableValidator resolveVariable()
   {
      if (AeUtil.notNullOrEmpty(getDef().getVariable()))
      {
         AeActivityScopeValidator scope = getChildScope();
         if ( scope != null )
            return getChildScope().getVariableValidator(getDef().getVariable());
      }
      return null;
   }
   
   /**
    * Resolves the variable for an enclosed fromPart
    * @param aVariableName
    */
   public AeVariableValidator resolveFromPartVariable(String aVariableName)
   {
      AeActivityScopeValidator scope = getChildScope();
      if ( scope != null )
         return getChildScope().getVariableValidator(aVariableName);
      return null;
   }

   /**
    * Gets the child scope or reports an error if not found
    */
   protected AeActivityScopeValidator getChildScope()
   {
      AeActivityScopeValidator scope = (AeActivityScopeValidator) getChild(AeActivityScopeValidator.class);
      if (scope == null && !mScopeChecked)
      {
         mScopeChecked = true;
         getReporter().addError( ERROR_REQUIRES_SCOPE_CHILD,
               new String[] { getDefinition().getLocationPath() },
               getDefinition() );
      }
      return scope;
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.AeBaseValidator#getCorrelationSetValidator(java.lang.String, boolean)
    */
   protected AeCorrelationSetValidator getCorrelationSetValidator(String aName, boolean aRecordReference)
   {
      AeActivityScopeValidator scope = getChildScope();

      // null scope would have been reported elsewhere
      if (scope != null)
      {
         AeCorrelationSetValidator model = scope.getCorrelationSetModel(aName);
         if (model != null)
         {
            return model;
         }
      }
      return super.getCorrelationSetValidator(aName, aRecordReference);
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.AeBaseValidator#getPartnerLinkValidator(java.lang.String, boolean)
    */
   protected AePartnerLinkValidator getPartnerLinkValidator(String aName, boolean aRecordReference)
   {
      AeActivityScopeValidator scope = getChildScope();

      // null scope would have been reported elsewhere
      if (scope != null)
      {
         AePartnerLinkValidator model = scope.getPartnerLinkValidator(aName);
         if (model != null)
         {
            return model;
         }
      }
      return super.getPartnerLinkValidator(aName, aRecordReference);
   }
}
 
