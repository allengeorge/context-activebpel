//$Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/util/AeMimeUtil.java,v 1.9 2007/09/07 19:06:3
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility methods for working with mime attributes
 */
public class AeMimeUtil
{
   /** key content type attribute */
   public static final String CONTENT_TYPE_ATTRIBUTE = "Content-Type"; //$NON-NLS-1$

   /** content location type attribute */
   public static final String CONTENT_LOCATION_ATTRIBUTE = "Content-Location"; //$NON-NLS-1$

   /** content id attribute */
   public static final String CONTENT_ID_ATTRIBUTE = "Content-Id"; //$NON-NLS-1$
   
   /** Ae extension length attribute */
   public static final String AE_SIZE_ATTRIBUTE = "X-Size"; //$NON-NLS-1$
   
   /** Default Content-Id for attachments added during remote debug  */
   public static final String AE_DEFAULT_REMOTE_CONTENT_ID = "remote-debug-1"; //$NON-NLS-1$
   
   /** Default Content-Id for attachments added from the admin console */
   public static final String AE_DEFAULT_ADMIN_CONTENT_ID = "bpel-admin-1"; //$NON-NLS-1$
   
   /** Default Content-Id for attachments added from an internal process expression function,
    *  the process id is appended by the engine for identification.
    */
   public static final String AE_DEFAULT_INLINE_CONTENT_ID = "bpel-inline-"; //$NON-NLS-1$

   /** default download file name */
   public static final String DEFAULT_FILE_NAME = "download"; //$NON-NLS-1$

   /** default download file name Eextension */
   public static final String DEFAULT_FILE_NAME_EXT = "bin"; //$NON-NLS-1$

