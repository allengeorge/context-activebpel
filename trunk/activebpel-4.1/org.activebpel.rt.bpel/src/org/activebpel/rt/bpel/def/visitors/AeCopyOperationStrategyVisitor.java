//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/visitors/AeCopyOperationStrategyVisitor.java,v 1.2 2006/09/20 17:01:4
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
package org.activebpel.rt.bpel.def.visitors; 

import org.activebpel.rt.bpel.IAeExpressionLanguageFactory;
import org.activebpel.rt.bpel.def.AeVariableDef;
import org.activebpel.rt.bpel.def.activity.support.AeFromDef;
import org.activebpel.rt.bpel.def.activity.support.AeToDef;
import org.activebpel.rt.bpel.def.activity.support.AeVarDef;
import org.activebpel.rt.bpel.def.io.readers.def.IAeCopyOperationStrategyMatcher;
import org.activebpel.rt.bpel.def.io.readers.def.IAeFromStrategyNames;
import org.activebpel.rt.bpel.def.io.readers.def.IAeToStrategyNames;
import org.activebpel.rt.bpel.def.util.AeDefUtil;
import org.activebpel.rt.util.AeUtil;

/**
 * Visits the process def and adds the strategy hint to the copy operation defs:
 * <from> and <to>.
 */
public class AeCopyOperationStrategyVisitor extends AeAbstractDefVisitor implements IAeFromStrategyNames, IAeToStrategyNames
{
   /** used to match the def to a prescribed strategy */
   private IAeCopyOperationStrategyMatcher mStrategyMatcher;
   /** The expression language factory to use. */
   private IAeExpressionLanguageFactory mExpressionLanguageFactory;
   
   /**
    * Constructor.
    * 
    * @param aMatcher
    * @param aExpressionLanguageFactory
    */
   public AeCopyOperationStrategyVisitor(IAeCopyOperationStrategyMatcher aMatcher,
         IAeExpressionLanguageFactory aExpressionLanguageFactory)
   {
      mStrategyMatcher = aMatcher;
      setExpressionLanguageFactory(aExpressionLanguageFactory);
      setTraversalVisitor( new AeTraversalVisitor(new AeDefTraverser(), this));
   }

   /**
    * Creates the appropriate impl object to model the <from>
    * 
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeFromDef)
    */
   public void visit(AeFromDef aDef)
   {
      AeVariableDef variableDef = findVariable(aDef);
      aDef.setStrategyKey(mStrategyMatcher.getStrategy(aDef, variableDef));
      super.visit(aDef);
   }
   
   /**
    * Finds the variable in scope.
    * 
    * @param aVarDef
    */
   protected AeVariableDef findVariable(AeVarDef aVarDef)
   {
      AeVariableDef def = null;
      
      // walk the parents and inspect each scope def to see if it declares this variable
      
      if (AeUtil.notNullOrEmpty(aVarDef.getVariable()))
      {
         def = AeDefUtil.getVariableByName(aVarDef.getVariable(), aVarDef);
      }
      
      return def;
   }
   
   /**
    * Creates the appropriate impl object to model the <to>
    * 
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.support.AeToDef)
    */
   public void visit(AeToDef aDef)
   {
      AeVariableDef variableDef = findVariable(aDef);
      aDef.setStrategyKey(mStrategyMatcher.getStrategy(aDef, variableDef, getExpressionLanguageFactory()));
      super.visit(aDef);
   }

   /**
    * @return Returns the expressionLanguageFactory.
    */
   protected IAeExpressionLanguageFactory getExpressionLanguageFactory()
   {
      return mExpressionLanguageFactory;
   }

   /**
    * @param aExpressionLanguageFactory The expressionLanguageFactory to set.
    */
   protected void setExpressionLanguageFactory(IAeExpressionLanguageFactory aExpressionLanguageFactory)
   {
      mExpressionLanguageFactory = aExpressionLanguageFactory;
   }
}
