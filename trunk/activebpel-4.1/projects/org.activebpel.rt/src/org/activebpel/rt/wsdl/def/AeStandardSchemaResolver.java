//$Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/wsdl/def/AeStandardSchemaResolver.java,v 1.3 2005/02/08 15:27:1
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

import java.io.InputStream;

import org.activebpel.rt.AeException;
import org.activebpel.rt.AeMessages;
import org.activebpel.rt.schemas.AeStandardSchemas;
import org.xml.sax.InputSource;

/**
 * This implemenation of a standard schema resolver knows how to resolve standard schemas
 * from within the context of the AWF Designer.
 */
public class AeStandardSchemaResolver implements IAeStandardSchemaResolver
{
   /**
    * Creates a new standard schema resolver.
    */
   public static IAeStandardSchemaResolver newInstance()
   {
      return new AeStandardSchemaResolver();
   }

   /**
    * Default constructor.
    */
   public AeStandardSchemaResolver()
   {
   }

   /**
    * @see org.activebpel.rt.wsdl.def.IAeStandardSchemaResolver#resolve(java.lang.String)
    */
   public InputSource resolve(String aNamespace)
   {
      try
      {
         InputStream is = AeStandardSchemas.getStandardSchema(aNamespace);
         if (is != null)
         {
            return new InputSource(is);
         }
      }
      catch (Throwable t)
      {
         AeException.logError(t, AeMessages.getString("AeStandardSchemaResolver.ERROR_0")); //$NON-NLS-1$
      }
      return null;
   }
}
