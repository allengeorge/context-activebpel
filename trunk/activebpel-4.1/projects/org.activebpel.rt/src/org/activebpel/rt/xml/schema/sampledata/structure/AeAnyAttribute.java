//$Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/xml/schema/sampledata/structure/AeAnyAttribute.java,v 1.2 2007/02/20 21:57:1
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
package org.activebpel.rt.xml.schema.sampledata.structure; 

import org.activebpel.rt.xml.schema.sampledata.IAeSampleDataVisitor;

/**
 *  Model class for the Schema wildcard attribute (anyAttribute).
 */
public class AeAnyAttribute extends AeBaseAttribute
{
   /** The replacement namespace. Will be null for unqalified namespace, i.e. ##local. */
   private String mNamespace;
   
   /**
    * Constructor.
    */
   public AeAnyAttribute()
   {
   }

   /**
    * @see org.activebpel.rt.xml.schema.sampledata.structure.AeStructure#accept(org.activebpel.rt.xml.schema.sampledata.IAeSampleDataVisitor)
    */
   public void accept(IAeSampleDataVisitor aVisitor)
   {
      aVisitor.visit(this);
   }

   /**
    * Gets the replacement attribute namespace.
    * 
    * @return String
    */
   protected String getNamespace()
   {
      return mNamespace;
   }

   /**
    * Sets the replacement attribute namepace.
    * 
    * @param namespace
    */
   public void setNamespace(String namespace)
   {
      mNamespace = namespace;
   }

   /**
    * @see org.activebpel.rt.xml.schema.sampledata.structure.AeStructure#getType()
    */
   public int getType()
   {
      return AeStructure.ANY_ATTRIBUTE_TYPE;
   }
   
}
 
