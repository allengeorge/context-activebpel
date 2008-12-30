//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.xmldb/src/org/activebpel/rt/bpel/server/engine/storage/xmldb/AeXMLDBDataSource.java,v 1.1 2007/08/17 00:40:5
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

import java.util.Map;

import org.activebpel.rt.AeException;

/**
 * The XMLDB data source.
 */
public abstract class AeXMLDBDataSource implements IAeXMLDBDataSource
{
   /**
    * Constructs a data source.
    *
    * @param aConfig The engine configuration map for this data source.
    */
   public AeXMLDBDataSource(Map aConfig) throws AeException
   {
   }

   /**
    * Gets a new XMLDB connection.
    * 
    * @throws AeException
    */
   public abstract IAeXMLDBConnection getNewConnection() throws AeXMLDBException;
}
