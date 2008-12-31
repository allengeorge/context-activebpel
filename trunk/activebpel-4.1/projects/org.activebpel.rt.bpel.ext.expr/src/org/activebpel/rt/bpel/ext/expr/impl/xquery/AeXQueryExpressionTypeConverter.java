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
package org.activebpel.rt.bpel.ext.expr.impl.xquery;

import java.util.List;

import net.sf.saxon.om.Item;
import net.sf.saxon.trans.XPathException;
import net.sf.saxon.value.AtomicValue;
import net.sf.saxon.value.ObjectValue;
import net.sf.saxon.value.Value;

import org.activebpel.rt.bpel.ext.expr.AeMessages;
import org.activebpel.rt.bpel.impl.expr.AeAbstractExpressionTypeConverter;
import org.activebpel.rt.xml.schema.IAeSchemaType;
import org.w3c.dom.Document;

/**
 * An implementation of a type converter for Saxon XQuery.  This class implements the rules outlined
 * in Saxon regarding how to convert from Saxon types to Java types (there, and back again).
 */
public class AeXQueryExpressionTypeConverter extends AeAbstractExpressionTypeConverter
{
   /**
    * @see org.activebpel.rt.bpel.impl.expr.AeAbstractExpressionTypeConverter#convertToExpressionType(java.lang.Object)
    */
   public Object convertToExpressionType(Object aEngineType)
   {
      if (aEngineType instanceof Document)
      {
         return ((Document) aEngineType).getDocumentElement();
      }
      else if (aEngineType instanceof IAeSchemaType)
      {
         AeXQuerySchemaTypeVisitor typeVisitor = new AeXQuerySchemaTypeVisitor();
         ((IAeSchemaType) aEngineType).accept(typeVisitor);
         return typeVisitor.getExpressionValue();
      }
      else
      {
         return super.convertToExpressionType(aEngineType);
      }
   }

   /**
    * Note that the return value from an XQuery expression will always be a Document that Saxon
    * generated for the result.  This Document contains a sequence of values which are either 
    * atomic types or elements.  The AeXQueryTypedExpressionResult class is used to iterate through
    * theose sequence values and return a List of converted Objects.
    * 
    * @see org.activebpel.rt.bpel.impl.expr.IAeExpressionTypeConverter#convertToEngineType(java.lang.Object)
    */
   public Object convertToEngineType(Object aExpressionType)
   {
      // Is this the end result of an expression?
      if (aExpressionType instanceof AeXQueryExpressionResult)
      {
         Document doc = ((AeXQueryExpressionResult) aExpressionType).getDocument();
         List list = AeXQueryTypedExpressionResult.createResultList(doc);
         if (list.size() == 1)
            return list.get(0);
         else if (list.size() == 0)
            return null;
         else
            return list;
      }
      else if (aExpressionType instanceof Item)
      {
         return convertFromSaxonType((Item) aExpressionType);
      }
      else if (aExpressionType == null)
      {
         return aExpressionType;
      }
      else
      {
         // This case should never happen, since this method will only be called either to convert
         // the end result of an Expression or an argument to a custom function.  In the former case,
         // the result will always be a AeXQueryExpressionResult, in the latter case it will always
         // be a Item.
         throw new IllegalArgumentException(AeMessages.format("AeXQueryExpressionTypeConverter.UnexpectedXQueryType", aExpressionType.getClass())); //$NON-NLS-1$
      }
   }

   /**
    * Converts from an internal Saxon type to an Engine type (Java/Ae type).  For example, this method
    * will convert a net.sf.saxon.value.DateTimeValue object to an AeSchemaDateTime object.  It will
    * convert a net.sf.saxon.value.Boolean to a java.lang.Boolean.
    * 
    * @param aExpressionType
    */
   protected Object convertFromSaxonType(Item aExpressionType)
   {
      // The ObjectValue could be an IAeAttachmentItem. This won't be useful for
      // any purpose other than passing it to another function call.
      if (aExpressionType instanceof ObjectValue)
      {
         ObjectValue value = (ObjectValue) aExpressionType;
         return value.getObject();
      }
      if (aExpressionType instanceof AtomicValue)
      {
         AtomicValue value = (AtomicValue) aExpressionType;
         if (AeXQueryTypeMapper.canConvert(value))
         {
            return AeXQueryTypeMapper.convert(value);
         }
      }

      try
      {
         return Value.convert(aExpressionType);
      }
      catch (XPathException ex)
      {
         throw new RuntimeException(ex);
      }
   }
}
