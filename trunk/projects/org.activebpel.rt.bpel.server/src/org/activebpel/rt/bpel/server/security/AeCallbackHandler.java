//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/security/AeCallbackHandler.java,v 1.1 2007/02/13 15:26:5
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
package org.activebpel.rt.bpel.server.security;

import java.io.IOException;
import java.util.Map;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.TextOutputCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.server.AeMessages;

/**
 * JAAS Callback handler that handles TextOutputCallback, NameCallback, PasswordCallback
 */
public class AeCallbackHandler implements CallbackHandler
{
   private String mUser;
   private String mPassword;

   /**
    * Constructs a callback instance using the Username and Password
    * values from the map.
    * 
    * @param aConfig
    */
   public AeCallbackHandler(Map aConfig)
   {
      if (aConfig != null)
      {
         mUser = (String) aConfig.get(IAeLoginProvider.USERNAME_ENTRY);
         mPassword = (String) aConfig.get(IAeLoginProvider.PASSWORD_ENTRY);
      }
   }
   
   /**
    * @see javax.security.auth.callback.CallbackHandler#handle(javax.security.auth.callback.Callback[])
    */
   public void handle(Callback[] aCallbacks) throws IOException, UnsupportedCallbackException
   {
      for(int i = 0; i < aCallbacks.length; i++)
      {
         handle(aCallbacks[i]);
      }
   }

   /**
    * Handles a callback. Subclasses should this override to support additional callbacks. 
    * 
    * @param aCallback
    * @throws IOException
    * @throws UnsupportedCallbackException
    */
   protected void handle(Callback aCallback) throws IOException, UnsupportedCallbackException
   {
      if(aCallback instanceof TextOutputCallback)
      {
         handleCallback((TextOutputCallback) aCallback);
      }
      else if(aCallback instanceof NameCallback)
      {
         handleCallback((NameCallback) aCallback);
      }
      else if(aCallback instanceof PasswordCallback)
      { 
         handleCallback((PasswordCallback) aCallback);
      }
      else
      {
        throw new UnsupportedCallbackException(aCallback, AeMessages.format("AeCallbackHandler.0", aCallback.getClass().getName())); //$NON-NLS-1$
      }
   }
   
   /**
    * Handle a password callback
    * @param aCallback
    */
   protected void handleCallback(PasswordCallback aCallback)
   {
      // Password
      aCallback.setPassword(getPassword().toCharArray());
   }
   
   /**
    * Handle a username callback
    * @param aCallback
    */
   protected void handleCallback(NameCallback aCallback)
   {
      // Username
      aCallback.setName(getUser());
   }
   
   /**
    * Handle a text output callback by writing the message to the logger.
    * @param aCallback
    */
   protected void handleCallback(TextOutputCallback aCallback)
   {
      // log the message according to the specified type
      if (aCallback.getMessageType() == TextOutputCallback.ERROR)
      {
         AeSecurityException ex = new AeSecurityException(aCallback.getMessage());
         AeException.logError(ex);
      }
      else if (aCallback.getMessageType() == TextOutputCallback.WARNING)
      {
         AeException.logWarning(aCallback.getMessage());
      }
      else
      {
         AeException.info(aCallback.getMessage());
      }
   }
   
   /**
    * @return the password
    */
   public String getPassword()
   {
      return mPassword;
   }

   /**
    * @return the user
    */
   public String getUser()
   {
      return mUser;
   }

   /**
    * @param aPassword the password to set
    */
   public void setPassword(String aPassword)
   {
      mPassword = aPassword;
   }

   /**
    * @param aUser the user to set
    */
   public void setUser(String aUser)
   {
      mUser = aUser;
   }
   
}
