//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/expr/AeExpressionValidationResult.java,v 1.4 2007/06/22 17:48:1
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
package org.activebpel.rt.bpel.def.validation.expr;

import java.util.LinkedList;
import java.util.List;

import org.activebpel.rt.bpel.def.expr.IAeExpressionParseResult;

/**
 * Implements a simple expression validation result object. This implementation of the
 * <code>IAeExpressionValidationResult</code> interface is basically just a container for the various lists
 * required by the interface.
 */
public class AeExpressionValidationResult implements IAeExpressionValidationResult
{
   /** List of info messages. */
   private List mInfoList;
   /** The list of errors. */
   private List mErrors;
   /** The list of warnings. */
   private List mWarnings;
   /** Results of the parsing, will be null if the expression wasn't parsed */
   private IAeExpressionParseResult mParseResult;

   /**
    * Default constructor.
    */
   public AeExpressionValidationResult()
   {
      setInfoList(new LinkedList());
      setErrors(new LinkedList());
      setWarnings(new LinkedList());
   }
   
   /**
    * @return Returns the infoList.
    */
   public List getInfoList()
   {
      return mInfoList;
   }

   /**
    * @param aInfoList The infoList to set.
    */
   public void setInfoList(List aInfoList)
   {
      mInfoList = aInfoList;
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.expr.IAeExpressionValidationResult#getErrors()
    */
   public List getErrors()
   {
      return mErrors;
   }

   /**
    * Setter for the errors.
    * @param aErrors
    */
   protected void setErrors(List aErrors)
   {
      mErrors = aErrors;
   }

   /**
    * Adds an inof to the info list.
    * @param aInfo
    */
   public void addInfo(String aInfo)
   {
      getInfoList().add(aInfo);
   }

   /**
    * Adds a list of info messages to the internal info list.
    * @param aInfoList
    */
   public void addInfo(List aInfoList)
   {
      getInfoList().addAll(aInfoList);
   }   
   
   /**
    * Adds an error to the list of errors.
    * @param aError
    */
   public void addError(String aError)
   {
      getErrors().add(aError);
   }

   /**
    * Adds a list of errors to the internal error list.
    * @param aErrors
    */
   public void addErrors(List aErrors)
   {
      getErrors().addAll(aErrors);
   }

   /**
    * Adds a warning to the list of errors.
    * @param aWarning
    */
   public void addWarning(String aWarning)
   {
      getWarnings().add(aWarning);
   }

   /**
    * Gets the list of warnings.
    */
   public List getWarnings()
   {
      return mWarnings;
   }

   /**
    * Sets the list of warnings.
    * 
    * @param aWarnings
    */
   protected void setWarnings(List aWarnings)
   {
      mWarnings = aWarnings;
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.expr.IAeExpressionValidationResult#getParseResult()
    */
   public IAeExpressionParseResult getParseResult()
   {
      return mParseResult;
   }
   
   /**
    * Setter for the parse result
    * @param aResult
    */
   public void setParseResult(IAeExpressionParseResult aResult)
   {
      mParseResult = aResult;
   }

}
