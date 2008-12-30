// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/wsio/produce/AeToPartsMessageDataProducer.java,v 1.7 2007/01/02 20:22:4
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
package org.activebpel.rt.bpel.impl.activity.wsio.produce;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeVariable;
import org.activebpel.rt.bpel.def.activity.support.AeToPartDef;
import org.activebpel.rt.bpel.impl.activity.assign.AeVirtualCopyOperation;
import org.activebpel.rt.bpel.impl.activity.assign.IAeCopyOperation;
import org.activebpel.rt.bpel.impl.activity.assign.IAeFrom;
import org.activebpel.rt.bpel.impl.activity.assign.IAeTo;
import org.activebpel.rt.bpel.impl.activity.assign.from.AeFromVariableElement;
import org.activebpel.rt.bpel.impl.activity.assign.from.AeFromVariableType;
import org.activebpel.rt.bpel.impl.activity.assign.to.AeToVariableMessagePart;
import org.activebpel.rt.message.IAeMessageData;

/**
 * Implements a message data producer that copies data according to a series of
 * <code>toPart</code> definitions.
 */
public class AeToPartsMessageDataProducer extends AeAbstractMessageDataProducer implements IAeMessageDataProducer
{
   /** The list of copy operations. */
   private List mCopyOperations;

   /**
    * Constructs a <code>toParts</code> message data producer for the given
    * context.
    *
    * @param aContext
    */
   public AeToPartsMessageDataProducer(IAeMessageDataProducerContext aContext)
   {
      super(aContext);
   }

   /**
    * Returns an {@link IAeFrom} instance that copies from the BPEL variable
    * specified by the given <code>toPart</code> definition.
    *
    * @param aToPartDef
    */
   protected IAeFrom createCopyFrom(AeToPartDef aToPartDef) throws AeBusinessProcessException
   {
      String variableName = aToPartDef.getFromVariable();

      IAeVariable variable = getBpelObject().findVariable(variableName);
      IAeFrom from = null;
      
      // By the time we get here, we know that the variable is either an element
      // variable or a schema type variable.
      if (variable.isElement())
      {
         from = new AeFromVariableElement(variableName);
      }
      else
      {
         from = new AeFromVariableType(variableName);
      }

      return from;
   }

   /**
    * Returns an {@link IAeTo} instance that copies to a part in the anonymous
    * variable that will wrap the outgoing message data.
    *
    * @param aToPartDef
    */
   protected IAeTo createCopyTo(AeToPartDef aToPartDef) throws AeBusinessProcessException
   {
      IAeVariable variable = getAnonymousVariable();
      String partName = aToPartDef.getPart();
      
      return new AeToVariableMessagePart(variable, partName);
   }

   /**
    * Returns a list of copy operations that copy from the
    * <code>fromVariable</code> variables defined by the <code>toPart</code>
    * definitions to an anonymous variable that will wrap the outgoing message
    * data.
    */
   protected List getCopyOperations() throws AeBusinessProcessException
   {
      if (mCopyOperations == null)
      {
         mCopyOperations = new LinkedList();

         for (Iterator i = getContext().getToPartDefs(); i.hasNext(); )
         {
            AeToPartDef toPartDef = (AeToPartDef) i.next();
            IAeFrom from = createCopyFrom(toPartDef);
            IAeTo to = createCopyTo(toPartDef);

            AeVirtualCopyOperation copyOperation = AeVirtualCopyOperation.createFromPartToPartOperation();
            copyOperation.setContext(getCopyOperationContext());
            copyOperation.setFrom(from);
            copyOperation.setTo(to);

            mCopyOperations.add(copyOperation);
         }
      }

      return mCopyOperations;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.wsio.produce.IAeMessageDataProducer#produceMessageData()
    */
   public IAeMessageData produceMessageData() throws AeBusinessProcessException
   {
      // Initialize the anonymous variable to wrap a new message data.
      IAeMessageData messageData = createMessageData();
      getAnonymousVariable().setMessageData(messageData);
      
      // initialize all of the outgoing message parts
      for (Iterator iter = getMessagePartsMap().getPartNames(); iter.hasNext();)
      {
         String partName = (String) iter.next();
         getAnonymousVariable().initializeForAssign(partName);
      }

      // Copy to the message data parts.
      for (Iterator i = getCopyOperations().iterator(); i.hasNext(); )
      {
         IAeCopyOperation copyOperation = (IAeCopyOperation) i.next();
         copyOperation.execute();
      }

      return messageData;
   }
}
