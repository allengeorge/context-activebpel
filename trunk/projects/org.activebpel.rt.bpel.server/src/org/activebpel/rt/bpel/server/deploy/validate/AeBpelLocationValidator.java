// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/validate/AeBpelLocationValidator.java,v 1.7 2005/06/13 17:54:0
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

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.def.validation.IAeBaseErrorReporter;
import org.activebpel.rt.bpel.server.AeMessages;
import org.activebpel.rt.bpel.server.deploy.bpr.IAeBpr;

/**
 * Validates that the value of the locatio attribute on the pdd process element
 * points to a bpel file contained in the bpr. 
 */
/**
 *
 */
public class AeBpelLocationValidator extends AeAbstractPddIterator
{
   /** Missing BPEL error message template */
   private static final String MISSING_BPEL = AeMessages.getString("AeBpelLocationValidator.0"); //$NON-NLS-1$

   /**
    * @see org.activebpel.rt.bpel.server.deploy.validate.AeAbstractPddIterator#validateImpl(org.activebpel.rt.bpel.server.deploy.validate.AePddInfo, org.activebpel.rt.bpel.server.deploy.bpr.IAeBpr, org.activebpel.rt.bpel.def.validation.IAeBaseErrorReporter)
    */
   protected void validateImpl(AePddInfo aPddInfo, IAeBpr aBprFile, IAeBaseErrorReporter aReporter) throws AeException
   {
      if( !aBprFile.exists( aPddInfo.getBpelLocation() ) )
      {
         String[] args = {aPddInfo.getName(), aBprFile.getBprFileName(), aPddInfo.getBpelLocation()};
         aReporter.addError(MISSING_BPEL, args, null);
      }
   }
}
