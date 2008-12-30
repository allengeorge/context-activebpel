// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/fastdom/AeFastNode.java,v 1.1 2004/09/07 22:08:2
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
package org.activebpel.rt.bpel.impl.fastdom;

/**
 * Abstract base class for nodes in the fast, lightweight DOM.
 */
public abstract class AeFastNode implements IAeVisitable
{
   /** The node's parent. */
   private IAeFastParent mParent;

   /**
    * Default constructor.
    */
   public AeFastNode()
   {
   }

   /**
    * Constructs a new with the specified parent.
    *
    * @param aParent
    */
   public AeFastNode(IAeFastParent aParent)
   {
      setParent(aParent);
   }

   /**
    * Detaches this node from its parent, or does nothing if this node has no
    * parent.
    *
    * @return This node detached.
    */
   public AeFastNode detach()
   {
      if (getParent() != null)
      {
         getParent().removeChild(this);
      }

      return this;
   }

   /**
    * Returns this node's parent.
    */
   public IAeFastParent getParent()
   {
      return mParent;
   }

   /**
    * Sets this node's parent to be the specified node.
    *
    * @param aParent
    */
   public void setParent(IAeFastParent aParent)
   {
      mParent = aParent;
   }
}
