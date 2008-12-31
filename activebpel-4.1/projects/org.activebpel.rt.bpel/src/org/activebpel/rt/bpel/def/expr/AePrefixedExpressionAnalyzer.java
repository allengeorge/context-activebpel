//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/expr/AePrefixedExpressionAnalyzer.java,v 1.3 2006/08/15 18:32:5
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
package org.activebpel.rt.bpel.def.expr;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.activebpel.rt.util.AeXmlUtil;

/**
 * This is a based class for the xpath and xquery expression analyzers.
 */
public abstract class AePrefixedExpressionAnalyzer extends AeAbstractExpressionAnalyzer
{
   /** The pattern that <code>getNamespaces</code> will use to find prefixes. */
   protected static Pattern sGetNamespacesPattern = Pattern.compile("(" + AeXmlUtil.NCNAME_PATTERN + "):"); //$NON-NLS-1$ //$NON-NLS-2$
   
   /**
    * @see org.activebpel.rt.bpel.def.expr.IAeExpressionAnalyzer#getNamespaces(org.activebpel.rt.bpel.def.expr.IAeExpressionAnalyzerContext, java.lang.String)
    */
   public Set getNamespaces(IAeExpressionAnalyzerContext aContext, String aExpression)
   {
      Set set = new LinkedHashSet();

      Pattern p = sGetNamespacesPattern;
      Matcher m = p.matcher(aExpression);
      while (m.find())
      {
         String prefix = m.group(1);
         String ns = aContext.getNamespaceContext().resolvePrefixToNamespace(prefix);
         if (ns != null)
            set.add(ns);
      }

      return set;
   }

   /**
    * @see org.activebpel.rt.bpel.def.expr.IAeExpressionAnalyzer#renameNamespacePrefix(org.activebpel.rt.bpel.def.expr.IAeExpressionAnalyzerContext, java.lang.String, java.lang.String, java.lang.String)
    */
   public String renameNamespacePrefix(IAeExpressionAnalyzerContext aContext, String aExpression, String aOldPrefix,
         String aNewPrefix)
   {
      // First do ', ", and / cases
      String pattern = "(['\"/\\s\\(<])" + aOldPrefix + ":"; //$NON-NLS-1$ //$NON-NLS-2$
      String replacement = "$1" + aNewPrefix + ":"; //$NON-NLS-1$ //$NON-NLS-2$
      String rval = aExpression.replaceAll(pattern, replacement);
      // Then do /@ case
      pattern = "([/@])" + aOldPrefix + ":"; //$NON-NLS-1$ //$NON-NLS-2$
      rval = rval.replaceAll(pattern, replacement);
      // Now the special case where it's at the beginning of the line
      if (rval.startsWith(aOldPrefix))
      {
         char ch = rval.charAt(aOldPrefix.length());
         if (ch == ':' || ch == '.')
         {
            rval = aNewPrefix + rval.substring(aOldPrefix.length());
         }
      }
      if (aExpression.equals(rval))
      {
         return null;
      }
      else
      {
         return rval;
      }
   }
}
