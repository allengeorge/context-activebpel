//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/assign/from/AeFromPropertyBase.java,v 1.3 2006/07/14 15:46:5
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
import org.activebpel.rt.bpel.impl.activity.assign.AePropertyAliasBasedSelector;
import org.activebpel.rt.bpel.impl.activity.assign.IAePropertyAliasCopyOperation;
import org.activebpel.rt.wsdl.def.IAePropertyAlias;

/**
 * Base class for impls that read variable data using a property alias 
 */
public abstract class AeFromPropertyBase extends AeFromBase implements IAePropertyAliasCopyOperation
{
   /** name of the property */
   private QName mProperty;

   /**
    * Ctor takes def
    * 
    * @param aFromDef
    */
   public AeFromPropertyBase(AeFromDef aFromDef)
   {
      super(aFromDef);
      setProperty(aFromDef.getProperty());
   }
   
   /**
    * Ctor accepts variable name and property
    * @param aVariableName
    * @param aProperty
    */
   protected AeFromPropertyBase(String aVariableName, QName aProperty)
   {
      setVariableName(aVariableName);
      setProperty(aProperty);
   }

   /**
    * Template method that allows subclasses to override getting of property alias and data for query
    * @see org.activebpel.rt.bpel.impl.activity.assign.IAeFrom#getFromData()
    */
   public Object getFromData() throws AeBusinessProcessException
   {
      IAePropertyAlias propAlias = getPropertyAlias();
      return AePropertyAliasBasedSelector.selectValue(propAlias, getDataForQueryContext(propAlias), getCopyOperation().getContext());
   }

   /**
    * @return Returns the property.
    */
   public QName getProperty()
   {
      return mProperty;
   }

   /**
    * @param aProperty The property to set.
    */
   public void setProperty(QName aProperty)
   {
      mProperty = aProperty;
   }
}
 
