// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/AeCharArrayDocumentReader.java,v 1.5 2005/02/08 15:36:0
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
package org.activebpel.rt.bpel.server.engine.storage;

import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.Reader;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.impl.fastdom.AeFastDocument;
import org.activebpel.rt.bpel.impl.fastdom.AeXMLFormatter;
import org.activebpel.rt.bpel.server.AeMessages;
import org.activebpel.rt.util.AeCloser;
import org.activebpel.rt.util.AeUnsynchronizedCharArrayWriter;
import org.activebpel.rt.xml.AeXMLParserBase;
import org.w3c.dom.Document;

/**
 * Delivers an <code>AeFastDocument</code> or standard XML
 * <code>Document</code> as a fixed-length character stream using an in-memory
 * character array.
 */
public class AeCharArrayDocumentReader extends AeDocumentReader
{
   /** Serialization of XML document. */
   private final char[] mCharArray;

   /**
    * Constructor.
    *
    * @param aDocument The <code>AeFastDocument</code>.
    */
   protected AeCharArrayDocumentReader(AeFastDocument aDocument)
   {
      mCharArray = toCharArray(aDocument);
   }

   /**
    * Constructor.
    *
    * @param aDocument The standard XML <code>Document</code>.
    */
   protected AeCharArrayDocumentReader(Document aDocument)
   {
      mCharArray = toCharArray(aDocument);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.AeDocumentReader#createReader()
    */
   protected Reader createReader()
   {
      return new CharArrayReader(getCharArray());
   }

   /**
    * Returns character array containing serialization of the XML document.
    */
   protected char[] getCharArray()
   {
      return mCharArray;
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.AeDocumentReader#getLength()
    */
   public int getLength()
   {
      return getCharArray().length;
   }

   /**
    * Returns character array containing serialization of the specified
    * <code>AeFastDocument</code>.
    *
    * @param aDocument The <code>AeFastDocument</code>.
    */
   protected char[] toCharArray(AeFastDocument aDocument)
   {
      CharArrayWriter out = new AeUnsynchronizedCharArrayWriter();

      try
      {
         new AeXMLFormatter().format(aDocument, out);
         return out.toCharArray();
      }
      finally
      {
         AeCloser.close(out);
      }
   }

   /**
    * Returns character array containing serialization of the specified
    * standard XML <code>Document</code>.
    *
    * @param aDocument The standard XML <code>Document</code>.
    */
   protected char[] toCharArray(Document aDocument)
   {
      CharArrayWriter out = new AeUnsynchronizedCharArrayWriter();

      try
      {
         AeXMLParserBase.saveDocument(aDocument, out);
         return out.toCharArray();
      }
      catch (AeException e)
      {
         AeException.logError(e, AeMessages.getString("AeCharArrayDocumentReader.ERROR_0")); //$NON-NLS-1$
         return new char[0];
      }
      finally
      {
         AeCloser.close(out);
      }
   }
}
