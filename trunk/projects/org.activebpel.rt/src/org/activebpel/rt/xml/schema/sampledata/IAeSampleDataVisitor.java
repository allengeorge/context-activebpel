//$Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/xml/schema/sampledata/IAeSampleDataVisitor.java,v 1.3 2007/08/10 02:13:0
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
package org.activebpel.rt.xml.schema.sampledata; 

import org.activebpel.rt.xml.schema.sampledata.structure.AeAbstractElement;
import org.activebpel.rt.xml.schema.sampledata.structure.AeAbstractType;
import org.activebpel.rt.xml.schema.sampledata.structure.AeAll;
import org.activebpel.rt.xml.schema.sampledata.structure.AeAny;
import org.activebpel.rt.xml.schema.sampledata.structure.AeAnyAttribute;
import org.activebpel.rt.xml.schema.sampledata.structure.AeAnyTypeElement;
import org.activebpel.rt.xml.schema.sampledata.structure.AeAttribute;
import org.activebpel.rt.xml.schema.sampledata.structure.AeChoice;
import org.activebpel.rt.xml.schema.sampledata.structure.AeComplexElement;
import org.activebpel.rt.xml.schema.sampledata.structure.AeGroup;
import org.activebpel.rt.xml.schema.sampledata.structure.AeSequence;
import org.activebpel.rt.xml.schema.sampledata.structure.AeSimpleElement;

/**
 * Interface for the schema to XML sample document generation visitor.
 */
public interface IAeSampleDataVisitor
{
   
   /**
    * @param aAll
    */
   public void visit(AeAll aAll);
   
   /**
    * @param aAny
    */
   public void visit(AeAny aAny);
   
   /**
    * @param aAttribute
    */
   public void visit(AeAttribute aAttribute);
   
   /**
    * @param aChoice
    */
   public void visit(AeChoice aChoice);
   
   /**
    * @param aComplexElement
    */
   public void visit(AeComplexElement aComplexElement);
   
   /**
    * @param aSimpleElement
    */
   public void visit(AeSimpleElement aSimpleElement);
   
   /**
    * @param aAnyTypeElement
    */
   public void visit(AeAnyTypeElement aAnyTypeElement);
   
   /**
    * @param aGroup
    */
   public void visit(AeGroup aGroup);
   
   /**
    * @param aSequence
    */
   public void visit(AeSequence aSequence);
   
   /**
    * @param aAnyAttribuite
    */
   public void visit(AeAnyAttribute aAnyAttribuite);
   
   /**
    * @param aAbstractElement
    */
   public void visit(AeAbstractElement aAbstractElement);

   /**
    * @param aType
    */
   public void visit(AeAbstractType aType);
}
 
