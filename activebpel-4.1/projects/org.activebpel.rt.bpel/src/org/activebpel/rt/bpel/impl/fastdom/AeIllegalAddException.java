// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/fastdom/AeIllegalAddException.java,v 1.1 2004/09/07 22:08:2
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
package org.activebpel.rt.bpel.impl.fastdom;

/**
 * Implements the exception that reports violations when adding a node to the
 * fast, lightweight DOM.
 */
public class AeIllegalAddException extends RuntimeException
{
   /**
    * Default constructor.
    */
   public AeIllegalAddException()
   {
      super();
   }

   /**
    * Constructs an exception with the specified message.
    *
    * @param aMessage
    */
   public AeIllegalAddException(String aMessage)
   {
      super(aMessage);
   }

   /**
    * Constructs an exception with the specified message and root cause.
    *
    * @param aMessage
    * @param aCause
    */
   public AeIllegalAddException(String aMessage, Throwable aCause)
   {
      super(aMessage, aCause);
   }
}
