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
package org.activebpel.rt.bpel.server.engine.storage.providers;

import java.util.Map;

import org.activebpel.rt.bpel.server.engine.storage.AeStorageException;

/**
 * A URN storage delegate. This interface defines methods that the delegating URN storage will call in order
 * to store/read date in the underlying database.
 */
public interface IAeURNStorageProvider extends IAeStorageProvider
{
   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.IAeURNStorage#getMappings()
    */
   public Map getMappings() throws AeStorageException;
   
   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.IAeURNStorage#addMapping(java.lang.String, java.lang.String)
    */
   public void addMapping(String aURN, String aURL, IAeStorageConnection aConnection) throws AeStorageException;

   /**
    * Removes a single URN mapping from the database.
    * 
    * @see org.activebpel.rt.bpel.server.engine.storage.IAeURNStorage#removeMappings(java.lang.String[])
    */
   public void removeMapping(String aURN, IAeStorageConnection aConnection) throws AeStorageException;
}
