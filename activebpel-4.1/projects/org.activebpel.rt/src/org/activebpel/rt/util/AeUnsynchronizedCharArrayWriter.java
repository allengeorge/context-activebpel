// $Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/util/AeUnsynchronizedCharArrayWriter.java,v 1.2 2006/07/14 21:44:4
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
package org.activebpel.rt.util;

import java.io.CharArrayWriter;

/**
 * Extends <code>CharArrayWriter</code> with unsynchronized write methods.
 */
public class AeUnsynchronizedCharArrayWriter extends CharArrayWriter
{
   private static final int DEFAULT_CAPACITY = 8192;

   /**
    * Default constructor.
    */
   public AeUnsynchronizedCharArrayWriter()
   {
      super(DEFAULT_CAPACITY);
   }

   /**
    * Ensures that <code>mBuffer</code> has at least the specified capacity.
    *
    * @param aCapacity
    */
   protected void ensureCapacity(int aCapacity)
   {
      if (aCapacity > buf.length)
      {
         char[] newBuffer = new char[aCapacity + buf.length];
         System.arraycopy(buf, 0, newBuffer, 0, count);
         buf = newBuffer;
      }
   }

   /**
    * @see java.io.Writer#write(int)
    */
   public void write(int c)
   {
      int newCount = count + 1;
      ensureCapacity(newCount);
      buf[count] = (char) c;
      count = newCount;
   }

   /**
    * @see java.io.Writer#write(char[], int, int)
    */
   public void write(char[] cbuf, int off, int len)
   {
      int newCount = count + len;
      ensureCapacity(newCount);
      System.arraycopy(cbuf, off, buf, count, len);
      count = newCount;
   }

   /**
    * @see java.io.Writer#write(java.lang.String, int, int)
    */
   public void write(String str, int off, int len)
   {
      int newCount = count + len;
      ensureCapacity(newCount);
      str.getChars(off, off + len, buf, count);
      count = newCount;
   }
   
   /**
    * @see java.io.Writer#write(java.lang.String)
    */
   public void write(String aStr)
   {
      write(aStr, 0, aStr.length());
   }
}
