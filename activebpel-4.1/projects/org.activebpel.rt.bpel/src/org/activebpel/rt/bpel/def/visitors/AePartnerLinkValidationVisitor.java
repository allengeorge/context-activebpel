//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/visitors/AePartnerLinkValidationVisitor.java,v 1.2 2007/09/12 02:48:1
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

import org.activebpel.rt.bpel.def.activity.AeActivityInvokeDef;

/**
 * Visitor used to determine if a partner link name is being used in an invoke.
 */
public class AePartnerLinkValidationVisitor extends AeAbstractSearchVisitor
{
   /** The partner link name being validated */
   private String mPartnerLinkName;
   /** Flag indicating if invoke reference was found */
   private boolean mReferenceFound;

   /**
    * Constructor.
    * @param aPartnerLinkName The partner link name we are validating
    */
   public AePartnerLinkValidationVisitor(String aPartnerLinkName)
   {
      mPartnerLinkName = aPartnerLinkName;

      setTraversalVisitor(new AeTraversalVisitor(new AeDefTraverser(), this));      
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityInvokeDef)
    */
   public void visit(AeActivityInvokeDef aDef)
   {
      // fixme (MF) seems like this should be comparing location paths, not just the plink name. This isn't safe for BPEL 2.0 since plinks can be defined within scopes. 
      if (aDef.getPartnerLinkDef() != null && mPartnerLinkName.equals(aDef.getPartnerLinkDef().getName()))
         mReferenceFound = true;
      
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractSearchVisitor#isFound()
    */
   public boolean isFound()
   {
      return mReferenceFound;
   }
}
