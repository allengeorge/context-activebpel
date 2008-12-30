//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/assign/IAeVariableDataWrapper.java,v 1.2 2006/06/26 16:50:3
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
package org.activebpel.rt.bpel.impl.activity.assign; 

import org.activebpel.rt.bpel.impl.AeBpelException;
import org.exolab.castor.xml.schema.XMLType;

/**
 * Interface used to wrap a variable that is being assigned to. 
 */
public interface IAeVariableDataWrapper
{
   /**
    * Sets the new value for the message part
    * 
    * @param aValue
    */
   public void setValue(Object aValue) throws AeBpelException;
   
   /**
    * Gets the value of the variable, initializing the variable to an acceptable
    * default if it was null. Simple types will default to an empty string. Complex
    * types and Elements will default to an empty element with the appropriate name
    * and namespace. 
    * @return Element, java primitive object, or one of our schema type objects (duration/deadline)
    */
   public Object getValue() throws AeBpelException;
   
   /**
    * Getter for the XMLType
    * 
    * @throws AeBpelException
    */
   public XMLType getXMLType() throws AeBpelException;
}
 
