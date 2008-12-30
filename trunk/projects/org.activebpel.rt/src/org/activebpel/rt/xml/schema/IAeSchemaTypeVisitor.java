// $Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/xml/schema/IAeSchemaTypeVisitor.java,v 1.1 2006/09/07 14:41:1
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
 * Visitor interface for schema types.
 */
public interface IAeSchemaTypeVisitor
{
   /**
    * Visit the given schema type.
    * 
    * @param aSchemaType
    */
   public void visit(AeSchemaAnyURI aSchemaType);

   /**
    * Visit the given schema type.
    * 
    * @param aSchemaType
    */
   public void visit(AeSchemaDate aSchemaType);

   /**
    * Visit the given schema type.
    * 
    * @param aSchemaType
    */
   public void visit(AeSchemaDateTime aSchemaType);

   /**
    * Visit the given schema type.
    * 
    * @param aSchemaType
    */
   public void visit(AeSchemaDay aSchemaType);

   /**
    * Visit the given schema type.
    * 
    * @param aSchemaType
    */
   public void visit(AeSchemaDuration aSchemaType);

   /**
    * Visit the given schema type.
    * 
    * @param aSchemaType
    */
   public void visit(AeSchemaMonth aSchemaType);

   /**
    * Visit the given schema type.
    * 
    * @param aSchemaType
    */
   public void visit(AeSchemaMonthDay aSchemaType);

   /**
    * Visit the given schema type.
    * 
    * @param aSchemaType
    */
   public void visit(AeSchemaTime aSchemaType);

   /**
    * Visit the given schema type.
    * 
    * @param aSchemaType
    */
   public void visit(AeSchemaYear aSchemaType);

   /**
    * Visit the given schema type.
    * 
    * @param aSchemaType
    */
   public void visit(AeSchemaYearMonth aSchemaType);

   /**
    * Visit the given schema type.
    * 
    * @param aSchemaType
    */
   public void visit(AeSchemaHexBinary aSchemaType);

   /**
    * Visit the given schema type.
    * 
    * @param aSchemaType
    */
   public void visit(AeSchemaBase64Binary aSchemaType);
}
