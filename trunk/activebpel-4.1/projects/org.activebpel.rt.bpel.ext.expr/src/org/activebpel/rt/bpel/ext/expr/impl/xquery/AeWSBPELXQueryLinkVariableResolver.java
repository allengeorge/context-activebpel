// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.ext.expr/src/org/activebpel/rt/bpel/ext/expr/impl/xquery/AeWSBPELXQueryLinkVariableResolver.java,v 1.2 2006/09/15 14:53:3
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
package org.activebpel.rt.bpel.ext.expr.impl.xquery;

import net.sf.saxon.expr.Expression;
import net.sf.saxon.value.StringValue;

import org.activebpel.rt.bpel.def.IAeBPELConstants;
import org.activebpel.rt.bpel.function.AeUnresolvableException;
import org.activebpel.rt.bpel.function.IAeFunction;
import org.activebpel.rt.bpel.function.IAeFunctionExecutionContext;
import org.activebpel.rt.bpel.function.IAeFunctionFactory;
import org.activebpel.rt.bpel.impl.activity.support.AeLink;
import org.activebpel.rt.util.AeUtil;

/**
 * A WS-BPEL Xquery variable resolver.  This class resolves XQuery variables (of the form $bpelLinkName)
 * to XQuery expressions that, when executed, return the value of the named BPEL Link.
 */
public class AeWSBPELXQueryLinkVariableResolver extends AeWSBPELXQueryVariableResolver
{
   /**
    * Constructor.
    * 
    * @param aContext
    */
   public AeWSBPELXQueryLinkVariableResolver(IAeFunctionExecutionContext aContext)
   {
      super(aContext);
   }
   
   /**
    * @see org.activebpel.rt.bpel.ext.expr.impl.xquery.AeWSBPELXQueryVariableResolver#hasVariable(int, java.lang.String, java.lang.String)
    */
   public boolean hasVariable(int aNameCode, String aUri, String aLocal)
   {
      if (AeUtil.notNullOrEmpty(aUri))
      {
         return false;
      }
      
      AeLink link = getFunctionExecutionContext().getAbstractBpelObject().findTargetLink(aLocal);
      return link != null;
   }

   /**
    * Create the expression that will be executed for the variable reference.
    * 
    * @param aLinkName
    */
   protected Expression createVariableExpression(String aLinkName)
   {
      try
      {
         IAeFunctionFactory funcContext = getFunctionExecutionContext().getFunctionContext();
         IAeFunction function = funcContext.getFunction(IAeBPELConstants.BPWS_NAMESPACE_URI, "getLinkStatus"); //$NON-NLS-1$
         AeXQueryFunction func = new AeXQueryFunction(function, getFunctionExecutionContext());
         Expression [] args = new Expression[] { StringValue.makeStringValue(aLinkName) };
         func.setArguments(args);
         return func;
      }
      catch (AeUnresolvableException ex)
      {
         throw new RuntimeException("Error: could not find getVariableData()."); //$NON-NLS-1$
      }
   }
}
