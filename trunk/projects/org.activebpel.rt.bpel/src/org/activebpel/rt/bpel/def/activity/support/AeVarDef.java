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
package org.activebpel.rt.bpel.def.activity.support;

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.def.AeBaseDef;
import org.activebpel.rt.bpel.def.IAeExpressionDef;
import org.activebpel.rt.bpel.def.io.readers.def.AeSpecStrategyKey;

/**
 * Assign activity support class, variables for copying information. This is an
 * abstract class so we can subclass for the explicit <code>from</code>
 * and <code>to</code> copy operations.
 */
abstract public class AeVarDef extends AeBaseDef implements IAeQueryParentDef, IAeExpressionDef
{
   /** The 'variable' attribute. */
   private String mVariable;
   /** The 'part' attribute. */
   private String mPart;
   /** The 'property' attribute. */
   private QName mProperty;
   /** The 'partnerLink' attribute. */
   private String mPartnerLink;
   /** The value of the expressionLanguage attribute (if any). */
   private String mExpressionLanguage;
   /** The expression (if any). */
   private String mExpression;
   /** The query def. */
   private AeQueryDef mQueryDef;
   
   /** The strategy to use to model this element */
   private AeSpecStrategyKey mStrategyKey;

   /**
    * Default constructor
    */
   public AeVarDef()
   {
      super();
   }

   /**
    * Return the variable value for the operation, null if none.
    */
   public String getVariable()
   {
      return mVariable;
   }

   /**
    * Set the variable value for the operation.
    */
   public void setVariable(String aVariable)
   {
      mVariable = aVariable;
   }

   /**
    * Accessor method to obtain the property for the object.
    * 
    * @return name of the property for the object
    */
   public QName getProperty()
   {
      return mProperty;
   }

   /**
    * Mutator method to set the property for the object.
    * 
    * @param aProperty the property value to be set
    */
   public void setProperty(QName aProperty)
   {
      mProperty = aProperty;
   }

   /**
    * Accessor method to obtain the part name for the object.
    * 
    * @return name of the part name for the object
    */
   public String getPart()
   {
      return mPart;
   }

   /**
    * Mutator method to set the part for the object.
    * 
    * @param aPart the part value to be set
    */
   public void setPart(String aPart)
   {
      mPart = aPart;
   }

   /**
    * Accessor method to obtain the query string for the object.
    * 
    * @return query string for the object
    */
   public String getQuery()
   {
      if (getQueryDef() != null)
         return getQueryDef().getQuery();
      else
         return null;
   }

   /**
    * Return the partner link associated with this operation, null if none.
    */
   public String getPartnerLink()
   {
      return mPartnerLink;
   }

   /**
    * Sets the partner link associated with this operation, null if none.
    */
   public void setPartnerLink(String aPartnerLink)
   {
      mPartnerLink = aPartnerLink;
   }

   /**
    * Accessor method to obtain the expression for the object.
    * 
    * @return name of the expression for the object
    */
   public String getExpression()
   {
      return mExpression;
   }

   /**
    * Mutator method to set the expression for the object.
    * 
    * @param aExpression the expression value to be set
    */
   public void setExpression(String aExpression)
   {
      mExpression = aExpression;
   }

   /**
    * @return Returns the expressionLanguage.
    */
   public String getExpressionLanguage()
   {
      return mExpressionLanguage;
   }

   /**
    * @param aExpressionLanguage The expressionLanguage to set.
    */
   public void setExpressionLanguage(String aExpressionLanguage)
   {
      mExpressionLanguage = aExpressionLanguage;
   }

   /**
    * @return Returns the strategy.
    */
   public AeSpecStrategyKey getStrategyKey()
   {
      return mStrategyKey;
   }

   /**
    * @param aStrategyKey The strategy to set.
    */
   public void setStrategyKey(AeSpecStrategyKey aStrategyKey)
   {
      mStrategyKey = aStrategyKey;
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.activity.support.IAeQueryParentDef#setQueryDef(org.activebpel.rt.bpel.def.activity.support.AeQueryDef)
    */
   public void setQueryDef(AeQueryDef aQueryDef)
   {
      mQueryDef = aQueryDef;
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.activity.support.IAeQueryParentDef#removeQueryDef()
    */
   public void removeQueryDef()
   {
      mQueryDef = null;
   }

   /**
    * @return Returns the queryDef.
    */
   public AeQueryDef getQueryDef()
   {
      return mQueryDef;
   }
}
