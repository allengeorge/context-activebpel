//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis/src/org/activebpel/rt/axis/AeWsdlReferenceTracker.java,v 1.3 2006/08/04 18:05:5
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
package org.activebpel.rt.axis;

import java.util.HashMap;
import java.util.Map;

import org.activebpel.rt.axis.IAeWsdlReference;
import org.activebpel.rt.bpel.server.engine.AeEngineFactory;

/**
 * Adds <code>IAeWsdlReference</code> instances as <code>IAeCatalogListener</code>
 * objects to the wsdl catalog on registration.  Removes <code>IAeWsdlReference</code>
 * instances from the wsdl catalog on unregistration (should be called when the
 * Axis service is removed to ensure that the <code>IAeWsdlReference</code> is
 * eligible for garbage collection). 
 */
public class AeWsdlReferenceTracker
{
   
   /** Map of service names to <code>IAeWsdlReference</code> objects. */
   private static Map mReferences = new HashMap();
   
   /**
    * Register the <code>IAeWsdlReference</code> as a <code>IAeCatalogListener</code> with
    * the catalog.
    * @param aServiceName
    * @param aReference
    */
   public static void registerReference( String aServiceName, IAeWsdlReference aReference )
   {
      unregisterReference( aServiceName );
      mReferences.put( aServiceName, aReference );
      AeEngineFactory.getCatalog().addCatalogListener( aReference );

   }
   
   /**
    * Remove the <code>IAeWsdlReference</code> mapped to the given service name
    * as a <code>IAeCatalogListener</code>.
    * @param aServiceName
    */
   public static void unregisterReference( String aServiceName )
   {
      IAeWsdlReference wsdlRef = (IAeWsdlReference)mReferences.remove( aServiceName );
      if( wsdlRef != null )
      {
         AeEngineFactory.getCatalog().removeCatalogListener( wsdlRef );
      }
   }
}
