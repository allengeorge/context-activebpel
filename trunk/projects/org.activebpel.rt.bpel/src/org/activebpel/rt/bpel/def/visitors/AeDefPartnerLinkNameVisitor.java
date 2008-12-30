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

import org.activebpel.rt.bpel.def.AePartnerLinkDef;
import org.activebpel.rt.bpel.def.AeProcessDef;

/**
 * This visitor is responsible for visiting all of the partner links and adding mappings for them
 * in the process def.
 */
public class AeDefPartnerLinkNameVisitor extends AeAbstractDefVisitor
{
   /** The process def. */
   private AeProcessDef mProcessDef;

   /**
    * Default c'tor.
    */
   public AeDefPartnerLinkNameVisitor()
   {
      setTraversalVisitor( new AeTraversalVisitor( new AeDefTraverser(), this ) );
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor#visit(org.activebpel.rt.bpel.def.AeProcessDef)
    */
   public void visit(AeProcessDef aDef)
   {
      setProcessDef(aDef);

      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor#visit(org.activebpel.rt.bpel.def.AePartnerLinkDef)
    */
   public void visit(AePartnerLinkDef aDef)
   {
      getProcessDef().addPartnerLinkMapping(aDef);

      super.visit(aDef);
   }

   /**
    * @return Returns the processDef.
    */
   protected AeProcessDef getProcessDef()
   {
      return mProcessDef;
   }

   /**
    * @param aProcessDef The processDef to set.
    */
   protected void setProcessDef(AeProcessDef aProcessDef)
   {
      mProcessDef = aProcessDef;
   }
}
