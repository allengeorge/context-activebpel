// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/io/writers/def/AeBPWSWriterVisitor.java,v 1.4 2006/11/04 16:34:2
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
package org.activebpel.rt.bpel.def.io.writers.def;

import org.activebpel.rt.bpel.def.AeActivityDef;
import org.activebpel.rt.bpel.def.AeBaseDef;
import org.activebpel.rt.bpel.def.AeProcessDef;
import org.activebpel.rt.bpel.def.IAeBPELConstants;
import org.activebpel.rt.bpel.def.activity.AeActivityCompensateScopeDef;
import org.activebpel.rt.bpel.def.activity.AeActivityIfDef;
import org.activebpel.rt.bpel.def.activity.AeActivityScopeDef;
import org.activebpel.rt.bpel.def.activity.AeActivityWaitDef;
import org.activebpel.rt.bpel.def.activity.AeActivityWhileDef;
import org.activebpel.rt.bpel.def.activity.support.AeElseDef;
import org.activebpel.rt.bpel.def.activity.support.AeElseIfDef;
import org.activebpel.rt.bpel.def.activity.support.AeForEachBranchesDef;
import org.activebpel.rt.bpel.def.activity.support.AeFromDef;
import org.activebpel.rt.bpel.def.activity.support.AeIfDef;
import org.activebpel.rt.bpel.def.activity.support.AeJoinConditionDef;
import org.activebpel.rt.bpel.def.activity.support.AeOnAlarmDef;
import org.activebpel.rt.bpel.def.activity.support.AeSourceDef;
import org.activebpel.rt.bpel.def.activity.support.AeToDef;
import org.activebpel.rt.bpel.def.activity.support.AeVarDef;
import org.activebpel.rt.bpel.def.io.IAeBpelLegacyConstants;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.w3c.dom.Element;

/**
 * An implementation of a writer visitor for bpel4ws 1.1.
 */
public class AeBPWSWriterVisitor extends AeWriterVisitor
{
   /**
    * Constructs a bpel4ws writer visitor.
    *
    * @param aDef
    * @param aParentElement
    * @param aNamespace
    * @param aTagName
    */
   public AeBPWSWriterVisitor(AeBaseDef aDef, Element aParentElement, String aNamespace, String aTagName)
   {
      super(aDef, aParentElement, aNamespace, aTagName);
   }

   /**
    * @see org.activebpel.rt.bpel.def.io.writers.def.AeWriterVisitor#writeAssignVarAttributes(org.activebpel.rt.bpel.def.activity.support.AeVarDef)
    */
   protected void writeAssignVarAttributes(AeVarDef aDef)
   {
      super.writeAssignVarAttributes(aDef);

      setAttribute(TAG_QUERY, aDef.getQuery());
   }

   /**
    * @see org.activebpel.rt.bpel.def.io.writers.def.AeWriterVisitor#writeAssignFromAttributes(org.activebpel.rt.bpel.def.activity.support.AeFromDef)
    */
   protected void writeAssignFromAttributes(AeFromDef aDef)
   {
      super.writeAssignFromAttributes(aDef);

      setAttribute(TAG_EXPRESSION, aDef.getExpression());
      setAttribute(TAG_OPAQUE_ATTR, aDef.isOpaque(), false);
   }

   /**
    * Write attributes to the Element.
    * @param aDef
    */
   protected void writeActivityAttributes( AeActivityDef aDef )
   {
      super.writeActivityAttributes(aDef);

      AeJoinConditionDef joinConditionDef = aDef.getJoinConditionDef();
      if (joinConditionDef != null)
         setAttribute(TAG_JOIN_CONDITION, joinConditionDef.getExpression());
   }

   /**
    * Writes the message exchange value if not empty or null.  Overrides the base in order
    * to put the message exchange attribute in the abx namespace.
    *
    * @see org.activebpel.rt.bpel.def.io.writers.def.AeWriterVisitor#writeMessageExchange(java.lang.String)
    */
   protected void writeMessageExchange(String aMessageExchangeValue)
   {
      if (AeUtil.notNullOrEmpty(aMessageExchangeValue))
      {
         String prefix = AeXmlUtil.getOrCreatePrefix(getElement(), IAeBPELConstants.ABX_2_0_NAMESPACE_URI);
         getElement().setAttributeNS(IAeBPELConstants.ABX_2_0_NAMESPACE_URI,
                                     prefix + ":" + TAG_MESSAGE_EXCHANGE,  //$NON-NLS-1$
                                     aMessageExchangeValue);
      }
   }
   
