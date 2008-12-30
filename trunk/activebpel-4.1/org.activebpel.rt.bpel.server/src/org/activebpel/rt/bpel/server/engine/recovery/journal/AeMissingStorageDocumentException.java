// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/recovery/journal/AeMissingStorageDocumentException.java,v 1.1 2005/07/12 00:25:2
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
package org.activebpel.rt.bpel.server.engine.recovery.journal;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.server.AeMessages;

/**
 * Implements an exception that reports a missing storage document for a
 * journal entry.
 */
public class AeMissingStorageDocumentException extends AeException
{
   /**
    * Constructs an exception that reports a missing storage document for a
    * journal entry.
    */
   public AeMissingStorageDocumentException()
   {
      super(AeMessages.getString("AeMissingStorageDocumentException.ERROR_0")); //$NON-NLS-1$
   }

   /**
    * Constructs an exception that reports a missing storage document for a
    * journal entry with data from the given location.
    *
    * @param aLocationId
    */
   public AeMissingStorageDocumentException(int aLocationId)
   {
      super(AeMessages.format("AeMissingStorageDocumentException.ERROR_1", aLocationId)); //$NON-NLS-1$
   }
}
