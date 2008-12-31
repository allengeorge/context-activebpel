//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/io/readers/def/IAeFromStrategyNames.java,v 1.3 2006/08/16 18:22:0
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
 * Names for the from strategies 
 */
public interface IAeFromStrategyNames
{
   /** 
    * Selects a simple or complex type variable
    *  
    * <from variable="V1"/>
    */
   public static final String FROM_VARIABLE_TYPE = "from-variable-type"; //$NON-NLS-1$

   /** 
    * Selects a simple or complex type variable
    *  
    * <from variable="typeVar">
    *    <query queryLanguage="urn:language:uri">/path/to/data</query>
    * </from>
    */
   public static final String FROM_VARIABLE_TYPE_QUERY = "from-variable-type-query"; //$NON-NLS-1$

   /** 
    * Selects data from a message variable part using a query 
    * 
    *   <from variable="msg" part="partName" query="/some/xpath"/>       [bpel 1.1]
    *      or
    *   <from variable="msg" part="partName">
    *      <query queryLanguage="urn:language:uri">/some/xpath</query>   [bpel 2.0]
    *   </from>
    */
   public static final String FROM_VARIABLE_MESSAGE_PART_QUERY = "from-variable-message-part-query"; //$NON-NLS-1$

   /**
    * Selects a single part from a message variable 
    * 
    * <from variable="msg" part="partName"/>
    */
   public static final String FROM_VARIABLE_MESSAGE_PART = "from-variable-message-part"; //$NON-NLS-1$

   /** 
    * Selects the whole message variable 
    * 
    * <from variable="msg"/> 
    */
   public static final String FROM_VARIABLE_MESSAGE = "from-variable-message"; //$NON-NLS-1$
   
   /** 
    * Selects data from element using a query  
    *  
    * <from variable="element" query="/ns:some/xpath"/>                [bpel 1.1]
    *    or
    * <from variable="element">
    *    <query queryLanguage="urn:language:uri">/some/xpath</query>   [bpel 2.0]
    * </from>
    */
   public static final String FROM_VARIABLE_ELEMENT_QUERY = "from-variable-element-query"; //$NON-NLS-1$
   
   /** 
    * Selects element variable 
    * 
    * <from variable="element"/> 
    */
   public static final String FROM_VARIABLE_ELEMENT = "from-variable-element"; //$NON-NLS-1$
   
   /** 
    * Selects data from a complex type or simple type variable using a property alias 
    * 
    * <from variable="V1" property="ns:propName"/> 
    */
   public static final String FROM_PROPERTY_TYPE = "from-property-type"; //$NON-NLS-1$
   
   /** 
    * Selects data from a message variable using a property alias 
    * 
    * <from variable="msg" property="ns:propName"/> 
    */
   public static final String FROM_PROPERTY_MESSAGE = "from-property-message"; //$NON-NLS-1$
   
   /** 
    * Selects data from an element variable using a property alias 
    * 
    * <from variable="element" property="ns:propName"/> 
    */
   public static final String FROM_PROPERTY_ELEMENT = "from-property-element"; //$NON-NLS-1$
   
   /** 
    * Selects endpoint reference data from a partner link
    * 
    * <from partnerLink='plink' endpointReference='myRole|partnerRole'/> 
    */
   public static final String FROM_PARTNER_LINK = "from-partnerLink"; //$NON-NLS-1$
   
   /** 
    * Selects an EII or TII from literal xml 
    * 
    * <from><literal>Hello</literal></from>
    * or 
    * <from><literal><myLiteralElement xmlns=''>Hello</myLiteralElement></literal></from> 
    */
   public static final String FROM_LITERAL = "from-literal"; //$NON-NLS-1$
   
   /** 
    * Executes expression to generate data for copy
    * 
    * <from expressionLanguage='some-lang'>$myVariable * Math.random()</from> 
    */
   public static final String FROM_EXPRESSION = "from-expression"; //$NON-NLS-1$
   
}
 
