//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/assign/from/AeFromVariableMessagePartWithQuery.java,v 1.5 2006/11/16 23:44:1
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
package org.activebpel.rt.bpel.impl.activity.assign.from; 

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.def.activity.support.AeFromDef;
import org.activebpel.rt.bpel.xpath.AeXPathHelper;

/**
 * Handles selecting data from a variable part with a query. 
 */
public class AeFromVariableMessagePartWithQuery extends AeFromVariableMessagePart
{
   /** query for message part */
   private String mQuery;
   
   /**
    * Ctor accepts the def object
    * 
    * @param aFromDef
    */
   public AeFromVariableMessagePartWithQuery(AeFromDef aFromDef)
   {
      super(aFromDef);
      setQuery(aFromDef.getQuery());
   }
   
   /**
    * Ctor accepts variable name, part, and query
    * @param aVariableName
    * @param aPart
    * @param aQuery
    */
   public AeFromVariableMessagePartWithQuery(String aVariableName, String aPart, String aQuery)
   {
      super(aVariableName, aPart);
      setQuery(aQuery);
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.activity.assign.IAeFrom#getFromData()
    */
   public Object getFromData() throws AeBusinessProcessException
   {
      Object data = super.getFromData();

      data = getCopyOperation().getContext().executeQuery(getQuery(), data);
      data = AeXPathHelper.getInstance(getCopyOperation().getContext().getBPELNamespace()).unwrapXPathValue(data);
      
      return data;
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
} 
