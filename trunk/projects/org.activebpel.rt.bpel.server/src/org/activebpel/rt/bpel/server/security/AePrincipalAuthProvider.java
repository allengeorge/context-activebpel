//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/security/AePrincipalAuthProvider.java,v 1.1 2007/02/13 15:26:5
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
package org.activebpel.rt.bpel.server.security;

import java.security.Principal;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.security.auth.Subject;
import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.def.AePartnerLinkDef;
import org.activebpel.rt.bpel.impl.IAeProcessPlan;
import org.activebpel.rt.bpel.server.AeMessages;
import org.activebpel.rt.bpel.server.IAeProcessDeployment;
import org.activebpel.rt.bpel.server.deploy.IAeServiceDeploymentInfo;
import org.activebpel.rt.bpel.server.engine.AeEngineFactory;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.wsio.receive.IAeMessageContext;

/**
 * Authorization provider that checks if one of a subject's principals is in
 * one of the configured allowed roles. 
 */
public class AePrincipalAuthProvider implements IAeAuthorizationProvider
{

   /**
    * Constructor with a configuration map
    * 
    * @param aConfig
    */
   public AePrincipalAuthProvider(Map aConfig)
   {
      
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.security.IAeAuthorizationProvider#authorize(javax.security.auth.Subject, org.activebpel.wsio.receive.IAeMessageContext)
    */
   public boolean authorize(Subject aSubject, IAeMessageContext aContext) throws AeSecurityException
   {
      if (AeUtil.isNullOrEmpty(aContext.getProcessName()))
      {
         throw new AeSecurityException(AeMessages.getString("AePrincipalAuthProvider.0")); //$NON-NLS-1$
      }
      
      try
      {
         // Lookup the allowed roles from the service deployment
         IAeProcessDeployment deployment = getDeploymentPlan(aContext.getProcessName());
         AePartnerLinkDef plinkDef = getPartnerLinkDef(deployment, aContext);
         IAeServiceDeploymentInfo service = deployment.getServiceInfo(plinkDef.getLocationPath());
         
         return authorize(aSubject, service.getAllowedRoles());
      }
      catch (AeBusinessProcessException ae)
      {
         throw new AeSecurityException(ae.getLocalizedMessage(), ae);
      }
   }

   /**
    * @see org.activebpel.rt.bpel.server.security.IAeAuthorizationProvider#authorize(javax.security.auth.Subject, java.lang.String)
    */
   public boolean authorize(Subject aSubject, Set aAllowedRoles) throws AeSecurityException
   {
      if (AeUtil.isNullOrEmpty(aAllowedRoles))
      {
         // No role restrictions configured
         return true;
      }

      // We're not enforcing roles
      if (!AeEngineFactory.getEngineConfig().isAllowedRolesEnforced())
      {
         return true;
      }
      
      if (aSubject == null)
      {
         // No login, return false 
         return false;
      }
      
      // Check the subject for each role
      
      for (Iterator it = aAllowedRoles.iterator(); it.hasNext();)
      {
         String thisRole = (String) it.next();
         if (isSubjectInRole(aSubject, thisRole))
         {
            return true;
         }
      }
      
      // No principals matched any roles
      return false;      
   }
   
   /**
    * Checks to see if one of the subject's principals is in the target role  
    * 
    * @param aSubject
    * @param aRolename
    * @return true if match found
    */
   protected boolean isSubjectInRole(Subject aSubject, String aRolename)
   {
      for (Iterator it = aSubject.getPrincipals().iterator(); it.hasNext();)
      {
         Principal p = (Principal) it.next();
         if (p instanceof IAePrincipal)
         {
            return ((IAePrincipal) p).isUserInRole(aRolename);
         }
         else if (aRolename.equals(p.getName()))
         {
            return true;
         }
      }
      
      return false;
   }

   /**
    * Returns the partner link definition 
    * 
    * @param aPlan
    * @param aContext
    * @return the partner link definition
    */
   protected AePartnerLinkDef getPartnerLinkDef(IAeProcessPlan aPlan, IAeMessageContext aContext)
   {
      return aPlan.getProcessDef().findPartnerLink(aContext.getPartnerLink());
   }
   
   /**
    * Gets the deployment plan for this service.
    * 
    * @param aServiceDesc
    * @throws AeBusinessProcessException
    */
   protected IAeProcessDeployment getDeploymentPlan(QName aProcessName) throws AeBusinessProcessException
   {
      IAeProcessDeployment deploymentPlan = AeEngineFactory.getDeploymentProvider().findCurrentDeployment(aProcessName);
      return deploymentPlan;
   }
   
}
