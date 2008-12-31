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
package org.activebpel.rt.bpel.ext.expr.bsf.impl.python;

import java.util.List;

import org.activebpel.rt.bpel.impl.expr.AeAbstractExpressionTypeConverter;
import org.activebpel.rt.util.AeXmlUtil;
import org.activebpel.rt.xml.schema.IAeSchemaType;
import org.python.core.PyFloat;
import org.python.core.PyInteger;
import org.python.core.PyLong;
import org.python.core.PyObject;
import org.w3c.dom.Element;

/**
 * Implements a python/Jython type converter.  This class converts values returned by Jython into 
 * native Java types.
 */
public class AeBSFPythonExpressionTypeConverter extends AeAbstractExpressionTypeConverter
{
   /**
    * @see org.activebpel.rt.bpel.impl.expr.xpath.AeXPathExpressionTypeConverter#convertToEngineType(java.lang.Object)
    */
   public Object convertToEngineType(Object aExpressionType)
   {
      if (aExpressionType instanceof PyInteger)
      {
         return new Integer(((PyInteger) aExpressionType).getValue());
      }
      else if (aExpressionType instanceof PyFloat)
      {
         return new Float(((PyFloat) aExpressionType).getValue());
      }
      else if (aExpressionType instanceof PyLong)
      {
         return new Double(((PyLong) aExpressionType).doubleValue());
      }
      else if (aExpressionType instanceof PyObject)
      {
         return aExpressionType.toString();
      }
      else
      {
         return aExpressionType;
      }
   }

   /**
    * @see org.activebpel.rt.bpel.impl.expr.xpath.AeXPathExpressionTypeConverter#convertToExpressionType(java.lang.Object)
    */
   public Object convertToExpressionType(Object aEngineType)
   {
      Object rval = super.convertToExpressionType(aEngineType);
      if (rval instanceof Number)
      {
         rval = new Double(((Number) aEngineType).doubleValue());
      }

      // Unwrap the return value if necessary.
      if (aEngineType instanceof List)
      {
         if (((List) aEngineType).size() == 1)
         {
            Object val = ((List) aEngineType).get(0);
            if (val instanceof Element)
            {
               rval = AeXmlUtil.getText((Element) val);
            }
            else
            {
               rval = val;
            }
         }
      }
      else if (aEngineType instanceof IAeSchemaType)
      {
         rval = aEngineType.toString();
      }

      return rval;
   }
}
