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
package org.activebpel.rt.bpel.def.expr.xpath;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.activebpel.rt.bpel.AeMessages;

/**
 * A class that represents a parsed variable reference (from a xpath 1.0 variable reference).
 */
public class AeXPathVariableReference
{
   /** Regexp to match the variable reference. */
   private static Pattern VARIABLE_REFERENCE_PATTERN = Pattern.compile("(.+?)(\\.(.*))?"); //$NON-NLS-1$

   /** The variable reference's variable name. */
   private String mVariableName;
   /** The variable reference's part name. */
   private String mPartName;

   /**
    * Construct the variable reference given the unparsed xpath variable name.
    * 
    * @param aXPathVariableName
    */
   public AeXPathVariableReference(String aXPathVariableName)
   {
      parseVariableReference(aXPathVariableName);
   }
   
   /**
    * Parses the variable 'name' and returns a xpath variable reference which will contain the
    * component parts of the variable name.  This would include the BPEL variable name and optional
    * message/WSDL part name.
    * 
    * @param aVariableName
    */
   protected void parseVariableReference(String aVariableName)
   {
      Matcher matcher = VARIABLE_REFERENCE_PATTERN.matcher(aVariableName);
      // The variable ref pattern should really always match.
      if (!matcher.matches())
         throw new RuntimeException(AeMessages.format("AeXPathVariableReference.ERROR_PARSING_VAR_REFERENCE", aVariableName)); //$NON-NLS-1$
      
      setVariableName(matcher.group(1));
      setPartName(matcher.group(3));
   }

   /**
    * @return Returns the variableName.
    */
   public String getVariableName()
   {
      return mVariableName;
   }

   /**
    * @param aVariableName The variableName to set.
    */
   protected void setVariableName(String aVariableName)
   {
      mVariableName = aVariableName;
   }

   /**
    * @return Returns the partName.
    */
   public String getPartName()
   {
      return mPartName;
   }

   /**
    * @param aPartName The partName to set.
    */
   protected void setPartName(String aPartName)
   {
      mPartName = aPartName;
   }

   /**
    * Returns true if this reference includes a part name.
    */
   public boolean hasPartName()
   {
      return getPartName() != null;
   }
}
