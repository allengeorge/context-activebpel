//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/coord/IAeCreateContextRequest.java,v 1.4 2006/05/24 23:07:0
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
package org.activebpel.rt.bpel.coord;


/**
 * Interface for a WS-Coordination CreateCoordinationContext 
 * 
 * Note: This is an internal implementation tailored to be used with requirement 111
 * ie - the interface provides a simple property getter and setter. 
 * 
 * The final implementation should follow something close to CreateCoordinationContext
 * as per http://schemas.xmlsoap.org/ws/2004/10/wsoor.
 * Maybe refactor this interface to wsio.IWebServiceCoordCreateContextRequest.  
 */
public interface IAeCreateContextRequest
{
   /**
    * Returns the coordination type.
    */
   public String getCoordinationType();
   
   /**
    * Sets the coordination type.
    * @param aCoordinationType
    */
   public void setCoordinationType(String aCoordinationType);
   
   /**
    * Returns a property value.
    * @param aName name of the property.
    * @return value if the property is found or null otherwise.
    */
   public String getProperty(String aName);
   
   /**
    * Sets a custom property.
    * @param aName name of the property.
    * @param aValue value of the property.
    */
   public void setProperty(String aName, String aValue);
}
