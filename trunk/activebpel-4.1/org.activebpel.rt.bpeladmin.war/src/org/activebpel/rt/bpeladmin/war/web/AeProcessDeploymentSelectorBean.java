// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/web/AeProcessDeploymentSelectorBean.java,v 1.4 2006/01/10 21:11:4
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
package org.activebpel.rt.bpeladmin.war.web;

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.server.admin.AeProcessDeploymentDetail;
import org.activebpel.rt.bpeladmin.war.AeMessages;
import org.activebpel.rt.util.AeUtil;

/**
 * Bean for selecting the info on a specific process deployment.
 */
public class AeProcessDeploymentSelectorBean extends AeAbstractAdminBean
{
   /** The process deployment id. */
   protected int mProcessDeploymentId;
   
   /** The specified process deployment */
   protected AeProcessDeploymentDetail mDetail;
   
   /**
    * Default constructor.
    */
   public AeProcessDeploymentSelectorBean()
   {
      
   }
   
   /**
    * The offset indicating a specific process deployment.
    * @param aOffset
    */
   public void setSelection( int aOffset )
   {
      AeProcessDeploymentDetail details[] = getAdmin().getDeployedProcesses();
      if (details.length > 0 && aOffset >=0 && aOffset < details.length )
      {
         mDetail = details[aOffset];
      }
      else
      {
         mDetail = null;
      }
      setProcessDeploymentId(aOffset);
   }
   
   /**
    * The qname indicating a specific process deployment.
    * @param aQName
    */
   public void setPlanQName( String aQName )
   {
       if( !AeUtil.isNullOrEmpty(aQName) )
       {
           String ns = null;
           String localPart = aQName;
           
           int colonIndex = aQName.lastIndexOf(':'); 
           if( colonIndex != -1 )
           {
               ns = aQName.substring(0,colonIndex);
               localPart = aQName.substring(colonIndex+1);
           }
           
           QName qname = new QName( ns, localPart );
           AeProcessDeploymentDetail[] details = getAdmin().getDeployedProcesses();
           for(int i=0; i < details.length; ++i)
           {
              if(qname.equals(details[i].getName()))
              {
                 setProcessDeploymentId(i);
                 mDetail = details[i];
                 break;
              }
           }
       }
   }
   
   /**
    * Getter for the process qname local part.
    */
   public String getLocalName()
   {
      if (mDetail != null)
      {
         return mDetail.getName().getLocalPart();
      }
      else
      {
         return AeMessages.getString("AeProcessDeploymentSelectorBean.DETAILS_NOT_AVAILABLE"); //$NON-NLS-1$
      }
   }
   
   /**
    * Getter for the process qname namespace uri.
    */
   public String getNamespaceURI()
   {
      if (mDetail != null)
      {
         return mDetail.getName().getNamespaceURI();
      }
      else
      {
         return AeMessages.getString("AeProcessDeploymentSelectorBean.DETAILS_NOT_AVAILABLE"); //$NON-NLS-1$
      }
   }
   
   /**
    * Getter for the process pdd source xml.
    */
   public String getSourceXml()
   {
      if (mDetail != null)
      {
         return mDetail.getSourceXml();
      }
      else
      {
         return AeMessages.getString("AeProcessDeploymentSelectorBean.DETAILS_NOT_AVAILABLE"); //$NON-NLS-1$
      }
   }
   
   /**
    * Getter for the bpel process source xml.
    */
   public String getBpelSourceXml()
   {
      if (mDetail != null)
      {
         return mDetail.getBpelSourceXml();
      }
      else
      {
         return AeMessages.getString("AeProcessDeploymentSelectorBean.DETAILS_NOT_AVAILABLE"); //$NON-NLS-1$
      }      
   }
   
   /**
    * @return Returns the processDeploymentId.
    */
   public int getProcessDeploymentId()
   {
      return mProcessDeploymentId;
   }
   
   /**
    * @param aProcessDeploymentId The processDeploymentId to set.
    */
   public void setProcessDeploymentId(int aProcessDeploymentId)
   {
      mProcessDeploymentId = aProcessDeploymentId;
   }
}
