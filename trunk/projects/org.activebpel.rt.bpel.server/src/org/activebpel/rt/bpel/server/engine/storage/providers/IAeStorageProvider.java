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

import org.activebpel.rt.bpel.server.engine.storage.AeStorageException;

/**
 * The base interface for all storage delegates.  This interface will be extended by the more
 * concrete storage delegates, such as Queue, Process, etc.
 */
public interface IAeStorageProvider
{
   /**
    * Gets an auto-commit DB connection.
    */
   public IAeStorageConnection getDBConnection() throws AeStorageException;

   /**
    * Gets a commit control DB connection.
    */
   public IAeStorageConnection getCommitControlDBConnection() throws AeStorageException;

   /**
    * Gets a transaction DB connection.
    */
   public IAeStorageConnection getTxCommitControlDBConnection() throws AeStorageException;
}
