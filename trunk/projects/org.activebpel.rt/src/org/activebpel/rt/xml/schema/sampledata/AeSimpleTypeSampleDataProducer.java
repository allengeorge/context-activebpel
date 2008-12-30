//$Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/xml/schema/sampledata/AeSimpleTypeSampleDataProducer.java,v 1.4 2007/08/10 02:13:0
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
package org.activebpel.rt.xml.schema.sampledata; 

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.activebpel.rt.IAeConstants;
import org.activebpel.rt.base64.Base64;
import org.activebpel.rt.xml.schema.AeSchemaAnyURI;
import org.activebpel.rt.xml.schema.AeSchemaBase64Binary;
import org.activebpel.rt.xml.schema.AeSchemaDate;
import org.activebpel.rt.xml.schema.AeSchemaDateTime;
import org.activebpel.rt.xml.schema.AeSchemaDay;
import org.activebpel.rt.xml.schema.AeSchemaDuration;
import org.activebpel.rt.xml.schema.AeSchemaHexBinary;
import org.activebpel.rt.xml.schema.AeSchemaMonth;
import org.activebpel.rt.xml.schema.AeSchemaMonthDay;
import org.activebpel.rt.xml.schema.AeSchemaTime;
import org.activebpel.rt.xml.schema.AeSchemaYear;
import org.activebpel.rt.xml.schema.AeSchemaYearMonth;
import org.activebpel.rt.xml.schema.AeTypeMapping;

/**
 * Class for generating Sample data for each of the Schema built-in simple types.
 */
public class AeSimpleTypeSampleDataProducer
{
   private Map mMap = new HashMap();
   private Date mDate = new Date();
   
   /**
    * Ctor 
    */
   public AeSimpleTypeSampleDataProducer()
   {
      this(new Date());
   }
   
   /**
    * Constructor.
    * @param aDate
    */
   public AeSimpleTypeSampleDataProducer(Date aDate)
   {
      setDate(aDate);
      populateMap();
   }
   
   /**
    * Gets sample data for the given simple type.
    * @param aType
    * @return String sample data string value.
    */
   public String getSampleData(QName aType)
   {
      Object type = getMap().get(aType);
      if (type == null)
      {
         return "data"; //$NON-NLS-1$
      }
      else
      {
         return type.toString();
      }
   }
   
   /**
    * Populated the data map for each sample data type.
    */
   protected void populateMap()
   {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(getDate());
      
      int year = calendar.get(Calendar.YEAR);
      
      Map map = getMap();
      map.put(AeTypeMapping.XSD_ANYURI, new AeSchemaAnyURI("anyURI")); //$NON-NLS-1$
      map.put(new QName(IAeConstants.W3C_XML_SCHEMA, "anyType"), "anyType"); //$NON-NLS-1$ //$NON-NLS-2$
      map.put(AeTypeMapping.XSD_BASE64_BINARY, new AeSchemaBase64Binary(Base64.encodeBytes("base64-string".getBytes()))); //$NON-NLS-1$
      map.put(AeTypeMapping.XSD_BOOLEAN, Boolean.TRUE);
      map.put(AeTypeMapping.XSD_BYTE, new Byte((byte)1));
      map.put(AeTypeMapping.XSD_DATE, new AeSchemaDate(getDate()));
      map.put(AeTypeMapping.XSD_DATETIME, new AeSchemaDateTime(getDate()));
      map.put(AeTypeMapping.XSD_DAY, new AeSchemaDay(1,0));
      map.put(AeTypeMapping.XSD_DECIMAL, new Integer(1));
      map.put(AeTypeMapping.XSD_DOUBLE, new Double(1));
      map.put(AeTypeMapping.XSD_DURATION, new AeSchemaDuration());
      map.put(AeTypeMapping.XSD_FLOAT, new Float(1));
      map.put(AeTypeMapping.XSD_HEX_BINARY, new AeSchemaHexBinary("10203F")); //$NON-NLS-1$
      map.put(AeTypeMapping.XSD_INT, new Integer(1));
      map.put(AeTypeMapping.XSD_INTEGER, new Integer(1));
      map.put(AeTypeMapping.XSD_LONG, new Long(1));
      map.put(AeTypeMapping.XSD_MONTH, new AeSchemaMonth(1, 0));
      map.put(AeTypeMapping.XSD_MONTHDAY, new AeSchemaMonthDay(1, 1, 0));
      map.put(AeTypeMapping.XSD_QNAME, "qname"); //$NON-NLS-1$
      map.put(AeTypeMapping.XSD_SHORT, new Short((short)1));
      map.put(AeTypeMapping.XSD_STRING, "string"); //$NON-NLS-1$
      map.put(AeTypeMapping.XSD_TIME, new AeSchemaTime(getDate()));
      map.put(AeTypeMapping.XSD_YEAR, new AeSchemaYear(year, 0));
      map.put(AeTypeMapping.XSD_YEARMONTH, new AeSchemaYearMonth(year, 1, 0));
      map.put(AeTypeMapping.XSD_POSITIVE_INTEGER, new Integer(1));
      map.put(AeTypeMapping.XSD_NORMALIZED_STRING, "string"); //$NON-NLS-1$
      map.put(AeTypeMapping.XSD_TOKEN, "string");  //$NON-NLS-1$
      map.put(AeTypeMapping.XSD_UNSIGNED_BYTE, new Integer(1));
      map.put(AeTypeMapping.XSD_NEGATIVE_INTEGER, new Integer(-1));
      map.put(AeTypeMapping.XSD_NON_NEGATIVE_INTEGER, new Integer(1));
      map.put(AeTypeMapping.XSD_NON_POSITIVE_INTEGER, new Integer(-1));
      map.put(AeTypeMapping.XSD_UNSIGNED_INT, new Integer(1));
      map.put(AeTypeMapping.XSD_UNSIGNED_LONG, new Long(1));
      map.put(AeTypeMapping.XSD_UNSIGNED_SHORT, new Short((short)1));
      map.put(AeTypeMapping.XSD_NAME, "name");   //$NON-NLS-1$
      map.put(AeTypeMapping.XSD_NCNAME, "ncname"); //$NON-NLS-1$
      map.put(AeTypeMapping.XSD_LANGUAGE, "en");   //$NON-NLS-1$
      map.put(AeTypeMapping.XSD_ID, "id");     //$NON-NLS-1$
      map.put(AeTypeMapping.XSD_IDREF, "idref");    //$NON-NLS-1$
      map.put(AeTypeMapping.XSD_IDREFS, "idrefs");   //$NON-NLS-1$
      map.put(AeTypeMapping.XSD_ENTITY, "entity");   //$NON-NLS-1$
      map.put(AeTypeMapping.XSD_ENTITIES, "entities"); //$NON-NLS-1$
      map.put(AeTypeMapping.XSD_NOTATION, "notation"); //$NON-NLS-1$
      map.put(AeTypeMapping.XSD_NMTOKENS, "nmtokens"); //$NON-NLS-1$
   }

   /**
    * @return the map
    */
   protected Map getMap()
   {
      return mMap;
   }

   /**
    * @param aMap the map to set
    */
   protected void setMap(Map aMap)
   {
      mMap = aMap;
   }
   
   protected Date getDate()
   {
      return mDate;
   }
   
   protected void setDate(Date aDate)
   {
      mDate = aDate;
   }

}
 
