//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/AeMessageExchangesDef.java,v 1.3 2006/11/03 22:48:0
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

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.activebpel.rt.bpel.def.visitors.IAeDefVisitor;
import org.activebpel.rt.util.AeUtil;

/**
 * Simple container for string values used for binding receives and replies
 */
public class AeMessageExchangesDef extends AeBaseContainer
{
   /** true if the messageExchanges implicitly declares the "default" messageExchange value */
   private boolean mDefaultDeclared = false;

   /** cached set of values for the messageExchanges contained within this def */
   private Set mValues = null;

   /** Indicates if this def is an implicit construct. */
   private boolean mImplict;
   /**
    * Default c'tor.
    */
   public AeMessageExchangesDef()
   {
      super();
   }

   /**
    * @return the implict
    */
   public boolean isImplict()
   {
      return mImplict;
   }

   /**
    * @param aImplict the implict to set
    */
   public void setImplict(boolean aImplict)
   {
      mImplict = aImplict;
   }


   /**
    * Returns true if the message exchange value is contained within the set of declared message exchanges
    * or if the value is empty and the def implicitly declares a default.
    * @param aValue
    */
   public boolean declaresMessageExchange(String aValue)
   {
      return getMessageExchangeValues().contains(aValue) || (isDefaultDeclared() && AeUtil.isNullOrEmpty(aValue));
   }

   /**
    * Gets an Iterator over the message exchange defs.
    */
   public Iterator getMessageExchangeDefs()
   {
      return getValues();
   }

   /**
    * @return Returns the messageExchanges.
    */
   public Set getMessageExchangeValues()
   {
      if (mValues == null)
      {
         Set set = new HashSet();
         for (Iterator iter = getValues(); iter.hasNext(); )
         {
            AeMessageExchangeDef msgExchangeDef = (AeMessageExchangeDef) iter.next();
            set.add(msgExchangeDef.getName());
         }
         mValues = Collections.unmodifiableSet(set);
      }
      return mValues;
   }

   /**
    * Adds a message exchange to the container.
    *
    * @param aDef
    */
   public void addMessageExchangeDef(AeMessageExchangeDef aDef)
   {
      add(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.AeBaseContainer#add(java.lang.Object)
    */
   protected void add(Object aValue)
   {
      super.add(aValue);
      clearValues();
   }

   /**
    * @see org.activebpel.rt.bpel.def.AeBaseContainer#add(java.lang.String, java.lang.Object)
    */
   protected void add(String aKey, Object aValue)
   {
      super.add(aKey, aValue);
      clearValues();
   }

   /**
    * Clears the cached set of values
    */
   protected void clearValues()
   {
      mValues = null;
   }

   /**
    * @return Returns the defaultDeclared.
    */
   public boolean isDefaultDeclared()
   {
      return mDefaultDeclared;
   }

   /**
    * @param aDefaultDeclared The defaultDeclared to set.
    */
   public void setDefaultDeclared(boolean aDefaultDeclared)
   {
      mDefaultDeclared = aDefaultDeclared;
   }


   /**
    * @see org.activebpel.rt.bpel.def.AeBaseDef#accept(org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void accept(IAeDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }
}
