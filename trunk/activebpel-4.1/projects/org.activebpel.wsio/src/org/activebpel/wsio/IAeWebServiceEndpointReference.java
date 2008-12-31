//$Header: /Development/AEDevelopment/projects/org.activebpel.wsio/src/org/activebpel/wsio/IAeWebServiceEndpointReference.java,v 1.6 2006/09/26 15:03:0
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
package org.activebpel.wsio;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

/**
 * Partner endpoint reference data.
 */
public interface IAeWebServiceEndpointReference extends Serializable
{
   /**
    * Returns the list of policy elements if any were specified. 
    */
   public List getPolicies();

   /**
    * Returns an Iterator of extensibility elements defined here.
    * @return Iterator the extensibility elements.
    */
   public Iterator getExtensibilityElements();

   /**
    * Returns the name of the WSDL file which contains the definition of the
    * service element. This value may be null.
    */
   public QName getServiceName();

   /**
    * Returns the port name of the service element. This value may be null.
    */
   public String getServicePort();
   
   /**
    * Returns the username to set on the call object or null if not set
    */
   public String getUsername();
   
   /**
    * Returns the password to set on the call object or null if not set
    */
   public String getPassword();
   
   /**
    * Returns the map of properties if any were specified. 
    */
   public Map getProperties();

   /**
    * Returns the address for the endpoint.
    */
   public String getAddress();

   /**
    * Returns the port type for the endpoint.
    */
   public QName getPortType();
   
   /**
    * Gets an Iterator of all reference property elements.
    * @return Iterator for reference property elements.
    */
   public List getReferenceProperties();
   
   /**
    * @return the wsa namespace uri
    */
   public String getSourceNamespace();
   
}
