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
 * interface for the fault matching rules
 */
public interface IAeMatch
{
   /**
    * Returns true if this match is the best match.
    */
   public boolean isBestMatch();

   /**
    * Return true if this match is better than the given match
    * @param aMatch
    */
   public boolean isBetterMatchThan(IAeMatch aMatch);

   /**
    * @return Returns the priority of this match.
    */
   public int getPriority();

   /**
    * @return Returns the substituion group level of this match.
    */
   public int getSGLevel();

   /**
    * Sets the substituon group level of this match.
    */
   public void setSGLevel(int sgLevel);

}
