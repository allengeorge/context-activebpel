//$Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/attachment/AeAttachmentException.java,v 1.1 2007/04/23 21:20:4
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
package org.activebpel.rt.attachment;

import org.activebpel.rt.AeException;

/**
 * Base class for attachment related exceptions.
 */
public class AeAttachmentException extends AeException
{

   /**
    * Default constructor.
    */
   public AeAttachmentException()
   {
      super();
   }

   /**
    * Creates an exception given the message.
    * @param aInfo message
    */
   public AeAttachmentException(String aInfo)
   {
      super(aInfo);
   }

   /**
    * Creates an exception given the root cause.
    * @param aRootCause 
    */
   public AeAttachmentException(Throwable aRootCause)
   {
      super(aRootCause);
   }

   /**
    * Creates an exception given the root cause and the message.
    * @param aInfo
    * @param aRootCause
    */
   public AeAttachmentException(String aInfo, Throwable aRootCause)
   {
      super(aInfo, aRootCause);
   }

}
