//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/assign/to/AeVariableElementDataWrapper.java,v 1.5 2006/12/14 22:59:3
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

import org.activebpel.rt.bpel.IAeVariable;
import org.activebpel.rt.bpel.impl.AeBpelException;
import org.activebpel.rt.bpel.impl.AeUninitializedVariableException;
import org.activebpel.rt.bpel.impl.activity.assign.AeMismatchedAssignmentException;
import org.exolab.castor.xml.schema.XMLType;
import org.w3c.dom.Element;

/**
 * The target for assigning a value to a variable that's an element 
 */
public class AeVariableElementDataWrapper extends AeVariableBaseDataWrapper
{
   /**
    * Ctor accepts the variable
    * 
    * @param aVariable
    */
   public AeVariableElementDataWrapper(IAeVariable aVariable)
   {
      super(aVariable);
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.activity.assign.IAeVariableDataWrapper#getXMLType()
    */
   public XMLType getXMLType() throws AeBpelException
   {
      // This is only used for complex and simple types
      return null;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.assign.to.AeVariableBaseDataWrapper#getValue()
    */
   public Object getValue() throws AeUninitializedVariableException
   {
      return getVariable().getElementData();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.assign.IAeVariableDataWrapper#setValue(java.lang.Object)
    */
   public void setValue(Object aValue) throws AeMismatchedAssignmentException
   {
      if (aValue instanceof Element)
      {
         getVariable().setElementData((Element) cloneValue(aValue));
      }
      else
      {
         // If we get here then there was likely something wrong with our strategy table since we should
         // only be here to copy string/element to an element
         throw new AeMismatchedAssignmentException(getNamespace());
      }
   }
}
 
