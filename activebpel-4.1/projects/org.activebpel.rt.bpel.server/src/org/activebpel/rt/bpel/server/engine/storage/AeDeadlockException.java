// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/AeDeadlockException.java,v 1.1 2006/02/10 21:51:1
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

import org.activebpel.rt.bpel.server.AeMessages;


/**
 * An exception thrown when a database encounters a deadlock.
 */
public class AeDeadlockException extends AeStorageException
{
   /**
    * Construct a new runtime exception.
    */
   public AeDeadlockException()
   {
      super(AeMessages.getString("AeDeadlockException.DEFAULT_DEALOCK_EXCEPTION_MESSAGE")); //$NON-NLS-1$
   }

   /**
    * Construct a new runtime exception with the passed info string.
    * 
    * @see java.lang.Throwable#Throwable(String)
    */
   public AeDeadlockException(String aInfo)
   {
      super(aInfo);
   }

   /**
    * Construct with a root exception (used primarily for rethrowing an underlying exception).
    * 
    * @param aRootCause
    */
   public AeDeadlockException(Throwable aRootCause)
   {
      super(aRootCause);
   }

   /**
    * Construct with a root exception (used prinarily for rethrowing an underlying exception).
    * 
    * @param aInfo Informational message for the exception
    * @param aRootCause Root cause of the exception
    */
   public AeDeadlockException(String aInfo, Throwable aRootCause)
   {
      super(aInfo, aRootCause);
   }
}
