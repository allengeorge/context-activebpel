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

import java.util.Iterator;

/**
 * Constructs that can be the parent of a 'partnerLinks' container def should implement this
 * interface.
 */
public interface IAePartnerLinksParentDef
{
   /**
    * Gets the partner links container def.
    */
   public AePartnerLinksDef getPartnerLinksDef();

   /**
    * Sets the partner links container def.
    *
    * @param aDef
    */
   public void setPartnerLinksDef(AePartnerLinksDef aDef);

   /**
    * Gets the partner link with the given name.
    *
    * @param aPartnerLinkName
    */
   public AePartnerLinkDef getPartnerLinkDef(String aPartnerLinkName);

   /**
    * Gets the partner link defs.
    */
   public Iterator getPartnerLinkDefs();
}
