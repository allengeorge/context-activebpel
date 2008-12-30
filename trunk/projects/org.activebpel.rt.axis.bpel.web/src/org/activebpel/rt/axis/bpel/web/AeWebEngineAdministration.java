//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis.bpel.web/src/org/activebpel/rt/axis/bpel/web/AeWebEngineAdministration.java,v 1.1 2005/02/04 21:46:4
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
package org.activebpel.rt.axis.bpel.web;

import java.io.File;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.server.engine.AeEngineAdministration;
import org.activebpel.rt.bpel.server.engine.AeEngineFactory;
import org.activebpel.rt.bpel.server.logging.AeTeeDeploymentLogger;
import org.activebpel.rt.bpel.server.logging.IAeDeploymentLogger;

/**
 * This is an implementation of the ActiveBPEL engine administration interface.  It extends
 * the base implementation in order to override the <code>deployNewBpr</code> method.  This
 * class provides an implementation of that method in order to allow web service deployments
 * to work in ActiveBPEL.
 */
public class AeWebEngineAdministration extends AeEngineAdministration
{
   /**
    * Overrides the base engine admin impl in order to provide an implementation of the 
    * <code>deployNewBpr</code> method.
    * 
    * @see org.activebpel.rt.bpel.server.admin.IAeEngineAdministration#deployNewBpr(java.io.File, java.lang.String, org.activebpel.rt.bpel.server.logging.IAeDeploymentLogger)
    */
   public void deployNewBpr(File aBprFile, String aBprFilename, IAeDeploymentLogger aLogger) throws AeException
   {
      // Combine the passed-in logger with the engine factory logger using the Tee logger.
      IAeDeploymentLogger logger = AeEngineFactory.getDeploymentLoggerFactory().createLogger();
      IAeDeploymentLogger teeLogger = new AeTeeDeploymentLogger(logger, aLogger);

      AeProcessEngineServlet.getDeploymentHandler().handleDeployment(aBprFile, aBprFilename, teeLogger);
   }

}
