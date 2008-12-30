//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/io/readers/def/AeCommonSpecStrategyMatcher.java,v 1.7 2006/09/20 17:01:4
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

import java.util.HashMap;
import java.util.Map;

import org.activebpel.rt.bpel.IAeExpressionLanguageFactory;
import org.activebpel.rt.bpel.def.AeVariableDef;
import org.activebpel.rt.bpel.def.activity.support.AeFromDef;
import org.activebpel.rt.bpel.def.activity.support.AeToDef;
import org.activebpel.rt.bpel.def.visitors.preprocess.strategies.AeBaseSpec;
import org.activebpel.rt.util.AeUtil;

/**
 * Creates specs or prototype objects for the commmon <from> and <to> variants
 * and maps them to the strategy names. Subclasses are available that add additional
 * variants for the particular version of bpel.
 */
public class AeCommonSpecStrategyMatcher implements IAeCopyOperationStrategyMatcher
{
   /** maps the spec object to the strategy name */
   private Map mFromStrategyMap = new HashMap();
   /** maps the spec object to the strategy name */
   private Map mToStrategyMap = new HashMap();

   /**
    * No arg ctor inits the maps
    */
   public AeCommonSpecStrategyMatcher()
   {
      initFromMap();
      initToMap();
   }

   /**
    * inits the from map with all of the common specs
    */
   protected void initFromMap()
   {
      // simple or complex type variable
      AeFromSpec fromSpec = new AeFromSpec();
      fromSpec.set(AeFromSpec.VARIABLE_TYPE);
      add(fromSpec, IAeFromStrategyKeys.KEY_FROM_VARIABLE_TYPE);

      // variable message part
      fromSpec = new AeFromSpec();
      fromSpec.set(AeFromSpec.VARIABLE_MESSAGE);
      fromSpec.set(AeFromSpec.PART);
      add(fromSpec, IAeFromStrategyKeys.KEY_FROM_VARIABLE_MESSAGE_PART);

      // whole message variable
      fromSpec = new AeFromSpec();
      fromSpec.set(AeFromSpec.VARIABLE_MESSAGE);
      add(fromSpec, IAeFromStrategyKeys.KEY_FROM_VARIABLE_MESSAGE);

      // variable element
      fromSpec = new AeFromSpec();
      fromSpec.set(AeFromSpec.VARIABLE_ELEMENT);
      add(fromSpec, IAeFromStrategyKeys.KEY_FROM_VARIABLE_ELEMENT);

      // message variable w/ property
      fromSpec = new AeFromSpec();
      fromSpec.set(AeFromSpec.VARIABLE_MESSAGE);
      fromSpec.set(AeFromSpec.PROPERTY);
      add(fromSpec, IAeFromStrategyKeys.KEY_FROM_PROPERTY_MESSAGE);

      // partnerlink with endpointreference for myRole or partnerRole
      fromSpec = new AeFromSpec();
      fromSpec.set(AeFromSpec.PARTNERLINK);
      fromSpec.set(AeFromSpec.ENDPOINTREFERENCE);
      add(fromSpec, IAeFromStrategyKeys.KEY_FROM_PARTNER_LINK);

      // literal
      fromSpec = new AeFromSpec();
      fromSpec.set(AeFromSpec.LITERAL);
      add(fromSpec, IAeFromStrategyKeys.KEY_FROM_LITERAL);

      // expression
      fromSpec = new AeFromSpec();
      fromSpec.set(AeFromSpec.EXPRESSION);
      add(fromSpec, IAeFromStrategyKeys.KEY_FROM_EXPRESSION);

      // variable / message / part / query
      fromSpec = new AeFromSpec();
      fromSpec.set(AeFromSpec.VARIABLE_MESSAGE);
      fromSpec.set(AeFromSpec.PART);
      fromSpec.set(AeFromSpec.QUERY);
      add(fromSpec, IAeFromStrategyKeys.KEY_FROM_VARIABLE_MESSAGE_PART_QUERY);

      // variable / element / query
      fromSpec = new AeFromSpec();
      fromSpec.set(AeFromSpec.VARIABLE_ELEMENT);
      fromSpec.set(AeFromSpec.QUERY);
      add(fromSpec, IAeFromStrategyKeys.KEY_FROM_VARIABLE_ELEMENT_QUERY);
   }

