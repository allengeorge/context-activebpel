//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/AeExceptionManagementType.java,v 1.1 2005/08/31 22:09:3
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
package org.activebpel.rt.bpel.server.deploy;

import java.util.HashMap;
import java.util.Map;

/**
 * Enumeration class for exception management types.
 */
public class AeExceptionManagementType
{

   /** Maps type names to type instances. */
   private static Map mTypes = new HashMap();

   // type constants
   public static final AeExceptionManagementType ENGINE = new AeExceptionManagementType( AeDeployConstants.EXCEPTION_MANAGEMENT_TYPE_ENGINE );
   public static final AeExceptionManagementType SUSPEND = new AeExceptionManagementType( AeDeployConstants.EXCEPTION_MANAGEMENT_TYPE_SUSPEND );
   public static final AeExceptionManagementType NORMAL = new AeExceptionManagementType( AeDeployConstants.EXCEPTION_MANAGEMENT_TYPE_NORMAL );
   
   /** name field */
   private String mName;
   
   /**
    * Constructor.
    * @param aName
    */
   private AeExceptionManagementType( String aName )
   {
      mName = aName;
      mTypes.put( aName, this );
   }

   /**
    * @return Returns the name.
    */
   public String getName()
   {
      return mName;
   }
   
   /**
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return getName(); 
   }
   
   /**
    * Return the <code>AeExceptionManagementType</code> object mapped to
    * the given name or the default ENGINE type if no match is found.
    * @param aName
    */
   public static AeExceptionManagementType getType( String aName )
   {
      AeExceptionManagementType type = (AeExceptionManagementType)mTypes.get( aName );
      if( type == null )
      {
         type = ENGINE;
      }
      return type;
   }
}
