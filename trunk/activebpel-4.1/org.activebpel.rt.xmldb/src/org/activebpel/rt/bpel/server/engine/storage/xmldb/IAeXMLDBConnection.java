// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.xmldb/src/org/activebpel/rt/bpel/server/engine/storage/xmldb/IAeXMLDBConnection.java,v 1.1 2007/08/17 00:40:5
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
 * A connection to an XML database.  This is equivalent to a jdbc connection.
 */
public interface IAeXMLDBConnection
{
   /**
    * Closes the connection.
    */
   public void close();

   /**
    * Commit the changes.
    *
    * @throws AeXMLDBException
    */
   public void commit() throws AeXMLDBException;

   /**
    * Roll-back the connection.
    *
    * @throws AeXMLDBException
    */
   public void rollback() throws AeXMLDBException;

   /**
    * Returns the native connection for this DB connection.  For example,
    * this would return a TConnection for a Tamino implementation.
    */
   public Object getNativeConnection();
}
