//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/convert/AeBPWSToWSBPELAbstractProcessConverter.java,v 1.2 2006/09/28 14:22:1
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

/**
 * Def convert for converting bpws 1.1. abstract processes to wsbpel 2.0 abstract processes.
 */
public class AeBPWSToWSBPELAbstractProcessConverter extends AeBPWSToWSBPELConverter
{

   /**
    * Default ctor.
    */
   public AeBPWSToWSBPELAbstractProcessConverter()
   {
      super();
   }

   /**
    * @see org.activebpel.rt.bpel.def.convert.AeAbstractBpelDefConverter#getNewBpelNamespace()
    */
   protected String getNewBpelNamespace()
   {
      return IAeBPELConstants.WSBPEL_2_0_ABSTRACT_NAMESPACE_URI;
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.convert.AeAbstractBpelDefConverter#getNewBpelNamespacePrefix()
    */
   protected String getNewBpelNamespacePrefix()
   {
      return IAeBPELConstants.ABSTRACT_PROC_PREFIX;
   }   
}
