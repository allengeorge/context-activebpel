//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/io/readers/def/IAeToStrategyNames.java,v 1.5 2006/08/16 18:22:0
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
 * Names of the strategies for selecting the "L" value in a copy operation
 */
public interface IAeToStrategyNames
{
   /** 
    * selects variable of simple or complex type 
    * 
    * <to variable='V1'/> 
    */
   public static final String TO_VARIABLE_TYPE = "to-variable-type"; //$NON-NLS-1$

   /**
    * selects variable of simple or complex type with a query 
    * 
    * <to variable="typeVar">
    *   <query queryLanguage="urn:queryLang:uri">path/to/data</query>
    * </to>
    */
   public static final String TO_VARIABLE_TYPE_QUERY = "to-variable-type-query"; //$NON-NLS-1$

   /** 
    * selects data within a message part using a query 
    * 
    * <to variable='msg' part='part' query='/some/xpath'/>                [bpel 1.1]
    *    or
    * <to variable='msg' part='part' query='/some/xpath'>
    *    <query queryLanguage="urn:queryLang:uri">path/to/data</query>    [bpel 2.0]
    * </to>
    */
   public static final String TO_VARIABLE_MESSAGE_PART_QUERY = "to-variable-message-part-query"; //$NON-NLS-1$
   
   /** 
    * selects a message part 
    * 
    * <to variable='msg' part='part'/> 
    */
   public static final String TO_VARIABLE_MESSAGE_PART = "to-variable-message-part"; //$NON-NLS-1$
   
   /** 
    * selects whole message 
    * 
    * <to variable='msg'/> 
    */
   public static final String TO_VARIABLE_MESSAGE = "to-variable-message"; //$NON-NLS-1$
   
   /** 
    * selects data within an element using a query 
    * 
    * <to variable='myElement'/>
    *    or
    * <to variable='myElement'>
    *    <query queryLanguage="urn:queryLang:uri">path/to/data</query>    [bpel 2.0]
    * </to>
    * 
    */
   public static final String TO_VARIABLE_ELEMENT_QUERY = "to-variable-element-query"; //$NON-NLS-1$
   
   /** 
    * selects variable that's an element 
    * 
    * <to variable='myElement'/> 
    */
   public static final String TO_VARIABLE_ELEMENT = "to-variable-element"; //$NON-NLS-1$
   
   /** 
    * selects variable of simple or complex type using a property alias 
    * 
    * WSBPEL 2.0 
    *
    * <to variable='V1' property='ns:prop'/>
    */
   public static final String TO_PROPERTY_TYPE = "to-property-type"; //$NON-NLS-1$
   
   /** 
    * selects data from a message variable using a property alias 
    * 
    * <to variable='msg' property='ns:prop'/> 
    */
   public static final String TO_PROPERTY_MESSAGE = "to-property-message"; //$NON-NLS-1$
   
   /** 
    * selects variable element using a property alias 
    * 
    * WSBPEL 2.0
    * 
    *  <to variable='myElement' property='ns:prop'/>
    */
   public static final String TO_PROPERTY_ELEMENT = "to-property-element"; //$NON-NLS-1$
   
   /** 
    * selects a partner link to assign to its partner role 
    * 
    * <to partnerLink='myLink'/> 
    */
   public static final String TO_PARTNER_LINK = "to-partnerLink"; //$NON-NLS-1$

   /**
    * Executes expression to select lValue 
    * 
    * <to expressionLanguage="urn:expressionLang:uri">$varName.partName/path/to/data</to> 
    */
   public static final String TO_EXPRESSION = "to-expression"; //$NON-NLS-1$
}
 
