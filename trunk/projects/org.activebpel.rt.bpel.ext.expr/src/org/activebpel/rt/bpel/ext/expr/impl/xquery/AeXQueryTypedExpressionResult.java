//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.ext.expr/src/org/activebpel/rt/bpel/ext/expr/impl/xquery/AeXQueryTypedExpressionResult.java,v 1.4 2007/08/21 14:59:2
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
package org.activebpel.rt.bpel.ext.expr.impl.xquery;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.activebpel.rt.xml.schema.AeTypeMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * A helper class for creating a List of objects from the result of a Saxon XQuery.
 */
public class AeXQueryTypedExpressionResult
{
   private static QName RESULT_TEXT_QNAME = new QName("http://saxon.sf.net/xquery-results", "text"); //$NON-NLS-1$ //$NON-NLS-2$
   
   /**
    * Creates a List of AeXQueryTypedExpressionResult objects from the given result Document.  The
    * result Document is obtained by executing a XQuery using Saxon.
    * 
    * @param aResultDoc
    */
   public static List createResultList(Document aResultDoc)
   {
      List rval = new ArrayList();
      
      Element rootElem = aResultDoc.getDocumentElement();
      NodeList children = rootElem.getChildNodes();
      for (int i = 0; i < children.getLength(); i++)
      {
         Node child = children.item(i);
         if (child instanceof Element)
         {
            Element elem = (Element) child;
            Object obj = null;
            if ("element".equals(elem.getLocalName()) || "document".equals(elem.getLocalName())) //$NON-NLS-1$ //$NON-NLS-2$
            {
               obj = AeXmlUtil.cloneElement(AeXmlUtil.getFirstSubElement(elem));
            }
            else if ("atomic-value".equals(elem.getLocalName())) //$NON-NLS-1$
            {
               String str = AeXmlUtil.getText(elem);
               QName type = AeXmlUtil.getXSIType(elem);
               obj = new AeTypeMapping().deserialize(type, str);
            }
            else if (AeUtil.compareObjects(RESULT_TEXT_QNAME.getNamespaceURI(), elem.getNamespaceURI()) &&
                  AeUtil.compareObjects(RESULT_TEXT_QNAME.getLocalPart(), elem.getLocalName()))
            {
               obj = AeXmlUtil.getText(elem);
            }
            else
            {
               throw new RuntimeException("Error: unknown Saxon return type."); //$NON-NLS-1$
            }
            rval.add(obj);
         }
      }
      
      return rval;
   }
   
   /**
    * Private c'tor.
    */
   private AeXQueryTypedExpressionResult()
   {
   }
}
