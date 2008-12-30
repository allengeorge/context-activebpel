//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/validate/AeSaxErrorRelayHandler.java,v 1.2 2007/04/03 20:51:4
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
package org.activebpel.rt.bpel.server.deploy.validate;

import org.activebpel.rt.xml.AeXMLParserErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


/**
 * This class is a SAX handler that will relay all SAX errors and warnings to an instance of
 * <code>IAePddValidationErrorHandler</code>.
 */
public class AeSaxErrorRelayHandler extends AeXMLParserErrorHandler
{
   /** The pdd validation error handler to relay the sax errors/warning to. */
   private IAeResourceValidationErrorHandler mHandler;

   /**
    * Constructor.
    * 
    * @param aHandler
    */
   public AeSaxErrorRelayHandler(IAeResourceValidationErrorHandler aHandler)
   {
      mHandler = aHandler;
   }

   /**
    * @see org.xml.sax.ErrorHandler#warning(org.xml.sax.SAXParseException)
    */
   public void warning(SAXParseException exception)
   {
      mHandler.parseWarning(exception.getMessage(), exception.getLineNumber());
   }

   /**
    * @see org.xml.sax.ErrorHandler#error(org.xml.sax.SAXParseException)
    */
   public void error(SAXParseException exception)
   {
      mHandler.parseError(exception.getMessage(), exception.getLineNumber());
   }

   /**
    * @see org.xml.sax.ErrorHandler#fatalError(org.xml.sax.SAXParseException)
    */
   public void fatalError(SAXParseException exception) throws SAXException
   {
      mHandler.parseFatalError(exception.getMessage(), exception.getLineNumber());
   }
}
