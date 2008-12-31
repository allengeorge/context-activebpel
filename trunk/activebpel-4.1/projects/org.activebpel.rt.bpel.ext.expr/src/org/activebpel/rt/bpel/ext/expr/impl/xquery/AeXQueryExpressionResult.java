// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.ext.expr/src/org/activebpel/rt/bpel/ext/expr/impl/xquery/AeXQueryExpressionResult.java,v 1.1 2006/09/07 15:11:4
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

import org.w3c.dom.Document;

/**
 * This class wraps the Document that is produced when an XQuery expression is executed using 
 * Saxon.  The result is an XML Document that contains a root node and a sequence of nodes that
 * may be either atomic values or elements.
 */
public class AeXQueryExpressionResult
{
   /** The Saxon result document. */
   private Document mDocument;

   /**
    * Constructs the result.
    * 
    * @param aDocument
    */
   public AeXQueryExpressionResult(Document aDocument)
   {
      setDocument(aDocument);
   }

   /**
    * @return Returns the document.
    */
   public Document getDocument()
   {
      return mDocument;
   }

   /**
    * @param aDocument The document to set.
    */
   protected void setDocument(Document aDocument)
   {
      mDocument = aDocument;
   }
}
