// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/xpath/ast/AeAbstractXPathQualifiedNode.java,v 1.3 2006/09/07 15:06:2
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
 * A simple base class for XPath nodes that are potentially namespace qualified.
 */
public abstract class AeAbstractXPathQualifiedNode extends AeAbstractXPathNode implements IAeXPathQualifiedNode
{
   /** The prefix used to resolve the namespace. */
   private String mPrefix;
   /** The namespace. */
   private String mNamespace;
   /** The local name. */
   private String mLocalName;

   /**
    * Simple constructor.
    * 
    * @param aType
    * @param aPrefix
    * @param aNamespace
    * @param aLocalName
    */
   public AeAbstractXPathQualifiedNode(String aType, String aPrefix, String aNamespace, String aLocalName)
   {
      super(aType);
      setPrefix(aPrefix);
      setNamespace(aNamespace);
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
   public void setLocalName(String aLocalName)
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
   public void setNamespace(String aNamespace)
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
}
