// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/addressing/pdef/AePartnerAddressingFactory.java,v 1.4 2005/02/08 15:36:0
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
package org.activebpel.rt.bpel.server.addressing.pdef;

import org.activebpel.rt.bpel.server.AeMessages;
import org.activebpel.rt.bpel.server.engine.AeEngineFactory;
import org.activebpel.rt.util.AeUtil;

/**
 *  Base class for accessing the parnter addressing factory impls.
 */
abstract public class AePartnerAddressingFactory implements IAePartnerAddressingFactory
{

   /**
    * Creation method for a new factory instance.
    * Delegates to the findFactoryClassMethod to determine
    * the actual factory impl.
    * @return a new factory instance.
    * @throws AePartnerAddressingException
    */
   public static IAePartnerAddressingFactory newInstance() 
   throws AePartnerAddressingException
   {
      String factoryClassName = findFactoryClassName();
      return newInstance( factoryClassName );
   }
   
   /**
    * Looks for the factory class name in AeEngineConfig via AeEngineFactory.
    */
   protected static String findFactoryClassName()
   {
      return AeEngineFactory.getEngineConfig().getPartnerAddressingFactoryClassName();
   }
   
   /**
    * Creation method for instantiating a new factory instance
    * based on the factory class name arg.
    * @param aFactoryClassName
    * @throws AePartnerAddressingException
    */
   public static IAePartnerAddressingFactory newInstance( String aFactoryClassName )
   throws AePartnerAddressingException
   {
      if( !AeUtil.isNullOrEmpty( aFactoryClassName ) )
      {
         try
         {
            Class factoryClass = Class.forName( aFactoryClassName );
            return (IAePartnerAddressingFactory)factoryClass.newInstance();
         }
         catch (Exception e)
         {
            throw new AePartnerAddressingException(AeMessages.getString("AePartnerAddressingFactory.ERROR_0") + aFactoryClassName, e ); //$NON-NLS-1$
         }
      }
      else
      {
         throw new AePartnerAddressingException(AeMessages.getString("AePartnerAddressingFactory.ERROR_1")); //$NON-NLS-1$
      }
   }
}
