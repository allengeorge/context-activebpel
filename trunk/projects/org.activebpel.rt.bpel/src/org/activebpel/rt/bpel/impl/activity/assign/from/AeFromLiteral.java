//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/assign/from/AeFromLiteral.java,v 1.3 2006/07/14 15:46:5
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
package org.activebpel.rt.bpel.impl.activity.assign.from; 

import java.util.HashMap;
import java.util.Map;

import org.activebpel.rt.bpel.def.activity.support.AeFromDef;
import org.activebpel.rt.bpel.impl.AeBpelException;
import org.activebpel.rt.util.AeXmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Handles getting the literal value. 
 */
public class AeFromLiteral extends AeFromBase
{
   /** literal node */
   private Node mLiteral;
   
   /**
    * Ctor accepts def
    * 
    * @param aFromDef
    */
   public AeFromLiteral(AeFromDef aFromDef)
   {
      this(aFromDef.getLiteral());
   }
   
   /**
    * Ctor accepts node
    * @param aNode
    */
   public AeFromLiteral(Node aNode)
   {
      setLiteral(aNode);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.assign.IAeFrom#getFromData()
    */
   public Object getFromData() throws AeBpelException
   {
      Object data = null;
      // Need to synchronize on the document fragment we are copying, or bad things can happen under load
      synchronized(getLiteral())
      {
         Document doc = AeXmlUtil.newDocument();
         Node node = doc.importNode(getLiteral(), true);
         if (node instanceof Element)
         {
            Map namespaceMap = new HashMap();
            AeXmlUtil.getDeclaredNamespaces((Element)getLiteral(), namespaceMap);
            AeXmlUtil.declareNamespacePrefixes((Element)node, namespaceMap);

            doc.appendChild(node);
            data = doc.getDocumentElement();
         }
         else
         {
            data = node;
         }
      }
      return data;
   }

   /**
    * @return Returns the literal.
    */
   public Node getLiteral()
   {
      return mLiteral;
   }

   /**
    * @param aLiteral The literal to set.
    */
   public void setLiteral(Node aLiteral)
   {
      mLiteral = aLiteral;
   }

}
 
