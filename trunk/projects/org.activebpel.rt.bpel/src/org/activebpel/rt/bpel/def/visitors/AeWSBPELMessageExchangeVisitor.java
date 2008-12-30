//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/visitors/AeWSBPELMessageExchangeVisitor.java,v 1.1 2006/09/22 19:52:3
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

import org.activebpel.rt.bpel.def.AeMessageExchangesDef;
import org.activebpel.rt.bpel.def.activity.support.AeOnEventDef;

/**
 * Adds support for the onEvent's child scope as a root scope
 */
public class AeWSBPELMessageExchangeVisitor extends AeMessageExchangeVisitor
{
   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeOnEventDef)
    */
   public void visit(AeOnEventDef aDef)
   {
      // this visitor is run prior to the static analysis code so it's possible the onEvent is invalid and doesn't have a child scope.
      if (aDef.getChildScope() != null)
      {
         AeMessageExchangesDef msgExsDef = getOrCreateMessageExchangesDef(aDef.getChildScope().getScopeDef());
         msgExsDef.setDefaultDeclared(true);
      }

      super.visit(aDef);
   }
}
 
