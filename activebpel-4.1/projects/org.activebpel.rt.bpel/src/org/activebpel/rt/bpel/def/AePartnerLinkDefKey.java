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


/**
 * This key identifies a single partner link in the Def layer.
 */
public class AePartnerLinkDefKey implements Comparable
{
   /** The partner link name. */
   private String mPartnerLinkName;
   /** The partner link id. */
   private int mPartnerLinkId;

   /**
    * Constructs a key with all of the component parts.
    *
    * @param aPartnerLinkName
    * @param aPartnerLinkId
    */
   public AePartnerLinkDefKey(String aPartnerLinkName, int aPartnerLinkId)
   {
      super();
      setPartnerLinkName(aPartnerLinkName);
      setPartnerLinkId(aPartnerLinkId);
   }

   /**
    * Constructs a key from the given partner link.
    *
    * @param aPartnerLinkDef
    */
   public AePartnerLinkDefKey(AePartnerLinkDef aPartnerLinkDef)
   {
      this(aPartnerLinkDef.getName(), aPartnerLinkDef.getLocationId());
   }

   /**
    * @return Returns the partnerLinkId.
    */
   public int getPartnerLinkId()
   {
      return mPartnerLinkId;
   }

   /**
    * @param aPartnerLinkId The partnerLinkId to set.
    */
   protected void setPartnerLinkId(int aPartnerLinkId)
   {
      mPartnerLinkId = aPartnerLinkId;
   }

   /**
    * @return Returns the partnerLinkName.
    */
   public String getPartnerLinkName()
   {
      return mPartnerLinkName;
   }

   /**
    * @param aPartnerLinkName The partnerLinkName to set.
    */
   protected void setPartnerLinkName(String aPartnerLinkName)
   {
      mPartnerLinkName = aPartnerLinkName;
   }

   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aObj)
   {
      if (aObj instanceof AePartnerLinkDefKey)
      {
         AePartnerLinkDefKey other = (AePartnerLinkDefKey) aObj;
         // Legacy issue here - the partner link id may be -1 (if it came out of an old DB, for example).  In
         // that case, we'll test for equality based on the partner link name only.
         if (getPartnerLinkId() == -1 || other.getPartnerLinkId() == -1)
            return getPartnerLinkName().equals(other.getPartnerLinkName());
         else
            return getPartnerLinkId() == other.getPartnerLinkId();
      }
      return false;
   }

   /**
    * @see java.lang.Object#hashCode()
    */
   public int hashCode()
   {
      // Use the partner link name as the hash code for legacy 
      // reasons.  Partner Link Def keys that came from old DB
      // versions may not have a valid partner link id.
      return getPartnerLinkName().hashCode();
   }

   /**
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      StringBuffer buff = new StringBuffer();
      synchronized(buff)
      {
         buff.append("Name:     "); //$NON-NLS-1$
         buff.append(getPartnerLinkName());
         buff.append("\n"); //$NON-NLS-1$
         buff.append("Id:       "); //$NON-NLS-1$
         buff.append(getPartnerLinkId());
      }
      return buff.toString();
   }

   /**
    * @see java.lang.Comparable#compareTo(java.lang.Object)
    */
   public int compareTo(Object aObj)
   {
      if (aObj instanceof AePartnerLinkDefKey)
      {
         AePartnerLinkDefKey other = (AePartnerLinkDefKey) aObj;
         // Legacy issue here - the partner link id may be -1 (if it came out of an old DB, for example).  In
         // that case, we'll do the comparison based on the partner link name only.
         if (getPartnerLinkId() == -1 || other.getPartnerLinkId() == -1)
            return getPartnerLinkName().compareTo(other.getPartnerLinkName());
         else
            return new Integer(getPartnerLinkId()).compareTo(new Integer(other.getPartnerLinkId()));
      }
      return -1;
   }
}
