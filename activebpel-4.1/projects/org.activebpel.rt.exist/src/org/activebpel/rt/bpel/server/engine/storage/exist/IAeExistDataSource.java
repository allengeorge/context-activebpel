//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.exist/src/org/activebpel/rt/bpel/server/engine/storage/exist/IAeExistDataSource.java,v 1.3 2007/08/23 21:26:5
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
package org.activebpel.rt.bpel.server.engine.storage.exist;

import org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBDataSource;

/**
 * An interface that defines the set of methods needed to give access to the Exist database.
 */
public interface IAeExistDataSource extends IAeXMLDBDataSource
{
   /** A key into the config map (the URL to the Exist server). */
   public static final String URL = "URL";  //$NON-NLS-1$
   /** A key into the config map (the Exist database to use). */
   public static final String DATABASE = "Database";  //$NON-NLS-1$
   /** A key into the config map (the Exist collection to use). */
   public static final String COLLECTION = "Collection"; //$NON-NLS-1$
   /** A key into the config map (the Exist collection to use). */
   public static final String EMBEDDED = "Embedded"; //$NON-NLS-1$
   /** A key into the config map (the Exist collection to use). */
   public static final String INITIALIZE_EMBEDDED = "InitEmbedded"; //$NON-NLS-1$
   /** A key into the config map (the Exist collection to use). */
   public static final String DB_LOCATION = "DBLocation"; //$NON-NLS-1$

   /**
    * Returns the name of the collection this data source is configured for.
    */
   public String getCollectionName();
}
