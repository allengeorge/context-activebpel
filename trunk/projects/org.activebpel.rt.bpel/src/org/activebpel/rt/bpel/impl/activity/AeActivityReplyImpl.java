// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/AeActivityReplyImpl.java,v 1.35 2006/11/06 23:37:0
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
package org.activebpel.rt.bpel.impl.activity;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.def.activity.AeActivityReplyDef;
import org.activebpel.rt.bpel.impl.AePartnerLink;
import org.activebpel.rt.bpel.impl.AePartnerLinkOpImplKey;
import org.activebpel.rt.bpel.impl.IAeActivityParent;
import org.activebpel.rt.bpel.impl.visitors.IAeImplVisitor;
import org.activebpel.rt.message.IAeMessageData;

/**
 * Implementation of the bpel reply activity.
 */
public class AeActivityReplyImpl extends AeWSIOActivityImpl
{
   /** default constructor for activity */
   public AeActivityReplyImpl(AeActivityReplyDef aActivityDef, IAeActivityParent aParent)
   {
      super(aActivityDef, aParent);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.visitors.IAeVisitable#accept(org.activebpel.rt.bpel.impl.visitors.IAeImplVisitor)
    */
   public void accept( IAeImplVisitor aVisitor ) throws AeBusinessProcessException
   {
      aVisitor.visit(this);
   }

   /**
    * Convenience method to avoid casting of definition.
    */
   private AeActivityReplyDef getDef()
   {
      return (AeActivityReplyDef) getDefinition();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeExecutableBpelObject#execute()
    */
   public void execute() throws AeBusinessProcessException
   {
      AeActivityReplyDef def = getDef();

      IAeMessageData inputMessage = getMessageDataProducer().produceMessageData();

      // Validate only if we know the outgoing message type. In particular, we
      // won't know the outgoing message type if we are replying with a fault
      // that was not defined as part of the WSDL operation.
      // TODO (MF) When static analysis is updated, then remove this check. 
      if (getDef().getProducerMessagePartsMap() != null)
      {
         getMessageValidator().validate(getProcess(), inputMessage, getDef().getProducerMessagePartsMap());
      }

      // get the correlation sets we are creating via input and initiate
      if (getResponseCorrelations() != null)
      {
         getResponseCorrelations().initiateOrValidate(inputMessage, getDef().getProducerMessagePartsMap());
      }

      
      String messageExchangePath = findEnclosingScope().getMessageExchangePath(getDef().getMessageExchange());
      
      AePartnerLink plink = findPartnerLink(def.getPartnerLink());
      AePartnerLinkOpImplKey plOpKey = new AePartnerLinkOpImplKey(plink, def.getOperation());
      // Queue our invocation of the partner operation we will be called back when it is done
      // note that we may be called back via onMessage or onFault before this returns
      getProcess().queueReply( inputMessage,
                               def.getFaultName(),
                               plOpKey,
                               messageExchangePath);

      // fixme (MF-3.1) introduce a callback so we can be sure that the reply was successful.                   
      objectCompleted();
   }
   
   /** 
    * Overrides method to remove the open IMA before changing state to COMPLETED.
    * 
    * @see org.activebpel.rt.bpel.impl.IAeExecutableBpelObject#exceptionManagementCompleteActivity()
    */
   public void exceptionManagementCompleteActivity() throws AeBusinessProcessException
   {
      // Remove the open IMA from the process open IMA list.
      AePartnerLink plink = findPartnerLink(getDef().getPartnerLink());
      AePartnerLinkOpImplKey plOpKey = new AePartnerLinkOpImplKey(plink, getDef().getOperation());
      String messageExchangePath = findEnclosingScope().getMessageExchangePath(getDef().getMessageExchange());
      
      getProcess().removeReply(plOpKey, messageExchangePath);

      super.exceptionManagementCompleteActivity();
   }
}
