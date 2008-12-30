// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/visitors/AeDoXslTransformUriVisitor.java,v 1.1 2006/10/12 20:15:2
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

import java.util.HashSet;
import java.util.Set;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.IAeExpressionLanguageFactory;
import org.activebpel.rt.bpel.def.AeBaseDef;
import org.activebpel.rt.bpel.def.AeBaseDefNamespaceContext;
import org.activebpel.rt.bpel.def.IAeExpressionDef;
import org.activebpel.rt.bpel.def.expr.AeExpressionAnalyzerContext;
import org.activebpel.rt.bpel.def.expr.IAeExpressionAnalyzer;
import org.activebpel.rt.bpel.def.util.AeDefUtil;

/**
 * Visits the process def and builds a list of doXslTransform() resources.
 */
public class AeDoXslTransformUriVisitor extends AeAbstractExpressionDefVisitor
{
   /** The expression language factory. */
   private IAeExpressionLanguageFactory mFactory;
   /** The set of stylesheets found during visiting. */
   private Set mStylesheets;
   
   /**
    * Default c'tor.
    */
   public AeDoXslTransformUriVisitor(IAeExpressionLanguageFactory aFactory)
   {
      super();
      setFactory(aFactory);
      setStylesheets(new HashSet());
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractExpressionDefVisitor#visitExpressionDef(org.activebpel.rt.bpel.def.IAeExpressionDef)
    */
   protected void visitExpressionDef(IAeExpressionDef aExpressionDef)
   {
      try
      {
         String exprLanguage = AeDefUtil.getExpressionLanguage(aExpressionDef);
         String bpelNS = getProcessDef().getNamespace();
         IAeExpressionAnalyzer analyzer = getFactory().createExpressionAnalyzer(bpelNS, exprLanguage);
         AeExpressionAnalyzerContext context = new AeExpressionAnalyzerContext(new AeBaseDefNamespaceContext(
               (AeBaseDef) aExpressionDef));
         Set stylesheets = analyzer.getStylesheetURIs(context, aExpressionDef.getExpression());
         getStylesheets().addAll(stylesheets);
      }
      catch (AeException ex)
      {
         AeException.logError(ex);
      }
   }

   /**
    * @return Returns the factory.
    */
   protected IAeExpressionLanguageFactory getFactory()
   {
      return mFactory;
   }

   /**
    * @param aFactory The factory to set.
    */
   protected void setFactory(IAeExpressionLanguageFactory aFactory)
   {
      mFactory = aFactory;
   }

   /**
    * @return Returns the stylesheets.
    */
   public Set getStylesheets()
   {
      return mStylesheets;
   }

   /**
    * @param aStylesheets The stylesheets to set.
    */
   protected void setStylesheets(Set aStylesheets)
   {
      mStylesheets = aStylesheets;
   }
}
