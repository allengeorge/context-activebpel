// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis.bpel/src/org/activebpel/rt/axis/bpel/handlers/soap/AeAxisObjectProxyFactory.java,v 1.4 2006/11/09 18:34:4
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

import org.activebpel.rt.AeException;
import org.apache.axis.Message;
import org.apache.axis.MessageContext;

import javax.xml.rpc.handler.soap.SOAPMessageContext;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * A factory for creating proxies of Axis SOAP objects.  This exists so that we can return our own 
 * versions of the Axis soap objects that fix some problems with them.
 */
public class AeAxisObjectProxyFactory
{
   /**
    * Wrap the <code>MessageContext</code> in the <code>InvocationHandler</code> instance.
    * 
    * @param aMessageContext
    */
   public static SOAPMessageContext getMessageContextProxy(MessageContext aMessageContext)
   {
      return getMessageContextProxy(aMessageContext, SOAPMessageContext.class);
   }
   
   /**
    * Wrap the <code>MessageContext</code> in the <code>InvocationHandler</code> instance.
    * 
    * @param aMessageContext
    * @param aClass SOAPMessageContext class
    */
   public static SOAPMessageContext getMessageContextProxy(MessageContext aMessageContext, Class aClass)
   {
      InvocationHandler handler = new AeAxisMessageContextInvocationHandler(aMessageContext);

      try
      {
         // Fix for defect 1957, which is essentially about things breaking on
         // newer versions of JBoss 4.0: use the context class loader here
         // instead of what we used to use (see below). The context class loader
         // at this point is the active-bpel web application class loader.
         return (SOAPMessageContext) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
            new Class[] { aClass }, handler);
      }
      catch (Throwable t)
      {
         AeException.logError(t);

         // This is what we used to do. Fall back to it, but it won't work on
         // JBoss 4.0.4 (and other JBoss versions after 4.0.0).
         return (SOAPMessageContext) Proxy.newProxyInstance(aMessageContext.getClass().getClassLoader(),
            new Class[] { aClass }, handler);
      }
   }

   /**
    * Wrap the <code>Message</code> in the <code>InvocationHandler</code> instance.
    * 
    * @param aMessage
    */
   public static SOAPMessage getMessageProxy(Message aMessage)
   {
      return new AeSOAPMessageWrapper(aMessage);
   }

   /**
    * Wrap the <code>SOAPHeader</code> in the <code>InvocationHandler</code> instance.
    * 
    * @param aHeader
    * @param aClass SOAPHeader class
    */
   public static SOAPHeader getSOAPHeaderProxy(org.apache.axis.message.SOAPHeader aHeader, Class aClass)
   {
      InvocationHandler handler = new AeAxisSOAPHeaderInvocationHandler(aHeader);

      try
      {
         // Fix for defect 1957, which is essentially about things breaking on
         // newer versions of JBoss 4.0: use the context class loader here
         // instead of what we used to use (see below). The context class loader
         // at this point is the active-bpel web application class loader.
         return (SOAPHeader) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
            new Class[] { aClass }, handler);
      }
      catch (Throwable t)
      {
         AeException.logError(t);

         // This is what we used to do. Fall back to it, but it won't work on
         // JBoss 4.0.4 (and other JBoss versions after 4.0.0).
         return (SOAPHeader) Proxy.newProxyInstance(aHeader.getClass().getClassLoader(),
            new Class[] { aClass }, handler);
      }
   }
   
   /**
    * Wrap the <code>SOAPHeader</code> in the <code>InvocationHandler</code> instance.
    * 
    * @param aHeader
    */
   public static SOAPHeader getSOAPHeaderProxy(org.apache.axis.message.SOAPHeader aHeader)
   {
      return getSOAPHeaderProxy(aHeader, SOAPHeader.class);
   }
}
