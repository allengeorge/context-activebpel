//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/function/AeAbstractBpelFunction.java,v 1.9 2007/09/04 15:50:4
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
package org.activebpel.rt.bpel.impl.function;

import java.util.List;

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeVariable;
import org.activebpel.rt.bpel.function.AeFunctionCallException;
import org.activebpel.rt.bpel.function.AeFunctionExceptions;
import org.activebpel.rt.bpel.function.IAeFunction;
import org.activebpel.rt.bpel.impl.AeAbstractBpelObject;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.wsdl.def.IAePropertyAlias;

/**
 * Base class for standard <code>IAeFunction</code> impls.
 */
public abstract class AeAbstractBpelFunction extends AeFunctionExceptions implements IAeFunction
{ 
   /** name of the function - used for reporting errors */
   private String mFunctionName;
   
   /**
    * Ctor
    * @param aFunctionName
    */
   protected AeAbstractBpelFunction(String aFunctionName)
   {
      setFunctionName(aFunctionName);
   }
   
   /**
    * Constructor.
    * TODO: (JB) lock this out for all functions
    */
   protected AeAbstractBpelFunction()
   {
   }

   /**
    * Delegates the call to the parent.
    *
    * @see org.activebpel.rt.bpel.impl.IAeBpelObject#findVariable(java.lang.String)
    */
   public IAeVariable getVariable(AeAbstractBpelObject aBpelObj, String aName)
   {
      return aBpelObj.findVariable(aName);
   }

   /**
    * Gets the message property from the wsdl
    *
    * @param aBpelObj
    * @param aPropertyAliasType
    * @param aName
    * @param aPropName
    * @throws AeBusinessProcessException
    */
   protected IAePropertyAlias getPropertyAlias(AeAbstractBpelObject aBpelObj, int aPropertyAliasType, QName aName, QName aPropName) throws AeBusinessProcessException
   {
      return aBpelObj.getProcess().getProcessDefinition().getPropertyAliasOrThrow(aPropertyAliasType, aName, aPropName);
   }

   /**
    * Return true if <code>IAeMutableConfig</code> settings for engine
    * allow empty query selection.
    */
   protected boolean isSelectionFailureDisabled(AeAbstractBpelObject aBpelObj)
   {
      return aBpelObj.getProcess().isDisableSelectionFailure();
   }
   

   /**
    * @return the functionName
    */
   protected String getFunctionName()
   {
      return mFunctionName;
   }

   /**
    * @param aFunctionName the functionName to set
    */
   protected void setFunctionName(String aFunctionName)
   {
      mFunctionName = aFunctionName;
   }
   
   /**
    * Validates that an argument is a String
    * @param aArgs
    * @param aArgIndex
    * @return
    * @throws AeFunctionCallException 
    */
   public  String getStringArg(List aArgs, int aArgIndex) throws AeFunctionCallException
   {
      Object argObj = aArgs.get(aArgIndex);
      if (!(argObj instanceof String))
      {
         Object[] args = { getFunctionName(), new Integer(aArgIndex+1)};
         throwFunctionException(EXPECT_STRING_ARGUMENT, args);
      }
      return (String)aArgs.get(aArgIndex);
   }
   
  /**
   * Validates that an argument is a positive integer or the equivalent.
   * @param aArgs
   * @param aArgIndex
   * @return
   * @throws AeFunctionCallException
   */
   public int getPositiveIntArg(List aArgs, int aArgIndex) throws AeFunctionCallException
   {
      Object argObj = aArgs.get(aArgIndex);
     
      int number = AeUtil.parseInt(argObj.toString(),-1);
      if (number < 0)
      {
         Object[] args = { getFunctionName(), new Integer(aArgIndex+1)};
         throwFunctionException(EXPECT_POSITIVE_INT_ARGUMENT, args);
      }
      return number;
   }
}
