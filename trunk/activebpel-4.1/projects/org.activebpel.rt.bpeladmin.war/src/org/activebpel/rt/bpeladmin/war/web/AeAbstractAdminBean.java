// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/web/AeAbstractAdminBean.java,v 1.5 2007/04/24 17:28:1
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
package org.activebpel.rt.bpeladmin.war.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activebpel.rt.bpel.server.admin.IAeEngineAdministration;
import org.activebpel.rt.bpel.server.engine.AeEngineFactory;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.war.tags.IAeErrorAwareBean;

// TODO (PJ) remove get and set statusDetail/Error methods and use addMessage/getMessageList instead. Impacts JSPs.
/**
 *  Base class to provide access to <code>IAeEngineAdministration</code>.
 */
public class AeAbstractAdminBean implements IAeErrorAwareBean
{
   /** maps property names to their error values */
   private Map mPropertyErrors;
   /** Indicates whether the status message is actually an error message */
   private boolean mErrorDetail;
   /** List of error or status detail messages. */
   private List mMessageList = new ArrayList();

   /**
    * Adds a error or detail message.
    * @param aMessage
    */
   protected void addMessage(String aMessage)
   {
      if (AeUtil.notNullOrEmpty(aMessage))
      {
         mMessageList.add(aMessage);
      }
   }

   /**
    * @return Returns a error or details message list as a single string.
    */
   protected String getMessages()
   {
      List list = getMessageList();
      if (list.size() == 0)
      {
         return ""; //$NON-NLS-1$
      }
      else if (list.size() == 1)
      {
         return (String) list.get(0) ;
      }
      StringBuffer sb = new StringBuffer();
      synchronized(sb)
      {
         for(int i = 0; i < list.size(); i++)
         {
            sb.append( (String) list.get(i));
            sb.append("<br/>\n" ); //$NON-NLS-1$
         }
      }
      return sb.toString();
   }

   /**
    * Returns message list.
    * @return
    */
   public List getMessageList()
   {
      return mMessageList;
   }

   /**
    * Returns <code>true</code> if and only if the status detail is not empty.
    */
   public boolean isStatusDetailAvailable()
   {
      return AeUtil.notNullOrEmpty(getStatusDetail());
   }

   /**
    * @return Returns the statusDetail.
    */
   public String getStatusDetail()
   {
      return getMessages();
   }

   /**
    * @param aStatusDetail The statusDetail to set.
    */
   public void setStatusDetail(String aStatusDetail)
   {
      addMessage(aStatusDetail);
   }

   /**
    * Appender for the status detail.
    * @param aDetail
    */
   public void addStatusDetail(String aDetail)
   {
      addMessage(aDetail);
   }

   /**
    * Sets the error detail flag.
    *
    * @param aBool
    */
   protected void setErrorDetail(boolean aBool)
   {
      mErrorDetail = true;
   }

   /**
    * Getter for the error detail.
    */
   public boolean isErrorDetail()
   {
      return mErrorDetail;
   }

   /**
    * Accessor for <code>IAeEngineAdministration</code>.
    */
   protected IAeEngineAdministration getAdmin()
   {
      return AeEngineFactory.getEngineAdministration();
   }

   /**
    * @see org.activebpel.rt.war.tags.IAeErrorAwareBean#addError(java.lang.String, java.lang.String, java.lang.String)
    */
   public void addError(String aPropertyName, String aValue,
         String aErrorMessage)
   {
      getPropertyErrors().put(aPropertyName, aValue);
      setErrorDetail(true);
      addMessage(aErrorMessage);
   }

   /**
    * @see org.activebpel.rt.war.tags.IAeErrorAwareBean#getErrorValue(java.lang.String)
    */
   public String getErrorValue(String aPropertyName)
   {
      if (hasPropertyErrors())
      {
         return (String) getPropertyErrors().get(aPropertyName);
      }
      return null;
   }

   /**
    * Returns true if there are property errors
    */
   protected boolean hasPropertyErrors()
   {
      return mPropertyErrors != null;
   }

   /**
    * @return Returns the propertyErrors.
    */
   protected Map getPropertyErrors()
   {
      if (mPropertyErrors == null)
      {
         mPropertyErrors = new HashMap();
      }
      return mPropertyErrors;
   }

   /**
    * @param aPropertyErrors The propertyErrors to set.
    */
   protected void setPropertyErrors(Map aPropertyErrors)
   {
      mPropertyErrors = aPropertyErrors;
   }
}
