// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/xpath/ast/AeAbstractXPathAxisNode.java,v 1.2 2006/09/07 15:06:2
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
 * This class extends the basic xpath node and acts as a base class for any node that contains
 * an axis.
 */
public abstract class AeAbstractXPathAxisNode extends AeAbstractXPathNode
{
   /** The axis of this node (parent, ancestor, child, etc...). */
   private int mAxis;
   
   /**
    * Constructor.
    * 
    * @param aType
    * @param aAxis
    */
   public AeAbstractXPathAxisNode(String aType, int aAxis)
   {
      super(aType);
      setAxis(aAxis);
   }

   /**
    * Gets the axis.
    */
   public int getAxis()
   {
      return mAxis;
   }

   /**
    * @param aAxis The axis to set.
    */
   protected void setAxis(int aAxis)
   {
      mAxis = aAxis;
   }
}
