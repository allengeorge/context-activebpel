// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/xpath/ast/AeXPathNameStepNode.java,v 1.1 2006/07/21 16:03:3
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
 * An XPath node for name step.
 */
public class AeXPathNameStepNode extends AeAbstractXPathAxisNode implements IAeXPathQualifiedNode
{
   /** The prefix used by the node step. */
   private String mPrefix;
   /** The namespace. */
   private String mNamespace;
   /** The local name. */
   private String mLocalName;

   /**
    * Default c'tor.
    */
   public AeXPathNameStepNode(int aAxis, String aPrefix, String aNamespace, String aLocalName)
   {
      super(AeAbstractXPathNode.NODE_TYPE_NAME_STEP, aAxis);
      
      setNamespace(aNamespace);
      setPrefix(aPrefix);
      setLocalName(aLocalName);
   }

   /**
    * @see org.activebpel.rt.bpel.xpath.ast.IAeXPathQualifiedNode#getLocalName()
    */
   public String getLocalName()
   {
      return mLocalName;
   }

   /**
    * @param aLocalName The localName to set.
    */
   protected void setLocalName(String aLocalName)
   {
      mLocalName = aLocalName;
   }

   /**
    * @see org.activebpel.rt.bpel.xpath.ast.IAeXPathQualifiedNode#getNamespace()
    */
   public String getNamespace()
   {
      return mNamespace;
   }

   /**
    * @param aNamespace The namespace to set.
    */
   protected void setNamespace(String aNamespace)
   {
      mNamespace = aNamespace;
   }

   /**
    * @see org.activebpel.rt.bpel.xpath.ast.IAeXPathQualifiedNode#getPrefix()
    */
   public String getPrefix()
   {
      return mPrefix;
   }

   /**
    * @param aPrefix The prefix to set.
    */
   public void setPrefix(String aPrefix)
   {
      mPrefix = aPrefix;
   }

   /**
    * @see org.activebpel.rt.bpel.xpath.ast.AeAbstractXPathNode#accept(org.activebpel.rt.bpel.xpath.ast.IAeXPathNodeVisitor)
    */
   public void accept(IAeXPathNodeVisitor aVisitor)
   {
      aVisitor.visit(this);
   }
}
