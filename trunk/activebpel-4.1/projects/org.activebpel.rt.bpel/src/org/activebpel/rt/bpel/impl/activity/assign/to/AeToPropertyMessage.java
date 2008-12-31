//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/assign/to/AeToPropertyMessage.java,v 1.12 2006/12/14 22:59:3
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
import org.activebpel.rt.bpel.IAeVariable;
import org.activebpel.rt.bpel.def.activity.support.AeToDef;
import org.activebpel.rt.bpel.impl.activity.assign.IAeVariableDataWrapper;
import org.activebpel.rt.wsdl.def.IAePropertyAlias;

/**
 * Gets the target of the copy by using a propertyAlias to 
 * select a node within a message part's complexType or element.
 * 
 * TODO (EPW) This impl should not be used when the part is a simple type.
 */
public class AeToPropertyMessage extends AeToPropertyBase
{
   /** cached to avoid multiple lookups */
   private IAePropertyAlias mPropertyAlias;
   
   /**
    * Ctor accepts def 
    * 
    * @param aToDef
    */
   public AeToPropertyMessage(AeToDef aToDef)
   {
      super(aToDef);
   }
   
   /**
    * Ctor accepts variable and property
    * @param aVariable
    * @param aProperty
    */
   public AeToPropertyMessage(String aVariable, QName aProperty)
   {
      super(aVariable, aProperty);
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.activity.assign.to.AeToPropertyBase#getVariableDataWrapper()
    */
   protected IAeVariableDataWrapper getVariableDataWrapper() throws AeBusinessProcessException
   {
      return new AeMessagePartDataWrapper(getVariable(), getVariable().getDefinition().getPartInfo(getPropertyAlias().getPart()));
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.assign.to.AeToPropertyBase#isEmptyQuery(org.activebpel.rt.wsdl.def.IAePropertyAlias)
    */
   protected boolean isEmptyQuery(IAePropertyAlias aPropAlias)
   {
      // TODO (EPW) the test for "/partName" is misleading here for BPEL 2.0.  At the moment, this is needed in cases where the variable has not yet been initialized.
      return super.isEmptyQuery(aPropAlias) || 
            aPropAlias.getQuery().equals("/" + aPropAlias.getPart()); //$NON-NLS-1$
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.assign.IAePropertyAliasCopyOperation#getDataForQueryContext(org.activebpel.rt.wsdl.def.IAePropertyAlias)
    */
   public Object getDataForQueryContext(IAePropertyAlias aPropAlias) throws AeBusinessProcessException
   {
      return getVariable().getMessageData().getData(aPropAlias.getPart());
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.assign.IAePropertyAliasCopyOperation#getPropertyAlias()
    */
   public IAePropertyAlias getPropertyAlias() throws AeBusinessProcessException
   {
      if (mPropertyAlias == null)
      {
         mPropertyAlias = getCopyOperation().getContext().getPropertyAlias(IAePropertyAlias.MESSAGE_TYPE, getMessageType(), getProperty());
      }
      return mPropertyAlias;
   }
   
   /**
    * Gets the message type so we know what propertyAlias to lookup
    */
   protected QName getMessageType()
   {
      // use getVariable() on the context instead of the one in the base class
      // in order to avoid a StackOverflowException.
      IAeVariable variable = getCopyOperation().getContext().getVariable(getVariableName());
      return variable.getMessageType();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.assign.to.AeToBase#getPartName()
    */
   protected String getPartName()
   {
      try
      {
         return getPropertyAlias().getPart();
      }
      catch (AeBusinessProcessException e)
      {
         // ignore the exception for now. If we can't find a property alias then
         // the exception will be reported elsewhere.
         return null;
      }
   }
}
 
