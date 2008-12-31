// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/xpath/ast/AeAbstractXPathOperatorNode.java,v 1.1 2006/07/21 16:03:3
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
package org.activebpel.rt.bpel.xpath.ast;

/**
 * A base class for xpath nodes that have operators (equality, relational, additive, multiplicative).
 */
public abstract class AeAbstractXPathOperatorNode extends AeAbstractXPathNode
{
   /** The operator. */
   private int mOperator;

   /**
    * Default c'tor.
    */
   public AeAbstractXPathOperatorNode(String aType)
   {
      super(aType);
   }

   /**
    * @return Returns the operator.
    */
   public int getOperator()
   {
      return mOperator;
   }

   /**
    * @param aOperator The operator to set.
    */
   public void setOperator(int aOperator)
   {
      mOperator = aOperator;
   }
   
   /**
    * @see org.activebpel.rt.bpel.xpath.ast.AeAbstractXPathNode#normalize()
    */
   public AeAbstractXPathNode normalize()
   {
      // Omit this node if it doesn't have a valid operator.
      if (getOperator() == 0)
      {
         return normalizeOmitSelf();
      }
      else
      {
         return super.normalize();
      }
   }
}
