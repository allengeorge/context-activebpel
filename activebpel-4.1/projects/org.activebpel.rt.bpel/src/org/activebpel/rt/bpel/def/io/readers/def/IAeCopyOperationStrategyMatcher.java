//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/io/readers/def/IAeCopyOperationStrategyMatcher.java,v 1.4 2006/09/20 17:01:4
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

import org.activebpel.rt.bpel.IAeExpressionLanguageFactory;
import org.activebpel.rt.bpel.def.AeVariableDef;
import org.activebpel.rt.bpel.def.activity.support.AeFromDef;
import org.activebpel.rt.bpel.def.activity.support.AeToDef;

/**
 * implements the matching logic for determining the strategy to use for a <from> or <to> 
 * def in a copy operation 
 */
public interface IAeCopyOperationStrategyMatcher
{
   /**
    * Gets the strategy to use for the from def
    * @param aFromDef
    * @param aVarDef optional since some defs don't reference variables directly (i.e. plink)
    */
   public AeSpecStrategyKey getStrategy(AeFromDef aFromDef, AeVariableDef aVarDef);

   /**
    * Gets the strategy to use for the to def
    * @param aToDef
    * @param aVarDef optional since some defs don't reference variables directly (i.e. plink or query)
    * @param aExpressionLanguageFactory
    */
   public AeSpecStrategyKey getStrategy(AeToDef aToDef, AeVariableDef aVarDef,
         IAeExpressionLanguageFactory aExpressionLanguageFactory);
}
 
