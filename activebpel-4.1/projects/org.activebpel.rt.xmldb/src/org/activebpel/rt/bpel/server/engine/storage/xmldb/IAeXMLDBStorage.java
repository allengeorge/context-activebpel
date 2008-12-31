//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.xmldb/src/org/activebpel/rt/bpel/server/engine/storage/xmldb/IAeXMLDBStorage.java,v 1.1 2007/08/17 00:40:5
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

/**
 * Interface for a XMLDB storage object.  All XMLDB storages should implement this interface.
 */
public interface IAeXMLDBStorage
{
   /** Represents a NULL xsd:integer. */
   public static final IAeXMLDBNull NULL_INTEGER = new IAeXMLDBNull()
   {
      /**
       * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBNull#getValue()
       */
      public String getValue()
      {
         return "0"; //$NON-NLS-1$
      }
   };
   
   /** Represents a NULL xsd:int. */
   public static final IAeXMLDBNull NULL_INT = new IAeXMLDBNull()
   {
      /**
       * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBNull#getValue()
       */
      public String getValue()
      {
         return "0"; //$NON-NLS-1$
      }
   };

   /** Represents a NULL xsd:string. */
   public static final IAeXMLDBNull NULL_STRING = new IAeXMLDBNull()
   {
      /**
       * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBNull#getValue()
       */
      public String getValue()
      {
         return ""; //$NON-NLS-1$
      }
   };

   /** Represents a NULL xsd:dateTime. */
   public static final IAeXMLDBNull NULL_DATETIME = new IAeXMLDBNull()
   {
      /**
       * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBNull#getValue()
       */
      public String getValue()
      {
         return "1970-01-01T12:00:00Z"; //$NON-NLS-1$
      }
   };

   /** Represents a NULL ae:AeAnyNullable typically used to store XML documents in XMLDB. */
   public static final IAeXMLDBNull NULL_DOCUMENT = new IAeXMLDBNull()
   {
      /**
       * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBNull#getValue()
       */
      public String getValue()
      {
         return null;
      }
   };

   /** Represents a NULL xsd:double. */
   public static final IAeXMLDBNull NULL_DOUBLE = new IAeXMLDBNull()
   {
      /**
       * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBNull#getValue()
       */
      public String getValue()
      {
         return "0.0"; //$NON-NLS-1$
      }
   };

}
