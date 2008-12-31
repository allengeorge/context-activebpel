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
 * Factory for reading and writing BPEL. Each factory deals with a specific version of 
 * BPEL. The factories also contain a registry that maps elements to def objects and
 * def objects to element names. These registries can be overridden if desired. 
 */
public interface IAeBpelFactory
{
   /**
    * Gets an object capable of reading BPEL xml into our definition objects.
    */
   public IAeBpelReader createBpelReader();
   
   /**
    * Gets an object capable of writing our def objects into BPEL xml.
    */
   public IAeBpelWriter createBpelWriter();
   
   /**
    * Getter for the currently installed BPEL registry
    */
   public IAeBpelRegistry getBpelRegistry();
   
   /**
    * An override for the BPEL registry. 
    * @param aRegistry
    */
   public void setBpelRegistry( IAeBpelRegistry aRegistry );
}
 
