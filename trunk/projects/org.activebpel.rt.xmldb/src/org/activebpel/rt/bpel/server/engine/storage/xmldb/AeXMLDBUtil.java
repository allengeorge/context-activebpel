//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.xmldb/src/org/activebpel/rt/bpel/server/engine/storage/xmldb/AeXMLDBUtil.java,v 1.1 2007/08/17 00:40:5
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
package org.activebpel.rt.bpel.server.engine.storage.xmldb;

import java.util.Date;

import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.xml.schema.AeSchemaDateTime;

/**
 * A class with some static methods to do some standard XMLDB related operations.
 */
public class AeXMLDBUtil
{
   /**
    * Returns either a AeSchemaDateTime for the given date or the special NULL object.
    * 
    * @param aDate
    */
   public static Object getDateTimeOrNull(Date aDate)
   {
      if (aDate == null)
      {
         return IAeXMLDBStorage.NULL_DATETIME;
      }
      else
      {
         return new AeSchemaDateTime(aDate);
      }
   }
   
   /**
    * Returns a XMLDB safe value. If the string is not <code>null</code>, then this method
    * returns the string unchanged. Otherwise, <code>IAeXMLDBStorage.NULL_STRING</code> is
    * returned.
    * @param aString
    * @return The string if is not null, otherwise returns <code>IAeXMLDBStorage.NULL_STRING</code>.
    */
   public static Object getStringOrNull(String aString)
   {
      if (aString != null)
      {
         return aString;
      }
      else
      {
         return IAeXMLDBStorage.NULL_STRING;
      }
   }   
   
   /**
    * Returns a XMLDB safe value. If the string is not <code>null</code>, then this method
    * returns the string unchanged. Otherwise, <code>IAeXMLDBStorage.NULL_STRING</code> is
    * returned.
    * @param aString
    * @return The string if is not null, otherwise returns <code>IAeXMLDBStorage.NULL_STRING</code>.
    */
   public static Object getStringOrNullNoEmpty(String aString)
   {
      if (AeUtil.notNullOrEmpty(aString))
      {
         return aString;
      }
      else
      {
         return IAeXMLDBStorage.NULL_STRING;
      }
   }   
}
