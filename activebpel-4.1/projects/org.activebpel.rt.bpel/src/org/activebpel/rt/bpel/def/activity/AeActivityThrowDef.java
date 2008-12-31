// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/activity/AeActivityThrowDef.java,v 1.9 2006/06/26 16:50:3
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
package org.activebpel.rt.bpel.def.activity;

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.def.AeActivityDef;
import org.activebpel.rt.bpel.def.visitors.IAeDefVisitor;

/**
 * Definition for bpel throw activity.
 */
public class AeActivityThrowDef extends AeActivityDef
{
   /** fault name for the throw */
   private QName mFaultName;
   /** name of variable for throw */
   private String mFaultVariable;

   // standard element names of the activity definition
   public static final String TAG_THROW = "throw"; //$NON-NLS-1$

   /**
    * Default constructor
    */
   public AeActivityThrowDef()
   {
   }

   /**
    * Accessor method to obtain the name of the fault for the throw activity.
    * 
    * @return name of fault
    */
   public QName getFaultName()
   {
      return mFaultName;
   }

   /**
    * Mutator method to set the fault name.
    * 
    * @param aFaultName name of fault to be set
    */
   public void setFaultName(QName aFaultName)
   {
      mFaultName = aFaultName;
   }

   /**
    * Accessor method to obtain the name of the fault variable for the throw activity.
    * 
    * @return name of fault variable
    */
   public String getFaultVariable()
   {
      return mFaultVariable;
   }

   /**
    * Mutator method to set the fault variable name.
    * 
    * @param aFaultVariable name of fault variable to be set
    */ 
   public void setFaultVariable(String aFaultVariable)
   {
      mFaultVariable = aFaultVariable;
   }

   /**
    * @see org.activebpel.rt.bpel.def.AeActivityDef#accept(org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void accept(IAeDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }
}
