//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/IAeReceiveHandler.java,v 1.1 2007/01/26 22:38:2
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
package org.activebpel.rt.bpel.impl;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.wsio.IAeWebServiceMessageData;
import org.activebpel.wsio.IAeWebServiceResponse;
import org.activebpel.wsio.receive.IAeMessageContext;
import org.w3c.dom.Document;

/**
 * Interface for pluggable receive handlers to process receives from a partner.
 * The methods represent the public API for the BPEL engine to interact with the handlers  
 */
public interface IAeReceiveHandler
{
   /**
    * Convert raw service message data into our internal message data format.
    * Queue the inbound receive with the BPEL engine or appropriate 
    * protocol manager (i.e. WSRM manager for WSRM requests) 
    * 
    * @param aData
    * @param aContext
    * @param aQueryData optional query data
    * @return service response from engine
    * @throws AeBusinessProcessException 
    */
   public IAeWebServiceResponse handleReceiveData( IAeWebServiceMessageData aData, IAeMessageContext aContext ) throws AeBusinessProcessException;

   /**
    * Convert raw service message data into our internal message data format.
    * Queue the inbound receive with the BPEL engine or appropriate 
    * protocol manager (i.e. WSRM manager for WSRM requests) 
    * @param aDocArray
    * @param aContext
    * 
    * @return service response from engine
    * @throws AeBusinessProcessException 
    */
   public IAeWebServiceResponse handleReceiveData( Document[] aDocArray, IAeMessageContext aContext ) throws AeBusinessProcessException;   
}
