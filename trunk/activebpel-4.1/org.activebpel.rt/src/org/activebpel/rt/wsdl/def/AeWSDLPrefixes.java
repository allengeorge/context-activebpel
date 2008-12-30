//$Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/wsdl/def/AeWSDLPrefixes.java,v 1.7 2007/07/27 18:08:5
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
package org.activebpel.rt.wsdl.def; 

/**
 * WSDL prefix helper utilities. 
 */
public class AeWSDLPrefixes
{

   /**
    * Static helper method for returning the preferred BPEL prefix String for the given BPEL namespace.
    * @param aBpelNamespace the BPEL version namespace.
    * @return String the BPEL prefix. 
    */
   public static String getBpelPrefix(String aBpelNamespace)
   {
      if ( IAeBPELExtendedWSDLConst.BPWS_NAMESPACE_URI.equals(aBpelNamespace) )
      {
         // BPEL4WS 1.1
         return IAeBPELExtendedWSDLConst.BPWS_PREFIX;   
      }
      else if ( IAeBPELExtendedWSDLConst.WSBPEL_2_0_ABSTRACT_NAMESPACE_URI.equals(aBpelNamespace) )
      {
         // WSBPEL 2.0 Abstract Process 
         return IAeBPELExtendedWSDLConst.ABSTRACT_PROC_PREFIX;   
      }      
      else
      {
         // Assume WSBPEL 2.0
         return IAeBPELExtendedWSDLConst.WSBPEL_2_0_PREFIX;  
      }
   }

   /**
    * Static helper method for returning the preferred Partner Link Type prefix String for the given 
    * Partner Link Type namespace.
    * @param aPLTNamespace the BPEL version namespace.
    * @return String the partner link type prefix. 
    */
   public static String getPltPrefix(String aPLTNamespace)
   {
      if ( IAeBPELExtendedWSDLConst.PARTNER_LINK_NAMESPACE.equals(aPLTNamespace) )
      {
         // BPEL4WS 1.1 PLT prefix.
         return IAeBPELExtendedWSDLConst.PARTNER_LINK_PREFIX;   
      }
      else
      {
         // Assume WSBPEL 2.0 PLT prefix
         return IAeBPELExtendedWSDLConst.WSBPEL_PARTNER_LINK_PREFIX;  
      }
   }

   /**
    * Static helper method for returning the correct Partner Link Type extension namespace
    * associated with the given BPEL namespace.
    * @param aBpelNamespace the BPEL version namespace.
    * @return String the correct partner link type namespace.
    */
   public static String getPltNamespace(String aBpelNamespace)
   {
      if ( IAeBPELExtendedWSDLConst.BPWS_NAMESPACE_URI.equals(aBpelNamespace) )
      {
         // BPEL4WS 1.1 PLT namespace. 
         return IAeBPELExtendedWSDLConst.PARTNER_LINK_NAMESPACE;   
      }
      else
      {
         // assume WSBPEL 2.0 PLT namespace.
         return IAeBPELExtendedWSDLConst.WSBPEL_PARTNER_LINK_NAMESPACE;  
      }
   }

   /**
    * Static helper method for returning the preferred prefix String for the given 
    * Property/PropertyAlias namespace..
    * @param aPropNamespace the property version namespace.
    * @return String the property prefix. 
    */
   public static String getPropertyPrefix(String aPropNamespace)
   {
      if ( IAeBPELExtendedWSDLConst.BPWS_NAMESPACE_URI.equals(aPropNamespace) )
      {
         // BPEL4WS 1.1
         return IAeBPELExtendedWSDLConst.BPWS_PREFIX;   
      }
      else
      {
         // Assume WSBPEL 2.0
         return IAeBPELExtendedWSDLConst.PROPERTY_2_0_PREFIX;  
      }
   }

   /**
    * Static helper method for returning the preferred prefix String for the given 
    * policy namespace..
    * @param aPolicyNamespace the property version namespace.
    * @return String the property prefix. 
    */
   public static String getPolicyPrefix(String aPolicyNamespace)
   {
      return IAeBPELExtendedWSDLConst.POLICY_PREFIX;
   }
   
   /**
    * Static helper method for returning the preferred prefix String for the given 
    * policy namespace..
    * @param aNamespace the property version namespace.
    * @return String the property prefix. 
    */
   public static String getSecUtilPrefix(String aNamespace)
   {
      return IAeBPELExtendedWSDLConst.WSUTIL_PREFIX;
   }
   
}
 
