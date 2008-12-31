// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/io/readers/def/IAeFromStrategyKeys.java,v 1.2 2006/08/16 18:22:0
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
 * The from strategies .
 */
public interface IAeFromStrategyKeys
{
   /*
    * Some from-spec strategy keys that can be used (no arguments).
    */
   public static final AeSpecStrategyKey KEY_FROM_VARIABLE_TYPE = new AeSpecStrategyKey(IAeFromStrategyNames.FROM_VARIABLE_TYPE);
   public static final AeSpecStrategyKey KEY_FROM_VARIABLE_TYPE_QUERY = new AeSpecStrategyKey(IAeFromStrategyNames.FROM_VARIABLE_TYPE_QUERY);
   public static final AeSpecStrategyKey KEY_FROM_VARIABLE_MESSAGE_PART_QUERY = new AeSpecStrategyKey(IAeFromStrategyNames.FROM_VARIABLE_MESSAGE_PART_QUERY);
   public static final AeSpecStrategyKey KEY_FROM_VARIABLE_MESSAGE_PART = new AeSpecStrategyKey(IAeFromStrategyNames.FROM_VARIABLE_MESSAGE_PART);
   public static final AeSpecStrategyKey KEY_FROM_VARIABLE_MESSAGE = new AeSpecStrategyKey(IAeFromStrategyNames.FROM_VARIABLE_MESSAGE);
   public static final AeSpecStrategyKey KEY_FROM_VARIABLE_ELEMENT_QUERY = new AeSpecStrategyKey(IAeFromStrategyNames.FROM_VARIABLE_ELEMENT_QUERY);
   public static final AeSpecStrategyKey KEY_FROM_VARIABLE_ELEMENT = new AeSpecStrategyKey(IAeFromStrategyNames.FROM_VARIABLE_ELEMENT);
   public static final AeSpecStrategyKey KEY_FROM_PROPERTY_TYPE = new AeSpecStrategyKey(IAeFromStrategyNames.FROM_PROPERTY_TYPE);
   public static final AeSpecStrategyKey KEY_FROM_PROPERTY_MESSAGE = new AeSpecStrategyKey(IAeFromStrategyNames.FROM_PROPERTY_MESSAGE);
   public static final AeSpecStrategyKey KEY_FROM_PROPERTY_ELEMENT = new AeSpecStrategyKey(IAeFromStrategyNames.FROM_PROPERTY_ELEMENT);
   public static final AeSpecStrategyKey KEY_FROM_PARTNER_LINK = new AeSpecStrategyKey(IAeFromStrategyNames.FROM_PARTNER_LINK);
   public static final AeSpecStrategyKey KEY_FROM_LITERAL = new AeSpecStrategyKey(IAeFromStrategyNames.FROM_LITERAL);
   public static final AeSpecStrategyKey KEY_FROM_EXPRESSION = new AeSpecStrategyKey(IAeFromStrategyNames.FROM_EXPRESSION);
}
