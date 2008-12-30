//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/assign/AePropertyAliasBasedSelector.java,v 1.5 2006/12/14 22:54:3
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

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.impl.AeNamespaceResolver;
import org.activebpel.rt.bpel.xpath.AeXPathHelper;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.wsdl.def.IAePropertyAlias;
import org.activebpel.rt.xml.IAeNamespaceContext;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * Selects a value from a variable using a property alias.
 */
public class AePropertyAliasBasedSelector
{
   /**
    * Selects a value from a variable using a property alias
    * @param aPropAlias property alias to use for the selction
    * @param aDataContext context to use for the propertyAlias query
    * @param aContext provides means to execute the query and any additional contextual info like config settings
    * @throws AeBusinessProcessException
    */
   public static Object selectValue(IAePropertyAlias aPropAlias, Object aDataContext, IAeCopyOperationContext aContext) throws AeBusinessProcessException
   {
      Object data = aDataContext;
      if (data instanceof Node && AeUtil.notNullOrEmpty(aPropAlias.getQuery()))
      {
         Node docElem = data instanceof Document? ((Document) data).getDocumentElement() : (Node)data;
         IAeNamespaceContext namespaceResolver = new AeNamespaceResolver(aPropAlias);
         // TODO (EPW) can't assume XPath 1.0 for BPEL 2.0
         data = aContext.executeQuery(aPropAlias.getQuery(), docElem, namespaceResolver);
         data = AeXPathHelper.getInstance(aContext.getBPELNamespace()).unwrapXPathValue(data);

         if (data == null && aContext.isCreateXPathAllowed())
            data = AeCreateXPathUtil.findOrCreateXPath(aPropAlias.getQuery(), docElem.getOwnerDocument(), aContext, namespaceResolver);
      }
      
      return data;
   }
}
 
