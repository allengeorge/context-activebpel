// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/fastdom/AeFastElement.java,v 1.4 2006/01/26 18:11:0
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.activebpel.rt.bpel.AeMessages;

/**
 * Implements an element node in the fast, lightweight DOM.
 */
public class AeFastElement extends AeFastNode implements IAeFastParent
{
   /** The element's name. */
   private final String mName;

   /** Maps attribute names to attribute instances. */
   private final Map mAttributesMap = new HashMap();

   /** The element's child nodes. */
   private final Collection mChildNodes = new LinkedHashSet();

   /**
    * Constructs an element with the specified name.
    *
    * @param aName
    */
   public AeFastElement(String aName)
   {
      mName = aName;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.fastdom.IAeVisitable#accept(org.activebpel.rt.bpel.impl.fastdom.IAeVisitor)
    */
   public void accept(IAeVisitor aVisitor)
   {
      aVisitor.visit(this);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.fastdom.IAeFastParent#appendChild(org.activebpel.rt.bpel.impl.fastdom.AeFastNode)
    */
   public void appendChild(AeFastNode aChild)
   {
      if (aChild.getParent() != null)
      {
         throw new AeIllegalAddException(AeMessages.getString("AeFastElement.ERROR_0")); //$NON-NLS-1$
      }

      internalGetChildNodes().add(aChild);
      aChild.setParent(this);
   }

   /**
    * Returns an ordered collection of this element's attributes.
    */
   public Collection getAttributes()
   {
      return new TreeMap(getAttributesMap()).values();
   }

   /**
    * Returns the <code>Map</code> of attribute names to attribute instances.
    */
   protected Map getAttributesMap()
   {
      return mAttributesMap;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.fastdom.IAeFastParent#getChildNodes()
    */
   public List getChildNodes()
   {
      return new ArrayList(internalGetChildNodes());
   }

   /**
    * Returns this element's name.
    */
   public String getName()
   {
      return mName;
   }

   /**
    * Returns the internal representation of this node's child nodes.
    */
   protected Collection internalGetChildNodes()
   {
      return mChildNodes;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.fastdom.IAeFastParent#removeChild(org.activebpel.rt.bpel.impl.fastdom.AeFastNode)
    */
   public boolean removeChild(AeFastNode aChild)
   {
      if (internalGetChildNodes().remove(aChild))
      {
         aChild.setParent(null);
         return true;
      }

      return false;
   }

   /**
    * Sets an attribute with the specified name and value on this element.
    *
    * @param aName
    * @param aValue
    */
   public void setAttribute(String aName, String aValue)
   {
      AeFastAttribute attribute = new AeFastAttribute(aName, aValue);
      attribute.setParent(this);
      getAttributesMap().put(aName, attribute);
   }
}
