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

import org.activebpel.rt.bpel.def.visitors.IAeDefVisitor;
import org.activebpel.rt.util.AeUtil;

/**
 * Models the 'extensions' bpel construct introduced in WS-BPEL 2.0.
 */
public class AeExtensionsDef extends AeBaseContainer
{
   /**
    * Default c'tor.
    */
   public AeExtensionsDef()
   {
      super();
   }
   
   /**
    * Adds an 'extension' child element to the container's list.
    * 
    * @param aDef
    */
   public void addExtensionDef(AeExtensionDef aDef)
   {
      add(aDef);
   }

   /**
    * Gets an Iterator over the list of all the extension defs.
    */
   public Iterator getExtensionDefs()
   {
      return getValues();
   }
   
   /**
    * Returns true if there is an extension def in the list with the given namespace.
    * 
    * @param aNamespace
    */
   public boolean hasExtensionDef(String aNamespace)
   {
      for (Iterator iter = getValues(); iter.hasNext(); )
      {
         AeExtensionDef extDef = (AeExtensionDef) iter.next();
         if (AeUtil.compareObjects(aNamespace, extDef.getNamespace()))
         {
            return true;
         }
      }
      return false;
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.AeBaseDef#accept(org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void accept(IAeDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }
}
