// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis/src/org/activebpel/rt/axis/ser/AeAbstractSchemaTypeDeserializer.java,v 1.1 2006/09/07 15:19:5
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
package org.activebpel.rt.axis.ser;

import javax.xml.namespace.QName;

import org.activebpel.rt.AeException;
import org.activebpel.rt.axis.AeMessages;
import org.apache.axis.encoding.ser.SimpleDeserializer;

/**
 * Base class for Ae deserializers.
 */
public abstract class AeAbstractSchemaTypeDeserializer extends SimpleDeserializer
{
   /**
    * The Deserializer is constructed with the xmlType and javaType
    */
   public AeAbstractSchemaTypeDeserializer(Class javaType, QName xmlType)
   {
      super(javaType, xmlType);
   }

   /**
    * @see org.apache.axis.encoding.ser.SimpleDeserializer#makeValue(java.lang.String)
    */
   public Object makeValue(String aSource)
   {
      try
      {
         return makeValueInternal(aSource);
      }
      catch (Exception e) 
      {
         AeException.logError(e, AeMessages.getString("AeAbstractSchemaTypeDeserializer.ERROR_0")); //$NON-NLS-1$
         return aSource;
      }
   }

   /**
    * Internal method that subclasses must implement - does the actual deserialization.
    * 
    * @param aSource
    */
   protected abstract Object makeValueInternal(String aSource);
}