   /**
    * Overrides to append the abstractProcess boolean attribute.
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AeProcessDef)
    */
   public void visit(AeProcessDef aDef)
   {
      super.visit(aDef);
      setAttribute(TAG_ABSTRACT_PROCESS, aDef.isAbstractProcess(), false);
   }      

   /**
    * @see org.activebpel.rt.bpel.def.io.writers.def.AeWriterVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeForEachBranchesDef)
    */
   public void visit(AeForEachBranchesDef aDef)
   {
      super.visit(aDef);
      setAttribute(IAeBpelLegacyConstants.COUNT_COMPLETED_BRANCHES_ONLY, aDef.isCountCompletedBranchesOnly(), false);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeSourceDef)
    */
   public void visit(AeSourceDef aDef)
   {
      super.visit(aDef);

      setAttribute(TAG_TRANSITION_CONDITION, aDef.getTransitionCondition());
   }

   /**
    * @see org.activebpel.rt.bpel.def.io.writers.def.AeWriterVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityWaitDef)
    */
   public void visit(AeActivityWaitDef aDef)
   {
      super.visit(aDef);

      setAttribute(TAG_FOR, aDef.getFor());
      setAttribute(TAG_UNTIL, aDef.getUntil());
   }

   /**
    * @see org.activebpel.rt.bpel.def.io.writers.def.AeWriterVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeOnAlarmDef)
    */
   public void visit(AeOnAlarmDef aDef)
   {
      super.visit(aDef);

      setAttribute(TAG_FOR, aDef.getFor());
      setAttribute(TAG_UNTIL, aDef.getUntil());
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityWhileDef)
    */
   public void visit(AeActivityWhileDef aDef)
   {
      super.visit(aDef);

      setAttribute(TAG_CONDITION, aDef.getConditionDef().getExpression());
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityScopeDef)
    */
   public void visit(AeActivityScopeDef aDef)
   {
      super.visit(aDef);

      setAttribute(IAeBpelLegacyConstants.TAG_VARIABLE_ACCESS_SERIALIZABLE, aDef.isIsolated(), false);
   }

   /**
    * Visit the if activity.  Note that in 1.1, the if activity is really a switch activity.  We
    * model it this way in order to have a single model for both 2.0 and 1.1.
    * 
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityIfDef)
    */
   public void visit(AeActivityIfDef aDef)
   {
      writeAttributes(aDef);
   }

   /**
    * Visits the ifDef to write out its state.  Note that the ifDef in bpel 1.1 is really the first
    * case in a switch.  We model it as an ifDef in order to have a single model for both 1.1 and 2.0.
    * 
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeIfDef)
    */
   public void visit(AeIfDef aDef)
   {
      visit((AeElseIfDef) aDef);
   }

   /**
    * Visits the elseIfDef to write out its state.  Note that the elseIfDef in bpel 1.1 is really 
    * a case def.  We model it as an elseIfDef in order to have a single model for both 1.1 and 2.0.
    * 
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeElseIfDef)
    */
   public void visit(AeElseIfDef aDef)
   {
      writeAttributes(aDef);
      if (aDef.getConditionDef() != null)
         setAttribute(TAG_CONDITION, aDef.getConditionDef().getExpression());
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeElseDef)
    */
   public void visit(AeElseDef aDef)
   {
      writeAttributes(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityCompensateScopeDef)
    */
   public void visit(AeActivityCompensateScopeDef aDef)
   {
      // Even though this construct doesn't exist in bpel 1.1, we model the <compensate scope="S1" form of the
      // bpel 1.1 activity by using the bpel 2.0 compensateScope def.
      writeAttributes(aDef);
      
      setAttribute(TAG_SCOPE, aDef.getTarget());
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.io.writers.def.AeWriterVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeToDef)
    */
   public void visit(AeToDef aDef)
   {
      super.visit(aDef);

      setAttribute(TAG_QUERY, aDef.getQuery());
   }
}
