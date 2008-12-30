//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/AeExceptionManagementUtil.java,v 1.1 2005/08/31 22:09:3
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

import org.activebpel.rt.bpel.server.engine.AeEngineFactory;
import org.activebpel.rt.util.AeUtil;
import org.w3c.dom.Document;

/**
 * Utility class for process exception management.
 */
public class AeExceptionManagementUtil
{

   /**
    * Extract the <code>AeExceptionManagementType</code> from the process
    * deployment descriptor (.pdd) document.
    * @param aPddDoc
    */
   public static AeExceptionManagementType getExceptionManagementType( Document aPddDoc )
   {
      AeExceptionManagementType type = AeExceptionManagementType.ENGINE;

      String processSpecificType = 
         aPddDoc.getDocumentElement().getAttribute(
               IAePddXmlConstants.ATT_SUSPEND_PROCESS_ON_UNCAUGHT_FAULT);

      // if an override for process exception management was specified in 
      // the process deployment descriptor create the appropriate type
      if( AeUtil.notNullOrEmpty(processSpecificType) )
      {
         if( AeUtil.toBoolean(processSpecificType) )
         {
            type = AeExceptionManagementType.SUSPEND;
         }
         else
         {
            type = AeExceptionManagementType.NORMAL;
         }
      }
      return type;
   }
   
   /**
    * Return true if the process should be suspended if it encounters an
    * uncaught fault based on its persisten type and exception management
    * type.
    * @param aExceptionManagementType
    * @param aPersistenceType
    */
   public static boolean isSuspendOnUncaughtFaultEnabled( 
         AeExceptionManagementType aExceptionManagementType, 
         AeProcessPersistenceType aPersistenceType )
   {
      boolean suspendMe = false;
      
      // if the process is a service flow, we will never suspend it
      if( AeProcessPersistenceType.NONE != aPersistenceType )
      {
         if( AeExceptionManagementType.ENGINE == aExceptionManagementType )
         {
            suspendMe = AeEngineFactory.getEngineConfig().isSuspendProcessOnUncaughtFault();
         }
         else
         {
            suspendMe = AeExceptionManagementType.SUSPEND == aExceptionManagementType; 
         }
      }
      return suspendMe;
   }
}
