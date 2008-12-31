//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/config/AeFileBasedEngineConfig.java,v 1.6 2005/06/08 13:30:3
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
package org.activebpel.rt.bpel.server.engine.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.text.MessageFormat;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.config.AeDefaultEngineConfiguration;
import org.activebpel.rt.bpel.server.AeMessages;
import org.activebpel.rt.util.AeCloser;

/**
 * Extension of <code>AeDefaultEngineConfiguratio</code> that
 * is capable of receiving change notification from a <code>AeDirectoryScanner</code>.
 * Allow for config file changes made externally (not throug console) to be
 * detected.
 */
public class AeFileBasedEngineConfig extends AeDefaultEngineConfiguration
{
   // Error message constants.
   private static final String SAVE_ERROR = AeMessages.getString("AeFileBasedEngineConfig.ERROR_0"); //$NON-NLS-1$
   private static final String UPDATE_ERROR = AeMessages.getString("AeFileBasedEngineConfig.ERROR_1"); //$NON-NLS-1$
      
   /** Object lock for preventing duplicate updates. */
   private Object mLock = new Object();
   /** The file we are listening on. */
   protected String mFilePath;
   /** The last modified time of the config file. */
   protected long mLastModified;
   /** The class loader */
   protected ClassLoader mClassLoader;
   
   /**
    * Constructor.
    * @param aConfigFile
    * @param aClassLoader
    */
   public AeFileBasedEngineConfig( File aConfigFile, ClassLoader aClassLoader )
   {
      mFilePath = aConfigFile.getPath();
      // TODO Z! check exception handling with jar resource
      mLastModified = new File(mFilePath).lastModified();
      mClassLoader = aClassLoader;
   }

   /**
    * @see org.activebpel.rt.bpel.config.IAeUpdatableEngineConfig#update()
    */
   public void update()
   {
      synchronized(mLock)
      {
         Writer w = null;
         try
         {
            File file = new File(mFilePath);
            w = new FileWriter( file );
            // it's possible that this save will cause the 'updateBecauseFileChanged'
            // method to be called (from a scan) before the last modified time has been updated,
            // so make sure that method locks on the mLock
            save(w);
            mLastModified = file.lastModified();
         }
         catch( Exception ae )
         {
            AeException.logError( ae, MessageFormat.format(SAVE_ERROR, new String[]{mFilePath}) );
         }
         finally
         {
            AeCloser.close( w );
         }
         super.update();
      }
   }
   
   /**
    * Update the engine config file settings due to a change in the file.
    */
   public void updateBecauseFileChanged()
   {
      // sync on the lock to prevent duplicate 
      // update calls after file has been 
      // saved (via above update method call)
      // but before last modified has been
      // set to new value
      synchronized( mLock )
      {
         File file = new File( mFilePath );

         if( mLastModified != file.lastModified() )
         {
            InputStream in = null;
            try
            {
               in = new FileInputStream( file );
               loadConfig( this, in, mClassLoader );
               super.update();
            }
            catch( IOException io )
            {
               AeException.logError( io, MessageFormat.format( UPDATE_ERROR, new String[]{mFilePath}) );
            }
            finally
            {
               AeCloser.close(in);
            }
         }
      }
   }
}
