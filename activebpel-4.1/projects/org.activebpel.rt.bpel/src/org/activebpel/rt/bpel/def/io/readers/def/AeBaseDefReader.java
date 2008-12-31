// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/io/readers/def/AeBaseDefReader.java,v 1.5 2006/06/26 16:50:3
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
 * Simple base class for AeDef object readers.
 * Subclasses provide behavior for creating AeDef object impls
 * and configuring them appropriately.
 */
abstract public class AeBaseDefReader implements IAeBpelDefReader
{
   /**
    * @see org.activebpel.rt.bpel.def.io.readers.def.IAeBpelDefReader#read(org.activebpel.rt.bpel.def.AeBaseDef, org.w3c.dom.Element)
    */
   public AeBaseDef read(AeBaseDef aParent, Element aElement)
      throws AeBusinessProcessException
   {
      AeBaseDef childDef = createChild( aParent, aElement );
      if (configureChild( aParent, childDef, aElement ))
         return childDef;
      return null;
   }
   
   /**
    * Create the new AeBaseDef object.
    * @param aParent provided in the case of containers.
    * @param aElement the Element being read
    * @return appropriate AeBaseDef type
    * @throws AeBusinessProcessException
    */
   abstract protected AeBaseDef createChild(AeBaseDef aParent, Element aElement)
         throws AeBusinessProcessException;
   
   /**
    * It is expected that reader impls will extract attribute
    * (and possibly other) data from element to set properties
    * on the new def object and then add it to its parent in
    * the appropriate manner.
    * @param aParentDef
    * @param aNewDef
    * @param aElement
    * @throws AeBusinessProcessException
    */
   abstract protected boolean configureChild( AeBaseDef aParentDef, AeBaseDef aNewDef, Element aElement )
   throws AeBusinessProcessException;
}
