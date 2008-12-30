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

import java.util.Set;

import org.activebpel.rt.AeException;


/**
 * This exception is thrown when an ambiguous partner link name is used to resolve a partner link.
 * Since partner links can be defined at the scope level, names can be re-used and therefore shadow
 * earlier declarations.  When that happens, the full path to the partner link must be used.  If
 * the partner link name alone is used in that case, this exception is thrown.
 */
public class AeAmbiguousPartnerLinkException extends AeException
{
   /** The list of locations that map to the partner link name. */
   private Set mPartnerLinkLocations;

   /**
    * Constructs the exception.
    *
    * @param aPartnerLinkLocations
    */
   public AeAmbiguousPartnerLinkException(Set aPartnerLinkLocations)
   {
      super();
      setPartnerLinkLocations(aPartnerLinkLocations);
   }

   /**
    * @return Returns the partnerLinkLocations.
    */
   public Set getPartnerLinkLocations()
   {
      return mPartnerLinkLocations;
   }

   /**
    * @param aPartnerLinkLocations The partnerLinkLocations to set.
    */
   protected void setPartnerLinkLocations(Set aPartnerLinkLocations)
   {
      mPartnerLinkLocations = aPartnerLinkLocations;
   }
}
