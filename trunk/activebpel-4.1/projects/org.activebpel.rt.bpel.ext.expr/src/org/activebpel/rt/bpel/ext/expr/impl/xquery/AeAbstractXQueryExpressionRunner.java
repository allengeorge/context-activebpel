// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.ext.expr/src/org/activebpel/rt/bpel/ext/expr/impl/xquery/AeAbstractXQueryExpressionRunner.java,v 1.1 2006/09/15 14:53:3
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

import java.util.Properties;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;

import net.sf.saxon.Configuration;
import net.sf.saxon.om.DocumentInfo;
import net.sf.saxon.om.SequenceIterator;
import net.sf.saxon.query.DynamicQueryContext;
import net.sf.saxon.query.QueryResult;
import net.sf.saxon.query.StaticQueryContext;
import net.sf.saxon.query.XQueryExpression;
import net.sf.saxon.trans.XPathException;
import net.sf.saxon.variables.VariableResolver;

import org.activebpel.rt.bpel.ext.expr.def.xquery.AeXQueryNamespaceResolver;
import org.activebpel.rt.bpel.function.IAeFunctionExecutionContext;
import org.activebpel.rt.bpel.impl.AeBpelException;
import org.activebpel.rt.bpel.impl.expr.AeAbstractExpressionRunner;
import org.activebpel.rt.bpel.impl.expr.AeExprFunctionExecutionContext;
import org.activebpel.rt.bpel.impl.expr.AeExpressionException;
import org.activebpel.rt.bpel.impl.expr.IAeExpressionRunnerContext;
import org.activebpel.rt.bpel.impl.expr.IAeExpressionTypeConverter;
import org.activebpel.rt.util.AeXmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * A base class for implementations of an XQuery expression runner.
 */
public abstract class AeAbstractXQueryExpressionRunner extends AeAbstractExpressionRunner
{
   /**
    * Default constructor.
    */
   public AeAbstractXQueryExpressionRunner()
   {
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.expr.AeAbstractExpressionRunner#createExpressionTypeConverter(org.activebpel.rt.bpel.impl.expr.IAeExpressionRunnerContext)
    */
   protected IAeExpressionTypeConverter createExpressionTypeConverter(IAeExpressionRunnerContext aContext)
   {
      return new AeXQueryExpressionTypeConverter();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.expr.AeAbstractExpressionRunner#doExecuteExpression(java.lang.String, org.activebpel.rt.bpel.impl.expr.IAeExpressionRunnerContext)
    */
   protected Object doExecuteExpression(String aExpression, IAeExpressionRunnerContext aContext)
         throws AeBpelException
   {
      try
      {
         IAeExpressionTypeConverter typeConverter = createExpressionTypeConverter(aContext);
         IAeFunctionExecutionContext funcExecContext = new AeExprFunctionExecutionContext(aContext, typeConverter);

         Configuration config = new Configuration();
         config.setExtensionBinder(new AeXQueryFunctionLibrary(funcExecContext));
         StaticQueryContext staticContext = new StaticQueryContext(config);
         VariableResolver varResolver = createVariableResolver(funcExecContext);
         staticContext.setVariableResolver(varResolver);
         staticContext.setExternalNamespaceResolver(new AeXQueryNamespaceResolver(aContext.getNamespaceContext()));
         XQueryExpression exp = staticContext.compileQuery(aExpression);

         return executeXQueryExpression(aContext, config, staticContext, exp);
      }
      catch (AeExpressionException ee)
      {
         throw ee.getWrappedException();
      }
      catch (Throwable t)
      {
         throwSubLangExecutionFault(aExpression, t, aContext);
         return null; // Will never get here - the above call will always throw.
      }
   }

   /**
    * @see org.activebpel.rt.bpel.impl.expr.AeAbstractExpressionRunner#doExecuteJoinConditionExpression(java.lang.String, org.activebpel.rt.bpel.impl.expr.IAeExpressionRunnerContext)
    */
   protected Object doExecuteJoinConditionExpression(String aExpression, IAeExpressionRunnerContext aContext) throws AeBpelException
   {
      try
      {
         IAeExpressionTypeConverter typeConverter = createExpressionTypeConverter(aContext);
         IAeFunctionExecutionContext funcExecContext = new AeExprFunctionExecutionContext(aContext, typeConverter);

         Configuration config = new Configuration();
         config.setExtensionBinder(new AeXQueryFunctionLibrary(funcExecContext));
         StaticQueryContext staticContext = new StaticQueryContext(config);
         VariableResolver varResolver = createLinkVariableResolver(funcExecContext);
         staticContext.setVariableResolver(varResolver);
         staticContext.setExternalNamespaceResolver(new AeXQueryNamespaceResolver(aContext.getNamespaceContext()));
         XQueryExpression exp = staticContext.compileQuery(aExpression);

         return executeXQueryExpression(aContext, config, staticContext, exp);
      }
      catch (AeExpressionException ee)
      {
         throw ee.getWrappedException();
      }
      catch (Throwable t)
      {
         throwSubLangExecutionFault(aExpression, t, aContext);
         return null; // Will never get here - the above call will always throw.
      }
   }

   /**
    * Executes a compiled XQuery.  This method is used to share code between doExecuteExpression and
    * doExecuteJoinConditionExpression.
    * 
    * @param aContext
    * @param aConfig
    * @param aStaticContext
    * @param aXQueryExpression
    * @throws XPathException
    */
   protected Object executeXQueryExpression(IAeExpressionRunnerContext aContext, Configuration aConfig,
         StaticQueryContext aStaticContext, XQueryExpression aXQueryExpression) throws XPathException
   {
      DynamicQueryContext dynamicContext = new DynamicQueryContext(aConfig);
      // For expressions, the context item is always null, but go ahead and add it anyway, since 
      // it is on the interface.
      if (aContext.getEvaluationContext() instanceof Node)
      {
         Node context = (Node) aContext.getEvaluationContext();
         dynamicContext.setContextItem(aStaticContext.buildDocument(new DOMSource(context)));
      }
      // Note: the call to iterator effectively evaluates the expression and returns a Sequence of
      // items.
      SequenceIterator resultsIter = aXQueryExpression.iterator(dynamicContext);
      DocumentInfo docInfo = QueryResult.wrap(resultsIter, aConfig);
      Properties props = new Properties();
      props.setProperty(OutputKeys.METHOD, "xml"); //$NON-NLS-1$
      props.setProperty(OutputKeys.INDENT, "yes"); //$NON-NLS-1$
      // Create a DOM Document/Element to store the result
      Document doc = AeXmlUtil.newDocument();
      DOMResult result = new DOMResult(doc);
      QueryResult.serialize(docInfo, result, props, aConfig);
      
      return new AeXQueryExpressionResult((Document) result.getNode());
   }

   /**
    * Creates the variable resolver to use for executing expressions.  Note that this implementation
    * returns null because BPEL 1.1 does not allow binding BPEL variables using the XQuery $varName
    * syntax.
    * 
    * @param aFunctionExecContext
    */
   protected abstract VariableResolver createVariableResolver(IAeFunctionExecutionContext aFunctionExecContext);

   /**
    * Creates the variable resolver to use for executing joing condition expressions.  Note that 
    * this implementation returns null because BPEL 1.1 does not allow binding BPEL links using 
    * the XQuery $linkName syntax.
    * 
    * @param aFunctionExecContext
    */
   protected abstract VariableResolver createLinkVariableResolver(IAeFunctionExecutionContext aFunctionExecContext);
}
