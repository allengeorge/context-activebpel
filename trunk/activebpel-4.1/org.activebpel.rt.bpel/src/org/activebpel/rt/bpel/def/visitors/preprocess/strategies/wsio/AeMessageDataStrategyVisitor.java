//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/visitors/preprocess/strategies/wsio/AeMessageDataStrategyVisitor.java,v 1.1 2006/08/18 22:20:3
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
package org.activebpel.rt.bpel.def.visitors.preprocess.strategies.wsio; 

import org.activebpel.rt.bpel.def.activity.AeActivityInvokeDef;
import org.activebpel.rt.bpel.def.activity.AeActivityReceiveDef;
import org.activebpel.rt.bpel.def.activity.AeActivityReplyDef;
import org.activebpel.rt.bpel.def.activity.IAeMessageDataConsumerDef;
import org.activebpel.rt.bpel.def.activity.IAeMessageDataProducerDef;
import org.activebpel.rt.bpel.def.activity.support.AeOnEventDef;
import org.activebpel.rt.bpel.def.activity.support.AeOnMessageDef;
import org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor;
import org.activebpel.rt.bpel.def.visitors.AeDefTraverser;
import org.activebpel.rt.bpel.def.visitors.AeTraversalVisitor;

/**
 * Visits each invoke and reply in order to set the strategy hint on the def
 */
public class AeMessageDataStrategyVisitor extends AeAbstractDefVisitor
{
   /** matcher determines if the invoke/reply conform to one of the prescribed patterns */
   private IAeMessageDataStrategyMatcher mMatcher;
   
   /**
    * Ctor accepts the matcher
    * @param aMatcher
    */
   public AeMessageDataStrategyVisitor(IAeMessageDataStrategyMatcher aMatcher)
   {
      setMatcher(aMatcher);
      setTraversalVisitor( new AeTraversalVisitor(new AeDefTraverser(), this));
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityInvokeDef)
    */
   public void visit(AeActivityInvokeDef aDef)
   {
      determineProducerStrategy(aDef);
      determineConsumerStrategy(aDef);
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityReplyDef)
    */
   public void visit(AeActivityReplyDef aDef)
   {
      determineProducerStrategy(aDef);
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityReceiveDef)
    */
   public void visit(AeActivityReceiveDef aDef)
   {
      determineConsumerStrategy(aDef);
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeOnEventDef)
    */
   public void visit(AeOnEventDef aDef)
   {
      determineConsumerStrategy(aDef);
      super.visit(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeOnMessageDef)
    */
   public void visit(AeOnMessageDef aDef)
   {
      determineConsumerStrategy(aDef);
      super.visit(aDef);
   }

   /**
    * Determines the strategy for the message data producer and sets it 
    * on the def
    * @param aDef
    */
   protected void determineProducerStrategy(IAeMessageDataProducerDef aDef)
   {
      // pass the def to the strategy matcher (as an interface)
      // set the strategy from the matcher on the def
      String strategy = getMatcher().getProducerStrategy(aDef);
      aDef.setMessageDataProducerStrategy(strategy);
   }

   /**
    * Determines the strategy for the message data producer and sets it 
    * on the def
    * @param aDef
    */
   protected void determineConsumerStrategy(IAeMessageDataConsumerDef aDef)
   {
      // pass the def to the strategy matcher (as an interface)
      // set the strategy from the matcher on the def
      String strategy = getMatcher().getConsumerStrategy(aDef);
      aDef.setMessageDataConsumerStrategy(strategy);
   }

   /**
    * @return Returns the matcher.
    */
   public IAeMessageDataStrategyMatcher getMatcher()
   {
      return mMatcher;
   }

   /**
    * @param aMatcher The matcher to set.
    */
   public void setMatcher(IAeMessageDataStrategyMatcher aMatcher)
   {
      mMatcher = aMatcher;
   }
}
 
