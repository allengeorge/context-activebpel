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
package org.activebpel.rt.bpel.def.validation.variable;

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.bpel.def.IAeBPELConstants;
import org.activebpel.rt.bpel.def.validation.AeVariableValidator;
import org.activebpel.rt.bpel.def.validation.IAeValidator;
import org.activebpel.rt.message.AeMessagePartTypeInfo;
import org.activebpel.rt.xml.IAeNamespaceContext;
import org.exolab.castor.xml.schema.XMLType;

/**
 * Subclass of AeMessagePartUsage that adds support for query validation
 */
public class AeMessagePartQueryUsage extends AeMessagePartUsage
{
   /** query used on part */
   private String mQuery;
   
   /**
    * ctor
    * @param aVariableValidator - the variable validator
    * @param aValidator - validator using the variable
    * @param aPart - name of the part
    * @param aQuery - query used on the part
    */
   public AeMessagePartQueryUsage(AeVariableValidator aVariableValidator, IAeValidator aValidator, String aPart, String aQuery)
   {
      super(aVariableValidator, aValidator, aPart);
      setQuery(aQuery);
   }
   
   /**
    * Relies on super to validate message and part usage and then validates the query
    * @see org.activebpel.rt.bpel.def.validation.variable.AeVariableUsage#validate()
    */
   public boolean validate()
   {
      if (super.validate())
      {
         return validateQuery();
      }
      return false;
   }

   /**
    * Validates the query using the xpath query validator on the process validator
    */
   protected boolean validateQuery()
   {
      // TODO (EPW) once there is a query framework, do additional validation here
      boolean isValid;

      try
      {
         // Add a warning if a WS-BPEL process uses an absolute path query with a complex type.
         String processNS = getVariableValidator().getProcessValidator().getProcessDef().getNamespace();
         if (!IAeBPELConstants.BPWS_NAMESPACE_URI.equals(processNS))
         {
            if (getQuery().startsWith("/") && !getVariableValidator().getDef().isPartElement(getPart())) //$NON-NLS-1$
            {
               getVariableValidator().getReporter().addWarning(
                     AeMessages.getString("AeVariableUsage.AbsolutePathSyntaxWarning"), //$NON-NLS-1$
                     new String[] { getQuery() }, getValidator().getDefinition());
            }
         }

         AeMessagePartTypeInfo part = getVariableValidator().getDef().getPartInfo(getPart());
         XMLType type = getVariableValidator().getDef().getPartType(part.getName());
         QName root = part.getElementName();
         if(root == null)
            root = new QName(null, part.getName());
         try
         {
            IAeNamespaceContext nsContext = createNamespaceContextForQuery();
            getVariableValidator().getProcessValidator().getXPathQueryValidator().validate(nsContext,
                  getQuery(), type, root);
         }
         catch (Exception ex)
         {
            getVariableValidator().getReporter().addWarning( AeVariableValidator.ERROR_INVALID_XPATH,
                                    new String[] { getQuery(), ex.getLocalizedMessage() },
                                    getValidator().getDefinition() );
         }
         isValid = true;
      }
      catch (Exception ex)
      {
         getVariableValidator().getReporter().addError( AeVariableValidator.ERROR_INVALID_XPATH,
                                 new String[] { getQuery(), ex.getLocalizedMessage() },
                                 getValidator().getDefinition() );
         isValid = false;
      }

      return isValid;
   }

   /**
    * Getter for the query
    */
   protected String getQuery()
   {
      return mQuery;
   }
   
   /**
    * Setter for the query
    * @param aQuery
    */
   protected void setQuery(String aQuery)
   {
      mQuery = aQuery;
   }
}
