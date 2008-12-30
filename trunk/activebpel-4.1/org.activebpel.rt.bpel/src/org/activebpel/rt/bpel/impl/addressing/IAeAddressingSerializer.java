// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/addressing/IAeAddressingSerializer.java,v 1.3 2007/01/17 17:48:0
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

import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.wsio.IAeWsAddressingConstants;
import org.w3c.dom.Document;

/**
 * Responsible for serializing a set of WS-Addressing headers to a SOAPHeader element. 
 * This is used when creating SOAP messages. 
 */
public interface IAeAddressingSerializer extends IAeWsAddressingConstants
{
   /**
    * Serializes the WS-Addressing information to a SOAPHeader.
    * @param aReference contains addressing information
    * @param aEnv SOAPEnvelope responsible for SOAP Name creation 
    */
   public SOAPHeader serializeHeaders(IAeAddressingHeaders aReference, SOAPEnvelope aEnv) throws AeBusinessProcessException;

   /**
    * Serializes the WS-Addressing information to a Document.
    * @param aReference contains addressing information
    */
   public Document serializeToDocument(IAeAddressingHeaders aReference) throws AeBusinessProcessException;
   
}
