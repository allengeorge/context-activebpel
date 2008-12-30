// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/AePartnerLinkDef.java,v 1.11 2006/11/16 23:33:3
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
package org.activebpel.rt.bpel.def;

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.def.visitors.IAeDefVisitor;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.wsdl.def.IAePartnerLinkType;

/**
 * Definition for bpel partner link.
 */
public class AePartnerLinkDef extends AeNamedDef
{
   /** The partner link type name. */
   private QName mPartnerLinkTypeName;
   /** The my role. */
   private String mMyRole;
   /** The partner role. */
   private String mPartnerRole;
   /** The partner link type. */
   private IAePartnerLinkType mPartnerLinkType;
   /** The initialize partner role flag. */
   private Boolean mInitializePartnerRole;

   /**
    * Default constructor
    */
   public AePartnerLinkDef()
   {
      super();
   }

   /**
    * @return Returns the partnerLinkType.
    */
   public IAePartnerLinkType getPartnerLinkType()
   {
      return mPartnerLinkType;
   }

   /**
    * @param aPartnerLinkType The partnerLinkType to set.
    */
   public void setPartnerLinkType(IAePartnerLinkType aPartnerLinkType)
   {
      mPartnerLinkType = aPartnerLinkType;
   }
   
   /**
    * Accessor method to obtain the Partner Link Type for this partner.
    * 
    * @return partner link type of the partner
    */
   public QName getPartnerLinkTypeName()
   {
      return mPartnerLinkTypeName;
   }

   /**
    * Mutator method to set the Partner Link Type for this partner.
    * 
    * @param aPartnerLinkType the Partner Link Type for this partner
    */
   public void setPartnerLinkTypeName(QName aPartnerLinkType)
   {
      mPartnerLinkTypeName = aPartnerLinkType;
   }

   /**
    * Accessor method to obtain my role for this partner link.
    * 
    * @return my role for this partner link
    */
   public String getMyRole()
   {
      return mMyRole;
   }
   
   /**
    * Mutator method to set myRole for this partner link.
    * 
    * @param aMyRole my role for this partner link
    */
   public void setMyRole(String aMyRole)
   {
      mMyRole = aMyRole;
   }
   
   /**
    * Getter for the myRoe portType
    */
   public QName getMyRolePortType()
   {
      if (AeUtil.notNullOrEmpty(getMyRole()) && getPartnerLinkType() != null)
      {
         return getPartnerLinkType().findRole(getMyRole()).getPortType().getQName();
      }
      return null;
   }
   
   /**
    * Getter for the partnerRole portType
    */
   public QName getPartnerRolePortType()
   {
      if (AeUtil.notNullOrEmpty(getPartnerRole()) && getPartnerLinkType() != null)
      {
         return getPartnerLinkType().findRole(getPartnerRole()).getPortType().getQName();
      }
      return null;
   }

   /**
    * Accessor method to obtain the partner role for this partner link.
    * 
    * @return partner role for this partner link
    */
   public String getPartnerRole()
   {
      return mPartnerRole;
   }

   /**
    * Mutator method to set the partner role for this partner link.
    * 
    * @param aPartnerRole partner role for this partner link.
    */
   public void setPartnerRole(String aPartnerRole)
   {
      mPartnerRole = aPartnerRole;
   }

   /**
    * @return Returns the initializePartnerRole.
    */
   public Boolean getInitializePartnerRole()
   {
      return mInitializePartnerRole;
   }

   /**
    * @param aInitializePartnerRole The initializePartnerRole to set.
    */
   public void setInitializePartnerRole(Boolean aInitializePartnerRole)
   {
      mInitializePartnerRole = aInitializePartnerRole;
   }

   /**
    * @see org.activebpel.rt.bpel.def.AeBaseDef#accept(org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void accept(IAeDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }
}
