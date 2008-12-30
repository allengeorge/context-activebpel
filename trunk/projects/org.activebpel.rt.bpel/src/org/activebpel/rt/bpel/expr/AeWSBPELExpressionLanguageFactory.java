//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/expr/AeWSBPELExpressionLanguageFactory.java,v 1.2 2007/06/10 19:07:5
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
package org.activebpel.rt.bpel.expr;

import java.util.HashMap;
import java.util.Map;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.def.IAeBPELConstants;
import org.activebpel.rt.bpel.def.expr.xpath.AeWSBPELXPathExpressionAnalyzer;
import org.activebpel.rt.bpel.def.validation.expr.xpath.AeWSBPELXPathExpressionValidator;
import org.activebpel.rt.bpel.impl.expr.xpath.AeWSBPELXPathExpressionRunner;

/**
 * This implementation of the expression language factory uses the engine configuration file to
 * map expression languages to implementations of validators and runners.
 */
public class AeWSBPELExpressionLanguageFactory extends AeAbstractBpelExpressionLanguageFactory
{
   /** The map/list of default languages when no config info is found in aeEngineConfig.xml. */
   private static Map sDefaultLanguages = new HashMap();

   /**
    * Initializes above languages map.
    */
   static 
   {
      try
      {
         Map map = new HashMap();
         map.put(AeAbstractBpelExpressionLanguageFactory.LANGUAGE_URI_KEY, IAeBPELConstants.WSBPEL_EXPR_LANGUAGE_URI);
         map.put(AeAbstractBpelExpressionLanguageFactory.LANGUAGE_VALIDATOR_KEY, AeWSBPELXPathExpressionValidator.class.getName());
         map.put(AeAbstractBpelExpressionLanguageFactory.LANGUAGE_RUNNER_KEY, AeWSBPELXPathExpressionRunner.class.getName());
         map.put(AeAbstractBpelExpressionLanguageFactory.LANGUAGE_ANALYZER_KEY, AeWSBPELXPathExpressionAnalyzer.class.getName());
         sDefaultLanguages.put("XPath1.0", map); //$NON-NLS-1$
      }
      catch (Throwable t)
      {
         AeException.logError(t, t.getLocalizedMessage());
      }
   }
   

   /**
    * Constructs an expression language factory using the given engine configuration map.
    * 
    * @param aConfig
    * @param aClassloader
    */
   public AeWSBPELExpressionLanguageFactory(Map aConfig, ClassLoader aClassloader)
   {
      super(aConfig, aClassloader);
   }

   /**
    * Constructs a default expression language factory that supports only XPath 1.0.
    *
    */
   public AeWSBPELExpressionLanguageFactory()
   {
      super();
   }
   
   /**
    * @see org.activebpel.rt.bpel.expr.IAeBpelExpressionLanguageFactory#getBpelDefaultLanguage()
    */
   public String getBpelDefaultLanguage()
   {
      return IAeBPELConstants.WSBPEL_EXPR_LANGUAGE_URI;
   }
   
   /**
    * @see org.activebpel.rt.bpel.expr.AeAbstractBpelExpressionLanguageFactory#getDefaultLanguages()
    */
   protected Map getDefaultLanguages()
   {
      return sDefaultLanguages;
   }
}
