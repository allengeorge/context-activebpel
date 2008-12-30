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
package org.activebpel.rt.bpel.def;

import org.activebpel.rt.bpel.def.visitors.IAeDefVisitor;

/**
 * A simple class that holds the information about an extension attribute (namespace, qualified
 * name, value).
 */
public class AeExtensionAttributeDef extends AeBaseDef
{
   /** The attribute's namespace. */
   private String mNamespace;
   /** The attribute's qualified name. */
   private String mQualifiedName;
   /** The attribute value. */
   private String mValue;

   /**
    * Constructor.
    * 
    * @param aNamespace
    * @param aQualifiedName
    * @param aValue
    */
   public AeExtensionAttributeDef(String aNamespace, String aQualifiedName, String aValue)
   {
      mNamespace = aNamespace;
      mQualifiedName = aQualifiedName;
      mValue = aValue;
   }

   /**
    * @return Returns the namespace.
    */
   public String getNamespace()
   {
      return mNamespace;
   }

   /**
    * @return Returns the qualifiedName.
    */
   public String getQualifiedName()
   {
      return mQualifiedName;
   }

   /**
    * @return Returns the value.
    */
   public String getValue()
   {
      return mValue;
   }

   /**
    * @see org.activebpel.rt.bpel.def.AeBaseDef#accept(org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void accept(IAeDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }
}
