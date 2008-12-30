//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.tamino/src/org/activebpel/rt/bpel/server/engine/storage/tamino/IAeTaminoDataSource.java,v 1.4 2007/08/17 00:57:3
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
package org.activebpel.rt.bpel.server.engine.storage.tamino;

import org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBDataSource;

/**
 * An interface that defines the set of methods needed to give access to the
 * Tamino database.
 */
public interface IAeTaminoDataSource extends IAeXMLDBDataSource
{
   /** A key into the config map (the URL to the Tamino server). */
   public static final String URL = "URL";  //$NON-NLS-1$
   /** A key into the config map (the Tamino database to use). */
   public static final String DATABASE = "Database";  //$NON-NLS-1$
   /** A key into the config map (the Tamino collection to use). */
   public static final String COLLECTION = "Collection"; //$NON-NLS-1$
   /** A key into the config map (a windows domain to use for Tamino auth). */
   public static final String DOMAIN = "Domain"; //$NON-NLS-1$

   /**
    * Returns the name of the collection this data source is configured for.
    */
   public String getCollectionName();
}
