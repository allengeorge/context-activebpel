// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/AeCorrelationSetDef.java,v 1.12 2006/07/06 14:54:5
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.def.visitors.IAeDefVisitor;

/**
 * Definition for bpel correlation set.
 */
public class AeCorrelationSetDef extends AeNamedDef
{
   /** The properties of the correlation set. */
   private Collection mProperties;
   /** Flag for whether this correlationSet has multiple points of initiation */
   private boolean mJoinStyle;

   /**
    * Default constructor
    */
   public AeCorrelationSetDef()
   {
      super();
   }

   /**
    * Provides a list of correlation set properties for the user to iterate.
    * @return Iterator of property of object.
    */
   public Iterator getPropertiesList()
   {
      if (mProperties == null)
         return Collections.EMPTY_LIST.iterator();
      else
         return mProperties.iterator();
   }
   
   /**
    * Adds the given property to the list property list.
    * @param aProperty property to be added
    */
   public void addProperty(QName aProperty)
   {
      if (mProperties == null)
         mProperties = new ArrayList();
         
      mProperties.add(aProperty);
   }
   
   /**
    * Setter for the properties
    * @param aCollection
    */
   public void setProperties(Collection aCollection)
   {
      mProperties = new ArrayList(aCollection);
   }

   /**
    * Getter for the properties
    */
   public Collection getProperties()
   {
      return mProperties;
   }
   
   /**
    * Returns true if the correlationSet is a "join" style which means that it has multiple initiation points
    */
   public boolean isJoinStyle()
   {
      return mJoinStyle;
   }
   
   /**
    * Sets the join style flag
    * @param aFlag
    */
   public void setJoinStyle(boolean aFlag)
   {
      mJoinStyle = aFlag;
   }

   /**
    * @param aVisitor 
    * @see org.activebpel.rt.bpel.def.AeBaseDef#accept(org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void accept(IAeDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }
}
