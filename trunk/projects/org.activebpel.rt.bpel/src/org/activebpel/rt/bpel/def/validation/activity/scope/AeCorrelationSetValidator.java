//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/activity/scope/AeCorrelationSetValidator.java,v 1.2 2006/12/14 22:47:3
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

import java.util.Collection;
import java.util.Iterator;

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.AeWSDLDefHelper;
import org.activebpel.rt.bpel.def.AeCorrelationSetDef;
import org.activebpel.rt.bpel.def.validation.AeBaseValidator;
import org.activebpel.rt.wsdl.def.AeBPELExtendedWSDLDef;

/**
 * moddel provides validation for a correlation set def
 */
public class AeCorrelationSetValidator extends AeBaseValidator
{
   /** used to record that the correlation set is used by a web service interaction */
   private boolean mReferenced = false;

   /**
    * ctor
    * @param aDef
    */
   public AeCorrelationSetValidator(AeCorrelationSetDef aDef)
   {
      super(aDef);
   }
   
   /**
    * Getter for the name
    */
   public String getName()
   {
      return getDef().getName();
   }
   
   /**
    * Getter for the correlationSet def
    */
   public AeCorrelationSetDef getDef()
   {
      return (AeCorrelationSetDef) getDefinition();
   }

   /**
    * Validates:
    * 1. warning if the correlationSet isn't referenced
    * 2. all of the properties in the set are resolved
    * @see org.activebpel.rt.bpel.def.validation.AeBaseValidator#validate()
    */
   public void validate()
   {
      super.validate();
      
      if (!isReferenced())
      {
         getReporter().addWarning( WARNING_CORR_SET_NOT_USED,  new String[] { getDef().getName() }, getDefinition());
      }
      
      validateNCName(true);
      
      // Ensure that the Correlation Set has an assigned properties
      //  list and the properties' QNames can be resolved in WSDL.
      //
      Collection props = getDef().getProperties();
      if ( props != null && !props.isEmpty() )
      {
         for ( Iterator iterProps = props.iterator() ; iterProps.hasNext() ; )
         {
            QName propName = (QName)iterProps.next();
            AeBPELExtendedWSDLDef wsdl = AeWSDLDefHelper.getWSDLDefinitionForProp( getValidationContext().getContextWSDLProvider(), propName );
            if ( wsdl == null )
            {
               addTypeNotFoundError(ERROR_PROP_NOT_FOUND, propName);
            }
         }
      }
      else
      {
         // No properties defined - error.  Added as fix for Defect #341.
         //
         getReporter().addError( ERROR_CORR_SET_PROPS_NOT_FOUND,
                                 new String[] { getDef().getName() },
                                 getDef() );
      }
   }

   /**
    * records the reference to the correlationSet
    */
   public void addReference()
   {
      mReferenced = true;
   }
   
   /**
    * Returns true if the correlationSet is referenced
    */
   public boolean isReferenced()
   {
      return mReferenced;
   }
}
 
