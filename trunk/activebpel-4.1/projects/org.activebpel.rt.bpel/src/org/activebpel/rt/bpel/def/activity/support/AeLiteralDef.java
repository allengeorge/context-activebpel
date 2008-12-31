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

import java.util.ArrayList;
import java.util.List;

import org.activebpel.rt.bpel.def.AeBaseDef;
import org.activebpel.rt.bpel.def.visitors.IAeDefVisitor;
import org.activebpel.rt.util.AeXmlUtil;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Models the new literal bpel construct wrapper introduced in WS-BPEL 2.0.
 */
public class AeLiteralDef extends AeBaseDef
{
   /** The list of child nodes in the literal. */
   private List mChildNodes = new ArrayList();

   /**
    * Default c'tor.
    */
   public AeLiteralDef()
   {
      super();
   }

   /**
    * The literal can only have a single child node but we're allowing for multiple here in order to preserve any
    * extra child nodes that we may have read in. We'll produce an error message for multiple children during validation.
    * @return Returns the childNodes.
    */
   public List getChildNodes()
   {
      return mChildNodes;
   }

   /**
    * Adds a child node to the list of child nodes.
    * 
    * @param aNode
    */
   public void addChildNode(Node aNode)
   {
      if (aNode instanceof Element)
      {
         mChildNodes.add(AeXmlUtil.cloneElement((Element) aNode));
      }
      else
      {
         mChildNodes.add(aNode.cloneNode(true));
      }
   }

   /**
    * @see org.activebpel.rt.bpel.def.AeBaseDef#accept(org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void accept(IAeDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }
}
