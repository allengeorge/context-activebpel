// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/xpath/ast/AeAbstractXPathBooleanNode.java,v 1.1 2006/07/21 16:03:3
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
 * A base class that boolean xpath nodes extend (or, and, union, unary).
 */
public abstract class AeAbstractXPathBooleanNode extends AeAbstractXPathNode
{
   /** The create flag. */
   private boolean mCreate;

   /**
    * Constructor.
    * 
    * @param aType
    */
   public AeAbstractXPathBooleanNode(String aType)
   {
      super(aType);
   }

   /**
    * @return Returns the create.
    */
   public boolean isCreate()
   {
      return mCreate;
   }

   /**
    * @see org.activebpel.rt.bpel.xpath.ast.AeAbstractXPathNode#normalize()
    */
   public AeAbstractXPathNode normalize()
   {
      return normalizeOmitSelf();
   }

   /**
    * @param aCreate The create to set.
    */
   public void setCreate(boolean aCreate)
   {
      mCreate = aCreate;
   }
}
