// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis.bpel/src/org/activebpel/rt/axis/bpel/deploy/AeCredentialPolicyMapper.java,v 1.2 2006/06/05 21:19:4
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
package org.activebpel.rt.axis.bpel.deploy;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.activebpel.rt.AeException;
import org.activebpel.rt.IAeConstants;
import org.activebpel.rt.bpel.server.AeCryptoUtil;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Creates Policy Deployment for a XPath mapping assertions 
 */
public class AeCredentialPolicyMapper extends AeAxisPolicyMapper 
{
   
   /**
    * Constructor.
    * @param aConfig
    */
   public AeCredentialPolicyMapper(Map aConfig)
   {
            
   }   
   
   /**
    * @see org.activebpel.rt.bpel.server.deploy.IAePolicyMapper#getServerRequestHandlers(java.util.List)
    */
   public List getServerRequestHandlers( List aPolicyList ) throws AeException
   {
      return Collections.EMPTY_LIST;
   }

   /**
    * @see org.activebpel.rt.bpel.server.deploy.IAePolicyMapper#getServerResponseHandlers(java.util.List)
    */
   public List getServerResponseHandlers( List aPolicyList )
   throws AeException
   {
      return Collections.EMPTY_LIST;
   }
   
   
   /**
    * @see org.activebpel.rt.bpel.server.deploy.IAePolicyMapper#getClientRequestHandlers(java.util.List)
    */
   public List getClientRequestHandlers( List aPolicyList )
   throws AeException
   {
      return Collections.EMPTY_LIST;
   }

   /**
    * @see org.activebpel.rt.bpel.server.deploy.IAePolicyMapper#getClientResponseHandlers(java.util.List)
    */
   public List getClientResponseHandlers( List aPolicyList )
   throws AeException
   {
      return Collections.EMPTY_LIST;
   }

   /**
    * @see org.activebpel.rt.bpel.server.deploy.IAePolicyMapper#getServiceParameters(java.util.List)
    */
   public List getServiceParameters(List aPolicyList) throws AeException
   {
      return Collections.EMPTY_LIST;
   }

   /**
    * @see org.activebpel.rt.bpel.server.deploy.IAePolicyMapper#getCallProperties(java.util.List)
    */
   public Map getCallProperties(List aPolicyList) throws AeException
   {
      HashMap map = new HashMap();
      
      for (Iterator it = aPolicyList.iterator(); it.hasNext();)
      {
         Element aPolicyElement = (Element)it.next();

         String username = null;
         String password = null;
         // grab the username (if any)
         NodeList children = aPolicyElement.getElementsByTagNameNS(IAeConstants.ABP_NAMESPACE_URI,
               TAG_ASSERT_AUTH_USER);
         if ( children.getLength() > 0 )
         {
            username = children.item(0).getFirstChild().getNodeValue();
            map.put(TAG_ASSERT_AUTH_USER, username);
         }

         // grab the cleartext password (if any)
         children = aPolicyElement.getElementsByTagNameNS(IAeConstants.ABP_NAMESPACE_URI,
               TAG_ASSERT_AUTH_PWD_CLEARTEXT);
         if ( children.getLength() > 0 )
         {
            password = children.item(0).getFirstChild().getNodeValue();
            password = AeCryptoUtil.encryptString(password);            
            map.put(TAG_ASSERT_AUTH_PASSWORD, password);
         }
         // grab the encrypted password (if any)
         children = aPolicyElement.getElementsByTagNameNS(IAeConstants.ABP_NAMESPACE_URI, TAG_ASSERT_AUTH_PASSWORD);
         if ( children.getLength() > 0 )
         {
            password = children.item(0).getFirstChild().getNodeValue();
            map.put(TAG_ASSERT_AUTH_PASSWORD, password);
         }

         // grab the preemptive flag
         children = aPolicyElement.getElementsByTagNameNS(IAeConstants.ABP_NAMESPACE_URI, TAG_ASSERT_AUTH_PREEMPTIVE);
         if ( children.getLength() > 0 )
         {
            map.put(TAG_ASSERT_AUTH_PREEMPTIVE, "true"); //$NON-NLS-1$
         }
      }
      return map;
   }
}