   /**
    * inits the <to> map with the common specs
    */
   protected void initToMap()
   {
      // simple or complex type variable
      AeToSpec toSpec = new AeToSpec();
      toSpec.set(AeToSpec.VARIABLE_TYPE);
      add(toSpec, IAeToStrategyKeys.KEY_TO_VARIABLE_TYPE);

      // message part
      toSpec = new AeToSpec();
      toSpec.set(AeToSpec.VARIABLE_MESSAGE);
      toSpec.set(AeToSpec.PART);
      add(toSpec, IAeToStrategyKeys.KEY_TO_VARIABLE_MESSAGE_PART);

      // whole message variable
      toSpec = new AeToSpec();
      toSpec.set(AeToSpec.VARIABLE_MESSAGE);
      add(toSpec, IAeToStrategyKeys.KEY_TO_VARIABLE_MESSAGE);

      // element variable
      toSpec = new AeToSpec();
      toSpec.set(AeToSpec.VARIABLE_ELEMENT);
      add(toSpec, IAeToStrategyKeys.KEY_TO_VARIABLE_ELEMENT);

      // message with property
      toSpec = new AeToSpec();
      toSpec.set(AeToSpec.VARIABLE_MESSAGE);
      toSpec.set(AeToSpec.PROPERTY);
      add(toSpec, IAeToStrategyKeys.KEY_TO_PROPERTY_MESSAGE);

      // partner link with partner role
      toSpec = new AeToSpec();
      toSpec.set(AeToSpec.PARTNERLINK);
      add(toSpec, IAeToStrategyKeys.KEY_TO_PARTNER_LINK);

      // variable / message / part / query
      toSpec = new AeToSpec();
      toSpec.set(AeToSpec.VARIABLE_MESSAGE);
      toSpec.set(AeToSpec.PART);
      toSpec.set(AeToSpec.QUERY);
      add(toSpec, IAeToStrategyKeys.KEY_TO_VARIABLE_MESSAGE_PART_QUERY);

      // variable / element / query
      toSpec = new AeToSpec();
      toSpec.set(AeToSpec.VARIABLE_ELEMENT);
      toSpec.set(AeToSpec.QUERY);
      add(toSpec, IAeToStrategyKeys.KEY_TO_VARIABLE_ELEMENT_QUERY);
   }

   /**
    * Adds a mapping for the from spec to the map
    * @param aFromSpec
    * @param aStrategySpecKey
    */
   protected void add(AeFromSpec aFromSpec, AeSpecStrategyKey aStrategySpecKey)
   {
      mFromStrategyMap.put(aFromSpec, aStrategySpecKey);
   }

   /**
    * Adds a mapping for the to spec to the map
    * @param aToSpec
    * @param aStrategySpecKey
    */
   protected void add(AeToSpec aToSpec, AeSpecStrategyKey aStrategySpecKey)
   {
      mToStrategyMap.put(aToSpec, aStrategySpecKey);
   }

   /**
    * @see org.activebpel.rt.bpel.def.io.readers.def.IAeCopyOperationStrategyMatcher#getStrategy(org.activebpel.rt.bpel.def.activity.support.AeFromDef, org.activebpel.rt.bpel.def.AeVariableDef)
    */
   public AeSpecStrategyKey getStrategy(AeFromDef aFromDef, AeVariableDef aVarDef)
   {
      AeFromSpec fromSpec = AeFromSpec.createSpec(aFromDef, aVarDef);
      return (AeSpecStrategyKey) mFromStrategyMap.get(fromSpec);
   }

   /**
    * @see org.activebpel.rt.bpel.def.io.readers.def.IAeCopyOperationStrategyMatcher#getStrategy(org.activebpel.rt.bpel.def.activity.support.AeToDef, org.activebpel.rt.bpel.def.AeVariableDef, org.activebpel.rt.bpel.IAeExpressionLanguageFactory)
    */
   public AeSpecStrategyKey getStrategy(AeToDef aToDef, AeVariableDef aVarDef, IAeExpressionLanguageFactory aExpressionLanguageFactory)
   {
      AeToSpec toSpec = AeToSpec.createSpec(aToDef, aVarDef);
      return (AeSpecStrategyKey) mToStrategyMap.get(toSpec);
   }

