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

import java.util.Date;

import org.activebpel.rt.bpel.impl.fastdom.AeFastDocument;
import org.activebpel.rt.bpel.server.engine.storage.AeStorageException;
import org.activebpel.rt.bpel.server.engine.storage.IAeLocationVersionSet;
import org.activebpel.rt.util.AeLongSet;
import org.w3c.dom.Document;

/**
 * A process state connection delegate. This interface defines methods that the delegating process state
 * connection storage will call in order to store/read date in the underlying database.
 */
public interface IAeProcessStateConnectionProvider extends IAeStorageProvider
{
   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.IAeProcessStateConnection#close()
    */
   public void close() throws AeStorageException;

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.IAeProcessStateConnection#commit()
    */
   public void commit() throws AeStorageException;

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.IAeProcessStateConnection#getProcessDocument()
    */
   public Document getProcessDocument() throws AeStorageException;

   /**
    * Gets a set of location id/version number tuples.
    * 
    * @throws AeStorageException
    */
   public IAeLocationVersionSet getProcessVariables() throws AeStorageException;
   
   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.IAeProcessStateConnection#getVariableDocument(long, int)
    */
   public Document getVariableDocument(long aLocationId, int aVersionNumber) throws AeStorageException;

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.IAeProcessStateConnection#removeJournalEntries(org.activebpel.rt.util.AeLongSet)
    */
   public void removeJournalEntries(AeLongSet aJournalIds) throws AeStorageException;

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.IAeProcessStateConnection#rollback()
    */
   public void rollback() throws AeStorageException;

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.IAeProcessStateConnection#saveProcess(org.activebpel.rt.bpel.impl.fastdom.AeFastDocument, int, int,  java.util.Date, java.util.Date, int)
    */
   public void saveProcess(AeFastDocument aDocument, int aProcessState, int aProcessStateReason,
         Date aStartDate, Date aEndDate, int aPendingInvokesCount) throws AeStorageException;
   
   /**
    * Saves a single variable in the database.
    * 
    * @param aDocument
    * @param aLocationId
    * @param aVersionNumber
    * @throws AeStorageException
    * @see org.activebpel.rt.bpel.server.engine.storage.IAeProcessStateConnection#saveVariable(org.activebpel.rt.bpel.IAeBusinessProcess, org.activebpel.rt.bpel.IAeVariable, org.activebpel.rt.bpel.impl.fastdom.AeFastDocument)
    */
   public void saveVariable(AeFastDocument aDocument, int aLocationId, int aVersionNumber)
         throws AeStorageException;

   /**
    * Removes a single stored variable from the database.
    * 
    * @param aLocationId
    * @param aVersionNumber
    * @throws AeStorageException
    * @see org.activebpel.rt.bpel.server.engine.storage.IAeProcessStateConnection#trimStoredVariables(org.activebpel.rt.bpel.server.engine.storage.IAeLocationVersionSet)
    */
   public void trimStoredVariable(long aLocationId, int aVersionNumber) throws AeStorageException;

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.IAeProcessStateConnection#saveLog(org.activebpel.rt.bpel.server.logging.IAeProcessLogEntry)
    */
   public void saveLog(String aLogString, int aLineCount) throws AeStorageException;
}
