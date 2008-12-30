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

import javax.xml.namespace.QName;

/**
 * This class represents a variable reference found in an expression.
 */
public class AeScriptVarDef
{
   /** The variable's namespace. */
   private String mNamespace;
   /** The variable name. */
   private String mName;
   /** Any relative query path expression that might be associated with this variable. */
   private String mQuery;

   /**
    * Constructs a variable from the variable QName.
    * 
    * @param aNamespace
    * @param aName
    * @param aQuery
    */
   public AeScriptVarDef(String aNamespace, String aName, String aQuery)
   {
      setNamespace(aNamespace);
      setName(aName);
      setQuery(aQuery);
   }

   /**
    * Constructs a variable def from a QName and query.
    * 
    * @param aQName
    * @param aQuery
    */
   public AeScriptVarDef(QName aQName, String aQuery)
   {
      this(aQName.getNamespaceURI(), aQName.getLocalPart(), aQuery);
   }

   /**
    * @return Returns the name.
    */
   public String getName()
   {
      return mName;
   }

   /**
    * @param aName The name to set.
    */
   protected void setName(String aName)
   {
      mName = aName;
   }

   /**
    * @return Returns the namespace.
    */
   public String getNamespace()
   {
      return mNamespace;
   }

   /**
    * @param aNamespace The namespace to set.
    */
   protected void setNamespace(String aNamespace)
   {
      mNamespace = aNamespace;
   }

   /**
    * Gets the function's fully qualified name.
    */
   public QName getQName()
   {
      return new QName(getNamespace(), getName());
   }

   /**
    * @return Returns the query.
    */
   public String getQuery()
   {
      return mQuery;
   }

   /**
    * @param aQuery The query to set.
    */
   protected void setQuery(String aQuery)
   {
      mQuery = aQuery;
   }
}
