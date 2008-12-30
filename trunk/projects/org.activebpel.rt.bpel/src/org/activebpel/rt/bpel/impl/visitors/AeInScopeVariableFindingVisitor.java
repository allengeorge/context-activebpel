// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/visitors/AeInScopeVariableFindingVisitor.java,v 1.2 2007/06/15 14:18:4
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
package org.activebpel.rt.bpel.impl.visitors;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeVariable;
import org.activebpel.rt.bpel.impl.AeBusinessProcess;
import org.activebpel.rt.bpel.impl.activity.AeActivityScopeImpl;
import org.activebpel.rt.bpel.impl.activity.IAeVariableContainer;

/**
 * A visitor that finds all variables in scope.
 */
public class AeInScopeVariableFindingVisitor extends AeImplReverseTraversingVisitor
{
   /** A map of variable name -> IAeVariable. */
   private Map mInScopeVariables;
   /** A set of variables that should be excluded from the search. */
   private Set mExcludedVariables;

   /**
    * C'tor.
    */
   public AeInScopeVariableFindingVisitor()
   {
      setInScopeVariables(new HashMap());
      setExcludedVariables(new HashSet());
   }

   /**
    * C'tor.
    */
   public AeInScopeVariableFindingVisitor(Set aExcludedVariables)
   {
      this();
      if (aExcludedVariables != null)
      {
          setExcludedVariables(aExcludedVariables);
      }
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.visitors.AeImplTraversingVisitor#visit(org.activebpel.rt.bpel.impl.activity.AeActivityScopeImpl)
    */
   public void visit(AeActivityScopeImpl aImpl) throws AeBusinessProcessException
   {
      findInScopeVariables(aImpl);
      
      super.visit(aImpl);
   }
   
  /**
   * @see org.activebpel.rt.bpel.impl.visitors.AeImplTraversingVisitor#visit(org.activebpel.rt.bpel.impl.AeBusinessProcess)
   */
   public void visit(AeBusinessProcess aImpl) throws AeBusinessProcessException
   {
      findInScopeVariables(aImpl);
      
      super.visit(aImpl);
   }

   /**
    * @param aImpl
    */
   private void findInScopeVariables(AeActivityScopeImpl aImpl)
   {
      
      IAeVariableContainer variableContainer = aImpl.getVariableContainer();
      if (variableContainer != null)
      {
         for (Iterator iter = variableContainer.iterator(); iter.hasNext(); )
         {
            IAeVariable variable = (IAeVariable) iter.next();
            // If it's not already included, and it's not excluded, then add it to
            // the map of variables that we've found.
            if (!getInScopeVariables().containsKey(variable.getName())
                  && !getExcludedVariables().contains(variable.getName()))
            {
               getInScopeVariables().put(variable.getName(), variable);
            }
         }
      }
   }

   /**
    * Gets the collection of variables found.
    */
   public Collection getVariables()
   {
      return getInScopeVariables().values();
   }
   
   /**
    * @return Returns the inScopeVariables.
    */
   protected Map getInScopeVariables()
   {
      return mInScopeVariables;
   }

   /**
    * @param aInScopeVariables the inScopeVariables to set
    */
   protected void setInScopeVariables(Map aInScopeVariables)
   {
      mInScopeVariables = aInScopeVariables;
   }

   /**
    * @return Returns the excludedVariables.
    */
   protected Set getExcludedVariables()
   {
      return mExcludedVariables;
   }

   /**
    * @param aExcludedVariables the excludedVariables to set
    */
   protected void setExcludedVariables(Set aExcludedVariables)
   {
      mExcludedVariables = aExcludedVariables;
   }
}