   /** Map of default content-type to file extentions. */
   private static Map sMimeToExtension;
   static
   {
      sMimeToExtension = new HashMap();
      sMimeToExtension.put("video/x-msvideo",               "avi"); //$NON-NLS-1$ //$NON-NLS-2$
      sMimeToExtension.put("application/octet-stream",      "bin"); //$NON-NLS-1$ //$NON-NLS-2$
      sMimeToExtension.put("image/bmp",                     "bmp"); //$NON-NLS-1$ //$NON-NLS-2$
      sMimeToExtension.put("application/x-msdownload",      "dll"); //$NON-NLS-1$ //$NON-NLS-2$
      sMimeToExtension.put("application/msword",            "doc"); //$NON-NLS-1$ //$NON-NLS-2$
      sMimeToExtension.put("image/gif",                     "gif"); //$NON-NLS-1$ //$NON-NLS-2$
      sMimeToExtension.put("application/x-gzip",            "gz");  //$NON-NLS-1$ //$NON-NLS-2$
      sMimeToExtension.put("text/html",                     "html");//$NON-NLS-1$ //$NON-NLS-2$
      sMimeToExtension.put("image/x-icon",                  "ico"); //$NON-NLS-1$ //$NON-NLS-2$
      sMimeToExtension.put("application/java",              "java");//$NON-NLS-1$ //$NON-NLS-2$ 
      sMimeToExtension.put("image/jpg",                     "jpg"); //$NON-NLS-1$ //$NON-NLS-2$
      sMimeToExtension.put("image/jpeg",                    "jpeg");//$NON-NLS-1$ //$NON-NLS-2$
      sMimeToExtension.put("application/javascript",        "js");  //$NON-NLS-1$ //$NON-NLS-2$
      sMimeToExtension.put("application/jsp",               "jsp"); //$NON-NLS-1$ //$NON-NLS-2$
      sMimeToExtension.put("video/x-la-asf",                "lsf"); //$NON-NLS-1$ //$NON-NLS-2$
      sMimeToExtension.put("application/x-msaccess",        "mdb"); //$NON-NLS-1$ //$NON-NLS-2$
      sMimeToExtension.put("audio/mid",                     "mid"); //$NON-NLS-1$ //$NON-NLS-2$ 
      sMimeToExtension.put("application/x-msmoney",         "mny"); //$NON-NLS-1$ //$NON-NLS-2$
      sMimeToExtension.put("video/quicktime",               "mov"); //$NON-NLS-1$ //$NON-NLS-2$
      sMimeToExtension.put("video/mpeg",                    "mp2"); //$NON-NLS-1$ //$NON-NLS-2$
      sMimeToExtension.put("audio/mpeg",                    "mp3"); //$NON-NLS-1$ //$NON-NLS-2$
      sMimeToExtension.put("video/mpeg",                    "mpg"); //$NON-NLS-1$ //$NON-NLS-2$
      sMimeToExtension.put("application/vnd.ms-project",    "mpp"); //$NON-NLS-1$ //$NON-NLS-2$
      sMimeToExtension.put("application/pdf",               "pdf"); //$NON-NLS-1$ //$NON-NLS-2$
      sMimeToExtension.put("image/png",                     "png"); //$NON-NLS-1$ //$NON-NLS-2$
      sMimeToExtension.put("application/vnd.ms-powerpoint", "ppt"); //$NON-NLS-1$ //$NON-NLS-2$
      sMimeToExtension.put("application/postscript",        "ps");  //$NON-NLS-1$ //$NON-NLS-2$
      sMimeToExtension.put("audio/x-pn-realaudio",          "ram"); //$NON-NLS-1$ //$NON-NLS-2$
      sMimeToExtension.put("application/rtf",               "rtf"); //$NON-NLS-1$ //$NON-NLS-2$
      sMimeToExtension.put("image/tiff",                    "tiff");//$NON-NLS-1$ //$NON-NLS-2$
      sMimeToExtension.put("text/plain",                    "txt"); //$NON-NLS-1$ //$NON-NLS-2$   
      sMimeToExtension.put("text/x-vcard",                  "vcf"); //$NON-NLS-1$ //$NON-NLS-2$
      sMimeToExtension.put("audio/wav",                     "wav"); //$NON-NLS-1$ //$NON-NLS-2$
      sMimeToExtension.put("application/x-msmetafile",      "wmf"); //$NON-NLS-1$ //$NON-NLS-2$
      sMimeToExtension.put("application/vnd.ms-excel",      "xls"); //$NON-NLS-1$ //$NON-NLS-2$
      sMimeToExtension.put("text/xml",                      "xml"); //$NON-NLS-1$ //$NON-NLS-2$
      sMimeToExtension.put("application/x-compress",        "z");   //$NON-NLS-1$ //$NON-NLS-2$
      sMimeToExtension.put("application/zip",               "zip"); //$NON-NLS-1$ //$NON-NLS-2$
   
     
   }

   /** The extension map of well known Mime types */
   protected static Map sExtensionToMime;
  
