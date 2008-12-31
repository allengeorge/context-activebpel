//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/assign/to/AeVariableBaseDataWrapper.java,v 1.6 2006/12/14 22:59:4
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
import org.activebpel.rt.bpel.impl.activity.assign.IAeVariableDataWrapper;
import org.activebpel.rt.util.AeXmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Base class for the variable data wrappers. Provides getter and setter for variable being assigned to. 
 */
public abstract class AeVariableBaseDataWrapper implements IAeVariableDataWrapper
{
   /** variable being assigned to */
   private IAeVariable mVariable;
   
   /**
    * Ctor accepts variable
    * 
    * @param aVariable
    */
   public AeVariableBaseDataWrapper(IAeVariable aVariable)
   {
      setVariable(aVariable);
   }

   /**
    * @return Returns the variable.
    */
   public IAeVariable getVariable()
   {
      return mVariable;
   }

   /**
    * @param aVariable The variable to set.
    */
   public void setVariable(IAeVariable aVariable)
   {
      mVariable = aVariable;
   }
   
   /**
    * Gets the value that is being assigned to 
    * 
    * @see org.activebpel.rt.bpel.impl.activity.assign.IAeVariableDataWrapper#getValue()
    */
   public abstract Object getValue() throws AeBpelException;
   
   /**
    * Creates a clone of a DOM Node or no-op if param isn't a Node.
    * @param aValue
    */
   protected Object cloneValue(Object aValue)
   {
      Object value = null;
      if (aValue instanceof Element)
      {
         value = AeXmlUtil.cloneElement((Element) aValue);
      }
      else if (aValue instanceof Document)
      {
         value = AeXmlUtil.cloneElement(((Document) aValue).getDocumentElement());
      }
      else
      {
         value = aValue;
      }
      return value;
   }
   
   /**
    * Getter for the namespace
    */
   protected String getNamespace()
   {
      return getVariable().getParent().getParent().getProcess().getBPELNamespace();
   }
}
 
