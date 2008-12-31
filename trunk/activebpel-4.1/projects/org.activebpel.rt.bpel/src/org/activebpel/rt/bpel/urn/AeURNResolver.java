//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/urn/AeURNResolver.java,v 1.2 2007/06/01 17:31:1
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
package org.activebpel.rt.bpel.urn; 

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.activebpel.rt.util.AeDeferredMapFactory;
import org.activebpel.rt.util.AeUtil;

/**
 * Resolves urns to urls. 
 */
public class AeURNResolver implements IAeURNResolver
{
   /** Map of URN to URL values */
   private Map mMap = new HashMap();

   /**
    * @see org.activebpel.rt.bpel.urn.IAeURNResolver#getMappings()
    */
   public Map getMappings()
   {
      return new HashMap(getMap());
   }

   /**
    * @see org.activebpel.rt.bpel.urn.IAeURNResolver#getURL(java.lang.String)
    */
   public String getURL(String aURN)
   {
      String url = getURL(aURN, aURN);
      if (AeUtil.isNullOrEmpty(url))
      {
         return aURN;
      }
      return url;
   }

   /**
    * @see org.activebpel.rt.bpel.urn.IAeURNResolver#addMapping(java.lang.String, java.lang.String)
    */
   public synchronized void addMapping(String aURN, String aURL)
   {
      Map map = getMappings();
      map.put(aURN, aURL);
      setMap(map);
   }

   /**
    * @see org.activebpel.rt.bpel.urn.IAeURNResolver#hasMapping(java.lang.String)
    */
   public boolean hasMapping(String aURN)
   {
      return getMap().containsKey(aURN);
   }

   /**
    * @see org.activebpel.rt.bpel.urn.IAeURNResolver#removeMappings(java.lang.String[])
    */
   public synchronized void removeMappings(String[] aURNArray)
   {
      Map map = getMappings();
      for (int i = 0; i < aURNArray.length; i++)
      {
         map.remove(aURNArray[i]);
      }
      setMap(map);
   }

   /**
    * @param aOriginalURN
    * @param aPartialURN
    */
   protected String getURL(String aOriginalURN, String aPartialURN)
   {
      String result = (String) getMap().get(aPartialURN);
      if (result == null)
      {
         // there was no match, try again by stripping off the last segment of the URN
         int offset = aPartialURN.lastIndexOf(':');
         if (offset != -1)
         {
            return getURL(aOriginalURN, aPartialURN.substring(0, offset));
         }
      }
      else
      {
         AeURNTokenParsingMapFactory deferredMapFactory = new AeURNTokenParsingMapFactory(aOriginalURN);
         result = AeUtil.replacePropertyVars(result, deferredMapFactory.getMapProxy());
      }
      
      return result;
   }

   /**
    * Implementation of the deferred map that loads the map with the parsed URN
    * value as soon as the map is accessed.
    */
   protected static class AeURNTokenParsingMapFactory extends AeDeferredMapFactory
   {
      /** urn we need to parse */
      private String mURN;
      
      /**
       * @param aURN
       */
      public AeURNTokenParsingMapFactory(String aURN)
      {
         mURN = aURN;
      }
      
      /**
       * Returns a new Map that contains the "urn." + segmentoffset to the
       * value of the segment of the URN. 
       * 
       * @see org.activebpel.rt.util.AeDeferredMapFactory#buildMap()
       */
      protected Map buildMap()
      {
         StringTokenizer stoker = new StringTokenizer(mURN, ":"); //$NON-NLS-1$
         Map map = new HashMap();
         int counter = 1;
         while(stoker.hasMoreTokens())
         {
            map.put("urn." + counter++, stoker.nextToken()); //$NON-NLS-1$
         }
         return map;
      }
   }

   /**
    * Getter for the map
    */
   protected Map getMap()
   {
      return mMap;
   }

   /**
    * Setter for the map.
    * 
    * @param aMap
    */
   protected void setMap(Map aMap)
   {
      mMap = aMap;
   }
}
 
