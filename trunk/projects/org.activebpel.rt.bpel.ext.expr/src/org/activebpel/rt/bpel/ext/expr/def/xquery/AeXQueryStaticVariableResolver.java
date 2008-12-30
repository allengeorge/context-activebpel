// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.ext.expr/src/org/activebpel/rt/bpel/ext/expr/def/xquery/AeXQueryStaticVariableResolver.java,v 1.1 2006/09/07 15:11:4
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
package org.activebpel.rt.bpel.ext.expr.def.xquery;

import net.sf.saxon.expr.VariableDeclaration;
import net.sf.saxon.query.GlobalVariableDefinition;
import net.sf.saxon.value.SequenceType;
import net.sf.saxon.variables.VariableResolver;

/**
 * An implementation of an XQuery variable resolver.  This implementation resolves all variables to 
 * simple 'no-op' variables that are used only for analysis (not execution).
 */
public class AeXQueryStaticVariableResolver implements VariableResolver
{
   /**
    * Default c'tor.
    */
   public AeXQueryStaticVariableResolver()
   {
   }

   /**
    * @see net.sf.saxon.variables.VariableResolver#hasVariable(int, java.lang.String, java.lang.String)
    */
   public boolean hasVariable(int aNameCode, String aUri, String aLocal)
   {
      return true;
   }

   /**
    * @see net.sf.saxon.variables.VariableResolver#resolve(int, java.lang.String, java.lang.String)
    */
   public VariableDeclaration resolve(int aNameCode, String aUri, String aLocal)
   {
      return createGlobalVariableDef(aNameCode, aUri, aLocal);
   }

   /**
    * Creates a global variable definition for the given namecode + local name.
    * 
    * @param aNameCode
    * @param aVariableName
    */
   private GlobalVariableDefinition createGlobalVariableDef(int aNameCode, String aUri, String aVariableName)
   {
      GlobalVariableDefinition globalVarDef = new GlobalVariableDefinition();
      globalVarDef.setNameCode(aNameCode);
      globalVarDef.setIsParameter(true);
      globalVarDef.setVariableName(aVariableName);
      globalVarDef.setRequiredType(SequenceType.SINGLE_ITEM);

      globalVarDef.setValueExpression(new AeNoOpVariable(aUri, aVariableName));

      return globalVarDef;
   }
}
