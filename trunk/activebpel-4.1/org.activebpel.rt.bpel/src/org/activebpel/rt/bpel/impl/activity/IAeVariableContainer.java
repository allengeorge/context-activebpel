//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/IAeVariableContainer.java,v 1.4 2006/09/22 19:52:3
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
package org.activebpel.rt.bpel.impl.activity; 

import java.util.Iterator;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeVariable;
import org.activebpel.rt.bpel.impl.AeBpelException;
import org.activebpel.rt.bpel.impl.IAeBpelObject;

/**
 * Provides method for adding a variable to an impl. Added to support special variables on non-scope
 * activities like the catch that defines/declares a variable. 
 */
public interface IAeVariableContainer
{
   /**
    * Gets the variable by its name
    * @param aVariableName
    */
   public IAeVariable findVariable(String aVariableName);
   
   /**
    * Adds the variable
    * @param aVariable
    */
   public void addVariable(IAeVariable aVariable);
   
   /**
    * Gets an iterator over the IAeVariables within this container
    */
   public Iterator iterator();
   
   /**
    * Initializes the variables in the container
    * @throws AeBusinessProcessException
    */
   public void initialize() throws AeBpelException;
   
   /**
    * Gets the parent of the variables container
    */
   public IAeBpelObject getParent();
}
 
