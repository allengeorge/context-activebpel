//$Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/util/AeStaticConstantsMap.java,v 1.1 2005/02/19 00:28:5
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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.activebpel.rt.AeException;
import org.activebpel.rt.AeMessages;

/**
 * Implements a map between the names and values of the static fields in a Java
 * class or interface.
 */
public class AeStaticConstantsMap
{
   /** The class or interface whose static fields will be mapped. */
   private final Class mClass;

   /** Whether {@link #init()} called yet. */
   private boolean mInited = false;

   /** Map from the names of the class's static fields to their values. */
   private Map mNamesToValuesMap;

   /** Map from the values of the class's static fields to their names. */
   private Map mValuesToNamesMap;

   /**
    * Constructs a map between the names and values of the static fields in the
    * specified class or interface.
    */
   public AeStaticConstantsMap(Class aClass)
   {
      mClass = aClass;
   }

   /**
    * Makes sure that the internal maps are initialized.
    */
   protected void init()
   {
      if (!mInited)
      {
         mInited = true;

         Field[] fields = mClass.getDeclaredFields();
         mNamesToValuesMap = new HashMap();
         mValuesToNamesMap = new HashMap();

         for (int i = 0; i < fields.length; ++i)
         {
            Field field = fields[i];

            if (Modifier.isStatic(field.getModifiers()))
            {
               try
               {
                  String name = field.getName();
                  Object value = field.get(null);

                  mNamesToValuesMap.put(name, value);
                  mValuesToNamesMap.put(value, name);
               }
               catch (IllegalArgumentException e)
               {
                  AeException.logError(e, AeMessages.getString("AeStaticConstantsMap.ERROR_0") + field); //$NON-NLS-1$
               }
               catch (IllegalAccessException e)
               {
                  AeException.logError(e, AeMessages.getString("AeStaticConstantsMap.ERROR_0") + field); //$NON-NLS-1$
               }
            }
         }
      }
   }

   /**
    * Returns the name of the static field with the specified value.
    */
   public String getName(Object aValue)
   {
      init();
      return (String) mValuesToNamesMap.get(aValue);
   }

   /**
    * Returns the value of the static field with the specified name.
    */
   public Object getValue(Object aName)
   {
      init();
      return mNamesToValuesMap.get(aName);
   }
}
