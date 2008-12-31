// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/assign/AeWSBPELCopyStrategyFactoryImpl.java,v 1.2 2007/02/16 17:24:3
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
package org.activebpel.rt.bpel.impl.activity.assign;

import org.activebpel.rt.bpel.def.IAeBPELConstants;
import org.w3c.dom.Element;

/**
 * A WSBPEL version of a copy strategy factory.
 */
public class AeWSBPELCopyStrategyFactoryImpl extends AeAbstractCopyStrategyFactoryImpl
{
   /**
    * Overrides the no-op method in super to add check for xsi:nil attribute on
    * EII to AII and EII to TII copy operations.
    * 
    * @see org.activebpel.rt.bpel.impl.activity.assign.AeAbstractCopyStrategyFactoryImpl#adjustIndex(org.activebpel.rt.bpel.impl.activity.assign.AeAbstractCopyStrategyFactoryImpl.AeStrategyIndex)
    */
   protected void adjustIndex(AeStrategyIndex aIndex)
   {
      if (aIndex.getFromType() == ELEMENT_TYPE && (aIndex.getToType() == TEXT_TYPE || aIndex.getToType() == ATTR_TYPE))
      {
         Element elem = (Element) aIndex.getFromData();
         if (elem.getAttributeNS(IAeBPELConstants.W3C_XML_SCHEMA_INSTANCE, "nil").equals("true")) //$NON-NLS-1$ //$NON-NLS-2$
         {
            aIndex.setFromType(NULL);
         }
      }
   }
}
