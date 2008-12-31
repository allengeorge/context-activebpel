//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/io/readers/def/AeWSBPELSpecStrategyMatcher.java,v 1.9 2006/09/25 21:15:3
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
package org.activebpel.rt.bpel.def.io.readers.def; 

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.IAeExpressionLanguageFactory;
import org.activebpel.rt.bpel.def.AeBaseDefNamespaceContext;
import org.activebpel.rt.bpel.def.AeVariableDef;
import org.activebpel.rt.bpel.def.IAeBPELConstants;
import org.activebpel.rt.bpel.def.activity.support.AeToDef;
import org.activebpel.rt.bpel.def.expr.AeExpressionAnalyzerContext;
import org.activebpel.rt.bpel.def.expr.IAeExpressionAnalyzer;
import org.activebpel.rt.bpel.def.expr.IAeExpressionAnalyzerContext;
import org.activebpel.rt.bpel.def.expr.IAeExpressionAnalyzer.AeExpressionToSpecDetails;
import org.activebpel.rt.bpel.def.util.AeDefUtil;

/**
 * Impl of the strategy matcher that adds additional patterns for WS-BPEL 
 */
public class AeWSBPELSpecStrategyMatcher extends AeCommonSpecStrategyMatcher
{
   /**
    * @see org.activebpel.rt.bpel.def.io.readers.def.AeCommonSpecStrategyMatcher#initFromMap()
    */
   protected void initFromMap()
   {
      super.initFromMap();

      // simple|complex variable / property
      AeFromSpec fromSpec = new AeFromSpec();
      fromSpec = new AeFromSpec();
      fromSpec.set(AeFromSpec.VARIABLE_TYPE);
      fromSpec.set(AeFromSpec.PROPERTY);
      add(fromSpec, IAeFromStrategyKeys.KEY_FROM_PROPERTY_TYPE);
      
      // element variable / property
      fromSpec = new AeFromSpec();
      fromSpec.set(AeFromSpec.VARIABLE_ELEMENT);
      fromSpec.set(AeFromSpec.PROPERTY);
      add(fromSpec, IAeFromStrategyKeys.KEY_FROM_PROPERTY_ELEMENT);

      // variable / simple|complex type / query
      fromSpec = new AeFromSpec();
      fromSpec.set(AeFromSpec.VARIABLE_TYPE);
      fromSpec.set(AeFromSpec.QUERY);
      add(fromSpec, IAeFromStrategyKeys.KEY_FROM_VARIABLE_TYPE_QUERY);
   }

   /**
    * @see org.activebpel.rt.bpel.def.io.readers.def.AeCommonSpecStrategyMatcher#initToMap()
    */
   protected void initToMap()
   {
      super.initToMap();

      // simple|complex variable / property
      AeToSpec toSpec = new AeToSpec();
      toSpec.set(AeToSpec.VARIABLE_TYPE);
      toSpec.set(AeToSpec.PROPERTY);
      add(toSpec, IAeToStrategyKeys.KEY_TO_PROPERTY_TYPE);
      
      // element variable / property
      toSpec = new AeToSpec();
      toSpec.set(AeToSpec.VARIABLE_ELEMENT);
      toSpec.set(AeToSpec.PROPERTY);
      add(toSpec, IAeToStrategyKeys.KEY_TO_PROPERTY_ELEMENT);

      // expression
      toSpec = new AeToSpec();
      toSpec.set(AeToSpec.EXPRESSION);
      add(toSpec, IAeToStrategyKeys.KEY_TO_EXPRESSION);

      // variable / simple|complex type / query
      toSpec = new AeToSpec();
      toSpec.set(AeToSpec.VARIABLE_TYPE);
      toSpec.set(AeToSpec.QUERY);
      add(toSpec, IAeToStrategyKeys.KEY_TO_VARIABLE_TYPE_QUERY);
   }

   /**
    * @see org.activebpel.rt.bpel.def.io.readers.def.AeCommonSpecStrategyMatcher#getStrategy(org.activebpel.rt.bpel.def.activity.support.AeToDef, org.activebpel.rt.bpel.def.AeVariableDef, org.activebpel.rt.bpel.IAeExpressionLanguageFactory)
    */
   public AeSpecStrategyKey getStrategy(AeToDef aToDef, AeVariableDef aVarDef, IAeExpressionLanguageFactory aExpressionLanguageFactory)
   {
      AeSpecStrategyKey key = super.getStrategy(aToDef, aVarDef, aExpressionLanguageFactory);
      if (key == IAeToStrategyKeys.KEY_TO_EXPRESSION)
      {
         key = getExpressionStrategy(aToDef, aExpressionLanguageFactory);
      }
      return key;
   }
   
