//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/reply/AeUnknownReplyReceiver.java,v 1.1 2006/05/24 23:07:0
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
package org.activebpel.rt.bpel.impl.reply;

import org.activebpel.rt.bpel.AeBusinessProcessException;

/**
 * Exception that is used to indicate that a <code>IAeReplyReceiver</code>
 * implementation for a given durable reply type was not found.
 */
public class AeUnknownReplyReceiver extends AeBusinessProcessException
{

   /**
    * Default ctor.
    */
   public AeUnknownReplyReceiver()
   {
      super();
   }

   /**
    * Constructs the exception given the reply type.
    * @param aInfo
    */
   public AeUnknownReplyReceiver(String aInfo)
   {
      super(aInfo);
   }

   /**
    * 
    * @param aInfo
    * @param aRootCause
    */
   public AeUnknownReplyReceiver(String aInfo, Throwable aRootCause)
   {
      super(aInfo, aRootCause);
   }
}
