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

import java.io.Serializable;
import java.util.List;

import javax.xml.namespace.QName;

import org.activebpel.wsio.IAeWebServiceEndpointReference;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * Describes the interface used for interacting with business processes. This
 * extends {@link org.activebpel.wsio.IAeWebServiceEndpointReference} to add
 * convenience methods but should not add additional data.
 */
public interface IAeEndpointReference extends IAeWebServiceEndpointReference, Serializable
{
   /**
    * Returns the current representation of the endpoint reference as a document
    * fragment.
    */
   public Document toDocument();

   /**
    * Sets the data which defines the endpoint reference based upon the
    * given element.
    * @param aData An element which defines an endpoint reference
    */
   public void setReferenceData(Element aData) throws AeBusinessProcessException;

   /**
    * Sets the data which defines the endpoint reference based upon the
    * given endpoint reference.
    * @param aReference the endpoint reference
    */
   public void setReferenceData(IAeWebServiceEndpointReference aReference);

   /**
    * Updates the data which defines the endpoint reference based upon the
    * given element.
    * @param aData An element which defines an endpoint reference
    */
   public void updateReferenceData(Element aData) throws AeBusinessProcessException;

   /**
    * Updates the data which defines the endpoint reference based upon the
    * given endpoint reference.
    * @param aReference An endpoint reference
    */
   public void updateReferenceData(IAeWebServiceEndpointReference aReference);

   /**
    * Add an extensibility element.
    * @param aExtElement the extensibility element to be added
    */
   public void addExtensibilityElement(Element aExtElement);

   /**
    * Add an Policy element.
    * @param aPolicyElement the policy element to be added
    */
   public void addPolicyElement(Element aPolicyElement);

   /**
    * Adds a property to the reference properties
    * @param key
    * @param aString
    */
   public void addProperty(QName key, String aString);

   /**
    * Setter for the address
    * @param aString
    */
   public void setAddress(String aString);

   /**
    * Setter for the porttype
    * @param aName
    */
   public void setPortType(QName aName);

   /**
    * Setter for the service port
    * @param portName
    */
   public void setServicePort(String portName);

   /**
    * Setter for the service name
    * @param aName
    */
   public void setServiceName(QName aName);

   /**
    * Setter for the source namespace that this endpoint was created from. This is
    * used if the endpoint gets serialized in order to preserve the original format.
    * @param aNamespace
    */
   public void setSourceNamespace(String aNamespace);

   /**
    * Adds a property to the reference properties
    * @param aElement
    */
   public void addReferenceProperty(Element aElement);

   /**
    * Gets an Iterator of all reference property elements.
    * @return Iterator for reference property elements.
    */
   public List getReferenceProperties();
   
   /**
    * Called to clone the endpoint reference.
    */
   public Object clone();
}