   /**
    * Determines the proper strategy to use for an Expression to-spec.  This method will parse the expression
    * to determine its form.  The forms (in XPath) could be:
    * 
    * $varName  (simple type, element)  Note: $varName syntax is illegal if the variable is a message
    * $varName.partName  (message part)
    * $varName/query/to/data  (element with query)
    * $varName.partName/query/to/data  (message part with query)
    * 
    * @param aToDef
    * @param aExpressionLanguageFactory
    */
   protected AeSpecStrategyKey getExpressionStrategy(AeToDef aToDef, IAeExpressionLanguageFactory aExpressionLanguageFactory)
   {
      AeSpecStrategyKey strategy = null;
      try
      {
         String expressionLanguage = AeDefUtil.getExpressionLanguage(aToDef);
         IAeExpressionAnalyzer analyzer = aExpressionLanguageFactory.createExpressionAnalyzer(IAeBPELConstants.WSBPEL_2_0_NAMESPACE_URI, expressionLanguage);
         IAeExpressionAnalyzerContext analyzerContext = new AeExpressionAnalyzerContext(new AeBaseDefNamespaceContext(aToDef));
         AeExpressionToSpecDetails toSpecDetails = analyzer.parseExpressionToSpec(analyzerContext, aToDef.getExpression());
         if (toSpecDetails != null)
            strategy = getStrategyFromExprToSpecDetails(aToDef, toSpecDetails, aExpressionLanguageFactory);
      }
      catch (AeException ex)
      {
         AeException.logError(ex);
      }

      if (strategy == null)
      {
         // Default to simply using the ToExpression strategy.  It won't work, but it will suppress
         // the "invalid to-spec" validation error.  We want to suppress that error because
         // the to-spec format is valid, but the query being used isn't.  We will catch and
         // report problems with the query itself elsewhere during validation (and prevent
         // deployment).
         strategy = IAeToStrategyKeys.KEY_TO_EXPRESSION;
      }
      
      return strategy;
   }
   
   /**
    * Creates a AeSpecStrategyKey from the result of parsing the to-spec expression into its
    * component parts (variable name, part name, query).
    * 
    * @param aToDef
    * @param aToSpecDetails
    * @param aExpressionLanguageFactory
    */
   protected AeSpecStrategyKey getStrategyFromExprToSpecDetails(AeToDef aToDef,
         AeExpressionToSpecDetails aToSpecDetails, IAeExpressionLanguageFactory aExpressionLanguageFactory)
   {
      AeSpecStrategyKey key = null;
      
      String varName = aToSpecDetails.getVariableName();
      AeVariableDef varDef = (AeVariableDef) AeDefUtil.getVariableByName(varName, aToDef);
      String partName = aToSpecDetails.getPartName();
      String query = aToSpecDetails.getQuery();
      String queryLanguage = aToSpecDetails.getQueryLanguage();
      String strategyName = getStrategyName(varDef, partName, query);
      if (strategyName != null)
      {
         key = new AeExpressionSpecStrategyKey(strategyName, varName, partName, queryLanguage, query);
      }
      return key;
   }

   /**
    * Gets the proper strategy name given the variable, part name, and query.
    * 
    * @param aVariableDef
    * @param aPartName
    * @param aQuery
    */
   protected String getStrategyName(AeVariableDef aVariableDef, String aPartName, String aQuery)
   {
      String strategyName = null;
      if (aVariableDef == null)
      {
         strategyName = IAeToStrategyNames.TO_VARIABLE_MESSAGE;
         if (aPartName != null)
         {
            strategyName = IAeToStrategyNames.TO_VARIABLE_MESSAGE_PART;
            if (aQuery != null)
            {
               strategyName = IAeToStrategyNames.TO_VARIABLE_MESSAGE_PART_QUERY;
            }
         }
         else if (aQuery != null)
         {
            strategyName = IAeToStrategyNames.TO_VARIABLE_ELEMENT_QUERY;
         }
      }
      else if (aVariableDef.isMessageType())
      {
         // There MUST be a part name, or the format is invalid.
         strategyName = null;
         if (aPartName != null)
         {
            strategyName = IAeToStrategyNames.TO_VARIABLE_MESSAGE_PART;
            if (aQuery != null)
            {
               strategyName = IAeToStrategyNames.TO_VARIABLE_MESSAGE_PART_QUERY;
            }
         }
      }
      else if (aVariableDef.isElement())
      {
         strategyName = IAeToStrategyNames.TO_VARIABLE_ELEMENT;
         if (aQuery != null)
         {
            strategyName = IAeToStrategyNames.TO_VARIABLE_ELEMENT_QUERY;
         }
      }
      else if (aVariableDef.isType())
      {
         strategyName = IAeToStrategyNames.TO_VARIABLE_TYPE;
         if (aQuery != null)
         {
            strategyName = IAeToStrategyNames.TO_VARIABLE_TYPE_QUERY;
         }
      }
      return strategyName;
   }
}