   /**
    * Base class for the specs, provides base impl of a spec matcher along with
    * constants and a BitSet to record which values are enabled in the from or
    * to def.
    */
   protected static class AeBaseVarSpec extends AeBaseSpec
   {
      /** bit for a variable of type message */
      protected static final int VARIABLE_MESSAGE = 1;
      /** bit for a variable of type element */
      protected static final int VARIABLE_ELEMENT = 2;
      /** bit for a variable of simple or complex type */
      protected static final int VARIABLE_TYPE = 3;

      /** bit for a query attribute or query style <to> */
      protected static final int QUERY = 4;
      /** bit for a part attribute */
      protected static final int PART = 5;
      /** bit for a property attribute */
      protected static final int PROPERTY = 6;
      /** bit for a partner link attribute */
      protected static final int PARTNERLINK = 7;
      /** bit for an expression style <from> */
      protected static final int EXPRESSION = 8;
      /** bit for a literal style <from> */
      protected static final int LITERAL = 9;
      /** bit for an endpointreference attribute on a <from> */
      protected static final int ENDPOINTREFERENCE = 10;

      /**
       * Sets the appropriate bit for the given variable def, if not null
       * @param aVarDef
       */
      protected void set(AeVariableDef aVarDef)
      {
         if (aVarDef != null)
         {
            if (aVarDef.isMessageType())
               set(VARIABLE_MESSAGE);
            else if (aVarDef.isElement())
               set(VARIABLE_ELEMENT);
            else
               set(VARIABLE_TYPE);
         }
      }
   }

   /**
    * Extension to the base spec that adds specs for the AeFromDef;
    */
   protected static class AeFromSpec extends AeBaseVarSpec
   {
      /**
       * Creates a spec for the given AeFromDef
       * @param aDef
       * @param aVarDef
       */
      public static AeFromSpec createSpec(AeFromDef aDef, AeVariableDef aVarDef)
      {
         AeFromSpec fromSpec = new AeFromSpec();

         if (AeUtil.notNullOrEmpty(aDef.getExpression()) || AeUtil.notNullOrEmpty(aDef.getExpressionLanguage()))
            fromSpec.set(EXPRESSION);

         if (aDef.getLiteralDef() != null)
            fromSpec.set(LITERAL);

         if(AeUtil.notNullOrEmpty(aDef.getPartnerLink()))
            fromSpec.set(PARTNERLINK);

         if(AeUtil.notNullOrEmpty(aDef.getEndpointReference()))
            fromSpec.set(ENDPOINTREFERENCE);

         if(aDef.getQueryDef() != null)
            fromSpec.set(QUERY);

         if(AeUtil.notNullOrEmpty(aDef.getPart()))
            fromSpec.set(PART);

         if(aDef.getProperty() != null)
            fromSpec.set(PROPERTY);

         fromSpec.set(aVarDef);

         return fromSpec;
      }
   }

   /**
    * Creates a spec for the given <to> spec
    */
   protected static class AeToSpec extends AeBaseVarSpec
   {
      /**
       * Converts a def to a spec
       * @param aDef
       * @param aVarDef
       */
      public static AeToSpec createSpec(AeToDef aDef, AeVariableDef aVarDef)
      {
         AeToSpec toSpec = new AeToSpec();

         if (aDef.getQueryDef() != null)
            toSpec.set(QUERY);

         if(AeUtil.notNullOrEmpty(aDef.getPart()))
            toSpec.set(PART);

         if(aDef.getProperty() != null)
            toSpec.set(PROPERTY);

         if(AeUtil.notNullOrEmpty(aDef.getPartnerLink()))
            toSpec.set(PARTNERLINK);

         if(AeUtil.notNullOrEmpty(aDef.getExpression()) || AeUtil.notNullOrEmpty(aDef.getExpressionLanguage()))
            toSpec.set(EXPRESSION);

         toSpec.set(aVarDef);

         return toSpec;
      }
   }
}
