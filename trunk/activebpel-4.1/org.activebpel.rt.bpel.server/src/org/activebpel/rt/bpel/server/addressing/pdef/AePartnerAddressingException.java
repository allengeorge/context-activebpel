// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/addressing/pdef/AePartnerAddressingException.java,v 1.3 2006/02/24 16:37:3
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
package org.activebpel.rt.bpel.server.addressing.pdef;

import org.activebpel.rt.bpel.server.deploy.AeDeploymentException;

/**
 * General exception for partner definition addressing layer.
 */
public class AePartnerAddressingException extends AeDeploymentException
{
   
   /**
    * Constructor.
    * 
    * @param aMessage
    */
   public AePartnerAddressingException(String aMessage)
   {
      super(aMessage);
   }


   /**
    * Constructor.
    * 
    * @param aMessage
    * @param aCause
    */
   public AePartnerAddressingException(String aMessage, Throwable aCause)
   {
      super(aMessage, aCause);
   }

}
