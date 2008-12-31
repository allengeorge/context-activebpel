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
package org.activebpel.rt.bpel.def;

import java.util.Iterator;

import org.activebpel.rt.bpel.def.activity.support.AeFromPartsDef;

/**
 * Defs that can have a fromParts child construct will implement this interface.
 */
public interface IAeFromPartsParentDef
{
   /**
    * Sets the fromParts on the activity.
    * 
    * @param aDef
    */
   public void setFromPartsDef(AeFromPartsDef aDef);
   
   /**
    * Gets the fromParts def from the activity.
    */
   public AeFromPartsDef getFromPartsDef();

   /**
    * Gets an iterator over the list of fromPart defs.
    */
   public Iterator getFromPartDefs();
}
