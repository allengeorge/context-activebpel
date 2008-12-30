// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/convert/AeBPWSToWSBPELConverter.java,v 1.7 2006/11/16 23:34:4
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
package org.activebpel.rt.bpel.def.convert;

import org.activebpel.rt.bpel.def.IAeBPELConstants;
import org.activebpel.rt.bpel.def.convert.visitors.AeBPWSToWSBPELCatchVisitor;
import org.activebpel.rt.bpel.def.convert.visitors.AeBPWSToWSBPELCorrelationInitiateVisitor;
import org.activebpel.rt.bpel.def.convert.visitors.AeBPWSToWSBPELExpressionVisitor;
import org.activebpel.rt.bpel.def.convert.visitors.AeBPWSToWSBPELExtensionActivityVisitor;
import org.activebpel.rt.bpel.def.convert.visitors.AeBPWSToWSBPELOnAlarmVisitor;
import org.activebpel.rt.bpel.def.convert.visitors.AeBPWSToWSBPELOnEventVisitor;

/**
 * A BPEL Def converter that converts a def from version 1.1 (BPWS) to version 2.0 (WS-BPEL 2.0).
 */
public class AeBPWSToWSBPELConverter extends AeAbstractBpelDefConverter
{
   /**
    * Default c'tor.
    */
   public AeBPWSToWSBPELConverter()
   {
   }

   /**
    * @see org.activebpel.rt.bpel.def.convert.AeAbstractBpelDefConverter#getNewBpelNamespace()
    */
   protected String getNewBpelNamespace()
   {
      return IAeBPELConstants.WSBPEL_2_0_NAMESPACE_URI;
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.convert.AeAbstractBpelDefConverter#getNewBpelNamespacePrefix()
    */
   protected String getNewBpelNamespacePrefix()
   {
      return IAeBPELConstants.WSBPEL_2_0_PREFIX;
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.convert.AeAbstractBpelDefConverter#doConversion()
    */
   protected void doConversion()
   {
      getProcessDef().accept(new AeBPWSToWSBPELExpressionVisitor());
      getProcessDef().accept(new AeBPWSToWSBPELOnEventVisitor());
      getProcessDef().accept(new AeBPWSToWSBPELOnAlarmVisitor());
      getProcessDef().accept(new AeBPWSToWSBPELCatchVisitor());
      getProcessDef().accept(new AeBPWSToWSBPELCorrelationInitiateVisitor());
      getProcessDef().accept(new AeBPWSToWSBPELExtensionActivityVisitor());
   }
}
