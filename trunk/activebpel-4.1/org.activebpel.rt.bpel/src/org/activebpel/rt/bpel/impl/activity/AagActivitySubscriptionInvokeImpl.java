package org.activebpel.rt.bpel.impl.activity;

import ece.uwaterloo.ca.aag.subscribe.IAagSubscribeResponse;
import org.activebpel.rt.bpel.*;
import org.activebpel.rt.bpel.def.activity.AeActivityInvokeDef;
import org.activebpel.rt.bpel.impl.IAagStatefulProcess;
import org.activebpel.rt.bpel.impl.IAeActivityParent;
import org.activebpel.rt.context.AagIncomingContextData;
import org.activebpel.rt.context.IAagIncomingContextData;
import org.activebpel.rt.message.IAeMessageData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Allen Ajit George
 * Date: Feb 29, 2008
 * Time: 1:25:17 PM
 */
public class AagActivitySubscriptionInvokeImpl extends AeActivityInvokeImpl implements IAagSubscriptionReceiver, IAagSubscriptionInvokeActivity {
   /**
    * default constructor for activity
    */
   public AagActivitySubscriptionInvokeImpl(AeActivityInvokeDef aActivityDef, IAeActivityParent aParent) {
      super(aActivityDef, aParent);
      mSubscribeEnabled = true;
      recivedContextParameters = 0;
   }

   public void onMessage(IAeMessageData aMessage) throws AeBusinessProcessException {
      if (getState().isFinal()) {
         return;
      }
      if (aMessage != null) {
         getMessageValidator().validate(getProcess(), aMessage, getDef().getConsumerMessagePartsMap());

         // initiates any correlation sets that are defined to initiate with INBOUND data
         if (getResponseCorrelations() != null)
            getResponseCorrelations().initiateOrValidate(aMessage, getDef().getConsumerMessagePartsMap());

         getMessageDataConsumer().consumeMessageData(aMessage);
      }

      // XXX Is this really required, since I'm already AagSubscriptionInvokeImpl?
      if (mSubscribeEnabled) {
         List<IAagIncomingContextData> contextParameters = getSubscriptionParameters(aMessage);

         if (contextParameters == null) {
            objectCompleted();
         } else {
            // NOTE: I know, since subscription is enabled, that the process is a stateful process
            numContextParameters = contextParameters.size();
            getStatefulProcess().queueSubscribe(this, contextParameters);
         }
      } else {
         objectCompleted();
      }
   }

   private List<IAagIncomingContextData> getSubscriptionParameters(IAeMessageData aMessage) {
      List<IAagIncomingContextData> contextParameters = null;

      // FIXME Right now I only support doc-lit wrapped
      if (aMessage.getPartCount() != 1)
         return null;

      // By now we know that we have exactly one part name
      Iterator it = aMessage.getPartNames();
      Document doc = (Document) aMessage.getData((String) it.next());
      doc.normalize();

      NodeList contextNodes = doc.getElementsByTagName("Context");
      int numContextNodes = contextNodes.getLength();
      if (numContextNodes != 0) {
         Element contextElement;
         contextParameters = new ArrayList<IAagIncomingContextData>(numContextNodes);

         for (int cn = 0; cn < numContextNodes; cn++) {
            contextElement = (Element) contextNodes.item(cn);

            AagIncomingContextData contextData;
            try {
               contextData = new AagIncomingContextData(contextElement);
               contextParameters.add(contextData);
            } catch (AeBusinessProcessException e) {
               // TODO Add an AE log statement here
               e.printStackTrace();
            }
         }

         // If, for some reason, converting the context data ran into problems we won't have a null list, but an empty one
         if (contextParameters.isEmpty()) {
            // Yes, this is ugly
            contextParameters = null;
         }         
      }

      return contextParameters;
   }

   public IAagStatefulProcess getStatefulProcess() {
      return (IAagStatefulProcess) getProcess();
   }

   public IAagStatefulVariable getStatefulOutputVariable() {
      IAagStatefulVariable statefulVar = null;

      // I _know_ this will be a stateful variable
      IAeVariable outputVar = getOutputVariable();
      if (outputVar != null) {
         if (outputVar instanceof IAagStatefulVariable) {
             statefulVar = (IAagStatefulVariable) outputVar;
         } else {
            System.err.println("error: for some reason the output variable isn't stateful");
         }
      } else {
         System.err.println("error: for some reason the output variable is null");
      }

      return statefulVar;
   }

   public void onSubscribeResponse(IAagSubscribeResponse subsResponse) throws AeBusinessProcessException {
      System.out.println("Subscribe response received. Reference is: ");
      System.out.println(subsResponse.getStatefulParameterName());
      System.out.println(subsResponse.getSubscriptionEPR());

      IAagStatefulVariable statefulVar = getStatefulOutputVariable();
      if (statefulVar != null) {
         statefulVar.setSubscription(subsResponse);
         getStatefulProcess().addSubscription(subsResponse.getSubscriptionEPR(), getStatefulOutputVariable());
      } else {
         System.err.println("error: can't add subscription because the output variable isn't stateful");   
      }

      // I've got to say...this is pretty damn ugly
      recivedContextParameters++;
      if (recivedContextParameters == numContextParameters) {
         objectCompleted();
      }
   }

   public void onSubscriptionFault() throws AeBusinessProcessException {
      // TODO Implement what should actually happen when we can't subscribe
      objectCompleted();
   }

   private int recivedContextParameters;
   private int numContextParameters;
   private boolean mSubscribeEnabled;
}
