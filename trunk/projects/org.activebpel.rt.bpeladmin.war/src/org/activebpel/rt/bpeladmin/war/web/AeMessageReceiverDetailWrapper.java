// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/web/AeMessageReceiverDetailWrapper.java,v 1.6 2007/03/06 15:48:5
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

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.impl.queue.AeMessageReceiver;
import org.activebpel.rt.bpel.server.engine.AeEngineFactory;

/**
 * Wraps single AeMessageReceiver for bean access.
 */
public class AeMessageReceiverDetailWrapper
{
   /** AeMessageReceiver detail object. */
   protected AeMessageReceiver mDetail;
   
   /**
    * Constructor.
    * @param aReceiver The delegate AeMessageReceiver.
    */
   public AeMessageReceiverDetailWrapper(AeMessageReceiver aReceiver)
   {
      mDetail = aReceiver;
   }
   
   /**
    * Getter for partner link type name.
    */
   public String getPartnerLinkTypeName()
   {
      return mDetail.getPartnerLinkOperationKey().getPartnerLinkName();
   }
   
   /**
    * Getter for port type qname string.
    */
   public String getPortType()
   {
      return AeWebUtil.toString( mDetail.getPortType() );
   }
   
   /**
    * Getter for the local portion of the port type qname.
    */
   public String getPortTypeLocal()
   {
      return mDetail.getPortType().getLocalPart();
   }
   
   /**
    * Getter for operation string.
    */
   public String getOperation()
   {
      return mDetail.getOperation();
   }
   
   /**
    * Getter for process id.
    */
   public int getProcessId()
   {
      return (int)mDetail.getProcessId();
   }
   
   /**
    * Returns true if receiver contains correlation data.
    */
   public boolean isCorrelated()
   {
      return mDetail.getCorrelation() != null && 
         !mDetail.getCorrelation().isEmpty();
   }
   
   /**
    * Returns correlation data as a string.  Each line represents a name value pair.
    */
   public String getCorrelationData()
   {
      String data = AeWebUtil.escapeSingleQuotes( AeWebUtil.toString(mDetail.getCorrelation()) ); 
      return data;
   }
   
   /**
    * Returns the location path string.
    */
   public String getLocationPath()
   {
      try
      {
         // NOTE: May acquire process lock, not to be used on listing pages
         String path = AeEngineFactory.getEngineAdministration().getLocationPathById(getProcessId(), getLocationPathId()); 
         return path.replace('\'',' ');
      }
      catch (AeBusinessProcessException e)
      {
         // ignore
         return ""; //$NON-NLS-1$
      }
   }
   
   /**
    * Returns the message receiver location path id
    */
   public int getLocationPathId()
   {
      return mDetail.getMessageReceiverPathId();
   }
}
