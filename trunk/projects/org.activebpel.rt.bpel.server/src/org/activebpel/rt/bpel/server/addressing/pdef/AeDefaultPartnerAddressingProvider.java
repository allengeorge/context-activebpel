// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/addressing/pdef/AeDefaultPartnerAddressingProvider.java,v 1.6 2005/06/13 17:54:0
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
package org.activebpel.rt.bpel.server.addressing.pdef;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.activebpel.rt.bpel.server.deploy.IAeDeploymentId;

/**
 * Default partner addressing provider.
 * Maintains mappings in memory.
 * Also provides access to admin info.
 */
public class AeDefaultPartnerAddressingProvider 
   implements IAePartnerAddressingProvider
{
   
   /** Map of principals to partner addresses */
   protected Map mCache;
   
   /**
    * Default constructor.
    */
   public AeDefaultPartnerAddressingProvider()
   {
      mCache = new HashMap();
   }

   /**
    * @see org.activebpel.rt.bpel.server.addressing.pdef.IAePartnerAddressingProvider#addAddresses(org.activebpel.rt.bpel.server.deploy.IAeDeploymentId, java.lang.String, org.activebpel.rt.bpel.server.addressing.pdef.IAePartnerDefInfo)
    */
   public synchronized void addAddresses( IAeDeploymentId  aDeploymentId, 
      String aDeploymentLocation, IAePartnerDefInfo aInfo )
   {
      AePartnerAddressBook book = (AePartnerAddressBook)getCache().get( aInfo.getPrincipal() );
      if( book == null )
      {
         book = new AePartnerAddressBook(aInfo.getPrincipal());
         getCache().put( book.getPrincipal(), book );      
      }
      book.mergeAddresses( aDeploymentId, aDeploymentLocation, aInfo );
   }

   /**
    * @see org.activebpel.rt.bpel.server.addressing.pdef.IAePartnerAddressingProvider#getAddressBook(java.lang.String)
    */
   public IAePartnerAddressBook getAddressBook(String aPrincipal)
   {
      return (IAePartnerAddressBook)getCache().get( aPrincipal );
   }

   /**
    * @see org.activebpel.rt.bpel.server.addressing.pdef.IAePartnerAddressingProvider#removeAddresses(org.activebpel.rt.bpel.server.deploy.IAeDeploymentId)
    */
   public synchronized void removeAddresses(IAeDeploymentId aDeploymentId)
   {
      List deleteQueue = new ArrayList();
      for (Iterator iter = getCache().entrySet().iterator(); iter.hasNext();)
      {
         Map.Entry entry = (Map.Entry) iter.next();
         AePartnerAddressBook book = (AePartnerAddressBook)entry.getValue();
         book.remove(aDeploymentId);
         if( book.isOkToDelete() )
         {
            deleteQueue.add( book.getPrincipal() );
         }
      }
      
      for (Iterator iter = deleteQueue.iterator(); iter.hasNext();)
      {
         getCache().remove( iter.next() );         
      }
   }
   
   /**
    * Accessor for the mappings.
    */
   protected Map getCache()
   {
      return mCache;
   }

   /**
    * @see org.activebpel.rt.bpel.server.addressing.pdef.IAePartnerAddressingAdmin#getPrincipals()
    */
   public String[] getPrincipals()
   {
      return (String[]) (new ArrayList(mCache.keySet()).toArray(new String[]{}));
   }

   /**
    * @see org.activebpel.rt.bpel.server.addressing.pdef.IAePartnerAddressingAdmin#getPartnerInfo(java.lang.String)
    */
   public IAePartnerDefInfo getPartnerInfo(String aPrincipal)
   {
      AePartnerDefInfo info = new AePartnerDefInfo(aPrincipal);
      AePartnerAddressBook book = (AePartnerAddressBook)mCache.get( aPrincipal );
      if( book != null )
      {
         for (Iterator iter = book.getMappings().values().iterator(); iter.hasNext();)
         {
            AePartnerAddressRef ref = (AePartnerAddressRef)iter.next();
            info.addInfo( ref.getPartnerLinkType(), ref.getRoleName(), ref.getEndpointRef() );
         }
      }
      return info;
   }
}
