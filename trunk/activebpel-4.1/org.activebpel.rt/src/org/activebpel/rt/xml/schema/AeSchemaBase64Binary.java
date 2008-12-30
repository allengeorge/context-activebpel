//$Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/xml/schema/AeSchemaBase64Binary.java,v 1.3 2006/10/12 20:04:5
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
package org.activebpel.rt.xml.schema;

import org.activebpel.rt.AeMessages;
import org.activebpel.rt.base64.Base64;

/**
 * Wrapper object for xsd:base64Binary simple data type. This ensures that Axis serializes the data correctly
 */
public class AeSchemaBase64Binary extends AeSchemaWrappedStringType
{
   /** Parsed-out binary data. */
   private byte [] mBinaryData;

   /**
    * Ctor accepts the base64 binary value.
    *
    * @param aBase64BinaryString
    */
   public AeSchemaBase64Binary(String aBase64BinaryString)
   {
      super(aBase64BinaryString);

      parseBase64String(aBase64BinaryString);
   }
   
   /**
    * Parses the base64 string into its binary form.
    * 
    * @param aBase64BinaryString
    */
   protected void parseBase64String(String aBase64BinaryString)
   {
      byte [] data = null;
      // Decode the string here.  Keep it null if the decode fails.
      try
      {
         data = Base64.decode(aBase64BinaryString);
      }
      catch (Throwable ex)
      {
      }
      
      if (data == null || data.length == 0)
         throw new AeSchemaTypeParseException(AeMessages.getString("AeSchemaBase64Binary.ParseFailureError")); //$NON-NLS-1$

      setBinaryData(data);
   }

   /**
    * @see org.activebpel.rt.xml.schema.IAeSchemaType#accept(org.activebpel.rt.xml.schema.IAeSchemaTypeVisitor)
    */
   public void accept(IAeSchemaTypeVisitor aVisitor)
   {
      aVisitor.visit(this);
   }

   /**
    * @return Returns the binaryData.
    */
   public byte[] getBinaryData()
   {
      return mBinaryData;
   }

   /**
    * @param aBinaryData The binaryData to set.
    */
   protected void setBinaryData(byte[] aBinaryData)
   {
      mBinaryData = aBinaryData;
   }
}
