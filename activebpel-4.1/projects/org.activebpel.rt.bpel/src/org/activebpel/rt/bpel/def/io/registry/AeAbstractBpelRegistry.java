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
package org.activebpel.rt.bpel.def.io.registry;

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.def.AeBaseDef;
import org.activebpel.rt.bpel.def.io.readers.def.IAeBpelDefReader;
import org.activebpel.rt.bpel.def.io.writers.def.IAeBpelDefWriter;

/**
 * An abstract base class for BPEL registries.
 */
public abstract class AeAbstractBpelRegistry implements IAeBpelRegistry
{
   /** reader registry impl */
   private IAeDefReaderRegistry mReaderRegistry;
   /** writer registry impl */
   private IAeDefWriterRegistry mWriterRegistry;

   /**
    * Default constructor - creates the reader and writer registries (delegates the actual
    * construction to subclasses).
    */
   public AeAbstractBpelRegistry()
   {
      setReaderRegistry(createReaderRegistry());
      setWriterRegistry(createWriterRegistry());
   }

   /**
    * Creates a reader registry.
    */
   protected abstract IAeDefReaderRegistry createReaderRegistry();

   /**
    * Creates a reader registry.
    */
   protected abstract IAeDefWriterRegistry createWriterRegistry();

   /**
    * @see org.activebpel.rt.bpel.def.io.registry.IAeBpelRegistry#getReader(org.activebpel.rt.bpel.def.AeBaseDef, javax.xml.namespace.QName)
    */
   public IAeBpelDefReader getReader(AeBaseDef aParentDef, QName aQName)
   {
      return getReaderRegistry().getReader(aParentDef, aQName);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.io.registry.IAeBpelRegistry#getExtensionReader()
    */
   public IAeBpelDefReader getExtensionReader()
   {
      return getReaderRegistry().getExtensionReader();
   }

   /**
    * @see org.activebpel.rt.bpel.def.io.registry.IAeBpelRegistry#getWriter(java.lang.Class, org.activebpel.rt.bpel.def.AeBaseDef)
    */
   public IAeBpelDefWriter getWriter(Class aParentClass, AeBaseDef aDef)
   {
      return getWriterRegistry().getWriter(aParentClass, aDef);
   }

   /**
    * @return Returns the readerRegistry.
    */
   public IAeDefReaderRegistry getReaderRegistry()
   {
      return mReaderRegistry;
   }

   /**
    * @param aReaderRegistry The readerRegistry to set.
    */
   protected void setReaderRegistry(IAeDefReaderRegistry aReaderRegistry)
   {
      mReaderRegistry = aReaderRegistry;
   }

   /**
    * @return Returns the writerRegistry.
    */
   public IAeDefWriterRegistry getWriterRegistry()
   {
      return mWriterRegistry;
   }

   /**
    * @param aWriterRegistry The writerRegistry to set.
    */
   protected void setWriterRegistry(IAeDefWriterRegistry aWriterRegistry)
   {
      mWriterRegistry = aWriterRegistry;
   }
}
