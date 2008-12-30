// $Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/xml/schema/sampledata/castor/AeCastorSampleDataGenerator.java,v 1.1 2007/06/08 03:32:2
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
package org.activebpel.rt.xml.schema.sampledata.castor;

import org.activebpel.rt.xml.schema.sampledata.AeSampleDataVisitor;
import org.activebpel.rt.xml.schema.sampledata.IAeSampleDataPreferences;
import org.activebpel.rt.xml.schema.sampledata.structure.AeStructure;
import org.exolab.castor.xml.schema.ComplexType;
import org.exolab.castor.xml.schema.ElementDecl;
import org.w3c.dom.Document;

/**
 * A utility class to make generating XML from schemas more
 * manageable.
 */
public class AeCastorSampleDataGenerator
{
   /**
    * Generates XML from a Complex Type.  Uses the given data generation
    * preferences.
    * 
    * @param aElementDecl
    * @param aPreferences
    */
   public static Document generateXML(ElementDecl aElementDecl, IAeSampleDataPreferences aPreferences)
   {
      AeSampleDataVisitor visitor = new AeSampleDataVisitor(aPreferences);
      AeStructure root = AeCastorToAeStructure.build(aElementDecl, aPreferences);
      root.accept(visitor);
      return visitor.getDocument();
   }
   
   /**
    * Generates XML from a Complex Type.  Uses the given data generation
    * preferences.
    * 
    * @param aComplexType
    * @param aPreferences
    */
   public static Document generateXML(ComplexType aComplexType, IAeSampleDataPreferences aPreferences)
   {
      AeSampleDataVisitor visitor = new AeSampleDataVisitor(aPreferences);
      AeStructure root = AeCastorToAeStructure.build(aComplexType, aPreferences);
      root.accept(visitor);
      return visitor.getDocument();
   }
}
