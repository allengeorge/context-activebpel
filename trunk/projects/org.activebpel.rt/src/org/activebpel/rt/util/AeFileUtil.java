// $Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/util/AeFileUtil.java,v 1.10 2007/06/29 14:30:1
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.activebpel.rt.AeMessages;

/**
 * Common file utility methods.
 */
public class AeFileUtil
{
   /** Default buffer size. */
   public static final int DEFAULT_BUFFER = 1024 * 4;

   /** prevent instantiation */
   private AeFileUtil() {}

   /**
    * Copy the source file to the destination file.  Neither file can
    * be a directory.
    * @param aSource The source file.
    * @param aDestination The destination file.
    * @throws IOException
    */
   public static void copyFile( File aSource, File aDestination )
   throws IOException
   {
      if( !aSource.isFile() )
      {
         throw new IOException(MessageFormat.format(AeMessages.getString("AeFileUtil.ERROR_10"), new Object [] {aSource} )); //$NON-NLS-1$
      }

      FileInputStream input = new FileInputStream(aSource);

       try
       {
           FileOutputStream output = new FileOutputStream(aDestination);
           try
           {
               AeFileUtil.copy( input, output );
           }
           finally
           {
               AeCloser.close(output);
           }
       }
       finally
       {
           AeCloser.close(input);
       }

       if(aSource.length() != aDestination.length())
       {
          throw new IOException(MessageFormat.format(AeMessages.getString("AeFileUtil.ERROR_11"), //$NON-NLS-1$
                                                     new Object[] {aSource, aDestination}));
       }
   }


   /**
    * Read the contents of the input stream and write them to the output stream.
    * Uses the default buffer size.  The method returns the number of bytes that
    * copied.
    * @param aIn
    * @param aOut
    * @throws IOException
    */
   public static long copy( InputStream aIn, OutputStream aOut ) throws IOException
   {
      return copy( aIn, aOut, DEFAULT_BUFFER );
   }

   /**
    * Read the contents of the input stream and write them to the output stream.
    * Uses the given buffer size.  Returns the number of bytes copied.
    * @param aIn
    * @param aOut
    * @param aBufferSize The buffer size to use for reading.
    * @return the number of bytes copied
    * @throws IOException
    */
   public static long copy( InputStream aIn, OutputStream aOut, int aBufferSize )
   throws IOException
   {
      byte[] buffer = new byte[ aBufferSize ];
      int read = 0;
      long totalBytes = 0;
      while (-1 != (read = aIn.read(buffer)) )
      {
          aOut.write(buffer, 0, read);
          totalBytes += read;
      }
      return totalBytes;
   }

   /**
    * Recursivelt deletes the given directory and all of
    * its nested contents. Use carefully!
    * @param aDirectory
    */
   public static void recursivelyDelete( File aDirectory )
   {
      if( !aDirectory.isDirectory() )
      {
         return;
      }

      File[] files = aDirectory.listFiles();

      if( files != null )
      {
         for( int i = 0; i < files.length; i++ )
         {
            if( files[i].isFile() )
            {
               files[i].delete();
            }
            else
            {
               recursivelyDelete( files[i] );
            }
         }
      }
      aDirectory.delete();
   }

   /**
    * Copies a given ZIP entry to the given output stream.
    *
    * @param aIn The ZIP input stream.
    * @param aOut The output stream to write to.
    * @throws IOException
    */
   protected static void copyEntry(ZipInputStream aIn, OutputStream aOut) throws IOException
   {
      try
      {
         AeFileUtil.copy(aIn, aOut);
      }
      finally
      {
         try
         {
            aIn.closeEntry();
         }
         catch (IOException io)
         {
            io.printStackTrace();
         }
      }
   }

   /**
    * Unpacks the contents of a given ZIP file to the given target directory.
    *
    * @param aFile The source ZIP file.
    * @param aTargetDir The target directory to unpack into.
    * @throws IOException
    */
   public static void unpack(File aFile, File aTargetDir) throws IOException
   {
      File rootDir = aTargetDir;
      rootDir.mkdirs();

      ZipInputStream in = null;
      try
      {
         in = new ZipInputStream(new FileInputStream(aFile));
         ZipEntry entry = in.getNextEntry();
         while (entry != null)
         {
            FileOutputStream out = null;
            try
            {
               if (!entry.isDirectory())
               {
                  File newFile = new File(rootDir, entry.getName());
                  newFile.getParentFile().mkdirs();
                  out = new FileOutputStream(newFile);
                  copyEntry(in, out);
               }
               entry = in.getNextEntry();
            }
            catch (IOException io)
            {
               io.printStackTrace();
               throw io;
            }
            finally
            {
               AeCloser.close(out);
            }
         }
      }
      finally
      {
         AeCloser.close(in);
      }
   }

   /**
    * Returns the filename without the extension.  Will return the
    * string passed in if the filename does not contain a '.' character.
    * @param aFileName
    */
   public static String stripOffExtenstion( String aFileName )
   {
      if( aFileName.indexOf('.') != -1 )
      {
         return aFileName.substring( 0, aFileName.lastIndexOf('.') );
      }
      else
      {
         return aFileName;
      }
   }

   /**
    * Returns the file extension given name. The extension string after the last period.
    * @param aFileName
    * @return file extension or empty string if not available.
    */
   public static String getExtension( String aFileName )
   {
      int i = aFileName.lastIndexOf("."); //$NON-NLS-1$
      if (i != -1 && i < aFileName.length()-2)
      {
         return aFileName.substring(i+1).trim();
      }
      else
      {
         return ""; //$NON-NLS-1$
      }
   }

   /**
    * Splits the given filename to filepath and file name.  For example, C:/temp/hello.txt.
    * returns array tuple("C:/temp", "hello.text").
    * @param aAbsoluteFilePath
    * @return String array with two non-null entries for filepath and filename.
    */
   public static String[] splitFilePathAndName(String aAbsoluteFilePath)
   {
      // normalize path and remove repeating file separators.
      aAbsoluteFilePath = AeUtil.getSafeString(aAbsoluteFilePath).replace('\\', '/').replaceAll("/+", "/"); //$NON-NLS-1$ //$NON-NLS-2$
      String rval [] = new String[2];
      int idx = aAbsoluteFilePath.lastIndexOf('/');
      if (idx != -1)
      {
         rval[0] = aAbsoluteFilePath.substring(0, idx);
         rval[1] = aAbsoluteFilePath.substring(idx + 1);
      }
      else
      {
         rval[0] = ""; //$NON-NLS-1$
         rval[1] = aAbsoluteFilePath;
      }
      return rval;
   }
}
