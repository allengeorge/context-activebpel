//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/assign/to/AeToVariableElementWithQuery.java,v 1.4 2006/09/20 17:01:4
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
package org.activebpel.rt.bpel.impl.activity.assign.to; 

import org.activebpel.rt.bpel.def.activity.support.AeToDef;
import org.activebpel.rt.bpel.impl.AeBpelException;
import org.w3c.dom.Element;

/**
 * Selects data from an element variable using a query.
 * This is only supported in BPEL4WS 1.1
 */
public class AeToVariableElementWithQuery extends AeToBase
{
   /** query for the element */
   private String mQuery;
   /** query language for the element */
   private String mQueryLanguage;
   
   /**
    * Ctor accepts def
    * 
    * @param aToDef
    */
   public AeToVariableElementWithQuery(AeToDef aToDef)
   {
      super(aToDef);
      setQuery(aToDef.getQuery());
      setQueryLanguage(aToDef.getQueryDef().getQueryLanguage());
   }
   
   /**
    * Ctor accepts variable and query
    * 
    * @param aVariable
    * @param aQuery
    * @param aQueryLanguage
    */
   public AeToVariableElementWithQuery(String aVariable, String aQuery, String aQueryLanguage)
   {
      setVariableName(aVariable);
      setQuery(aQuery);
      setQueryLanguage(aQueryLanguage);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.assign.IAeTo#getTarget()
    */
   public Object getTarget() throws AeBpelException
   {
      AeVariableElementDataWrapper wrapper = new AeVariableElementDataWrapper(getVariable());

      // this will initialize the element for us if it's null
      Element element = (Element) wrapper.getValue();
      
      return AeToQueryRunner.selectValue(getCopyOperation(), getQuery(), element);
   }

   /**
    * @return Returns the query.
    */
   public String getQuery()
   {
      return mQuery;
   }

   /**
    * @param aQuery The query to set.
    */
   public void setQuery(String aQuery)
   {
      mQuery = aQuery;
   }

   /**
    * @return Returns the queryLanguage.
    */
   protected String getQueryLanguage()
   {
      return mQueryLanguage;
   }

   /**
    * @param aQueryLanguage The queryLanguage to set.
    */
   protected void setQueryLanguage(String aQueryLanguage)
   {
      mQueryLanguage = aQueryLanguage;
   }
}
 
