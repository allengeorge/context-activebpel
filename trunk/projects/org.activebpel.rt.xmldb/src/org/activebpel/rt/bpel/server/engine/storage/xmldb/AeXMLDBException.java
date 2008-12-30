//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.xmldb/src/org/activebpel/rt/bpel/server/engine/storage/xmldb/AeXMLDBException.java,v 1.1 2007/08/17 00:40:5
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

import org.activebpel.rt.bpel.server.engine.storage.AeStorageException;

/**
 * An exception that is thrown when an error occurs in the XMLDB layer.
 */
public class AeXMLDBException extends AeStorageException
{

   /**
    * Construct a new XMLDB exception with the passed info string.
    */
   public AeXMLDBException(String aInfo)
   {
      super(aInfo);
      setInfo(aInfo);
   }

   /**
    * Construct with a root exception (used primarily for rethrowing an underlying exception).
    * 
    * @param aRootCause
    */
   public AeXMLDBException(Throwable aRootCause)
   {
      super(aRootCause);
      setRootCause(aRootCause);
      setInfo(aRootCause.getLocalizedMessage());
   }

   /**
    * Construct with a root exception (used prinarily for rethrowing an underlying exception).
    * 
    * @param aInfo Informational message for the exception
    * @param aRootCause Root cause of the exception
    */
   public AeXMLDBException(String aInfo, Throwable aRootCause)
   {
      super(aInfo, aRootCause);
      setRootCause(aRootCause);
   }
}
