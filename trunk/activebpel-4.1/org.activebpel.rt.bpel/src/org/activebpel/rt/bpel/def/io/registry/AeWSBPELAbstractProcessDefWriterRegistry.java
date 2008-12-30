//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/io/registry/AeWSBPELAbstractProcessDefWriterRegistry.java,v 1.3 2006/11/04 16:34:2
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

import org.activebpel.rt.bpel.def.AeBaseDef;
import org.activebpel.rt.bpel.def.IAeBPELConstants;
import org.activebpel.rt.bpel.def.io.writers.def.AeWSBPELAbstractProcessWriterVisitor;
import org.activebpel.rt.bpel.def.io.writers.def.IAeWriterDefVisitor;
import org.activebpel.rt.bpel.def.io.writers.def.IAeWriterDefVisitorFactory;
import org.w3c.dom.Element;

/**
 * A WSBPEL 2.0 Abstract process's implementation of a Def Writer Registry.
 */
public class AeWSBPELAbstractProcessDefWriterRegistry extends AeWSBPELDefWriterRegistry
{

   /**
    * Default ctor.
    */
   public AeWSBPELAbstractProcessDefWriterRegistry()
   {
      super();
   }

   /**
    * Overrides the base class in order to return the WSBPEL 2.0 Abstract process namespace.
    * @see org.activebpel.rt.bpel.def.io.registry.AeBPWSDefWriterRegistry#getBpelNamespace()
    */
   protected String getBpelNamespace()
   {
      return IAeBPELConstants.WSBPEL_2_0_ABSTRACT_NAMESPACE_URI;
   }

   /**
    * Creates the writer def factory that the dispatch writer will use to create visitors to dispatch to.
    */
   protected IAeWriterDefVisitorFactory createWriterVisitorFactory()
   {
      return new IAeWriterDefVisitorFactory()
      {
         /**
          * @see org.activebpel.rt.bpel.def.io.writers.def.IAeWriterDefVisitorFactory#createWriterDefVisitor(org.activebpel.rt.bpel.def.AeBaseDef, org.w3c.dom.Element, java.lang.String, java.lang.String)
          */
         public IAeWriterDefVisitor createWriterDefVisitor(AeBaseDef aDef, Element aParentElement,
               String aNamespaceUri, String aTagName)
         {
            return new AeWSBPELAbstractProcessWriterVisitor(aDef, aParentElement, aNamespaceUri, aTagName);
         }
      };
   }
}
