// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/faults/AeMatch.java,v 1.2 2006/09/28 16:01:0
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
package org.activebpel.rt.bpel.def.faults;

/**
 * Definition for bpel fault handler and fault data match.
 */
public class AeMatch implements IAeMatch
{
   /** true if this curren match is the best match */
   private boolean mBestMatch;

   /** the priority order of this match. */
   private int mPriority;

   /** the substition group level of this match. */
   private int mSGLevel;

   /**
    * Default constructor
    */
   public AeMatch(boolean aBestMatch, int aPriority)
   {
      mBestMatch = aBestMatch;
      mPriority = aPriority;
   }

   /**
    * Returns true if this match is the best match.
    */
   public boolean isBestMatch()
   {
      return mBestMatch;
   }

   /**
    * Return true if this match is better than the given match. Only when two matches has the identical
    * priority, then the substitution group level will be tested for better match. 
    * @param aMatch
    */
   public boolean isBetterMatchThan(IAeMatch aMatch)
   {
      if ( this.mPriority < aMatch.getPriority()
            || (this.mPriority == aMatch.getPriority() && this.mSGLevel < aMatch.getSGLevel()) )
         return true;
      return false;
   }

   /**
    * @return Returns the priority.
    */
   public int getPriority()
   {
      return mPriority;
   }

   /**
    * @return Returns the substituion group level of this match.
    */
   public int getSGLevel()
   {
      return mSGLevel;
   }

   /**
    * Sets the substituon group level of this match.
    */
   public void setSGLevel(int aSGLevel)
   {
      mSGLevel = aSGLevel;
   }

}
