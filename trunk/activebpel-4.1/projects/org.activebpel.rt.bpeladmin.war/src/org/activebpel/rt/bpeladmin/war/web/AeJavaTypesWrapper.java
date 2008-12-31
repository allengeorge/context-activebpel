// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/web/AeJavaTypesWrapper.java,v 1.2 2005/01/14 16:30:3
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

import java.io.Serializable;

/**
 * Exposes well known java types via bean propery accessors.
 * Current impl supports only strings.
 */
public class AeJavaTypesWrapper implements Serializable
{
   /** Null types wrapper. */
   private static final AeJavaTypesWrapper EMPTY = new AeJavaTypesWrapper(""); //$NON-NLS-1$
   
   /** Property value. */
   protected String mString;
   
   /**
    * Utility method for wrapping a string array.
    * @param aArray
    */
   public static AeJavaTypesWrapper[] wrap( String[] aArray )
   {
      if( aArray == null )
      {
         return new AeJavaTypesWrapper[]{EMPTY};
      }
      else
      {
         AeJavaTypesWrapper[] wrapper = new AeJavaTypesWrapper[aArray.length];
         for( int i = 0; i < aArray.length; i++ )
         {
            wrapper[i] = new AeJavaTypesWrapper( aArray[i] );
         }
         return wrapper;
      }
   }

   /**
    * Constructor.
    * @param aString 
    */
   public AeJavaTypesWrapper( String aString )
   {
      mString = aString;
   }
   
   /**
    * Returns the string value of the property.
    */
   public String getString()
   {
      return mString;
   }
}
