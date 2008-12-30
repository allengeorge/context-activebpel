//$Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/xml/schema/sampledata/structure/AeStructure.java,v 1.2 2007/02/20 21:57:1
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

import java.util.ArrayList;
import java.util.List;

import org.activebpel.rt.xml.schema.sampledata.IAeSampleDataVisitor;


/**
 * Root abstract class for Schema models.
 */
public abstract class AeStructure
{
   public static final int ALL_TYPE             = 1;
   public static final int ANY_TYPE             = 2;
   public static final int ANY_ATTRIBUTE_TYPE   = 3;
   public static final int ATTRIBUTE_TYPE       = 4;
   public static final int CHOICE_TYPE          = 5;
   public static final int ELEMENT_TYPE         = 6;
   public static final int GROUP_TYPE           = 7;
   public static final int SEQUENCE_TYPE        = 8;
   
   /** The parent model of this object. */
   private AeStructure mParent;
   
   /** The children of this object. */
   private List mChildren = new ArrayList();
   
   /** Minimum number of time this structure can occur; Default is 1. */
   private int  mMinOccurs = 1;
   
   /** Max number of time this structure can occur; a value < 1 indicates unbounded. */
   private int mMaxOccurs = 1;
   
   /**
    * Called to accept the sample data type visitor.  All implementations should simply call
    * <code>aVisitor.visit(this)</code>.
    * 
    * @param aVisitor
    */
   public abstract void accept(IAeSampleDataVisitor aVisitor);

   /**
    * @return List
    */
   public List getChildren()
   {
      return mChildren;
   }
   
   /**
    * @param aChildren.
    */
   public void setChildren(List aChildren)
   {
      mChildren = aChildren;
   }
   
   /**
    * Addes the given structure to list of children.
    * 
    * @param aStructure
    */
   public void addChild(AeStructure aStructure)
   {
      getChildren().add(aStructure);
      aStructure.setParent(this);
   }
   
   /**
    * @return int.
    */
   public int getMaxOccurs()
   {
      return mMaxOccurs;
   }
   
   /**
    * @param aMaxOccurs.
    */
   public void setMaxOccurs(int aMaxOccurs)
   {
      mMaxOccurs = aMaxOccurs;
   }
   
   /**
    * @return int.
    */
   public int getMinOccurs()
   {
      return mMinOccurs;
   }
   
   /**
    * @param aMinOccurs
    */
   public void setMinOccurs(int aMinOccurs)
   {
      mMinOccurs = aMinOccurs;
   }

   /**
    * @return the mParent
    */
   public AeStructure getParent()
   {
      return mParent;
   }

   /**
    * @param parent the mParent to set
    */
   public void setParent(AeStructure parent)
   {
      mParent = parent;
   }

   /**
    * Gets the type for this structure
    */
   public abstract int getType();
}
 
