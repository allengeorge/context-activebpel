// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.ext.expr/src/org/activebpel/rt/bpel/ext/expr/def/validation/javascript/AeWSBPELJavaScriptExpressionValidator.java,v 1.2 2006/09/18 20:08:5
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
package org.activebpel.rt.bpel.ext.expr.def.validation.javascript;

import java.util.HashSet;
import java.util.Set;

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.def.IAeBPELConstants;
import org.activebpel.rt.bpel.def.expr.AeExpressionLanguageUtil;
import org.activebpel.rt.bpel.def.expr.IAeExpressionParser;
import org.activebpel.rt.bpel.def.expr.IAeExpressionParserContext;
import org.activebpel.rt.bpel.ext.expr.def.javascript.AeWSBPELJavaScriptExpressionParser;

/**
 * A BPEL 2.0 implementation of a JavaScript expression validator.
 */
public class AeWSBPELJavaScriptExpressionValidator extends AeAbstractJavaScriptExpressionValidator
{
   /** The Set of allowed join condition functions for WSBPEL Javascript expressions. */
   private static Set sAllowedJoinConditionFunctions;

   /**
    * Default c'tor.
    */
   public AeWSBPELJavaScriptExpressionValidator()
   {
      super();
   }
   
   /**
    * @see org.activebpel.rt.bpel.ext.expr.def.validation.javascript.AeAbstractJavaScriptExpressionValidator#createExpressionParser(org.activebpel.rt.bpel.def.expr.IAeExpressionParserContext)
    */
   protected IAeExpressionParser createExpressionParser(IAeExpressionParserContext aContext)
   {
      return new AeWSBPELJavaScriptExpressionParser(aContext);
   }

   /**
    * Override this in order to supply the bpel.getLinkStatus() function as a valid function
    * for JavaScript join conditions.
    * 
    * @see org.activebpel.rt.bpel.def.validation.expr.AeAbstractExpressionValidator#getJoinConditionAllowedFunctions()
    */
   protected Set getJoinConditionAllowedFunctions()
   {
      if (sAllowedJoinConditionFunctions == null)
      {
         Set set = new HashSet(super.getJoinConditionAllowedFunctions());
         set.add(new QName(IAeBPELConstants.WSBPEL_2_0_NAMESPACE_URI, AeExpressionLanguageUtil.LINK_STATUS_FUNC_NAME));
         sAllowedJoinConditionFunctions = set;
      }

      return sAllowedJoinConditionFunctions;
   }
}
