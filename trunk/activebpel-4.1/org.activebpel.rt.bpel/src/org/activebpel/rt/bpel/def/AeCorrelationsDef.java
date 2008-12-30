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
package org.activebpel.rt.bpel.def;

import java.util.Iterator;

import org.activebpel.rt.bpel.def.activity.support.AeCorrelationDef;
import org.activebpel.rt.bpel.def.visitors.IAeDefVisitor;


/**
 * Container class for <code>correlations</code> that are a part of <code>receive</code>,
 * <code>reply</code>, <code>invoke</code>, and <code>pick</code>.
 */
public class AeCorrelationsDef extends AeBaseContainer
{
   /**
    * Default c'tor.
    */
   public AeCorrelationsDef()
   {
      super();
   }

   /**
    * Adds a correlation def to the container.
    * 
    * @param aDef
    */
   public void addCorrelationDef(AeCorrelationDef aDef)
   {
      super.add(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.AeBaseDef#accept(org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void accept(IAeDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }

   /**
    * @return Returns the requestFlag.
    */
   public boolean isRequestPatternUsed()
   {
      for (Iterator iter = getValues(); iter.hasNext();)
      {
         AeCorrelationDef def = (AeCorrelationDef) iter.next();
         if (def.getPattern() != null && def.getPattern().isRequestDataUsed())
         {
            return true;
         }
      }
      return false;
   }

   /**
    * @return Returns the responseFlag.
    */
   public boolean isResponsePatternUsed()
   {
      for (Iterator iter = getValues(); iter.hasNext();)
      {
         AeCorrelationDef def = (AeCorrelationDef) iter.next();
         if (def.getPattern() != null && def.getPattern().isResponseDataUsed())
         {
            return true;
         }
      }
      return false;
   }
}
