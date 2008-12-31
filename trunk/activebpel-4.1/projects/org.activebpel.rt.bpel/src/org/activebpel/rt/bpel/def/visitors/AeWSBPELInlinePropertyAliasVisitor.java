//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/visitors/AeWSBPELInlinePropertyAliasVisitor.java,v 1.2 2006/10/05 21:15:3
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
package org.activebpel.rt.bpel.def.visitors; 

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.IAeContextWSDLProvider;
import org.activebpel.rt.bpel.IAeExpressionLanguageFactory;
import org.activebpel.rt.message.AeMessagePartsMap;
import org.activebpel.rt.wsdl.def.IAePropertyAlias;

/**
 * Provides WS-BPEL 2.0 logic for inlining propertyAliases
 */
public class AeWSBPELInlinePropertyAliasVisitor extends AeInlinePropertyAliasVisitor
{
   /**
    * Ctor
    * @param aProvider
    * @param aExpressionLanguageFactory
    */
   protected AeWSBPELInlinePropertyAliasVisitor(IAeContextWSDLProvider aProvider, IAeExpressionLanguageFactory aExpressionLanguageFactory)
   {
      super(aProvider, aExpressionLanguageFactory);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeInlinePropertyAliasVisitor#cacheCorrelationPropertyAlias(org.activebpel.rt.message.AeMessagePartsMap, javax.xml.namespace.QName)
    */
   protected boolean cacheCorrelationPropertyAlias(AeMessagePartsMap aMessagePartsMap, QName aPropName)
   {
      boolean found = super.cacheCorrelationPropertyAlias(aMessagePartsMap, aPropName);
      if (!found && aMessagePartsMap.isSinglePartElement())
      {
         found = cachePropertyAlias(IAePropertyAlias.ELEMENT_TYPE, aMessagePartsMap.getSingleElementPart(), aPropName);
      }
      return found;
   }
}
 
