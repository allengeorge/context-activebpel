// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/web/AePartnerDefsBean.java,v 1.1 2004/08/19 16:19:2
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
package org.activebpel.rt.bpeladmin.war.web;

import org.activebpel.rt.bpel.server.engine.AeEngineFactory;

/**
 * Wraps the partner definitions listing.
 */
public class AePartnerDefsBean
{
   /** Wrapper principal names. */
   protected AeJavaTypesWrapper[] mPartners;
   
   /**
    * Constructor.  Initializes the wrapped details.
    */
   public AePartnerDefsBean()
   {
      String[] partners = 
            AeEngineFactory.getEngineAdministration().getPartnerAddressingAdmin().getPrincipals();

      if( partners != null )
      {
         mPartners = AeJavaTypesWrapper.wrap( partners );            
      }
   }
   
   /**
    * Returns true if there are no details to display.
    */
   public boolean isEmpty()
   {
      return mPartners == null || mPartners.length == 0;
   }
   
   /**
    * Indexed property accessor for the wrapped principal.
    * @param aIndex
    * @return AeJavaTypesWrapper Wraps the string name.
    */
   public AeJavaTypesWrapper getPrincipal( int aIndex )
   {
      return mPartners[aIndex];
   }
   
   /**
    * Returns the size of the indexed property array.
    */
   public int getPrincipalSize()
   {
      if( mPartners == null )
      {
         return 0;
      }
      return mPartners.length;
   }
}
