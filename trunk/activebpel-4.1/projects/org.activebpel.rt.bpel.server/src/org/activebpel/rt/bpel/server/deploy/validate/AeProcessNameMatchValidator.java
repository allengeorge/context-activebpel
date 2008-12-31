//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/validate/AeProcessNameMatchValidator.java,v 1.6 2006/07/18 20:05:3
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

import javax.xml.namespace.QName;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.def.validation.IAeBaseErrorReporter;
import org.activebpel.rt.bpel.server.AeMessages;
import org.activebpel.rt.bpel.server.deploy.bpr.IAeBpr;
import org.activebpel.rt.util.AeUtil;
import org.w3c.dom.Document;

/**
 * Ensure that the process name in the pdd its matching bpel file are the same.
 */
public class AeProcessNameMatchValidator extends AeAbstractPddIterator
{
   
   private static final String NO_MATCH = AeMessages.getString("AeProcessNameMatchValidator.0"); //$NON-NLS-1$
   
   /**
    * @see org.activebpel.rt.bpel.server.deploy.validate.AeAbstractPddIterator#validateImpl(org.activebpel.rt.bpel.server.deploy.validate.AePddInfo, org.activebpel.rt.bpel.server.deploy.bpr.IAeBpr, org.activebpel.rt.bpel.def.validation.IAeBaseErrorReporter)
    */
   protected void validateImpl(AePddInfo aPddInfo, IAeBpr aBprFile, IAeBaseErrorReporter aReporter) 
      throws AeException
   {
      QName nameFromPdd = aPddInfo.getProcessQName();
      QName nameFromBpel = extractFromBpel( aPddInfo.getBpelLocation(), aBprFile );
      
      if( !AeUtil.compareObjects(nameFromPdd, nameFromBpel) )
      {
         String[] args = { aPddInfo.getName(), aPddInfo.getBpelLocation() };
         aReporter.addError( NO_MATCH, args, null );
      }
   }
   
   
   /**
    * Extract the process qname from the bpel <code>Document</code>.
    * @param aLocation BPEL resource location.
    * @param aBprFile
    * @throws AeException
    */
   protected QName extractFromBpel( String aLocation, IAeBpr aBprFile ) throws AeException
   {
      Document bpelDom = aBprFile.getResourceAsDocument(aLocation);
      String localPart = bpelDom.getDocumentElement().getAttribute( NAME_ATTR );
      String namespace = bpelDom.getDocumentElement().getAttribute( TARGET_NAMESPACE_ATTR );
      return new QName( namespace, localPart );
   }
}
