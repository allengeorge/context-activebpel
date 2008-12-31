// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/fastdom/AeFastAttribute.java,v 1.1 2004/09/07 22:08:2
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
 * Implements an element attribute in the fast, lightweight DOM.
 */
public class AeFastAttribute extends AeFastNode
{
   /** The attribute's name. */
   private final String mName;

   /** The attribute's value. */
   private final String mValue;

   /**
    * Constructs an attribute with the specified name and value.
    *
    * @param aName
    * @param aValue
    */
   public AeFastAttribute(String aName, String aValue)
   {
      mName = aName;
      mValue = aValue;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.fastdom.IAeVisitable#accept(org.activebpel.rt.bpel.impl.fastdom.IAeVisitor)
    */
   public void accept(IAeVisitor aVisitor)
   {
      aVisitor.visit(this);
   }

   /**
    * Returns this attribute's name.
    */
   public String getName()
   {
      return mName;
   }

   /**
    * Returns this attribute's value.
    */
   public String getValue()
   {
      return mValue;
   }
}
