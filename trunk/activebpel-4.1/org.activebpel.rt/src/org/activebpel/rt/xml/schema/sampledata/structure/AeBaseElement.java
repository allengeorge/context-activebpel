//$Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/xml/schema/sampledata/structure/AeBaseElement.java,v 1.2 2007/02/20 21:57:1
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

import javax.xml.namespace.QName;

import org.activebpel.rt.xml.schema.sampledata.IAeSampleDataVisitor;

/**
 * Base class for element models (e.g. complex, simple, abstract.)
 */
public abstract class AeBaseElement extends AeStructure
{
   /** The name of this element. Namespace will be empty if the name is unqualified. */
   private QName mName;

   /** Nillable indicator. */
   private boolean mNillable;

   /**
    * Called to accept the sample data type visitor.  All implementations should simply call
    * <code>aVisitor.visit(this)</code>.
    * 
    * @param aVisitor
    */
   public abstract void accept(IAeSampleDataVisitor aVisitor);

   /**
    * Gets the name of this element.
    * 
    * @return QName.
    */
   public QName getName()
   {
      return mName;
   }

   /**
    * Sets the name of this element.
    * 
    * @param aName.
    */
   public void setName(QName aName)
   {
      mName = aName;
   }
   
   /**
    * @return boolean.
    */
   public boolean isNillable()
   {
      return mNillable;
   }

   /**
    * @param aNillable
    */
   public void setNillable(boolean aNillable)
   {
      mNillable = aNillable;
   }

   /**
    * @return boolean
    */
   public boolean isAbstractElement()
   {
      return false;
   }
   
   /**
    * @see org.activebpel.rt.xml.schema.sampledata.structure.AeStructure#getType()
    */
   public int getType()
   {
      return AeStructure.ELEMENT_TYPE;
   }
}
 
