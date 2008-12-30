// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/convert/visitors/AeBPWSToWSBPELExtensionActivityVisitor.java,v 1.3 2006/10/30 22:47:2
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
package org.activebpel.rt.bpel.def.convert.visitors;

import org.activebpel.rt.bpel.def.AeCompensationHandlerDef;
import org.activebpel.rt.bpel.def.AeExtensionDef;
import org.activebpel.rt.bpel.def.AeExtensionsDef;
import org.activebpel.rt.bpel.def.AeProcessDef;
import org.activebpel.rt.bpel.def.IAeBPELConstants;
import org.activebpel.rt.bpel.def.activity.AeActivityBreakDef;
import org.activebpel.rt.bpel.def.activity.AeActivityContinueDef;
import org.activebpel.rt.bpel.def.activity.AeActivitySuspendDef;

/**
 * This visitor is used when converting from BPWS to WSBPEL.  It is responsible for adding an
 * appropriate extension def for the break, suspend, and continue activities.  In addition, it
 * adds an extension def if a process level compensation handler exists.
 */
public class AeBPWSToWSBPELExtensionActivityVisitor extends AeAbstractBPWSToWSBPELVisitor
{
   /**
    * Constructor.
    */
   public AeBPWSToWSBPELExtensionActivityVisitor()
   {
      super();
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor#visit(org.activebpel.rt.bpel.def.AeCompensationHandlerDef)
    */
   public void visit(AeCompensationHandlerDef aDef)
   {
      if (aDef.getParent() instanceof AeProcessDef)
      {
         addAeProcessCompensationExtensionDef();
      }
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityBreakDef)
    */
   public void visit(AeActivityBreakDef aDef)
   {
      addAeActivityExtensionDef();
      super.visit(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityContinueDef)
    */
   public void visit(AeActivityContinueDef aDef)
   {
      addAeActivityExtensionDef();
      super.visit(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivitySuspendDef)
    */
   public void visit(AeActivitySuspendDef aDef)
   {
      addAeActivityExtensionDef();
      super.visit(aDef);
   }

   /**
    * Adds an extension def to the list of extension defs for the standard Ae-Activity extension
    * namespace.
    */
   protected void addAeProcessCompensationExtensionDef()
   {
      addExtensionDef(IAeBPELConstants.AE_EXTENSION_NAMESPACE_URI_ALLOW_PROCESS_COORDINATION);
   }
   
   /**
    * Adds an extension def to the list of extension defs for the standard Ae-Activity extension
    * namespace.
    */
   protected void addAeActivityExtensionDef()
   {
      addExtensionDef(IAeBPELConstants.AE_EXTENSION_NAMESPACE_URI_ACTIVITY);
   }

   /**
    * Adds an extension def to the list of extension defs for the given namespace.
    * 
    * @param aNamespace
    */
   protected void addExtensionDef(String aNamespace)
   {
      AeExtensionsDef extensionsDef = getExtensionsDef();
      if (!extensionsDef.hasExtensionDef(aNamespace))
      {
         AeExtensionDef extensionDef = new AeExtensionDef();
         extensionDef.setNamespace(aNamespace);
         extensionDef.setMustUnderstand(true);
         extensionsDef.addExtensionDef(extensionDef);
      }
   }

   /**
    * Gets the extensions def from the process def.
    */
   protected AeExtensionsDef getExtensionsDef()
   {
      AeExtensionsDef extensionsDef = getProcessDef().getExtensionsDef();
      if (extensionsDef == null)
      {
         extensionsDef = new AeExtensionsDef();
         getProcessDef().setExtensionsDef(extensionsDef);
      }
      return extensionsDef;
   }
}
