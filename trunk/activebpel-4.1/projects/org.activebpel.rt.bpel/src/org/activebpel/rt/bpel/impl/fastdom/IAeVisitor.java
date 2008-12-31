// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/fastdom/IAeVisitor.java,v 1.1 2004/09/07 22:08:2
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
 * Defines the interface for objects that can visit nodes in the fast,
 * lightweight DOM.
 */
public interface IAeVisitor
{
   /**
    * Visits the specified attribute.
    *
    * @param aAttribute
    */
   public void visit(AeFastAttribute aAttribute);

   /**
    * Visits the specified document.
    *
    * @param aDocument
    */
   public void visit(AeFastDocument aDocument);

   /**
    * Visits the specified element.
    *
    * @param aElement
    */
   public void visit(AeFastElement aElement);

   /**
    * Visits the specified text node.
    *
    * @param aText
    */
   public void visit(AeFastText aText);

   /**
    * Visits the specified foreign node reference
    *
    * @param aForeignNode
    */
   public void visit(AeForeignNode aForeignNode);
}
