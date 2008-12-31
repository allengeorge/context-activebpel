// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.ext.expr/src/org/activebpel/rt/bpel/ext/expr/AeBuildNumber.java,v 1.1 2005/06/08 13:11:3
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
package org.activebpel.rt.bpel.ext.expr;

import java.io.IOException;
import java.util.Properties;

/**
 * Resource file which contains the build number and build date.
 */
public class AeBuildNumber
{
   private static String mBuildNumber = ""; //$NON-NLS-1$
   private static String mBuildDate   = ""; //$NON-NLS-1$

   static
   {
      try
      {
         Properties props = new Properties();
         props.load(new AeBuildNumber().getClass().getResourceAsStream("version.properties")); //$NON-NLS-1$
      
         mBuildNumber = props.getProperty("build.number"); //$NON-NLS-1$
         mBuildDate   = props.getProperty("build.date"); //$NON-NLS-1$
      }
      catch (IOException ioe)
      {
      }
   }

   /**
    * default constructor for a build resource file
    */
   private AeBuildNumber()
   {
   }

   /**
    * Obtains the build number for this component.
    * 
    * @return a String value representing the build number
    */
   public final static String getBuildNumber() 
   {
      return mBuildNumber;
   }
   
   /**
    * Obtains the build date for this component.
    * 
    * @return a String value representing the build date
    */
   public final static String getBuildDate() 
   {
      return mBuildDate;
   }
}
