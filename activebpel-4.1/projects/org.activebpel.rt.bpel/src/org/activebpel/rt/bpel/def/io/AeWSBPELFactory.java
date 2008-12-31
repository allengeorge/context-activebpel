// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/io/AeWSBPELFactory.java,v 1.2 2006/10/12 20:15:2
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
package org.activebpel.rt.bpel.def.io;

import org.activebpel.rt.bpel.def.io.readers.AeWSBPELBpelReader;
import org.activebpel.rt.bpel.def.io.registry.AeWSBPELBpelRegistry;
import org.activebpel.rt.bpel.def.io.registry.IAeBpelRegistry;
import org.activebpel.rt.bpel.def.io.writers.AeRegistryBasedBpelWriter;


/**
 * BPEL factory for WSBPEL4 2.0.
 */
public class AeWSBPELFactory extends AeAbstractBpelFactory
{
   /**
    * Default c'tor.
    */
   protected AeWSBPELFactory()
   {
      super();
   }

   /**
    * @see org.activebpel.rt.bpel.def.io.IAeBpelFactory#createBpelReader()
    */
   public IAeBpelReader createBpelReader()
   {
      return new AeWSBPELBpelReader(getBpelRegistry());
   }

   /**
    * @see org.activebpel.rt.bpel.def.io.IAeBpelFactory#createBpelWriter()
    */
   public IAeBpelWriter createBpelWriter()
   {
      return new AeRegistryBasedBpelWriter(getBpelRegistry());
   }

   /**
    * @see org.activebpel.rt.bpel.def.io.AeAbstractBpelFactory#createBpelRegistry()
    */
   protected IAeBpelRegistry createBpelRegistry()
   {
      return new AeWSBPELBpelRegistry();
   }
}
