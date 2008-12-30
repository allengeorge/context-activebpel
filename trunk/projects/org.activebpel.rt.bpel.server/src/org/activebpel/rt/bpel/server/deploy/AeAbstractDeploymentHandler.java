// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/AeAbstractDeploymentHandler.java,v 1.7 2005/02/01 19:56:3
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
package org.activebpel.rt.bpel.server.deploy;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.server.logging.IAeDeploymentLogger;
import org.activebpel.rt.bpel.server.logging.IAeLogWrapper;

/**
 *  Base class for AeDeploymentHandler impls.
 */
abstract public class AeAbstractDeploymentHandler implements IAeDeploymentHandler
{
   /** Platform specific logging. */
   private IAeLogWrapper mLogger = IAeLogWrapper.NULL_LOG;
   /** logger that we're currently writing to */
   protected IAeDeploymentLogger mDeploymentLogger;
   /** name of the container currently being deployed */
   private String mContainerName;
   
   /**
    * Setter for the container name being deployed 
    * @param aContainerName
    */
   protected void setContainerName(String aContainerName)
   {
      mContainerName = aContainerName;
   }
   
   /**
    * Constructor.
    * @param aLogger IAeLogWrapper impl. May be null.
    */
   protected AeAbstractDeploymentHandler( IAeLogWrapper aLogger )
   {
      if( aLogger != null )
      {
         mLogger = aLogger;
      }
   }
   
   /**
    * Log debug messages.
    * @param aMessge
    */
   protected void logDebug( String aMessge )
   {
      mLogger.logDebug( formatMessage( aMessge ) );
   }

   /**
    * Log info messages.
    * @param aMessage
    */
   protected void logInfo( String aMessage )
   {
      mLogger.logInfo( formatMessage( aMessage ) );
   }

   /**
    * Log error messages.
    * @param aMessage
    */
   protected void logError( String aMessage )
   {
      logError(aMessage, null);
   }
   
   /**
    * Logs the error w/ the stacktrace.
    * @param aMessage
    * @param aError
    */
   protected void logError(String aMessage, Throwable aError)
   {
      String message = formatMessage(aMessage);
      mLogger.logError(message, aError);
      AeException.logError(aError, aMessage);
   }
   
   /**
    * Utility method for log output.
    * @param aMessage
    */
   protected String formatMessage( String aMessage )
   {
      StringBuffer sb = new StringBuffer();
      sb.append( '[' );
      sb.append( mContainerName );
      sb.append( "] "); //$NON-NLS-1$
      sb.append( aMessage );
      return sb.toString();      
   }
   
   /**
    * @return Returns the deploymentLogger.
    */
   protected IAeDeploymentLogger getDeploymentLogger()
   {
      return mDeploymentLogger;
   }
   
   /**
    * @param aDeploymentLogger The deploymentLogger to set.
    */
   protected void setDeploymentLogger(IAeDeploymentLogger aDeploymentLogger)
   {
      mDeploymentLogger = aDeploymentLogger;
   }
}
