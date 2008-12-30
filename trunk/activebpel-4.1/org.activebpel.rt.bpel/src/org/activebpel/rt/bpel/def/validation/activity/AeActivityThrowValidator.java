//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/activity/AeActivityThrowValidator.java,v 1.6 2006/10/30 22:47:2
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
package org.activebpel.rt.bpel.def.validation.activity; 

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.IAeFaultTypeInfo;
import org.activebpel.rt.bpel.def.activity.AeActivityThrowDef;
import org.activebpel.rt.bpel.def.validation.AeBaseValidator;
import org.activebpel.rt.bpel.def.validation.AeVariableValidator;
import org.activebpel.rt.bpel.def.validation.activity.scope.AeFaultHandlersValidator;

/**
 * model provides validation for the throw activity
 */
public abstract class AeActivityThrowValidator extends AeActivityValidator
{
   /** optional variable that is thrown with fault name */
   private AeVariableValidator mVariable;

   /**
    * ctor
    * @param aDef
    */
   public AeActivityThrowValidator(AeActivityThrowDef aDef)
   {
      super(aDef);
   }
   
   /**
    * Getter for the def
    */
   protected AeActivityThrowDef getDef()
   {
      return (AeActivityThrowDef) getDefinition();
   }
   
   /**
    * Getter for the variable
    */
   protected AeVariableValidator getVariable()
   {
      return mVariable;
   }

   /**
    * Validates:
    * 1. empty fault name not allowed
    * 2. variable exists if provided
    * 3. emits warning if fault is not handled in process
    * @see org.activebpel.rt.bpel.def.validation.activity.AeActivityValidator#validate()
    */
   public void validate()
   {
      super.validate();
      
      if (  getDef().getFaultName() == null ||
            getDef().getFaultName().equals(EMPTY_QNAME))
      {
         getReporter().addError( ERROR_FIELD_MISSING,
                                 new String[] { AeActivityThrowDef.TAG_FAULT_NAME },
                                 getDef() );
      }
      else
      {
         if (!isUndefined(getDef().getFaultVariable()))
         {
            mVariable = getVariableValidator( getDef().getFaultVariable(), AeActivityThrowDef.TAG_FAULT_VARIABLE, true );
         }
         
         if (getVariable() != null)
            validateVariable();
         
         if ( !isFaultHandledInProcess( ))
         {
            // Fault Not handled
            //
            getReporter().addWarning( WARN_FAULT_NAME_NOT_CAUGHT,
                                      new String[] { getNSPrefix(getDef().getFaultName().getNamespaceURI()), getDef().getFaultName().getLocalPart() },
                                      getDef() );
         }
      }
   }
   
   /**
    * Validates that the variable can be thrown
    */
   protected abstract void validateVariable();
   
   /**
    * Walks the parent scopes looking for a <catchAll> or a <catch> that matches our <throw>
    */
   protected boolean isFaultHandledInProcess()
   {
      AeBaseValidator current = this;
      AeFaultHandlersValidator faultHandlersModel = null;
      boolean catchFound = false;
      AeFaultTypeInfo faultTypeInfo = new AeFaultTypeInfo(getDef().getFaultName(), getVariable());
      while (!catchFound && (faultHandlersModel = current.getScopedFaultHandlersValidator()) != null)
      {
         catchFound = faultHandlersModel.isFaultHandled(faultTypeInfo);
         current = faultHandlersModel;
      }
      return catchFound;
   }
   
   /**
    * Provides info on the fault being thrown
    */
   private static class AeFaultTypeInfo implements IAeFaultTypeInfo
   {
      /** name of the fault */
      private QName mFaultName;
      /** variable being thrown */
      private AeVariableValidator mVariableValidator;
      
      /**
       * Ctor accepts fault name and variable
       * @param aFaultName
       * @param aVariableValidator
       */
      public AeFaultTypeInfo(QName aFaultName, AeVariableValidator aVariableValidator)
      {
         mFaultName = aFaultName;
         mVariableValidator = aVariableValidator;
      }
      
      /**
       * @see org.activebpel.rt.bpel.IAeFaultTypeInfo#getElementType()
       */
      public QName getElementType()
      {
         if (getVariableValidator() != null)
            return getVariableValidator().getDef().getElement();
         return null;
      }

      /**
       * @see org.activebpel.rt.bpel.IAeFaultTypeInfo#getFaultName()
       */
      public QName getFaultName()
      {
         return mFaultName;
      }

      /**
       * @see org.activebpel.rt.bpel.IAeFaultTypeInfo#getMessageType()
       */
      public QName getMessageType()
      {
         if (getVariableValidator() != null)
            return getVariableValidator().getDef().getMessageType();
         return null;
      }

      /**
       * @see org.activebpel.rt.bpel.IAeFaultTypeInfo#getSinglePartElementType()
       */
      public QName getSinglePartElementType()
      {
         if (hasMessageData())
         {
            return getVariableValidator().getDef().getMessageParts().getSingleElementPart();
         }
         return null;
      }

      /**
       * @see org.activebpel.rt.bpel.IAeFaultTypeInfo#hasElementData()
       */
      public boolean hasElementData()
      {
         return getElementType() != null;
      }

      /**
       * @see org.activebpel.rt.bpel.IAeFaultTypeInfo#hasMessageData()
       */
      public boolean hasMessageData()
      {
         return getMessageType() != null;
      }
      
      /**
       * 
       * Return ture  if there is any data in this fault. 
       * @see org.activebpel.rt.bpel.IAeFaultTypeInfo#hasData()
       */
      public boolean hasData()
      {
         return hasElementData() || hasMessageData();
      }
      
      /**
       * Getter for the variable validator
       */
      protected AeVariableValidator getVariableValidator()
      {
         return mVariableValidator;
      }
   }
}
 
