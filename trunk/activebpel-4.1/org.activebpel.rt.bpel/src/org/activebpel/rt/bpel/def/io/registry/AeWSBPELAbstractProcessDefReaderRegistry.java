//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/io/registry/AeWSBPELAbstractProcessDefReaderRegistry.java,v 1.2 2006/10/24 21:23:5
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
import org.activebpel.rt.bpel.def.io.readers.def.AeWSBPELAbstractProcessReaderVisitor;
import org.activebpel.rt.bpel.def.io.readers.def.IAeReaderDefVisitor;
import org.activebpel.rt.bpel.def.io.readers.def.IAeReaderDefVisitorFactory;
import org.w3c.dom.Element;

/**
 * An implementation of the Def Reader Registry for WS-BPEL 2.0 Abstract processes. This implementation extends the 
 * WS BPEL 2.0 (executable) implementation.
 */
public class AeWSBPELAbstractProcessDefReaderRegistry extends AeWSBPELDefReaderRegistry
{

   /**
    * Default ctor.
    */
   public AeWSBPELAbstractProcessDefReaderRegistry()
   {
      super();
   }
   
   /**
    * Overrides the base class in order to return the WSBPEL 2.0 Abstract process namespace.
    * @see org.activebpel.rt.bpel.def.io.registry.AeBPWSDefReaderRegistry#getBPELNamespace()
    */
   protected String getBPELNamespace()
   {
      return IAeBPELConstants.WSBPEL_2_0_ABSTRACT_NAMESPACE_URI;
   }   
   
   /**
    * Creates the reader def visitor factory that the dispatch reader will use.
    */
   protected IAeReaderDefVisitorFactory createReaderVisitorFactory()
   {
      return new IAeReaderDefVisitorFactory()
      {
         /**
          * @see org.activebpel.rt.bpel.def.io.readers.def.IAeReaderDefVisitorFactory#createReaderDefVisitor(org.activebpel.rt.bpel.def.AeBaseDef, org.activebpel.rt.bpel.def.AeBaseDef, org.w3c.dom.Element)
          */
         public IAeReaderDefVisitor createReaderDefVisitor(AeBaseDef aParentDef, AeBaseDef aNewDef, Element aElement)
         {
            return new AeWSBPELAbstractProcessReaderVisitor(aParentDef, aElement);
         }
      };
   }   

}
