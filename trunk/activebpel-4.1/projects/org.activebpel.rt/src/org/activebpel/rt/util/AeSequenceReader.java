// $Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/util/AeSequenceReader.java,v 1.3 2005/02/08 15:27:1
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

import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.activebpel.rt.AeMessages;

/**
 * Joins two readers together much like <code>java.io.SequenceInputStream</code> 
 */
public class AeSequenceReader extends Reader
{
   /** our readers */
   private Reader[] mReaders;
   /** offset into readers array */
   private int mOffset;
   
   /**
    * Constructor accepts two readers to join together.
    * @param aFirst
    * @param aSecond
    */
   public AeSequenceReader(Reader aFirst, Reader aSecond)
   {
      if (aFirst== null || aSecond == null)
      {
         throw new IllegalArgumentException(AeMessages.getString("AeSequenceReader.ERROR_0")); //$NON-NLS-1$
      }
      mReaders = new Reader[2];
      mReaders[0] = aFirst;
      mReaders[1] = aSecond;
   }
   
   /**
    * Constructor accepts mutliple readers to join together
    * @param aIterOfReaders
    */
   public AeSequenceReader(Iterator aIterOfReaders)
   {
      List list = new LinkedList();
      while(aIterOfReaders.hasNext())
      {
         Reader reader = (Reader)aIterOfReaders.next();
         if (reader == null)
         {
            throw new IllegalArgumentException(AeMessages.getString("AeSequenceReader.ERROR_0")); //$NON-NLS-1$
         }
         list.add(reader);
      }
      mReaders = new Reader[list.size()];
      list.toArray(mReaders);
   }
   
   /**
    * @see java.io.Reader#read(char[], int, int)
    */
   public int read(char[] cbuf, int off, int len) throws IOException
   {
      try
      {
         int result = -1;
         while( hasMoreReaders() && (result = getDelegate().read(cbuf, off, len)) == -1)
            prepNextReader();
         return result;
      }
      catch (IOException e)
      {
         close();
         throw e;
      }
   }

   /**
    * Closes the current reader and moves onto the next
    */
   private void prepNextReader()
   {
      AeCloser.close(getDelegate());
      // null out the reader to help gc
      mReaders[mOffset] = null;
      // move onto the next one
      mOffset++;
   }

   /**
    * Returns the current reader
    */
   private Reader getDelegate()
   {
      return mReaders[mOffset];
   }

   /**
    * Returns true if there are more readers to read.
    */
   private boolean hasMoreReaders()
   {
      return mOffset < mReaders.length && getDelegate() != null;
   }

   /**
    * @see java.io.Reader#close()
    */
   public void close() throws IOException
   {
      for (int i = 0; i < mReaders.length; i++)
      {
         AeCloser.close(mReaders[i]);
      }
   }

}
