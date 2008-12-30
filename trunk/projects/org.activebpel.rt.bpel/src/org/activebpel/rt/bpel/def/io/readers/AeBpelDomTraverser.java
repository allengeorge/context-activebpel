// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/io/readers/AeBpelDomTraverser.java,v 1.12 2007/04/03 20:38:4
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
package org.activebpel.rt.bpel.def.io.readers;

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.bpel.def.AeBaseDef;
import org.activebpel.rt.bpel.def.AeProcessDef;
import org.activebpel.rt.bpel.def.IAeBPELConstants;
import org.activebpel.rt.bpel.def.activity.support.AeExpressionBaseDef;
import org.activebpel.rt.bpel.def.activity.support.AeFromDef;
import org.activebpel.rt.bpel.def.io.AeCommentIO;
import org.activebpel.rt.bpel.def.io.readers.def.IAeBpelDefReader;
import org.activebpel.rt.bpel.def.io.registry.IAeBpelRegistry;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Drives the BPEL DOM traversal and reader lookup process.
 */
public class AeBpelDomTraverser implements IAeBPELConstants
{
   /**
    * Factory method used to create a bpel dom traverser.
    * 
    * @param aProcessElement
    * @param aReg
    * @param aExtReg
    */
   public static AeBpelDomTraverser createBpelDomTraverser(Element aProcessElement, IAeBpelRegistry aReg)
   {
      String ns = aProcessElement.getNamespaceURI();
      if (IAeBPELConstants.WSBPEL_2_0_NAMESPACE_URI.equals(ns))
      {
         return new AeWSBPELBpelDomTraverser(aReg);
      }
      return new AeBpelDomTraverser(aReg);
   }
   
   /** registry for bpel element readers */
   private IAeBpelRegistry mBpelRegistry;
   /** AeProcessDef result of deserialization */
   private AeProcessDef mProcessDef;
   /** preserves comments for bpel and extension element objects */
   private AeCommentIO mCommentIO;
   
   /**
    * Constructor.
    * @param aReg the bpel element registry
    * @param aExtReg the extension element registry
    */
   protected AeBpelDomTraverser(IAeBpelRegistry aReg)
   {
      mBpelRegistry = aReg;
      mCommentIO = new AeCommentIO();
   }
   
   /**
    * Starts the traversal process which reads in the entire BPEL doc and
    * creates the corresponding AeBaseDef object model.
    * 
    * @param aProcessElement bpel process element
    * @throws AeBusinessProcessException
    */
   public void traverseProcess( Element aProcessElement)
   throws AeBusinessProcessException
   {
      QName qName = extractQName(aProcessElement);
      IAeBpelDefReader processReader = mBpelRegistry.getReader(null, qName);
      AeProcessDef processDef = (AeProcessDef)processReader.read(null, aProcessElement);
      preserveProcessComments(aProcessElement, processDef);
      traverseChildren(processDef, aProcessElement);
      processDef.setNamespace(aProcessElement.getNamespaceURI());
      mProcessDef = processDef;    
   }
   
   /**
    * Preserve the comments above the passed element for the passed process definition.
    * 
    * @param aProcessElement
    * @param aProcessDef
    */
   protected void preserveProcessComments(Element aProcessElement, AeProcessDef aProcessDef)
   {
      // process comments for process element.
      NodeList children = aProcessElement.getParentNode().getChildNodes();
      for (int i = 0; i < children.getLength(); ++i)
      {
         Node node = children.item(i);
         if ( node.getNodeType() == Node.COMMENT_NODE )         
         {
            mCommentIO.appendToComments(node.getNodeValue());
         }
      }
      
      // assign any comments found to the process definition
      mCommentIO.preserveComments(aProcessDef);
   }

   /**
    * Determine is the current element is a BPEL element or an
    * extension element and pass it off to the corresponding reader.
    * If it is a BPEL element, traverse its children as well.
    * @param aParentDef
    * @param aElement
    * @throws AeBusinessProcessException
    */
   protected void traverseBpel(AeBaseDef aParentDef, Element aElement) throws AeBusinessProcessException
   {
      QName qName = extractQName( aElement );

      AeBaseDef newDef = null;
      IAeBpelDefReader reader = getBpelRegistry().getReader( aParentDef, qName );
      // Read the def if the returned reader is not null.
      if (reader != null)
         newDef = reader.read( aParentDef, aElement );
      // If the def was null, there was an error reading it or no reader was found...so read it in as an
      // extension element.
      if (newDef == null)
         newDef = getBpelRegistry().getExtensionReader().read(aParentDef, aElement);

      // if the reader didn't return a def then there was a problem during the
      // deserialization. The likely problem is that the BPEL file we're reading is
      // invalid (e.g. multiple child activities where only one or none are allowed)
      // We'll treat this element as an extensibility element and the user will
      // get a warning during deployment.
      if (newDef != null)
      {
         mCommentIO.preserveComments(newDef);
         traverseChildren( newDef, aElement );
      }
      else
      {
         throw new AeBusinessProcessException(AeMessages.getString("AeBpelDomTraverser.FailedToReadBPELElementError")); //$NON-NLS-1$
      }
   }
   
   /**
    * Returns true if the children should be traversed.
    * 
    * @param aDef
    * @param aElement
    */
   protected boolean shouldTraverseChildren(AeBaseDef aDef, Element aElement)
   {
      return !(aDef instanceof AeFromDef) && !(aDef instanceof AeExpressionBaseDef);
   }
   
   /**
    * Propogates the recursive traversal.
    * @param aDef will serve as the parent def for any recursive reads
    * @param aElement read in the elements child elements
    * @throws AeBusinessProcessException
    */
   protected void traverseChildren( AeBaseDef aDef, Element aElement )
   throws AeBusinessProcessException
   {
      // assign var def readers will handle children
      // because of potential literal values
      if (!shouldTraverseChildren(aDef, aElement))
      {
         return;
      }
      
      // look for element or comment nodes
      // process child element nodes and 
      // store text from comment nodes
      NodeList children = aElement.getChildNodes();
      if (children != null)
      {
         for (int i = 0; i < children.getLength(); ++i)
         {
            Node node = children.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
               traverseBpel(aDef, (Element)node);
            }
            else if ( node.getNodeType() == Node.COMMENT_NODE )         
            {
               mCommentIO.appendToComments(node.getNodeValue());
            }
         }
      }
   }
   
   /**
    * Convenience method for extracting element QName.
    * @param aElement
    * @return element qname
    */
   protected QName extractQName(Element aElement)
   {
      return new QName(aElement.getNamespaceURI(),aElement.getLocalName());
   }

   /**
    * Accessor for AeProcessDef after deserialization.
    * @return AeProcessDef object model
    */
   public AeProcessDef getProcessDef()
   {
      return mProcessDef;
   }
   
   /**
    * Accessor for bpel registry.
    * @return BPEL element registry
    */
   protected IAeBpelRegistry getBpelRegistry()
   {
      return mBpelRegistry;
   }
}
