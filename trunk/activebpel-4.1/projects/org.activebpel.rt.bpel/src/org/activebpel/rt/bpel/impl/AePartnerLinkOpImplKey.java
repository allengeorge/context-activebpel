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
package org.activebpel.rt.bpel.impl;

import org.activebpel.rt.bpel.def.AePartnerLinkOpKey;

/**
 * This class implements a partner link impl key.  It extends the partner link def key and simply
 * adds the instance specific information (instance based location and id).
 */
public class AePartnerLinkOpImplKey extends AePartnerLinkOpKey implements Comparable
{
   /** The location path of the partner link */
   private String mPartnerLinkLocationPath;

   /**
    * Constructs the partner link impl key from the partner link.  This constructor uses the 
    * def information as the instance info (with the assumption that this particular partner
    * link has no specific instance info).
    * 
    * @param aPartnerLink
    * @param aOperation
    */
   public AePartnerLinkOpImplKey(AePartnerLink aPartnerLink, String aOperation)
   {
      super(aPartnerLink.getDefinition(), aOperation);

      setPartnerLinkLocationPath(aPartnerLink.getLocationPath());
   }

   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aObj)
   {
      if (aObj instanceof AePartnerLinkOpImplKey)
      {
         return compareTo(aObj) == 0;
      }
      else
      {
         return super.equals(aObj);
      }
   }

   /**
    * @see java.lang.Object#hashCode()
    */
   public int hashCode()
   {
      return getPartnerLinkLocationPath().hashCode() ^ getOperation().hashCode();
   }

   /**
    * @see org.activebpel.rt.bpel.def.AePartnerLinkDefKey#toString()
    */
   public String toString()
   {
      StringBuffer buff = new StringBuffer();
      synchronized(buff)
      {
         buff.append(super.toString());
         buff.append("\nLoc Path:  "); //$NON-NLS-1$
         buff.append(getPartnerLinkLocationPath());
      }
      return buff.toString();
   }

   /**
    * @see java.lang.Comparable#compareTo(java.lang.Object)
    */
   public int compareTo(Object aObj)
   {
      if (aObj instanceof AePartnerLinkOpImplKey)
      {
         AePartnerLinkOpImplKey other = (AePartnerLinkOpImplKey) aObj;
         int rval = getPartnerLinkLocationPath().compareTo(other.getPartnerLinkLocationPath());
         if (rval == 0)
            rval = getOperation().compareTo(other.getOperation());

         return rval;
      }
      return super.compareTo(aObj);
   }

   /**
    * @return Returns the locationPath of the partner link.
    */
   public String getPartnerLinkLocationPath()
   {
      return mPartnerLinkLocationPath;
   }

   /**
    * @param aLocationPath The locationPath of the partner link 
    */
   public void setPartnerLinkLocationPath(String aLocationPath)
   {
      mPartnerLinkLocationPath = aLocationPath;
   }
}
