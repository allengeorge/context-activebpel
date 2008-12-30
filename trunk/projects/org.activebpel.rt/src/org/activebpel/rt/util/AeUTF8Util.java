//$Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/util/AeUTF8Util.java,v 1.1 2005/01/19 22:09:4
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
package org.activebpel.rt.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.activebpel.rt.AeException;
import org.activebpel.rt.xml.AeXMLParserBase;
import org.w3c.dom.Document;

/**
 * Utility class for supporting UTF-8 encoding.
 */
public class AeUTF8Util
{
   /** UTF-8 encoding string. */
   public static final String UTF8_ENCODING = "UTF-8"; //$NON-NLS-1$
   
   /**
    * Convert <code>Document</code> to <code>InputStream</code> backed by a 
    * UTF-8 encoded byte array.
    * @param aDoc
    * @throws AeException
    */
   public static InputStream getInputStream( Document aDoc )
   throws AeException
   {
      String docString = AeXMLParserBase.documentToString( aDoc, true );
      return getInputStream( docString );
   }
   
   /**
    * Convert <code>String</code> to <code>InputStream</code> backed by a 
    * UTF-8 encoded byte array.
    * @param aString
    * @throws AeException
    */
   public static InputStream getInputStream( String aString )
   throws AeException
   {
         return new ByteArrayInputStream( getBytes( aString ) );
   }
   
   /**
    * Convert <code>Document</code> to byte[] using the UTF-8 charset.
    * @param aDoc
    * @throws AeException
    */
   public static byte[] getBytes( Document aDoc ) throws AeException
   {
      return getBytes( AeXMLParserBase.documentToString(aDoc, true) );
   }
   
   /**
    * Encodes this <tt>String</tt> into a sequence of bytes using the
    * UTF-8 charset, returning the result as a new byte array.
    * @param aString
    * @throws AeException
    */
   public static byte[] getBytes( String aString ) throws AeException
   {
      try
      {
         return aString.getBytes( getUTF8Encoding() );
      }
      catch( UnsupportedEncodingException io )
      {
         throw new AeException( io );
      }
   }
   
   /**
     * Constructs a new String by decoding the specified subarray of
     * bytes using the UTF-8 charset. 
    * @param aBytes
    * @param aOffSet
    * @param aLength
    * @throws AeException
    */
   public static String newString( byte[] aBytes, int aOffSet, int aLength  )
   throws AeException
   {
      try
      {
         return new String( aBytes, aOffSet, aLength, getUTF8Encoding() );
      }
      catch( UnsupportedEncodingException io )
      {
         throw new AeException( io );
      }
   }
   
   /**
    * Translates a string into <code>application/x-www-form-urlencoded</code>
    * format using the UTF-8 encoding scheme. This method uses the
    * @param aString
    * @throws UnsupportedEncodingException
    */
   public static String urlEncode( String aString ) throws UnsupportedEncodingException
   {
      return URLEncoder.encode( aString, getUTF8Encoding() );
   }
   
   /**
    * Decodes a <code>application/x-www-form-urlencoded</code> string using the UTF-8 
    * encoding scheme.
    * @param aString
    * @throws UnsupportedEncodingException
    */
   public static String urlDecode( String aString ) throws UnsupportedEncodingException 
   {
      return URLDecoder.decode( aString, getUTF8Encoding() );
   }
   
   /**
    * @return Return the UTF-8 encoding string.
    */
   private static String getUTF8Encoding()
   {
      return UTF8_ENCODING;
   }
}
