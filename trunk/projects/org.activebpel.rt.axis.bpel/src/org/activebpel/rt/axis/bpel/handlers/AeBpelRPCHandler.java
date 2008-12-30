//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis.bpel/src/org/activebpel/rt/axis/bpel/handlers/AeBpelRPCHandler.java,v 1.39 2007/01/26 23:02:0
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
package org.activebpel.rt.axis.bpel.handlers;

import org.apache.axis.constants.Style;
import org.apache.axis.constants.Use;

/**
 * The BPEL handler for web services under an Axis framework for RPC style binding. 
 */
public class AeBpelRPCHandler extends AeBpelHandler
{
   /**
    * @see org.activebpel.rt.axis.bpel.handlers.AeBpelHandler#getStyle()
    */
   protected Style getStyle()
   {
      return Style.RPC;
   }
   
   /**
    * @see org.activebpel.rt.axis.bpel.handlers.AeBpelHandler#getUse()
    */
   protected Use getUse()
   {
      return Use.ENCODED;
   }

   /**
    * @see org.activebpel.rt.axis.bpel.handlers.AeBpelHandler#getReceiveHandler()
    */
   protected String getReceiveHandler()
   {
      return "soap:RPC"; //$NON-NLS-1$
   }
}
