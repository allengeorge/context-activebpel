// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/fastdom/AeFastDocument.java,v 1.5 2006/01/03 19:55:0
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

import java.util.Collections;
import java.util.List;

import org.activebpel.rt.bpel.AeMessages;

/**
 * Implements a document in the fast, lightweight DOM.
 */
public class AeFastDocument extends AeFastNode implements IAeFastParent
{
   /** The document's root element. */
   private AeFastElement mRootElement;

   /**
    * Default constructor.
    */
   public AeFastDocument()
   {
      this(null);
   }

   /**
    * Constructs a new <code>AeFastDocument</code> with the specified element
    * for the root element.
    */
   public AeFastDocument(AeFastElement aRootElement)
   {
      mRootElement = aRootElement;
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
      if (!(aChild instanceof AeFastElement))
      {
         throw new AeIllegalAddException(AeMessages.getString("AeFastDocument.ERROR_0")); //$NON-NLS-1$
      }

      if (getRootElement() != null)
      {
         throw new AeIllegalAddException(AeMessages.getString("AeFastDocument.ERROR_1")); //$NON-NLS-1$
      }

      setRootElement((AeFastElement) aChild);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.fastdom.IAeFastParent#getChildNodes()
    */
   public List getChildNodes()
   {
      AeFastElement root = getRootElement();
      return (root == null) ? Collections.EMPTY_LIST : Collections.singletonList(root);
   }

   /**
    * Returns this document's root element.
    */
   public AeFastElement getRootElement()
   {
      return mRootElement;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.fastdom.IAeFastParent#removeChild(org.activebpel.rt.bpel.impl.fastdom.AeFastNode)
    */
   public boolean removeChild(AeFastNode aChild)
   {
      if (aChild == getRootElement())
      {
         setRootElement(null);
         aChild.setParent(null);
         return true;
      }

      return false;
   }

   /**
    * Sets this document's root element to be the specified element.
    *
    * @param aElement
    */
   public void setRootElement(AeFastElement aElement)
   {
      if (aElement != null && aElement.getParent() != null)
      {
         throw new AeIllegalAddException();
      }

      mRootElement = aElement;
      if (aElement != null)
         aElement.setParent(this);
   }
}
