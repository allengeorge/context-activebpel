//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis/src/org/activebpel/rt/axis/AeMutableServiceDesc.java,v 1.3 2006/07/18 20:09:0
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
package org.activebpel.rt.axis;

import org.apache.axis.description.OperationDesc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A <code>ServiceDesc</code> impl that delegates access to its wsdl related
 * properties to an <code>IAeWsdlReference</code>.  This allows the wsdl reference
 * to cache/update its properties as the catalog is updated.
 */
public class AeMutableServiceDesc extends AeServiceDesc
{
   /**
    * The <code>IAeWsdlReference</code> impl.
    */
   private IAeWsdlReference mWsdlReference;
   
   /**
    * Constructor.
    */
   public AeMutableServiceDesc()
   {
      super();
   }
   
   /**
    * @see org.apache.axis.description.ServiceDesc#getProperty(java.lang.String)
    */
   public Object getProperty(String aName)
   {
      return getWsdlReferenceProperty( aName, getWsdlReference(), getProperties() );
   }
   
   /**
    * Look for wsdl related properties in the <code>IAeWsdlReference</code> member,
    * otherwise use the standard properties map. 
    * @param aPropertyKey
    * @param aReference
    * @param aServiceDescProps
    */
   protected Object getWsdlReferenceProperty( String aPropertyKey, IAeWsdlReference aReference, Map aServiceDescProps )
   {
      Object propertyObj = null;
      
      if( AeHandler.WSDL_DEF_ENTRY.equals( aPropertyKey ) )
      {
         propertyObj = aReference.getWsdlDef();
      }
      else if( AeHandler.PARTNER_LINK_ENTRY.equals( aPropertyKey ) )
      {
         propertyObj = aReference.getPartnerLinkDef();
      }
      else if( AeHandler.PORT_TYPE_ENTRY.equals( aPropertyKey) )
      {
         propertyObj = aReference.getPortTypeQName();
      }
      else if( aServiceDescProps != null )
      {
         propertyObj = aServiceDescProps.get( aPropertyKey );
      }
      
      return propertyObj;
   }
   
   /**
    * @see org.apache.axis.description.ServiceDesc#getOperations()
    */
   public ArrayList getOperations()
   {
      return getWsdlReference().getOperations();
   }

   /**
    * @see org.apache.axis.description.ServiceDesc#getOperationsByName(java.lang.String)
    */
   public OperationDesc[] getOperationsByName(String aMethodName)
   {
      return getWsdlReference().getOperationsByName( aMethodName );
   }

   /**
    * @see org.apache.axis.description.ServiceDesc#getOperationByName(java.lang.String)
    */
   public OperationDesc getOperationByName(String aMethodName)
   {
      return getWsdlReference().getOperationByName( aMethodName );
   }

   /**
    * @see org.apache.axis.description.ServiceDesc#removeOperationDesc(org.apache.axis.description.OperationDesc)
    */
   public void removeOperationDesc(OperationDesc aOperation)
   {
      getWsdlReference().removeOperationDesc( aOperation );
   }

   /**
    * @see org.apache.axis.description.ServiceDesc#getAllowedMethods()
    */
   public List getAllowedMethods()
   {
      return getWsdlReference().getAllowedMethods();
   }
   
   /**
    * @param aWsdlReference The wsdlReference to set.
    */
   public void setWsdlReference(IAeWsdlReference aWsdlReference)
   {
      mWsdlReference = aWsdlReference;
   }
   
   /**
    * @see org.activebpel.rt.axis.AeServiceDesc#setInitialized(boolean)
    */
   public void setInitialized(boolean aFlag)
   {
      super.setInitialized(aFlag);

      if( aFlag )
      {
         AeWsdlReferenceTracker.registerReference( getName(), getWsdlReference() );
      }
   }

   /**
    * @return Returns the wsdlReference.
    */
   public IAeWsdlReference getWsdlReference()
   {
      return mWsdlReference;
   }
}
