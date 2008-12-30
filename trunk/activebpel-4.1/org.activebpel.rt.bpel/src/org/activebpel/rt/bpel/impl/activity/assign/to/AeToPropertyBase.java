//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/assign/to/AeToPropertyBase.java,v 1.4 2006/12/14 22:59:3
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
import org.activebpel.rt.bpel.impl.activity.assign.AePropertyAliasBasedSelector;
import org.activebpel.rt.bpel.impl.activity.assign.IAePropertyAliasCopyOperation;
import org.activebpel.rt.bpel.impl.activity.assign.IAeVariableDataWrapper;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.wsdl.def.IAePropertyAlias;

/**
 * Base class for selecting the target for a copy operation 
 */
public abstract class AeToPropertyBase extends AeToBase implements IAePropertyAliasCopyOperation
{
   /** property name */
   private QName mProperty;
   
   /**
    * Ctor accepts the def 
    * 
    * @param aToDef
    */
   public AeToPropertyBase(AeToDef aToDef)
   {
      super(aToDef);
      setProperty(aToDef.getProperty());
   }
   
   /**
    * Ctor accepts variable and property
    * @param aVariableName
    * @param aProperty
    */
   public AeToPropertyBase(String aVariableName, QName aProperty)
   {
      setVariableName(aVariableName);
      setProperty(aProperty);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.assign.IAeTo#getTarget()
    */
   public Object getTarget() throws AeBusinessProcessException
   {
      IAePropertyAlias propAlias = getPropertyAlias();
      
      // Check to see if the query is empty. If it's empty, then don't bother
      // using the selector since there's no query to execute.
      // Furthermore, we want to wrap the underlying value (message part, 
      // element or type) in a data wrapper so we have a legitamate L-Value to 
      // assign to. 
      
      if (isEmptyQuery(propAlias))
      {
         return getVariableDataWrapper();
      }
      return AePropertyAliasBasedSelector.selectValue(propAlias, getDataForQueryContext(propAlias), getCopyOperation().getContext());
   }

   /**
    * Returns true if the query is empty
    * @param propAlias
    */
   protected boolean isEmptyQuery(IAePropertyAlias propAlias)
   {
      return AeUtil.isNullOrEmpty(propAlias.getQuery());
   }
   
   /**
    * Gets the appropriate wrapper for the data type.
    * @throws AeBusinessProcessException
    */
   protected abstract IAeVariableDataWrapper getVariableDataWrapper() throws AeBusinessProcessException;

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
