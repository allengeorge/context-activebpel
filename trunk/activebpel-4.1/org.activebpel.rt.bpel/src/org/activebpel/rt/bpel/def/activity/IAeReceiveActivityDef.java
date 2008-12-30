//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/activity/IAeReceiveActivityDef.java,v 1.3 2006/09/27 00:36:2
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
package org.activebpel.rt.bpel.def.activity; 

import org.activebpel.rt.bpel.def.AeBaseDef;

/**
 * This method exposes some useful methods for determining if the receive 
 * (or onMessage) is a one-way or request-response. 
 */
public interface IAeReceiveActivityDef extends IAePartnerLinkActivityDef
{
   /**
    * Returns true if the receive is a one-way message that doesn't have a reply.
    */
   public boolean isOneWay();
   
   /**
    * Set by the visitor during the creation of the process def after its determined
    * if the receive is a one way or request-response.
    * 
    * @param aFlag
    */
   public void setOneWay(boolean aFlag);
   
   /**
    * Getter for the message exchange
    */
   public String getMessageExchange();
   
   /**
    * Getter for the location path
    */
   public String getLocationPath();
   
   /**
    * Gets the context to use for resolving references to plinks, variables, and correlation sets
    */
   public AeBaseDef getContext();
   
}
 
