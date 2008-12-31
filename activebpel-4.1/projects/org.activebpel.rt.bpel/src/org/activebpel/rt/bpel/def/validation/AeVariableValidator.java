//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/AeVariableValidator.java,v 1.6 2006/12/14 22:43:1
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
import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.bpel.AeWSDLDefHelper;
import org.activebpel.rt.bpel.def.AeVariableDef;
import org.activebpel.rt.bpel.def.IAeBPELConstants;
import org.activebpel.rt.bpel.def.io.readers.def.IAeFromStrategyNames;
import org.activebpel.rt.bpel.def.validation.extensions.AeExtensionValidator;
import org.activebpel.rt.bpel.def.validation.variable.*;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.wsdl.def.AeBPELExtendedWSDLDef;

import javax.wsdl.Part;
import javax.xml.namespace.QName;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Model for validating variables.
 */
public class AeVariableValidator extends AeBaseValidator
{
   /** reference to the WSDL def that defines the variable's message or type */
   private AeBPELExtendedWSDLDef mWsdlDef;
   /** list of variable usage objects that record a variable's usage by a wsio activity, correlation, or assign */
   private List mVariableUsers = new LinkedList();
   /** flag that indicates a variable is referenced by another model but without requiring any additional validation like the usage list */
   private boolean mReferenced;
   private boolean mAttemptedToResolveWSDL = false;
   
   /**
    * ctor
    * @param aDef
    */
   public AeVariableValidator(AeVariableDef aDef)
   {
      super(aDef);
   }
   
