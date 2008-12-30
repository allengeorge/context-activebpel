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
package org.activebpel.rt.bpel.config;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.activebpel.rt.util.AeUtil;

/**
 * Utility class that compares the source and target maps and adds
 * any diffs to the dif map.
 */
public class AeConfigDifUtil
{
   /**
    * Utility method to extract only the diff between the two maps
    * @param aSource The original map.
    * @param aTarget The map that may have changed.
    * @return The resulting diffs.
    */
   public static Map compare( Map aSource, Map aTarget )
   {
      Map diffs = new HashMap();
      compareInternal( "", aSource, aTarget, diffs ); //$NON-NLS-1$
      return diffs;
   }
   
   /**
    * Recursive compare that continues the compareInteral calls if the 
    * values of the two maps are maps and they are different.
    * @param aPath Used to build a composite key for map of map values that have changed.
    * @param aSource The source map.
    * @param aTarget The target map.
    * @param aDifs Map of discovered diffs.
    */
   protected static void compareInternal( String aPath, Map aSource, Map aTarget, Map aDifs )
   {
      /////////////////////////////////////////////////////////////////////
      // work off of the target map as the source map may not contain
      // all of the possible properties
      // ie: there is no param for logging in the config file 
      /////////////////////////////////////////////////////////////////////
      for( Iterator iter = aTarget.entrySet().iterator(); iter.hasNext(); )
      {
         Map.Entry entry = (Map.Entry)iter.next();
         String key = (String)entry.getKey();
         Object value = entry.getValue();
         
         Object sourceValue = aSource.get( key );
         if( sourceValue == null || !sourceValue.equals(value) )
         {
            String newPath=""; //$NON-NLS-1$
            if( AeUtil.isNullOrEmpty(aPath) )
            {
               newPath=key;
            }
            else
            {
               newPath = aPath+"/"+key; //$NON-NLS-1$
            }
            
            if( value instanceof String )
            {
               aDifs.put( newPath, value );
            }
            else if( value instanceof Map )
            {
               // if this layer did not exist (eg: function context) in the original config then create an empty map for compare
               if( sourceValue == null )
               {
                  sourceValue = new HashMap();
               }
               compareInternal( newPath, (Map)sourceValue, (Map)value, aDifs );
            }
         }
      }
   }
}
