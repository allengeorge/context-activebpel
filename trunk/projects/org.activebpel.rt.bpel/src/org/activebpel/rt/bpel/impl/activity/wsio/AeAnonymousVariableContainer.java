// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/wsio/AeAnonymousVariableContainer.java,v 1.2 2006/09/22 19:52:3
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
package org.activebpel.rt.bpel.impl.activity.wsio;

import java.util.Collections;
import java.util.Iterator;

import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.bpel.IAeVariable;
import org.activebpel.rt.bpel.impl.AeBpelException;
import org.activebpel.rt.bpel.impl.IAeBpelObject;
import org.activebpel.rt.bpel.impl.activity.IAeVariableContainer;

/**
 * Implements a variable container that holds a single anonymous variable.
 */
public class AeAnonymousVariableContainer implements IAeVariableContainer
{
   /** The anonymous variable. */
   IAeVariable mVariable;

   /**
    * Returns the anonymous variable.
    */
   protected IAeVariable getVariable()
   {
      return mVariable;
   }

   /*===========================================================================
    * IAeVariableContainer methods
    *===========================================================================
    */

   /**
    * @see org.activebpel.rt.bpel.impl.activity.IAeVariableContainer#findVariable(java.lang.String)
    */
   public IAeVariable findVariable(String aVariableName)
   {
      return getVariable();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.IAeVariableContainer#addVariable(org.activebpel.rt.bpel.IAeVariable)
    */
   public void addVariable(IAeVariable aVariable)
   {
      if (getVariable() != null)
      {
         throw new IllegalStateException(AeMessages.getString("AeAnonymousMessageDataVariable.ERROR_AddVariable")); //$NON-NLS-1$
      }

      mVariable = aVariable;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.IAeVariableContainer#iterator()
    */
   public Iterator iterator()
   {
      return Collections.singleton(getVariable()).iterator();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.IAeVariableContainer#initialize()
    */
   public void initialize() throws AeBpelException
   {
      throw new UnsupportedOperationException();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.IAeVariableContainer#getParent()
    */
   public IAeBpelObject getParent()
   {
      throw new UnsupportedOperationException();
   }
}
