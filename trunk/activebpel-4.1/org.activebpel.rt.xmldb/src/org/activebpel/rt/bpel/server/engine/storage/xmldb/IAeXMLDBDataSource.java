//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.xmldb/src/org/activebpel/rt/bpel/server/engine/storage/xmldb/IAeXMLDBDataSource.java,v 1.1 2007/08/17 00:40:5
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

import org.activebpel.rt.AeException;

/**
 * An interface that defines the set of methods needed to give access to the XMLDB database.
 */
public interface IAeXMLDBDataSource
{
   /**
    * Gets a new auto-commit Exist connection.
    * 
    * @throws AeException
    */
   public IAeXMLDBConnection getNewConnection() throws AeXMLDBException;

   /**
    * Gets a new Exist connection.
    * 
    * @throws AeException
    */
   public IAeXMLDBConnection getNewConnection(boolean aAutoCommit) throws AeXMLDBException;

   /**
    * Returns the native data source for this database.  The return type 
    * will be implementation dependent.
    */
   public Object getNativeDataSource();

   /**
    * Destroys the data source, cleaning up any resouces associated with it.
    * This is really only useful for testing purposes where the data source is
    * destroyed between each test run and is only implemented for a pooled data 
    * source. 
    */
   public void destroy();
}
