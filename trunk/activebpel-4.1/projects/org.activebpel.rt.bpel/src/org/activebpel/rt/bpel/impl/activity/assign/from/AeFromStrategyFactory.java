//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/assign/from/AeFromStrategyFactory.java,v 1.3 2006/08/16 18:22:0
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
package org.activebpel.rt.bpel.impl.activity.assign.from; 


import java.util.Map;

import org.activebpel.rt.bpel.def.activity.support.AeFromDef;
import org.activebpel.rt.bpel.def.io.readers.def.IAeFromStrategyNames;
import org.activebpel.rt.bpel.impl.activity.assign.AeCopyOperationComponentFactory;
import org.activebpel.rt.bpel.impl.activity.assign.IAeFrom;

/**
 * Factory for creating the from def based on its strategy name. This factory
 * assumes that the defs passed to it have already been adorned with their 
 * specific strategy name.
 */
public class AeFromStrategyFactory extends AeCopyOperationComponentFactory
{
   /** singleton instance of the factory */
   private static final AeFromStrategyFactory SINGLETON = new AeFromStrategyFactory();
   
   /**
    * protected ctor to force factory method usage 
    */
   protected AeFromStrategyFactory()
   {
      super();
   }
   
   /**
    * Populates the map with the strategy name to <from> strategery
    *  
    * @see org.activebpel.rt.bpel.impl.activity.assign.AeCopyOperationComponentFactory#initMap()
    */
   protected void initMap()
   {
      Map map = getMap();
      map.put(IAeFromStrategyNames.FROM_EXPRESSION, AeFromExpression.class);
      map.put(IAeFromStrategyNames.FROM_LITERAL, AeFromLiteral.class);
      map.put(IAeFromStrategyNames.FROM_PARTNER_LINK, AeFromPartnerLink.class);
      map.put(IAeFromStrategyNames.FROM_PROPERTY_ELEMENT, AeFromPropertyElement.class);
      map.put(IAeFromStrategyNames.FROM_PROPERTY_MESSAGE, AeFromPropertyMessage.class);
      map.put(IAeFromStrategyNames.FROM_PROPERTY_TYPE, AeFromPropertyType.class);
      map.put(IAeFromStrategyNames.FROM_VARIABLE_ELEMENT, AeFromVariableElement.class);
      map.put(IAeFromStrategyNames.FROM_VARIABLE_ELEMENT_QUERY, AeFromVariableElementWithQuery.class);
      map.put(IAeFromStrategyNames.FROM_VARIABLE_MESSAGE, AeFromVariableMessage.class);
      map.put(IAeFromStrategyNames.FROM_VARIABLE_MESSAGE_PART, AeFromVariableMessagePart.class);
      map.put(IAeFromStrategyNames.FROM_VARIABLE_MESSAGE_PART_QUERY, AeFromVariableMessagePartWithQuery.class);
      map.put(IAeFromStrategyNames.FROM_VARIABLE_TYPE, AeFromVariableType.class);
      map.put(IAeFromStrategyNames.FROM_VARIABLE_TYPE_QUERY, AeFromVariableTypeWithQuery.class);
   }
   
   /**
    * Creates the IAeFrom instance from the def object's strategy hint.
    * 
    * @param aDef
    */
   public static IAeFrom createFromStrategy(AeFromDef aDef)
   {
      return (IAeFrom) SINGLETON.create(aDef);
   }
}
 
