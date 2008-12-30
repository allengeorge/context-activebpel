// $Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/xml/AeXMLParserDefaultHandler.java,v 1.1 2004/10/16 03:51:4
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

import javax.wsdl.xml.WSDLLocator;

import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Implements a SAX <code>DefaultHandler</code> that resolves entities and
 * optionally dispatches errors to a delegate error handler.
 */
public class AeXMLParserDefaultHandler extends DefaultHandler
{
   /** The WSDL locator for locating schema resources. */
   private final WSDLLocator mWSDLLocator;

   /** The delegate SAX <code>ErrorHandler</code>. */
   private final ErrorHandler mErrorHandler;

   /**
    * Constructor for entity resolver.
    */
   public AeXMLParserDefaultHandler(WSDLLocator aWSDLLocator)
   {
      this(aWSDLLocator, null);
   }

   /**
    * Constructor for entity resolver and error handler.
    */
   public AeXMLParserDefaultHandler(WSDLLocator aWSDLLocator, ErrorHandler aErrorHandler)
   {
      mWSDLLocator = aWSDLLocator;
      mErrorHandler = aErrorHandler;
   }

   /**
    * @see org.xml.sax.ErrorHandler#error(org.xml.sax.SAXParseException)
    */
   public void error(SAXParseException aException) throws SAXException
   {
      if (getErrorHandler() != null)
      {
         getErrorHandler().error(aException);
      }
   }

   /**
    * @see org.xml.sax.ErrorHandler#fatalError(org.xml.sax.SAXParseException)
    */
   public void fatalError(SAXParseException aException) throws SAXException
   {
      if (getErrorHandler() != null)
      {
         getErrorHandler().fatalError(aException);
      }
   }

   /**
    * Returns the delegate SAX <code>ErrorHandler</code>.
    */
   protected ErrorHandler getErrorHandler()
   {
      return mErrorHandler;
   }

   /**
    * Returns the WSDL locator for locating schema resources.
    */
   protected WSDLLocator getWSDLLocator()
   {
      return mWSDLLocator;
   }

   /**
    * @see org.xml.sax.EntityResolver#resolveEntity(java.lang.String, java.lang.String)
    */
   public InputSource resolveEntity(String publicId, String systemId)
   {
      WSDLLocator locator = getWSDLLocator();
      InputSource result = null;

      if (locator != null)
      {
         result = locator.getImportInputSource(locator.getBaseURI(), systemId);
      }

      return result;
   }

   /**
    * @see org.xml.sax.ErrorHandler#warning(org.xml.sax.SAXParseException)
    */
   public void warning(SAXParseException aException) throws SAXException
   {
      if (getErrorHandler() != null)
      {
         getErrorHandler().warning(aException);
      }
   }
}
