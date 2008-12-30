//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/AeInvokeHandlerUri.java,v 1.5 2006/10/25 16:10:1
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
package org.activebpel.rt.bpel.server.engine;

import javax.xml.namespace.QName;

import org.activebpel.rt.util.AeUtil;

/**
 * Utility methods for parsing the custom invoker uri.
 */
public class AeInvokeHandlerUri
{
   // TODO (MF) replace this class with a struct. Also, replace all of this code with a simple StringTokenizer!
   
   /** uri delim */
   private static final String URI_DELIM = ":"; //$NON-NLS-1$
   private static final String LEGACY_QUERY_STRING_DELIM = "?"; //$NON-NLS-1$
   private static final String QUERY_STRING_DELIM = URI_DELIM; 
   
   /**
    * Get the protocol part of the uri.
    * @param aCustomInvokerUri
    */
   public static String getProtocolString( String aCustomInvokerUri )
   {
      int idx = aCustomInvokerUri.indexOf(URI_DELIM);
      String retVal = null;
      if (idx != -1) 
      {
         retVal = aCustomInvokerUri.substring(0, idx );
      }
      else
      {
        retVal = aCustomInvokerUri;   
      }
      return retVal;
      
   }
   
   /**
    * Return any query data or null if none was specified.
    * @param aCustomInvokerUri
    */
   public static String getQueryString( String aCustomInvokerUri )
   {
      String queryData = null;
      if( AeUtil.notNullOrEmpty( aCustomInvokerUri ) )
      {
         // we start with:
         //     protocol:some/value:query-string
         // or
         //     protocol:some/value?query-string
         // we just want the query-string
         String uriMinusProtocol = getUriMinusProtocol(aCustomInvokerUri);
         int qsOffset = getIndexOfQueryStringDelim(uriMinusProtocol);
         if( qsOffset != -1 && qsOffset != (uriMinusProtocol.length()-1) )
         {
            queryData = uriMinusProtocol.substring(qsOffset+1);
         }
            
      }
      return queryData;
   }
   
   /**
    * Gets the index of the query string delim, accounting for the legacy delim
    * of "?" versus the current delim of ":"
    * @param aInput
    */
   protected static int getIndexOfQueryStringDelim(String aInput)
   {
      int qsOffset = aInput.indexOf( QUERY_STRING_DELIM );
      if (qsOffset == -1)
      {
         return aInput.indexOf(LEGACY_QUERY_STRING_DELIM);
      }
      else
      {
         int legacyOffset = aInput.indexOf(LEGACY_QUERY_STRING_DELIM);
         
         if (legacyOffset != -1)
            return Math.min(legacyOffset, qsOffset);
         else
            return qsOffset;
      }
   }

   /**
    * Return the invoker portion of the uri.
    * eg: java:org.ae.Invoker?debug=true returns org.ae.Invoker
    * @param aCustomInvokerUri
    */
   public static String getInvokerString( String aCustomInvokerUri )
   {
      String invoker; 
      if (AeUtil.isNullOrEmpty(aCustomInvokerUri))
      {
         invoker = aCustomInvokerUri;
      }
      else
      {
         invoker = getUriMinusProtocol( aCustomInvokerUri ); 
         int secondOffset = getIndexOfQueryStringDelim(invoker);
         if( secondOffset != -1 )
         {
            invoker = invoker.substring(0, secondOffset);
         }
      }
      return invoker;
   }

   /**
    * Return the uri with the protocol prefix removed.
    * eg: java:org.net.Invoker:debug=true returns org.net.Invoker:debug=true
    * @param aCustomInvokerUri 
    */
   protected static String getUriMinusProtocol( String aCustomInvokerUri )
   {
      int firstColon = aCustomInvokerUri.indexOf( URI_DELIM );
      String uriMinusPrefix = aCustomInvokerUri.substring(firstColon+1);
      return uriMinusPrefix;
   }
   
   /**
    * Returns the QName given the address string. The address string takes
    * the format nameSpace:localName. Returns null if the address string value is empty or null.
    * @param aAddress string containing nameSpace:localName value. 
    * @return Qname from address field or null if unable to create a QName from the given string.
    */
   public static QName getQNameFromAddress(String aAddress)
   {
      QName qName = null;
      if (AeUtil.notNullOrEmpty(aAddress))
      {
         String nameSpace = ""; //$NON-NLS-1$
         String localName = ""; //$NON-NLS-1$        
         int idx = aAddress.lastIndexOf( URI_DELIM );
         if (idx  != -1 && idx < (aAddress.length()-1) ) {
            nameSpace = aAddress.substring(0,idx);
            localName = aAddress.substring(idx + 1);
         }
         else
         {
            localName = aAddress;
         }   
         qName = new QName(nameSpace, localName);
      } 
      return qName;
   }
   
}
