//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/web/processview/AeBpelScopeObject.java,v 1.2 2006/07/25 17:56:3
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
package org.activebpel.rt.bpeladmin.war.web.processview;

import java.util.List;

import org.activebpel.rt.bpel.def.AeBaseDef;
import org.activebpel.rt.bpel.def.activity.AeActivityScopeDef;

/**
 * Represents a web visual model for a BPEL scope activity.
 */
public class AeBpelScopeObject extends AeBpelActivityObject
{

   /**
    * Ctor.
    * @param aTagName tag name
    * @param aDef scope or process tag name.
    */
   protected AeBpelScopeObject(String aTagName, AeBaseDef aDef)
   {
      super(aTagName, aDef);
   }

   /**
    * Ctor.
    * @param aDef scope definition.
    */
   public AeBpelScopeObject(AeActivityScopeDef aDef)
   {
      this(AeActivityScopeDef.TAG_SCOPE, aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpeladmin.war.web.processview.AeBpelObjectBase#findPartnerLink(java.lang.String)
    */
   public AeBpelObjectBase findPartnerLink(String aPartnerLinkName)
   {
      AeBpelObjectBase  rVal = getPartnerLink(aPartnerLinkName);
      if (rVal != null)
      {
         return rVal;
      }
      else
      {
         return super.findPartnerLink(aPartnerLinkName);
      }
   }    
   
   /**
    * Returns the partnerlink contained in this scope or null if not found.
    * @param aPartnerLinkName
    * @return partner link.
    */
   protected AeBpelObjectBase getPartnerLink(String aPartnerLinkName)
   {
      AeBpelObjectBase rVal = null;
      List partnerLinks = getChildren("partnerLinks");//$NON-NLS-1$
      if (partnerLinks.size() > 0)
      {
         List partners = ( (AeBpelObjectContainer)partnerLinks.get(0)).getChildren();
         for (int i = 0; i < partners.size(); i++)
         {
            AeBpelObjectBase partnerLink = (AeBpelObjectBase) partners.get(i);
            if (partnerLink.getName().equals(aPartnerLinkName))
            {
               rVal = partnerLink;
               break;
            }
          }         
      }
      return rVal;
   }   
}
