//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/validate/AeAbstractPddIterator.java,v 1.4 2006/07/18 20:05:3
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

import java.util.Iterator;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.def.validation.IAeBaseErrorReporter;
import org.activebpel.rt.bpel.server.deploy.bpr.IAeBpr;
import org.w3c.dom.Document;

/**
 * Base class for validators that need to iterator over all pdd files in the bpr. 
 */
public abstract class AeAbstractPddIterator implements IAePredeploymentValidator 
{

   /**
    * @see org.activebpel.rt.bpel.server.deploy.validate.IAePredeploymentValidator#validate(org.activebpel.rt.bpel.server.deploy.bpr.IAeBpr, org.activebpel.rt.bpel.def.validation.IAeBaseErrorReporter)
    */
   public void validate(IAeBpr aBprFile, IAeBaseErrorReporter aReporter)
         throws AeException
   {
      for( Iterator iter = aBprFile.getPddResources().iterator(); iter.hasNext(); )
      {
         String pddName = (String) iter.next();
         Document pddDom = aBprFile.getResourceAsDocument( pddName );
         AePddInfo pddInfo = new AePddInfo( pddName, pddDom );
         
         validateImpl( pddInfo, aBprFile, aReporter );
      }
   }
   
   /**
    * Perform the actual validation logic.
    * @param aPddInfo
    * @param aBprFile
    * @param aReporter
    * @throws AeException
    */
   protected abstract void validateImpl( AePddInfo aPddInfo, 
         IAeBpr aBprFile, IAeBaseErrorReporter aReporter ) throws AeException;
   
}
