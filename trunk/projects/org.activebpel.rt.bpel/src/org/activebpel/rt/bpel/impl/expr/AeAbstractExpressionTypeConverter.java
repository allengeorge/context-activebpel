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
package org.activebpel.rt.bpel.impl.expr;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.activebpel.rt.xml.schema.IAeSchemaType;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * A simple type converter that does some basic type conversions that are necessary for all/most
 * languages.
 */
public abstract class AeAbstractExpressionTypeConverter implements IAeExpressionTypeConverter
{
   /**
    * @see org.activebpel.rt.bpel.impl.expr.IAeExpressionTypeConverter#convertToExpressionType(java.lang.Object)
    */
   public Object convertToExpressionType(Object aEngineType)
   {
      Object rval = null;
      if (aEngineType instanceof IAeSchemaType)
      {
         // Convert our schema types (date, dateTime, time, duration) to Strings
         rval = aEngineType.toString();
      }
      else if (aEngineType instanceof List)
      {
         // Convert all of the items in the list.
         List list = (List) aEngineType;
         List rvalList = new ArrayList();
         if (list.size() > 0)
         {
            for (Iterator iter = list.iterator(); iter.hasNext(); )
            {
               Object item = iter.next();
               if (item instanceof Node)
                  rvalList.add(item);
               else
                  rvalList.add(convertToExpressionType(item));
            }
            rval = rvalList;
         }
      }
      else if (aEngineType instanceof Document)
      {
         // Note: this seems redundant, but is necessary is that the 'instanceof Node' case doesn't 
         // get run.
         rval = aEngineType;
      }
      else if (aEngineType instanceof Node)
      {
         // If it's a Node, wrap it in a List so that it can be used in sub-queries of the form:
         //   getVariableData('var')/sub/xpath/query
         ArrayList list = new ArrayList();
         list.add(aEngineType);
         rval = list;
      }
      else
      {
         rval = aEngineType;
      }
      return rval;
   }
}
