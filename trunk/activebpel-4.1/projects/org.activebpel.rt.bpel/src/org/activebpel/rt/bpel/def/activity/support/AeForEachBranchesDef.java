//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/activity/support/AeForEachBranchesDef.java,v 1.2 2006/06/26 16:50:3
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
package org.activebpel.rt.bpel.def.activity.support; 

import org.activebpel.rt.bpel.def.visitors.IAeDefVisitor;

/**
 * Branches def resides under the optional completionCondition for the
 * serial and parallel forEach. It contains an expression that determines the 
 * number of iterations of the loop required in order for the loop to complete
 * without an error.
 */
public class AeForEachBranchesDef extends AeExpressionBaseDef
{
   /** true limits the completion count to scopes that completed normally */
   private boolean mCountCompletedBranchesOnly = false;
   
   /**
    * Default c'tor.
    */
   public AeForEachBranchesDef()
   {
      super();
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.AeBaseDef#accept(org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void accept(IAeDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }
   
   /**
    * @return Returns the countCompletedBranchesOnly.
    */
   public boolean isCountCompletedBranchesOnly()
   {
      return mCountCompletedBranchesOnly;
   }
   
   /**
    * @param aCountCompletedBranchesOnly The countCompletedBranchesOnly to set.
    */
   public void setCountCompletedBranchesOnly(boolean aCountCompletedBranchesOnly)
   {
      mCountCompletedBranchesOnly = aCountCompletedBranchesOnly;
   }
}
 
