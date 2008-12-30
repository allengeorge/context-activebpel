//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/addressing/IAeStorageBackedURNResolver.java,v 1.1 2007/05/22 00:19:1
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
package org.activebpel.rt.bpel.server.addressing; 

import org.activebpel.rt.bpel.urn.IAeURNResolver;

/**
 * Extends the URN resolver by adding a reload method that reloads the mappings
 * from its storage. 
 */
public interface IAeStorageBackedURNResolver extends IAeURNResolver
{
   /**
    * Reloads the mappings from the underlying storage layer.
    */
   public void reload();
}
 
