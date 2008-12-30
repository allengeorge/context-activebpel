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
 * A def implementation of the ws-bpel 2.0 &lt;documentation&gt; construct.
 */
public class AeDocumentationDef extends AeBaseDef
{
   /** The source uri attribute if specified. */
   private String mSource;
   /** The language attribute if specified. */
   private String mLanguage;
   /** The value of the documentation element. */
   private String mValue;

   /**
    * Default c'tor.
    */
   public AeDocumentationDef()
   {
      super();
   }

   /**
    * @see org.activebpel.rt.bpel.def.AeBaseDef#accept(org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void accept(IAeDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }

   /**
    * @return Returns the value.
    */
   public String getValue()
   {
      return mValue;
   }

   /**
    * @param aValue The value to set.
    */
   public void setValue(String aValue)
   {
      mValue = aValue;
   }

   /**
    * @return Returns the language.
    */
   public String getLanguage()
   {
      return mLanguage;
   }

   /**
    * @param aLanguage The language to set.
    */
   public void setLanguage(String aLanguage)
   {
      mLanguage = aLanguage;
   }

   /**
    * @return Returns the source.
    */
   public String getSource()
   {
      return mSource;
   }

   /**
    * @param aSource The source to set.
    */
   public void setSource(String aSource)
   {
      mSource = aSource;
   }
}
