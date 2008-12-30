//$Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/wsdl/def/AeBindingUtils.java,v 1.4 2006/10/25 16:07:0
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
package org.activebpel.rt.wsdl.def; 

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.wsdl.extensions.ElementExtensible;
import javax.wsdl.extensions.UnknownExtensibilityElement;
import javax.xml.namespace.QName;

import org.activebpel.rt.AeException;
import org.activebpel.rt.AeMessages;
import org.activebpel.rt.util.AeXmlUtil;

/**
 * Utilities for extracting info from WSDL bindings 
 */
public class AeBindingUtils
{
   private static final UnknownExtensibilityElement[] EMPTY = new UnknownExtensibilityElement[0];
   /** constant for soap:header */
   public static final QName SOAP_HEADER = new QName(IAeBPELExtendedWSDLConst.SOAP_NAMESPACE, "header"); //$NON-NLS-1$
   /** constant for soap:body */
   public static final QName SOAP_BODY = new QName(IAeBPELExtendedWSDLConst.SOAP_NAMESPACE, "body"); //$NON-NLS-1$
   
   /**
    * Gets a collection of part names that are destined for the header
    * @param aElementExtensible an input or output binding
    * @param aMessageQName name of the message, we only support parts from the same message going in the header
    */
   public static Collection getPartsForHeader(ElementExtensible aElementExtensible, QName aMessageQName)
   {
      Collection coll = null;
      UnknownExtensibilityElement[] headerExtElements = getUnknownExtensibilityElementsByName(aElementExtensible, SOAP_HEADER); 
      for (int i=0; i<headerExtElements.length; i++)
      {
         // found a header, make sure the message matches
         UnknownExtensibilityElement extElement = headerExtElements[i];
         QName msgQName = AeXmlUtil.createQName(extElement.getElement(), extElement.getElement().getAttribute("message")); //$NON-NLS-1$
         if (aMessageQName.equals(msgQName))
         {
            String partName = extElement.getElement().getAttribute("part"); //$NON-NLS-1$
            if (coll == null)
               coll = new HashSet();
            coll.add(partName);
         }
         else
         {
            Object[] args = new Object[2];
            args[0] = aMessageQName;
            args[1] = msgQName;
            AeException.logWarning(AeMessages.format("AeBindingUtils.DifferentMessageInHeader", args)); //$NON-NLS-1$
         }
      }
      return coll == null? Collections.EMPTY_LIST : coll;
   }
   
   /**
    * Walks the extensibility elements and returns that that match the name passed in.
    * @param aElement extensible element (either an input or output binding)
    * @param aName name of the element we're looking for (e.g. soap:header, soap:body)
    */
   public static UnknownExtensibilityElement[] getUnknownExtensibilityElementsByName(ElementExtensible aElement, QName aName)
   {
      List values = null;
      
      for (Iterator iter = aElement.getExtensibilityElements().iterator(); iter.hasNext();)
      {
         Object obj = iter.next();

         if ( obj instanceof UnknownExtensibilityElement )
         {
            UnknownExtensibilityElement extElement = (UnknownExtensibilityElement)obj;
            if ( aName.equals(extElement.getElementType()) ) 
            {
               if (values == null)
                  values = new ArrayList();
               values.add(extElement);
            }
         }
      }

      if (values == null)
      {
         return EMPTY;
      }
      else
      {
         UnknownExtensibilityElement[] elements = new UnknownExtensibilityElement[values.size()];
         return (UnknownExtensibilityElement[]) values.toArray(elements);
      }
   }
}
 
