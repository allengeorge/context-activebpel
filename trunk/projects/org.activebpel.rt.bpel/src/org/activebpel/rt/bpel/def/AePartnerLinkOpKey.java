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

import org.activebpel.rt.bpel.impl.AePartnerLinkOpImplKey;


/**
 * This class combines a partner link and an operation into a key that can be used in maps and
 * the like.  It is also convenient to use when passing partner link:operation pairs around.
 */
public class AePartnerLinkOpKey extends AePartnerLinkDefKey
{
   /** The operation. */
   private String mOperation;
   
   /**
    * Constructs the partner link op key from all of the necessary parts.
    * 
    * @param aPartnerLinkName
    * @param aPartnerLinkId
    * @param aOperation
    */
   public AePartnerLinkOpKey(String aPartnerLinkName, int aPartnerLinkId, String aOperation)
   {
      super(aPartnerLinkName, aPartnerLinkId);
      setOperation(aOperation);
   }
   
   /**
    * Constructs the partner link op key from the partner link def and the operation.
    * 
    * @param aPartnerLinkDef
    * @param aOperation
    */
   public AePartnerLinkOpKey(AePartnerLinkDef aPartnerLinkDef, String aOperation)
   {
      super(aPartnerLinkDef);
      setOperation(aOperation);
   }

   /**
    * Constructs the key from the partner link and operation.
    * 
    * @param aPartnerLinkKey
    * @param aOperation
    */
   public AePartnerLinkOpKey(AePartnerLinkDefKey aPartnerLinkKey, String aOperation)
   {
      super(aPartnerLinkKey.getPartnerLinkName(), aPartnerLinkKey.getPartnerLinkId());
      setOperation(aOperation);
   }

   /**
    * @return Returns the operation.
    */
   public String getOperation()
   {
      return mOperation;
   }

   /**
    * @param aOperation The operation to set.
    */
   protected void setOperation(String aOperation)
   {
      mOperation = aOperation;
   }
   
   /**
    * @see java.lang.Object#hashCode()
    */
   public int hashCode()
   {
      return super.hashCode() ^ getOperation().hashCode();
   }
   
   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aObj)
   {
      if (aObj instanceof AePartnerLinkOpKey)
      {
         AePartnerLinkOpKey other = (AePartnerLinkOpKey) aObj;
         return super.equals(other) && getOperation().equals(other.getOperation());
      }

      return super.equals(aObj);
   }

   /**
    * @see java.lang.Comparable#compareTo(java.lang.Object)
    */
   public int compareTo(Object aObj)
   {
      int rval = super.compareTo(aObj);

      if (aObj instanceof AePartnerLinkOpImplKey && rval == 0)
      {
         AePartnerLinkOpImplKey other = (AePartnerLinkOpImplKey) aObj;
         rval = getOperation().compareTo(other.getOperation());
      }

      return rval;
   }

   /**
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      StringBuffer buff = new StringBuffer();
      synchronized(buff)
      {
         buff.append(super.toString());
         buff.append("\n"); //$NON-NLS-1$
         buff.append("Operation: "); //$NON-NLS-1$
         buff.append(getOperation());
      }
      return buff.toString();
   }
}
