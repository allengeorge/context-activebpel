// $Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/util/AeCloser.java,v 1.9 2007/06/29 14:30:2
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
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.Writer;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.jar.JarFile;

import org.activebpel.rt.AeException;
import org.activebpel.rt.AeMessages;

/**
 * Convenience class for closing I/O without having to catch IOException.
 */
public class AeCloser
{
   /**
    * Closes the InputStream and eats any IOException.
    * @param aIn can be null
    */
   public static void close(InputStream aIn)
   {
      if (aIn != null)
      {
         try
         {
            aIn.close();
         }
         catch (IOException e)
         {
            // eat the exception
         }
      }
   }

   /**
    * Closes Reader and eats any IOException
    * @param aReader
    */
   public static void close(Reader aReader)
   {
      if (aReader != null)
      {
         try
         {
            aReader.close();
         }
         catch (IOException e)
         {
            // eat the exception
         }
      }
   }

   /**
    * Closes Writer and eats any IOException
    * @param aWriter
    */
   public static void close(Writer aWriter)
   {
      if (aWriter != null)
      {
         try
         {
            aWriter.close();
         }
         catch (IOException e)
         {
            // eat the exception
         }
      }
   }

   /**
    * Closes RandomAccessFile and eats any IOException
    * @param aRandomAccessFile
    */
   public static void close(RandomAccessFile aRandomAccessFile)
   {
      if (aRandomAccessFile != null)
      {
         try
         {
            aRandomAccessFile.close();
         }
         catch (IOException e)
         {
            // eat the exception
         }
      }
   }

   /**
    * Closes OutputStream and eats any IOException
    * @param aOut
    */
   public static void close( OutputStream aOut )
   {
      if (aOut != null)
      {
         try
         {
            aOut.close();
         }
         catch (IOException e)
         {
            // eat the exception
         }
      }
   }

   /**
    * Closes JarFile and eats any IOException
    * @param aJarFile
    */
   public static void close( JarFile aJarFile )
   {
      if( aJarFile != null )
      {
         try
         {
            aJarFile.close();
         }
         catch( IOException io )
         {
            // eat it
         }
      }
   }

   /**
    * Closes the connection and eats any SQLException
    * @param aConn
    */
   public static void close(Connection aConn)
   {
      if (aConn != null)
      {
         try
         {
            aConn.close();
         }
         catch (SQLException sql)
         {
            AeException.logError(sql, AeMessages.getString("AeCloser.ERROR_CLOSING_SQL_CONNECTION")); //$NON-NLS-1$
         }
      }
   }

   /**
    * Closes the AeJarReaderUtil.
    *
    * @param aJarReader
    */
   public static void close(AeJarReaderUtil aJarReader)
   {
      if (aJarReader != null)
      {
         aJarReader.close();
      }
   }
}
