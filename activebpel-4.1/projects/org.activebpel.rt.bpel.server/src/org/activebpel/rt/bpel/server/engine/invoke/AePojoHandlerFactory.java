//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/invoke/AePojoHandlerFactory.java,v 1.1 2005/06/22 16:53:5
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
package org.activebpel.rt.bpel.server.engine.invoke; 

import java.util.Hashtable;
import java.util.Map;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.server.AeMessages;
import org.activebpel.rt.bpel.server.engine.AeInvokeHandlerUri;
import org.activebpel.rt.bpel.server.engine.IAeInvokeHandlerFactory;
import org.activebpel.wsio.invoke.IAeInvoke;
import org.activebpel.wsio.invoke.IAeInvokeHandler;

/**
 * Handles the java protocol where the invoke handlers are "plain old java objects"
 * or POJO. The fully qualified class names appear in the custom invoker uri immediately
 * following the protocol but before any query params.
 * 
 * i.e. java:com.my.custom.InvokerHandler?name=value 
 * 
 * The objects are instantiated and cached so they should be reentrant. 
 */
public class AePojoHandlerFactory implements IAeInvokeHandlerFactory
{
   /** map of class names to instances */
   private Map mInvokers = new Hashtable();
   
   /**
    * @see org.activebpel.rt.bpel.server.engine.IAeInvokeHandlerFactory#createInvokeHandler(org.activebpel.wsio.invoke.IAeInvoke)
    */
   public IAeInvokeHandler createInvokeHandler(IAeInvoke aInvoke)
         throws AeBusinessProcessException
   {
      String clazz = AeInvokeHandlerUri.getInvokerString(aInvoke.getInvokeHandler());
      IAeInvokeHandler handler = (IAeInvokeHandler) getInvokers().get(clazz);
      if (handler == null)
      {
         try
         {
            handler = (IAeInvokeHandler) Class.forName(clazz).newInstance();
            getInvokers().put(clazz, handler);
         }
         catch(Exception e)
         {
            throw new AeBusinessProcessException( AeMessages.format("AePojoHandlerFactory.ERROR_CREATING_HANDLER", clazz), e ); //$NON-NLS-1$
         }
      }
      return handler;
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.engine.IAeInvokeHandlerFactory#getQueryData(org.activebpel.wsio.invoke.IAeInvoke)
    */
   public String getQueryData(IAeInvoke aInvoke)
   {
      return AeInvokeHandlerUri.getQueryString(aInvoke.getInvokeHandler());
   }
   
   /**
    * @return Returns the invokers.
    */
   protected Map getInvokers()
   {
      return mInvokers;
   }
}
 
