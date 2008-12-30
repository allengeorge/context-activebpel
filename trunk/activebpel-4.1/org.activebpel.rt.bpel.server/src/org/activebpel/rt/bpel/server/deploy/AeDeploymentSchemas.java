// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/AeDeploymentSchemas.java,v 1.6 2006/08/08 16:49:3
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

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.util.AeCloser;
import org.exolab.castor.xml.schema.Schema;
import org.exolab.castor.xml.schema.reader.SchemaReader;
import org.xml.sax.InputSource;

/**
 * Helper class for loading schemas used for deploying bpel processes to the engine. 
 * todo (cck) do these schemas really need to be loaded statically (performance?)
 */
public class AeDeploymentSchemas
{
   /** set of the pdd schemas */
   private static final Set PDD_SCHEMAS = new HashSet();
   /** set of the pdef schemas */
   private static final Set PDEF_SCHEMAS = new HashSet();
   /** set of the catalog schemas */
   private static final Set CATALOG_SCHEMAS = new HashSet();

   static
   {
      // Load the PDD schemas
      PDD_SCHEMAS.add(loadSchema("/org/activebpel/rt/bpel/server/deploy/pdd.xsd")); //$NON-NLS-1$
      PDD_SCHEMAS.add(loadSchema("/org/activebpel/rt/bpel/server/deploy/pdd_2_1.xsd")); //$NON-NLS-1$      
      PDD_SCHEMAS.add(loadSchema("/org/activebpel/rt/bpel/server/deploy/pdd_1_0.xsd")); //$NON-NLS-1$
      PDD_SCHEMAS.add(loadSchema("/org/activebpel/rt/bpel/server/deploy/pdd_pre_1_0.xsd")); //$NON-NLS-1$

      // Load the PDEF schemas.
      PDEF_SCHEMAS.add(loadSchema("/org/activebpel/rt/bpel/server/deploy/pdef.xsd")); //$NON-NLS-1$
      
      // Load the PDD schemas
      CATALOG_SCHEMAS.add(loadSchema("/org/activebpel/rt/bpel/server/deploy/catalog.xsd")); //$NON-NLS-1$
      CATALOG_SCHEMAS.add(loadSchema("/org/activebpel/rt/bpel/server/deploy/wsdlCatalog.xsd")); //$NON-NLS-1$
   }
   
   /**
    * Gets an iterator containing the pdd schemas as castor schema objects
    */
   public static Iterator getPddSchemas()
   {
      return PDD_SCHEMAS.iterator();      
   }

   /**
    * Gets an iterator containing the pdef schemas as castor schema objects
    */
   public static Iterator getPdefSchemas()
   {
      return PDEF_SCHEMAS.iterator();      
   }

   /**
    * Gets an iterator containing the catalog schemas as castor schema objects
    */
   public static Iterator getCatalogSchemas()
   {
      return CATALOG_SCHEMAS.iterator();      
   }

   /**
    * Loads schemas relative to this class's packaging.
    * @param aPath
    */
   private static Schema loadSchema(String aPath)
   {
      InputStream in = null;
      try
      {
         in = AeDeploymentSchemas.class.getResourceAsStream(aPath);
         SchemaReader schemaReader = new SchemaReader(new InputSource(in));
         
         return schemaReader.read();
      }
      catch(IOException e)
      {
         AeException.logError( e, AeMessages.getString("AeDeploymentSchemas.ERROR_0") + aPath ); //$NON-NLS-1$
         throw new InternalError(AeMessages.getString("AeDeploymentSchemas.ERROR_4")+aPath); //$NON-NLS-1$
      }
      finally
      {
         AeCloser.close(in);
      }
   }
}
