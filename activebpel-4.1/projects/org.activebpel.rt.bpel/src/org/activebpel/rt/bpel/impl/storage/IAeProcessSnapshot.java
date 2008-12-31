// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/storage/IAeProcessSnapshot.java,v 1.2 2005/12/29 20:01:3
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
package org.activebpel.rt.bpel.impl.storage;

import java.util.Set;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeVariable;
import org.activebpel.rt.bpel.impl.fastdom.AeFastDocument;
import org.w3c.dom.Document;

/**
 * Defines the interface for process snapshots that contain all variables and
 * correlation sets that are live for normal or compensation processing.
 */
public interface IAeProcessSnapshot
{
   /**
    * Returns a <code>Set</code> containing the location paths for all
    * correlation sets.
    */
   public Set getCorrelationSetLocationPaths();

   /**
    * Returns a <code>Set</code> containing the version numbers for all
    * correlation sets with the specified location path.
    */
   public Set getCorrelationSetVersionNumbers(String aLocationPath);

   /**
    * Returns a <code>Set</code> containing the pending invoke instances for
    * the process--those invoke instances that are still in the executing
    * state.
    */
   public Set getPendingInvokes();

   /**
    * Returns a <code>Set</code> containing the location paths for all
    * variables.
    */
   public Set getVariableLocationPaths();

   /**
    * Returns a <code>Set</code> containing the version numbers for all
    * variables with the specified location path.
    */
   public Set getVariableVersionNumbers(String aLocationPath);

   /**
    * Returns the serialization of the correlation set instance corresponding
    * to the specified location path and version number.
    *
    * @param aLocationPath
    * @param aVersionNumber
    */
   public AeFastDocument serializeCorrelationSet(String aLocationPath, int aVersionNumber) throws AeBusinessProcessException;

   /**
    * Returns the serialization of the process.
    *
    * @param aForPersistence <code>true</code> to serialize for persistence.
    */
   public AeFastDocument serializeProcess(boolean aForPersistence) throws AeBusinessProcessException;

   /**
    * Returns the serialization of the variable instance corresponding to the
    * specified location path and version number.
    *
    * @param aVariable
    */
   public AeFastDocument serializeVariable(IAeVariable aVariable) throws AeBusinessProcessException;
   
   /**
    * Gets the variable instance corresponding to the specified location path 
    * and version number.
    * 
    * @param aLocationPath
    * @param aVersionNumber
    * @throws AeBusinessProcessException
    */
   public IAeVariable getVariable(String aLocationPath, int aVersionNumber) throws AeBusinessProcessException;

   /**
    * Sets a correlation set from a previously serialized correlation set
    * document.
    *
    * @param aLocationPath
    * @param aVersionNumber
    * @param aDocument
    * @throws AeBusinessProcessException
    */
   public void setCorrelationSetData(String aLocationPath, int aVersionNumber, Document aDocument) throws AeBusinessProcessException;

   /**
    * Sets a variable from a previously serialized variable document.
    *
    * @param aLocationPath
    * @param aVersionNumber
    * @param aDocument
    * @throws AeBusinessProcessException
    */
   public void setVariableData(String aLocationPath, int aVersionNumber, Document aDocument) throws AeBusinessProcessException;
}
