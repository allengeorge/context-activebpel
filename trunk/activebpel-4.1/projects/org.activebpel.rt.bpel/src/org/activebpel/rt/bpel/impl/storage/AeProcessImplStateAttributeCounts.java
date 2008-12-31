// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/storage/AeProcessImplStateAttributeCounts.java,v 1.2 2005/02/01 19:52:5
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
package org.activebpel.rt.bpel.impl.storage;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Counts occurrences of attribute values used for persistence by attribute
 * name. The counts can be used to determine which values should be default
 * values in <code>AeProcessImplStateAttributeDefaults</code>.
 */
public abstract class AeProcessImplStateAttributeCounts
{
   private static final Integer ONE = new Integer(1);

   /** <code>true</code> to enable counts. */
   private static final boolean ENABLED = false;

   /** Maximum number of distinct values to track. */
   private static final int MAX_VALUES = 4;

   /** Singleton instance. */
   private static final AeProcessImplStateAttributeCounts sCounts =
      ENABLED ? (AeProcessImplStateAttributeCounts) new AeRealCounts() : new AeFakeCounts();

   /**
    * Private base constructor for singleton instance.
    */
   private AeProcessImplStateAttributeCounts()
   {
   }

   /**
    * Returns singleton instance.
    */
   public static AeProcessImplStateAttributeCounts getCounts()
   {
      return sCounts;
   }

   /**
    * Increments the count for a specified attribute value.
    */
   public abstract void incrementCount(String aName, String aValue);

   /**
    * Prints the counts.
    */
   public abstract void printCounts();

   /**
    * Implements fake, do-nothing counts.
    */
   protected static class AeFakeCounts extends AeProcessImplStateAttributeCounts
   {
      /**
       * @see org.activebpel.rt.bpel.impl.storage.AeProcessImplStateAttributeCounts#incrementCount(java.lang.String, java.lang.String)
       */
      public void incrementCount(String aName, String aValue)
      {
      }

      /**
       * @see org.activebpel.rt.bpel.impl.storage.AeProcessImplStateAttributeCounts#printCounts()
       */
      public void printCounts()
      {
      }
   }

   /**
    * Implements real counts.
    */
   protected static class AeRealCounts extends AeProcessImplStateAttributeCounts
   {
      /** Maps attribute names to the map of values for that name. */
      private final Map mNamesMap = new HashMap();

      /**
       * @see org.activebpel.rt.bpel.impl.storage.AeProcessImplStateAttributeCounts#incrementCount(java.lang.String, java.lang.String)
       */
      public void incrementCount(String aName, String aValue)
      {
         Map valuesMap;

         synchronized (mNamesMap)
         {
            // Get the values for the name.
            valuesMap = (Map) mNamesMap.get(aName);

            if (valuesMap == null)
            {
               // Haven't seen this name yet. Create a new values map.
               valuesMap = new HashMap();
               mNamesMap.put(aName, valuesMap);
            }
         }

         synchronized (valuesMap)
         {
            // Get the old count.
            Integer count = (Integer) valuesMap.get(aValue);

            if (count != null)
            {
               // Store the new count.
               valuesMap.put(aValue, new Integer(count.intValue() + 1));
            }
            else if (valuesMap.size() < MAX_VALUES)
            {
               // Start a new count.
               valuesMap.put(aValue, ONE);
            }
         }
      }

      /**
       * @see org.activebpel.rt.bpel.impl.storage.AeProcessImplStateAttributeCounts#printCounts()
       */
      public void printCounts()
      {
         Map sortedNamesMap;

         synchronized (mNamesMap)
         {
            sortedNamesMap = new TreeMap(mNamesMap);
         }

         for (Iterator i = sortedNamesMap.entrySet().iterator(); i.hasNext(); )
         {
            Map.Entry entry = (Map.Entry) i.next();
            String name = (String) entry.getKey();
            Map valuesMap = (Map) entry.getValue();

            printCounts(name, valuesMap);
         }
      }

      /**
       * Prints the counts for the specified name from the specified
       * <code>Map</code> of values to counts.
       */
      public void printCounts(String aName, Map aValuesMap)
      {
         Map sortedValuesMap = new TreeMap(new AeReverseCountComparator(aValuesMap));

         synchronized (aValuesMap)
         {
            sortedValuesMap.putAll(aValuesMap);
         }

         for (Iterator j = sortedValuesMap.entrySet().iterator(); j.hasNext(); )
         {
            Map.Entry entry = (Map.Entry) j.next();
            String value = (String) entry.getKey();
            Integer count = (Integer) entry.getValue();

            System.out.println("count[" + aName + "][" + value + "] = " + count); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
         }
      }

      /**
       * Implements a <code>Comparator</code> that compares values in reverse
       * order of associated counts.
       */
      protected static class AeReverseCountComparator implements Comparator
      {
         /** <code>Map</code> from values to counts. */
         private final Map mValuesMap;

         /**
          * Constructs reverse count comparator with the specified
          * <code>Map</code> from values to counts.
          *
          * @param aValuesMap
          */
         public AeReverseCountComparator(Map aValuesMap)
         {
            mValuesMap = aValuesMap;
         }

         /**
          * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
          */
         public int compare(Object o1, Object o2)
         {
            synchronized (mValuesMap)
            {
               int count1 = ((Integer) mValuesMap.get(o1)).intValue();
               int count2 = ((Integer) mValuesMap.get(o2)).intValue();
               return count2 - count1;
            }
         }
      }
   }
}
