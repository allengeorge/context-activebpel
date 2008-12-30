// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/catalog/IAeCatalogEventDispatcher.java,v 1.1 2006/07/18 20:05:3
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
package org.activebpel.rt.bpel.server.catalog;

/**
 * Provides convenience methods for dispatching an <code>AeCatalogEvent</code> to the
 * appropriate IAeCatalogListener handler methods.
 */
public interface IAeCatalogEventDispatcher
{
   /** sends the event to the onDeployment handler method */
   public static final IAeCatalogEventDispatcher DEPLOY_SENDER = new IAeCatalogEventDispatcher()
   {
      public void dispatch( IAeCatalogListener aListener, AeCatalogEvent aEvent )
      {
         aListener.onDeployment( aEvent );
      }
   };
   
   /** sends the event to the onRemoval handler method */
   public static final IAeCatalogEventDispatcher REMOVE_SENDER = new IAeCatalogEventDispatcher()
   {
      public void dispatch( IAeCatalogListener aListener, AeCatalogEvent aEvent )
      {
         aListener.onRemoval( aEvent );
      }
   };
   
   /** sends the event to the onDuplicateDeployment handler method */
   public static final IAeCatalogEventDispatcher WARNING_SENDER = new IAeCatalogEventDispatcher()
   {
      public void dispatch( IAeCatalogListener aListener, AeCatalogEvent aEvent )
      {
         aListener.onDuplicateDeployment( aEvent );
      }
   };


   /**
    * Dispatch the event to the appropriate listener method.
    * @param aListener
    * @param aEvent
    */
   public void dispatch( IAeCatalogListener aListener, AeCatalogEvent aEvent );
}
