//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/sql/IAeURNSQLKeys.java,v 1.1 2006/01/03 20:34:5
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
package org.activebpel.rt.bpel.server.engine.storage.sql;

/**
 * Constants for the URN storage SQL keys (keys into the {@link
 * org.activebpel.rt.bpel.server.engine.storage.sql.AeSQLConfig} object).
 */
public interface IAeURNSQLKeys
{
   public static final String SQL_INSERT_MAPPING = "InsertMapping"; //$NON-NLS-1$
   public static final String SQL_UPDATE_MAPPING = "UpdateMapping"; //$NON-NLS-1$
   public static final String SQL_DELETE_MAPPING = "DeleteMapping"; //$NON-NLS-1$
   public static final String SQL_GET_MAPPINGS = "GetMappings"; //$NON-NLS-1$
}
