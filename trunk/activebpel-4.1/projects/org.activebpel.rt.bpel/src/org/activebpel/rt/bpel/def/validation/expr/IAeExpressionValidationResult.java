//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/expr/IAeExpressionValidationResult.java,v 1.3 2007/06/22 17:48:1
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

import java.util.List;

import org.activebpel.rt.bpel.def.expr.IAeExpressionParseResult;

/**
 * An instance of this interface is returned by expression validators.  This result
 * includes a list of error messages, if any, as well as a list of <code>AeVariableData</code>
 * and <code>AeVariableProperty</code> objects used by the expression.
 */
public interface IAeExpressionValidationResult
{
   /**
    * Returns list of information (INFO) messages found during parsing. This method should never return
    * null.  In cases where there are no errors, an empty list should be returned.
    */
   public List getInfoList();
   
   /**
    * Returns the list of errors found during parsing.  This method should never return
    * null.  In cases where there are no errors, an empty list should be returned.
    */
   public List getErrors();

   /**
    * Returns the list of warnings found during parsing.This method should never return
    * null.  In cases where there are no warnings, an empty list should be returned.
    */
   public List getWarnings();

   /**
    * Gets the result from parsing the expression.
    */
   public IAeExpressionParseResult getParseResult();
}
