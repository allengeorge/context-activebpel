// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/io/readers/def/IAeToStrategyKeys.java,v 1.3 2006/08/16 18:22:0
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
package org.activebpel.rt.bpel.def.io.readers.def;

/**
 * Sstrategies for selecting the "L" value in a copy operation.
 */
public interface IAeToStrategyKeys
{
   /*
    * Some to-spec strategy keys that can be used (no arguments).
    */
   public static final AeSpecStrategyKey KEY_TO_VARIABLE_TYPE = new AeSpecStrategyKey(IAeToStrategyNames.TO_VARIABLE_TYPE);
   public static final AeSpecStrategyKey KEY_TO_VARIABLE_TYPE_QUERY = new AeSpecStrategyKey(IAeToStrategyNames.TO_VARIABLE_TYPE_QUERY);
   public static final AeSpecStrategyKey KEY_TO_VARIABLE_MESSAGE_PART_QUERY = new AeSpecStrategyKey(IAeToStrategyNames.TO_VARIABLE_MESSAGE_PART_QUERY);
   public static final AeSpecStrategyKey KEY_TO_VARIABLE_MESSAGE_PART = new AeSpecStrategyKey(IAeToStrategyNames.TO_VARIABLE_MESSAGE_PART);
   public static final AeSpecStrategyKey KEY_TO_VARIABLE_MESSAGE = new AeSpecStrategyKey(IAeToStrategyNames.TO_VARIABLE_MESSAGE);
   public static final AeSpecStrategyKey KEY_TO_VARIABLE_ELEMENT_QUERY = new AeSpecStrategyKey(IAeToStrategyNames.TO_VARIABLE_ELEMENT_QUERY);
   public static final AeSpecStrategyKey KEY_TO_VARIABLE_ELEMENT = new AeSpecStrategyKey(IAeToStrategyNames.TO_VARIABLE_ELEMENT);
   public static final AeSpecStrategyKey KEY_TO_PROPERTY_TYPE = new AeSpecStrategyKey(IAeToStrategyNames.TO_PROPERTY_TYPE);
   public static final AeSpecStrategyKey KEY_TO_PROPERTY_MESSAGE = new AeSpecStrategyKey(IAeToStrategyNames.TO_PROPERTY_MESSAGE);
   public static final AeSpecStrategyKey KEY_TO_PROPERTY_ELEMENT = new AeSpecStrategyKey(IAeToStrategyNames.TO_PROPERTY_ELEMENT);
   public static final AeSpecStrategyKey KEY_TO_PARTNER_LINK = new AeSpecStrategyKey(IAeToStrategyNames.TO_PARTNER_LINK);
   public static final AeSpecStrategyKey KEY_TO_EXPRESSION = new AeSpecStrategyKey(IAeToStrategyNames.TO_EXPRESSION);
}
