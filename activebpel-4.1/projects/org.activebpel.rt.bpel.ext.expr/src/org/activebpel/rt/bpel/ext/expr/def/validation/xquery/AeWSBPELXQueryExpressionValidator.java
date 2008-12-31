// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.ext.expr/src/org/activebpel/rt/bpel/ext/expr/def/validation/xquery/AeWSBPELXQueryExpressionValidator.java,v 1.4 2007/06/29 22:28:5
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
package org.activebpel.rt.bpel.ext.expr.def.validation.xquery;

import org.activebpel.rt.bpel.def.expr.IAeExpressionParser;
import org.activebpel.rt.bpel.def.expr.IAeExpressionParserContext;
import org.activebpel.rt.bpel.ext.expr.def.xquery.AeWSBPELXQueryExpressionParser;

/**
 * A WS-BPEL version of an XQuery expression validator.
 */
public class AeWSBPELXQueryExpressionValidator extends AeAbstractXQueryExpressionValidator
{
   /**
    * Default c'tor.
    */
   public AeWSBPELXQueryExpressionValidator()
   {
      super();
   }

   /**
    * @see org.activebpel.rt.bpel.ext.expr.def.validation.xquery.AeAbstractXQueryExpressionValidator#createExpressionParser(org.activebpel.rt.bpel.def.expr.IAeExpressionParserContext)
    */
   protected IAeExpressionParser createExpressionParser(IAeExpressionParserContext aContext)
   {
      return new AeWSBPELXQueryExpressionParser(aContext);
   }
}
