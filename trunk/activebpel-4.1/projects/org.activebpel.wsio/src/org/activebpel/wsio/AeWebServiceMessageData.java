// $Header: /Development/AEDevelopment/projects/org.activebpel.wsio/src/org/activebpel/wsio/AeWebServiceMessageData.java,v 1.5 2007/05/01 17:00:4
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
package org.activebpel.wsio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

/**
 * Standard impl of <code>IAeWebServiceMessageData</code>.
 */
public class AeWebServiceMessageData implements IAeWebServiceMessageData
{
   /** Message qname. */
   protected QName mMessageQName;
   /** Message part data. */
   protected Map mParts = new HashMap();
   /** Optional list of attachments */
   protected List mAttachments;
   
   /**
    * no arg ctor.
    */
   public AeWebServiceMessageData()
   {
   }

   /**
    * Constructor.
    * @param aQName
    */
   public AeWebServiceMessageData( QName aQName )
   {
      mMessageQName = aQName;
   }
   
   /**
    * Constructor.
    * @param aQName
    * @param aData
    */
   public AeWebServiceMessageData( QName aQName, Map aData )
   {
      this( aQName );
      mParts.putAll( aData );
   }
   
    /**
    * @see org.activebpel.wsio.IAeWebServiceMessageData#getMessageData()
    */
   public Map getMessageData()
   {
      return mParts;
   }
   
   /**
    * Setter for message data.
    * @param aPartName
    * @param aMessageData
    */
   public void setData( String aPartName, Object aMessageData )
   {
      mParts.put( aPartName, aMessageData );
   }
   
   /**
    * Setter for name
    * @param aName
    */
   public void setName(QName aName)
   {
      mMessageQName = aName;
   }
   
   /**
    * Setter for attachments.
    * @param aAttachments
    */
   public void setAttachments( List aAttachments )
   {
      mAttachments = aAttachments;
   }

   /**
    * @see org.activebpel.wsio.IAeWebServiceMessageData#getMessageType()
    */
   public QName getMessageType()
   {
      return mMessageQName;
   }

   /**
    * @see org.activebpel.wsio.IAeWebServiceMessageData#getAttachments()
    */
   public List getAttachments()
   {
      return mAttachments;
   }
}
