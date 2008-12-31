//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/invoke/AeAddressHandlingType.java,v 1.1 2005/06/22 16:53:5
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
package org.activebpel.rt.bpel.server.engine.invoke; 

import java.io.Serializable;

import org.activebpel.rt.util.AeUtil;


/**
 * Enumerated types for addressHandling values. The addressHandling value dictates
 * how we will use the value of the wsa:Adddress field in an endpoint reference.
 * The default behavior is Service which relies on the optional wsa:ServiceName
 * value to provide the QName of the service being invoked. The other possible value
 * is Address which will use the value from wsa:Address to override the soap:address
 * value. In either case, the endpoint used will be passed through the URN mapping
 * facility to see if it is a mapping to another URL.
 */
public class AeAddressHandlingType implements Serializable
{
   /** value of the address handling directive */
   private String mValue;
   
   /** wsa:Address will be ignored in favor of the soap:address found in the service definition provided by wsa:ServiceName */
   public static final AeAddressHandlingType SERVICE = new AeAddressHandlingType("Service"); //$NON-NLS-1$
   
   /** wsa:Address is used to override the soap:address (if wsa:ServiceName provided) */
   public static final AeAddressHandlingType ADDRESS = new AeAddressHandlingType("Address"); //$NON-NLS-1$
   
   /**
    * Private ctor to force use of constant types.
    * 
    * @param aValue
    */
   private AeAddressHandlingType(String aValue)
   {
      mValue = aValue;
   }
   
   /**
    * Gets the type by name
    * 
    * @param aName
    */
   public static AeAddressHandlingType getByName(String aName)
   {
      if (AeUtil.isNullOrEmpty(aName))
      {
         return getDefault();
      }
      else if (SERVICE.toString().equals(aName))
      {
         return SERVICE;
      }
      else if (ADDRESS.toString().equals(aName))
      {
         return ADDRESS;
      }
      return null;
   }
   
   /**
    * Gets the default type when none is specified for the partner link
    */
   public static AeAddressHandlingType getDefault()
   {
      return SERVICE;
   }
   
   /**
    * Returns the value of the type string.
    * 
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return mValue;
   }
}
 
