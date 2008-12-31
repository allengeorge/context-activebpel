// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/addressing/IAeAddressingDeserializer.java,v 1.3 2007/01/17 17:48:0
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
package org.activebpel.rt.bpel.impl.addressing;

import javax.xml.soap.SOAPHeader;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.wsio.IAeWsAddressingConstants;
import org.w3c.dom.Element;

/**
 * Defines the interface for a class capable of parsing WS-Addressing headers from
 * into endpoint references from a SOAP Header element. 
 */
public interface IAeAddressingDeserializer extends IAeWsAddressingConstants
{
   /**
    * Parses a set of WS-Addressing Headers from a dom element.
    * @param aElement the element to parse
    */
   public IAeAddressingHeaders deserializeHeaders(Element aElement) throws AeBusinessProcessException;
   
   /**
    * Parses a set of WS-Addressing Headers from a SOAP Header.
    * @param aHeader the SOAPHeader to parse
    */
   public IAeAddressingHeaders deserializeHeaders(SOAPHeader aHeader) throws AeBusinessProcessException;

   /**
    * Populates an existing WS-Addressing Header object with information contained within
    * the element
    * @param aElement the Element to parse
    * @param aRef - gets populated and returned if not null, otherwise a new ref is created, populated, and returned
    */
   public IAeAddressingHeaders deserializeHeaders(Element aElement, IAeAddressingHeaders aRef) throws AeBusinessProcessException;
   
   /**
    * Populates an existing WS-Addressing Header object with information contained within
    * the SOAP header
    * @param aHeader the SOAPHeader to parse
    * @param aRef - gets populated and returned if not null, otherwise a new ref is created, populated, and returned
    */
   public IAeAddressingHeaders deserializeHeaders(SOAPHeader aHeader, IAeAddressingHeaders aRef) throws AeBusinessProcessException;
   
   /**
    * Returns true if the element is one that contains a wsa header
    * @param aElement
    */
   public boolean isEndpointHeader(Element aElement);
}
