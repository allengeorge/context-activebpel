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
package org.activebpel.rt.bpel.ext.expr.impl.javascript;

import java.util.Date;
import java.util.List;

import org.activebpel.rt.bpel.impl.expr.AeAbstractExpressionTypeConverter;
import org.activebpel.rt.util.AeXmlUtil;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.IdScriptableObject;
import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.ScriptRuntime;
import org.w3c.dom.Element;

/**
 * A JavaScript implementation of a type converter.
 */
public class AeJavaScriptExpressionTypeConverter extends AeAbstractExpressionTypeConverter
{
   /**
    * @see org.activebpel.rt.bpel.impl.expr.AeAbstractExpressionTypeConverter#convertToExpressionType(java.lang.Object)
    */
   public Object convertToExpressionType(Object aEngineType)
   {
      Object rval = super.convertToExpressionType(aEngineType);
      if (rval instanceof List)
         if (((List) rval).size() == 1)
            rval = ((List) rval).get(0);

      if (rval instanceof Element)
      {
         Element elem = (Element) rval;
         if (AeXmlUtil.getFirstSubElement(elem) == null)
         {
            rval = AeXmlUtil.getText((Element) rval);
         }
      }

      return rval;
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.expr.IAeExpressionTypeConverter#convertToEngineType(java.lang.Object)
    */
   public Object convertToEngineType(Object aExpressionType)
   {
      // Unwrap the return value if necessary.
      if (aExpressionType instanceof NativeJavaObject)
      {
         return ((NativeJavaObject) aExpressionType).unwrap();
      }
      else if (aExpressionType instanceof IdScriptableObject)
      {
         Context.enter();

         try
         {
            IdScriptableObject idsObj = (IdScriptableObject) aExpressionType;
            // Handle an expression like:  'new Date(85, 2, 10)'
            // which will return a NativeDate object (which is a private rhino class)
            if ("Date".equals(idsObj.getClassName())) //$NON-NLS-1$
            {
               Number number = (Number) idsObj.getDefaultValue(ScriptRuntime.NumberClass);
               Date date = new Date(number.longValue());
               return date;
            }
            else
            {
               // No other special cases are known - just in case this happens, convert to string.
               return idsObj.getDefaultValue(ScriptRuntime.StringClass);
            }
         }
         finally
         {
            Context.exit();
         }
      }
      else
      {
         return aExpressionType;
      }
   }
}
