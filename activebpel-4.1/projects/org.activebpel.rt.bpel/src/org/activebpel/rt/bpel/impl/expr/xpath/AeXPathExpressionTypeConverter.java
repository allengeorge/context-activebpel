//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/expr/xpath/AeXPathExpressionTypeConverter.java,v 1.2 2006/07/10 16:32:5
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
package org.activebpel.rt.bpel.impl.expr.xpath;

import org.activebpel.rt.bpel.impl.expr.AeAbstractExpressionTypeConverter;
import org.activebpel.rt.bpel.xpath.AeXPathHelper;

/**
 * An implementation of a type converter for jaxen.  This class is necessary because Jaxen doesn't like
 * certain Java types.  So we convert those types to other types that Jaxen DOES like.
 */
public class AeXPathExpressionTypeConverter extends AeAbstractExpressionTypeConverter
{
   /** The XPath Helper to use. */
   private AeXPathHelper mXPathHelper;

   /**
    * Constructor.
    * 
    * @param aXPathHelper
    */
   public AeXPathExpressionTypeConverter(AeXPathHelper aXPathHelper)
   {
      setXPathHelper(aXPathHelper);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.expr.IAeExpressionTypeConverter#convertToExpressionType(java.lang.Object)
    */
   public Object convertToExpressionType(Object aEngineType)
   {
      Object type = super.convertToExpressionType(aEngineType);
      if (type instanceof Number)
      {
         // jaxen likes doubles, but not floats, big integers, so convert to double here
         type = new Double(((Number) aEngineType).doubleValue());
      }
      return type;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.expr.IAeExpressionTypeConverter#convertToEngineType(java.lang.Object)
    */
   public Object convertToEngineType(Object aExpressionType)
   {
      return getXPathHelper().unwrapXPathValue(aExpressionType);
   }

   /**
    * @return Returns the xPathHelper.
    */
   protected AeXPathHelper getXPathHelper()
   {
      return mXPathHelper;
   }

   /**
    * @param aPathHelper The xPathHelper to set.
    */
   protected void setXPathHelper(AeXPathHelper aPathHelper)
   {
      mXPathHelper = aPathHelper;
   }
}