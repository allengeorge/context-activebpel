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
package org.activebpel.rt.bpel.ext.expr.bsf.impl;

import java.util.List;

import org.activebpel.rt.bpel.impl.expr.IAeExpressionTypeConverter;
import org.w3c.dom.Node;

/**
 * Implements a default BSF type converter.  This converter knows nothing about any particular langage,
 * and so is basically just a pass-through converter.  It passes through common types and converts
 * unknown types to String.
 */
public class AeDefaultBSFExpressionTypeConverter implements IAeExpressionTypeConverter
{
   /**
    * @see org.activebpel.rt.bpel.impl.expr.IAeExpressionTypeConverter#convertToEngineType(java.lang.Object)
    */
   public Object convertToEngineType(Object aExpressionType)
   {
      if (aExpressionType instanceof Integer || aExpressionType instanceof Double || aExpressionType instanceof String || aExpressionType instanceof Boolean)
      {
         return aExpressionType;
      }
      if (aExpressionType instanceof Node || aExpressionType instanceof List)
      {
         return aExpressionType;
      }
      else
      {
         return aExpressionType.toString();
      }
   }

   /**
    * @see org.activebpel.rt.bpel.impl.expr.AeAbstractExpressionTypeConverter#convertToExpressionType(java.lang.Object)
    */
   public Object convertToExpressionType(Object aEngineType)
   {
      return aEngineType;
   }
}
