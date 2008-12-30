// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/convert/AeBpelDefConverterFactory.java,v 1.3 2006/09/27 18:23:5
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
package org.activebpel.rt.bpel.def.convert;

import java.util.HashMap;
import java.util.Map;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.def.IAeBPELConstants;

/**
 * A factory for creating BPEL def converters.  This factory takes the 'from' bpel namespace 
 * and the 'to' bpel namespace and returns a converter that will convert a Process def.
 */
public class AeBpelDefConverterFactory
{
   /** A map of converters. */
   private static Map sConverters;
   
   static
   {
      sConverters = new HashMap();
      // bpws 1.1 -> wsbpel 2.x
      sConverters.put(
            new AeBpelConverterKey(IAeBPELConstants.BPWS_NAMESPACE_URI, IAeBPELConstants.WSBPEL_2_0_NAMESPACE_URI),
            AeBPWSToWSBPELConverter.class);
      // bpws 1.1 abstract process  -> wsbpel 2.x abstract process.
      sConverters.put(
            new AeBpelConverterKey(IAeBPELConstants.BPWS_NAMESPACE_URI, IAeBPELConstants.WSBPEL_2_0_ABSTRACT_NAMESPACE_URI),
            AeBPWSToWSBPELAbstractProcessConverter.class);
   }
   
   /**
    * Given the BPEL namespace to convert <strong>from</strong> and the BPEL namespace to
    * convert <string>to</strong>, return a BPEL def converter.
    * 
    * @param aFromNamespace
    * @param aToNamespace
    */
   public static IAeBpelDefConverter createConverter(String aFromNamespace, String aToNamespace)
   {
      IAeBpelDefConverter converter = null;

      AeBpelConverterKey key = new AeBpelConverterKey(aFromNamespace, aToNamespace);
      Class clazz = (Class) sConverters.get(key);
      if (clazz != null)
      {
         try
         {
            converter = (IAeBpelDefConverter) clazz.newInstance();
         }
         catch (Throwable t)
         {
            AeException.logError(t);
         }
      }
      
      return converter;
   }
   
   /**
    * A simple inner class that represents the key into the map of converters.
    */
   protected static class AeBpelConverterKey
   {
      /** The 'from' namespace. */
      private String mFromNS;
      /** The 'to' namespace. */
      private String mToNS;
      
      /**
       * C'tor.
       * 
       * @param aFromNS
       * @param aToNS
       */
      public AeBpelConverterKey(String aFromNS, String aToNS)
      {
         setFromNS(aFromNS);
         setToNS(aToNS);
      }
      
      /**
       * @return Returns the fromNS.
       */
      public String getFromNS()
      {
         return mFromNS;
      }
      /**
       * @param aFromNS The fromNS to set.
       */
      public void setFromNS(String aFromNS)
      {
         mFromNS = aFromNS;
      }
      /**
       * @return Returns the toNS.
       */
      public String getToNS()
      {
         return mToNS;
      }
      /**
       * @param aToNS The toNS to set.
       */
      public void setToNS(String aToNS)
      {
         mToNS = aToNS;
      }
      
      /**
       * @see java.lang.Object#hashCode()
       */
      public int hashCode()
      {
         return getFromNS().hashCode() ^ getToNS().hashCode();
      }
      
      /**
       * @see java.lang.Object#equals(java.lang.Object)
       */
      public boolean equals(Object aObject)
      {
         if (aObject instanceof AeBpelConverterKey)
         {
            AeBpelConverterKey other = (AeBpelConverterKey) aObject;
            return getFromNS().equals(other.getFromNS()) && getToNS().equals(other.getToNS());
         }
         else
         {
            return false;
         }
      }
   }
}
