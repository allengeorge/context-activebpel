// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/AeDocumentReader.java,v 1.2 2004/09/07 22:13:4
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

import java.io.IOException;
import java.io.Reader;

import org.activebpel.rt.bpel.impl.fastdom.AeFastDocument;
import org.w3c.dom.Document;

/**
 * Abstract class that delivers an <code>AeFastDocument</code> or standard XML
 * <code>Document</code> as a fixed-length character stream (Java
 * <code>Reader</code>). Subclasses implement the <code>createReader</code> and
 * <code>getLength</code> methods.
 */
public abstract class AeDocumentReader extends Reader
{
   /** The underlying character stream that serializes the XML document. */
   private Reader mReader;

   /**
    * Creates the underlying character stream.
    *
    * @return Reader
    */
   protected abstract Reader createReader();

   /**
    * Returns a character stream that serializes the specified
    * <code>AeFastDocument</code>.
    *
    * @param aDocument The document to serialize.
    * @return AeDocumentReader
    */
   public static AeDocumentReader getDocumentReader(AeFastDocument aDocument)
   {
      return new AeCharArrayDocumentReader(aDocument);
   }

   /**
    * Returns a character stream that serializes the specified standard XML
    * document.
    *
    * @param aDocument The document to serialize.
    * @return AeDocumentReader
    */
   public static AeDocumentReader getDocumentReader(Document aDocument)
   {
      return new AeCharArrayDocumentReader(aDocument);
   }

   /**
    * Returns the underlying character stream.
    */
   public Reader getReader()
   {
      if (mReader == null)
      {
         mReader = createReader();
      }

      return mReader;
   }

   /**
    * Returns the length of the serialized document.
    *
    * @return int
    */
   public abstract int getLength();

   /*======================================================================
    * java.io.Reader methods
    *======================================================================
    */

   /**
    * @see java.io.Reader#close()
    */
   public void close() throws IOException
   {
      getReader().close();
   }

   /**
    * @see java.io.Reader#read()
    */
   public int read() throws IOException
   {
      return getReader().read();
   }

   /**
    * @see java.io.Reader#read(char[])
    */
   public int read(char[] aBuffer) throws IOException
   {
      return getReader().read(aBuffer);
   }

   /**
    * @see java.io.Reader#read(char[], int, int)
    */
   public int read(char[] aBuffer, int aOffset, int aLength) throws IOException
   {
      return getReader().read(aBuffer, aOffset, aLength);
   }

   /**
    * @see java.io.Reader#skip(long)
    */
   public long skip(long aCount) throws IOException
   {
      return getReader().skip(aCount);
   }
}
