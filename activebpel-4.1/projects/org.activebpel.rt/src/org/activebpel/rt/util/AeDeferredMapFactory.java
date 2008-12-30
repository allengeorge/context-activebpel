//$Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/util/AeDeferredMapFactory.java,v 1.2 2006/03/10 21:45:4
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
package org.activebpel.rt.util; 

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * A base class for building a {@link java.util.Map} whose contents won't be loaded
 * until the map is accessed. 
 */
public abstract class AeDeferredMapFactory implements InvocationHandler
{
   /** init flag to avoid building the map multiple times */
   private boolean mInitialized;
   /** map that we're delegating to */
   private Map mMap;
   
   /**
    * Method to be overridden by subclass to return our delegate map.
    */
   protected abstract Map buildMap();
   
   /**
    * Creates a proxy for the map. Invoking any method on the proxy will cause
    * the delegate map to get built.
    */
   public Map getMapProxy()
   {
      return (Map) Proxy.newProxyInstance(
            getClass().getClassLoader(),
            new Class[] { Map.class },
            this );
   }
   
   /**
    * Override the invoke method to ensure that our delegate map gets built.
    * 
    * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
    */
   public Object invoke(Object aProxy, Method aMethod, Object[] args)
         throws Throwable
   {
      init();
      return aMethod.invoke(getMap(), args);
   }
   
   /**
    * If we're not initialized, then <code>buildMap()</code> is called and the 
    * resulting map becomes our delegate. 
    */
   protected void init()
   {
      if (!isInitialized())
      {
         setMap(buildMap());
         setInitialized(true);
      }
   }
   
   /**
    * @return Returns the initialized.
    */
   protected boolean isInitialized()
   {
      return mInitialized;
   }

   /**
    * @param aInitialized The initialized to set.
    */
   protected void setInitialized(boolean aInitialized)
   {
      mInitialized = aInitialized;
   }
   
   /**
    * @return Returns the map.
    */
   protected Map getMap()
   {
      return mMap;
   }

   /**
    * @param aMap The map to set.
    */
   protected void setMap(Map aMap)
   {
      mMap = aMap;
   }
}
 
