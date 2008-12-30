//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/assign/to/AeToPartnerLink.java,v 1.3 2006/07/14 15:46:5
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
package org.activebpel.rt.bpel.impl.activity.assign.to; 

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.def.activity.support.AeToDef;

/**
 * Returns the partner link as a target for the copy 
 */
public class AeToPartnerLink extends AeToBase
{
   /** partner link name */
   private String mPartnerLink;
   
   /**
    * Ctor accepts def 
    * 
    * @param aToDef
    */
   public AeToPartnerLink(AeToDef aToDef)
   {
      this(aToDef.getPartnerLink());
   }
   
   /**
    * Ctor accepts plink name 
    * 
    * @param aPartnerLinkName
    */
   public AeToPartnerLink(String aPartnerLinkName)
   {
      super();
      setPartnerLink(aPartnerLinkName);
   }

   
   /**
    * @see org.activebpel.rt.bpel.impl.activity.assign.IAeTo#getTarget()
    */
   public Object getTarget() throws AeBusinessProcessException
   {
      return getCopyOperation().getContext().getPartnerLinkForUpdate(getPartnerLink());
   }

   /**
    * @return Returns the partnerLink.
    */
   public String getPartnerLink()
   {
      return mPartnerLink;
   }

   /**
    * @param aPartnerLink The partnerLink to set.
    */
   public void setPartnerLink(String aPartnerLink)
   {
      mPartnerLink = aPartnerLink;
   }
}
 
