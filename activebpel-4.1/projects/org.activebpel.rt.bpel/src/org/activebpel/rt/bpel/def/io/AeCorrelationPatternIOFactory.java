//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/io/AeCorrelationPatternIOFactory.java,v 1.1 2006/07/14 15:46:5
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
package org.activebpel.rt.bpel.def.io; 

import org.activebpel.rt.bpel.def.IAeBPELConstants;
import org.activebpel.rt.bpel.def.activity.support.AeCorrelationDef;
import org.activebpel.rt.bpel.def.activity.support.AeCorrelationDef.AeCorrelationPatternType;

/**
 * factory for creating a class for reading/writing the correlation pattern value for a correlation. 
 */
public abstract class AeCorrelationPatternIOFactory
{
   /** reads and writes the pattern values for BPEL 1.1 */
   private static final IAeCorrelationPatternIO BPWS = new AeBaseCorrelationPatternIO(new String[] {"out", "in", "out-in"});  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
   
   /** reads and writes the pattern values for WS-BPEL 2.0 */
   private static final IAeCorrelationPatternIO WSBPEL20 = new AeBaseCorrelationPatternIO(new String[] {"request", "response", "request-response"}); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

   /**
    * private ctor to prevent instantiation 
    */
   private AeCorrelationPatternIOFactory()
   {
   }
   
   /**
    * Gets the IO class by the BPEL namespace
    * @param aNamespace
    */
   public static IAeCorrelationPatternIO getInstance(String aNamespace)
   {
      if (IAeBPELConstants.BPWS_NAMESPACE_URI.equals(aNamespace))
      {
         return BPWS;
      }
      else
      {
         return WSBPEL20;
      }
   }
   
   /**
    * I/O class that reads/writes correlation pattern types to/from strings given the legal values for the pattern attribute. 
    */
   private static class AeBaseCorrelationPatternIO implements IAeCorrelationPatternIO
   {
      /** index of request pattern */
      private static final int REQUEST_PATTERN = 0;
      /** index of response pattern */
      private static final int RESPONSE_PATTERN = 1;
      /** index of request-response pattern */
      private static final int REQUEST_RESPONSE_PATTERN = 2;
      
      /** values for the correlation patterns */
      private String[] mPatterns;
      
      /**
       * Ctor accepts patterns
       * @param aPatterns
       */
      public AeBaseCorrelationPatternIO(String[] aPatterns)
      {
         mPatterns = aPatterns;
      }
      
      /**
       * Getter for the patterns
       */
      protected String[] getPatternValues()
      {
         return mPatterns;
      }

      /**
       * @see org.activebpel.rt.bpel.def.io.IAeCorrelationPatternIO#fromString(java.lang.String)
       */
      public AeCorrelationPatternType fromString(String pattern)
      {
         if(getPatternValues()[REQUEST_PATTERN].equals(pattern))
         {
            return AeCorrelationPatternType.REQUEST;
         }
         else if (getPatternValues()[RESPONSE_PATTERN].equals(pattern))
         {
            return AeCorrelationPatternType.RESPONSE;
         }
         else if (getPatternValues()[REQUEST_RESPONSE_PATTERN].equals(pattern))
         {
            return AeCorrelationPatternType.REQUEST_RESPONSE;
         }
         else // handles invalid case
         {
            return new AeCorrelationDef.AeInvalidCorrelationPatternType(pattern);
         }
      }

      /**
       * @see org.activebpel.rt.bpel.def.io.IAeCorrelationPatternIO#toString(org.activebpel.rt.bpel.def.activity.support.AeCorrelationDef.AeCorrelationPatternType)
       */
      public String toString(AeCorrelationPatternType type)
      {
         if (type == AeCorrelationDef.AeCorrelationPatternType.REQUEST)
         {
            return getPatternValues()[REQUEST_PATTERN];
         }
         else if (type == AeCorrelationDef.AeCorrelationPatternType.RESPONSE)
         {
            return getPatternValues()[RESPONSE_PATTERN];
         }
         else if (type == AeCorrelationDef.AeCorrelationPatternType.REQUEST_RESPONSE)
         {
            return getPatternValues()[REQUEST_RESPONSE_PATTERN];
         }
         else // handles invalid values case
         {
            return type.toString();
         }
      }
   }
}
 
