//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/IAePartnerLinkOperationUser.java,v 1.1 2006/08/16 22:07:2
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
package org.activebpel.rt.bpel.def.validation; 

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.def.validation.activity.IAeCorrelationUser;

/**
 * Interface for a model that uses a partner link.
 */
public interface IAePartnerLinkOperationUser extends IAeValidator, IAeCorrelationUser
{
   /**
    * True if the model makes use of the partnerRole of the plink
    */
   public boolean isPartnerRole();
   
   /**
    * True if the model makes use of the myRole role of the plink
    */
   public boolean isMyRole();

   /**
    * Getter for the portType of the model
    */
   public QName getPortType();
   
   /**
    * Getter for the operation of the model
    */
   public String getOperation();
}
 
