//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/bpr/AeUnpackedBprAccessor.java,v 1.2 2005/06/17 21:51:1
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
package org.activebpel.rt.bpel.server.deploy.bpr;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.activebpel.rt.bpel.server.AeMessages;
import org.activebpel.rt.bpel.server.deploy.AeDeploymentException;
import org.activebpel.rt.bpel.server.deploy.IAeDeploymentContext;
import org.activebpel.rt.util.AeUtil;

/**
 * A <code>IAeBprFileStrategy</code> impl where bpr resources are pulled from
 * an unpacked copy of the original bpr file.
 */
public class AeUnpackedBprAccessor extends AeJarFileBprAccessor
{
   /**
    * Constructor.
    * @param aDeploymentContext
    */
   public AeUnpackedBprAccessor(IAeDeploymentContext aDeploymentContext)
   {
      super(aDeploymentContext);
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.deploy.bpr.IAeBprAccessor#init()
    */
   public void init() throws AeDeploymentException
   {
      File rootDir = new File( getDeploymentContext().getTempDeploymentLocation().getFile() );
      
      setPddResources(new ArrayList()); 
      listFiles( getPddResources(), rootDir, "", new AeSuffixFilter(".pdd") ); //$NON-NLS-1$ //$NON-NLS-2$

      setPdefResources( new ArrayList() );
      listFiles( getPdefResources(), rootDir, "", new AeSuffixFilter(".pdef") ); //$NON-NLS-1$ //$NON-NLS-2$

      List wsddList = new ArrayList();
      listFiles( wsddList, rootDir, "", new AeSuffixFilter(".wsdd") ); //$NON-NLS-1$ //$NON-NLS-2$
      
      if( !AeUtil.isNullOrEmpty(wsddList) )
      {
         if( !getPddResources().isEmpty() )
         {
            throw new AeDeploymentException(AeMessages.getString("AeUnpackedBprFile.ERROR_0"));  //$NON-NLS-1$
         }

         setWsddResource( (String)wsddList.iterator().next() );
      }
   }

   /**
    * Extract the appropriate files from the directory.
    * @param aMatches Container collection for holding matched resources.
    * @param aDir The directory in which to look for matches (recurses).
    * @param aPath Path prefix to add to file names.
    * @param aFilter FileFilter instance.
    */
   protected void listFiles( Collection aMatches, File aDir, String aPath, FileFilter aFilter )
   {
      File[] files = aDir.listFiles(aFilter);
      if( files!= null )
      {
         for( int i = 0; i < files.length; i++ )
         {
            if( files[i].isFile() )
            {
               aMatches.add( aPath + files[i].getName() );
            }
            else
            {
               String name = files[i].getName();
               if( !name.endsWith(File.separator) )
               {
                  name+=File.separatorChar;
               }
               listFiles( aMatches, files[i], aPath+name, aFilter );
            }
         }
      }
   }
   
   /**
    * Simple suffix file filter.
    */
   public class AeSuffixFilter implements FileFilter
   {
      /** suffix string */
      private String mSuffix;
      
      /**
       * Constructor.
       * @param aSuffix
       */
      public AeSuffixFilter(String aSuffix)
      {
         mSuffix = aSuffix;   
      }
      
      /**
       * @see java.io.FileFilter#accept(java.io.File)
       */
      public boolean accept(File aFile)
      {
         return aFile.isDirectory() || aFile.getName().endsWith(mSuffix);
      }
   }
}
