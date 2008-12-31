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

/**
 * Container for partnerLinks. Broken out as a separate class so we can visit it.
 */
public class AePartnerLinksDef extends AeBaseContainer implements IAePartnerLinkParentDef
{
   /**
    * Default c'tor.
    */
   public AePartnerLinksDef()
   {
      super();
   }

   /**
    * Adds a new partnerLinkDef to the collection.
    * @param aPartnerLink
    */
   public void addPartnerLinkDef(AePartnerLinkDef aPartnerLink)
   {
      add(aPartnerLink.getName(), aPartnerLink);
   }
   
   /**
    * Gets a single partner link by its name.
    * 
    * @param aPartnerLinkName
    */
   public AePartnerLinkDef getPartnerLinkDef(String aPartnerLinkName)
   {
      return (AePartnerLinkDef) get(aPartnerLinkName);
   }
   
   /**
    * Gets an iterator over the list of all partner link defs.
    */
   public Iterator getPartnerLinkDefs()
   {
      return getValues();
   }

   /**
    * @see org.activebpel.rt.bpel.def.AeBaseDef#accept(org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void accept(IAeDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }
}
