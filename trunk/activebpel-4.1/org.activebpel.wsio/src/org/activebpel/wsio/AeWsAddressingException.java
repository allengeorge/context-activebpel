//$Header: /Development/AEDevelopment/projects/org.activebpel.wsio/src/org/activebpel/wsio/AeWsAddressingException.java,v 1.1 2006/08/08 16:37:5
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
package org.activebpel.wsio;

/**
 * Exception thrown by addressing layer when a set of headers
 * violates the rules set by WS-Addressing  
 */
public class AeWsAddressingException extends Exception
{

   /**
    * Default Constructor
    *
    */
   public AeWsAddressingException()
   {
      super();
   }

   /**
    * Constructor with additional message text
    * @param aMessage
    */
   public AeWsAddressingException(String aMessage)
   {
      super(aMessage);
   }

   /**
    * Constructor with additional message text and root cause
    * @param aMessage
    * @param aCause
    */
   public AeWsAddressingException(String aMessage, Throwable aCause)
   {
      super(aMessage, aCause);
   }

   /**
    * Constructor with a root cause only
    * @param aCause
    */
   public AeWsAddressingException(Throwable aCause)
   {
      super(aCause);
   }

}
