//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/function/attachment/AeAbstractAttachmentFunction.java,v 1.6 2007/09/04 15:51:3
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
package org.activebpel.rt.bpel.impl.function.attachment; 

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import org.activebpel.rt.attachment.IAeAttachmentItem;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeVariable;
import org.activebpel.rt.bpel.function.AeFunctionCallException;
import org.activebpel.rt.bpel.function.IAeFunctionExecutionContext;
import org.activebpel.rt.bpel.impl.AeAbstractBpelObject;
import org.activebpel.rt.bpel.impl.function.AeAbstractBpelFunction;
import org.activebpel.rt.bpel.impl.visitors.AeInScopeVariableFindingVisitor;

/**
 * Base class for attachment functions
 */
public abstract class AeAbstractAttachmentFunction extends AeAbstractBpelFunction
{
   /**
    * Ctor
    * @param aFunctionName
    */
   public AeAbstractAttachmentFunction(String aFunctionName)
   {
      super(aFunctionName);
   }

   /**
    * Gets the attachment item for the given variable and item number
    * @param aContext
    * @param aVariableName
    * @param aItemNumber
    * @throws AeFunctionCallException
    */
   protected IAeAttachmentItem getAttachment(IAeFunctionExecutionContext aContext, String aVariableName, int aItemNumber) throws AeFunctionCallException
   {
      IAeVariable variable = getVariable(aContext, aVariableName);
      
      int offset = getItemIndex(aItemNumber);
      
      if ( offset >= variable.getAttachmentData().size() || offset < 0 )
      {
         // return the itemnumber not the index  for human readability
         Object[] args = { getFunctionName(),  new Integer(offset+1), new Integer(offset), variable.getName() };
         throwFunctionException(INVALID_ATTACHMENT_INDEX, args);
      }

      Object itemObject = variable.getAttachmentData().get(offset);
     
      if ( itemObject == null )
         throwFunctionException(NULL_RESULT_ERROR, getFunctionName());
      
      if ( !(itemObject instanceof IAeAttachmentItem) )
         throwFunctionException(INVALID_ATTACHMENT_OBJECT_TYPE, getFunctionName());

      return (IAeAttachmentItem)itemObject;
      
     
   }
   
   /**
    * Returns an initialized variable 
    * @param aContext
    * @param aVariableName
    * @return IAeVariable object
    * @throws AeFunctionCallException when the variable is not initialized
    */
   protected IAeVariable getVariable(IAeFunctionExecutionContext aContext, String aVariableName) throws AeFunctionCallException
   {
      IAeVariable variable = getVariable(aContext.getAbstractBpelObject(), aVariableName);
      if ( variable == null )
      {
         Object[] args = { getFunctionName(), variable, aContext };
         throwFunctionException(VARIABLE_NOT_INITIALIZED, args);
      }
      return variable;
   }
   
   /**
    * Gets the attachement index from the passed itemNumber with validation.
    * @param aItemNumber
    * @return itemIndex 
    * @throws AeFunctionCallException
    */
   protected int getItemIndex(int aItemNumber) throws AeFunctionCallException
   {
      // Req 160 requires itemNumbers to start from one, so the here it is converted to the index that starts from zero
      return aItemNumber - 1;
   }
   
   /**
    * Resolves the given String, which must be a space separated list
    * of variable names, to a List of IAeVariable instances.  Throws a
    * function exception if a variable is not found.
    * 
    * @param aContext
    * @param aVariableNames
    * @return collection of variables
    */
   protected Collection resolveVariables(IAeFunctionExecutionContext aContext, String aVariableNames) throws AeFunctionCallException
   {
      return resolveVariables(aContext, aVariableNames, null);
   }
   
   /**
    * Resolves the given String, which must be a space separated list
    * of variable names, to a List of IAeVariable instances.  Throws a
    * function exception if a variable is not found.
    * 
    * @param aContext
    * @param aVariableNames
    * @param aToVariableName
    * @return collection of variables
    */
   protected Collection resolveVariables(IAeFunctionExecutionContext aContext, String aVariableNames,
         String aToVariableName) throws AeFunctionCallException
   {
      List variables = new ArrayList();
      
      for (StringTokenizer tokenizer = new StringTokenizer(aVariableNames); tokenizer.hasMoreTokens(); )
      {
         String variableName = tokenizer.nextToken();
         // If * is one of the variable names on the list, then simply return
         // all of the variables in-scope.
         if ("*".equals(variableName)) //$NON-NLS-1$
            return resolveAllVariablesInScope(aContext, aToVariableName);
         IAeVariable variable = getVariable(aContext.getAbstractBpelObject(), variableName);
         if (variable == null)
            throwFunctionException(UNRESOLVED_VARIABLE, variableName);
         variables.add(variable);
      }
      
      return variables;
   }
   
   /**
    * Returns a list of all variables currently in-scope.
    * 
    * @param aContext
    * @throws AeFunctionCallException
    */
   protected Collection resolveAllVariablesInScope(IAeFunctionExecutionContext aContext,
         String aToVariableName) throws AeFunctionCallException
   {
      AeAbstractBpelObject bpelObj = aContext.getAbstractBpelObject();
      AeInScopeVariableFindingVisitor visitor = new AeInScopeVariableFindingVisitor(Collections.singleton(aToVariableName));
      try
      {
         bpelObj.accept(visitor);
      }
      catch (AeBusinessProcessException ex)
      {
         throw new AeFunctionCallException(ex);
      }
      
      return visitor.getVariables();
   }
}
 
