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

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.def.expr.IAeExpressionAnalyzer;
import org.activebpel.rt.bpel.def.validation.expr.IAeExpressionValidator;
import org.activebpel.rt.bpel.impl.expr.IAeExpressionRunner;

/**
 * Interface for getting expression validators, runners, analyzers, etc for expressions written
 * in various languages.  This interface is implemented by BPEL-version specific implementations.
 */
public interface IAeBpelExpressionLanguageFactory
{
   /**
    * Given a language identifier found in the 'expressionLanguage' attribute of the BPEL 'process'
    * element (or in a child BPEL constructs in 2.0), returns an expression validator capable of 
    * validating expressions written in that language.
    * 
    * @param aLanguageUri
    * @throws AeException
    */
   public IAeExpressionValidator createExpressionValidator(String aLanguageUri) throws AeException;

   /**
    * Given a language identifier found in the 'expressionLanguage' attribute of the BPEL 'process'
    * element (or in a child BPEL constructs in 2.0), returns an expression runner capable of 
    * executing expressions written in that language.
    * 
    * @param aLanguageUri
    * @throws AeException
    */
   public IAeExpressionRunner createExpressionRunner(String aLanguageUri) throws AeException;

   /**
    * Given a language identifier found in the 'expressionLanguage' attribute of the BPEL 'process'
    * element (or in a child BPEL constructs in 2.0), returns an expression analyzer capable of 
    * parsing and analyzing expressions written in that language.
    * 
    * @param aLanguageUri
    * @throws AeException
    */
   public IAeExpressionAnalyzer createExpressionAnalyzer(String aLanguageUri) throws AeException;

   /**
    * Returns true if the given language is supported by this expression language factory.  If
    * null or empty string is passed in, then this method should return true if the BPEL default
    * language is supported (xpath 1.0).
    * 
    * @param aLanguageUri
    */
   public boolean supportsLanguage(String aLanguageUri);
   
   /**
    * Returns true if the given language URI is the default language URI.
    * 
    * @param aLanguageUri
    */
   public boolean isBpelDefaultLanguage(String aLanguageUri);

   /**
    * Returns the default language URI.
    */
   public String getBpelDefaultLanguage();
}
