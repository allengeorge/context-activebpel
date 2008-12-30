// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/attachment/AeAttachmentFile.java,v 1.1 2007/05/24 00:57:1
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
package org.activebpel.rt.bpel.impl.attachment;

import java.io.File;
import java.io.InputStream;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.util.AeUtil;

/**
 * Implements a file managed by an {@link AeFileAttachmentStorage}. Maintains a
 * reference count to automatically delete the underlying disk file when the
 * storage and all attached streams are done with the file.
 */
public class AeAttachmentFile extends File
{
   /** Prefix for names of temporary attachment file. */
   public static final String ATTACHMENT_FILE_PREFIX = "att"; //$NON-NLS-1$

   /** Suffix for names of temporary attachment file. */
   public static final String ATTACHMENT_FILE_SUFFIX = ".bin"; //$NON-NLS-1$

   /** Count of references to this file. */
   private int mReferenceCount;

   /**
    * Constructs a file from an attachment input stream.
    *
    * @param aInputStream
    * @throws AeBusinessProcessException
    */
   public AeAttachmentFile(InputStream aInputStream) throws AeBusinessProcessException
   {
      super(createAttachmentFile(aInputStream).getAbsolutePath());

      // Initialize the reference count to 1, so at least one explicit call to
      // delete() is necessary to remove the file.
      mReferenceCount = 1;
   }

   /**
    * Add a reference to this file. Called by
    * {@link AeAttachmentFileInputStream#AeAttachmentFileInputStream(File)}.
    */
   protected int addReference()
   {
      return ++mReferenceCount;
   }

   /**
    * Overrides method to really delete the file only when all references have
    * tried to delete the file.
    * 
    * @see java.io.File#delete()
    */
   public boolean delete()
   {
      return (--mReferenceCount > 0) ? true : super.delete();
   }

   /**
    * Convenience method to create a temporary file from an input stream.
    *
    * @param aInputStream
    * @throws AeBusinessProcessException
    */
   protected static File createAttachmentFile(InputStream aInputStream) throws AeBusinessProcessException
   {
      try
      {
         // Store the attachment stream into a temporary file.
         return AeUtil.createTempFile(aInputStream, ATTACHMENT_FILE_PREFIX, ATTACHMENT_FILE_SUFFIX);
      }
      catch (AeException e)
      {
         throw new AeBusinessProcessException(e.getLocalizedMessage(), e);
      }
   }

   /**
    * Returns all existing temporary files that are attachment files.
    *
    * @throws AeBusinessProcessException
    */
   public static File[] listAttachmentFiles() throws AeBusinessProcessException
   {
      try
      {
         // List all files with the same prefix and suffix that we use in
         // createAttachmentFile().
         return AeUtil.listTempFiles(ATTACHMENT_FILE_PREFIX, ATTACHMENT_FILE_SUFFIX);
      }
      catch (AeException e)
      {
         throw new AeBusinessProcessException(e.getLocalizedMessage(), e);
      }
   }
}
