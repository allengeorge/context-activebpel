//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/activity/support/AeForEachCompletionConditionDef.java,v 1.5 2006/09/13 21:18:3
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

import org.activebpel.rt.bpel.def.AeBaseDef;
import org.activebpel.rt.bpel.def.IAeExpressionDef;
import org.activebpel.rt.bpel.def.util.AeDefUtil;
import org.activebpel.rt.bpel.def.visitors.IAeDefVisitor;

/**
 * Models the optional completion def that resides under a serial or
 * parallel forEach. This def contains pass throughs to the underlying
 * branches def and mainly exists as a container for extension elements.
 */
public class AeForEachCompletionConditionDef extends AeBaseDef implements IAeExpressionDef
{
   /** delegate branches */
   private AeForEachBranchesDef mBranches;
   
   /**
    * @see org.activebpel.rt.bpel.def.AeBaseDef#accept(org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void accept(IAeDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }
   
   /**
    * @return Returns the branches.
    */
   public AeForEachBranchesDef getBranches()
   {
      return mBranches;
   }

   /**
    * @param aBranches The branches to set.
    */
   public void setBranches(AeForEachBranchesDef aBranches)
   {
      mBranches = aBranches;
   }
   
   /**
    * Returns true if there is a branches child.
    */
   protected boolean hasBranches()
   {
      return mBranches != null;
   }

   /**
    * Pass through to the branches def if it exists or false
    */
   public boolean isCountCompletedBranchesOnly()
   {
      if (hasBranches())
         return getBranches().isCountCompletedBranchesOnly();
      return false;
   }
   
   /**
    * Pass through to the branches def if it exists or empty string
    */
   public String getExpression()
   {
      if (hasBranches())
         return getBranches().getExpression();
      return ""; //$NON-NLS-1$
   }
   
   /**
    * Pass through to the branches def if it exists or empty string
    */
   public String getExpressionLanguage()
   {
      if (hasBranches())
         return getBranches().getExpressionLanguage();
      return ""; //$NON-NLS-1$
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.IAeExpressionDef#getBpelNamespace()
    */
   public String getBpelNamespace()
   {
      return AeDefUtil.getProcessDef(this).getNamespace();
   }

   /**
    * @see org.activebpel.rt.bpel.def.IAeExpressionDef#setExpression(java.lang.String)
    */
   public void setExpression(String aExpression)
   {
      if (hasBranches())
      {
         getBranches().setExpression(aExpression);
      }
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.IAeExpressionDef#setExpressionLanguage(java.lang.String)
    */
   public void setExpressionLanguage(String aLanguageURI)
   {
      if (hasBranches())
      {
         getBranches().setExpressionLanguage(aLanguageURI);
      }
   }
}
 
