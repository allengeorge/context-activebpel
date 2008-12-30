// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/AeProcessDeployment.java,v 1.56 2007/09/12 12:58:1
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
package org.activebpel.rt.bpel.server.deploy;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.AeNamespaceFilteredWSDLIterator;
import org.activebpel.rt.bpel.IAeEndpointReference;
import org.activebpel.rt.bpel.IAePartnerLink;
import org.activebpel.rt.bpel.def.AePartnerLinkDef;
import org.activebpel.rt.bpel.def.AePartnerLinkDefKey;
import org.activebpel.rt.bpel.def.AePartnerLinkOpKey;
import org.activebpel.rt.bpel.def.AeProcessDef;
import org.activebpel.rt.bpel.def.io.AeBpelIO;
import org.activebpel.rt.bpel.impl.addressing.IAeAddressingHeaders;
import org.activebpel.rt.bpel.server.AeMessages;
import org.activebpel.rt.bpel.server.IAeProcessDeployment;
import org.activebpel.rt.bpel.server.addressing.AeEndpointReferenceSourceType;
import org.activebpel.rt.bpel.server.addressing.IAePartnerAddressing;
import org.activebpel.rt.bpel.server.catalog.resource.IAeResourceKey;
import org.activebpel.rt.bpel.server.deploy.pdd.AePartnerLinkDescriptor;
import org.activebpel.rt.bpel.server.engine.AeEngineFactory;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.activebpel.rt.wsdl.def.AeBPELExtendedWSDLDef;
import org.activebpel.rt.wsdl.def.IAePartnerLinkType;
import org.activebpel.rt.wsdl.def.IAeRole;
import org.activebpel.rt.xml.AeXMLParserBase;
import org.activebpel.wsio.IAeWebServiceEndpointReference;
import org.activebpel.wsio.IAeWsAddressingHeaders;
import org.activebpel.wsio.receive.IAeMessageContext;
import org.exolab.castor.xml.schema.Schema;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Manages process deployment information for a BPEL deployment.
 */
public class AeProcessDeployment implements IAeProcessDeployment
{
   /** The set of resource keys for this deployments context. */
   private Set mResourceKeys;

   /** The process definition which was deployed */
   private AeProcessDef mProcess;

   /** Element that all of this data came from. Holding onto it here for admin purposes. */
   private Element mSourceElement;

   /** Plan id. */
   protected int mPlanId;

   /** Map of partner link name to partner link descriptor object. */
   protected Map mPartnerLinkDescriptors = new HashMap();

   /** Process persistence type. */
   private AeProcessPersistenceType mPersistenceType;

   /** Process transaction type. */
   private AeProcessTransactionType mTransactionType;

   /** Exception management type */
   private AeExceptionManagementType mExceptionManagementType;
   
   /** Map of plDefKeys to AeServiceDeploymentInfo  */
   private Map mServices = new HashMap();

   /**
    * Constructs the deployment under the passed context.
    */
   public AeProcessDeployment(IAeDeploymentSource aSource) throws AeDeploymentException
   {
      mPlanId = aSource.getPlanId();
      mResourceKeys = aSource.getResourceKeys();
      mSourceElement = aSource.getProcessSourceElement();
      mPersistenceType = aSource.getPersistenceType();
      mTransactionType = aSource.getTransactionType();
      mExceptionManagementType = aSource.getExceptionManagementType();
      IAeServiceDeploymentInfo[] services = aSource.getServices();
      for (int i = 0; i < services.length; i++)
      {
         // Touching all nodes to avoid issues when multiple threads examine the same element
         AeXmlUtil.touchXmlNodes(services[i].getPolicies());
         mServices.put(services[i].getPartnerLinkDefKey(), services[i]);
      }
   }
   
