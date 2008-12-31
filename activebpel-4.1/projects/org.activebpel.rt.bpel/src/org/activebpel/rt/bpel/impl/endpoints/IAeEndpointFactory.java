// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/endpoints/IAeEndpointFactory.java,v 1.2 2004/07/08 13:10:0
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


/**
 * Factory provides getters for serializers and deserializers for the endpoint
 * references based on the provided namespace.  
 */
public interface IAeEndpointFactory
{
   /**
    * Gets a serializer for the specified namespace
    * @param aNamespace
    */
   public IAeEndpointSerializer getSerializer(String aNamespace);

   /**
    * Gets a deserializer for the specified namespace
    * @param aNamespace
    */
   public IAeEndpointDeserializer getDeserializer(String aNamespace);
}
