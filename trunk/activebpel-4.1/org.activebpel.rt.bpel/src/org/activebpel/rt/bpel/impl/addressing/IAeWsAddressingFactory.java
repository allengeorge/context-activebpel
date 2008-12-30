//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/addressing/IAeWsAddressingFactory.java,v 1.1 2006/08/08 16:44:2
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

/**
 * Interface for a factory class that hands out the appropriate serializer/deserializer
 * for a given WSA namespace
 */
public interface IAeWsAddressingFactory
{
   /**
    * Returns the WS-Addressing deserializer for a given namespace.  
    * The default deserializer is returned if the namespace parameter is null.
    * @param aNamespace
    * @return the Deserializer
    */
   public IAeAddressingDeserializer getDeserializer(String aNamespace);
   
   /**
    * Returns the WS-Addressing serializer for a given namespace.  
    * The default serializer is returned if the namespace parameter is null.
    * @param aNamespace
    * @return the Serializer
    */
   public IAeAddressingSerializer getSerializer(String aNamespace);


}
