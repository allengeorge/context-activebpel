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

import org.activebpel.rt.bpel.def.io.registry.IAeBpelRegistry;

/**
 * A base class for the BPEL factories.
 */
public abstract class AeAbstractBpelFactory implements IAeBpelFactory
{
   /** bpel reader/writer registry */
   private IAeBpelRegistry mBpelRegistry;

   /**
    * Default c'tor.
    */
   public AeAbstractBpelFactory()
   {
      super();
   }

   /**
    * @see org.activebpel.rt.bpel.def.io.IAeBpelFactory#setBpelRegistry(org.activebpel.rt.bpel.def.io.registry.IAeBpelRegistry)
    */
   public void setBpelRegistry(IAeBpelRegistry aRegistry)
   {
      mBpelRegistry = aRegistry;
   }

   /**
    * Accessor for the IAeBpelRegistry impl. If none has been set, a new <code>AeDefaultBpelRegistry</code>
    * is created and set as the member registry. <br />
    * This registry will be installed in all readers and writers.
    * 
    * @return IAeBpelRegistry impl
    */
   public IAeBpelRegistry getBpelRegistry()
   {
      if (mBpelRegistry == null)
      {
         mBpelRegistry = createBpelRegistry();
      }
      return mBpelRegistry;
   }

   /**
    * Creates the BPEL registry.
    */
   protected abstract IAeBpelRegistry createBpelRegistry();
}
