// $Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/util/AeBlobInputStream.java,v 1.4 2007/05/24 00:38:2
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.activebpel.rt.AeException;

/**
 * Implemention of a known length stream. This implementation creates a temporary file that contains the
 * content of the stream passed in the constructor. The file length is the length of the stream. The close
 * method removes the temporary file.
 */
public class AeBlobInputStream extends FileInputStream
{
   /** Default temporary file name prefix. */
   public static final String DEFAULT_TEMP_STREAM_FILE_PREFIX = "aeblob"; //$NON-NLS-1$

   /** The temporary file name prefix for WSIO outbound binary streams. */
   public static final String WSIO_BINARY_STREAM_FILE_PREFIX = "aewsio"; //$NON-NLS-1$

   /** Default temporary file name suffix. */
   public static final String DEFAULT_TEMP_STREAM_FILE_SUFFIX = ".bin"; //$NON-NLS-1$

   /**
    * File holding the contents of the stream.
    */
   private File mBlobFile;
   
   /**
    * Constructor
    * @param aInputStream the stream for which the length can be returned
    * @throws AeException
    */
   public AeBlobInputStream(InputStream aInputStream) throws AeException, FileNotFoundException
   {
      this(aInputStream, DEFAULT_TEMP_STREAM_FILE_PREFIX, DEFAULT_TEMP_STREAM_FILE_SUFFIX);
   }

   /**
    * Constructor allows setting the prefix and suffix of the temporary file name
    * @param aInputStream
    * @param aFilePrefix
    * @param aFileSuffix
    * @throws AeException
    */
   public AeBlobInputStream(InputStream aInputStream, String aFilePrefix, String aFileSuffix) throws AeException, FileNotFoundException
   {
      this(AeUtil.createTempFile(aInputStream, aFilePrefix, aFileSuffix));
   }

   /**
    * Constructs an input stream on the given file without automatically
    * deleting the file when the stream is closed.
    *
    * @param aBlobFile
    * @throws FileNotFoundException
    */
   public AeBlobInputStream(File aBlobFile) throws FileNotFoundException
   {
      super(aBlobFile);

      mBlobFile = aBlobFile;
   }

   /**
    * Overrides method to delete temporary file.
    * 
    * @see java.io.FilterInputStream#close()
    */
   public void close() throws IOException 
   {
      try
      {
         super.close();
      }
      finally
      {
         if ( mBlobFile != null )
         {
            mBlobFile.delete();
            mBlobFile = null;
         }
      }
   }

   /**
    * Returns the <code>File</code> object corresponding to the underlying file.
    */
   public File getBlobFile()
   {
      return mBlobFile;
   }

   /**
    * Returns the length of the underlying file.
    */
   public int length()
   {
      return (int) getBlobFile().length();
   }
}
