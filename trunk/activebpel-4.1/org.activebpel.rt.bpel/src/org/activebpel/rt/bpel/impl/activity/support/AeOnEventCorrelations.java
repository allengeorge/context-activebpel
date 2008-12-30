//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/support/AeOnEventCorrelations.java,v 1.2 2006/10/26 14:01:3
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
package org.activebpel.rt.bpel.impl.activity.support; 

import java.util.Iterator;

import org.activebpel.rt.bpel.def.AeCorrelationSetsDef;
import org.activebpel.rt.bpel.def.AeCorrelationsDef;
import org.activebpel.rt.bpel.def.activity.support.AeCorrelationDef;
import org.activebpel.rt.bpel.def.activity.support.AeOnEventDef;
import org.activebpel.rt.util.AeFilteredIterator;

/**
 * Impl for the correlations used for onEvent. There is special handling here to filter
 * out any references to correlation sets that resolve to the child scope. The reason
 * being that the child scope is not instantiated until a message arrives.    
 */
public class AeOnEventCorrelations extends AeIMACorrelations
{
   public AeOnEventCorrelations(AeCorrelationsDef aDef, AeOnEvent aParent)
   {
      super(aDef, aParent);
   }

   /**
    * Overrides to filter out any correlation sets that are defined within the onEvent's associated scope.
    * The onEvent's concurrency implementation results in the scope's creation being deferred until the message arrives.
    * As such, the scope and its correlation set info won't be available here - nor is it needed since the 
    * correlation information we're looking for is for initiated correlation sets only.
    * @see org.activebpel.rt.bpel.impl.activity.support.AeIMACorrelations#getInitiatedCorrelationDefs()
    */
   protected Iterator getInitiatedCorrelationDefs()
   {
      return new AeFilteredIterator(super.getInitiatedCorrelationDefs())
      {
         /**
          * @see org.activebpel.rt.util.AeFilteredIterator#accept(java.lang.Object)
          */
         protected boolean accept(Object aObject)
         {
            AeCorrelationDef corrDef = (AeCorrelationDef)aObject;
            String corrSetName = corrDef.getCorrelationSetName();
            AeOnEvent onEvent = (AeOnEvent) getParent();
            AeOnEventDef def = (AeOnEventDef) onEvent.getDefinition();
            AeCorrelationSetsDef scopeCorrelations = def.getChildScope().getCorrelationSetsDef();
            if (scopeCorrelations != null && scopeCorrelations.getCorrelationSetDef(corrSetName) != null)
            {
               return false;
            }
            return true;
         }
      };
   }
}
 
