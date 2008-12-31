// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/io/readers/def/AeDispatchReader.java,v 1.10 2007/04/03 20:40:5
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
package org.activebpel.rt.bpel.def.io.readers.def;

import java.util.Iterator;
import java.util.Set;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.bpel.def.AeBaseDef;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

/**
 * Generic AeBaseDefReader impl that creates a new instance of
 * its child class and uses double dispatch to populate it
 * based on type.
 */
public class AeDispatchReader extends AeBaseDefReader
{
   /** The AeBaseDef class type to create */
   private Class mChildClass;
   /** The reader factory - creates a reader to dispatch to. */
   private IAeReaderDefVisitorFactory mReaderVisitorFactory;

   /**
    * Constructor.
    * 
    * @param aChildClass the AeBaseDef type to create
    * @param aReaderVisitorFactory
    */
   public AeDispatchReader(Class aChildClass, IAeReaderDefVisitorFactory aReaderVisitorFactory)
   {
      setChildClass(aChildClass);
      setReaderVisitorFactory(aReaderVisitorFactory);
   }

   /**
    * Dispatch newly created AeBaseDef to an instance of the AeReaderVisitor to set its properties based on
    * the current element.
    * 
    * @see org.activebpel.rt.bpel.def.io.readers.def.AeBaseDefReader#configureChild(org.activebpel.rt.bpel.def.AeBaseDef, org.activebpel.rt.bpel.def.AeBaseDef, org.w3c.dom.Element)
    */
   protected boolean configureChild(AeBaseDef aParentDef, AeBaseDef aNewDef, Element aElement)
      throws AeBusinessProcessException
   {
      IAeReaderDefVisitor visitor = getReaderVisitorFactory().createReaderDefVisitor(aParentDef, aNewDef, aElement);
      aNewDef.accept(visitor);
      readExtensionAttributes(aNewDef, aElement, visitor.getConsumedAttributes());
      if (visitor.hasErrors())
      {
         // report any errors to the console
         for (Iterator iter = visitor.getErrors().iterator(); iter.hasNext();)
         {
            String error = (String) iter.next();
            AeBusinessProcessException.logWarning(error);
         }
      }
      return !visitor.hasErrors();
   }

   /**
    * Returns a mChildClass.newInstance()
    * @see org.activebpel.rt.bpel.def.io.readers.def.AeBaseDefReader#createChild(org.activebpel.rt.bpel.def.AeBaseDef, Element)
    */
   protected AeBaseDef createChild(AeBaseDef aParent, Element aElement)
      throws AeBusinessProcessException
   {
      try
      {
         return (AeBaseDef)getChildClass().newInstance();
      }
      catch (InstantiationException e)
      {
         throw new AeBusinessProcessException(
                  AeMessages.getString("AeDispatchReader.ERROR_0") + getChildClass().getName(), e); //$NON-NLS-1$
      }
      catch (IllegalAccessException e)
      {
         throw new AeBusinessProcessException(
                  AeMessages.getString("AeDispatchReader.ERROR_0") + getChildClass().getName(), e); //$NON-NLS-1$
      }
   }

   /**
    * Reads any extension attributes into the def.
    * 
    * @param aDef
    * @param aElement
    * @param aConsumedAttributes
    */
   protected void readExtensionAttributes(AeBaseDef aDef, Element aElement, Set aConsumedAttributes)
   {
      if (aElement.hasAttributes())
      {
         // Loop through and add all attributes which are qualified but not part of the 
         // xmlns namespace
         NamedNodeMap attrNodes = aElement.getAttributes();
         for (int i = 0, length = attrNodes.getLength(); i < length; i++)
         {
            Attr attr = (Attr) attrNodes.item(i);
            if (!aConsumedAttributes.contains(attr))
            {
               aDef.addExtensionAttributeDef(attr.getNamespaceURI(), attr.getNodeName(), attr.getNodeValue());
            }
         }
      }
   }

   /**
    * @return Returns the readerVisitorFactory.
    */
   protected IAeReaderDefVisitorFactory getReaderVisitorFactory()
   {
      return mReaderVisitorFactory;
   }

   /**
    * @param aReaderVisitorFactory The readerVisitorFactory to set.
    */
   protected void setReaderVisitorFactory(IAeReaderDefVisitorFactory aReaderVisitorFactory)
   {
      mReaderVisitorFactory = aReaderVisitorFactory;
   }

   /**
    * @return Returns the childClass.
    */
   protected Class getChildClass()
   {
      return mChildClass;
   }

   /**
    * @param aChildClass The childClass to set.
    */
   protected void setChildClass(Class aChildClass)
   {
      mChildClass = aChildClass;
   }
}
