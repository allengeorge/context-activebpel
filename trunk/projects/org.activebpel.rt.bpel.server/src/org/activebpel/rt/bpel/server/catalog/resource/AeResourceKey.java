// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/catalog/resource/AeResourceKey.java,v 1.3 2007/09/12 12:58:1
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
package org.activebpel.rt.bpel.server.catalog.resource;

import org.activebpel.rt.IAeConstants;
import org.activebpel.rt.bpel.def.IAeBPELConstants;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.wsdl.def.IAeBPELExtendedWSDLConst;

/**
 * Key for a resource reference stored in the cache.
 */
public class AeResourceKey implements IAeResourceKey
{
   /** the resource location */
   private final String mLocation;
   /** the resource type */
   private final String mTypeURI;

   /**
    * Constructor.
    */
   public AeResourceKey( String aLocation, String aType )
   {
      mLocation = aLocation;
      mTypeURI = aType;
   }

   /**
    * @see org.activebpel.rt.bpel.server.catalog.resource.IAeResourceKey#getLocation()
    */
   public String getLocation()
   {
      return mLocation;
   }

   /**
    * @see org.activebpel.rt.bpel.server.catalog.resource.IAeResourceKey#getTypeURI()
    */
   public String getTypeURI()
   {
      return mTypeURI;
   }

   /**
    * Return true if this is a wsdl entry.
    */
   public boolean isWsdlEntry()
   {
      return IAeBPELExtendedWSDLConst.WSDL_NAMESPACE.equals(getTypeURI());
   }

   /**
    * Return true if this is a schema entry.
    */
   public boolean isSchemaEntry()
   {
      return IAeConstants.W3C_XML_SCHEMA.equals(getTypeURI());
   }

   /**
    * Returns true if this is an xsl entry.
    */
   public boolean isXslEntry()
   {
      return IAeBPELConstants.XSL_NAMESPACE.equals(getTypeURI());
   }

   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals( Object aObject )
   {
      if( aObject != null && aObject instanceof AeResourceKey )
      {
         IAeResourceKey other = (IAeResourceKey)aObject;
         return AeUtil.compareObjects(getLocation(), other.getLocation()) &&
                AeUtil.compareObjects(getTypeURI(), other.getTypeURI());
      }
      return false;
   }

   /**
    * @see java.lang.Object#hashCode()
    */
   public int hashCode()
   {
      return AeUtil.getSafeString(getLocation()).hashCode() +  (AeUtil.getSafeString(getTypeURI()).hashCode() << 1);
   }
}
