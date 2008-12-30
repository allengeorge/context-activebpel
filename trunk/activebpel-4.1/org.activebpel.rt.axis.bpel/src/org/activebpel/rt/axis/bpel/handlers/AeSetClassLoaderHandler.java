// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis.bpel/src/org/activebpel/rt/axis/bpel/handlers/AeSetClassLoaderHandler.java,v 1.1 2006/03/09 14:28:1
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

import javax.xml.namespace.QName;

import org.activebpel.rt.axis.bpel.deploy.AeResourceProvider;
import org.apache.axis.EngineConfiguration;
import org.apache.axis.MessageContext;

/**
 * This class loads the correct context classloader for the target service so we
 * can locate service resource effectivly.  Note that 
 * <code>org.activebpel.rt.tomcat.AeResetClassLoader</code> will put
 * back the original context class loader.
 */
public class AeSetClassLoaderHandler extends AeRestoreClassLoaderHandler
{
   /** the property to save the classloader in. */
   public static final String SAVED_CLASSLOADER_PROPERTY = "aeOldClassLoader"; // $NON_NLS1 //$NON-NLS-1$
   
   /**
    * Switches context class loaders to that saved for the target service.
    * @see org.apache.axis.Handler#invoke(org.apache.axis.MessageContext)
    */
   public void invoke(MessageContext aMessageContext)
   {
      EngineConfiguration engineConfig = aMessageContext.getAxisEngine().getConfig();

      if (engineConfig instanceof AeResourceProvider)
      {
         AeResourceProvider config = (AeResourceProvider) engineConfig;
         ClassLoader newLoader =
            config.getMyDeployment().getClassLoader(new QName(null, aMessageContext.getTargetService()));
         if (newLoader != null)
         {
            ClassLoader currentLoader = Thread.currentThread().getContextClassLoader();
            if (!newLoader.equals(currentLoader))
            {
               aMessageContext.setProperty(SAVED_CLASSLOADER_PROPERTY, currentLoader);
               Thread.currentThread().setContextClassLoader(newLoader);
            }
         }
      }
   }

}
