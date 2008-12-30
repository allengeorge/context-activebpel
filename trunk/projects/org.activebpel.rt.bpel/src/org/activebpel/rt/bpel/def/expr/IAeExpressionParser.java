//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/expr/IAeExpressionParser.java,v 1.2 2006/06/26 16:50:4
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
package org.activebpel.rt.bpel.def.expr;

import org.activebpel.rt.AeException;


/**
 * This interface must be implemented by expression parsers.  There will be an implementation of this
 * interface for each expression language supported.
 */
public interface IAeExpressionParser
{
   /**
    * This method is called to parse a given expression into its parse result object.
    * 
    * @param aExpression
    * @throws AeException
    */
   public IAeExpressionParseResult parse(String aExpression) throws AeException;
}
