//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/assign/from/AeFromPropertyType.java,v 1.6 2006/12/14 22:58:5
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
package org.activebpel.rt.bpel.impl.activity.assign.from; 

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.def.activity.support.AeFromDef;
import org.activebpel.rt.wsdl.def.IAePropertyAlias;

/**
 * Handles reading value from a type using a property alias 
 */
public class AeFromPropertyType extends AeFromPropertyBase
{
   /**
    * Ctor takes def
    * 
    * @param aFromDef
    */
   public AeFromPropertyType(AeFromDef aFromDef)
   {
      super(aFromDef);
   }
   
   /**
    * Ctor accepts variable name and property
    * @param aVariableName
    * @param aProperty
    */
   public AeFromPropertyType(String aVariableName, QName aProperty)
   {
      super(aVariableName, aProperty);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.assign.IAePropertyAliasCopyOperation#getDataForQueryContext(org.activebpel.rt.wsdl.def.IAePropertyAlias)
    */
   public Object getDataForQueryContext(IAePropertyAlias aPropAlias) throws AeBusinessProcessException
   {
      return getVariable().getTypeData();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.assign.IAePropertyAliasCopyOperation#getPropertyAlias()
    */
   public IAePropertyAlias getPropertyAlias() throws AeBusinessProcessException
   {
      return getCopyOperation().getContext().getPropertyAlias(IAePropertyAlias.TYPE, getVariable().getDefinition().getType(), getProperty());
   }
}
 
