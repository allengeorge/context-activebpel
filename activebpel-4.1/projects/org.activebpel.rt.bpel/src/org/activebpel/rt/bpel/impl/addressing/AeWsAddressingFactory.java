// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/addressing/AeWsAddressingFactory.java,v 1.4 2006/12/20 23:36:3
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

import org.activebpel.rt.IAeConstants;
import org.activebpel.rt.bpel.def.IAeBPELConstants;
import org.activebpel.rt.util.AeUtil;

/**
 * Factory for returning the serializers and deserializers for WS-Addressing Headers
 */
public class AeWsAddressingFactory implements IAeWsAddressingFactory
{
   private static final IAeWsAddressingFactory sInstance = new AeWsAddressingFactory();
   
   /**
    * Private constructor for singleton
    */
   private AeWsAddressingFactory()
   {
      
   }

   /**
    * @return the singleton addressing factory instance
    */
   public static IAeWsAddressingFactory getInstance()
   {
      return sInstance;
   }
   
   /**
    * Returns the WS-Addressing deserializer for a given namespace.  
    * The default deserializer is returned if the namespace parameter is null.
    * @param aNamespace
    * @return the Deserializer
    */
   public IAeAddressingDeserializer getDeserializer(String aNamespace)
   {
      if (AeUtil.isNullOrEmpty( aNamespace) || IAeConstants.WSA_NAMESPACE_URI.equals(aNamespace))
      {
         return AeWSAddressingDeserializer.getInstance();
      }
      else 
      {
         return AeWSAddressingDeserializer.getInstance(aNamespace);         
      }
      
   }

   /**
    * Returns the WS-Addressing serializer for a given namespace.  
    * The default serializer is returned if the namespace parameter is null.
    * @param aNamespace
    */
   public IAeAddressingSerializer getSerializer(String aNamespace)
   {
      if (IAeBPELConstants.WSA_NAMESPACE_URI.equals(aNamespace) || AeUtil.isNullOrEmpty(aNamespace))
      {
         return AeWSAddressingSerializer.getInstance();
      }
      else 
      {
         return AeWSAddressingSerializer.getInstance(aNamespace);         
      }
   }
}
