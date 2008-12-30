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
package org.activebpel.rt.bpel.def;

/**
 * Interface for all defs that can be expressions.
 */
public interface IAeExpressionDef
{
   /**
    * Gets the expression language configured for this def.  This may be null or empty, in which
    * case the process level expressionLanguage setting should be used.
    */
   public String getExpressionLanguage();
   
   /**
    * Sets the expression language URI on the def.
    * 
    * @param aLanguageURI
    */
   public void setExpressionLanguage(String aLanguageURI);

   /**
    * Gets the expression.
    */
   public String getExpression();
   
   /**
    * Sets the expression.
    * 
    * @param aExpression
    */
   public void setExpression(String aExpression);
   
   /**
    * Gets the BPEL namespace of the process that this expression is nested within.
    */
   public String getBpelNamespace();
}
