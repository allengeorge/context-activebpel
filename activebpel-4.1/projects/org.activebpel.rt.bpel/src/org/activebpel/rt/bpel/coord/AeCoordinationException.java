//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/coord/AeCoordinationException.java,v 1.1 2005/10/28 21:07:1
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
package org.activebpel.rt.bpel.coord;

import org.activebpel.rt.AeException;

/**
 * Base class for coordination related exceptions.
 */
public class AeCoordinationException extends AeException
{

   /**
    * Default constructor.
    */
   public AeCoordinationException()
   {
      super();
   }

   /**
    * Creates an exception given the message.
    * @param aInfo message
    */
   public AeCoordinationException(String aInfo)
   {
      super(aInfo);
   }

   /**
    * Creates an exception given the root cause.
    * @param aRootCause 
    */
   public AeCoordinationException(Throwable aRootCause)
   {
      super(aRootCause);
   }

   /**
    * Creates an exception given the root cause and the message.
    * @param aInfo
    * @param aRootCause
    */
   public AeCoordinationException(String aInfo, Throwable aRootCause)
   {
      super(aInfo, aRootCause);
   }

}
