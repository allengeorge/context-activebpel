//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/visitors/AeDefPartnerLinkTypeVisitor.java,v 1.4 2006/10/26 13:34:4
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

import org.activebpel.rt.bpel.AeWSDLDefHelper;
import org.activebpel.rt.bpel.IAeContextWSDLProvider;
import org.activebpel.rt.bpel.def.AePartnerLinkDef;
import org.activebpel.rt.wsdl.def.AeBPELExtendedWSDLDef;
import org.activebpel.rt.wsdl.def.IAePartnerLinkType;

/**
 * Sets the IAePartnerLinkType reference on each of the partner link def objects. 
 */
public class AeDefPartnerLinkTypeVisitor extends AeAbstractDefVisitor
{
   /** wsdl provider used to find partner link type definition elements */
   private IAeContextWSDLProvider mProvider;
   
   /**
    * Creates the visitor with the default traverser 
    */
   public AeDefPartnerLinkTypeVisitor(IAeContextWSDLProvider aProvider)
   {
      setTraversalVisitor( new AeTraversalVisitor(new AeDefTraverser(), this));
      mProvider = aProvider;
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AePartnerLinkDef)
    */
   public void visit(AePartnerLinkDef aDef)
   {
      AeBPELExtendedWSDLDef wsdlDef = AeWSDLDefHelper.getWSDLDefinitionForPLT(mProvider, aDef.getPartnerLinkTypeName());
      // Note: if the wsdlDef is null then the plink is unresolved. This is caught during static analysis.
      if (wsdlDef != null)
      {
         IAePartnerLinkType plType = wsdlDef.getPartnerLinkType(aDef.getPartnerLinkTypeName().getLocalPart());
         // Note: if the plType is null then the plink is unresolved. This is caught during static analysis.
         aDef.setPartnerLinkType(plType);
      }
      super.visit(aDef);
   }
}
 