   /**
    * No arg ctor for subclasses
    */
   protected AeProcessDeployment()
   {
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessPlan#getMyRolePortType(org.activebpel.rt.bpel.def.AePartnerLinkDefKey)
    */
   public QName getMyRolePortType(AePartnerLinkDefKey aPartnerLinkKey)
   {
      AePartnerLinkDef plink = getProcessDef().findPartnerLink(aPartnerLinkKey);
      IAePartnerLinkType plinkType = plink.getPartnerLinkType();
      IAeRole role = plinkType.findRole(plink.getMyRole());
      return role.getPortType().getQName();
   }

   /**
    * @see org.activebpel.rt.bpel.server.IAeProcessDeployment#getBpelSource()
    */
   public String getBpelSource()
   {
      try
      {
         Document bpelDom = AeBpelIO.serialize( getProcessDef() );
         return AeXMLParserBase.documentToString(bpelDom, true);
      }
      catch (Exception e)
      {
         AeException.logError( e, AeMessages.getString("AeProcessDeployment.ERROR_0") ); //$NON-NLS-1$
         return null;
      }
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessPlan#isCreateInstance(org.activebpel.rt.bpel.def.AePartnerLinkOpKey)
    */
   public boolean isCreateInstance(AePartnerLinkOpKey aPartnerLinkOpKey)
   {
      return getProcessDef().isCreateInstance(aPartnerLinkOpKey);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessPlan#getCorrelatedPropertyNames(org.activebpel.rt.bpel.def.AePartnerLinkOpKey)
    */
   public Collection getCorrelatedPropertyNames(AePartnerLinkOpKey aPartnerLinkOpKey)
   {
      return getProcessDef().getCorrelatedPropertyNames(aPartnerLinkOpKey);
   }

   /**
    * Returns an endpoint reference for the given partner link for partnerRole, or null if not found.
    * 
    * @param aPartnerLink the name of the partner link we are looking for
    * @see org.activebpel.rt.bpel.server.IAeProcessDeployment#getPartnerEndpointRef(java.lang.String)
    */
   public IAeEndpointReference getPartnerEndpointRef(String aPartnerLink)
   {
      AePartnerLinkDescriptor pLinkData = getPartnerLinkDescriptor( aPartnerLink );
      if( pLinkData != null )
      {
         return pLinkData.getPartnerEndpointReference();
      }
      else
      {
         return null;
      }
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessPlan#getProcessDef()
    */
   public AeProcessDef getProcessDef()
   {
      return mProcess;
   }

   /**
    * Sets the process definition for this deployment.
    * @param aDef the definition to be set
    */
   public void setProcess(AeProcessDef aDef)
   {
      mProcess = aDef;
   }

   /**
    * @see org.activebpel.rt.bpel.IAeWSDLProvider#getWSDLIterator(java.lang.String)
    */
   public Iterator getWSDLIterator( String aNamespaceUri )
   {
      return new AeNamespaceFilteredWSDLIterator(aNamespaceUri, getWSDLIterator(), this);
   }

   /**
    * @see org.activebpel.rt.bpel.IAeWSDLProvider#dereferenceIteration(java.lang.Object)
    */
   public AeBPELExtendedWSDLDef dereferenceIteration( Object aIteration )
   {
      // if the iteration object is a  key then its a request for context resources
      // (resource included in the deployment), so extract the resource directly from the cache
      // and return a wsdl object (schemas are wrapped, other hand back empty wsdl object)
      if( aIteration instanceof IAeResourceKey )
      {
         IAeResourceKey key = (IAeResourceKey)aIteration;
         try
         {
            if(key.isWsdlEntry())
            {
               return (AeBPELExtendedWSDLDef)AeEngineFactory.getCatalog().getResourceCache().getResource( key );
         }
            else if(key.isSchemaEntry())
            {
               Schema schema = (Schema)AeEngineFactory.getCatalog().getResourceCache().getResource( key );
               return new AeBPELExtendedWSDLDef(schema);
            }
            else
            {
               return AeBPELExtendedWSDLDef.getDefaultDef();
            }
         }
         catch( AeException ex )
         {
            ex.logError();
            return AeBPELExtendedWSDLDef.getDefaultDef();
         }
      }
      else
      {
         return (AeBPELExtendedWSDLDef)aIteration;
      }
   }

   /**
    * Sets the source element in place.
    * @param sourceElement
    */
   public void setSourceElement(Element sourceElement)
   {
      mSourceElement = sourceElement;
   }

   /**
    * Gets the source element.
    */
   public Element getSourceElement()
   {
      return mSourceElement;
   }

   
   /**
    * @see org.activebpel.rt.bpel.server.IAeProcessDeployment#updatePartnerLink(org.activebpel.rt.bpel.IAePartnerLink, org.activebpel.wsio.receive.IAeMessageContext)
    */
   public void updatePartnerLink(IAePartnerLink aPartnerLink, IAeMessageContext aMessageContext) throws AeBusinessProcessException
   {
      IAeWsAddressingHeaders wsAddressing = aMessageContext.getWsAddressingHeaders();
      
      // update the myRole partnerlink to include the service qname that's being hit.
      if (aPartnerLink.getMyReference() != null && wsAddressing.getRecipient() != null)
      {
         aPartnerLink.getMyReference().updateReferenceData(wsAddressing.getRecipient());
         // Increment the partner link version
         aPartnerLink.incrementVersionNumber();
      }

      if (AeUtil.notNullOrEmpty(aPartnerLink.getDefinition().getPartnerRole()))
      {
         IAeEndpointReference partnerEndpoint = aPartnerLink.getPartnerReference();      
         
         // Get the reply addressing headers
         IAePartnerAddressing partnerAddressing = AeEngineFactory.getPartnerAddressing();
         IAeAddressingHeaders replyHeaders = partnerAddressing.getReplyAddressing(wsAddressing, wsAddressing.getAction());
         // This is the epr that came in with the request
         IAeWebServiceEndpointReference invokerEndpoint = replyHeaders.getRecipient();
         
         AeEndpointReferenceSourceType type = (AeEndpointReferenceSourceType) getEndpointSourceType(aPartnerLink.getLocationPath());
         if (type == AeEndpointReferenceSourceType.PRINCIPAL)
         {
            partnerAddressing.updateFromPrincipal(aPartnerLink, aMessageContext.getPrincipal());
            partnerEndpoint = aPartnerLink.getPartnerReference();
         }
         else if (type == AeEndpointReferenceSourceType.INVOKER)
         {
            if (invokerEndpoint == null)
            {
               if (partnerEndpoint == null)
               {
                  throw new AeBusinessProcessException(AeMessages.getString("AeProcessDeployment.ERROR_3")+aPartnerLink); //$NON-NLS-1$
               }
            }
            else
            {
               partnerEndpoint.setReferenceData(invokerEndpoint);
            }
         }
         
         // Add any message context reference properties to partnerlink
         for (Iterator refProps = aMessageContext.getReferenceProperties(); refProps.hasNext();)
         {
            partnerEndpoint.addReferenceProperty((Element) refProps.next());
         }
         
         // Update the partner link with WS-Addressing info
         if (wsAddressing.getReplyTo() != null)
         {
            IAeEndpointReference newEndpoint = partnerAddressing.updateEndpointHeaders(replyHeaders, partnerEndpoint); 
            partnerEndpoint.setReferenceData(newEndpoint);
         }
         
         // Increment the partner link version
         aPartnerLink.incrementVersionNumber();
      }
   }

   /**
    * @see org.activebpel.rt.bpel.server.IAeProcessDeployment#getEndpointSourceType(java.lang.String)
    */
   public AeEndpointReferenceSourceType getEndpointSourceType(String aPartnerLink)
   {
      return getPartnerLinkDescriptor(aPartnerLink).getPartnerEndpointReferenceType();
   }

   /**
    * @see org.activebpel.rt.bpel.IAeContextWSDLProvider#getWSDLIterator()
    */
   public Iterator getWSDLIterator()
   {
      return mResourceKeys.iterator();
   }

   /**
    * @see org.activebpel.rt.bpel.server.IAeProcessDeployment#getPlanId()
    */
   public int getPlanId()
   {
      return mPlanId;
   }

   /**
    * Return the invoker uri for this partner link name or null if none is found.
	 * @param aPartnerLink
    */
   public String getInvokeHandler( String aPartnerLink )
   {
      return getPartnerLinkDescriptor( aPartnerLink ).getInvokeHandler();
   }

   /**
    * Accessor for a given partner link descriptor object.
    *
    * @param aPartnerLink
    */
   protected AePartnerLinkDescriptor getPartnerLinkDescriptor( String aPartnerLink )
   {
      AePartnerLinkDef plDef = getProcessDef().findPartnerLink(aPartnerLink);
      AePartnerLinkDefKey key = new AePartnerLinkDefKey(plDef);
      return (AePartnerLinkDescriptor)mPartnerLinkDescriptors.get( key );
   }

   /**
    * Add partner link decriptor information.
    *
    * @param aPartnerLinkDesc
    */
   public void addPartnerLinkDescriptor( AePartnerLinkDescriptor aPartnerLinkDesc )
   {
      AePartnerLinkDef plDef = getProcessDef().findPartnerLink(aPartnerLinkDesc.getPartnerLinkDefKey());
      mPartnerLinkDescriptors.put( new AePartnerLinkDefKey(plDef), aPartnerLinkDesc );
   }

   /**
    * Preprocess the underlying bpel def object.
    * @throws AeBusinessProcessException
    */
   public void preProcessDefinition() throws AeBusinessProcessException
   {
      try
      {
         getProcessDef().preProcessForExecution(this,
               AeEngineFactory.getExpressionLanguageFactory());
      }
      catch (AeException ae)
      {
         throw new AeBusinessProcessException(ae.getLocalizedMessage(), ae);
      }
   }

   /**
    * @see org.activebpel.rt.bpel.server.IAeProcessDeployment#getPersistenceType()
    */
   public AeProcessPersistenceType getPersistenceType()
   {
      return mPersistenceType;
   }

   /**
    * @see org.activebpel.rt.bpel.server.IAeProcessDeployment#getTransactionType()
    */
   public AeProcessTransactionType getTransactionType()
   {
      return mTransactionType;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeProcessPlan#isSuspendProcessOnUncaughtFaultEnabled()
    */
   public boolean isSuspendProcessOnUncaughtFaultEnabled()
   {
      return AeExceptionManagementUtil.isSuspendOnUncaughtFaultEnabled( mExceptionManagementType, getPersistenceType() );
   }

   /**
    * @see org.activebpel.rt.bpel.server.IAeProcessDeployment#getResourceKeys()
    */
   public Set getResourceKeys()
   {
      return mResourceKeys;
   }

   /**
    * @see org.activebpel.rt.bpel.server.IAeProcessDeployment#getServiceInfo(java.lang.String)
    */
   public IAeServiceDeploymentInfo getServiceInfo(String aPartnerLink)
   {
      AePartnerLinkDef plDef = getProcessDef().findPartnerLink(aPartnerLink);
      // plDef should never be null
      if (plDef == null)
         return null;
      AePartnerLinkDefKey key = new AePartnerLinkDefKey(plDef);
      return (IAeServiceDeploymentInfo)mServices.get( key );
   }
}
