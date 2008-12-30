//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/validate/AeDeploymentFileValidator.java,v 1.3 2005/02/08 15:35:5
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
package org.activebpel.rt.bpel.server.deploy.validate;

import org.activebpel.rt.bpel.server.AeMessages;
import org.activebpel.rt.bpel.server.deploy.IAeDeploymentContainer;
import org.activebpel.rt.bpel.server.logging.IAeDeploymentLogger;

/**
 * This class handles some very basic file contents validation for
 * WSR and BPR deployments.
 */
public class AeDeploymentFileValidator
{
   
   private static final String INVALID_BPR = AeMessages.getString("AeDeploymentFileValidator.0"); //$NON-NLS-1$
   private static final String INVALID_WSR = AeMessages.getString("AeDeploymentFileValidator.1"); //$NON-NLS-1$
   
   /**
    * Preliminary validation of the deployment file.  Ensure that any BPR deployments DO NOT
    * contain any .wsdd files and that WSR deployments contain a .wsdd file.
    * @param aContainer
    * @param aBprFlag
    * @param aLogger
    */
   public static void validateFileType( IAeDeploymentContainer aContainer, boolean aBprFlag, IAeDeploymentLogger aLogger )
   {
      // if this is supposed to be a bpr deployment and the file contains
      // a .wsdd file, then log an error
      if( aBprFlag  )
      {
         if( aContainer.isWsddDeployment() )
         {
            aLogger.addError(INVALID_BPR, null, null );
         }
      }
      // otherwise if this is a wsr deployment and no META-INF/wsdd file
      // has been detected then log an error
      else if( !aContainer.isWsddDeployment() )
      {
         aLogger.addError(INVALID_WSR, null, null );
      }
   }
}
