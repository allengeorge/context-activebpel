// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/AeDataConverter.java,v 1.5 2007/06/28 22:00:4
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
package org.activebpel.rt.bpel.impl;

import org.activebpel.rt.AeException;
import org.activebpel.rt.attachment.IAeAttachmentContainer;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.AeMessageDataFactory;
import org.activebpel.rt.bpel.IAeFault;
import org.activebpel.rt.message.IAeMessageData;
import org.activebpel.wsio.AeWebServiceMessageData;
import org.activebpel.wsio.IAeWebServiceMessageData;

import java.util.Iterator;
import java.util.List;

/**
 * Utility class for converting between wsio data and our standard data container. The wsio packaging provides
 * a standalone set of interfaces and support classes for dispatching messages to the engine and for invoking
 * external web services. In order to simplify the packaging, the wsio project does not have ties to the rest
 * of the activebpel codebase. One side effect is that we're left with two different interfaces
 */
public class AeDataConverter
{
   private static IAeAttachmentManager sAttachmentManager = null;

   /**
    * Sets the attachment manager to use to convert attachments that may be
    * associated with incoming or outgoing message data.
    *
    * @param aAttachmentManager
    */
   public static void setAttachmentManager(IAeAttachmentManager aAttachmentManager)
   {
      sAttachmentManager = aAttachmentManager;
   }

   /**
    * Converts a message data object to a WSIO message data. Returns <code>null</code> if input is
    * <code>null</code>. Does not convert attachments.
    * @param aData
    */
   public static IAeWebServiceMessageData convert(IAeMessageData aData) throws AeBusinessProcessException
   {
      AeWebServiceMessageData wsMsg;

      if ( aData == null )
      {
         wsMsg = null;
      }
      else
      {
         wsMsg = new AeWebServiceMessageData(aData.getMessageType());

         for (Iterator iter = aData.getPartNames(); iter.hasNext();)
         {
            String partName = (String)iter.next();
            wsMsg.setData(partName, aData.getData(partName));
         }

         if (aData.hasAttachments())
            wsMsg.setAttachments(sAttachmentManager.bpel2wsio(aData.getAttachmentContainer()));
      }

      return wsMsg;
   }

   /**
    * Converts the message data for the given fault, silently consuming any
    * exception that may arise.
    *
    * @param aFault
    */
   public static IAeWebServiceMessageData convertFaultDataNoException(IAeFault aFault)
   {
      try
      {
         return convert(aFault.getMessageData());
      }
      catch (AeBusinessProcessException e)
      {
         // TODO (JB) Try AeDataConvert.convert() again without the attachments.
         AeException.logError(e);
         return null;
      }
   }

   /**
    * Converts a WSIO message to a message data. Returns <code>null</code> if input is <code>null</code>.
    * @param aData
    */
   public static IAeMessageData convert(IAeWebServiceMessageData aData)  throws AeBusinessProcessException
   {
      IAeMessageData bpelMsg = null;

      if ( aData != null )
      {
         bpelMsg = AeMessageDataFactory.instance().createMessageData(aData.getMessageType(),
               aData.getMessageData());

         List attachments = aData.getAttachments();
         if ((attachments != null) && !attachments.isEmpty())
         {
            IAeAttachmentContainer container = sAttachmentManager.wsio2bpel(attachments);
            bpelMsg.setAttachmentContainer(container);
         }
      }
      return bpelMsg;
   }
}
