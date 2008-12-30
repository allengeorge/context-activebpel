// $Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/message/AeMessageData.java,v 1.14 2007/06/28 21:57:3
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
package org.activebpel.rt.message;

import org.activebpel.rt.attachment.AeAttachmentContainer;
import org.activebpel.rt.attachment.IAeAttachmentContainer;
import org.activebpel.rt.util.AeXmlUtil;
import org.w3c.dom.Document;

import javax.xml.namespace.QName;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Manages data interaction for a WSDL message.
 * Internal normalized message structure used by all engines. These messages are not publicly exposed.
 * For pulic message API see {@link org.activebpel.wsio.IAeWebServiceMessageData IAeWebServiceMessageData}
 */
public class AeMessageData implements IAeMessageData
{
   /** Holds on to the part data, the name of the part is its key. */
   private HashMap mPartData = new HashMap();

   /** The variable message type for which we are storing data */
   private QName mMsgType;

   /** Flag to track if the message data is dirty */
   private boolean mDirty;
   
   /** place holder for optional attachments */
   private IAeAttachmentContainer mAttachmentContainer;

   /**
    * Constructor.
    *
    * @param aQName
    * @param aPartData
    */
   public AeMessageData( QName aQName, Map aPartData )
   {
      this( aQName );
      mPartData.putAll( aPartData );
   }

   /**
    * Constructor which takes the QName of the message as input.
    * @param aMsgType the qualified name of the message this data element represents.
    */
   public AeMessageData(QName aMsgType)
   {
      mMsgType = aMsgType;
   }

   /**
    * Returns the type of message this data is representing.
    */
   public QName getMessageType()
   {
      return mMsgType;
   }

   /**
    * Returns flag indicating if the message data is dirty.
    */
   public boolean isDirty()
   {
      // TODO (MF) only called by win32, perhaps those classes could use a subclass and remove this method?
      return mDirty;
   }

   /**
    * Clears the flag indicating that the data is dirty.
    */
   public void clearDirty()
   {
      mDirty = false;
   }

   /**
    * Allows inheriting classes ability to set dirty flag.
    * @param aDirty True for dirty and False if not dirty
    */
   protected void setDirty(boolean aDirty)
   {
      mDirty = aDirty;
   }

   /**
    * Returns an iterator over the part names for which we are storing data.
    */
   public Iterator getPartNames()
   {
      return mPartData.keySet().iterator();
   }

   /**
    * Returns the data associated with a passed part. Null if none.
    * @param aPartName The part name to get data for.
    * @return The data associated with the passed part name. Can be null.
    */
   public Object getData(String aPartName)
   {
      return mPartData.get(aPartName);
   }

   /**
    * Sets the data associated with a passed part. Data can be null.
    * @param aPartName The part to set the data for.
    * @param aData The data to which the part is set.
    */
   public void setData(String aPartName, Object aData)
   {
      mPartData.put(aPartName, aData);
      mDirty = true;
   }

   /**
    * @see java.lang.Object#clone()
    */
   public Object clone()
   {
      try
      {
         AeMessageData copy = (AeMessageData) super.clone();
         copy.mPartData = new HashMap(mPartData);
         
         // walk the map and deep clone any Nodes
         for (Iterator iter = copy.mPartData.entrySet().iterator(); iter.hasNext();)
         {
            Map.Entry entry = (Map.Entry) iter.next();
            if (entry.getValue() instanceof Document)
            {
               Document doc = (Document) entry.getValue();
               entry.setValue(AeXmlUtil.cloneElement(doc.getDocumentElement()).getOwnerDocument());
            }
         }
         
         // clone attachments
         if (hasAttachments())
            copy.setAttachmentContainer(new AeAttachmentContainer(getAttachmentContainer()));
         
         return copy;
      }
      catch(CloneNotSupportedException e)
      {
         throw new InternalError("unexpected error during clone:" + e.getLocalizedMessage()); //$NON-NLS-1$
      }
   }

   /**
    * @see org.activebpel.rt.message.IAeMessageData#getPartCount()
    */
   public int getPartCount()
   {
      return mPartData.size();
   }

   /**
    * @see org.activebpel.rt.message.IAeMessageData#hasAttachments()
    */
   public boolean hasAttachments()
   {
      return mAttachmentContainer != null && ! mAttachmentContainer.isEmpty();
   }
   
   /**
    * @see org.activebpel.rt.message.IAeMessageData#getAttachmentContainer()
    */
   public IAeAttachmentContainer getAttachmentContainer()
   {
      if (mAttachmentContainer == null)
         mAttachmentContainer = new AeAttachmentContainer();
      
      return mAttachmentContainer;
   }

   /**
    * @see org.activebpel.rt.message.IAeMessageData#setAttachmentContainer(org.activebpel.rt.attachment.IAeAttachmentContainer)
    */
   public void setAttachmentContainer(IAeAttachmentContainer aAttachmentContainer)
   {
      mAttachmentContainer = aAttachmentContainer;
   }
}
