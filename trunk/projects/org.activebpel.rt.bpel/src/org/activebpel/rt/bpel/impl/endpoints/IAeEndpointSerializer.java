// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/endpoints/IAeEndpointSerializer.java,v 1.4 2005/10/17 19:44:1
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

import org.activebpel.wsio.IAeWebServiceEndpointReference;
import org.w3c.dom.Document;

/**
 * Responsible for serializing an endpoint reference to a document fragment. This
 * is used when we're copying endpoint references in an assign's copy operation. 
 */
public interface IAeEndpointSerializer
{
   /**
    * Serializes the endpoint reference to a document fragment.
    * @param aReference
    */
   public Document serializeEndpoint(IAeWebServiceEndpointReference aReference);
}
