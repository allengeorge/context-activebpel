// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/io/writers/def/AeDispatchWriter.java,v 1.8 2006/11/04 16:34:2
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
package org.activebpel.rt.bpel.def.io.writers.def;

import org.activebpel.rt.bpel.def.AeBaseContainer;
import org.activebpel.rt.bpel.def.AeBaseDef;
import org.activebpel.rt.bpel.def.AeEventHandlersDef;
import org.activebpel.rt.bpel.def.AeVariableDef;
import org.w3c.dom.Element;

/**
 * Generic writer class for serializing AeDef objects.
 * Creates an element with the given tag name
 * and uses an instance of the AeWriterVisitor to 
 * extract specific attributes based on object type.
 */
public class AeDispatchWriter implements IAeBpelDefWriter
{
   /** tag name for element*/
   private String mTagName;
   /** namespace for the element, defaults to the bpel namespace */
   private String mNamespace;
   /** Factory for creating writer def visitors to dispatch to. */
   private IAeWriterDefVisitorFactory mWriterVisitorFactory;

   /**
    * Constructor. Creates the writer with the specified namespace uri and tagname.
    * 
    * @param aNamespaceUri
    * @param aTagName
    */
   public AeDispatchWriter(String aNamespaceUri, String aTagName, IAeWriterDefVisitorFactory aWriterVisitorFactory)
   {
      setNamespace(aNamespaceUri);
      setTagName(aTagName);
      setWriterVisitorFactory(aWriterVisitorFactory);
   }

   /**
    * @see org.activebpel.rt.bpel.def.io.writers.def.IAeBpelDefWriter#createElement(org.activebpel.rt.bpel.def.AeBaseDef, org.w3c.dom.Element)
    */
   public Element createElement(AeBaseDef aBaseDef, Element aParentElement)
   {
      if( isEmptyContainer(aBaseDef) || isImplicitVariable(aBaseDef))
      {
         return null;
      }
      
      IAeWriterDefVisitor visitor = getWriterVisitorFactory().createWriterDefVisitor(aBaseDef,
            aParentElement, getNamespace(), getTagName());
      aBaseDef.accept(visitor);
      return visitor.getElement();      
   }
   
   /**
    * Tests to see if the def passed in is an implicit variable. These variables
    * are not written to the xml.
    * 
    * @param aBaseDef
    */
   protected boolean isImplicitVariable(AeBaseDef aBaseDef)
   {
      if (aBaseDef instanceof AeVariableDef)
      {
         return ((AeVariableDef)aBaseDef).isImplicit();
      }
      return false;
   }

   /**
    * Used to determine if the base def is an empty container.
    * Empty container elements are not added to the bpel xml.
    * @param aBaseDef
    * @return true is the base def is an AeBaseContainer with no contents
    */
   private boolean isEmptyContainer(AeBaseDef aBaseDef)
   {
      if (aBaseDef instanceof AeBaseContainer)
         return ((AeBaseContainer) aBaseDef).isEmpty();
      else if (aBaseDef instanceof AeEventHandlersDef)
         return !((AeEventHandlersDef) aBaseDef).hasEventHandler();

      return false;
   }

   /**
    * Setter for tag name
    * @param tagName
    */
   protected void setTagName(String tagName)
   {
      mTagName = tagName;
   }

   /**
    * Getter for tag name
    */
   protected String getTagName()
   {
      return mTagName;
   }

   /**
    * Setter for namespace
    * @param namespace
    */
   protected void setNamespace(String namespace)
   {
      mNamespace = namespace;
   }

   /**
    * Getter for namespace
    */
   protected String getNamespace()
   {
      return mNamespace;
   }

   /**
    * @return Returns the writerVisitorFactory.
    */
   protected IAeWriterDefVisitorFactory getWriterVisitorFactory()
   {
      return mWriterVisitorFactory;
   }

   /**
    * @param aWriterVisitorFactory The writerVisitorFactory to set.
    */
   protected void setWriterVisitorFactory(IAeWriterDefVisitorFactory aWriterVisitorFactory)
   {
      mWriterVisitorFactory = aWriterVisitorFactory;
   }
}