   /**
    * Returns true if the passed variable is the same type as this variable.
    * @param aModel
    */
   public boolean isSameType(AeVariableValidator aModel)
   {
      return AeUtil.compareObjects(getDef().getMessageType(), aModel.getDef().getMessageType()) &&
             AeUtil.compareObjects(getDef().getElement(), aModel.getDef().getElement()) &&
             AeUtil.compareObjects(getDef().getType(), aModel.getDef().getType());
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.validation.AeBaseValidator#validate()
    */
   public void validate()
   {
      super.validate();
      
      if (!isReferenced() && !getDef().isImplicit())
      {
         getReporter().addWarning( WARNING_VARIABLE_NOT_USED,  new String[] { getDef().getName() }, getDef() );
      }

      if (getDef().isStateful())
      {
         AeExtensionValidator extValidator = findExtensionValidator(IAeBPELConstants.SBPEL_EXTENSION_NAMESPACE_URI_STATEFUL_PROCESS);
         processExtensionValidator(extValidator, true, IAeBPELConstants.SBPEL_EXTENSION_NAMESPACE_URI_STATEFUL_PROCESS);
      }
      
      validateInitialization();
      validateVariableName(getDef().getName(), getDefinition());
      validateVariableDefinition();
      validateVariableUsage();
   }
   
   /**
    * Validates that the variable initialization is correct. Only applies to message style variables.
    */
   protected void validateInitialization()
   {
      if (getDef().getFromDef() != null && getDef().isMessageType())
      {
         // report an error if they're trying to init the variable from something other than another message variable
         if (getDef().getFromDef().getStrategyKey() != null)
         {
            if (!IAeFromStrategyNames.FROM_VARIABLE_MESSAGE.equals(getDef().getFromDef().getStrategyKey().getStrategyName()))
            {
               getReporter().addError(WARNING_INVALID_MESSAGE_VARIABLE_INIT, new String[] {getDef().getName()}, getDef());
            }
            else
            {
               // it's being init'd from another message variable, make sure the type is compatible
               String variableName = getDef().getFromDef().getVariable();
               AeVariableValidator otherValidator = getVariableValidator(variableName, null, false);
               if (!AeUtil.compareObjects(getDef().getMessageType(), otherValidator.getDef().getMessageType()))
               {
                  getReporter().addError(WARNING_INVALID_MESSAGE_VARIABLE_INIT, new String[] {getDef().getName()}, getDef());
               }
            }
         }
      }
   }

   /**
    * validates we can find a definition for the given variable from the WSDL provider.
    * This applies to complex types, elements, and WSDL message variables. 
    */
   protected void validateVariableDefinition()
   {
      // verify the element exists
      if(getDef().isElement())
      {
         validateElement();
      }
      // else verify the type exists
      else if(getDef().isType())
      {
         validateType();
      }
      // else message so verify the message exists
      else
      {
         validateMessageType();
      }
   }

   /**
    * Validates the Element.
    */
   protected void validateElement()
   {
      if ( getWsdlDef() == null )
      {
         addTypeNotFoundError(ERROR_ELEMENT_SPEC_NOT_FOUND, getDef().getElement());
      }
      if (getDef().isType() || getDef().isMessageType())
      {
         getReporter().addError(AeMessages.getString("AeVariableModel.InvalidVariableTypeError"), //$NON-NLS-1$
               null, getDef());
      }
   }

   /**
    * Validates the type.
    */
   protected void validateType()
   {
      // don't need to locate def for simple types
      // and potentially other well known types
      QName schemaQName = getDef().getType();
      if( !IAeBPELConstants.DEFAULT_SCHEMA_NS.equals( schemaQName.getNamespaceURI() ) )
      {
         if ( getWsdlDef() == null )
         {
            addTypeNotFoundError(ERROR_TYPE_SPEC_NOT_FOUND, getDef().getType());
         }
      }
      if (getDef().isElement() || getDef().isMessageType())
      {
         getReporter().addError(AeMessages.getString("AeVariableModel.InvalidVariableTypeError"), //$NON-NLS-1$
               null, getDef());
      }
   }

   /**
    * Validates the message type.
    */
   protected void validateMessageType()
   {
      // Ensure that the Variable has an assigned MessageType
      //  and that it can be resolved.
      //
      QName msgType = getDef().getMessageType();
      if (msgType == null || EMPTY_QNAME.equals(msgType))
      {
         getReporter().addError(ERROR_VAR_HAS_NO_TYPE, new String[] { getDef().getName() }, getDef());
      }
      else
      {
         if ( getWsdlDef() == null )
         {
            addTypeNotFoundError(ERROR_MSG_SPEC_NOT_FOUND, getDef().getMessageType());
         }
         else
         {
            for (Iterator iter=getWsdlDef().getMessageParts(getDef().getMessageType()); iter.hasNext();)
            {
               Part part = (Part)iter.next();
               try
               {
                  getDef().addPartTypeInfo(part, getWsdlDef());
               }
               catch (AeException e)
               {
                  getReporter().addError( ERROR_DISCOVERING_PART_TYPE_SPECS,
                                          new String[] { part.getName(),
                                                         getNSPrefix( getDef().getMessageType().getNamespaceURI()),
                                                         getDef().getMessageType().getLocalPart(),
                                                         e.getMessage()},
                                          getDef() );
               }
            }
         }
      }
      if (getDef().isType() || getDef().isElement())
      {
         getReporter().addError(AeMessages.getString("AeVariableModel.InvalidVariableTypeError"), //$NON-NLS-1$
               null, getDef());
      }
   }
   
   /**
    * Gets the QName of the variable's type, returning either the message, element, or type type.
    */
   public QName getType()
   {
      if (getDef().isMessageType())
         return getDef().getMessageType();
      else if (getDef().isElement())
         return getDef().getElement();
      else
         return getDef().getType();
   }

   /**
    * pass through to the def to get the name
    */
   public String getName()
   {
      return getDef().getName();
   }
   
   /**
    * Getter for the def
    */
   public AeVariableDef getDef()
   {
      return (AeVariableDef) getDefinition();
   }
   
   /**
    * Records the models usage of the variable
    * @param aModel
    * @param aPart
    * @param aQuery
    * @param aProperty
    */
   public void addUsage(IAeValidator aModel, String aPart, String aQuery, QName aProperty)
   {
      // message + part
      if (AeUtil.notNullOrEmpty(aPart) && AeUtil.isNullOrEmpty(aQuery))
      {
         mVariableUsers.add(new AeMessagePartUsage(this, aModel, aPart));
      }
      // message + part + query
      else if (AeUtil.notNullOrEmpty(aPart) && AeUtil.notNullOrEmpty(aQuery))
      {
         mVariableUsers.add(new AeMessagePartQueryUsage(this, aModel, aPart, aQuery));
      }
      // var (elem, type, message) + property
      else if (aProperty != null)
      {
         if (getDef().isElement())
         {
            mVariableUsers.add(new AeElementPropertyUsage(this, aModel, aProperty));
         }
         else if (getDef().isType())
         {
            mVariableUsers.add(new AeTypePropertyUsage(this, aModel, aProperty));
         }
         else
         {
            mVariableUsers.add(new AeMessagePropertyUsage(this, aModel, aProperty));
         }
      }
      // element + query or complex type + query
      else if (AeUtil.notNullOrEmpty(aQuery))
      {
         if (getDef().isType())
         {
            mVariableUsers.add(new AeComplexTypeQueryUsage(this, aModel, aQuery));
         }
         else
         {
            mVariableUsers.add(new AeElementQueryUsage(this, aModel, aQuery));
         }
      }
   }
   
   /**
    * Returns true if this variable is referenced
    */
   public boolean isReferenced()
   {
      return mReferenced || mVariableUsers.size() > 0;
   }
   
   /**
    * sets the reference flag
    */
   public void addReference()
   {
      mReferenced = true;
   }
   
   /**
    * Getter for the WSDL def
    */
   public AeBPELExtendedWSDLDef getWsdlDef()
   {
      if(mWsdlDef == null && !mAttemptedToResolveWSDL)
      {         
         mAttemptedToResolveWSDL = true;
         
         // set wsdlDef for element
         if(getDef().isElement())
         {
            setWsdlDef(AeWSDLDefHelper.getWSDLDefinitionForElement( getValidationContext().getContextWSDLProvider(), getDef().getElement()));
         }
         // set wsdlDef for type
         else if(getDef().isType())
         {
            setWsdlDef(AeWSDLDefHelper.getWSDLDefinitionForType( getValidationContext().getContextWSDLProvider(), getDef().getType() ));
         }
         // set wsdlDef for message type
         else
         {
            setWsdlDef(AeWSDLDefHelper.getWSDLDefinitionForMsg( getValidationContext().getContextWSDLProvider(), getDef().getMessageType() ));
         }                          
      }
      
      return mWsdlDef;
   }
   
   /**
    * Setter for the WSDL def
    * @param aDef
    */
   protected void setWsdlDef(AeBPELExtendedWSDLDef aDef)
   {
      mWsdlDef = aDef;
   }
   
   
   
   /**
    * Validates all of the recorded users of the variable
    */
   protected void validateVariableUsage()
   {
      for(Iterator it=mVariableUsers.iterator(); it.hasNext();)
      {
         AeVariableUsage usage = (AeVariableUsage) it.next();
         usage.validate();
      }
   }
}
 
