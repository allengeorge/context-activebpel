//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/assign/IAeCopyOperationContext.java,v 1.9 2006/12/14 22:55:1
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

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeContextWSDLProvider;
import org.activebpel.rt.bpel.IAeVariable;
import org.activebpel.rt.bpel.def.IAeExpressionDef;
import org.activebpel.rt.bpel.function.IAeFunctionExecutionContext;
import org.activebpel.rt.bpel.impl.AePartnerLink;
import org.activebpel.rt.bpel.xpath.AeXPathHelper;
import org.activebpel.rt.wsdl.def.IAePropertyAlias;
import org.activebpel.rt.xml.IAeNamespaceContext;
import org.activebpel.rt.xml.schema.AeTypeMapping;

/**
 * The context for the copy operation.
 */
public interface IAeCopyOperationContext extends IAeNamespaceContext
{
   /**
    * Returns the type mapping helper for the engine for schema to java and back.
    */
   public AeTypeMapping getTypeMapping();
   
   /**
    * Getter for the variable
    * @param aName
    */
   public IAeVariable getVariable(String aName);
   
   /**
    * Getter for a variable that will be updated
    * @param aName - Name of the variable being updated
    * @param aPartName - Name of the part which will be assigned to or null if 
    *                    it't not a message variable
    */
   public IAeVariable getVariableForUpdate(String aName, String aPartName);
   
   /**
    * Executes the expression
    * @param aDef
    * @throws AeBusinessProcessException
    */
   public Object executeExpression(IAeExpressionDef aDef) throws AeBusinessProcessException;
   
   /**
    * Getter for the partner link
    * @param aName
    */
   public AePartnerLink getPartnerLink(String aName);
   
   /**
    * Getter for the partner link that will be updated
    * @param aName
    */
   public AePartnerLink getPartnerLinkForUpdate(String aName);
   
   /**
    * Getter for the property alias.
    * @param aPropertyAliasType
    * @param aName
    * @param aPropName
    * @throws AeBusinessProcessException
    */
   public IAePropertyAlias getPropertyAlias(int aPropertyAliasType, QName aName, QName aPropName) throws AeBusinessProcessException;
   
   /**
    * Executes the query using the provided context and the default namespace context
    * @param aQueryExpression
    * @param aContext
    * @throws AeBusinessProcessException
    */
   public Object executeQuery(String aQueryExpression, Object aContext) throws AeBusinessProcessException;
   
   /**
    * Executes the query using the provided context and namespace context
    * @param aQueryExpression
    * @param aContext
    * @param aNamespaceContext
    * @throws AeBusinessProcessException
    */
   public Object executeQuery(String aQueryExpression, Object aContext, IAeNamespaceContext aNamespaceContext) throws AeBusinessProcessException;

   /**
    * Returns true if empty query selections are allowed.
    */
   public boolean isEmptyQuerySelectionAllowed();
   
   /**
    * Returns true if the create xpath extension is enabled
    */
   public boolean isCreateXPathAllowed();
   
   /**
    * Creates a function context for use in executing functions within a XPath <strong>query</strong>.  Note
    * that this method is not used (and not to be used) when executing a BPEL expression, as it
    * assumes XPath.
    * 
    * @param aContext
    */
   public IAeFunctionExecutionContext createFunctionExecutionContext(Object aContext, AeXPathHelper aXPathHelper);
   
   /**
    * Gets the BPEL namespace
    */
   public String getBPELNamespace();
   
   
   public IAeContextWSDLProvider getContextWSDLProvider();
   
}
