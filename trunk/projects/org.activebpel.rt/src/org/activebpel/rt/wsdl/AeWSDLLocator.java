// $Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/wsdl/AeWSDLLocator.java,v 1.3 2006/02/17 14:53:1
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
package org.activebpel.rt.wsdl;

import org.activebpel.rt.AeException;
import org.activebpel.rt.IAeWSDLFactory;

import javax.wsdl.xml.WSDLLocator;

import org.xml.sax.InputSource;

/**
 * Helper class used by WSDL reader to obtain WSDL files which are located in
 * the WSR file.
 */
public class AeWSDLLocator implements WSDLLocator
{
   /** The base WSDL file given during contruction */
   private String mBaseURI;
   /** The last import file which was requested */
   private String mLastImportURI;
   /** The WSDL factory used to load */
   private IAeWSDLFactory mWSDLFactory;

   /**
    * WSDL locator object used by WSDL reader to load WSDL files
    * @param aFactory the factory from which to load WSDL resources
    * @param aBaseURI the URI for the base WSDL file
    */
   public AeWSDLLocator(IAeWSDLFactory aFactory, String aBaseURI)
   {
      mWSDLFactory = aFactory;
      mBaseURI = aBaseURI;
   }

   /**
    * @see javax.wsdl.xml.WSDLLocator#getBaseInputSource()
    */
   public InputSource getBaseInputSource()
   {
      return getInputSource(getBaseURI());
   }

   /**
    * @see javax.wsdl.xml.WSDLLocator#getBaseURI()
    */
   public String getBaseURI()
   {
      return mBaseURI;
   }

   /**
    * @see javax.wsdl.xml.WSDLLocator#getImportInputSource(java.lang.String, java.lang.String)
    */
   public InputSource getImportInputSource(String aParentLocation, String aImportLocation)
   {
      mLastImportURI = aImportLocation;
      return getInputSource(aImportLocation);
   }

   /**
    * @see javax.wsdl.xml.WSDLLocator#getLatestImportURI()
    */
   public String getLatestImportURI()
   {
      return mLastImportURI;
   }

   /**
    * Returns the input source for the given WSDL URI or null if not found.
    * @param aURI the WSDL file we are looking for
    */
   private InputSource getInputSource(String aURI)
   {
      InputSource inSource = null;
      try
      {
         inSource = mWSDLFactory.getWSDLSource(aURI);
         inSource.setSystemId(aURI);
      }
      catch(AeException e)
      {
         e.printStackTrace();
      }

      return inSource;
   }
}
