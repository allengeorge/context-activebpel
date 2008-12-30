//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/coord/IAeCoordinationContext.java,v 1.4 2006/05/24 23:07:0
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

import java.io.Serializable;

/**
 * Interface for a WS-Coordination context. 
 * 
 * Note: This is an internal implementation tailored to be used with requirement 111
 * i.e. the interface provides a simple property getter and setter. 
 *  * 
 * The final implementation should follow something close to CoordinationContext
 * as per http://schemas.xmlsoap.org/ws/2004/10/wsoor.
 *  
 */
public interface IAeCoordinationContext extends Serializable
{   
   /**
    * Returns the coordination identifier assigned by the activation service.
    * @return coordination id
    */
   public String getIdentifier();
   
   /**
    * Returns the coordination type. E.g. activebpel:coord:SubProcess, wsba:AtomicOutcome, wsba:MixedOutcome.
    */
   public String getCoordinationType();   
   
   /**
    * Returns a context property.
    * @param aName name of property.
    * @return property value.
    */
   public String getProperty(String aName);
   
   /**
    * Sets a property value.
    * @param aName property name.
    * @param aValue property value.
    */
   public void setProperty(String aName, String aValue);
}