   static
   {
      sExtensionToMime = new HashMap();
      
      sExtensionToMime.put("bas",        "text/plain"); //$NON-NLS-1$ //$NON-NLS-2$
      sExtensionToMime.put("java",       "text/plain"); //$NON-NLS-1$ //$NON-NLS-2$
      sExtensionToMime.put("ini",        "text/plain"); //$NON-NLS-1$ //$NON-NLS-2$
      sExtensionToMime.put("sql",        "text/plain"); //$NON-NLS-1$ //$NON-NLS-2$
      sExtensionToMime.put("unl",        "text/plain"); //$NON-NLS-1$ //$NON-NLS-2$
      sExtensionToMime.put("c",          "text/plain"); //$NON-NLS-1$ //$NON-NLS-2$
      sExtensionToMime.put("properties", "text/plain"); //$NON-NLS-1$ //$NON-NLS-2$
      sExtensionToMime.put("bpel",       "text/xml"); //$NON-NLS-1$ //$NON-NLS-2$
      sExtensionToMime.put("vbpel",      "text/xml"); //$NON-NLS-1$ //$NON-NLS-2$
      sExtensionToMime.put("htm",        "text/html"); //$NON-NLS-1$ //$NON-NLS-2$
      sExtensionToMime.put("dot",        "application/msword"); //$NON-NLS-1$ //$NON-NLS-2$
      sExtensionToMime.put("xla",        "application/vnd.ms-excel"); //$NON-NLS-1$ //$NON-NLS-2$
      sExtensionToMime.put("xlc",        "application/vnd.ms-excel"); //$NON-NLS-1$ //$NON-NLS-2$
      sExtensionToMime.put("xlm",        "application/vnd.ms-excel"); //$NON-NLS-1$ //$NON-NLS-2$
      sExtensionToMime.put("xlt",        "application/vnd.ms-excel"); //$NON-NLS-1$ //$NON-NLS-2$
      sExtensionToMime.put("xlw",        "application/vnd.ms-excel"); //$NON-NLS-1$ //$NON-NLS-2$
      sExtensionToMime.put("class",      "application/octet-stream"); //$NON-NLS-1$ //$NON-NLS-2$
      sExtensionToMime.put("exe",        "application/octet-stream"); //$NON-NLS-1$ //$NON-NLS-2$
      sExtensionToMime.put("lha",        "application/octet-stream"); //$NON-NLS-1$ //$NON-NLS-2$
      sExtensionToMime.put("lzh",        "application/octet-stream"); //$NON-NLS-1$ //$NON-NLS-2$
      sExtensionToMime.put("pps",        "application/vnd.ms-powerpoint"); //$NON-NLS-1$ //$NON-NLS-2$
      sExtensionToMime.put("eps",        "application/postscript"); //$NON-NLS-1$ //$NON-NLS-2$
      sExtensionToMime.put("ai",         "application/postscript"); //$NON-NLS-1$ //$NON-NLS-2$
      sExtensionToMime.put("jpe",        "image/jpeg"); //$NON-NLS-1$ //$NON-NLS-2$
      sExtensionToMime.put("mpa",        "video/mpeg"); //$NON-NLS-1$ //$NON-NLS-2$
      sExtensionToMime.put("mpe",        "video/mpeg"); //$NON-NLS-1$ //$NON-NLS-2$
      sExtensionToMime.put("mpeg",       "video/mpeg"); //$NON-NLS-1$ //$NON-NLS-2$
      sExtensionToMime.put("mpg",        "video/mpeg"); //$NON-NLS-1$ //$NON-NLS-2$
      sExtensionToMime.put("mpv2",       "video/mpeg"); //$NON-NLS-1$ //$NON-NLS-2$
      sExtensionToMime.put("lsx",        "video/x-la-asf"); //$NON-NLS-1$ //$NON-NLS-2$
      sExtensionToMime.put("rmi",        "audio/mid"); //$NON-NLS-1$ //$NON-NLS-2$
     
      
      for (Iterator iter=sMimeToExtension.entrySet().iterator(); iter.hasNext();)
      {
         Map.Entry entry = (Map.Entry)iter.next();
         sExtensionToMime.put(entry.getValue(), entry.getKey());
      }
   }

   /**
    * Regular expression to match a valid Content-Id per RFC 2046
    */
   private static Pattern sContentIdRegEx = Pattern.compile(
         "<([^\\s]*)@([^\\s]*)>", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE); //$NON-NLS-1$

   /** Regular expression to match a valid file name */
   private static Pattern sNameRegEx = Pattern.compile(
         "([a-zA-Z0-9_\\-\\. ]+)", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE); //$NON-NLS-1$

   /** regular expression to match a filename with an extension */
   private static Pattern sNameExtRegEx = Pattern.compile(
         "(.*)\\.([a-z][A-Z0-9_\\-]+)", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE); //$NON-NLS-1$

   /** regular expression to match a filename ending with . */
   private static Pattern sEndDotRegEx = Pattern.compile(
         "(.*)\\.", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE); //$NON-NLS-1$

   /**
    * Return a reasonable file name for downloading the attachment
    * @param aHeaders map of name/value mime headers
    * @return filename with extension
    */
   public static String getFileName(Map aHeaders)
   {
      return getFileName(aHeaders, DEFAULT_FILE_NAME, DEFAULT_FILE_NAME_EXT);
   }

   /**
    * Return a reasonable file name for downloading the attachment
    * @param aHeaders map of name/value mime headers
    * @param aDefaultFileName fallback default filename
    * @return filename with extension
    */
   public static String getFileName(Map aHeaders, String aDefaultFileName)
   {
      return getFileName(aHeaders, aDefaultFileName, null);
   }

