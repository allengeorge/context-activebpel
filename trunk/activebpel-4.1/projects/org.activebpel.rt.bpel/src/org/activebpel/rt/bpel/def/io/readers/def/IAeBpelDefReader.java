// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/io/readers/def/IAeBpelDefReader.java,v 1.3 2006/10/12 20:15:2
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

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.def.AeBaseDef;
import org.w3c.dom.Element;

/**
 * Interface for working with AeDef object readers.  Readers
 * are responsible for creating their AeDef object counterpart,
 * setting the properties on def object based on element attributes
 * and adding the def to its parent object (via the IAeReaderContext).
 */
public interface IAeBpelDefReader
{
   /**
    * Deserialize the current element to it def type and add it to the parent def.
    * 
    * @param aParent add child to this def
    * @param aElement to be deserialized
    * @return newly created AeDef object
    * @throws AeBusinessProcessException
    */
   public AeBaseDef read(AeBaseDef aParent, Element aElement) throws AeBusinessProcessException;
}
