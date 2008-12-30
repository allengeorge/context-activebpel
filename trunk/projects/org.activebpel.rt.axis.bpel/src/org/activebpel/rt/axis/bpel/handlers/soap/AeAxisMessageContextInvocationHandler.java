// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis.bpel/src/org/activebpel/rt/axis/bpel/handlers/soap/AeAxisMessageContextInvocationHandler.java,v 1.2 2006/10/04 22:12:5
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
package org.activebpel.rt.axis.bpel.handlers.soap;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import javax.xml.rpc.handler.soap.SOAPMessageContext;

import org.apache.axis.Message;
import org.apache.axis.MessageContext;

/**
 * An invocation handler that is used to proxy an Axis MessageContext.  This handler only kicks
 * in when the getMessage() method is called (in order to return a proxied Message object).
 */
public class AeAxisMessageContextInvocationHandler implements InvocationHandler
{
   /** The proxied msg ctx. */
   private MessageContext mProxiedMessageContext;

   /**
    * Constructs the invocation handler with the given message context.
    *
    * @param aProxiedMessageContext
    */
   public AeAxisMessageContextInvocationHandler(MessageContext aProxiedMessageContext)
   {
      setProxiedMessageContext(aProxiedMessageContext);
   }

   /**
    * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
    */
   public Object invoke(Object aProxy, Method aMethod, Object[] args) throws Throwable
   {
      if (aMethod.equals(getGetMessageMethod()))
      {
         return AeAxisObjectProxyFactory.getMessageProxy((Message) getProxiedMessageContext().getMessage());
      }
      return aMethod.invoke(getProxiedMessageContext(), args);
   }

   /**
    * Returns the getMessage() method reference.
    *
    * @throws NoSuchMethodException
    */
   private Method getGetMessageMethod() throws NoSuchMethodException
   {
      return SOAPMessageContext.class.getMethod("getMessage", null); //$NON-NLS-1$
   }

   /**
    * @return Returns the proxiedMessageContext.
    */
   protected MessageContext getProxiedMessageContext()
   {
      return mProxiedMessageContext;
   }

   /**
    * @param aProxiedMessageContext The proxiedMessageContext to set.
    */
   protected void setProxiedMessageContext(MessageContext aProxiedMessageContext)
   {
      mProxiedMessageContext = aProxiedMessageContext;
   }
}
