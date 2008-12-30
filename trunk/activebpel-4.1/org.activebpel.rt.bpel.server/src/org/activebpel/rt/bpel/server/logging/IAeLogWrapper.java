// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/logging/IAeLogWrapper.java,v 1.5 2005/02/08 15:36:0
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
package org.activebpel.rt.bpel.server.logging;

import org.activebpel.rt.bpel.server.AeMessages;

/**
 *  Wrapper around app server logging.
 */
public interface IAeLogWrapper
{
   /**
    * Simple console logging. 
    */
   public static final IAeLogWrapper CONSOLE_LOG = new IAeLogWrapper()
   {
      /**
       * Debug messages.
       * @param aMessage
       */
      public void logDebug( String aMessage )
      {
         System.out.println( AeMessages.getString("IAeLogWrapper.0") + aMessage ); //$NON-NLS-1$
      }

      /**
       * Info messages.
       * @param aMessage
       */
      public void logInfo( String aMessage )
      {
         System.out.println( AeMessages.getString("IAeLogWrapper.1") + aMessage ); //$NON-NLS-1$
      }
   
      /**
       * Error messages.
       * @param aMessage
       * @param aProblem
       */
      public void logError( String aMessage, Throwable aProblem )
      {
         System.out.println( AeMessages.getString("IAeLogWrapper.2") + aMessage ); //$NON-NLS-1$
         if( aProblem != null )
         {
            aProblem.printStackTrace();
      	}
      }
   };
   
   /** No-op IAeLogWrapper impl. */
   public static IAeLogWrapper NULL_LOG = new IAeLogWrapper()
   {
      /**
       * @see org.activebpel.rt.bpel.server.logging.IAeLogWrapper#logDebug(java.lang.String)
       */
      public void logDebug(String aMessage)
      {
      }

      /**
       * @see org.activebpel.rt.bpel.server.logging.IAeLogWrapper#logError(java.lang.String, java.lang.Throwable)
       */
      public void logError(String aMessage, Throwable aProblem)
      {
      }

      /**
       * @see org.activebpel.rt.bpel.server.logging.IAeLogWrapper#logInfo(java.lang.String)
       */
      public void logInfo(String aMessage)
      {
      }
   };

   /**
    * Debug messages.
    * @param aMessage
    */
   public void logDebug( String aMessage );

   /**
    * Info messages.
    * @param aMessage
    */
   public void logInfo( String aMessage );
   
   /**
    * Error messages.
    * @param aMessage
    * @param aProblem
    */
   public void logError( String aMessage, Throwable aProblem );
}
