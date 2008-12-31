//$Header: /Development/AEDevelopment/projects/org.activebpel.wsio/src/org/activebpel/wsio/invoke/AeInvokeResponse.java,v 1.7 2005/07/25 16:48:3
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
package org.activebpel.wsio.invoke;

import org.activebpel.wsio.IAeWebServiceMessageData;
import org.activebpel.wsio.IAeWebServiceResponse;

import javax.xml.namespace.QName;
import java.util.Map;

/**
 * Wraps any response data from an invoke call.
 */
public class AeInvokeResponse implements IAeWebServiceResponse
{
   /** Error code QName. */
   protected QName mErrorCode;
   /** Message data. */
   protected IAeWebServiceMessageData mMessageData;
   /** Error message */
   protected String mErrorString;
   /** Stacktrace */
   protected String mErrorDetail;
   /** Business process properties */
   protected Map mBusinessProcessProperties;
   /** Flag to indicate that the actual response will be coming later. (Early Reply to the client) **/
   protected boolean mEarlyReply = false;
   
   /**
    * Constructor.
    */
   public AeInvokeResponse()
   {
   }
   
   /**
    * Set any fault data.
    * @param aErrorCode
    * @param aFaultData
    */
   public void setFaultData(QName aErrorCode, IAeWebServiceMessageData aFaultData)
   {
      mErrorCode = aErrorCode;
      mMessageData = aFaultData;
   }
   
   /**
    * Set the error code
    * @param aErrorCode
    */
   public void setErrorCode(QName aErrorCode)
   {
      mErrorCode = aErrorCode;
   }

   /**
    * Set the message data on the response.
    * @param aMessageData
    */
   public void setMessageData(IAeWebServiceMessageData aMessageData)
   {
      mMessageData = aMessageData;
   }
   
   /**
    * @see org.activebpel.wsio.IAeWebServiceResponse#isFaultResponse()
    */
   public boolean isFaultResponse()
   {
      return mErrorCode != null;
   }
   
   /**
    * @see org.activebpel.wsio.IAeWebServiceResponse#getErrorCode()
    */
   public QName getErrorCode()
   {
      return mErrorCode;
   }
   /**
    * @see org.activebpel.wsio.IAeWebServiceResponse#getMessageData()
    */
   public IAeWebServiceMessageData getMessageData()
   {
      return mMessageData;
   }
   
   /**
    * @see org.activebpel.wsio.IAeWebServiceResponse#getErrorString()
    */
   public String getErrorString()
   {
      return mErrorString;
   }
   
   /**
    * Setter for the fault string.
    * 
    * @param aString
    */
   public void setErrorString(String aString)
   {
      mErrorString = aString;
   }
   
   /**
    * @see org.activebpel.wsio.IAeWebServiceResponse#getErrorDetail()
    */
   public String getErrorDetail()
   {
      return mErrorDetail;
   }
   
   /**
    * Setter for the error detail
    * 
    * @param aDetail
    */
   public void setErrorDetail(String aDetail)
   {
      mErrorDetail = aDetail;
   }
   
   /**
    * Add a business process property to this response.
    * @param aProperties
    */
   public void setBusinessProcessProperties( Map aProperties )
   {
      mBusinessProcessProperties = aProperties;
   }
   
   /**
    * @see org.activebpel.wsio.IAeWebServiceResponse#getBusinessProcessProperties()
    */
   public Map getBusinessProcessProperties()
   {
      return mBusinessProcessProperties;
   }   
   
   /**
    * @return Returns the earlyReply.
    */
   public boolean isEarlyReply()
   {
      return mEarlyReply;
   }
   
   /**
    * @param aEarlyReply The earlyReply to set.
    */
   public void setEarlyReply(boolean aEarlyReply)
   {
      mEarlyReply = aEarlyReply;
   }
}
