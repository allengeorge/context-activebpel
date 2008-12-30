// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis.bpel/src/org/activebpel/rt/axis/bpel/handlers/soap/AeAxisSOAPHeaderInvocationHandler.java,v 1.3 2006/10/24 17:46:3
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.activebpel.rt.util.AeUtil;
import org.apache.axis.message.SOAPHeader;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * An invocation handler that is used to proxy an Axis SOAPHeader.  This handler only kicks
 * in when the getChildElements() method is called.
 */
public class AeAxisSOAPHeaderInvocationHandler implements InvocationHandler
{
   /** The message being proxied. */
   private SOAPHeader mProxiedSOAPHeader;
   /** A cached version of the header converted to a DOM. */
   private Element mCachedHeaderElement;
   /** The <code>getChildElements()</code> method. */
   private Method mGetChildElementsMethod;
   /** The child elements. */
   private List mChildElements;
   
   /**
    * Constructs the invocation handler with the given soap header.
    * 
    * @param aProxiedSOAPHeader
    */
   public AeAxisSOAPHeaderInvocationHandler(SOAPHeader aProxiedSOAPHeader)
   {
      setProxiedSOAPHeader(aProxiedSOAPHeader);
   }

   /**
    * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
    */
   public Object invoke(Object aProxy, Method aMethod, Object[] args) throws Throwable
   {
      if (aMethod.equals(getGetChildElementsMethod()))
      {
         if (getChildElements() == null)
         {
            try
            {
               // Workaround for defect 2141, "I keep getting the error
               // [Fatal Error] :-1:-1: Premature end of file. in my WebLogic
               // log file running different tests":
               // The SOAP header is often absent, but the WebLogic 9.2 XML
               // parser emits this message whenever it parses an empty string,
               // so don't give it an empty string.
               String elementString = getProxiedSOAPHeader().getAsString();
               if (AeUtil.isNullOrEmpty(elementString))
               {
                  setChildElements(Collections.EMPTY_LIST);
               }
               else
               {
                  if (getCachedHeaderElement() == null)
                  {
                     setCachedHeaderElement(getProxiedSOAPHeader().getAsDOM());
                  }
                  NodeList nl = getCachedHeaderElement().getChildNodes();
                  List list = new ArrayList(nl.getLength());
                  for (int i = 0; i < nl.getLength(); i++)
                  {
                     Node node = nl.item(i);
                     if (node instanceof Element)
                        list.add((Element) node);
                  }
                  setChildElements(list);
               }
            }
            catch (Exception ex)
            {
               setChildElements(Collections.EMPTY_LIST);
            }
         }
         return getChildElements().iterator();
      }
      return aMethod.invoke(getProxiedSOAPHeader(), args);
   }

   /**
    * Returns the getChildElements() method reference.
    *
    * @throws NoSuchMethodException
    */
   private Method getGetChildElementsMethod() throws NoSuchMethodException
   {
      if (mGetChildElementsMethod == null)
      {
         mGetChildElementsMethod = javax.xml.soap.SOAPHeader.class.getMethod("getChildElements", null); //$NON-NLS-1$
      }
      return mGetChildElementsMethod;
   }

   /**
    * @return Returns the proxiedSOAPHeader.
    */
   protected SOAPHeader getProxiedSOAPHeader()
   {
      return mProxiedSOAPHeader;
   }

   /**
    * @param aProxiedSOAPHeader The proxiedSOAPHeader to set.
    */
   protected void setProxiedSOAPHeader(SOAPHeader aProxiedSOAPHeader)
   {
      mProxiedSOAPHeader = aProxiedSOAPHeader;
   }

   /**
    * @return Returns the cachedHeaderElement.
    */
   protected Element getCachedHeaderElement()
   {
      return mCachedHeaderElement;
   }

   /**
    * @param aCachedHeaderElement The cachedHeaderElement to set.
    */
   protected void setCachedHeaderElement(Element aCachedHeaderElement)
   {
      mCachedHeaderElement = aCachedHeaderElement;
   }

   /**
    * @return Returns the childElements.
    */
   private List getChildElements()
   {
      return mChildElements;
   }

   /**
    * @param aChildElements The childElements to set.
    */
   private void setChildElements(List aChildElements)
   {
      mChildElements = aChildElements;
   }
}
