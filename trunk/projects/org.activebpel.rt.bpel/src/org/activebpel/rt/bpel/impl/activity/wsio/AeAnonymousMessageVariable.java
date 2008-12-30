// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/wsio/AeAnonymousMessageVariable.java,v 1.1 2006/08/03 23:33:0
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
package org.activebpel.rt.bpel.impl.activity.wsio;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.def.AeVariableDef;
import org.activebpel.rt.bpel.impl.AeVariable;
import org.activebpel.rt.message.AeMessagePartsMap;

/**
 * Implements an anonymous message variable.
 */
public class AeAnonymousMessageVariable extends AeVariable
{
   /**
    * Constructs an anonymous message variable with the given message parts map.
    *
    * @param aMessagePartsMap
    */
   public AeAnonymousMessageVariable(AeMessagePartsMap aMessagePartsMap) throws AeBusinessProcessException
   {
      super(new AeAnonymousVariableContainer(), new AeVariableDef(aMessagePartsMap));

      getParent().addVariable(this);
   }

   /**
    * Overrides method to do nothing for anonymous variable.
    *
    * @see org.activebpel.rt.bpel.IAeVariable#incrementVersionNumber()
    */
   public void incrementVersionNumber()
   {
      // Do nothing for anonymous variable.
   }
}
