//$Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/xml/AeXMLParserSchemaHandler.java,v 1.8 2007/08/16 23:47:5
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
package org.activebpel.rt.xml;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.wsdl.xml.WSDLLocator;

import org.activebpel.rt.AeException;
import org.activebpel.rt.AeMessages;
import org.activebpel.rt.xml.schema.AeSchemaUtil;
import org.exolab.castor.xml.schema.Schema;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;

/**
 * This class implements an XML parser default handler.  It extends the AE default handler
 * and simply adds the ability to resolve schema entities from a list of schemas.
 * 
 * FIXMEQ (Castor SchemaWriter) XML parser schema handler uses Castor Schema objects to resolve schema entities - if we want to remove our dependency on the Castor schema-writer, we need to change this behavior
 */
public class AeXMLParserSchemaHandler extends AeXMLParserDefaultHandler
{
   /** A map from namespace to <code>Schema</code> object. */
   private HashMap mSchemaMap = new HashMap();

   /**
    * Constructs a schema xml handler given the wsdl locator and schema list.
    * 
    * @param aWSDLLocator
    * @param aSchemaList
    */
   public AeXMLParserSchemaHandler(WSDLLocator aWSDLLocator, List aSchemaList)
   {
      this(aWSDLLocator, null, aSchemaList);
   }

   /**
    * Constructs a schema xml handler given the wsdl locator, error handler, and list
    * of schemas.
    * 
    * @param aWSDLLocator
    * @param aErrorHandler
    */
   public AeXMLParserSchemaHandler(WSDLLocator aWSDLLocator, ErrorHandler aErrorHandler, List aSchemaList)
   {
      super(aWSDLLocator, aErrorHandler);
      
      buildSchemaMap(aSchemaList);
   }

   /**
    * Overrides method to resolve schema imports with no location.  The schema will be looked up
    * by its namespace from the list of schemas passed in to the handler during construction.
    * 
    * @see org.activebpel.rt.xml.AeXMLParserDefaultHandler#resolveEntity(java.lang.String, java.lang.String)
    */
   public InputSource resolveEntity(String aPublicId, String aSystemId)
   {
      if (aPublicId == null)
      {
         Schema schema = findSchema(aSystemId);
         if (schema != null)
         {
            try
            {
               String schemaStr = AeSchemaUtil.serializeSchema(schema, false);
               InputSource is = new InputSource(new StringReader(schemaStr));
               is.setSystemId(aSystemId);
               
               return is;
            }
            catch (Exception e)
            {
               AeException.logError(e, AeMessages.getString("AeXMLParserSchemaHandler.ERROR_0")); //$NON-NLS-1$
            }
         }
      }
      return super.resolveEntity(aPublicId, aSystemId);
   }

   /**
    * This method builds the internal schema map, which is a map from schema target namespace
    * to Schema object.
    * 
    * @param aSchemaList
    */
   protected void buildSchemaMap(List aSchemaList)
   {
      for (Iterator iter = aSchemaList.iterator(); iter.hasNext(); )
      {
         Schema schema = (Schema) iter.next();
         mSchemaMap.put(schema.getTargetNamespace(), schema);
      }
   }

   /**
    * Returns a schema for a given namespace or null if not found.
    * 
    * @param aNamespace
    */
   protected Schema findSchema(String aNamespace)
   {
      return (Schema) mSchemaMap.get(aNamespace);
   }
}
