// $Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/message/IAeMessageData.java,v 1.8 2007/06/28 21:57:3
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

import java.util.Iterator;

import javax.xml.namespace.QName;

import org.activebpel.rt.attachment.IAeAttachmentContainer;

/**
 * Interface describing the message data associated with a variable of type message.
 */
public interface IAeMessageData extends Cloneable
{
   /**
    * Returns flag indicating if the message data is dirty.
    */
   public boolean isDirty();

   /**
    * Clears the flag indicating that the data is dirty.
    */
   public void clearDirty();

   /**
    * Returns the type of message this data is representing. 
    */
   public QName getMessageType();

   /**
    * Returns an iterator over the part names for which we are storing data.
    */
   public Iterator getPartNames();

   /**
    * Returns the data associated with a passed part. Null if none.
    * @param aPartName The part name to get data for.
    * @return The data associated with the passed part name. Can be null.
    */
   public Object getData(String aPartName);
    
   /**
    * Sets the data associated with a passed part. Data can be null.
    * @param aPartName The part to set the data for.
    * @param aData The data to which the part is set.
    */
   public void setData(String aPartName, Object aData);
   
   /**
    * Makes a deep copy of the part data 
    */
   public Object clone();

   /**
    * Returns the number of parts in this message data
    */
   public int getPartCount();
   
   /**
    * Returns true if there are attachments associated with this MessageData object.
    */
   public boolean hasAttachments();
   
   /**
    * Returns an attachment container associated with the message, if any.
    * @return the  attachment container
    * @see org.activebpel.rt.attachment#IAeAttachmentContainer
    */
   public IAeAttachmentContainer getAttachmentContainer();
   
   /**
    * associates an optional attachment container to the message.
    * @param aAttachmentContainer
    */
   public void setAttachmentContainer(IAeAttachmentContainer aAttachmentContainer);
}
