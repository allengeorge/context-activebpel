// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/AeStorageException.java,v 1.1 2004/07/31 00:46:5
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

import org.activebpel.rt.bpel.AeBusinessProcessException;

/**
 * Standard exception for the storage package.
 */
public class AeStorageException extends AeBusinessProcessException
{
   /** Constructor. */
   public AeStorageException(String aInfo)
   {
      super(aInfo);
   }

   /** Constructor. */
   public AeStorageException(String aInfo, Throwable aRootCause)
   {
      super(aInfo, aRootCause);
   }

   /** Constructor. */
   public AeStorageException(Throwable aRootCause)
   {
      super(aRootCause.getLocalizedMessage(), aRootCause);
   }
}
