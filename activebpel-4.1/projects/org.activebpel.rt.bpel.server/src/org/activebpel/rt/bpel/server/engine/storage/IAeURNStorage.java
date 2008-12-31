//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/IAeURNStorage.java,v 1.2 2007/04/03 20:54:3
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
package org.activebpel.rt.bpel.server.engine.storage; 

import java.util.Map;


/**
 * Defines the methods for storing and retrieving URN mappings. The interface resembles
 * a Map.   
 */
public interface IAeURNStorage extends IAeStorage
{
   /**
    * Gets all of the mappings for URN to URL
    */
   public Map getMappings() throws AeStorageException;
   
   /**
    * Adds the mapping. If the mapping already exists in the db then it is updated.
    * 
    * @param aURN
    * @param aURL
    */
   public void addMapping(String aURN, String aURL) throws AeStorageException;
   
   /**
    * Removes the mapping. 
    * 
    * @param aURNArray
    */
   public void removeMappings(String[] aURNArray) throws AeStorageException;
}
 
