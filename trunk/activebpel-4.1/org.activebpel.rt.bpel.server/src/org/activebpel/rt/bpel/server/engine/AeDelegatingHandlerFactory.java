//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/AeDelegatingHandlerFactory.java,v 1.1 2007/01/26 22:54:0
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
package org.activebpel.rt.bpel.server.engine; 

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.server.AeMessages;
import org.activebpel.rt.bpel.server.engine.AeInvokeHandlerUri;
import org.activebpel.rt.util.AeUtil;

/**
 * An abstract handler factory that delegates to other factories based on the protocol in the URN. 
 */
public class AeDelegatingHandlerFactory 
{
   /** key for the map of protocol to factory classes in the config file */
   private static final String PROTOCOL_KEY = "Protocols"; //$NON-NLS-1$
   
   /** default protocol to use if none specified in the invokerHandler */
   private static final String DEFAULT_PROTOCOL = "default"; //$NON-NLS-1$

   /** map of protocol to factory */
   private Map mDelegates = new HashMap();
   
   /**
    * Constructor loads the delegate factories from the protocol map in the config.
    * 
    * @param aConfig
    */
   public AeDelegatingHandlerFactory(Map aConfig) throws AeException
   {
      try
      {
         Map protocolMap = (Map) aConfig.get(PROTOCOL_KEY);
         for (Iterator it = protocolMap.entrySet().iterator(); it.hasNext();)
         {
            Map.Entry entry = (Map.Entry) it.next();
            String clazz = (String) entry.getValue();
            getDelegates().put(entry.getKey(), Class.forName(clazz).newInstance());
         }
      }
      catch(Exception e)
      {
         throw new AeException(e);
      }
   }
   
   /**
    * @return Returns the delegates.
    */
   public Map getDelegates()
   {
      return mDelegates;
   }
   
   /**
    * Gets the protocol from the invoker uri or the default protocol if the uri is null or empty
    * @param aURI
    */
   protected String getProtocol(String aURI)
   {
      if (AeUtil.isNullOrEmpty(aURI))
      {
         return DEFAULT_PROTOCOL;
      }
      return AeInvokeHandlerUri.getProtocolString(aURI);
   }

   /**
    * Gets the factory to delegate the requests to.
    * @param aURI
    */
   protected Object getDelegate(String aURI) throws AeBusinessProcessException
   {
      String protocol = getProtocol(aURI);
      Object delegate = getDelegates().get(protocol);
      if (delegate == null)
      {
         Object[] args = new Object[2];
         args[0] = getProtocol(aURI);
         args[1] = aURI;
         throw new AeBusinessProcessException(AeMessages.format("AeDelegatingHandlerFactory.MissingHandlerFactory", args)); //$NON-NLS-1$
      }
      return delegate;
   }
}
 
