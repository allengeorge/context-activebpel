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

import javax.xml.namespace.QName;

/**
 * A node type that identifies as a variable reference.
 */
public class AeXPathVariableNode extends AeAbstractXPathQualifiedNode
{
   /**
    * Simple constructor.
    *
    * @param aPrefix
    * @param aNamespace
    * @param aVariableName
    */
   public AeXPathVariableNode(String aPrefix, String aNamespace, String aVariableName)
   {
      super(AeAbstractXPathNode.NODE_TYPE_VARIABLE_REF, aPrefix, aNamespace, aVariableName);
   }

   /**
    * Returns the variable's QName.
    */
   public QName getVariableQName()
   {
      return new QName(getNamespace(), getLocalName());
   }

   /**
    * @see org.activebpel.rt.bpel.xpath.ast.AeAbstractXPathNode#accept(org.activebpel.rt.bpel.xpath.ast.IAeXPathNodeVisitor)
    */
   public void accept(IAeXPathNodeVisitor aVisitor)
   {
      aVisitor.visit(this);
   }
}
