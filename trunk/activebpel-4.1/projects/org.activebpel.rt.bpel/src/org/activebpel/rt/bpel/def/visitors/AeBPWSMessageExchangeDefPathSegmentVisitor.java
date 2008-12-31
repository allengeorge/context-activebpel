//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/visitors/AeBPWSMessageExchangeDefPathSegmentVisitor.java,v 1.1 2006/11/14 19:47:3
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

import org.activebpel.rt.bpel.def.AeMessageExchangeDef;
import org.activebpel.rt.bpel.def.AeMessageExchangesDef;
import org.activebpel.rt.bpel.def.IAeBPELConstants;

/**
 * A def path visitor extension for BPEL4WS 1.1 which also assigns paths for message exchanges.
 */
public class AeBPWSMessageExchangeDefPathSegmentVisitor extends AeBPWSDefPathSegmentVisitor
{

   /**
    * Overrides method to assign a segment path.
    * @see org.activebpel.rt.bpel.def.visitors.AeBPWSDefPathSegmentVisitor#visit(org.activebpel.rt.bpel.def.AeMessageExchangesDef)
    */
   public void visit(AeMessageExchangesDef aDef)
   {
      setPathSegment(IAeBPELConstants.TAG_MESSAGE_EXCHANGES);
   }

   /**
    *
    * Overrides method to assign a segment path.
    * @see org.activebpel.rt.bpel.def.visitors.AeBPWSDefPathSegmentVisitor#visit(org.activebpel.rt.bpel.def.AeMessageExchangeDef)
    */
   public void visit(AeMessageExchangeDef aDef)
   {
      setPathSegment(IAeBPELConstants.TAG_MESSAGE_EXCHANGE);
   }

}
