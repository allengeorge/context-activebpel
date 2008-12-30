//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/urn/IAeURNResolver.java,v 1.2 2007/06/01 17:31:1
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
package org.activebpel.rt.bpel.urn; 

import java.util.Map;


/**
 * Used to resolve URN values to a URL.
 */
public interface IAeURNResolver
{
   /**
    * Gets the URL mapped to the URN.
    * 
    * @param aURN
    * @return URL value or the original URN if nothing was mapped to the URN.
    */
   public String getURL(String aURN);
   
   /**
    * Removes all of the mappings in the array.
    * 
    * @param aURNArray
    */
   public void removeMappings(String[] aURNArray);
   
   /**
    * Adds the urn to url mapping.
    * 
    * @param aURN
    * @param aURL
    */
   public void addMapping(String aURN, String aURL);
   
   /**
    * Checks if the resolver has the url mapping.
    * 
    * @param aURN
    */
   public boolean hasMapping(String aURN);
   
   /**
    * Gets all of the mappings.
    */
   public Map getMappings();
}
 
