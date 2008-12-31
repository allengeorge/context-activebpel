//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/visitors/preprocess/strategies/AeBaseSpec.java,v 1.1 2006/08/18 22:20:3
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
package org.activebpel.rt.bpel.def.visitors.preprocess.strategies; 

import java.util.BitSet;

/**
 * Provides a wrapper around a bit set for use in analyzing def objects and determining
 * the runtime strategy for some feature (i.e. copying data, receiving message data, producing message data..etc)
 */
public class AeBaseSpec
{
   /** used to record the bit flags */
   protected BitSet mBits = new BitSet();

   /**
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aObject)
   {
      if (aObject instanceof AeBaseSpec)
      {
         AeBaseSpec other = (AeBaseSpec) aObject;
         return mBits.equals(other.mBits);
      }
      return false;
   }

   /**
    * Setter for the given bit
    * @param aConstant
    */
   public void set(int aConstant)
   {
      mBits.set(aConstant);
   }

   /**
    * @see java.lang.Object#hashCode()
    */
   public int hashCode()
   {
      return mBits.hashCode();
   }
}
