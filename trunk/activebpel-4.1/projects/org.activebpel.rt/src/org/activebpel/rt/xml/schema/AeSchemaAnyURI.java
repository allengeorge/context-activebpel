//$Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/xml/schema/AeSchemaAnyURI.java,v 1.2 2006/09/07 14:41:1
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
package org.activebpel.rt.xml.schema; 

/**
 * Wrapper object for xsd:anyURI simple data type. This ensures that Axis serializes the data correctly 
 */
public class AeSchemaAnyURI extends AeSchemaWrappedStringType
{
   /**
    * Ctor accepts the uri
    * @param aURI
    */
   public AeSchemaAnyURI(String aURI)
   {
      super(aURI);
   }
   
   /**
    * Gets the AnyURI value.
    */
   public String getURI()
   {
      return getValue();
   }
   
   /**
    * @see org.activebpel.rt.xml.schema.IAeSchemaType#accept(org.activebpel.rt.xml.schema.IAeSchemaTypeVisitor)
    */
   public void accept(IAeSchemaTypeVisitor aVisitor)
   {
      aVisitor.visit(this);
   }
}
 
