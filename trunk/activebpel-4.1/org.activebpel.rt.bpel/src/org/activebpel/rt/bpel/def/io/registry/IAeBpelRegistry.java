// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/io/registry/IAeBpelRegistry.java,v 1.4 2006/10/12 20:15:2
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
 * Interface for retrieving AeDef object serializers and
 * deserializers.
 * <br />
 * IAeBpelProcessDefReader is assumed to be a stateful reader
 * so a special accessor is provided.  All other readers will most
 * likely be stateless.
 */
public interface IAeBpelRegistry
{
   /**
    * Retrieve the appropriate reader based on the parent
    * def object and QName mapping.
    * @param aParentDef
    * @param aQName
    * @return IAeBpelDefReader impl
    */
   public IAeBpelDefReader getReader( AeBaseDef aParentDef, QName aQName );

   /**
    * Returns the extension reader configured for the registry.
    */
   public IAeBpelDefReader getExtensionReader();

   /**
    * Retrieve the appropriate writer based on the parent class
    * and the current AeDef object.
    * @param aParentClass
    * @param aDef
    * @return IAeBpelDefWriter impl
    */
   public IAeBpelDefWriter getWriter( Class aParentClass, AeBaseDef aDef );
}
