// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/AePartnerDef.java,v 1.8 2006/06/26 16:50:2
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.activebpel.rt.bpel.def.visitors.IAeDefVisitor;

/**
 * Definition for bpel partner.
 */
public class AePartnerDef extends AeNamedDef implements IAePartnerLinkParentDef
{
   /** List of strings which are the names of the associated partner links. */
   private List mPartnerLinks = new ArrayList();

   /**
    * Default constructor
    */
   public AePartnerDef()
   {
      super();
   }

   /**
    * Returns an iterator for the list of partnerLink names (String) associated
    * with this partner.
    */
   public Iterator getPartnerLinks()
   {
      return mPartnerLinks.iterator();
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.IAePartnerLinkParentDef#addPartnerLinkDef(org.activebpel.rt.bpel.def.AePartnerLinkDef)
    */
   public void addPartnerLinkDef(AePartnerLinkDef aPartnerLink)
   {
      addPartnerLink(aPartnerLink.getName());
   }

   /**
    * Adds a partnerLink to the list associated with this partner.
    * @param aPartnerLink
    */
   public void addPartnerLink(String aPartnerLink)
   {
      mPartnerLinks.add(aPartnerLink);
   }

   /**
    * @see org.activebpel.rt.bpel.def.AeBaseDef#accept(org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void accept(IAeDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }
}
