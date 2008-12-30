//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/IAeLocatableObject.java,v 1.1 2005/08/18 21:35:5
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
package org.activebpel.rt.bpel; 

/**
 * Defines a common interface for bpel objects and variables, both of which are
 * referred to by location paths or ids. The location path is an xpath that
 * uniquely identifies the object within the source xml in a human readable way.
 * The location id is a unique id for that same node and is used for storage when
 * a lighter weight id is necessary.
 * 
 * For the most part, implementations will typically defer to their definition
 * objects to get the location path and id. These values are calculated when the
 * xml is deserialized into our definition objects and are the same for all 
 * instances of a given project.
 * 
 * The exception to this is when we create multiple objects from a single definition
 * object like the parallel forEach. In this case, the implementation objects will
 * have their own location paths and ids that differ from the defintion objects.
 */
public interface IAeLocatableObject
{
   /**
    * Gets the location for the object
    */
   public String getLocationPath();
   
   /**
    * Gets the location id for the object
    */
   public int getLocationId();
   
   /**
    * Returns <code>true</code> if and only if this object has a location id.
    * Must return <code>false</code> if <code>getLocationId()</code> will fail.
    */
   public boolean hasLocationId();
   
   /**
    * Setter for the location path
    * @param aPath
    */
   public void setLocationPath(String aPath);
   
   /**
    * Setter for the location id
    * @param aId
    */
   public void setLocationId(int aId);
   
   /**
    * Returns true if the object has had a custom location path set on it.
    */
   public boolean hasCustomLocationPath();
}
 
