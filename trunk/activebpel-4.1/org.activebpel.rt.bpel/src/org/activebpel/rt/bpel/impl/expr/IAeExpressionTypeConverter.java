//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/expr/IAeExpressionTypeConverter.java,v 1.2 2006/07/10 16:32:4
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
package org.activebpel.rt.bpel.impl.expr;


/**
 * This interface provides type conversion functionality.  Each expression language implementation 
 * may need to convert data of certain Java types into other Java types.  For example, the Jaxen XPath
 * implementation needs to convert BigInteger and Float objects to Doubles.
 */
public interface IAeExpressionTypeConverter
{
   /**
    * Converts the given engine type (an object coming from the BPEL engine) to a type that
    * can be properly handled by the expression language library.
    * 
    * @param aEngineType
    */
   public Object convertToExpressionType(Object aEngineType);
   
   /**
    * Converts the given expression type (an object created by the expression language library
    * while executing the expression) to a new type that can be used by the BPEL engine.
    * 
    * @param aExpressionType
    */
   public Object convertToEngineType(Object aExpressionType);
}
