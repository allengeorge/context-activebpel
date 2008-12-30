//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis.bpel/src/org/activebpel/rt/axis/bpel/handlers/AeRestoreClassLoaderHandler.java,v 1.1 2006/03/09 14:28:2
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
package org.activebpel.rt.axis.bpel.handlers;

import org.apache.axis.MessageContext;
import org.apache.axis.handlers.BasicHandler;

/**
 * This class restores the original classloader which was replaced
 * with the services context classloader by
 * <code>org.activebpel.rt.tomcat.AeSetClassLoader</code>.
 */
public class AeRestoreClassLoaderHandler extends BasicHandler
{
   /**
    * Restore original classloader.
    * @see org.apache.axis.Handler#invoke(org.apache.axis.MessageContext)
    */
   public void invoke(MessageContext aMessageContext)
   {
      restoreClassLoader(aMessageContext);
   }

   /**
    * Restore original classloader.
    * @see org.apache.axis.Handler#onFault(org.apache.axis.MessageContext)
    */
   public void onFault(MessageContext aMessageContext)
   {
      restoreClassLoader(aMessageContext);
   }
   
   /** 
    * Puts the original classloader context back in place from the one
    * saved in the passed message context.
    * @param aMessageContext the message context to restore the classloader for.
    */
   protected void restoreClassLoader(MessageContext aMessageContext)
   {
      ClassLoader loader = 
         (ClassLoader) aMessageContext.getProperty(AeSetClassLoaderHandler.SAVED_CLASSLOADER_PROPERTY);
      if (loader != null)
      {
         aMessageContext.setProperty(AeSetClassLoaderHandler.SAVED_CLASSLOADER_PROPERTY, null);
         Thread.currentThread().setContextClassLoader(loader);
      }
   }
}
