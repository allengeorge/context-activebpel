//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/assign/to/AeToPropertyType.java,v 1.6 2006/12/14 22:59:3
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
package org.activebpel.rt.bpel.impl.activity.assign.to; 

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.def.activity.support.AeToDef;
import org.activebpel.rt.bpel.impl.activity.assign.IAeVariableDataWrapper;
import org.activebpel.rt.wsdl.def.IAePropertyAlias;

/**
 * Gets the target for the copy using a propertyAlias 
 */
public class AeToPropertyType extends AeToPropertyBase
{
   /**
    * Ctor accepts def and context
    *  
    * @param aToDef
    */
   public AeToPropertyType(AeToDef aToDef)
   {
      super(aToDef);
   }
   
   /**
    * Ctor accepts variable and property
    * @param aVariable
    * @param aProperty
    */
   public AeToPropertyType(String aVariable, QName aProperty)
   {
      super(aVariable, aProperty);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.assign.IAePropertyAliasCopyOperation#getPropertyAlias()
    */
   public IAePropertyAlias getPropertyAlias() throws AeBusinessProcessException
   {
      return getCopyOperation().getContext().getPropertyAlias(IAePropertyAlias.TYPE, getVariable().getType(), getProperty());
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.assign.IAePropertyAliasCopyOperation#getDataForQueryContext(org.activebpel.rt.wsdl.def.IAePropertyAlias)
    */
   public Object getDataForQueryContext(IAePropertyAlias aPropAlias) throws AeBusinessProcessException
   {
      return getVariable().getTypeData();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.assign.to.AeToPropertyBase#getVariableDataWrapper()
    */
   protected IAeVariableDataWrapper getVariableDataWrapper() throws AeBusinessProcessException
   {
      return new AeVariableComplexTypeDataWrapper(getVariable());
   }
}
 
