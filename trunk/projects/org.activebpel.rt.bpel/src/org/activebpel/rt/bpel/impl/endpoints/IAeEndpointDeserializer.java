// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/endpoints/IAeEndpointDeserializer.java,v 1.2 2004/07/08 13:10:0
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
package org.activebpel.rt.bpel.impl.endpoints;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeEndpointReference;

import org.w3c.dom.Element;

/**
 * Defines the interface for a class capable of parsing an IAeEndpointReference from
 * an DOM element. This provides a layer of abstraction between endpoint references
 * and their xml format, primarily since WS-Addressing is not yet an open standard
 * and it's possible that there may arise a competing standard for serializing 
 * endpoints as xml. 
 */
public interface IAeEndpointDeserializer
{
   /**
    * Parses an IAeEndpointReference from a DOM Element.
    * @param aElement
    */
   public IAeEndpointReference deserializeEndpoint(Element aElement) throws AeBusinessProcessException;

   /**
    * Populates an existing reference with the endpoint information contained within
    * the DOM Element.
    * @param aElement
    * @param aRef - gets populated and returned if not null, otherwise a new ref is created, populated, and returned
    */
   public IAeEndpointReference deserializeEndpoint(Element aElement, IAeEndpointReference aRef) throws AeBusinessProcessException;
}
