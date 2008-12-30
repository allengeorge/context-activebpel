//$Header: /Development/AEDevelopment/projects/ddl.org.activebpel/src/org/activebpel/ddl/storage/sql/upgrade/AeSQLUpgrader1_0_8_4_QueuedReceiveTable.java,v 1.2 2005/10/17 20:43:0
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
package org.activebpel.ddl.storage.sql.upgrade;

import org.activebpel.rt.bpel.server.engine.storage.sql.AeSQLConfig;

/**
 * Generates the correlation hash for the duplicate receives.
 */
public class AeSQLUpgrader1_0_8_4_QueuedReceiveTable extends AeSQLUpgrader1_0_8_3_QueuedReceiveTable
{
   /**
    * Constructs a queue storage upgrader.
    * 
    * @param aUpgradeName
    * @param aSQLConfig
    */
   public AeSQLUpgrader1_0_8_4_QueuedReceiveTable(String aUpgradeName, AeSQLConfig aSQLConfig)
   {
      super(aUpgradeName, aSQLConfig);
   }
   
   /**
    * @see org.activebpel.ddl.storage.sql.upgrade.AeSQLUpgrader1_0_8_3_QueuedReceiveTable#getQueryName()
    */
   protected String getQueryName()
   {
      return IAeUpgraderSQLKeys.GET_DUPE_QUEUED_RECEIVES;
   }
}
