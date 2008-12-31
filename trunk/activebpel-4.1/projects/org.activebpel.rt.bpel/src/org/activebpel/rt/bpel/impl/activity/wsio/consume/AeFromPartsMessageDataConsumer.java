// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/wsio/consume/AeFromPartsMessageDataConsumer.java,v 1.5 2007/06/09 01:08:4
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
package org.activebpel.rt.bpel.impl.activity.wsio.consume;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeVariable;
import org.activebpel.rt.bpel.def.activity.support.AeFromPartDef;
import org.activebpel.rt.bpel.impl.activity.assign.AeAtomicCopyOperationContext;
import org.activebpel.rt.bpel.impl.activity.assign.AeVirtualCopyOperation;
import org.activebpel.rt.bpel.impl.activity.assign.IAeFrom;
import org.activebpel.rt.bpel.impl.activity.assign.IAeTo;
import org.activebpel.rt.bpel.impl.activity.assign.from.AeFromVariableMessagePart;
import org.activebpel.rt.bpel.impl.activity.assign.from.AeFromVariableMessagePartWithAttachments;
import org.activebpel.rt.bpel.impl.activity.assign.to.AeToVariableElement;
import org.activebpel.rt.bpel.impl.activity.assign.to.AeToVariableType;
import org.activebpel.rt.message.IAeMessageData;

/**
 * Implements a message data consumer that copies data according to a series of
 * <code>fromPart</code> definitions.
 */
public class AeFromPartsMessageDataConsumer extends AeAbstractMessageDataConsumer implements IAeMessageDataConsumer
{
   /** The list of copy operations. */
   private List mCopyOperations;
   
   /**
    * Constructs a <code>fromParts</code> message data consumer for the given
    * context
    *
    * @param aContext
    */
   public AeFromPartsMessageDataConsumer(IAeMessageDataConsumerContext aContext)
   {
      super(aContext);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.wsio.consume.IAeMessageDataConsumer#consumeMessageData(org.activebpel.rt.message.IAeMessageData)
    */
   public void consumeMessageData(IAeMessageData aMessageData) throws AeBusinessProcessException
   {
      // Create an anonymous variable that wraps the incoming message data.
      IAeVariable anonymousVariable = createAnonymousVariable(aMessageData);

      // Prepare the copy operation context.
      AeAtomicCopyOperationContext copyContext = getAtomicCopyOperationContext();
      copyContext.clearRollback();
      
      try
      {
         for (Iterator i = getCopyOperations().iterator(); i.hasNext(); )
         {
            // Get the virtual copy operation.
            AeVirtualCopyOperation copyOperation = (AeVirtualCopyOperation) i.next();

            // Update the virtual copy operation to copy from the anonymous
            // variable.
            AeFromVariableMessagePart from = (AeFromVariableMessagePart)copyOperation.getFrom();
            from.setVariable(anonymousVariable);

            // Execute the copy operation.
            copyOperation.execute();
         }
      }
      catch (Throwable t)
      {
         // Note: The only way that we should fault here is if validation is
         // turned off and we are consuming an incomplete message.
         //
         // Restore data to the initial state.
         copyContext.rollback();

         if (t instanceof AeBusinessProcessException)
         {
            throw (AeBusinessProcessException) t;
         }
         
         throw new AeBusinessProcessException(t.getLocalizedMessage(), t);
      }
   }

   /**
    * Returns an {@link IAeFrom} instance that will copy from the anonymous
    * variable that will wrap the incoming message data.
    *
    * @param aFromPartDef
    */
   protected IAeFrom createCopyFrom(AeFromPartDef aFromPartDef,int aPartNumber) throws AeBusinessProcessException
   {
      String partName = aFromPartDef.getPart();

      // Specify null for the variable here. We will set the variable to be the
      // anonymous variable that wraps the incoming message data just before
      // executing the copy operation.
      return (aPartNumber == 0)? new AeFromVariableMessagePartWithAttachments((IAeVariable) null, partName) : new AeFromVariableMessagePart((IAeVariable) null, partName);
   }
   
   /**
    * Returns an {@link IAeTo} instance that copies to the BPEL variable
    * specified by the given <code>fromPart</code> definition.
    *
    * @param aFromPartDef
    */
   protected IAeTo createCopyTo(AeFromPartDef aFromPartDef) throws AeBusinessProcessException
   {
      String variableName = aFromPartDef.getToVariable();
      IAeVariable variable = getBpelObject().findVariable(variableName);
      IAeTo to = null;

      // By the time we get here, we know that the variable is either an element
      // variable or schema type variable.
      if (variable.isElement())
      {
         to = new AeToVariableElement(variableName);
      }
      else
      {
         to = new AeToVariableType(variableName);
      }

      return to;
   }

   /**
    * Returns a list of copy operations that will copy from an anonymous
    * variable that wraps the incoming message data to the
    * <code>toVariable</code> variables defined by the <code>fromPart</code>
    * definitions.
    */
   protected List getCopyOperations() throws AeBusinessProcessException
   {
      if (mCopyOperations == null)
      {
         mCopyOperations = new LinkedList();

         int partNumber = 0;
         for (Iterator i = getFromPartDefs(); i.hasNext(); partNumber++)
         {
            AeFromPartDef fromPartDef = (AeFromPartDef) i.next();
            IAeFrom from = createCopyFrom(fromPartDef,partNumber);
            IAeTo to = createCopyTo(fromPartDef);

            AeVirtualCopyOperation copyOperation = AeVirtualCopyOperation.createFromPartToPartOperation();
            copyOperation.setContext(getAtomicCopyOperationContext());
            copyOperation.setFrom(from);
            copyOperation.setTo(to);

            mCopyOperations.add(copyOperation);
         }
      }

      return mCopyOperations;
   }
}