   /**
    * Return a reasonable file name for downloading the attachment
    * @param aHeaders map of name/value mime headers
    * @param aDefaultFileName fallback default filename
    * @param aDefaultExt fallback default filename extension
    * @return filename with extension
    */
   public static String getFileName(Map aHeaders, String aDefaultFileName, String aDefaultExt)
   {
      String fileName = null;
      String ext = null;

      // Workout a reasonable filename
      Matcher matcher;

      String match = (String)aHeaders.get(CONTENT_LOCATION_ATTRIBUTE);
      if ( !AeUtil.isNullOrEmpty(match) )
      {
         if ( sNameRegEx.matcher(match).matches() )
         {
            fileName = match;
         }
      }

      match = (String)aHeaders.get(CONTENT_ID_ATTRIBUTE);
      if ( !AeUtil.isNullOrEmpty(match) && AeUtil.isNullOrEmpty(fileName) )
      {
         matcher = sContentIdRegEx.matcher(match);
         if ( matcher.matches() )
         {
            fileName = matcher.group(1);
         }
         if ( sNameRegEx.matcher(match).matches() )
         {
            fileName = match;
         }
      }

      if ( sMimeToExtension.get((String)aHeaders.get(CONTENT_TYPE_ATTRIBUTE)) != null )
      {
         ext = (String)sMimeToExtension.get((String)aHeaders.get(CONTENT_TYPE_ATTRIBUTE));
      }

      if ( AeUtil.isNullOrEmpty(fileName) )
      {
         // resort to default
         fileName = (aDefaultFileName == null) ? DEFAULT_FILE_NAME : aDefaultFileName;
      }

      if ( AeUtil.isNullOrEmpty(ext) )
      {
         // resort to default
         ext = (aDefaultExt == null) ? DEFAULT_FILE_NAME_EXT : aDefaultExt;
      }

      matcher = sNameExtRegEx.matcher(fileName);
      if ( matcher.matches() )
      {
         if ( ext.equals(matcher.group(2)) )
         {
            fileName = matcher.group(1);
         }
      }

      matcher = sEndDotRegEx.matcher(fileName);
      if ( matcher.matches() )
      {
         fileName = matcher.group(1);
      }

      return fileName + "." + ext; //$NON-NLS-1$
   }

   /**
    * Returns the length of the given file.
    * @param aFilename
    */
   public static long getContentLength(String aFilename)
   {
      return new File(aFilename).length();
   }
   
   /**
    * Given a filename, return the appropriate content type
    * @param aFilename
    */
   public static String getContentType(String aFilename)
   {
      return getContentType(aFilename,"unknown"); //$NON-NLS-1$
   }

   /**
    * Given a filename, return the appropriate content type. If the type is not found,
    * then the default value is returned.
    * @param aFilename file name
    * @param aDefaultType default content type.
    */
   public static String getContentType(String aFilename, String aDefaultType)
   {
      String ext = AeFileUtil.getExtension(aFilename);
      return getContentTypeFromExtension(ext, aDefaultType);
   }

   /**
    * Given a file extension, returns the appropriate content type. If the type is not found,
    * then the default value is returned.
    * @param aExtension file name extension
    * @param aDefaultType default content type.
    */
   public static String getContentTypeFromExtension(String aExtension, String aDefaultType)
   {  
      String contentType = (String)sExtensionToMime.get(AeUtil.getSafeString(aExtension).toLowerCase());
      if (AeUtil.notNullOrEmpty(contentType))
      {
         return contentType;
      }
      else if (AeUtil.notNullOrEmpty(aDefaultType))
      {
         return aDefaultType;
      }
      else
      {
         // default is binary stream.
         return "application/octet-stream"; //$NON-NLS-1$
      }
   }

   /**
    * Returns the mime type from the map of mime headers
    * @param aHeaders
    * @return mime type
    */
   public static String getMimeType(Map aHeaders)
   {
      return (aHeaders == null) ? null : (String)aHeaders.get(CONTENT_TYPE_ATTRIBUTE);
   }
}
