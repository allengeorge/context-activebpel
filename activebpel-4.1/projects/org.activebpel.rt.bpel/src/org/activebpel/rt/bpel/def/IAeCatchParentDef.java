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


/**
 * Interface for adding a catch or catchAll to a fault handler container.
 */
public interface IAeCatchParentDef
{
   /**
    * Gets an iterator of the list of 'catch' defs.
    */
   public Iterator getCatchDefs();
   
   /**
    * Gets the 'catchall' def.
    */
   public AeCatchAllDef getCatchAllDef();
   
   /**
    * Adds the fault handler (catch) to the collection.
    * @param aDef
    */
   public void addCatchDef(AeCatchDef aDef);

   /**
    * Setter for the default fault handler (catchall).
    * @param aDef
    */
   public void setCatchAllDef(AeCatchAllDef aDef);
}
 
