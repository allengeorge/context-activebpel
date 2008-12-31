//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/activity/assign/AeLiteralValidator.java,v 1.3 2006/12/14 22:45:2
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
package org.activebpel.rt.bpel.def.validation.activity.assign; 

import java.util.List;

import org.activebpel.rt.AeException;
import org.activebpel.rt.IAeConstants;
import org.activebpel.rt.bpel.def.activity.support.AeLiteralDef;
import org.activebpel.rt.bpel.def.validation.AeBaseValidator;
import org.activebpel.rt.bpel.def.validation.IAeValidationDefs;
import org.jaxen.JaxenException;
import org.jaxen.NamespaceContext;
import org.jaxen.dom.DOMXPath;
import org.w3c.dom.Node;

/**
 * model provides validation for the literal def
 */
public class AeLiteralValidator extends AeBaseValidator
{
   /**
    * ctor
    * @param aDef
    */
   public AeLiteralValidator(AeLiteralDef aDef)
   {
      super(aDef);
   }
   
   /**
    * Type aware getter.
    */
   protected AeLiteralDef getDef()
   {
      return (AeLiteralDef) getDefinition();
   }
   
   /**
    * Returns the literal Node for the copy variable.
    * @return Node the literal node
    */
   public Node getLiteral()
   {
      List nodes = getDef().getChildNodes();
      if (nodes.size() > 0)
      {
         return (Node) nodes.get(0);
      }

      return null;
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.validation.AeBaseValidator#validate()
    */
   public void validate()
   {
      super.validate();
      
      int numChildNodes = getDef().getChildNodes().size();
      
      // Def i/o layer guarantees that getLiteral() returns a Node - even if
      // that node is simply an empty TII - so no need to check for null.
      Node node = getLiteral();
      if (numChildNodes > 1 || (node.getNodeType() != Node.ELEMENT_NODE
            && node.getNodeType() != Node.TEXT_NODE && node.getNodeType() != Node.CDATA_SECTION_NODE))
      {
         getReporter().addError(IAeValidationDefs.ERROR_INVALID_LITERAL, null, getDefinition());
      }

      try
      {
         // Make sure that there aren't any xsi:schemaLocation attributes 
         // declared in the literal.
         DOMXPath xpath = new DOMXPath("descendant-or-self::*[@xsi:schemaLocation]"); //$NON-NLS-1$
         xpath.setNamespaceContext(new NamespaceContext()
         {
            public String translateNamespacePrefixToUri(String aPrefix)
            {
               return IAeConstants.W3C_XML_SCHEMA_INSTANCE;
            }
         });
         List nodes = (List) xpath.evaluate(node);
         if (nodes.size() > 0)
         {
            getReporter().addWarning(IAeValidationDefs.WARNING_SCHEMA_LOCATION_IN_LITERAL, null, getDefinition());
         }
      }
      catch (JaxenException e)
      {
         AeException.logError(e);
      }
   }
}
