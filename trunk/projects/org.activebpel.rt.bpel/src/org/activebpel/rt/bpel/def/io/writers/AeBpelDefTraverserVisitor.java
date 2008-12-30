// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/io/writers/AeBpelDefTraverserVisitor.java,v 1.14 2006/11/04 16:33:5
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
package org.activebpel.rt.bpel.def.io.writers;

import java.util.Stack;

import org.activebpel.rt.bpel.def.AeBaseDef;
import org.activebpel.rt.bpel.def.AeScopeDef;
import org.activebpel.rt.bpel.def.IAeBPELConstants;
import org.activebpel.rt.bpel.def.io.registry.IAeBpelRegistry;
import org.activebpel.rt.bpel.def.io.writers.def.IAeBpelDefWriter;
import org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor;
import org.activebpel.rt.bpel.def.visitors.AeDefTraverser;
import org.activebpel.rt.bpel.def.visitors.AeTraversalVisitor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Work horse class for traversing and serializing the AeProcessDef
 * object model.
 * <br />
 * The traversal mechanism is driven an AeDefTraverser subclass, the
 * <code>AeInvokeWithScopeTraverser</code>. 
 */
public class AeBpelDefTraverserVisitor extends AeAbstractDefVisitor implements IAeBPELConstants
{
   /** BPEL dom */
   private Document mProcessDoc;
   /** stack for maintaining parent elements */
   private Stack mStack = new Stack();
   /** bpel registry */
   private IAeBpelRegistry mRegistry;

   /**
    * Constructor.
    * @param aRegistry to retrieve IAeBpelDefWriter impls
    * @param aExtRegistry to retrieve IAeExtensionWriter impls
    */
   public AeBpelDefTraverserVisitor(IAeBpelRegistry aRegistry)
   {
      init();
      setRegistry(aRegistry);
   }
   
   /**
    * Install the appropriate traversal visitor
    * <br />
    * Override this method to change the traversal behavior.
    */
   protected void init()
   {
      setTraversalVisitor(new AeTraversalVisitor(new AeDefTraverser(), this));
   }
   
   /**
    * Push the current element onto the stack.
    * 
    * @param aElement
    */
   private void push(Element aElement)
   {
      mStack.push( aElement );
   }
   
   /**
    * Peek at the top level element.
    * @return the current top level element
    */
   private Element peek()
   {
      return mStack.isEmpty() ? null : (Element)mStack.peek();
   }
   
   /**
    * Pop the current element off the stack.
    */
   private void pop()
   {
      mStack.pop();
   }
   
   /**
    * Accessor for the serialized process.
    * @return the BPEL xml
    */
   public Document getProcessDoc()
   {
      return mProcessDoc;
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor#traverse(org.activebpel.rt.bpel.def.AeBaseDef)
    */
   protected void traverse(AeBaseDef aDef)
   {
      // call the traverse method with the current node from the stack which serves as the parent element
      // for the element we're going to create for this def.
      createElementAndTraverse(aDef, peek());
   }

   /**
    * Creates an element for the def, adding it to the parent element passed in. The new element is then
    * pushed onto the stack and we traverse the def to write all of its children. We then write any
    * extensions for the def followed by a pop() and then done and done.
    * @param aDef
    * @param aParentElementFromStack will be null if writing the first node
    */
   protected void createElementAndTraverse(AeBaseDef aDef, Element aParentElementFromStack)
   {
      Element element = createElement(aDef, aParentElementFromStack);
      if (mProcessDoc == null)
         mProcessDoc = element.getOwnerDocument();
      push(element);
      super.traverse(aDef);
      pop();
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AeScopeDef)
    */
   public void visit(AeScopeDef aDef)
   {
      // there is no xml to write for a scope def so we do a super.traverse() here.
      super.traverse(aDef);
   }

   /**
    * Creates the element using the writer and the current element on the 
    * stack as the parent element. 
    * @param aBaseDef
    */
   protected Element createElement( AeBaseDef aBaseDef )
   {
      return getWriter(aBaseDef).createElement(aBaseDef, peek());
   }

   /**
    * Creates the element using the writer
    * @param aBaseDef
    * @param aParentElement - if null, a new doc is created to contain the new element
    */
   protected Element createElement( AeBaseDef aBaseDef, Element aParentElement )
   {
      return getWriter(aBaseDef).createElement(aBaseDef, aParentElement);
   }

   /**
    * Accessor for the IAeBpelDefWriter.
    * @param aBaseDef
    * @return IAeBpelDefWriter impl
    */
   protected IAeBpelDefWriter getWriter(AeBaseDef aBaseDef)
   {
     // Noticed that when BPEP firsts starts up
     // it does a serialization call and it looked like all
     // of the def objs had null parents - not really an
     // issue for this impl of serialization, where the 
     // AeDef traverser drives the parent child relationship
     // but it would be an issue if another writer impl
     // required the parent class to look up potential child
     // writers for its children  
      Class parentClass = null;
      
      if( aBaseDef.getParent() != null )
      {
         parentClass = aBaseDef.getClass();
      }
      
      return getBpelRegistry().getWriter( parentClass, aBaseDef );
   }

   /**
    * @return registry member
    */
   private IAeBpelRegistry getBpelRegistry()
   {
      return mRegistry;
   }

   /**
    * @return Returns the registry.
    */
   protected IAeBpelRegistry getRegistry()
   {
      return mRegistry;
   }

   /**
    * @param aRegistry the registry to set
    */
   protected void setRegistry(IAeBpelRegistry aRegistry)
   {
      mRegistry = aRegistry;
   }
}
