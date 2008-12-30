//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/assign/to/AeToStrategyFactory.java,v 1.4 2006/08/16 18:22:0
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
package org.activebpel.rt.bpel.impl.activity.assign.to; 

import java.util.Map;

import org.activebpel.rt.bpel.def.activity.support.AeToDef;
import org.activebpel.rt.bpel.def.io.readers.def.IAeToStrategyNames;
import org.activebpel.rt.bpel.impl.activity.assign.AeCopyOperationComponentFactory;
import org.activebpel.rt.bpel.impl.activity.assign.IAeTo;

/**
 * Factory for creating strategies for implementing the <to> portion of a copy
 * operation.
 */
public class AeToStrategyFactory extends AeCopyOperationComponentFactory
{
   /** singleton instance */
   private static final AeToStrategyFactory SINGLETON = new AeToStrategyFactory();
   
   /**
    * private ctor to enforce singleton
    */
   private AeToStrategyFactory()
   {
      super();
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.activity.assign.AeCopyOperationComponentFactory#initMap()
    */
   protected void initMap()
   {
      Map map = getMap();

      map.put(IAeToStrategyNames.TO_PARTNER_LINK, AeToPartnerLink.class);
      map.put(IAeToStrategyNames.TO_PROPERTY_ELEMENT, AeToPropertyElement.class);
      map.put(IAeToStrategyNames.TO_PROPERTY_MESSAGE, AeToPropertyMessage.class);
      map.put(IAeToStrategyNames.TO_PROPERTY_TYPE, AeToPropertyType.class);
      map.put(IAeToStrategyNames.TO_VARIABLE_ELEMENT, AeToVariableElement.class);
      map.put(IAeToStrategyNames.TO_VARIABLE_ELEMENT_QUERY, AeToVariableElementWithQuery.class);
      map.put(IAeToStrategyNames.TO_VARIABLE_MESSAGE, AeToVariableMessage.class);
      map.put(IAeToStrategyNames.TO_VARIABLE_MESSAGE_PART, AeToVariableMessagePart.class);
      map.put(IAeToStrategyNames.TO_VARIABLE_MESSAGE_PART_QUERY, AeToVariableMessagePartWithQuery.class);
      map.put(IAeToStrategyNames.TO_VARIABLE_TYPE, AeToVariableType.class);
      map.put(IAeToStrategyNames.TO_VARIABLE_TYPE_QUERY, AeToVariableTypeWithQuery.class);
      // Note: ToExpression Should never get created because we create specific strategies depending
      // on the structure of the expression (ToVariableMessagePart, ToVariableMessagePartQuery, 
      // ToVariableType, ToVariableElement, or ToVariableElementQuery).  This is done by the
      // visitor that sets the strategy on the def.
      map.put(IAeToStrategyNames.TO_EXPRESSION, null);
   }
   
   /**
    * Creates the strategy used to determine the location of the data for the copy operation.
    * @param aDef
    */
   public static IAeTo createToStrategy(AeToDef aDef)
   {
      return (IAeTo) SINGLETON.create(aDef);
   }
}
 
