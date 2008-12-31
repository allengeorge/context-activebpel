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

/**
 * Defines a Def reader registry.
 */
public interface IAeDefReaderRegistry
{
   /**
    * Return the appropriate IAeBpelDefReader impl for this
    * parent def and QName mapping.
    * 
    * @param aParentDef parent AeDef in the object model
    * @param aElementQName the child element QName
    * @return IAeBpelReader impl for deserializing this element or null if not found
    */
   public IAeBpelDefReader getReader(AeBaseDef aParentDef, QName aElementQName)
         throws UnsupportedOperationException;
   
   /**
    * Returns the bpel def reader for reading an extension element.
    */
   public IAeBpelDefReader getExtensionReader();
}
